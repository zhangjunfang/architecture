package cn.newcapec.framework.plugins.quartz.vo;

import java.util.Map;

public class JobBean {

	private String name;

	private String group;

	private String description;

	private String jobClass;

	private boolean durable;

	private boolean recovery;
	
	private boolean disallowConcurrent;
	
	private boolean persistJobData;
	
	private boolean hasError;
	
	/**
	 * 参数名,参数默认值(变量值)定义
	 */
	private Map<String, String> parameters;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getGroup() {
		return group;
	}

	public void setGroup(String group) {
		this.group = group;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getJobClass() {
		return jobClass;
	}

	public void setJobClass(String jobClass) {
		this.jobClass = jobClass;
	}

	public boolean isDurable() {
		return durable;
	}

	public void setDurable(boolean durable) {
		this.durable = durable;
	}

	public boolean isRecovery() {
		return recovery;
	}

	public void setRecovery(boolean recovery) {
		this.recovery = recovery;
	}

	public Map<String, String> getParameters() {
		return parameters;
	}

	public void setParameters(Map<String, String> parameters) {
		this.parameters = parameters;
	}

	public boolean isHasError() {
		return hasError;
	}

	public void setHasError(boolean hasError) {
		this.hasError = hasError;
	}

	public boolean isPersistJobData() {
		return persistJobData;
	}

	public void setPersistJobData(boolean persistJobData) {
		this.persistJobData = persistJobData;
	}

	public boolean isDisallowConcurrent() {
		return disallowConcurrent;
	}

	public void setDisallowConcurrent(boolean disallowConcurrent) {
		this.disallowConcurrent = disallowConcurrent;
	}

}
