package cn.newcapec.framework.plugins.quartz.biz;

import java.util.Date;

import org.quartz.CronExpression;
import org.quartz.JobDetail;
import org.quartz.Trigger;

/**
 * 
 * 调度接口
 */
public interface ScheduleService {
	
	/**
	 * 根据 jobDetail 和 trigger来调度
	 * @param jobDetail  调度任务对象
	 * @param trigger  Quartz触发器
	 */
	void schedule(JobDetail jobDetail, Trigger trigger);
	
	/**
	 * 根据 trigger来调度
	 * @param trigger  Quartz触发器
	 */
	void schedule(Trigger trigger);
	
	/**
	 * 根据 Quartz Cron Expression 调试任务
	 * @param job  调度任务对象
	 * @param cronExpression  Quartz Cron 表达式，如 "0/10 * * ? * * *"等
	 */
	void schedule(JobDetail jobDetail, String cronExpression);
	
	/**
	 * 根据 Quartz Cron Expression 调试任务
	 * @param job  调度任务对象
	 * @param name  Quartz CronTrigger名称
	 * @param cronExpression Quartz Cron 表达式，如 "0/10 * * ? * * *"等
	 */
	void schedule(JobDetail jobDetail, String name,String cronExpression);
	
	/**
	 * 根据 Quartz Cron Expression 调试任务
	 * @param job  调度任务对象
	 * @param name  Quartz CronTrigger名称
	 * @param cronExpression Quartz Cron 表达式，如 "0/10 * * ? * * *"等
	   * @param group Quartz CronExpression Group
	 */
	 void schedule(JobDetail jobDetail, String name, String cronExpression, String group);
	
	/**
	 * 根据 Quartz Cron Expression 调试任务
	 * @param job  调度任务对象
	 * @param cronExpression Quartz CronExpression
	 */
	void schedule(JobDetail jobDetail, CronExpression cronExpression);
	
	/**
	 * 根据 Quartz Cron Expression 调试任务
	 * @param job  调度任务对象
	 * @param name Quartz CronTrigger名称
	 * @param cronExpression Quartz CronExpression
	 */
	void schedule(JobDetail jobDetail, String name,CronExpression cronExpression);
	
	/**
	 * 根据 Quartz Cron Expression 调试任务
	 * @param job  调度任务对象
	 * @param name Quartz CronTrigger名称
	 * @param cronExpression Quartz CronExpression
	 * @param group Quartz CronExpression Group
	 */
	void schedule(JobDetail jobDetail, String name, CronExpression cronExpression, String group);
	
	/**
	 * 在startTime时执行调试一次
	 * @param job  调度任务对象
	 * @param startTime 调度开始时间
	 */
	void schedule(JobDetail jobDetail, Date startTime);	
	
	/**
	 * 在startTime时执行调试一次
	 * @param job  调度任务对象
	 * @param startTime 调度开始时间
	  * @param group Quartz SimpleTrigger Group
	 */
	void schedule(JobDetail jobDetail, Date startTime, String group);
	
	/**
	 * 在startTime时执行调试一次
	 * @param job  调度任务对象
	 * @param name Quartz SimpleTrigger 名称
	 * @param startTime 调度开始时间
	 */
	void schedule(JobDetail jobDetail, String name,Date startTime);
	
	/**
	 * 在startTime时执行调试一次
	 * @param job  调度任务对象
	 * @param name Quartz SimpleTrigger 名称
	 * @param startTime 调度开始时间
	 * @param group Quartz SimpleTrigger Group
	 */
	void schedule(JobDetail jobDetail, String name, Date startTime,String group);
	
	/**
	 * 在startTime时执行调试，endTime结束执行调度
	 * @param job  调度任务对象
	 * @param startTime 调度开始时间
	 * @param endTime 调度结束时间
	 */
	void schedule(JobDetail jobDetail, Date startTime, Date endTime);	

	/**
	 * 在startTime时执行调试，endTime结束执行调度
	 * @param job  调度任务对象
	 * @param startTime 调度开始时间
	 * @param endTime 调度结束时间
	 * @param group Quartz SimpleTrigger Group
	 */
	void schedule(JobDetail jobDetail, Date startTime, Date endTime, String group) ;
	
	/**
	 * 在startTime时执行调试，endTime结束执行调度
	 * @param job  调度任务对象
	 * @param name Quartz SimpleTrigger 名称
	 * @param startTime 调度开始时间
	 * @param endTime 调度结束时间
	 */
	void schedule(JobDetail jobDetail, String name, Date startTime, Date endTime);
	
	/**
	 * 在startTime时执行调试，endTime结束执行调度
	 * @param job  调度任务对象
	 * @param name Quartz SimpleTrigger 名称
	 * @param startTime 调度开始时间
	 * @param endTime 调度结束时间
	 * @param group Quartz SimpleTrigger Group
	 */
	void schedule(JobDetail jobDetail, String name, Date startTime, Date endTime, String group) ;
	
