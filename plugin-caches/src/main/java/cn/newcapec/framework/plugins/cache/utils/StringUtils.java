package cn.newcapec.framework.plugins.cache.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.sql.Clob;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.Assert;

/**
 * 字符串工�?
 * @author sntey
 */

public abstract class StringUtils{
	protected final static Log log = LogFactory.getLog(StringUtils.class);
	/**ISO-8859-1*/
	public static final String ISO_8859_1="ISO-8859-1";
	/**UTF-8*/
	public static final String UTF_8="UTF-8";

	public abstract class Symbol{
		/** [] */
		public static final String EMPTY = "";
		
		/** [.] 句点 */
		public static final String POINT = ".";
		
		/** [,] 逗号 */
		public static final String COMMA = ",";
		
		/** [/] */
		public static final String SLASH="/";
		
		/** [\] */
		public static final String BACKSLASH="\\";
		
		/** ['] */
		public static final String QM="\'";
		
		/** ["] */
		public static final String DQM="\"";
		
		/** [全角空格] */
		public static final String BLANK_SBC="　";
		
		/** [半角空格] */
		public static final String BLANK_DBC=" ";
	};
	
	

	/**
	 * 判断 参数 sb 是否�? null,""," "
	 * @param sb
	 * @return
	 */
	public static final boolean notText(StringBuffer sb){
		return !hasText(sb);
	}
	
	/**
	 * 是否不为null,"" 或只含有" "(空格)
	 * */
	public static final boolean hasText(StringBuffer sb){
		return (sb != null) && (hasText(sb.toString()));
	}
	
	/**
	 * 是否为null,"" 或只含有" "(空格)
	 * */
	public static final boolean notText(String str){
		return !hasText(str);
	}
	
	/**
	 * <br>字符串是否有非空内容 
	 * <br>StringUtils.hasText(null) -- false
	 * <br>StringUtils.hasText("") -- false
	 * <br>StringUtils.hasText(" ") -- false
	 * <br>StringUtils.hasText("12345") -- true
	 * <br>StringUtils.hasText(" 12345 ") -- true
	 */
	public static final boolean hasText(String str){
		if(str == null || "null".equals(str)) return false;
		String use = str.replace("'", "");
		
		return (use != null && use.trim().length() > 0);
	}
	
	/**
	 * <br>字符串是否有长度 
	 * <br>StringUtils.hasText(null) -- false
	 * <br>StringUtils.hasText("") -- false
	 * <br>StringUtils.hasText(" ") -- true
	 * <br>StringUtils.hasText("12345") -- true
	 * <br>StringUtils.hasText(" 12345 ") -- true
	 */
	public static boolean hasLength(String str) {
		if(str == null) return false;
		return (str != null && str.length() > 0);
	}
	
	
	
	public static boolean hasLength(String[] strs) {
		if(strs == null) return false;
		
		return strs.length>0;
	}
	/**
	 * 计算子串的数�?
	 * @param str string to search in. Return 0 if this is null.
	 * @param sub string to search for. Return 0 if this is null.
	 */
	public static int countSubstring(String str, String sub) {
		return org.springframework.util.StringUtils.countOccurrencesOf(str, sub);
	}
	
	/** 对数组元素进行批量更�? 每个元素 加首尾字符串 */
	public static void bulkUpdate(String[] array, String leading, String trailing){
		if(ArrayUtils.isEmpty(array)){
			return;
		}
		if(leading==null){	leading="";};
		if(trailing==null){	trailing="";};
		
		for(int i=0;i<array.length;i++){
			array[i]= leading + (array[i]==null?"":array[i]) + trailing;
		}
	}

	/** 根据正则表达式分割指定字符串 (正则表达式不能为空串) 字符串为空则返回 String[0]*/
	public static String[] split(String str, String regex){
		Assert.hasText(regex);
		if(StringUtils.notText(str)){
			return new String[0];
		}
		return str.split(regex);
	}
	
