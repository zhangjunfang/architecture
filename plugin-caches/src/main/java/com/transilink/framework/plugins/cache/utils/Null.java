package com.transilink.framework.plugins.cache.utils;

public class Null {
	
	private static Null INSTANCE = new Null();
	
	public static Null getInstance(){
		return INSTANCE;
	}
	
	private Null () {
		
	}
	

	@Override
	public boolean equals(Object obj) {
		if(obj == null){
			return true;
		}
		return false;
	}

	@Override
	public int hashCode() {
		return 26;
	}

	@Override
	public String toString() {
		return "[This is " + getClass().getCanonicalName() + "]";
	}
	
	
}
