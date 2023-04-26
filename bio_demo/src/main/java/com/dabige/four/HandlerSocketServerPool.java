package com.dabige.four;

import com.sun.corba.se.spi.orbutil.threadpool.WorkQueue;

import java.util.concurrent.*;

public class HandlerSocketServerPool {
    private ExecutorService executorService;

    public HandlerSocketServerPool(int maxThreadNum, int queueSize) {
        this.executorService = new ThreadPoolExecutor(3,
                maxThreadNum,
                120,
                TimeUnit.SECONDS,
                new ArrayBlockingQueue<Runnable>(queueSize));
    }

    public void execute(Runnable runnable) {
        this.executorService.execute(runnable);
    }
}

