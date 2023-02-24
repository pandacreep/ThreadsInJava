package javaOnlineRu.L05_Semaphore;

import java.util.concurrent.Semaphore;

public class SemaphoreExample
{
    public static final int COUNT_CONTROL_PLACES = 5;
    private static final int COUNT_RIDERS = 7;
    // Флаги мест контроля
    public static boolean[] CONTROL_PLACES = null;

    // Семафор
    public static Semaphore SEMAPHORE = null;

    public static void main(String[] args)
            throws InterruptedException
    {
        CONTROL_PLACES = new boolean[COUNT_CONTROL_PLACES];
        for (int i = 0; i < COUNT_CONTROL_PLACES; i++)
            CONTROL_PLACES[i] = true;
        SEMAPHORE = new Semaphore(CONTROL_PLACES.length,true);

        for (int i = 1; i <= COUNT_RIDERS; i++) {
            new Thread(new Rider(i)).start();
            Thread.sleep(400);
        }
    }
}
