package com.hansight.deploy.core.resources;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.hansight.deploy.core.conf.Configured;
import com.hansight.deploy.vo.Status;

@Path("/api/conf")
public class ConfResource extends Configured {
	private String encode = "utf-8";

	@GET
	public String doGet(@QueryParam("all") String all,
			@QueryParam("name") String name, @QueryParam("pretty") String pretty) {
		String result = null;
		Gson gson = build(pretty != null);
		if (all != null) {
			Map<String, String> map = conf.getAll();
			List<Parameter> list = new ArrayList<Parameter>();
			for (Map.Entry<String, String> entry : map.entrySet()) {
				list.add(new Parameter(entry.getKey(), entry.getValue()));
			}
			result = gson.toJson(list);
		} else if (name != null) {
			String value = conf.get(name);
			result = gson.toJson(new Parameter(name, value));
		}
		return result;
	}

	@POST
	public Status doPost(InputStream is) {
		Reader in;
		try {
			in = new InputStreamReader(is, encode);
			Gson gson = new Gson();

			Parameter param = gson.fromJson(in, Parameter.class);
			if (param != null && param.getName() != null
					&& param.getName().length() > 0) {

				boolean result = conf.set(param.getName(), param.getValue());
				return Status.status(result);
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return Status.failure(e);
		}
		return Status.failure();

	}

	@PUT
	public Status doPut(InputStream is) throws IOException {
		return doPost(is);
	}

	@DELETE
	public Status doDelete(@QueryParam("pretty") String pretty,
			@QueryParam("name") String name) {
		boolean result = conf.delete(name);
		return Status.status(result);
	}

	private static Gson build(boolean pretty) {
		if (pretty) {
			return new GsonBuilder().setPrettyPrinting().create();
		} else {
			return new Gson();
		}
	}
}
