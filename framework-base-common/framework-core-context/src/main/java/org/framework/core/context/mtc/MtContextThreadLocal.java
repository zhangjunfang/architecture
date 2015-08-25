package org.framework.core.context.mtc;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.WeakHashMap;

/**
 * {@link MtContextThreadLocal} can transmit context from the thread of submitting task to the thread of executing task.
 * <p/>
 * Note: this class extends {@link java.lang.InheritableThreadLocal},
 * so {@link org.framework.core.context.mtc.MtContextThreadLocal} first is a {@link java.lang.InheritableThreadLocal}.
 *
 * @author  ocean zhangjunfang0505@163.com
 * @see MtContextRunnable
 * @see MtContextCallable
 * @since 0.10.0
 */
public class MtContextThreadLocal<T> extends InheritableThreadLocal<T> {
    /**
     * Computes the context value for this multi-thread context variable
     * as a function of the source thread's value at the time the task
     * Object is created.  This method is called from {@link org.framework.core.context.mtc.MtContextRunnable} or
     * {@link org.framework.core.context.mtc.MtContextCallable} when it create, before the task is started.
     * <p/>
     * This method merely returns reference of its source thread value, and should be overridden
     * if a different behavior is desired.
     *
     * @since 1.0.0
     */
    protected T copy(T parentValue) {
        return parentValue;
    }

    /**
     * Callback method before task object({@link MtContextRunnable}/{@link MtContextCallable}) execute.
     * <p/>
     * Default behavior is do nothing, and should be overridden
     * if a different behavior is desired.
     * <p/>
     * Do not throw any exception, just ignored.
     *
     * @since 1.2.0
     */
    protected void beforeExecute() {
    }

    /**
     * Callback method after task object({@link MtContextRunnable}/{@link MtContextCallable}) execute.
     * <p/>
     * Default behavior is do nothing, and should be overridden
     * if a different behavior is desired.
     * <p/>
     * Do not throw any exception, just ignored.
     *
     * @since 1.2.0
     */
    protected void afterExecute() {
    }

    @Override
    public final T get() {
        T value = super.get();
        if (null != value) {
            addMtContextThreadLocal();
        }
        return value;
    }

    @Override
    public final void set(T value) {
        super.set(value);
        if (null == value) { // may set null to remove value
            removeMtContextThreadLocal();
        } else {
            addMtContextThreadLocal();
        }
    }

    @Override
    public final void remove() {
        removeMtContextThreadLocal();
        super.remove();
    }

    private void superRemove() {
        super.remove();
    }

    T copyMtContextValue() {
        return copy(get());
    }

    static ThreadLocal<Map<MtContextThreadLocal<?>, ?>> holder =
            new ThreadLocal<Map<MtContextThreadLocal<?>, ?>>() {
                @Override
                protected Map<MtContextThreadLocal<?>, ?> initialValue() {
                    return new WeakHashMap<MtContextThreadLocal<?>, Object>();
                }
            };

    void addMtContextThreadLocal() {
        if (!holder.get().containsKey(this)) {
            holder.get().put(this, null); // WeakHashMap supports null value.
        }
    }

    void removeMtContextThreadLocal() {
        holder.get().remove(this);
    }

    static Map<MtContextThreadLocal<?>, Object> copy() {
        Map<MtContextThreadLocal<?>, Object> copy = new HashMap<MtContextThreadLocal<?>, Object>();
        for (MtContextThreadLocal<?> threadLocal : holder.get().keySet()) {
            copy.put(threadLocal, threadLocal.copyMtContextValue());
        }
        return copy;
    }

    static Map<MtContextThreadLocal<?>, Object> backupAndSet(Map<MtContextThreadLocal<?>, Object> copied) {
        Map<MtContextThreadLocal<?>, Object> backup = new HashMap<MtContextThreadLocal<?>, Object>();
        for (Iterator<? extends Map.Entry<MtContextThreadLocal<?>, ?>> iterator = holder.get().entrySet().iterator();
             iterator.hasNext(); ) {
            Map.Entry<MtContextThreadLocal<?>, ?> next = iterator.next();
            MtContextThreadLocal<?> threadLocal = next.getKey();
            // backup 
            backup.put(threadLocal, threadLocal.get());
            // clean extra MtContext in destination thread
            if (!copied.containsKey(threadLocal)) {
                iterator.remove();
                threadLocal.superRemove();
            }
        }
        setMtContexts(copied, true);
        return backup;
    }

    static void restore(Map<MtContextThreadLocal<?>, Object> backup) {
        for (Iterator<? extends Map.Entry<MtContextThreadLocal<?>, ?>> iterator = holder.get().entrySet().iterator();
             iterator.hasNext(); ) {
            Map.Entry<MtContextThreadLocal<?>, ?> next = iterator.next();
            MtContextThreadLocal<?> threadLocal = next.getKey();
            // clean extra MtContext
            if (!backup.containsKey(threadLocal)) {
                iterator.remove();
                threadLocal.superRemove();
            }
        }
        setMtContexts(backup, false);
    }

    static void setMtContexts(Map<MtContextThreadLocal<?>, Object> set, boolean isStore) {
        for (Map.Entry<MtContextThreadLocal<?>, Object> entry : set.entrySet()) {
            @SuppressWarnings("unchecked")
            MtContextThreadLocal<Object> threadLocal = (MtContextThreadLocal<Object>) entry.getKey();

            if (!isStore) {
                try {
                    threadLocal.afterExecute();
                } catch (Throwable t) {
                    t.printStackTrace();
                }
            }
            threadLocal.set(entry.getValue());
            if (isStore) {
                try {
                    threadLocal.beforeExecute();
                } catch (Throwable t) {
                    t.printStackTrace();
                }
            }
        }
    }
}
