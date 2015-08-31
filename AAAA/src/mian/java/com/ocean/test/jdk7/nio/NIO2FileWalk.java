package com.ocean.test.jdk7.nio;

import java.io.IOException;
import java.nio.file.FileVisitOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.EnumSet;

public class NIO2FileWalk {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Path listDir = Paths.get("/tmp"); // define the starting file
		//
		ListTree walk = new ListTree();
		try {
			Files.walkFileTree(listDir, walk);
		} catch (IOException e) {
			System.err.println(e);
		}

		//遍历的时候跟踪链接
		EnumSet<FileVisitOption> opts = EnumSet.of(FileVisitOption.FOLLOW_LINKS); 
		try {
			Files.walkFileTree(listDir, opts, Integer.MAX_VALUE, walk); 
		} catch (IOException e) {
			System.err.println(e);
		}
		
		//FileVisitor 提供perform a file search, a recursive copy, arecursive move, and a recursive delete.

	}

}