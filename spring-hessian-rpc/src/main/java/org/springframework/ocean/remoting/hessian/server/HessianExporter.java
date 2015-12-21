/**
 * 
 */
package org.springframework.ocean.remoting.hessian.server;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.commons.logging.Log;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.remoting.support.RemoteExporter;
import org.springframework.util.Assert;

import com.caucho.hessian.io.AbstractHessianInput;
import com.caucho.hessian.io.AbstractHessianOutput;
import com.caucho.hessian.io.Hessian2Input;
import com.caucho.hessian.io.Hessian2Output;
import com.caucho.hessian.io.HessianInput;
import com.caucho.hessian.io.HessianOutput;
import com.caucho.hessian.io.SerializerFactory;
import com.caucho.hessian.server.HessianSkeleton;

/**
 * 
 * @author： ocean
 * 创建时间：2015年12月21日
 * mail：zhangjunfang0505@163.com
 * 描述：
 */
public class HessianExporter extends RemoteExporter implements InitializingBean {
    public static final String CONTENT_TYPE_HESSIAN = "application/x-hessian";

    private SerializerFactory  serializerFactory    = new SerializerFactory();

    private Log                debugLogger;

    private HessianSkeleton    skeleton;

    /**
     * Specify the Hessian SerializerFactory to use.
     * <p>This will typically be passed in as an inner bean definition
     * of type {@code com.caucho.hessian.io.SerializerFactory},
     * with custom bean property values applied.
     */
    public void setSerializerFactory(SerializerFactory serializerFactory) {
        this.serializerFactory = (serializerFactory != null ? serializerFactory
            : new SerializerFactory());
    }

    /**
     * Set whether to send the Java collection type for each serialized
     * collection. Default is "true".
     */
    public void setSendCollectionType(boolean sendCollectionType) {
        this.serializerFactory.setSendCollectionType(sendCollectionType);
    }

    /**
     * Set whether Hessian's debug mode should be enabled, logging to
     * this exporter's Commons Logging log. Default is "false".
     * @see com.caucho.hessian.client.HessianProxyFactory#setDebug
     */
    public void setDebug(boolean debug) {
        this.debugLogger = (debug ? logger : null);
    }

    public void afterPropertiesSet() {
        prepare();
    }

    /**
     * Initialize this exporter.
     */
    public void prepare() {
        checkService();
        checkServiceInterface();
        this.skeleton = new HessianSkeleton(getProxyForService(), getServiceInterface());
    }

    /**
     * Perform an invocation on the exported object.
     * @param inputStream the request stream
     * @param outputStream the response stream
     * @throws Throwable if invocation failed
     */
    public void invoke(InputStream inputStream, OutputStream outputStream) throws Throwable {
        Assert.notNull(this.skeleton, "Hessian exporter has not been initialized");
        doInvoke(this.skeleton, inputStream, outputStream);
    }

    /**
     * Actually invoke the skeleton with the given streams.
     * @param skeleton the skeleton to invoke
     * @param inputStream the request stream
     * @param outputStream the response stream
     * @throws Throwable if invocation failed
     */
    protected void doInvoke(HessianSkeleton skeleton, InputStream inputStream,
                            OutputStream outputStream) throws Throwable {

        ClassLoader originalClassLoader = overrideThreadContextClassLoader();
        try {
            InputStream isToUse = inputStream;
            OutputStream osToUse = outputStream;

            if (this.debugLogger != null && this.debugLogger.isDebugEnabled()) {
//                PrintWriter debugWriter = new PrintWriter(new CommonsLogWriter(this.debugLogger));
//                HessianDebugInputStream dis = new HessianDebugInputStream(inputStream, debugWriter);
//                dis.startTop2();
//                HessianDebugOutputStream dos = new HessianDebugOutputStream(outputStream,
//                    debugWriter);
//                dos.startTop2();
//                isToUse = dis;
//                osToUse = dos;
            }

            if (!isToUse.markSupported()) {
                isToUse = new BufferedInputStream(isToUse);
                isToUse.mark(1);
            }

            int code = isToUse.read();
            int major;
            int minor;

            AbstractHessianInput in;
            AbstractHessianOutput out;

            if (code == 'H') {
                // Hessian 2.0 stream
                major = isToUse.read();
                minor = isToUse.read();
                if (major != 0x02) {
                    throw new IOException("Version " + major + "." + minor + " is not understood");
                }
                in = new Hessian2Input(isToUse);
                out = new Hessian2Output(osToUse);
                in.readCall();
            } else if (code == 'C') {
                // Hessian 2.0 call... for some reason not handled in HessianServlet!
                isToUse.reset();
                in = new Hessian2Input(isToUse);
                out = new Hessian2Output(osToUse);
                in.readCall();
            } else if (code == 'c') {
                // Hessian 1.0 call
                major = isToUse.read();
                minor = isToUse.read();
                in = new HessianInput(isToUse);
                if (major >= 2) {
                    out = new Hessian2Output(osToUse);
                } else {
                    out = new HessianOutput(osToUse);
                }
            } else {
                throw new IOException(
                    "Expected 'H'/'C' (Hessian 2.0) or 'c' (Hessian 1.0) in hessian input at "
                            + code);
            }

            if (this.serializerFactory != null) {
                in.setSerializerFactory(this.serializerFactory);
                out.setSerializerFactory(this.serializerFactory);
            }

            try {
                skeleton.invoke(in, out);
            } finally {
                try {
                    in.close();
                    isToUse.close();
                } catch (IOException ex) {
                    // ignore
                }
                try {
                    out.close();
                    osToUse.close();
                } catch (IOException ex) {
                    // ignore
                }
            }
        } finally {
            resetThreadContextClassLoader(originalClassLoader);
        }
    }
}
