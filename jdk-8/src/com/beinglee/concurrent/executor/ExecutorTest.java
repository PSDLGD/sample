package com.beinglee.concurrent.executor;

import java.util.concurrent.*;

/**
 * @author Luz
 * @date 2020/8/17
 */
public class ExecutorTest {

    private static final ThreadPoolExecutor executor = new ThreadPoolExecutor(2, 5, 60, TimeUnit.SECONDS, new LinkedBlockingQueue<>(),
            r -> {
                Thread t = new Thread(r, "FutureSampleThread");
//                t.setUncaughtExceptionHandler((t1, e) -> System.out.println(t1.getName() + ",出错啦," + e));
                t.setDaemon(false);
                return t;
            });

    private static void sayHi(String name) {
        String printStr = "【thread-name:" + Thread.currentThread().getName() + ",执行方式:" + name + "】";
        System.out.println(printStr);
        throw new RuntimeException(printStr + ",我异常啦!哈哈哈!");
    }

    /**
     * 执行execute会抛出堆栈异常
     * 执行submit不会抛出堆栈异常 submit异常包裹在Future里，执行get方法时，才会打印出堆栈异常。
     */
    public static void main(String[] args) throws InterruptedException {
        executor.execute(() -> sayHi("execute"));
        TimeUnit.MILLISECONDS.sleep(100);

        // 不会抛出异常
        executor.submit(() -> sayHi("submit"));

        Future<?> future = executor.submit(() -> sayHi("submit"));
        try {
            future.get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        executor.shutdown();
    }

}
