package com.hansight.deploy.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class SerializableUtil {
	public static void main(String[] args) {
	}

	public static byte[] get(Object obj) {
		if (obj == null) {
			return null;
		}
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ObjectOutputStream oos = null;
		try {
			oos = new ObjectOutputStream(baos);
			oos.writeObject(obj);
			byte[] bs = baos.toByteArray();
			return bs;
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (oos != null)
					oos.close();
				if (baos != null)
					baos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	public static Object get(byte[] bs) {
		ByteArrayInputStream bais = new ByteArrayInputStream(bs);
		ObjectInputStream ois = null;
		try {
			ois = new ObjectInputStream(bais);
			return ois.readObject();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			try {
				if (ois != null)
					ois.close();
				if (bais != null)
					bais.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
}
