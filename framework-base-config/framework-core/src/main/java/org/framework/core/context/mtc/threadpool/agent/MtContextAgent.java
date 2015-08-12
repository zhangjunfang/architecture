package org.framework.core.context.mtc.threadpool.agent;


import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.Instrumentation;
import java.util.logging.Logger;


/**
 * @author  ocean zhangjunfang0505@163.com
 * @see <a href="http://docs.oracle.com/javase/6/docs/api/java/lang/instrument/package-summary.html">The mechanism for instrumentation</a>
 * 
 */
public class MtContextAgent {
    private static final Logger logger = Logger.getLogger(MtContextAgent.class.getName());

    public static void premain(String agentArgs, Instrumentation inst) {
        logger.info("[MtContextAgent.premain] begin, agentArgs: " + agentArgs);
        install(agentArgs, inst);
    }

    static void install(String agentArgs, Instrumentation inst) {
        logger.info("[MtContextAgent.install] agentArgs: " + agentArgs + ", Instrumentation: " + inst);

        ClassFileTransformer transformer = new MtContextTransformer();
        inst.addTransformer(transformer, true);

        logger.info("[MtContextAgent.install] addTransformer success.");
    }
}
