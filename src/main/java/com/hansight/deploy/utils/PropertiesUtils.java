package com.hansight.deploy.utils;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.net.URL;
import java.util.Properties;

public class PropertiesUtils {
	private static Properties props = new Properties();
	static {
		try {
			props.load(PropertiesUtils.class.getClassLoader()
					.getResourceAsStream("ultradfs.properties"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 获取key的值，如果为空返回defaultValue。
	 * 
	 * @param key
	 * @param defaultValue
	 * @return
	 */
	public static String getProperty(String key, String defaultValue) {
		String value = props.getProperty(key);
		if (ParamUtil.empty(value)) {
			return defaultValue;
		}
		return value;
	}

	/**
	 * 设置key的值为value,然后保存到文件。
	 * 
	 * @param key
	 * @param value
	 */
	public static void saveProperties(String key, long value) {
		props.setProperty(key, String.valueOf(value));
		URL url = PropertiesUtils.class.getClassLoader().getResource(
				"openrdp.properties");
		Writer writer;
		try {
			writer = new FileWriter(url.getFile());
			props.store(writer, "");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
