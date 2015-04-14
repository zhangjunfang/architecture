/**
 * 
 */
package cn.newcapec.framework.plugins.quartz.rest;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.lang.time.DateUtils;
import org.quartz.CronExpression;
import org.quartz.Job;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.JobListener;
import org.quartz.ListenerManager;
import org.quartz.Matcher;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerKey;
import org.quartz.impl.JobDetailImpl;
import org.quartz.impl.matchers.GroupMatcher;
import org.quartz.impl.matchers.NameMatcher;
import org.quartz.impl.triggers.CronTriggerImpl;
import org.quartz.impl.triggers.SimpleTriggerImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import cn.newcapec.framework.core.exception.BaseException;
import cn.newcapec.framework.core.exception.asserts.AssertObject;
import cn.newcapec.framework.core.rest.BaseRequest;
import cn.newcapec.framework.core.rest.BaseResource;
import cn.newcapec.framework.core.rest.BaseResponse;
import cn.newcapec.framework.core.rest.Msg;
import cn.newcapec.framework.core.utils.dataUtils.DateUtil;
import cn.newcapec.framework.core.utils.jsonUtils.JSONTools;
import cn.newcapec.framework.core.utils.pagesUtils.PageContext;
import cn.newcapec.framework.core.utils.pagesUtils.PageView;
import cn.newcapec.framework.plugins.quartz.vo.JobBean;
import cn.newcapec.framework.plugins.quartz.vo.JobDetailForm;
import cn.newcapec.framework.plugins.quartz.vo.JobListenerBean;
import cn.newcapec.framework.plugins.quartz.vo.TriggerForm;

/**
 * @author 景明超
 * @version JobController.java 2013-12-26 上午10:01:50
 */
@Component
@Scope("prototype")
public class JobResource extends BaseResource{

	@Autowired
	private Scheduler scheduler;


	public void jobListUI(BaseRequest request,BaseResponse response){
		jobListGrid(request,response);
		response.toView(getUrl("job.jobListUI"), getNewcapectViewContext());
	}

	public void jobListGrid(BaseRequest request,BaseResponse response){
		String jobName=JSONTools.getString(getJsonObject(), "jobName");
		Map<String, List<JobDetailForm>> jobDetails = null;
		try {
			jobDetails = this.getJobDetails();
		} catch (Exception e) {
			log.error(null,e);
		}
		if (StringUtils.isNotBlank(jobName) && jobDetails != null && !jobDetails.isEmpty()) {
			for (Iterator<String> iter = jobDetails.keySet().iterator(); iter.hasNext();) {
				String key = iter.next();
				List<JobDetailForm> list = jobDetails.get(key);
				if (list != null && !list.isEmpty()) {
					for (int i = 0; i < list.size(); i++) {
						JobDetailForm jdf = list.get(i);
						if (jobName.indexOf(jdf.getName()) < 0) {
							list.remove(i);
						}
					}
				}
				jobDetails.put(key, list);
			}
		}

		List<JobDetailForm>allList=new ArrayList<JobDetailForm>();
		for (Iterator<String> iter = jobDetails.keySet().iterator(); iter.hasNext();) {
			String key = iter.next();
			List<JobDetailForm> list = jobDetails.get(key);
			if(list != null && !list.isEmpty()){
				for (int i = 0; i < list.size(); i++) {
					JobDetailForm jdf = list.get(i);
					allList.add(jdf);
				}
			}
		}
		PageView<JobDetailForm>pageView=new PageView<JobDetailForm>(PageContext.getPagesize(), PageContext.getOffset());
		if(PageContext.getOffset()==0){
			PageContext.setOffset(1);
		}
		int fromIndex=(PageContext.getOffset()-1)*PageContext.getPagesize();
		int toIndex=fromIndex+PageContext.getPagesize();
		if(toIndex>allList.size()){
			toIndex=allList.size();
		}
		pageView.setTotalrecord(allList.size());
		pageView.setRecords(allList.subList(fromIndex, toIndex));
		pageView.setJsMethod("reloadJobList");
		getNewcapectViewContext().put("pageView", pageView);
		response.toView(getUrl("job.jobListGrid"), getNewcapectViewContext());
	}

