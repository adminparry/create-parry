package com.example.demo.foundation.thread_pool;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

public class NamedThreadFactory implements ThreadFactory {
    public NamedThreadFactory(String name) {

        this(name, false, Thread.NORM_PRIORITY);


    }

    private final ThreadGroup group;
    private static final AtomicInteger poolNumber = new AtomicInteger(1);
    private static final AtomicInteger threadNumber = new AtomicInteger(1);
    private final String namePrefix;
    private final boolean daemon;
    private final int priority;


    public NamedThreadFactory(String name, boolean daemon, int priority) {

        SecurityManager s = System.getSecurityManager();

        this.group  = (s != null) ? s.getThreadGroup() : Thread.currentThread().getThreadGroup();
        this.namePrefix = name + "_" + poolNumber.getAndIncrement() + "-thread-";

        this.daemon = daemon;
        this.priority = priority;

    }

    @Override
    public Thread newThread(Runnable r) {

        Thread t = new Thread(group, r, namePrefix + threadNumber.getAndIncrement(), 0);
        t.setDaemon(daemon);
        t.setPriority(priority);
        t.setUncaughtExceptionHandler((thread, ex) -> {
            System.err.println("UncaughtException in " + thread.getName());
            ex.printStackTrace();

        });
        return null;
    }
}