	/**
	 * 在startTime时执行调试，endTime结束执行调度，重复执行repeatCount次
	 * @param job  调度任务对象
	 * @param startTime 调度开始时间
	 * @param endTime 调度结束时间
	 * @param repeatCount 重复执行次数
	 */
	void schedule(JobDetail jobDetail, Date startTime, Date endTime,int repeatCount);	
	
	/**
	 * 在startTime时执行调试，endTime结束执行调度，重复执行repeatCount次
	 * @param job  调度任务对象
	 * @param startTime 调度开始时间
	 * @param endTime 调度结束时间
	 * @param repeatCount 重复执行次数
	 * @param group Quartz SimpleTrigger Group
	 */
	void schedule(JobDetail jobDetail, Date startTime, Date endTime, int repeatCount, String group);
	
	/**
	 * 在startTime时执行调试，endTime结束执行调度，重复执行repeatCount次
	 * @param job  调度任务对象
	 * @param name Quartz SimpleTrigger 名称
	 * @param startTime 调度开始时间
	 * @param endTime 调度结束时间
	 * @param repeatCount 重复执行次数
	 */
	void schedule(JobDetail jobDetail, String name,Date startTime, Date endTime, int repeatCount);
	
	/**
	 * 在startTime时执行调试，endTime结束执行调度，重复执行repeatCount次
	 * @param job  调度任务对象
	 * @param name Quartz SimpleTrigger 名称
	 * @param startTime 调度开始时间
	 * @param endTime 调度结束时间
	 * @param repeatCount 重复执行次数
	 * @param group Quartz SimpleTrigger Group
	 */
	void schedule(JobDetail jobDetail, String name, Date startTime, Date endTime, int repeatCount, String group);
	
	/**
	 * 在startTime时执行调试，endTime结束执行调度，重复执行repeatCount次，每隔repeatInterval秒执行一次
	 * @param job  调度任务对象
	 * @param startTime 调度开始时间
	 * @param endTime 调度结束时间
	 * @param repeatCount 重复执行次数
	 * @param repeatInterval 执行时间隔间
	 */
	void schedule(JobDetail jobDetail, Date startTime,Date endTime, int repeatCount, long repeatInterval) ;
	
	/**
	 * 在startTime时执行调试，endTime结束执行调度，重复执行repeatCount次，每隔repeatInterval秒执行一次
	 * @param job  调度任务对象
	 * @param startTime 调度开始时间
	 * @param endTime 调度结束时间
	 * @param repeatCount 重复执行次数
	 * @param repeatInterval 执行时间隔间
	 * @param group Quartz SimpleTrigger Group
	 */
	void schedule(JobDetail jobDetail, Date startTime, Date endTime, int repeatCount, long repeatInterval, String group);
	
	/**
	 * 在startTime时执行调试，endTime结束执行调度，重复执行repeatCount次，每隔repeatInterval秒执行一次
	 * @param job  调度任务对象
	 * @param name Quartz SimpleTrigger 名称
	 * @param startTime 调度开始时间
	 * @param endTime 调度结束时间
	 * @param repeatCount 重复执行次数
	 * @param repeatInterval 执行时间隔间
	 */
	void schedule(JobDetail jobDetail, String name, Date startTime, Date endTime, int repeatCount, long repeatInterval);
	
	/**
	 * 在startTime时执行调试，endTime结束执行调度，重复执行repeatCount次，每隔repeatInterval秒执行一次
	 * @param job  调度任务对象
	 * @param name Quartz SimpleTrigger 名称
	 * @param startTime 调度开始时间
	 * @param endTime 调度结束时间
	 * @param repeatCount 重复执行次数
	 * @param repeatInterval 执行时间隔间
	 *  @param group Quartz SimpleTrigger Group
	 */
	void schedule(JobDetail jobDetail, String name ,Date startTime, Date endTime, int repeatCount, long repeatInterval, String group);
	
	/**
	 * 根据名称和组别暂停Tigger
	 * @param triggerName
	 * @param group
	 */
	void pauseTrigger(String triggerName, String triggerGroup);
	
	/**
	 * 恢复Trigger
	 * @param triggerName
	 * @param group
	 */
	void resumeTrigger(String triggerName, String triggerGroup);
	
	/**
	 * 删除Trigger
	 * @param triggerName
	 * @param triggerGroup
	 */
	boolean removeTrigger(String triggerName, String triggerGroup);
	
	/**
	 * 删除Job
	 * @param jobName
	 * @param jobGroup
	 */
	boolean deleteJob(String jobName, String jobGroup);
	
	/**
	 * 任务实体是否存在Job
	 * @param jobName
	 * @param jobGroup
	 */
	boolean jobExisting(String jobName, String jobGroup);
	
	/**
	 * 触发器是否存在
	 * @param trigdgerName
	 * @param trigdgerGroup
	 */
	boolean triggerExisting(String triggerName, String triggerGroup);

}
