package com.wafersystems.tcs;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5 {

	private static String byte2Hex(byte b) {
		int value = (b & 0x7F) + (b < 0 ? 0x80 : 0);
		return (value < 0x10 ? "0" : "")
				+ Integer.toHexString(value).toLowerCase();
	}

	public static String MD5_32(String passwd) throws NoSuchAlgorithmException {
		MessageDigest md5 = MessageDigest.getInstance("MD5");
		StringBuffer strbuf = new StringBuffer();
		md5.update(passwd.getBytes(), 0, passwd.length());
		byte[] digest = md5.digest();

		for (int i = 0; i < digest.length; i++) {
			strbuf.append(byte2Hex(digest[i]));
		}

		return strbuf.toString();
	}

	public static String MD5_16(String passwd) throws NoSuchAlgorithmException {
		return MD5_32(passwd).subSequence(8, 24).toString();
	}

	public static void main(String[] args) {
		try {
			System.out
					.println(MD5_32("012ac0fb655cf761a7498e8a8fe8e768:503205e7d5cca:00000001:0a4f113b:auth:bfca3ed67e7b88f2c6d2c93b3302600b"));
			// System.out.println(MD5_16("cisco123"));
			// System.out.println(Base64.encode("中国".getBytes("UTF-8")));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
