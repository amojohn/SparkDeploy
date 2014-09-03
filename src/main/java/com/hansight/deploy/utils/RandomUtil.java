package com.hansight.deploy.utils;

import java.util.Random;

@SuppressWarnings("serial")
public class RandomUtil extends Random {
	private static RandomUtil random = new RandomUtil(
			System.currentTimeMillis());

	private RandomUtil() {}

	private RandomUtil(long time) {
		super(time);
	}

	public static RandomUtil getInstance() {
		return random;
	}
}
