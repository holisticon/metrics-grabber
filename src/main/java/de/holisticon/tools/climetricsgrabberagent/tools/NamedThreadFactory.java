package de.holisticon.tools.climetricsgrabberagent.tools;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Thread factory for local metric agent thread pools.
 * @author Tobias Gindler, Holisticon AG
 */
public class NamedThreadFactory implements ThreadFactory{

    private final ThreadFactory backingThreadFactory = Executors.defaultThreadFactory();

    private boolean daemon;

    private String threadNamePrefix;

    private AtomicLong increment = new AtomicLong();

    public NamedThreadFactory(String threadNamePrefix, boolean daemon) {
        this.threadNamePrefix = threadNamePrefix;
        this.daemon = daemon;
    }


    @Override
    public Thread newThread(Runnable r) {
        Thread thread = backingThreadFactory.newThread(r);
        thread.setName(threadNamePrefix + increment.incrementAndGet());
        thread.setDaemon(daemon);
        return thread;
    }

}
