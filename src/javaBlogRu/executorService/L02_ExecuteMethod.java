package javaBlogRu.executorService;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

// Невозможно получить результат выполнения Runnable, для этого вам нужно использовать Callable.
public class L02_ExecuteMethod {
    public static void main(String[] args) {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(new Runnable(){
            public void run(){
                System.out.println("asynchronous task");
            }
        });
        executorService.shutdown();
    }
}