	public void view(BaseRequest request,BaseResponse response){
		editJobUI(request,response);
		String name=JSONTools.getString(getJsonObject(), "name");
		String group=JSONTools.getString(getJsonObject(), "group");
		JobKey jobKey = new JobKey(name, group);
		List<Trigger> triggers = null;
		try {
			triggers = (List<Trigger>) this.scheduler.getTriggersOfJob(jobKey);
		} catch (SchedulerException e1) {
			log.error("", e1);
		}
		java.util.Collection<TriggerForm> jobTriggers = new java.util.ArrayList<TriggerForm>();
		for (Trigger trigger:triggers) {
			TriggerForm tForm = new TriggerForm();
			tForm.setDescription(trigger.getDescription());
			tForm.setJobGroup(trigger.getJobKey().getGroup());
			tForm.setJobName(trigger.getJobKey().getName());
			tForm.setMisFireInstruction(trigger.getMisfireInstruction());
			tForm.setStartTime(DateUtil.toDateTimeString(trigger.getStartTime(),DateUtil.DATETIME_FORMAT));
			tForm.setStopTime(DateUtil.toDateTimeString(trigger.getEndTime(),DateUtil.DATETIME_FORMAT));
			tForm.setTriggerGroup(trigger.getKey().getGroup());
			tForm.setTriggerName(trigger.getKey().getName());
			tForm.setNextFireTime(DateUtil.toDateTimeString(trigger.getNextFireTime(),DateUtil.DATETIME_FORMAT));
			tForm.setPreviousFireTime(DateUtil.toDateTimeString(trigger.getPreviousFireTime(),DateUtil.DATETIME_FORMAT));
			tForm.setType(trigger.getClass().getName());
			jobTriggers.add(tForm);
		}

		getNewcapectViewContext().put("jobTriggers", jobTriggers);

		java.util.Collection<JobListenerBean> jobListeners = new java.util.ArrayList<JobListenerBean>();
		ListenerManager listenerManager = null;
		try {
			listenerManager = this.scheduler.getListenerManager();
		} catch (SchedulerException e) {
			log.error("", e);
		}
		List<JobListener> jls = listenerManager.getJobListeners();


		for (JobListener jobListener : jls) {
			JobListenerBean listenerBean = new JobListenerBean();

			boolean isMatch = false;
			List<Matcher<JobKey>> matchers = listenerManager.getJobListenerMatchers(jobListener.getName());
			for (Matcher<JobKey> matcher : matchers) {
				if (matcher.isMatch(jobKey)) {
					isMatch = true;
					break;
				}
			}
			if (isMatch) {
				listenerBean.setName(jobListener.getName());
				listenerBean.setClazz(jobListener.getClass().getName());
				jobListeners.add(listenerBean);
			}
		}
		getNewcapectViewContext().put("jobListeners", jobListeners);
		response.toView(getUrl("job.jobView"), getNewcapectViewContext());
	}

	public void editJobUI(BaseRequest request,BaseResponse response) {
		String name=JSONTools.getString(getJsonObject(), "name");
		String group=JSONTools.getString(getJsonObject(), "group");
		JobBean jobBean=new JobBean();
		jobBean.setName(name);
		jobBean.setGroup(group);
		jobBean.setHasError(true);// 默认
		if (name.length() > 0 && group.length() > 0) {
			JobKey jobKey = new JobKey(name, group);
			try{
				JobDetail jobDetail = this.scheduler.getJobDetail(jobKey);
				jobBean.setDescription(jobDetail.getDescription());
				jobBean.setJobClass(jobDetail.getJobClass().getName());
				jobBean.setDurable(jobDetail.isDurable());
				jobBean.setRecovery(jobDetail.requestsRecovery());
				jobBean.setDisallowConcurrent(jobDetail.isConcurrentExectionDisallowed());
				jobBean.setPersistJobData(jobDetail.isPersistJobDataAfterExecution());

				Map<String, String> parameters = new java.util.LinkedHashMap<String, String>();
				for(Map.Entry<String, Object> entry : jobDetail.getJobDataMap().entrySet()){
					parameters.put(entry.getKey(), String.valueOf(entry.getValue()));
				}

				jobBean.setParameters(parameters);
				jobBean.setHasError(false);
				getNewcapectViewContext().put("jobBean", jobBean);
			} catch (org.quartz.JobPersistenceException e) {
				this.log.warn(null, e);
				jobBean.setJobClass(e.getCause().getMessage());
			} catch (Exception e) {
				this.log.warn(null, e);
			}
		}
		response.toView(getUrl("job.editJobUI"), getNewcapectViewContext());
	}

