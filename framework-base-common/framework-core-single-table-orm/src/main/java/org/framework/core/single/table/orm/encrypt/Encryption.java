/**
 * Copyright (c) 2011-2015, @author ocean(zhangjufang0505@163.com).
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.framework.core.single.table.orm.encrypt;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/**
 * Encryption provides MD5,SHA1,AES,DES,XOR and BASE64 Encryption methods.
 * @author ocean(zhangjufang0505@163.com)
 *
 */
public class Encryption {

	private static final String MD5 = "MD5";
	private static final String SHA1 = "SHA1";
	private static final String HmacMD5 = "HmacMD5";
	private static final String HmacSHA1 = "HmacSHA1";
	private static final String DES = "DES";
	private static final String AES = "AES";
 
    private static final char[] BASE64_CHAR_TABLE = {'A', 'B', 'C', 'D',
        'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q',
        'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd',
        'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q',
        'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '0', '1', '2', '3',
        '4', '5', '6', '7', '8', '9', '+', '/'};
    
    private String charset = "UTF-8";
    private int keysizeDES = 0;
    private int keysizeAES = 128;
    
    private Encryption(){}
 
    public static class instanceHolder {
    	private static final Encryption me = new Encryption();
    }
    
    public static Encryption getInstance(){
        return instanceHolder.me;
    }
    
    public String encrypt(Algorithm algorithm,String res,String key){
    	if(algorithm==Algorithm.MD5){
    		if (key==null) {
				return MD5(res);
			} else {
				return MD5(res, key);
			}
    	}else if (algorithm==Algorithm.SHA1) {
    		if (key==null) {
				return SHA1(res);
			} else {
				return SHA1(res, key);
			}
		}else if (algorithm==Algorithm.DES) {
			return DESencode(res, key);
		}else if (algorithm==Algorithm.AES) {
			return AESencode(res, key);
		}else if (algorithm==Algorithm.XOR) {
			return XORencode(res, key);
		}else if (algorithm==Algorithm.BASE64) {
			try {
				return base64Encode(res.getBytes(charset));
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
				return res;
			}
		}else {
			System.out.println("warning:unsupported encryption algorithm '"+algorithm+"'");
	    	return res;
		}
    	
    }
    
    public String decrypt(Algorithm algorithm,String res,String key){
    	if (algorithm==Algorithm.DES) {
			return DESdecode(res, key);
		}else if (algorithm==Algorithm.AES) {
			return AESdecode(res, key);
		}else if (algorithm==Algorithm.XOR) {
			return XORdecode(res, key);
		}else if (algorithm==Algorithm.BASE64) {
			try {
				return base64Decode(res.getBytes(charset));
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
				return res;
			}
		}else {
			System.out.println("warning:unsupported decryption algorithm '"+algorithm+"'");
	    	return res;
		}
    	
    }
    
    public static String base64Encode(byte[] value) {
        StringBuilder sb = new StringBuilder();
        //获取编码字节是3的倍数;
        int len = value.length;
        int len3 = len / 3;
        //先处理没有加换行符;
        for (int i = 0; i < len3; i++) {

            //得到第一个字符;
            int b1 = (value[i * 3] >> 2) & 0x3F;
            char c1 = BASE64_CHAR_TABLE[b1];
            sb.append(c1);

            //得到第二个字符;
            int b2 = ((value[i * 3] << 4 & 0x3F) + (value[i * 3 + 1] >> 4)) & 0x3F;
            char c2 = BASE64_CHAR_TABLE[b2];
            sb.append(c2);

            //得到第三个字符;
            int b3 = ((value[i * 3 + 1] << 2 & 0x3C) + (value[i * 3 + 2] >> 6)) & 0x3F;
            char c3 = BASE64_CHAR_TABLE[b3];
            sb.append(c3);

            //得到第四个字符;
            int b4 = value[i * 3 + 2] & 0x3F;
            char c4 = BASE64_CHAR_TABLE[b4];
            sb.append(c4);

        }

        //如果有剩余的字符就补0;
        //剩余的个数;
        int less = len % 3;
        if (less == 1) {//剩余一个字符--补充两个等号;;

            //得到第一个字符;
            int b1 = value[len3 * 3] >> 2 & 0x3F;
            char c1 = BASE64_CHAR_TABLE[b1];
            sb.append(c1);

            //得到第二个字符;
            int b2 = (value[len3 * 3] << 4 & 0x30) & 0x3F;
            char c2 = BASE64_CHAR_TABLE[b2];
            sb.append(c2);
            sb.append("==");

        } else if (less == 2) {//剩余两个字符--补充一个等号;

            //得到第一个字符;
            int b1 = value[len3 * 3] >> 2 & 0x3F;
            char c1 = BASE64_CHAR_TABLE[b1];
            sb.append(c1);

            //得到第二个字符;
            int b2 = ((value[len3 * 3] << 4 & 0x30) + (value[len3 * 3 + 1] >> 4)) & 0x3F;
            char c2 = BASE64_CHAR_TABLE[b2];
            sb.append(c2);

            //得到第三个字符;
            int b3 = (value[len3 * 3 + 1] << 2 & 0x3C) & 0x3F;
            char c3 = BASE64_CHAR_TABLE[b3];
            sb.append(c3);
            sb.append("=");

        }

        return sb.toString();
    }
    
