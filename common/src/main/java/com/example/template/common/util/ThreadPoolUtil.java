package com.example.template.common.util;

import cn.hutool.core.thread.ThreadUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.*;

/**
 * Title:
 *
 * @author trifolium
 * @version 1.0
 */
@Slf4j
public class ThreadPoolUtil {

    private static final int CORE_THREAD_NUM = Runtime.getRuntime().availableProcessors();
    // 最大为100个线程
    private static final int MAXIMUM_POOL_SIZE = Math.min(CORE_THREAD_NUM * 24, 100);

    private static final String THREAD_POOL_GROUP_NAME = "APP-CUSTOM-T-GROUP";
    private static final String THREAD_NAME_PREFIX = "APP-CUSTOM-T-POOL-";
    private static final ThreadTaskLinkedBlockingDeque<Runnable> BLOCKING_QUEUE = new ThreadTaskLinkedBlockingDeque<>();
    private static final ThreadPoolExecutor THREAD_POOL;

    static {
        // unbounded thread pool
        THREAD_POOL = new ThreadPoolExecutor(CORE_THREAD_NUM, MAXIMUM_POOL_SIZE,
                10L, TimeUnit.SECONDS, BLOCKING_QUEUE,
                ThreadUtil.newNamedThreadFactory(THREAD_NAME_PREFIX,
                        new ThreadGroup(ThreadUtil.currentThreadGroup(), THREAD_POOL_GROUP_NAME), false),
                (r, executor) -> {
                    System.out.println("线程池满,打印错误日志");
                    new ThreadPoolExecutor.AbortPolicy().rejectedExecution(r, executor);
                });
    }

    public static void addThread(Runnable runnable) {
        THREAD_POOL.execute(runnable);
    }

    public static Future<?> submit(Runnable runnable) {

        ThreadGroup threadGroup = Thread.currentThread().getThreadGroup();
        if (THREAD_POOL_GROUP_NAME.equals(threadGroup.getName())) {
            log.warn("Don't submit Future tasks and get results in the current thread’s thread pool," +
                    " as it may cause deadlock.");
        }
        return THREAD_POOL.submit(runnable);
    }

    public static <T> Future<T> submit(Callable<T> callable) {

        ThreadGroup threadGroup = Thread.currentThread().getThreadGroup();
        if (THREAD_POOL_GROUP_NAME.equals(threadGroup.getName())) {
            log.warn("Don't submit Future tasks and get results in the current thread’s thread pool," +
                    " as it may cause deadlock.");
        }
        return THREAD_POOL.submit(callable);
    }

    public static void shutDownNow() {

        if (!THREAD_POOL.isShutdown()) {
            log.info("Application thread pool is shutdown.");
            THREAD_POOL.shutdownNow();
        }
    }

    /**
     * 队列积压长度
     */
    public static int queueOverstockingLength() {

        return BLOCKING_QUEUE.size();
    }

    /**
     * 获取当前线程池对象
     */
    public static ExecutorService getExecutor() {

        return THREAD_POOL;
    }

    private ThreadPoolUtil() {

    }

    private static class ThreadTaskLinkedBlockingDeque<E> extends LinkedBlockingDeque<E> {
        @Override
        public boolean offer(E e) {
            int activeThreadNum = THREAD_POOL.getActiveCount();
            if (activeThreadNum < MAXIMUM_POOL_SIZE) {
                return false;
            }

            return offerLast(e);
        }
    }

}
