package org.framewoke.core.compiler;
import java.io.File;
import java.io.FileWriter;

import javax.tools.JavaCompiler;
import javax.tools.JavaCompiler.CompilationTask;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;
/**
 * 动态生成类以及编译，JDK版本必须要在1.6，或者1.6以上
 */
public class Compiler {

    //回车加换行符
    static String rt = "\r\n";
    //生成类的源文件，写成字符串的形式
    static String src =
        "package org.framewoke.core.compiler;"
        + "public class HelloWorld  {" + rt +
        "    public static void main(String[] args) {" + rt +
        "         System.out.println(\"Hello world!\");" + rt +
        "    }" + rt +
        "}";

    public static void main (String[] args) throws Exception {

        //写文件，目录可以自己定义
        String filename = System.getProperty ("user.dir").replaceAll("\\\\", "/") + "/src/test/java/org/framewoke/core/compiler/HelloWorld.java";
       System.err.println(filename);
        File file = new File (filename);
        FileWriter fw = new FileWriter (file);
        fw.write (src);
        fw.flush();
        fw.close();

        //编译文件,调用jdk本身的工具
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        StandardJavaFileManager sjfm = compiler.getStandardFileManager (null, null, null);
        Iterable<? extends JavaFileObject> units = sjfm.getJavaFileObjects (filename);
        CompilationTask ct = compiler.getTask (null, sjfm, null, null, null, units);
        ct.call();
        sjfm.close();
    }
}