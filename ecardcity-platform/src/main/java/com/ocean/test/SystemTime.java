package com.ocean.test;

import org.xvolks.jnative.JNative;
import org.xvolks.jnative.exceptions.NativeException;
import org.xvolks.jnative.misc.basicStructures.AbstractBasicData;
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
public class SystemTime extends AbstractBasicData<SystemTime> {
	public short wYear;
	public short wMonth;
	public short wDayOfWeek;
	public short wDay;
	public short wHour;
	public short wMinute;
	public short wSecond;
	public short wMilliseconds;

	@Override
	public Pointer createPointer() throws NativeException {
		pointer = new Pointer(MemoryBlockFactory.createMemoryBlock(getSizeOf()));
		return pointer;
	}

	@Override
	public int getSizeOf() {
		return 8 * 2; // 8 WORDS of 2 bytes
	}

	@Override
	public SystemTime getValueFromPointer() throws NativeException {
		wYear = getNextShort();
		wMonth = getNextShort();
		wDayOfWeek = getNextShort();
		wDay = getNextShort();
		wHour = getNextShort();
		wMinute = getNextShort();
		wSecond = getNextShort();
		wMilliseconds = getNextShort();
		return this;
	}

	public SystemTime() throws NativeException {
		super(null);
		createPointer();
		mValue = this;
	}

	public static SystemTime GetSystemTime() throws NativeException,
			IllegalAccessException {
		JNative nGetSystemTime = new JNative("", "GetSystemTime");
		SystemTime systemTime = new SystemTime();
		nGetSystemTime.setParameter(0, systemTime.getPointer());
		nGetSystemTime.invoke();
		return systemTime.getValueFromPointer();
	}

	public static void main(String[] args) throws NativeException,
			IllegalAccessException {
		System.err.println(GetSystemTime());
	}
}
