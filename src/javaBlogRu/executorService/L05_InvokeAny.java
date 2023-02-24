package javaBlogRu.executorService;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class L05_InvokeAny {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        Set<Callable<String>> callables = new HashSet<Callable<String>>();
        callables.add(new Callable<String>(){
            public String call() throws Exception{
                var note = "task A";
                System.out.println(note);
                return note;
            }
        });
        callables.add(new Callable<String>(){
            public String call() throws Exception{
                var note = "task B";
                System.out.println(note);
                return note;
            }
        });
        callables.add(new Callable<String>(){
            public String call() throws Exception{
                var note = "task C";
                System.out.println(note);
                return note;
            }
        });
        callables.add(new Callable<String>(){
            public String call() throws Exception{
                var note = "task D";
                System.out.println(note);
                return note;
            }
        });

        String result = executorService.invokeAny(callables);
        System.out.println("result = " + result);
        executorService.shutdown();
        System.out.println("summary: InvokeAny returns one of called objects result");
    }
}
