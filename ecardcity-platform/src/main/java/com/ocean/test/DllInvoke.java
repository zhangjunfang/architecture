/**
 *
 */
package com.ocean.test;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.xvolks.jnative.JNative;
import org.xvolks.jnative.Type;
import org.xvolks.jnative.exceptions.NativeException;
import org.xvolks.jnative.pointers.Pointer;
import org.xvolks.jnative.pointers.memory.MemoryBlockFactory;


/**
*
* @Description :
* @author : ocean
* @date : 2014-5-13 上午10:33:15
* @email : zhangjunfang0505@163.com
* @Copyright : newcapec zhengzhou
*/
public class DllInvoke implements Serializable {

	private static final long serialVersionUID = -5591460604929069753L;
	private static final boolean isWindows = System.getProperty("os.name")
			.toLowerCase().indexOf("windows") != -1;
	// private static final boolean isLinux = System.getProperty("os.name")
	// .toLowerCase().indexOf("linux") != -1;
	public static final String DLL_NAME = isWindows ? "SoftGrant.dll"
			: "SoftGrant.so";
	public static String rtfPath = "";
	static {
		String path = Thread.currentThread().getContextClassLoader()
				.getResource("SoftGrant.dll").toString()
				.replaceFirst("file:/", "");
		rtfPath = Thread.currentThread().getContextClassLoader()
				.getResource("20140402.grf").toString()
				.replaceFirst("file:/", "");
		// System.out.println(rtfPath);
		// System.out.println(
		// Thread.currentThread().getContextClassLoader().getResource("SoftGrant.dll").toString());
		System.load(path);
	}

	/**
	 *
	 * @throws UnsatisfiedLinkError
	 */

	public static void loadFromJar() throws UnsatisfiedLinkError {
		File tempDir = new File(System.getProperty("user.dir"));
		File dllFile = new File(tempDir, DLL_NAME);

		InputStream in = JNative.class.getResourceAsStream("/lib-bin/"
				+ DLL_NAME);
		if (in == null) {
			if (!dllFile.exists()) {
				throw new UnsatisfiedLinkError(DLL_NAME
						+ " : unable to find in " + tempDir);
			}
		} else {
			if ((dllFile.exists()) && (dllFile.canWrite())) {
				dllFile.delete();
			}
			if (!dllFile.exists()) {
				byte[] buffer = new byte[512];
				BufferedOutputStream out = null;
				try {
					try {
						out = new BufferedOutputStream(new FileOutputStream(
								dllFile));
						while (true) {
							int readed = in.read(buffer);
							if (readed <= -1)
								break;
							out.write(buffer, 0, readed);
						}

					} finally {
						if (out != null) {
							out.close();
						}
					}
				} catch (IOException e) {
					throw new UnsatisfiedLinkError("Can't write library in "
							+ dllFile);
				}
			}
			System.load(dllFile.toString());
		}
	}

	/**
	 * 获取特征码 map中存放key为code是成功失败标识 0是成功 ，其他为失败 key为result 获取特征码
	 *
	 */
	// 此方法测试成功 ！！！！
	public static Map<String, Object> getCharacterCode()
			throws NativeException, IllegalAccessException {
		JNative jnative = new JNative("SoftGrant", "GetCharacterCode");
		Pointer pTar = new Pointer(
				MemoryBlockFactory.createMemoryBlock(4 * 128));
		jnative.setRetVal(Type.INT);
		jnative.setParameter(0, pTar);
		jnative.invoke();
		Map<String, Object> map = new HashMap<String, Object>(2);
		map.put("code", jnative.getRetVal());
		map.put("result", pTar.getAsString());
		pTar.dispose();
		return map;
	}

	// 获得文件中包含的授权个数
	// int __stdcall GetGrantCount(const char* characterCode, const char*
	// FileName, int& errcode);
	// 测试通过 正确！！！！！！
	public static Map<String, Object> getGrantCount(String file,
			String characterCode) throws NativeException,
			IllegalAccessException {
		int i = 0;
		JNative jnative = new JNative("SoftGrant", "GetGrantCount");
		jnative.setRetVal(Type.INT);

		Pointer characterCodePointer = Pointer
				.createPointerFromString(characterCode);
		jnative.setParameter(i++, characterCodePointer);

		Pointer fileName = Pointer.createPointerFromString(file);
		jnative.setParameter(i++, fileName);

		Pointer errcode = Pointer.createPointer(4 * 4);
		jnative.setParameter(i++, errcode);

		jnative.invoke();
		Map<String, Object> map = new HashMap<String, Object>(8);
		map.put("errcode", errcode.getAsString());
		map.put("code", jnative.getRetVal());
		characterCodePointer.dispose();
		fileName.dispose();
		errcode.dispose();
		return map;
	}

