package org.framewoke.core.compiler;
import java.io.File;
import java.io.FileWriter;
import java.lang.reflect.Method;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;

public class CompileString { 
    public static void main(String[] args) throws Exception { 
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler(); 
        StandardJavaFileManager fileManager = compiler.getStandardFileManager( 
                null, Locale.getDefault(), Charset.forName("UTF-8")); 

        StringObject so = new StringObject( 
        		System.getProperty ("user.dir") .replaceAll("\\\\", "/")+ "/src/main/java"+"/org/framewoke/core/compiler/CalculatorTest", 
                "package org.framewoke.core.compiler; "
                + "public  class  "
                + "CalculatorTest {" 
                        + " public int multiply(int multiplicand, int multiplier) {" 
                        + " System.out.println(multiplicand);" 
                        + " System.out.println(multiplier);" 
                        + " return multiplicand * multiplier;" 
                    + " }" 
                 + "}"); 

        JavaFileObject file = so; 

        List<JavaFileObject> files = Arrays.asList(file); 
        File dirpath=new File(System.getProperty ("user.dir") + "/src/main/java/"+"/org/framewoke/core/compiler/"+"CalculatorTest.class");
        FileWriter fileWriter=new FileWriter(dirpath);
        JavaCompiler.CompilationTask task = compiler.getTask(fileWriter, fileManager, 
                null,  Arrays.asList("-d","./target/test-classes"), null, files); 
        Boolean result = task.call(); 
        System.out.println(result); 
        if (result) { 
            Class<?> clazz = Class.forName("org.framewoke.core.compiler.CalculatorTest"); 
            Object instance = clazz.newInstance(); 
            Method m = clazz.getMethod("multiply", new Class[] { int.class, 
                    int.class }); 
            Object[] o = new Object[] { 3, 2 }; 
            System.out.println(m.invoke(instance, o)); 
        } 
    } 
} 
