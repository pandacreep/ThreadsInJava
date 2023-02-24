package javaBlogRu.executorService;

import java.util.concurrent.*;

public class L04_SubmitCallable {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        Future future = executorService.submit(new Callable() {
            @Override
            public Object call() throws Exception {
                System.out.println("Asynchronous callable");
                return "Callable Result";
            }
        });
        System.out.println("returned result: " + future.get());
        System.out.println("summary: Submitting of Callable can returns an object");
    }
}
