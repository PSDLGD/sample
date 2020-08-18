package com.beinglee.concurrent.future;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * @author Luz
 * @date 2020/8/17
 */
public class FutureSample {

    private static final ThreadPoolExecutor executor = new ThreadPoolExecutor(2, 5, 60, TimeUnit.SECONDS, new LinkedBlockingQueue<>(), r -> {
        Thread t = new Thread(r, "FutureSampleThread");
        t.setDaemon(false);
        return t;
    });

    public static void main(String[] args) throws ExecutionException, InterruptedException, TimeoutException {
//        t1();
        t2();
        executor.shutdown();
    }

    private static void t1() throws ExecutionException, InterruptedException, TimeoutException {
        Future<String> future = executor.submit(() -> {
            System.out.println(Thread.currentThread().getName() + ":来了老弟");
            TimeUnit.SECONDS.sleep(1);
            return "哈哈哈";
        });
        System.out.println("Future的内容:" + future.get(2, TimeUnit.SECONDS));
    }

    private static void t2() throws ExecutionException, InterruptedException {
        List<String> strings = new ArrayList<>();
        Future<List<String>> future = executor.submit(() -> {
            strings.add(Thread.currentThread().getName() + ":来了老弟");
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, strings);
        System.out.println(future.get());
    }
}
