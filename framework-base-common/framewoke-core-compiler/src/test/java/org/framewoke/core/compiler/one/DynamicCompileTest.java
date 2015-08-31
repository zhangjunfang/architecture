package org.framewoke.core.compiler.one;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Arrays;

import javax.tools.JavaCompiler;
import javax.tools.JavaCompiler.CompilationTask;
import javax.tools.JavaFileObject;
import javax.tools.ToolProvider;

public class DynamicCompileTest {
	
    public static void main(String[] args) throws IOException, ClassNotFoundException, NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, InstantiationException{
        
        /*
         * 编译内存中的java代码
         * */
        // 1.将代码写入内存中
        StringWriter writer = new StringWriter(); // 内存字符串输出流
        PrintWriter out = new PrintWriter(writer);
        out.println("package org.framewoke.core.compiler.one;");
        out.println("public class Hello{");
        out.println("public static void main(String[] args){");
        out.println("System.out.println(\"HelloWorld! 2\");");
        out.println("}");
        out.println("}");
        out.flush();
        out.close();
        
        // 2.开始编译
        JavaCompiler javaCompiler = ToolProvider.getSystemJavaCompiler();
        JavaFileObject fileObject = new JavaStringObject("Hello", writer.toString());
        CompilationTask task = javaCompiler.getTask(null, null, null, Arrays.asList("-d","./target/test-classes"), null, Arrays.asList(fileObject));
        boolean success = task.call();
        if (!success) {
            System.out.println("编译失败");
        }else{
            System.out.println("编译成功");
        }
        URL[] urls = new URL[]{new URL("file:/" + "./target/test-classes")};
        URLClassLoader classLoader = new URLClassLoader(urls);
        Class<?> classl = classLoader.loadClass("org.framewoke.core.compiler.one.Hello");
        Method method = classl.getDeclaredMethod("main", String[].class);
        String[] argsl = {null};
        method.invoke(classl.newInstance(), argsl);
        classLoader.close();
    }
}
