package javaBlogRu.executorService;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.*;

public class L06_InvokeAll {
    public static void main(String[] args) throws InterruptedException, ExecutionException {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        Set<Callable<String>> callables = new HashSet<Callable<String>>();
        callables.add(new Callable<String>(){
            public String call() throws Exception{
                var note = "Task A";
                System.out.println(note);
                return note;
            }
        });
        callables.add(new Callable<String>(){
            public String call() throws Exception{
                var note = "Task B";
                System.out.println(note);
                return note;
            }
        });
        callables.add(new Callable<String>(){
            public String call() throws Exception{
                var note = "Task C";
                System.out.println(note);
                return note;
            }
        });
        List<Future<String>> futures = executorService.invokeAll(callables);
        for(Future<String> future: futures){
            System.out.println(" future.get = " + future.get());
        }
        executorService.shutdown();
        System.out.println("summary: InvokeAll returns list of all objects results");
    }
}
