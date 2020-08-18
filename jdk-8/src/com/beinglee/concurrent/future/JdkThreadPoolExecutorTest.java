package com.beinglee.concurrent.future;

import java.util.ArrayList;
import java.util.concurrent.*;

/**
 * @author Luz
 * @date 2020/8/17
 */
public class JdkThreadPoolExecutorTest {

    public static void main(String[] args) throws Exception {
        ExecutorService executorService = Executors.newCachedThreadPool();
//        testFuture(executorService);
        testCompletionService(executorService);
        executorService.shutdown();
    }

    private static void testFuture(ExecutorService executorService) throws InterruptedException, java.util.concurrent.ExecutionException {
        ArrayList<Future<Integer>> list = new ArrayList<>();
        Future<Integer> future_15 = executorService.submit(() -> {
            TimeUnit.SECONDS.sleep(15);
            System.out.println("执行时长为15s的执行完成。");
            return 15;
        });
        list.add(future_15);

        Future<Integer> future_5 = executorService.submit(() -> {
            TimeUnit.SECONDS.sleep(5);
            System.out.println("执行时长为5s的执行完成。");
            return 5;
        });
        list.add(future_5);

        Future<Integer> future_10 = executorService.submit(() -> {
            TimeUnit.SECONDS.sleep(10);
            System.out.println("执行时长为10s的执行完成。");
            return 10;
        });
        list.add(future_10);

        System.out.println("开始准备获取结果");
        for (Future<Integer> future : list) {
            System.out.println("future.get() = " + future.get());
        }
    }

    private static void testCompletionService(ExecutorService executorService) throws InterruptedException, ExecutionException {
        CompletionService<Integer> completionService = new ExecutorCompletionService<>(executorService);
        completionService.submit(() -> {
            TimeUnit.SECONDS.sleep(15);
            System.out.println("执行时长为15s的执行完成。");
            return 15;
        });
        completionService.submit(() -> {
            TimeUnit.SECONDS.sleep(5);
            System.out.println("执行时长为5s的执行完成。");
            return 5;
        });
        completionService.submit(() -> {
            TimeUnit.SECONDS.sleep(10);
            System.out.println("执行时长为10s的执行完成。");
            return 10;
        });
        for (int i = 0; i < 3; i++) {
            Integer ret = completionService.take().get();
            System.out.println(ret);
        }
    }
}