    public static String base64Decode(byte[] value) {

        //每四个一组进行解码;
        int len = value.length;
        int len4 = len / 4;
        StringBuilder sb = new StringBuilder();
        //除去末尾的四个可能特殊的字符;
        int i = 0;
        for (i = 0; i < len4 - 1; i++) {

            //第一个字符;
            byte b1 = (byte) ((char2Index((char) value[i * 4]) << 2) + (char2Index((char) value[i * 4 + 1]) >> 4));
            sb.append((char) b1);
            //第二个字符;
            byte b2 = (byte) ((char2Index((char) value[i * 4 + 1]) << 4)
                    + (char2Index((char) value[i * 4 + 2]) >> 2));
            sb.append((char) b2);
            //第三个字符;
            byte b3 = (byte) ((char2Index((char) value[i * 4 + 2]) << 6) + (char2Index((char) value[i * 4 + 3])));
            sb.append((char) b3);

        }

        //处理最后的四个字符串;
        for (int j = 0; j < 3; j++) {
            int index = i * 4 + j;
            if ((char) value[index + 1] != '=') {

                if (j == 0) {
                    byte b = (byte) ((char2Index((char) value[index]) << 2)
                            + (char2Index((char) value[index + 1]) >> 4));
                    sb.append((char) b);
                } else if (j == 1) {
                    byte b = (byte) ((char2Index((char) value[index]) << 4)
                            + (char2Index((char) value[index + 1]) >> 2));
                    sb.append((char) b);
                } else if (j == 2) {
                    byte b = (byte) ((char2Index((char) value[index]) << 6)
                            + (char2Index((char) value[index + 1])));
                    sb.append((char) b);
                }

            } else {
                break;
            }
        }

        return sb.toString();
    }

    private static int char2Index(char ch) {
        if (ch >= 'A' && ch <= 'Z') {
            return ch - 'A';
        } else if (ch >= 'a' && ch <= 'z') {
            return 26 + ch - 'a';
        } else if (ch >= '0' && ch <= '9') {
            return 52 + ch - '0';
        } else if (ch == '+') {
            return 62;
        } else if (ch == '/') {
            return 63;
        }
        return 0;
    }

