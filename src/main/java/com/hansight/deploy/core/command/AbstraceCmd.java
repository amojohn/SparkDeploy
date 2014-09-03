package com.hansight.deploy.core.command;

import java.io.IOException;

import com.hansight.deploy.utils.FileUtils;

public abstract class AbstraceCmd implements Cmd {

	@Override
	public String help() {
		try {
			return FileUtils.readClassPath("cmd/command/" + getName() + ".txt");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

}
