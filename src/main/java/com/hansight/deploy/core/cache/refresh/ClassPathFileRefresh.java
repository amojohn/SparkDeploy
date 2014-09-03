package com.hansight.deploy.core.cache.refresh;

import java.io.IOException;

import com.hansight.deploy.core.cache.Refresh;
import com.hansight.deploy.utils.FileUtils;

public class ClassPathFileRefresh implements Refresh {
	private String name;

	public ClassPathFileRefresh(String name) {
		this.name = name;
	}

	@Override
	public Object refresh() {
		try {
			return FileUtils.readClassPath(name);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

}
