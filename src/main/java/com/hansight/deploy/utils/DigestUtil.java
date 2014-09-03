package com.hansight.deploy.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class DigestUtil {
	private static final char hexDigits[] = { '0', '1', '2', '3', '4', '5',
			'6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };

	public static String md5(byte[] b) {
		try {
			MessageDigest md = MessageDigest.getInstance("md5");
			md.update(b);
			byte[] rs = md.digest();
			return byteToHexString(rs);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String md5(String str) {
		if (str != null) {
			return md5(str.getBytes());
		} else {
			return null;
		}
	}

	public static String sha1(byte[] b) {
		try {
			MessageDigest md = MessageDigest.getInstance("sha-1");
			md.update(b);
			byte[] rs = md.digest();
			return byteToHexString(rs);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String sha1(String str) {
		if (str != null) {
			return sha1(str.getBytes());
		} else {
			return null;
		}
	}

	public static final String byteToHexString(byte[] bytes) {
		char str[] = new char[bytes.length * 2];
		int k = 0;
		for (int i = 0; i < bytes.length; i++) {
			byte byte0 = bytes[i];
			str[k++] = hexDigits[byte0 >>> 4 & 0xf];
			str[k++] = hexDigits[byte0 & 0xf];
		}
		return new String(str);
	}

	public static void main(String[] args) {
		System.out.println(md5("password"));
		;
	}
}