	public static String[] trimAll(String[] array){
		if(array==null){	return array;}
		String[] result = new String[array.length];
		for(int i=0;i<array.length;i++){
			result[i]=array[i]==null?null:array[i].trim();
		}
		return result;
	}
	
	/**
	 * 数组转化成字符串,
	 * 使用默认的连接符�?
	 * */
	public static <T> String arrayToString(T[] array) {
		return arrayToString(array, ",", "", "");
	}
	
	/**
	 * 数组转化成字符串,
	 * 使用自定义的连接符号
	 * */
	public static <T> String arrayToString(T[] array,String connectSymbol) {
		return arrayToString(array, connectSymbol, "", "");
	}
	
	/**
	 * 数组转化成字符串,
	 * 使用逗号连接[,]
	 * 并加上自定义的首 尾标�?
	 * @param leading 首标�?
	 * @param trailing 尾标�?
	 * */
	public static <T> String arrayToString(T[] array,String leading,String trailing){
		return arrayToString(array, ",", leading, trailing);
	}
	
	/**
	 * 数组转化成字符串,
	 * 使用自定义的连接符号
	 * 并加上自定义的首 尾标�?
	 * @param connectSymbol 连接符号
	 * @param leading 首标�?
	 * @param trailing 尾标�?
	 * */
	public static <T> String arrayToString(T[] array,String connectSymbol,String leading,String trailing) {
		connectSymbol = (connectSymbol == null?"":connectSymbol);
		leading = (leading == null?"":leading);
		trailing = (trailing == null?"":trailing);
		int len = array.length;
		if ( len == 0 ) return "";
		StringBuffer buf = new StringBuffer( len * 12 + leading.length() + trailing.length());
		for ( int i = 0; i < len - 1; i++ ) {
			buf.append(leading).append( array[i].toString() ).append(trailing)
					.append(connectSymbol);
		}
		return buf.append(leading).append( array[len - 1].toString() ).append(trailing).toString();
	}

	
	/**
	 * 字符串编码转�? 使用默认的编�? {@link #DEFAULT_CODING}
	 * @see #convertStrByCoding(String, String)
	 * 
	 * @param str �?要转换的字符�?
	 * @return 
	 */
	public static String convertStrByCoding(String str){
		return convertStrByCoding(str,ISO_8859_1);
	}
	
	/**
	 * 字符串编码转�? 使用自定义编�?
	 * @param str    �?要转换的字符�?
	 * @param coding 编码
	 * @return
	 */
	public static String convertStrByCoding(String str, String coding) {
		try {
			return new String(str.getBytes(coding));
		} catch (Exception e) {
			log.error("string coding is error!", e);
			return null;
		}
	}
	
	/**
	 * 将str由code1转换为code2
	 * 如果str为空，则返回"";
	 * **/
	public static String converStrByCoding(String str,String code1, String code2){
		
		try {
			if(StringUtils.notText(str)){
				return "";
			}
			return new String(str.getBytes(code1),code2);
		} catch (UnsupportedEncodingException e) {
			return "";
		}
	}
	
	public static boolean nullSafeEquals(String s1, String s2) {
		return ObjectUtils.nullSafeEquals(s1, s2);
	}
	
	/**
	 * 字符串字串替�?
	 */
	public static String replace(String inString, String oldPattern, String newPattern) {
		if (inString == null) {
			return null;
		}
		if (oldPattern == null || newPattern == null) {
			return inString;
		}

		StringBuffer sbuf = new StringBuffer();
		// output StringBuffer we'll build up
		int pos = 0; // our position in the old string
		int index = inString.indexOf(oldPattern);
		// the index of an occurrence we've found, or -1
		int patLen = oldPattern.length();
		while (index >= 0) {
			sbuf.append(inString.substring(pos, index));
			sbuf.append(newPattern);
			pos = index + patLen;
			index = inString.indexOf(oldPattern, pos);
		}
		sbuf.append(inString.substring(pos));

		// remember to append any characters to the right of a match
		return sbuf.toString();
	}

	public static String collectionToString(Collection<?> coll, String connectSymbol) {
		return collectionToString(coll,connectSymbol,"","");
	}
	
