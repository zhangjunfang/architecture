package cn.newcapec.framework.plugins.quartz.biz.impl;

import java.text.ParseException;
import java.util.Date;
import java.util.UUID;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.CronExpression;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerKey;
import org.quartz.impl.triggers.CronTriggerImpl;
import org.quartz.impl.triggers.SimpleTriggerImpl;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.newcapec.framework.plugins.quartz.biz.ScheduleService;

/**
 * 调度器的调度接口实现
 */
@Service("scheduleService")
public class ScheduleServiceImpl implements ScheduleService, InitializingBean {
	
	protected final transient Log logger = LogFactory.getLog(this.getClass());
	
	@Autowired
	protected Scheduler quartzScheduler;
	
	@Override
	public void schedule(JobDetail jobDetail, Trigger trigger){
		try {
			this.quartzScheduler.scheduleJob(jobDetail, trigger);
		} catch (SchedulerException e) {
			throw new RuntimeException(e);
		}
	}
	
	@Override
	public void schedule(Trigger trigger){
		try {
			this.quartzScheduler.scheduleJob(trigger);
			this.quartzScheduler.rescheduleJob(trigger.getKey(), trigger);
		} catch (SchedulerException e) {
			throw new RuntimeException(e);
		}
	}
	
	@Override
	public void schedule(JobDetail jobDetail, String cronExpression) {
		schedule(jobDetail, "", cronExpression);
	}

	@Override
	public void schedule(JobDetail jobDetail, String name, String cronExpression) {
		schedule(jobDetail, name, cronExpression, Scheduler.DEFAULT_GROUP);
	}
	
