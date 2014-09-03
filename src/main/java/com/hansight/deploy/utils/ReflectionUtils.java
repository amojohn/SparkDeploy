package com.hansight.deploy.utils;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.hansight.deploy.core.conf.Configuration;

public class ReflectionUtils {
	public static <T> T newInstance(Class<T> theClass, Configuration conf) {
		T result = null;
		Constructor<T> cons;
		try {
			cons = theClass.getConstructor();
			result = cons.newInstance();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		return result;
	}

	public static Map<String, String> toMap(Object soft) {
		Map<String, String> data = new HashMap<String, String>();

		Method[] methods = soft.getClass().getMethods();
		if (methods != null) {
			try {
				for (Method method : methods) {
					// all get method add top map except getClass Method
					String methodName = method.getName();
					if (methodName.startsWith("get")
							&& !methodName.equals("getClass")) {
						Object object = method.invoke(soft);
						String key = methodName.substring(3);
						// all object has toString
						String value = "";
						if (object != null) {
							value = object.toString();
						}
						data.put(key, value);
					}
				}
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}
		}
		return data;
	}

	public static <T> T createFromMap(Map<String, String> data, Class<T> t) {
		if (data != null) {
			try {
				T obj = t.newInstance();
				for (Iterator<String> iter = data.keySet().iterator(); iter
						.hasNext();) {
					String key = iter.next();
					String value = data.get(key);
					if (value != null && !value.equals("")) {
						Method getMethod = t.getMethod("get"
								+ StringUtil.upperFirst(key));
						if (getMethod != null) {
							Class<?> returnClass = getMethod.getReturnType();
							Method setMethod = t.getMethod(
									"set" + StringUtil.upperFirst(key),
									returnClass);
							String returnType = returnClass.getName();
							if (returnType.equals("int")
									|| returnClass == Integer.class) {
								setMethod.invoke(obj, Integer.parseInt(value));
							} else if (returnType.equals("long")
									|| returnClass == Long.class) {
								setMethod.invoke(obj, Long.parseLong(value));
							} else if (returnType.equals("float")
									|| returnClass == Float.class) {
								setMethod.invoke(obj, Float.parseFloat(value));
							} else if (returnType.equals("double")
									|| returnClass == Double.class) {
								setMethod
										.invoke(obj, Double.parseDouble(value));
							} else if (returnType.equals("boolean")
									|| returnClass == Boolean.class) {
								setMethod.invoke(obj,
										Boolean.parseBoolean(value));
							} else if (returnClass == String.class) {
								setMethod.invoke(obj, value);
							}
						}
					}
				}
				return obj;
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				e.printStackTrace();
			} catch (SecurityException e) {
				e.printStackTrace();
			} catch (NumberFormatException e) {
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	public static void main(String[] args) {
	}
}
