package com.hansight.deploy.utils;

public class StringUtil {
	public static String upperFirst(String name) {
		if (name == null || "".equals(name)) {
			return "";
		}
		String dist = name.substring(0, 1).toUpperCase();
		if (name.length() > 1) {
			dist += name.substring(1);
		}
		return dist;
	}

	public static String lowerFirst(String name) {
		if (name == null || "".equals(name)) {
			return "";
		}
		String dist = name.substring(0, 1).toLowerCase();
		if (name.length() > 1) {
			dist += name.substring(1);
		}
		return dist;
	}
}
