package com.hansight.deploy.utils;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.RandomAccessFile;

public class FileUtils {
	public static String readClassPath(String name) throws IOException {
		InputStream in = FileUtils.class.getClassLoader().getResourceAsStream(
				name);
		try {
			return read(in);
		} finally {
			IOUtils.close(in);
		}
	}

	public static String readShell(String name, String enter)
			throws IOException {
		InputStream in = FileUtils.class.getClassLoader().getResourceAsStream(
				name);
		try {
			return IOUtils.readShell(in, enter);
		} finally {
			IOUtils.close(in);
		}
	}

	public static String readClassPath(String name, String enter)
			throws IOException {
		InputStream in = FileUtils.class.getClassLoader().getResourceAsStream(
				name);
		try {
			return read(in);
		} finally {
			IOUtils.close(in);
		}
	}

	public static String read(InputStream in) throws IOException {
		return IOUtils.read(in).toString();
	}

	public static String readTxt(String name) throws IOException {
		return readTxt(new File(name));
	}

	public static String readTxt(File name) throws IOException {
		InputStream in = new FileInputStream(name);
		return IOUtils.read(in, true).toString();
	}

	public static byte[] readBin(String name) throws IOException {
		return readBin(new File(name));
	}

	public static byte[] readBin(File name) throws IOException {
		InputStream in = new FileInputStream(name);
		return IOUtils.readBin(in, true);
	}

	public static void copy(File src, File dst, boolean append)
			throws IOException {
		if (dst == null || src == null)
			return;
		File dirFile = dst.getParentFile();
		if (dirFile != null && !dirFile.exists()) {
			dirFile.mkdirs();
		}
		InputStream in = null;
		OutputStream out = null;
		try {
			in = new FileInputStream(src);
			out = new FileOutputStream(dst, append);
			byte[] buffer = new byte[16 * 1024];
			while (in.read(buffer) != -1) {
				out.write(buffer);
			}
		} finally {
			IOUtils.close(in, out);
		}
	}

	// TODO test
	public static void copy(RandomAccessFile raf, long start, long length,
			File dst, boolean append) {
		if (dst == null || raf == null)
			return;
		// 目录不存在在话就添加
		File dirFile = dst.getParentFile();
		if (dirFile != null && !dirFile.exists()) {
			dirFile.mkdirs();
		}
		BufferedOutputStream bos = null;
		try {
			if (start > 0) {
				raf.seek(start);
			}
			bos = new BufferedOutputStream(new FileOutputStream(dst, append));
			byte[] buffer = new byte[4072];
			int size = 0;
			long total = 0;
			while ((size = raf.read(buffer)) != -1) {
				if (total + size < length) {
					bos.write(buffer, 0, size);
				} else {
					bos.write(buffer, 0, (int) (length - total));
				}
				total += size;
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			IOUtils.close(bos);
		}
	}

	public static void deleteFile(String filePath) {
		deleteFile(new File(filePath));
	}

	public static void deleteFile(File file) {
		if (file.exists()) {
			file.delete();
		}
	}

	public static boolean write(String file, String content, boolean append) {
		return write(new File(file), content, append);
	}

	public static boolean write(File file, String content) {
		return write(file, content, false);
	}

	public static boolean write(File file, String content, boolean append) {
		File parent = file.getParentFile();
		BufferedOutputStream bos;
		try {
			if (!parent.exists()) {
				parent.mkdirs();
			}
			bos = new BufferedOutputStream(new FileOutputStream(file, append));
			bos.write(content.getBytes());
			bos.flush();
			bos.close();
			return true;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}
}
