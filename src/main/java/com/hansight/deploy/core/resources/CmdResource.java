package com.hansight.deploy.core.resources;

import java.io.IOException;
import java.io.InputStream;

import javax.servlet.ServletException;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;

import com.google.gson.Gson;
import com.hansight.deploy.App;
import com.hansight.deploy.core.cache.CacheDaemon;
import com.hansight.deploy.utils.FileUtils;

@Path("api/cmd")
public class CmdResource {

	@GET
	public String doGet() throws ServletException, IOException {
		return (String) CacheDaemon.get("cmd/index.html");
	}

	@POST
	public String doPost(@QueryParam("cmd") String cmd, InputStream is) {
		try {
			if (cmd == null) {
				cmd = FileUtils.read(is);
				Gson gson = new Gson();
				Parameter param = gson.fromJson(cmd, Parameter.class);
				cmd = param.getValue();
			}
			String res = App.cmd.execute(cmd);
			return "{\"result\":\"" + res + "\"}";
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

}
