
package com.transilink.framework.plugins.quartz.rest;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobListener;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.transilink.framework.core.rest.BaseRequest;
import com.transilink.framework.core.rest.BaseResource;
import com.transilink.framework.core.rest.BaseResponse;
import com.transilink.framework.core.utils.dataUtils.DateUtil;
import com.transilink.framework.core.utils.jsonUtils.JSONTools;
import com.transilink.framework.plugins.quartz.vo.ChooseSchedulerForm;
import com.transilink.framework.plugins.quartz.vo.JobDetailForm;
import com.transilink.framework.plugins.quartz.vo.ListenerForm;
import com.transilink.framework.plugins.quartz.vo.SchedulerDTO;

/**
 * 
 * 描述：
 * 
 * @author ocean
 * 2015年4月15日
 *  email：zhangjunfang0505@163.com
 */
@Component
@Scope(value="prototype")
public class SchedulerResource extends BaseResource {
	
	@Autowired
	private Scheduler scheduler;
	
	public void schedulerUI(BaseRequest request,BaseResponse response){
		String command=JSONTools.getString(getJsonObject(), "command");
		if (SchedulerResource.log.isDebugEnabled()) {
			SchedulerResource.log.debug("command=" + command);
		}
		ChooseSchedulerForm scheduleInfo = new ChooseSchedulerForm();

		try {
			scheduleInfo.setMessage("命令‘" + command + "’执行成功。");
			if (command.equals("start")) {
				this.scheduler.start();
			} else if (command.equals("stop")) {
				this.scheduler.shutdown();
			} else if (command.equals("pause")) {
				this.scheduler.standby();
			} else if (command.equals("waitAndStopScheduler")) {
				this.scheduler.shutdown(true);
			} else if (command.equals("pauseAll")) {
				this.scheduler.pauseAll();
			} else if (command.equals("resumeAll")) {
				this.scheduler.resumeAll();
			} else {
				scheduleInfo.setMessage(StringUtils.isBlank(command) ? "无" : "命令‘" + command + "’不存在。");
			}
		} catch (SchedulerException e) {
			scheduleInfo.setMessage("命令‘" + command + "’执行失败，失败原因：" + e.getMessage());
			SchedulerResource.log.warn("error in Scheduler Controller,  command=:" + command, e);
		} catch (Exception e) {
			scheduleInfo.setMessage("命令‘" + command + "’执行失败，失败原因：" + e.toString());
			SchedulerResource.log.error("error in Scheduler Controller,  command=:" + command, e);
		}
		try {
			this.populateSchedulerForm(this.scheduler, scheduleInfo);
		} catch (SchedulerException e) {
			SchedulerResource.log.error(null, e);
		}
		getTransilinkViewContext().put("scheduleInfo", scheduleInfo);
		response.toView(getUrl("scheduler.schedulerUI"), getTransilinkViewContext());
	}

	/**
	 * @author 景明超
	 * 2014-1-2 下午3:22:57
	 * @param scheduler2
	 * @param scheduleInfo
	 * @throws SchedulerException 
	 */
	private void populateSchedulerForm(Scheduler scheduler,
			ChooseSchedulerForm form) throws SchedulerException {
		form.setChoosenSchedulerName(scheduler.getSchedulerName());
		form.setSchedulers(new ArrayList<Scheduler>());
		form.getSchedulers().add(scheduler);

		SchedulerDTO schedForm = new SchedulerDTO();
		schedForm.setSchedulerId(scheduler.getSchedulerInstanceId());
		schedForm.setSchedulerName(scheduler.getSchedulerName());
		schedForm.setNumJobsExecuted(String.valueOf(scheduler.getMetaData().getNumberOfJobsExecuted()));

		if (scheduler.getMetaData().isJobStoreSupportsPersistence()) {
			schedForm.setPersistenceType("数据库");
		} else {
			schedForm.setPersistenceType("内存");
		}
		schedForm.setRunningSince(DateUtil.toDatetimeString(scheduler.getMetaData().getRunningSince()));
		if (scheduler.isShutdown()) {
			schedForm.setState("停止");
		} else if (scheduler.isInStandbyMode()) {
			schedForm.setState("暂停");
		} else {
			schedForm.setState("运行");
		}

		schedForm.setThreadPoolSize(String.valueOf(scheduler.getMetaData().getThreadPoolSize()));
		schedForm.setVersion(scheduler.getMetaData().getVersion());
		schedForm.setSummary(scheduler.getMetaData().getSummary());

		List<JobExecutionContext> jobDetails = scheduler.getCurrentlyExecutingJobs();
		form.setExecutingJobs(new ArrayList<JobDetailForm>());
		for (Iterator<JobExecutionContext> iter = jobDetails.iterator(); iter.hasNext();) {
			JobExecutionContext job = iter.next();
			JobDetail jobDetail = job.getJobDetail();

			JobDetailForm jobForm = new JobDetailForm();
			jobForm.setGroupName(jobDetail.getKey().getGroup());
			jobForm.setName(jobDetail.getKey().getName());
			jobForm.setDescription(jobDetail.getDescription());
			jobForm.setJobClass(jobDetail.getJobClass().getName());

			form.getExecutingJobs().add(jobForm);
		}
		List<JobListener> jobListeners = scheduler.getListenerManager().getJobListeners();
		for (Iterator<JobListener> iter = jobListeners.iterator(); iter.hasNext();) {
			JobListener jobListener = iter.next();
			ListenerForm listenerForm = new ListenerForm();
			listenerForm.setListenerName(jobListener.getName());
			listenerForm.setListenerClass(jobListener.getClass().getName());
			schedForm.getGlobalJobListeners().add(listenerForm);
		}
		form.setScheduler(schedForm);
	}

}
