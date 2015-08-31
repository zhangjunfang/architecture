package org.framewoke.core.compiler.one;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;

public class DynamicCompileTest1 {
	public static void main(String[] args) throws IOException {
		// 编译程序
		JavaCompiler javaCompiler = ToolProvider.getSystemJavaCompiler();
		int result = javaCompiler.run(null, null, null, "-d",
				"./target/test-classes", System.getProperty("user.dir")
						+ "/src/test/java"
						+ "/org/framewoke/core/compiler/one/Hello.java");
		System.out.println(result == 0 ? "恭喜编译成功" : "对不起编译失败");

		// 运行程序
		Runtime run = Runtime.getRuntime();
		Process process = run
				.exec("java -cp ./target/test-classes org.framewoke.core.compiler.one/Hello");
		InputStream in = process.getInputStream();
		BufferedReader reader = new BufferedReader(new InputStreamReader(in));
		String info = "";
		while ((info = reader.readLine()) != null) {
			System.out.println(info);
		}
	}
}