	// 验证授权信息
	// int __stdcall VerifyGrantInfo(const char* characterCode, const char*
	// dbGrantInfo);

	public static Map<String, Object> verifyGrantInfo(String characterCode,
			String dbGrantInfo) throws NativeException, IllegalAccessException {
		JNative jnative = new JNative("SoftGrant", "VerifyGrantInfo");
		jnative.setRetVal(Type.INT);
		int i = 0;
		Pointer characterCodePointer = Pointer
				.createPointerFromString(characterCode);
		jnative.setParameter(i++, characterCodePointer);

		Pointer dbGrantInfopPointer = Pointer
				.createPointerFromString(dbGrantInfo);
		jnative.setParameter(i++, dbGrantInfopPointer);
		jnative.invoke();
		Map<String, Object> map = new HashMap<String, Object>(8);
		map.put("code", jnative.getRetVal());
		characterCodePointer.dispose();
		dbGrantInfopPointer.dispose();
		return map;
	}

	// 验证授权信息，并返回详细的授权信息
	// int __stdcall VerifyGrantInfo1(const char* characterCode, const char*
	// pdbGrantInfo, PST_CUSTGRANTINFO pSt_cust);
	public static Map<String, Object> verifyGrantInfo1(String characterCode,
			String pdbGrantInfo, CustGrantInfo custGrantInfo)
			throws NativeException, IllegalAccessException {
		JNative jnative = new JNative("SoftGrant", "VerifyGrantInfo1");
		jnative.setRetVal(Type.INT);
		int i = 0;
		Pointer characterCodePointer = Pointer
				.createPointerFromString(characterCode);
		jnative.setParameter(i++, characterCodePointer);

		Pointer dbGrantInfopPointer = Pointer
				.createPointerFromString(pdbGrantInfo);
		jnative.setParameter(i++, dbGrantInfopPointer);

		jnative.setParameter(i++, custGrantInfo.getPointer());

		jnative.invoke();
		Map<String, Object> map = new HashMap<String, Object>(8);
		map.put("code", jnative.getRetVal());
		map.put("pSt_cust", custGrantInfo);
		characterCodePointer.dispose();
		dbGrantInfopPointer.dispose();
		return map;
	}

	public static Map<String, String> getAsStringSerial(int offset,
			int byteSize, Pointer pointer) throws NativeException,
			UnsupportedEncodingException {
		byte[] bb = pointer.getMemory();
		byte[] bs = new byte[byteSize];
		int byteSizetemp = 0;
		int length = bb.length;
		for (int j = offset; j < length; j++) {
			if (bb[j] == 0) {
				offset = j;
				break;
			} else {
				bs[byteSizetemp] = bb[j];
			}
			byteSizetemp++;
		}
		Map<String, String> map = new HashMap<String, String>(2);
		map.put("offset", offset + "");
		map.put("serial", new String(bs, 0, byteSizetemp, "gbk"));
		return map;
	}

	public static int getIntPointerSize(Class<Integer> intClass) {

		return 4;
	}

	public static int getBytePointerSize(Class<Byte> byteClass) {

		return 1;
	}

	public static int getShortPointerSize(Class<Short> shortClass) {

		return 2;
	}

	public static int getFloatPointerSize(Class<Short> shortClass) {

		return 4;
	}

	public static int getDoublePointerSize(Class<Short> shortClass) {

		return 8;
	}

	// 解析授权文件，得到所有的授权信息
	// int __stdcall ParseGrantFile(const char* characterCode, const char*
	// FileName,PST_CUSTGRANTINFO st_cust,int* count);

