package org.framewoke.core.compiler;

import java.io.FileOutputStream;

import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;

public class Compiler2 {
	public static void main(String[] args) throws Exception {
		String fullQuanlifiedFileName = System.getProperty("user.dir")+"/src/test/java/org/framewoke/core/compiler/"+"HelloWorld.java";
		JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
		FileOutputStream err = new FileOutputStream("err.txt",true);
		int compilationResult = compiler.run(null, null, err, " -d   ./src/test/java/org/framewoke/core/compiler ", fullQuanlifiedFileName);

		if(compilationResult == 0) {
			System.out.println("Done");
		} else {
			System.out.println("Fail");
		}
	}
}