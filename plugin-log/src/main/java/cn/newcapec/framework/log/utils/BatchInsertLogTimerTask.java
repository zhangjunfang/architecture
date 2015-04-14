package cn.newcapec.framework.log.utils;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.newcapec.framework.core.exception.RedisAccessException;


@Service("batchInsertLogTimerTask")
@Transactional
public class BatchInsertLogTimerTask  {
	/**
	 * 系统日志调度
	 * @throws RedisAccessException 
	 */
	@PostConstruct
	public  void exceptionScheduled() {
		ScheduledExecutorService service = Executors.newScheduledThreadPool(10);
		long initialDelay = 1;
		long period = 1;
        // 从现在开始10秒钟之后，每隔1秒钟执行一次job
		service.scheduleAtFixedRate(
		        new BatchInsertLogTimerJob(), initialDelay,
				period, TimeUnit.SECONDS);
	}
}