	public static Map<String, Object> parseGrantFile(String file,
			String characterCode) throws NativeException,
			IllegalAccessException, UnsupportedEncodingException {
		JNative jnative = new JNative("SoftGrant", "ParseGrantFile");
		jnative.setRetVal(Type.INT);
		int i = 0;

		Pointer characterCodePointer = Pointer
				.createPointerFromString(characterCode);
		jnative.setParameter(i++, characterCodePointer);

		Pointer fileName = Pointer.createPointerFromString(file);
		jnative.setParameter(i++, fileName);
		CustGrantInfo custGrantInfo = new CustGrantInfo();
		jnative.setParameter(i++, custGrantInfo.getPointer());

		Pointer count = new Pointer(MemoryBlockFactory.createMemoryBlock(4));
		count.setIntAt(0, 1);
		jnative.setParameter(i++, count);

		jnative.invoke();

		Map<String, Object> map = new HashMap<String, Object>(8);
		map.put("custGrantInfo2", custGrantInfo.getValueFromPointer());
		map.put("count", count.getAsInt(0) + "");
		map.put("code", jnative.getRetVal());

		characterCodePointer.dispose();
		fileName.dispose();
		custGrantInfo.getPointer().dispose();
		count.dispose();
		return map;
	}

	public static Map<String, Object> parseGrantFile02(String file,
			String characterCode) throws NativeException,
			IllegalAccessException, UnsupportedEncodingException {
		JNative jnative = new JNative("SoftGrant", "ParseGrantFile");
		jnative.setRetVal(Type.INT);
		int i = 0;

		Pointer characterCodePointer = Pointer
				.createPointerFromString(characterCode);
		jnative.setParameter(i++, characterCodePointer);

		Pointer fileName = Pointer.createPointerFromString(file);
		jnative.setParameter(i++, fileName);

		Pointer st_cust = new Pointer(
				MemoryBlockFactory.createMemoryBlock(2564));
		jnative.setParameter(i++, st_cust);

		Pointer count = new Pointer(MemoryBlockFactory.createMemoryBlock(4));
		count.setIntAt(0, 1);
		jnative.setParameter(i++, count);

		jnative.invoke();

		//byte[] bb = st_cust.getMemory();
		testDLL(st_cust);
		CustGrantInfo2 custGrantInfo2 = new CustGrantInfo2();
		int offset = 0;
		Class<Integer> intClass = int.class;
		Map<String, String> result = getAsStringSerial(offset, 65, st_cust);
		custGrantInfo2.setCustomerName(result.get("serial"));

		// int 数据偏移量开始位置
		int pointerOffset = Integer.parseInt(result.get("offset")) + 1;
		// 获取int数据值
		int grantSerialNO = st_cust.getAsInt(pointerOffset);
		// 给结构体赋值
		custGrantInfo2.setGrantSerialNO(grantSerialNO);
		// 计算下一个数据偏移量
		offset = pointerOffset + getIntPointerSize(intClass);

		result = getAsStringSerial(offset, 7, st_cust);
		custGrantInfo2.setCustomerCode(result.get("serial"));
		pointerOffset = Integer.parseInt(result.get("offset"));
		// 字符串遇到数字0标示字符串结束，所以需要加上数字 1
		offset = pointerOffset + 1;
		result = getAsStringSerial(offset, 13, st_cust);
		custGrantInfo2.setGrantDt(result.get("serial"));
		pointerOffset = Integer.parseInt(result.get("offset"));
		// 字符串遇到数字0标示字符串结束，所以需要加上数字 1
		offset = pointerOffset + 1;
		result = getAsStringSerial(offset, 2, st_cust);

		custGrantInfo2.setPwdVer(result.get("serial"));
		pointerOffset = Integer.parseInt(result.get("offset"));
		// 下一个数据的偏移量
		offset = pointerOffset + 1;
		// market
		result = getAsStringSerial(offset, 5, st_cust);
		custGrantInfo2.setMarket(result.get("serial"));
		pointerOffset = Integer.parseInt(result.get("offset"));

		// 下一个数据的偏移量
		offset = pointerOffset + 1;
		// grantRights
		result = getAsStringSerial(offset, 7, st_cust);
		custGrantInfo2.setGrantRights(result.get("serial"));
		pointerOffset = Integer.parseInt(result.get("offset"));

		// 下一个数据的偏移量
		offset = pointerOffset + 1;
		// sysID
		int sysID = st_cust.getAsInt(offset);
		custGrantInfo2.setSysID(sysID);
		// 下一个数据的偏移量
		offset = offset + getIntPointerSize(intClass);
		// grantPwd
		result = getAsStringSerial(offset, 33, st_cust);
		custGrantInfo2.setGrantPwd(result.get("serial"));
		pointerOffset = Integer.parseInt(result.get("offset"));
		// 下一个数据的偏移量
		offset = pointerOffset + 1;
		// status
		custGrantInfo2.setStatus(st_cust.getAsByte(offset));

		// 下一个数据的偏移量
		offset = offset + 1;
		// invalidDt
		result = getAsStringSerial(offset, 9, st_cust);
		custGrantInfo2.setInvalidDt(result.get("serial"));
		pointerOffset = Integer.parseInt(result.get("offset"));
		// 下一个数据的偏移量
		offset = pointerOffset + 1;
		// SysRegCode
		result = getAsStringSerial(offset, 33, st_cust);
		custGrantInfo2.setSysRegCode(result.get("serial"));
		pointerOffset = Integer.parseInt(result.get("offset"));

		// 下一个数据的偏移量
		offset = pointerOffset + 1;
		// GrantInfoVer
		result = getAsStringSerial(offset, 7, st_cust);
		custGrantInfo2.setGrantInfoVer(result.get("serial"));
		pointerOffset = Integer.parseInt(result.get("offset"));

		// 下一个数据的偏移量
		offset = pointerOffset + 1;
		// SubSystemType
		result = getAsStringSerial(offset, 7, st_cust);
		custGrantInfo2.setSubSystemType(result.get("serial"));
		pointerOffset = Integer.parseInt(result.get("offset"));

		// 下一个数据的偏移量
		offset = pointerOffset + 1;
		// GrantCode
		result = getAsStringSerial(offset, 129, st_cust);
		custGrantInfo2.setGrantCode(result.get("serial"));
		pointerOffset = Integer.parseInt(result.get("offset"));

		// 下一个数据的偏移量
		offset = pointerOffset + 1;
		// ClientSysCode
		result = getAsStringSerial16(offset, 33, st_cust);
		custGrantInfo2.setClientSysCode(result.get("serial"));
		pointerOffset = Integer.parseInt(result.get("offset"));

		// 下一个数据的偏移量
		offset = pointerOffset + 1;
		// SystemGrantNumber
		result = getAsStringSerial(offset, 9, st_cust);
		custGrantInfo2.setSystemGrantNumber(result.get("serial"));
		pointerOffset = Integer.parseInt(result.get("offset"));

		// 下一个数据的偏移量
		offset = pointerOffset + 1;
		// Custbh
		result = getAsStringSerial(offset, 17, st_cust);
		custGrantInfo2.setCustbh(result.get("serial"));
		pointerOffset = Integer.parseInt(result.get("offset"));
		// 下一个数据的偏移量
		offset = pointerOffset + 1;
		// CustCountLimited
		custGrantInfo2.setCustCountLimited(st_cust.getAsInt(offset));

		// 下一个数据的偏移量
		offset = offset + getIntPointerSize(intClass);
		// CustReleaseCardLimited
		custGrantInfo2.setCustReleaseCardLimited(st_cust.getAsInt(offset));

		// 下一个数据的偏移量
		offset = offset + getIntPointerSize(intClass);
		// CustRepairCardLimited
		custGrantInfo2.setCustRepairCardLimited(st_cust.getAsInt(offset));

		// 下一个数据的偏移量
		offset = offset + getIntPointerSize(intClass);
		// SoftSetUpType
		result = getAsStringSerial(offset, 3, st_cust);
		custGrantInfo2.setSoftSetUpType(result.get("serial"));
		pointerOffset = Integer.parseInt(result.get("offset"));

		// 下一个数据的偏移量
		offset = pointerOffset + 1;
		// RandomNumber
		result = getAsStringSerial(offset, 9, st_cust);
		custGrantInfo2.setRandomNumber(result.get("serial"));
		pointerOffset = Integer.parseInt(result.get("offset"));

		// 下一个数据的偏移量
		offset = pointerOffset + 1;
		// CustGrantKey
		result = getAsStringSerial(offset, 49, st_cust);
		custGrantInfo2.setCustGrantKey(result.get("serial"));
		pointerOffset = Integer.parseInt(result.get("offset"));

		// 下一个数据的偏移量
		offset = pointerOffset + 1;
		// CustM1CardKey
		result = getAsStringSerial(offset, 49, st_cust);
		custGrantInfo2.setCustM1CardKey(result.get("serial"));
		pointerOffset = Integer.parseInt(result.get("offset"));

		// 下一个数据的偏移量
		offset = pointerOffset + 1;
		// ALlGrantInfo
		result = getAsStringSerial(offset, 2049, st_cust);
		custGrantInfo2.setALlGrantInfo(result.get("serial"));
		pointerOffset = Integer.parseInt(result.get("offset"));

		Map<String, Object> map = new HashMap<String, Object>(8);
		map.put("custGrantInfo2", custGrantInfo2);
		map.put("count", count.getAsInt(0) + "");
		map.put("code", jnative.getRetVal());

		characterCodePointer.dispose();
		fileName.dispose();
		st_cust.dispose();
		count.dispose();
		return map;
	}

