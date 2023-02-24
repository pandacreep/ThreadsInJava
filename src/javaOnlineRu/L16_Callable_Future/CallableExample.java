package javaOnlineRu.L16_Callable_Future;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.*;

public class CallableExample {

    public CallableExample() {
        ExecutorService executor;
        executor = Executors.newFixedThreadPool(3);
        List<Future<String>> futures = new ArrayList<Future<String>>();

        Callable<String> callable = new CallableClass();

        for (int i = 0; i < 3; i++){
            /*
             * Стартуем возвращаюший результат исполнения
             * в виде объекта Future поток
             */
            Future<String> future;
            future = executor.submit(callable);
            /*
             * Добавляем объект Future в список для
             * отображения результат выполнения (получение
             * наименования потока)
             */
            futures.add(future);
        }
        SimpleDateFormat sdf;
        sdf = new SimpleDateFormat("HH:mm:ss  ");
        for (Future<String> future : futures){
            try {
                // Выводим в консоль полученное значение
                String text = sdf.format(new Date()) + future.get();
                System.out.println(text);
            } catch (InterruptedException |
                     ExecutionException e) {}
        }
        // Останавливаем пул потоков
        executor.shutdown();
    }

    //-----------------------------------------------------
    // Класс, реализующий интерфейс Callable
    class CallableClass implements Callable<String> {
        @Override
        public String call() throws Exception {
            Thread.sleep(1000);
            // наименование потока, выполняющего
            // callable задачу
            return "returned value:" + Thread.currentThread().getName();
        }
    }

    //-----------------------------------------------------
    public static void main(String args[]) {
        new CallableExample();
    }
}