	public static String collectionToString(Collection<?> coll, String connectSymbol,String leading,String trailing) {
		return arrayToString(coll.toArray(), connectSymbol, leading, trailing);
	}
	
	public static List<String> collectionToStringList(Collection<?> coll){
		if(coll == null || coll.size() == 0){	return new ArrayList<String>(0);}
		List<String> result = new ArrayList<String>(coll.size()>10000?10000:coll.size());
		
		for(Object o:coll){
			if(o instanceof String
					|| o instanceof Number){
				result.add(o.toString());
			}else if(o instanceof Date){
				result.add(DateUtils.format((Date)o));
			}
		}
		return result;
	}

	/**
	 * 在数组的指定位置插入指定个数的元�?
	 * */
	public static String[] addToArray(String[] array, String...strs) {
		return addToArray(array, 0, strs);
	}
	
	/**
	 * 在数组的指定位置插入指定个数的元�?,
	 * <br>将当前处于该位置的元素（如果有的话）和所有后续元素向右移动（在其索引中加指定个数).
	 * @param array 源数�?
	 * @param index 插入位置
	 * @param strs 插入内容 支持变参
	 * @return 新数�? 
	 * */
	public static String[] addToArray(String[] array, int index, String...strs) {
		if(strs == null || strs.length == 0){	return array;}
		
		if(index<0 || index>array.length){	
			throw new IndexOutOfBoundsException(
				"索引:["+index+"]小于0 或�?? 大于数组长度: ["+array.length+"]"); 
		}
		
		String[] result = new String[array.length + strs.length];
		
		System.arraycopy(array, 	0, result,					 0,                index);
		System.arraycopy(strs, 		0, result, 				 index, 		 strs.length);
		System.arraycopy(array, index, result, index + strs.length, array.length - index);
		
		return result;
	}
	
	/**
	 * 按次数输�?
	 * */
	public static String outByTimes(String s, int times) {
		if(s==null){	return s;}
		
		StringBuilder builder = new StringBuilder(s.length()*times>2000?2000:s.length()*times);
		for(int i=0;i<times;i++){
			builder.append(s);
		}
		return builder.toString();
	}
	
	public static String removeChars(String s,char...chars){
		if(s==null || chars==null){	return s;}
		String temp = new String(s);
		for(char c:chars){
			temp = StringUtils.replace(temp, String.valueOf(c), "");
		}
		return temp;
	}
	