	/**
	 * @param pointer
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private static void testDLL(Pointer pointer) {
		// try {
		// byte[] b = new byte[1024];
		// int i=0;
		// for (; i < pointer.getSize(); i++) {
		//
		// if (pointer.getAsByte(i) != 0) {
		// b[i] = pointer.getAsByte(i);
		//
		// } else {
		// break;
		// }
		// }
		//
		// char[] dest = new char[i];
		// for(int k=0;k<dest.length;k++)
		// {
		// dest[k]=(char)pointer.getAsByte(k);
		// System.out.println(b[k]+"============"+dest[k]);
		// }
		// System.out.println(i);
		// String st = new String(dest);
		// System.out.println(st+"=======getAsInt 1========"+pointer.getAsInt(i+1));
		// i=i+1+4;
		// b = new byte[1024];
		// System.out.println("============================");
		// for (; i < pointer.getSize(); i++) {
		//
		// if (pointer.getAsByte(i) != 0) {
		// b[i] = pointer.getAsByte(i);
		//
		// } else {
		// break;
		// }
		// }
		//
		// dest = new char[i];
		// for(int k=0;k<dest.length;k++)
		// {
		// dest[k]=(char)pointer.getAsByte(k);
		// System.out.println(b[k]+"============"+dest[k]);
		// }
		// System.out.println(dest.length);
		// System.out.println("222222222222222"+ new String(b,0,i,"utf-8"));
		// } catch (NativeException e) {
		//
		// e.printStackTrace();
		// } catch (UnsupportedEncodingException e) {
		//
		// e.printStackTrace();
		// }
		try {
			List temp = new ArrayList();
			for (int k = 19; k < pointer.getSize(); k++) {
				if (pointer.getAsByte(k) != 0) // C++字符串以0结尾
				{
					temp.add(pointer.getAsByte(k));
				} else {
					break;
				}
			}
			System.out.println(Arrays.deepToString(temp.toArray()));
			char[] dest = new char[temp.size()];
			for (int k = 0; k < temp.size(); k++) {
				dest[k] = (char) pointer.getAsByte(k);
			}
			String st = new String(dest);
			System.out.println(st);
		} catch (NativeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * @param offset
	 * @param byteSize
	 * @param pointer
	 * @return Map<String, String>
	 * @throws NativeException
	 * @throws UnsupportedEncodingException
	 */
	public static Map<String, String> getAsStringSerial16(int offset,
			int byteSize, Pointer pointer) throws NativeException,
			UnsupportedEncodingException {

		byte[] bb = pointer.getMemory();

		byte[] bs = new byte[byteSize];
		int byteSizetemp = 0;
		int length = bb.length;
		for (int j = offset; j < length; j++) {
			byte b = Byte.parseByte(bb[j] + "", 16);
			if (b == 0) {
				offset = j;
				break;
			} else {
				bs[byteSizetemp] = b;
			}
			byteSizetemp++;
		}
		Map<String, String> map = new HashMap<String, String>(2);
		map.put("offset", offset + "");
		map.put("serial", new String(bs, 0, byteSizetemp, "gbk"));
		return map;
	}

	public static void main(String[] args) throws NativeException,
			IllegalAccessException, UnsupportedEncodingException {
		// long start = System.currentTimeMillis();
		// System.out.println(getCharacterCode().toString());
		// System.out.println(getGrantCount(rtfPath,"12121212121212121121212121212121").toString());
		 System.out.println(parseGrantFile(rtfPath,
		 "12121212121212121121212121212121"));
		//System.out.println("======================"+getCharacterCode());
		System.out.println(parseGrantFile02(rtfPath,
				"12121212121212121121212121212121"));
		byte[] b = "zhagneretes发生的发生的".getBytes();
		System.out
				.println("--------------------------------------------------");
		for (int i = 0; i < b.length; i++) {
			System.out.println(b[i] + "====" + (char) b[i]);
		}
		System.out.println(new String(b, 0, b.length, "utf-8"));
		// System.out.println(System.currentTimeMillis() - start);

	}
}
