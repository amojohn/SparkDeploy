package com.hansight.deploy.utils;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ParamUtil {
	private static Pattern p = Pattern.compile("^(86)?(13|15|18)\\d{9}$");

	public static boolean empty(String param) {
		return !notEmpty(param);
	}

	public static boolean empty(Boolean param) {
		return !notEmpty(param);
	}

	public static boolean empty(Long param) {
		return !notEmpty(param);
	}

	@SuppressWarnings({"rawtypes" })
	public static boolean empty(List param) {
		return !notEmpty(param);
	}

	public static boolean empty(Object param) {
		return !notEmpty(param);
	}

	public static boolean notEmpty(String param) {
		if (param == null || "".equals(param)) {
			return false;
		}
		String tmp = param.trim();
		if (tmp == null || "".equals(tmp)) {
			return false;
		}
		return true;
	}

	public static boolean notEmpty(Boolean enable) {
		if (enable != null) {
			return true;
		}
		return false;
	}

	public static boolean notEmpty(Long param) {
		if (param != null) {
			return true;
		}
		return false;
	}

	@SuppressWarnings({"rawtypes" })
	public static boolean notEmpty(List list) {
		if (list != null && list.size() > 0) {
			return true;
		}
		return false;
	}

	public static boolean notEmpty(Object obj) {
		if (obj instanceof String) {
			return notEmpty((String)obj);
		}
		if (obj != null) {
			return true;
		}
		return false;
	}

	public static boolean isMobile(String param) {
		Matcher m = p.matcher(param);
		if (m.matches()) {
			return true;
		}
		return false;
	}

	public static void main(String[] args) {
		System.out.println(isMobile("15538024053"));
	}
}