	public void addJobUI(BaseRequest request,BaseResponse response){
		jobListGrid(request,response);
		response.toView(getUrl("job.addJobUI"), getNewcapectViewContext());
	}

	public void removeJobTrigger(BaseRequest request,BaseResponse response){
		response.print(doExpAssert(new AssertObject() {
			@Override
			public void AssertMethod(Msg msg) {
				String triggerName = JSONTools.getString(getJsonObject(),"triggerName");
				String triggerGroup = JSONTools.getString(getJsonObject(),"triggerGroup");
				try {
					scheduler.unscheduleJob(new TriggerKey(triggerName, triggerGroup));
					msg.setSuccess(true);
				} catch (SchedulerException e) {
					log.error(null, e);
					msg.setSuccess(false);
				}
			}
		}).toJSONObjectPresention());
	}

	public void removeJobListener(BaseRequest request,BaseResponse response){
		response.print(doExpAssert(new AssertObject() {
			@Override
			public void AssertMethod(Msg msg) {
				String listenerName = JSONTools.getString(getJsonObject(),"listenerName");
				String name = JSONTools.getString(getJsonObject(),"name");
				try {
					Matcher<JobKey> jobkeyMatcher = NameMatcher.nameEndsWith(name);
					scheduler.getListenerManager().removeJobListenerMatcher(listenerName, jobkeyMatcher);
					msg.setSuccess(true);
				} catch (SchedulerException e) {
					log.error(null, e);
					msg.setSuccess(false);
				}
			}
		}).toJSONObjectPresention());
	}

	public void add(BaseRequest request,BaseResponse response){
		response.print(doExpAssert(new AssertObject() {
			@Override
			public void AssertMethod(Msg msg) {
				String name=JSONTools.getString(getJsonObject(), "name");
				String group=JSONTools.getString(getJsonObject(), "group");
				String jobClass=JSONTools.getString(getJsonObject(), "jobClass");
				String description=JSONTools.getString(getJsonObject(), "description");
				boolean recovery=JSONTools.getBoolean(getJsonObject(), "recovery");
				boolean durable=JSONTools.getBoolean(getJsonObject(), "durable");
				JSONObject parameters=JSONObject.fromObject(JSONTools.getString(getJsonObject(), "parameters"));

				JobDetailImpl jobDetail = new JobDetailImpl();
				JobKey jobkey = new JobKey(name, group);
				jobDetail.setKey(jobkey);
				jobDetail.setDescription(description);
				Class<Job>jobClazz=null;
				try {
					jobClazz = (Class<Job>)Class.forName(jobClass);
				} catch (ClassNotFoundException e) {
					throw new BaseException("任务(类名)不存在！");
				}
				jobDetail.setJobClass(jobClazz);
				jobDetail.setRequestsRecovery(recovery);
				jobDetail.setDurability(durable);
				jobDetail.getJobDataMap().putAll(parameters);
				try {
					scheduler.addJob(jobDetail, true);
				} catch (SchedulerException e) {
					log.error(null,e);
					throw new BaseException("增加失败！");
				}

			}
		}).toJSONObjectPresention());
	}

	public void delete(BaseRequest request,BaseResponse response){
		response.print(doExpAssert(new AssertObject() {
			@Override
			public void AssertMethod(Msg msg) {
				JSONArray jsonArray=JSONArray.fromObject(JSONTools.getString(getJsonObject(), "ids"));
				for (Object object : jsonArray) {
					JSONObject jsonObject=(JSONObject)object;
					String name=JSONTools.getString(jsonObject, "name");
					String group=JSONTools.getString(jsonObject, "group");
					if (StringUtils.isNotBlank(name) && StringUtils.isNotBlank(group)) {
						try {
							scheduler.deleteJob(new JobKey(name, group));
						} catch (SchedulerException e) {
							log.error(null, e);
							throw new BaseException("删除失败！");
						}
					} else {
						throw new BaseException("任务名或分组名不能为空！");
					}
				}
			}
		}).toJSONObjectPresention());
	}

