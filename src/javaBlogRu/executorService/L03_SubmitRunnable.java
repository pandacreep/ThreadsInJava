package javaBlogRu.executorService;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class L03_SubmitRunnable {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        Future future = executorService.submit(new Runnable(){
            public void run(){
                System.out.println("asynchronous task");
            }
        });
        Object o = future.get();

        //returns null if the task is finished correctly.
        System.out.println("returned object: " + o);
        System.out.println("summary: Submitting of Runnable does not return anything");
    }
}
