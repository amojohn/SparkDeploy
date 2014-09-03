package com.hansight.deploy.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class HtmlUtil {
	
	/**
	 * 移除给定信息中所有的HTML元素
	 * 
	 * @param description
	 * @return
	 */
	public static String removeAllTag(String description) {
		if (!"".equals(description) || null != description) {
			return description.replaceAll("<[.[^<]]*>", "")
					.replaceAll("\"", "");
		} else {
			return "";
		}
	}
	
	/**
	 * 读取网页并存盘
	 * @param page 网页地址
	 * @param filePath 本地路径
	 */
	public static void makeHtml(String page, String filePath) {
		makeHtml(page, filePath, "UTF-8");
	}

	/**
	 * 读取网页并存盘
	 * 
	 * @param page 网页地址
	 * @param filePath 本地路径
	 * @param chartset 网页编码
	 */
	public static void makeHtml(String page, String filePath, String chartset) {
		HttpURLConnection huc = null;
		BufferedReader br = null;
		BufferedWriter bw = null;
		try {
			huc = (HttpURLConnection) new URL(page).openConnection();
			System.setProperty("sun.net.client.defaultConnectTimeout", "30000");
			System.setProperty("sun.net.client.defaultReadTimeout", "30000");
			huc.connect();
			InputStream stream = huc.getInputStream();
			bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(filePath), chartset));
			br = new BufferedReader(new InputStreamReader(stream, chartset));
			String line;
			while ((line = br.readLine()) != null) {
				if (line.trim().length() > 0) {
					bw.write(line);
					bw.newLine();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				br.close();
				bw.close();
				huc.disconnect();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public static String transURL(String context, String dist) {
		try {
			URL c = new URL(context);
			URL d = new URL(c, dist);
			return d.toString();
		} catch (MalformedURLException e) {
			e.printStackTrace();
			return dist;
		}
	}
	
	/**
	 * 获取网址的源码
	 * 
	 * @param url
	 * @return
	 */
	public static String getHtmlSource(String url) {
		return getHtmlSource(url, "utf-8");
	}

	/**
	 * 获取网址的源码
	 * 
	 * @param url
	 * @param encoding
	 * @return
	 */
	public static String getHtmlSource(String url, String encoding) {
		StringBuffer codeBuffer = null;
		BufferedReader in = null;
		try {
			URLConnection uc = new URL(url).openConnection();
			uc.setConnectTimeout(5*60*000);
			// 读取url流内容
			in = new BufferedReader(new InputStreamReader(uc.getInputStream(),
					encoding));
			codeBuffer = new StringBuffer();
			String tempCode = "";
			// 把buffer内的值读取出来
			while ((tempCode = in.readLine()) != null) {
				codeBuffer.append(tempCode).append("\n");
			}
			in.close();
			return codeBuffer.toString();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "";
	}

	public static boolean download(String fileUrl, String dist) {
		OutputStream os = null;
		try {
			URL url = new URL(fileUrl);
			InputStream is = url.openStream();
			File f = new File(dist);
			f.getParentFile().mkdirs();
			os = new FileOutputStream(f);
			int bytesRead = 0;
			byte[] buffer = new byte[8192];
			while ((bytesRead = is.read(buffer, 0, 8192)) != -1) {
				os.write(buffer, 0, bytesRead);
			}
			return true;
		} catch (Exception e) {
			return false;
		} finally {
			IOUtils.close(os);
		}
	}
	
	public static void main(String args[]) {
//		HtmlUtil.makeHtml("http://www.baidu.com", "E:/aa.html");
		System.out.println(HtmlUtil.getHtmlSource("http://www.shuoshuo520.com/Book1/1.html", "gbk"));
	}
}