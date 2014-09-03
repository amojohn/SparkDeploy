package com.hansight.deploy.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class IPUtils {
	private static Pattern pattern = Pattern
			.compile("\\[(\\d{1,3})-(\\d{1,3})\\]");

	public synchronized static List<String> parse(String inIps) {
		String[] arr = inIps.split(",");
		List<String> list = new ArrayList<>();
		for (String ips : arr) {
			String[] fields = ips.split("\\.");
			if (fields.length != 4) {
				return null;
			}
			parse(fields, "", 0, list);
		}
		return list;
	}

	private static void parse(String[] fields, String pre, int index,
			List<String> list) {
		Matcher m = pattern.matcher(fields[index]);

		if (m.matches()) {
			int begin = Integer.parseInt(m.group(1));
			int end = Integer.parseInt(m.group(2));
			if (begin > 255 || begin < 0 || end > 255 || end < 0) {
				throw new IllegalArgumentException();
			}
			if (begin > end) {
				int tmp = begin;
				begin = end;
				end = tmp;
			}
			if (index == 3) {
				for (int i = begin; i <= end; i++) {
					list.add(pre + "." + i);
				}
			} else {
				for (int i = begin; i < end; i++) {
					if (index != 0) {
						parse(fields, pre + "." + i, index + 1, list);
					} else {
						parse(fields, i + "", index + 1, list);
					}

				}
			}
		} else {
			if (index == 3) {
				list.add(pre + "." + fields[index]);
			} else {
				if (index != 0) {
					parse(fields, pre + "." + fields[index], index + 1, list);
				} else {
					parse(fields, fields[index], index + 1, list);
				}
			}
		}
	}
}
