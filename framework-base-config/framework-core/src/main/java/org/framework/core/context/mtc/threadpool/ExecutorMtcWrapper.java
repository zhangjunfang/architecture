package org.framework.core.context.mtc.threadpool;

import org.framework.core.context.mtc.MtContextRunnable;

import java.util.concurrent.Executor;

/**
 * {@link org.framework.core.context.mtc.MtContextThreadLocal} Wrapper of {@link Executor},
 * transmit the {@link org.framework.core.context.mtc.MtContextThreadLocal} from the task submit time of {@link Runnable}
 * to the execution time of {@link Runnable}.
 *
 * @author  ocean zhangjunfang0505@163.com
 * 
 */
class ExecutorMtcWrapper implements Executor {
    final Executor executor;

    public ExecutorMtcWrapper(Executor executor) {
        this.executor = executor;
    }

    @Override
    public void execute(Runnable command) {
        executor.execute(MtContextRunnable.get(command));
    }
}
