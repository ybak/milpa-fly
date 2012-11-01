package com.corntree.milpa.fly.api.socket;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.corntree.milpa.fly.common.NamedThreadFactory;

public class ExecutorManager {

    public static ExecutorService newCachedThreadPool(String poolName) {
        return Executors.newCachedThreadPool(new NamedThreadFactory(poolName));
    }

    public static ExecutorService newFixedThreadPool(int nThreads, String poolName) {
        return Executors.newFixedThreadPool(nThreads, new NamedThreadFactory(poolName));
    }
}
