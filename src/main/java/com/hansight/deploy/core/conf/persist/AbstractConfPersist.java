package com.hansight.deploy.core.conf.persist;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hansight.deploy.core.conf.Configuration;
import com.hansight.deploy.core.conf.Constants;
import com.hansight.deploy.utils.IOUtils;

public abstract class AbstractConfPersist implements ConfPersist {
	private static final Logger LOG = LoggerFactory
			.getLogger(AbstractConfPersist.class);
	protected final Map<String, Value> cache = new HashMap<String, Value>();

	public void init() {
		loadXML("conf.default.xml");
		loadValue();
		reset();
	}

	protected abstract void loadValue();

	// update false , invoke insertValue
	public abstract boolean setValue(String name, Value val) throws Exception;

	public abstract boolean deleteValue(String name) throws Exception;

	protected void reset() {
		String resource = get(Constants.CONF_RESET_FILE);
		if (resource == null || resource.length() == 0) {
			LOG.debug("reset file not config");
			return;
		}
		File file = new File(resource);
		if (file.isDirectory()) {
			file = new File(file.getAbsolutePath() + File.separator
					+ "conf.reset.xml");
		}
		if (!file.exists()) {
			LOG.debug("reset file not exists:{}", resource);
			return;
		}
		try {
			parseXML(new FileInputStream(file));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		String reset = get(Constants.CONF_RESET_JUST_ONCE);
		if ("true".equalsIgnoreCase(reset)) {
			String renameto = resource + "_" + System.currentTimeMillis()
					+ ".bak";
			file.renameTo(new File(renameto));
			LOG.debug("to avoid next start reset again, rename reset file :{}",
					renameto);
		}
	}

	protected void loadXML(String... files) {
		InputStream in = null;
		for (String file : files) {
			try {
				in = Configuration.class.getClassLoader().getResourceAsStream(
						file);
				parseXML(in);
			} finally {
				IOUtils.close(in);
			}
		}
	}

	protected void parseXML(InputStream in) {
		SAXReader reader = new SAXReader();
		Document document;
		try {
			document = reader.read(in);
			Element root = document.getRootElement();
			for (Iterator<?> iter = root.elementIterator("property"); iter
					.hasNext();) {
				Element property = (Element) iter.next();

				String description = property.elementText("description");
				String name = property.elementText("name");
				String value = property.elementText("value");
				String finalType = property.elementText("final");
				if (description != null) {
					description = description.trim();
				}
				if (name != null) {
					name = name.trim();
				}
				if (value != null) {
					value = value.trim();
				}
				Value val = cache.get(name);
				if (val != null) {
					if (val.isFinal()) {
						LOG.warn("want overwrite final property:{}, {}", name,
								value);
						continue;
					}
				} else {
					val = new Value();
				}
				val.setValue(value);
				val.setDescription(description);
				val.setValue(value);
				if (finalType != null
						&& "true".equalsIgnoreCase(finalType.trim())) {
					val.setFinal(true);
				}
				cache.put(name, val);
			}
		} catch (DocumentException e) {
			e.printStackTrace();
		} finally {
			IOUtils.close(in);
		}
	}

	protected Value getValue(String name) {
		Value val = null;
		val = cache.get(name);
		if (val == null) {
			val = new Value();
			cache.put(name, val);
		}
		return val;
	}

	public boolean set(String name, String value) {
		boolean res = false;
		Value val = getValue(name);
		try {
			val.setValue(value);
			res = setValue(name, val);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return res;
	}

	public boolean delete(String name) {
		boolean res = false;
		try {
			boolean exist = false;
			Value val = cache.get(name);
			if (val != null) {
				exist = true;
			}
			if (exist) {
				res = deleteValue(name);
			}
		} catch (Exception e) {
			LOG.warn("set conf error", e);
		}
		return res;
	}

	public String get(String name) {
		Value val = cache.get(name);
		if (val == null) {
			return null;
		}
		return val.getValue();
	}

	public Map<String, String> getAll() {
		Map<String, String> maps = new HashMap<String, String>(cache.size());
		for (Map.Entry<String, Value> entry : cache.entrySet()) {
			maps.put(entry.getKey(), entry.getValue().getValue());
		}
		return maps;
	}
}
