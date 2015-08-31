package org.framewoke.core.compiler;

import java.io.IOException;
import java.net.URI;

import javax.tools.SimpleJavaFileObject;
class StringObject extends SimpleJavaFileObject { 
    private String contents = null; 
    
    public StringObject(String className, String contents) throws Exception {          
        super(URI.create( className.replace('.', '/') 
                + Kind.SOURCE.extension), Kind.SOURCE); 
        System.out.println(URI.create(className.replace('.', '/') + Kind.SOURCE.extension));
        this.contents = contents; 
    } 

    public CharSequence getCharContent(boolean ignoreEncodingErrors) 
            throws IOException { 
        return contents; 
    } 
}