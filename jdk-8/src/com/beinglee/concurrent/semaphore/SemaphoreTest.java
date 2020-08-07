package com.beinglee.concurrent.semaphore;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/**
 * @author Luz
 * @date 2020/8/7
 */
public class SemaphoreTest {

    private static final int THREAD_COUNT = 10;

    private static ExecutorService executor = Executors.newFixedThreadPool(THREAD_COUNT);

    private static final Semaphore semaphore = new Semaphore(2);

    public static void main(String[] args) {
        for (int i = 0; i < THREAD_COUNT; i++) {
            executor.execute(
                    () -> {
                        try {
                            semaphore.acquire();
                            TimeUnit.SECONDS.sleep(1);
                            System.out.println("acquire success!");
                        } catch (InterruptedException e) {

                        } finally {
                            semaphore.release();
                        }
                    });
        }
        executor.shutdown();
    }
}