	public void executeOnce(BaseRequest request,BaseResponse response){
		response.print(doExpAssert(new AssertObject() {
			@Override
			public void AssertMethod(Msg msg) {
				String name=JSONTools.getString(getJsonObject(), "name");
				String group=JSONTools.getString(getJsonObject(), "group");
				try {
					scheduler.triggerJob(new JobKey(name, group));
				} catch (SchedulerException e) {
					log.error(null, e);
					throw new BaseException("执行失败！");
				}
			}
		}).toJSONObjectPresention());
	}

	public void executeSimpleUI(BaseRequest request,BaseResponse response){
		String name=JSONTools.getString(getJsonObject(), "name");
		String group=JSONTools.getString(getJsonObject(), "group");
		getNewcapectViewContext().put("name", name);
		getNewcapectViewContext().put("group", group);
		response.toView(getUrl("job.executeSimpleUI"), getNewcapectViewContext());
	}
	public void executeSimple(BaseRequest request,BaseResponse response){
		response.print(doExpAssert(new AssertObject() {
			@Override
			public void AssertMethod(Msg msg) {
				String name=JSONTools.getString(getJsonObject(), "name");
				String group=JSONTools.getString(getJsonObject(), "group");
				String triggerGroup=JSONTools.getString(getJsonObject(), "triggerGroup");
				String triggerName=JSONTools.getString(getJsonObject(), "triggerName");
				String description=JSONTools.getString(getJsonObject(), "description");
				String startTime=JSONTools.getString(getJsonObject(), "startTime");
				String endTime=JSONTools.getString(getJsonObject(), "endTime");
				int repeatCount=JSONTools.getInt(getJsonObject(), "repeatCount");
				int repeatInterval=JSONTools.getInt(getJsonObject(), "repeatInterval");

				SimpleTriggerImpl simpleTrigger = new SimpleTriggerImpl();
				simpleTrigger.setName(triggerName);
				simpleTrigger.setGroup(triggerGroup);
				simpleTrigger.setDescription(description);
				simpleTrigger.setRepeatCount(repeatCount);
				simpleTrigger.setRepeatInterval(repeatInterval);

				if(StringUtils.isNotBlank(startTime)){
					try {
						simpleTrigger.setStartTime(DateUtils.parseDate(startTime, new String[]{"yyyy-MM-dd HH:mm"}));
					} catch (ParseException e) {
						log.error(null, e);
					}
				}else {
					simpleTrigger.setStartTime(new Date());
				}
				if(StringUtils.isNotBlank(endTime)){
					try {
						simpleTrigger.setEndTime(DateUtils.parseDate(endTime, new String[]{"yyyy-MM-dd HH:mm"}));
					} catch (ParseException e) {
						log.error(null, e);
					}
				}
				simpleTrigger.setJobName(name);
				simpleTrigger.setJobGroup(group);
				try {
					scheduler.scheduleJob(simpleTrigger);
				} catch (SchedulerException e) {
					log.error(null, e);
					throw new BaseException("执行失败！");
				}
				msg.setSuccess(false);
			}
		}).toJSONObjectPresention());
	}

	public void executeCronUI(BaseRequest request,BaseResponse response){
		String name=JSONTools.getString(getJsonObject(), "name");
		String group=JSONTools.getString(getJsonObject(), "group");
		getNewcapectViewContext().put("name", name);
		getNewcapectViewContext().put("group", group);
		response.toView(getUrl("job.executeCronUI"), getNewcapectViewContext());
	}
	
	public void createCronUI(BaseRequest request,BaseResponse response){
		response.toView(getUrl("job.createCronUI"), getNewcapectViewContext());
	}

	private Map<String, List<JobDetailForm>> getJobDetails() throws Exception {
		Map<String, List<JobDetailForm>> jobDetails = new java.util.LinkedHashMap<String, List<JobDetailForm>>();
		List<String> jobGroupNames = this.scheduler.getJobGroupNames();
		if (jobGroupNames != null) {
			for (String groupName : jobGroupNames) {
				List<JobDetailForm> jgs = new java.util.LinkedList<JobDetailForm>();
				jobDetails.put(groupName, jgs);
				Set<JobKey> jobKeys = this.scheduler.getJobKeys(GroupMatcher.jobGroupEquals(groupName));
				if (jobKeys != null) {
					for (JobKey jobKey : jobKeys) {
						JobDetail detail = null;
						try {
							detail = this.scheduler.getJobDetail(jobKey);
						} catch (org.quartz.JobPersistenceException e) {
							this.log.warn(null, e);
							JobDetailForm jobForm = new JobDetailForm();
							jobForm.setName(jobKey.getName());
							jobForm.setGroupName(jobKey.getGroup());
							jobForm.setJobClass(e.getCause().getMessage());
							jobForm.setDescription(e.toString());
							jgs.add(jobForm);
						} catch (Exception e) {
							this.log.warn(null, e);
						}
						if (detail == null) continue;

						JobDetailForm jobForm = new JobDetailForm();
						jobForm.setName(jobKey.getName());
						jobForm.setGroupName(jobKey.getGroup());
						jobForm.setDescription(detail.getDescription());
						jobForm.setJobClass(detail.getJobClass().getName());
						jgs.add(jobForm);
					}
				}
			}
		}
		return jobDetails;
	}

