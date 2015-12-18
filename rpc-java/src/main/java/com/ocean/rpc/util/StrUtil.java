package com.ocean.rpc.util;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import com.ocean.rpc.io.ByteBufferStream;

/**
 * 
 * @author： ocean 创建时间：2015年12月18日 mail：zhangjunfang0505@163.com 描述：
 */
public final class StrUtil {

	public final static String toString(ByteBufferStream stream) {
		byte[] bytes = stream.toArray();
		try {
			return new String(bytes, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			return new String(bytes);
		}
	}

	public final static String[] split(String s, char c, int limit) {
		if (s == null) {
			return null;
		}
		ArrayList<Integer> pos = new ArrayList<Integer>();
		int i = -1;
		while ((i = s.indexOf(c, i + 1)) > 0) {
			pos.add(i);
		}
		int n = pos.size();
		int[] p = new int[n];
		i = -1;
		for (int x : pos) {
			p[++i] = x;
		}
		if ((limit == 0) || (limit > n)) {
			limit = n + 1;
		}
		String[] result = new String[limit];
		if (n > 0) {
			result[0] = s.substring(0, p[0]);
		} else {
			result[0] = s;
		}
		for (i = 1; i < limit - 1; ++i) {
			result[i] = s.substring(p[i - 1] + 1, p[i]);
		}
		if (limit > 1) {
			result[limit - 1] = s.substring(p[limit - 2] + 1);
		}
		return result;
	}

}
