package com.transilink.framework.core.handler;

import java.io.File;
import java.io.FileInputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.springframework.stereotype.Component;

import com.transilink.framework.core.logs.LogEnabled;

/**
 *
 * @author huangxin (3203317@qq.com)
 *
 */
@Component
@SuppressWarnings("rawtypes")
public class ConfigurationRead implements LogEnabled {
	private List<String> PFILE = null;

	private long m_lastModifiedTime = 0L;

	private Properties m_props = null;

	@SuppressWarnings("unused")
	private ConfigurationRead() {
	}

	public ConfigurationRead(List<String> pFile) {
		this.PFILE = pFile;
		try {
			List list = getFile(pFile);
			if ((list != null) && (list.size() > 0)) {
				for (int i = 0; i < list.size(); i++) {
					File loadFile = (File) list.get(i);
					this.m_lastModifiedTime = loadFile.lastModified();
					if (this.m_lastModifiedTime == 0L) {
						log.info(this.PFILE + "file does not exist!");
					}
					this.m_props = new Properties();
					this.m_props.load(new FileInputStream(loadFile));

					Set set = this.m_props.entrySet();

					Iterator it = set.iterator();
					String key = null;
					String value = null;

					while (it.hasNext()) {
						Map.Entry entry = (Map.Entry) it.next();

						key = String.valueOf(entry.getKey());
						value = String.valueOf(entry.getValue());

						key = key == null ? key : key.trim();
						value = value == null ? value : value.trim();

						UrlMapping.loadUrlMap.put(key, value);
					}
				}
			}
		} catch (URISyntaxException e) {
			log.error("文件路径不正确");
		} catch (Exception e) {
			log.error("文件读取异常");
		}
	}

	@SuppressWarnings("unchecked")
	private List<File> getFile(List<String> PFILE) throws URISyntaxException {
		List files = new ArrayList();
		if ((PFILE != null) && (PFILE.size() > 0)) {
			for (int i = 0; i < PFILE.size(); i++) {
				URI fileUri = getClass().getClassLoader()
						.getResource(((String) PFILE.get(i)).toString())
						.toURI();
				files.add(new File(fileUri));
			}
		}
		return files;
	}
}