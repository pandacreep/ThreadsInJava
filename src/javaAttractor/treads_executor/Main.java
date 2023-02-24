package javaAttractor.treads_executor;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

public class Main {
    public static void main(String[] args) {
        ExecutorService pool = Executors.newCachedThreadPool();

        int taskCount = 8;
        submitTasksInto(pool, taskCount);
        System.out.println("-------------------");
        // Initiates an orderly shutdown in which previously submitted tasks are executed, but no new tasks will be accepted
        pool.shutdown();
        measure(pool);
    }

    private static void submitTasksInto(ExecutorService pool, int taskCount) {
        System.out.println("Creating tasks");
        IntStream.rangeClosed(1, taskCount)
                .mapToObj(i -> makeTask(i))
                .forEach(pool::submit);
    }

    private static Runnable makeTask(int taskId) {
        int temp = new Random().nextInt(20_000) + 10_000;
        int taskTime = (int) TimeUnit.MILLISECONDS
                .toSeconds(temp);
        return () -> heavyTask(taskId, taskTime);
    }

    private static void heavyTask(int taskId, int taskTime) {
        String msg = String.format("Task %s planned for %s seconds", taskId, taskTime);
        System.out.println(msg);
        try {
            Thread.sleep(taskTime * 1000);
            System.out.printf("Task %s completed," +
                    " for %s seconds%n", taskId, taskTime);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static void measure(ExecutorService pool) {
        long start = System.nanoTime();

        System.out.println("Tasks running...");
        try {
            // Blocks until all tasks have completed execution after a shutdown request, or the timeout occurs, or the current thread is interrupted, whichever happens first
            // этот метод, обычно, используется для ожидания
            // завершения всех накопившихся задач,
            // но мы его используем для блокирования основного
            // потока и тем самым замеряем время выполнения
            // наших задач
            pool.awaitTermination(600, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        long delta = TimeUnit.NANOSECONDS
                .toMillis(System.nanoTime() - start);
        System.out.printf("All tasks completed for %s millis %n", delta);
    }
}
