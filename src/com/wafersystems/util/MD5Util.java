package com.wafersystems.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.codec.binary.Base64;

public class MD5Util {

	public static String encodeNoEqualSign(String str) throws NoSuchAlgorithmException {
		String temp = encode(str);
		temp = temp.substring(0, temp.indexOf('='));
		return temp;
	}

	public static String encode(String str) throws NoSuchAlgorithmException {
		if (null == str) {
			throw new NullPointerException("要加密的字符串为空");
		}

		MessageDigest messageDigest = MessageDigest.getInstance("MD5");
		messageDigest.update(str.getBytes());

		return new String(new Base64().encode(messageDigest.digest()));
	}

}
