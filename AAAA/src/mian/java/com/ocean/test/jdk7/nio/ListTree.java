package com.ocean.test.jdk7.nio;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;

class ListTree extends SimpleFileVisitor<Path> {  
    @Override  
    public FileVisitResult postVisitDirectory(Path dir, IOException exc) {  
        System.out.println("Visited directory: " + dir.toString());  
        return FileVisitResult.CONTINUE;  
    }  
  
    @Override  
    public FileVisitResult visitFileFailed(Path file, IOException exc) {  
        System.out.println(exc);  
        return FileVisitResult.CONTINUE;  
    }  
}  