	/**
	 * �? s 转换�? UTF-8 格式并返�?.
	 * @param s
	 * @return
	 */
	public static String toUtf8(String s) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);
			if (c >= 0 && c <= 255) {
				sb.append(c);
			} else {
				byte[] b;
				try {
					b = Character.toString(c).getBytes("utf-8");
				} catch (Exception ex) {
					b = new byte[0];
				}
				for (int j = 0; j < b.length; j++) {
					int k = b[j];
					if (k < 0)
						k += 256;
					sb.append("%" + Integer.toHexString(k).toUpperCase());
				}
			}
		}

		return sb.toString();
	}
	
	/**
	 * 获得拼音�?�?
	 */
	public static String getPYSimpleName(String a){
		if(!StringUtils.hasText(a)){		return "";}
		char[] chars=a.toCharArray();
		StringBuffer strBuffer=new StringBuffer(chars.length);
		for(int i=0;i<chars.length;i++)
		{
			strBuffer.append(getPYHeadLetter(chars[i]));
		}
		return strBuffer.toString();
	}
	
	/**
	 * 获得汉字拼音的首字母,如果参数不符号要�?
	 * 则返回自�?(单字�?) or ' '(无法解析的双字符) 
	 * */
	public static char getPYHeadLetter(char aChar)
	{
		char result;
//		boolean flag = true;
		int charAscii=getCharAscii(aChar);
		if	   (0<=charAscii && charAscii<=255)		  result=aChar;		//Ascii 小于 256 返回自身
		else if(charAscii>=45217 && charAscii<=45252) result= 'A';
		else if(charAscii>=45253 && charAscii<=45760) result= 'B';
		else if(charAscii>=45761 && charAscii<=46317) result= 'C';
		else if(charAscii>=46318 && charAscii<=46825) result= 'D';
		else if(charAscii>=46826 && charAscii<=47009) result= 'E';
		else if(charAscii>=47010 && charAscii<=47296) result= 'F';
		else if(charAscii>=47297 && charAscii<=47613) result= 'G';
		else if(charAscii>=47614 && charAscii<=48118) result= 'H';
		else if(charAscii>=48119 && charAscii<=49061) result= 'J';
		else if(charAscii>=49062 && charAscii<=49323) result= 'K';
		else if(charAscii>=49324 && charAscii<=49895) result= 'L';
		else if(charAscii>=49896 && charAscii<=50370) result= 'M';
		else if(charAscii>=50371 && charAscii<=50613) result= 'N';
		else if(charAscii>=50614 && charAscii<=50621) result= 'O';
		else if(charAscii>=50622 && charAscii<=50905) result= 'P';
		else if(charAscii>=50906 && charAscii<=51386) result= 'Q';
		else if(charAscii>=51387 && charAscii<=51445) result= 'R';
		else if(charAscii>=51446 && charAscii<=52217) result= 'S';
		else if(charAscii>=52218 && charAscii<=52697) result= 'T';
		else if(charAscii>=52698 && charAscii<=52979) result= 'W';
		else if(charAscii>=52980 && charAscii<=53640) result= 'X';
		else if(charAscii>=53689 && charAscii<=54480) result= 'Y';
		else if(charAscii>=54481 && charAscii<=62289) result= 'Z';
		else {
			log.warn("无法解析字母["+aChar+"]Ascii["+charAscii+"]");
			result=' ';
//			flag = false;
		}
//		if(flag){
//			log.info("字母["+aChar+"]Ascii["+charAscii+"]");
//		}
		
		//返回小写
		return Character.toLowerCase(result);
	}
	
	
	public static String parse(Double d){

		return parse(d, 0);
	}
	public static String parse(Object o , int m){
		if(m<0) throw new RuntimeException("格式不对");
		
		
		String formatString = "0";
		if(m>0){
			formatString+=".";
		}
		for(int i=1; i<=m; i++){
			formatString +="0";
		}
		DecimalFormat df = new DecimalFormat(formatString);
		
		return df.format(o);
		
	}
	/**
	 * 获得单个字符的Ascii.
	 * 
	 * @param char
	 *            汉字字符
	 * @return int 错误返回 0,否则返回ascii
	 */
	public static int getCharAscii(char aChar) {
		byte[] bytes = (String.valueOf(aChar)).getBytes();
		if (bytes == null || bytes.length > 2 || bytes.length <= 0) {
			return 0;
		}
		
		if (bytes.length == 1) {
			return bytes[0];
		}
		else if (bytes.length == 2) {
			int hightByte = 256 + bytes[0];
			int lowByte = 256 + bytes[1];
			int ascii = (256 * hightByte + lowByte);
			return ascii;
		}else{
			log.warn("无法获得字符["+aChar+"]的Ascii编码");
			return 0;
		}
	}

	public static String trimText(String text){
		if(hasText(text)){
			return text.trim();
		}
		return "";
	}
	public static String ClobToString(Clob clob) {

        String reString = "";
        Reader is = null;
        BufferedReader br = null;
        try {
	        is = clob.getCharacterStream();// 得到流
	        br = new BufferedReader(is);
	        String s = br.readLine();
	        StringBuffer sb = new StringBuffer();
	        while (s != null) {// 执行循环将字符串全部取出付值给StringBuffer由StringBuffer转成STRING
	            sb.append(s);
	            s = br.readLine();
	        }
	        reString = sb.toString();
        } catch (Exception e) {
			log.info("clob转换过程中出错");
		}finally{
			try {
				if(br!=null){
					br.close();
				}
				if(is!=null){
					is.close();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
        return reString;
    }
}
