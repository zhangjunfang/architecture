package com.ocean.rpc.util;

/**
 * 
 * @author： ocean 创建时间：2015年12月18日 mail：zhangjunfang0505@163.com 描述：
 */
public abstract class JdkVersion {
	public final static int JAVA_15 = 5;
	public final static int JAVA_16 = 6;
	public final static int JAVA_17 = 7;
	public final static int JAVA_18 = 8;
	public final static int JAVA_19 = 9;
	public final static String javaVersion;
	public final static int majorJavaVersion;
	static {
		javaVersion = System.getProperty("java.version");
		if (javaVersion.contains("1.9.")) {
			majorJavaVersion = JAVA_19;
		} else if (javaVersion.contains("1.8.")) {
			majorJavaVersion = JAVA_18;
		} else if (javaVersion.contains("1.7.")) {
			majorJavaVersion = JAVA_17;
		} else if (javaVersion.contains("1.6.")) {
			majorJavaVersion = JAVA_16;
		} else {
			majorJavaVersion = JAVA_15;
		}
	}
}