	@Override
	public void schedule(JobDetail jobDetail, String name, String cronExpression, String group) {
		try {
			schedule(jobDetail, name, new CronExpression(cronExpression), group);
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void schedule(JobDetail jobDetail, CronExpression cronExpression) {
		schedule(jobDetail, null, cronExpression);
	}

	@Override
	public void schedule(JobDetail jobDetail, String name, CronExpression cronExpression) {
		schedule(jobDetail, name,  cronExpression, Scheduler.DEFAULT_GROUP) ;
	}
	
	@Override
	public void schedule(JobDetail jobDetail, String name, CronExpression cronExpression, String group) {
		if (name == null || name.trim().equals("")) {
			name = UUID.randomUUID().toString();
		}
		try {
			this.quartzScheduler.addJob(jobDetail, true);
			
			CronTriggerImpl cronTrigger = new CronTriggerImpl();
			cronTrigger.setName(name);
			cronTrigger.setGroup(group);
			cronTrigger.setJobKey(jobDetail.getKey());
			cronTrigger.setCronExpression(cronExpression);

			this.schedule(cronTrigger);
		} catch (SchedulerException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void schedule(JobDetail jobDetail, Date startTime) {
		schedule(jobDetail, startTime, Scheduler.DEFAULT_GROUP);
	}
	
	@Override
	public void schedule(JobDetail jobDetail, Date startTime, String group) {
		schedule(jobDetail, startTime, null,group);
	}

	@Override
	public void schedule(JobDetail jobDetail, String name, Date startTime) {
		schedule(jobDetail, name, startTime, Scheduler.DEFAULT_GROUP);
	}
	
	@Override
	public void schedule(JobDetail jobDetail, String name, Date startTime, String group) {
		schedule(jobDetail, name, startTime, null, group);
	}

	@Override
	public void schedule(JobDetail jobDetail, Date startTime, Date endTime) {
		schedule(jobDetail, startTime, endTime, Scheduler.DEFAULT_GROUP);
	}
	
	@Override
	public void schedule(JobDetail jobDetail, Date startTime, Date endTime, String group) {
		schedule(jobDetail, startTime, endTime, 0, group);
	}

	@Override
	public void schedule(JobDetail jobDetail, String name, Date startTime, Date endTime) {
		schedule(jobDetail, name, startTime, endTime, Scheduler.DEFAULT_GROUP);
	}
	
	@Override
	public void schedule(JobDetail jobDetail, String name, Date startTime, Date endTime, String group) {
		schedule(jobDetail, name, startTime, endTime, 0, group);
	}

	@Override
	public void schedule(JobDetail jobDetail, Date startTime, Date endTime, int repeatCount) {
		schedule(jobDetail, startTime, endTime, repeatCount, Scheduler.DEFAULT_GROUP);
	}
	
	@Override
	public void schedule(JobDetail jobDetail, Date startTime, Date endTime, int repeatCount, String group) {
		schedule(jobDetail, null, startTime, endTime, repeatCount, group);
	}

	@Override
	public void schedule(JobDetail jobDetail, String name, Date startTime, Date endTime, int repeatCount) {
		schedule(jobDetail, name, startTime, endTime, repeatCount, Scheduler.DEFAULT_GROUP);
	}
	
	@Override
	public void schedule(JobDetail jobDetail, String name, Date startTime, Date endTime, int repeatCount, String group) {
		schedule(jobDetail, name, startTime, endTime, repeatCount, 1L, group);
	}

	@Override
	public void schedule(JobDetail jobDetail, Date startTime, Date endTime, int repeatCount, long repeatInterval) {
		schedule(jobDetail, startTime, endTime, repeatCount, repeatInterval, Scheduler.DEFAULT_GROUP);
	}
	
	@Override
	public void schedule(JobDetail jobDetail, Date startTime, Date endTime, int repeatCount, long repeatInterval, String group) {
		schedule(jobDetail, null, startTime, endTime, repeatCount, repeatInterval, group);
	}

	@Override
	public void schedule(JobDetail jobDetail, String name, Date startTime, Date endTime, int repeatCount, long repeatInterval) {
		this.schedule(jobDetail, name , startTime,  endTime,  repeatCount,  repeatInterval, Scheduler.DEFAULT_GROUP);
	}
	
	@Override
	public void schedule(JobDetail jobDetail, String name, Date startTime, Date endTime, int repeatCount, long repeatInterval, String group ) {
		if (name == null || name.trim().equals("")) {
			name = UUID.randomUUID().toString();
		}
		try {
			quartzScheduler.addJob(jobDetail, true);
			
			SimpleTriggerImpl simpleTrigger = new SimpleTriggerImpl();
			simpleTrigger.setName(name);
			simpleTrigger.setGroup(group);
			simpleTrigger.setJobKey(jobDetail.getKey());
			simpleTrigger.setRepeatCount(repeatCount);
			simpleTrigger.setRepeatInterval(repeatInterval);
			simpleTrigger.setStartTime(startTime);
			simpleTrigger.setEndTime(endTime);

			quartzScheduler.scheduleJob(simpleTrigger);
			quartzScheduler.rescheduleJob(simpleTrigger.getKey(), simpleTrigger);
		} catch (SchedulerException e) {
			throw new RuntimeException(e);
		}
	}
	
	@Override
	public void pauseTrigger(String triggerName, String triggerGroup){		
		try {
			quartzScheduler.pauseTrigger(new TriggerKey(triggerName, triggerGroup));//停止触发器
		} catch (SchedulerException e) {
			throw new RuntimeException(e);
		}
	}
	
	@Override
	public void resumeTrigger(String triggerName, String triggerGroup){		
		try {
			quartzScheduler.resumeTrigger(new TriggerKey(triggerName, triggerGroup));//重启触发器
		} catch (SchedulerException e) {
			throw new RuntimeException(e);
		}
	}
	
	@Override
	public boolean removeTrigger(String triggerName, String triggerGroup){		
		try {
			quartzScheduler.pauseTrigger(new TriggerKey(triggerName, triggerGroup));//停止触发器
			return quartzScheduler.unscheduleJob(new TriggerKey(triggerName, triggerGroup));//移除触发器
		} catch (SchedulerException e) {
			throw new RuntimeException(e);
		}
	}
	
	@Override
	public boolean deleteJob(String jobName, String groupName) {
		try {
			return quartzScheduler.deleteJob(new JobKey(jobName, groupName));
		} catch (SchedulerException e) {
			throw new RuntimeException(e);
		}
	}
	
	@Override
	public void afterPropertiesSet() throws Exception {
		org.springframework.util.Assert.notNull(this.quartzScheduler, "properties 'quartzScheduler' is requirt.");
	}

	public void setQuartzScheduler(Scheduler quartzScheduler) {
		this.quartzScheduler = quartzScheduler;
	}

	@Override
	public boolean jobExisting(String jobName, String jobGroup) {
		try {
			return this.quartzScheduler.checkExists(new JobKey(jobName, jobGroup));
		} catch (SchedulerException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public boolean triggerExisting(String triggerName, String triggerGroup) {
		try {
			return this.quartzScheduler.checkExists(new TriggerKey(triggerName, triggerGroup));
		} catch (SchedulerException e) {
			throw new RuntimeException(e);
		}
	}
}
