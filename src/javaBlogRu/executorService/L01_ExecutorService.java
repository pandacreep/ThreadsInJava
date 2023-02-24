package javaBlogRu.executorService;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

// https://java-blog.ru/osnovy/executorservice-java

public class L01_ExecutorService {
    public static void main(String[] args) {
        System.out.println("Inside : " + Thread.currentThread().getName());

        System.out.println("creating ExecutorService");
        ExecutorService executorService = Executors.newSingleThreadExecutor();

        System.out.println("creating a runnable");
        Runnable runnable = () -> {
            System.out.println("inside: " + Thread.currentThread().getName());
        };

        System.out.println("submit the task specified by the runnable to the executorService");
        executorService.submit(runnable);
        executorService.close();
    }
}