    /**使用MessageDigest进行单向加密（无密码）*/
    private String messageDigest(String res,String algorithm){
        try {
            MessageDigest md = MessageDigest.getInstance(algorithm);
            byte[] resBytes = charset==null?res.getBytes():res.getBytes(charset);
            return base64Encode(md.digest(resBytes));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
     
    /**使用KeyGenerator进行单向/双向加密（可设密码）*/
    private String keyGeneratorMac(String res,String algorithm,String key){
        try {
            SecretKey sk = null;
            if (key==null) {
                KeyGenerator kg = KeyGenerator.getInstance(algorithm);
                sk = kg.generateKey();
            }else {
                byte[] keyBytes = charset==null?key.getBytes():key.getBytes(charset);
                sk = new SecretKeySpec(keyBytes, algorithm);
            }
            Mac mac = Mac.getInstance(algorithm);
            mac.init(sk);
            byte[] result = mac.doFinal(res.getBytes());
            return base64Encode(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
 
    /**使用KeyGenerator双向加密，DES/AES，注意这里转化为字符串的时候是将2进制转为16进制格式的字符串，不是直接转，因为会出错*/
    private String keyGeneratorES(String res,String algorithm,String key,int keysize,boolean isEncode){
        try {
            KeyGenerator kg = KeyGenerator.getInstance(algorithm);
            if (keysize == 0) {
                byte[] keyBytes = charset==null?key.getBytes():key.getBytes(charset);
                kg.init(new SecureRandom(keyBytes));
            }else if (key==null) {
                kg.init(keysize);
            }else {
                byte[] keyBytes = charset==null?key.getBytes():key.getBytes(charset);
                kg.init(keysize, new SecureRandom(keyBytes));
            }
            SecretKey sk = kg.generateKey();
            SecretKeySpec sks = new SecretKeySpec(sk.getEncoded(), algorithm);
            Cipher cipher = Cipher.getInstance(algorithm);
            if (isEncode) {
                cipher.init(Cipher.ENCRYPT_MODE, sks);
                byte[] resBytes = charset==null?res.getBytes():res.getBytes(charset);
                return parseByte2HexStr(cipher.doFinal(resBytes));
            }else {
                cipher.init(Cipher.DECRYPT_MODE, sks);
                return new String(cipher.doFinal(parseHexStr2Byte(res)));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
     
    /**将二进制转换成16进制 */
    public static String parseByte2HexStr(byte buf[]) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < buf.length; i++) {
            String hex = Integer.toHexString(buf[i] & 0xFF);  
            if (hex.length() == 1) {
                hex = '0' + hex;  
            }
            sb.append(hex.toUpperCase());  
        }
        return sb.toString();  
    }
    /**将16进制转换为二进制*/
    public static byte[] parseHexStr2Byte(String hexStr) {
        if (hexStr.length() < 1)
            return null;
        byte[] result = new byte[hexStr.length()/2];
        for (int i = 0;i< hexStr.length()/2; i++) {
            int high = Integer.parseInt(hexStr.substring(i*2, i*2+1), 16);  
            int low = Integer.parseInt(hexStr.substring(i*2+1, i*2+2), 16);  
            result[i] = (byte) (high * 16 + low);  
        }
        return result;  
    }
 
    public String MD5(String res) {
        return messageDigest(res, MD5);
    }
 
    public String MD5(String res, String key) {
        return keyGeneratorMac(res, HmacMD5, key);
    }
 
    public String SHA1(String res) {
        return messageDigest(res, SHA1);
    }
 
    public String SHA1(String res, String key) {
        return keyGeneratorMac(res, HmacSHA1, key);
    }
 
    public String DESencode(String res, String key) {
        return keyGeneratorES(res, DES, key, keysizeDES, true);
    }
 
    public String DESdecode(String res, String key) {
        return keyGeneratorES(res, DES, key, keysizeDES, false);
    }
 
    public String AESencode(String res, String key) {
        return keyGeneratorES(res, AES, key, keysizeAES, true);
    }
 
    public String AESdecode(String res, String key) {
        return keyGeneratorES(res, AES, key, keysizeAES, false);
    }
 
    public String XORencode(String res, String key) {
        byte[] bs = res.getBytes();
        for (int i = 0; i < bs.length; i++) {
            bs[i] = (byte) ((bs[i]) ^ key.hashCode());
        }
        return parseByte2HexStr(bs);
    }
 
    public String XORdecode(String res, String key) {
        byte[] bs = parseHexStr2Byte(res);
        for (int i = 0; i < bs.length; i++) {
            bs[i] = (byte) ((bs[i]) ^ key.hashCode());
        }
        return new String(bs);
    }
 
    public int XOR(int res, String key) {
        return res ^ key.hashCode();
    }

	public String getCharset() {
		return charset;
	}

	public void setCharset(String charset) {
		this.charset = charset;
	}

	public int getKeysizeDES() {
		return keysizeDES;
	}

	public void setKeysizeDES(int keysizeDES) {
		this.keysizeDES = keysizeDES;
	}

	public int getKeysizeAES() {
		return keysizeAES;
	}

	public void setKeysizeAES(int keysizeAES) {
		this.keysizeAES = keysizeAES;
	}
     
}