	public void executeCron(BaseRequest request,BaseResponse response){
		response.print(doExpAssert(new AssertObject() {
			@Override
			public void AssertMethod(Msg msg) {
				String name=JSONTools.getString(getJsonObject(), "name");
				String group=JSONTools.getString(getJsonObject(), "group");
				String triggerGroup=JSONTools.getString(getJsonObject(), "triggerGroup");
				String triggerName=JSONTools.getString(getJsonObject(), "triggerName");
				String description=JSONTools.getString(getJsonObject(), "description");
				String startTime=JSONTools.getString(getJsonObject(), "startTime");
				String endTime=JSONTools.getString(getJsonObject(), "endTime");
				String cron=JSONTools.getString(getJsonObject(), "cron");

				CronTriggerImpl cronTrigger = new CronTriggerImpl();
				cronTrigger.setName(triggerName);
				cronTrigger.setGroup(triggerGroup);
				cronTrigger.setDescription(description);
				
				CronExpression cronExpression;
				try {
					cronExpression = new CronExpression(cron);
				} catch (ParseException e1) {
					log.error(null, e1);
					throw new BaseException("cron表达式不正确！");
				}
				cronTrigger.setCronExpression(cronExpression);

				if(StringUtils.isNotBlank(startTime)){
					try {
						cronTrigger.setStartTime(DateUtils.parseDate(startTime, new String[]{"yyyy-MM-dd HH:mm"}));
					} catch (ParseException e) {
						log.error(null, e);
					}
				}else {
					cronTrigger.setStartTime(new Date());
				}
				if(StringUtils.isNotBlank(endTime)){
					try {
						cronTrigger.setEndTime(DateUtils.parseDate(endTime, new String[]{"yyyy-MM-dd HH:mm"}));
					} catch (ParseException e) {
						log.error(null, e);
					}
				}
				cronTrigger.setJobName(name);
				cronTrigger.setJobGroup(group);
				try {
					scheduler.scheduleJob(cronTrigger);
				} catch (SchedulerException e) {
					log.error(null, e);
					throw new BaseException("执行失败！");
				}
				msg.setSuccess(false);
			}
		}).toJSONObjectPresention());
	}
	public void testCronExpression(BaseRequest request,BaseResponse response){
		response.print(doExpAssert(new AssertObject() {
			@Override
			public void AssertMethod(Msg msg) {
				String cronExp = JSONTools.getString(getJsonObject(),"cronExp");
				JSONObject result=new JSONObject();
				CronExpression exp;
				try {
					exp = new CronExpression(cronExp);
				} catch (ParseException e) {
					log.error(null, e);
					throw new BaseException("Cron表达式错误！");
				}
				result.put("result", "success");
				result.put("message", "Cron表达式格式正确，列出模拟执行时间：");
				Date curentTime = new java.util.Date();
				String beginDate = DateFormatUtils.format(curentTime, "yyyy-MM-dd HH:mm:ss");
				result.put("begin", beginDate);
				Date validTime = curentTime;
				List<String> fireTime = new java.util.LinkedList<String>();
				for (int i = 1; i <= 8; i++) {
					validTime = exp.getNextValidTimeAfter(validTime);
					fireTime.add(DateFormatUtils.format(validTime, "yyyy-MM-dd HH:mm:ss"));
				}
				result.put("fire", fireTime);
				msg.setData(result);
			}
		}).toJSONObjectPresention());
	}
	public void cronHelp(BaseRequest request,BaseResponse response) {
		response.toView(getUrl("job.cronHelp"), getNewcapectViewContext());
	}
}