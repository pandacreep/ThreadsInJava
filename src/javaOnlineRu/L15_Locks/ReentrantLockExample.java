package javaOnlineRu.L15_Locks;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ReentrantLockExample {
    String      resource = "Hello, World!";
    SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss  ");

    Lock lock;

    final  int  TIME_WAIT  = 5_000;
    final  int  TIME_SLEEP = 7_000;

    ReentrantLockExample() {
        lock = new ReentrantLock();
        Thread thread1;
        Thread thread2;
        thread1 = new Thread(new LockClass("first","The first thread"));
        thread2 = new Thread(new LockClass("second","The second thread"));
        thread1.start();
        thread2.start();

        printMessage(null);

        while (thread1.isAlive() || thread2.isAlive()) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("\nCompleting example work");
        System.exit(0);
    }
    //-----------------------------------------------------
    void printMessage(final String msg) {
        String text = sdf.format(new Date());
        if (msg == null)
            text += resource;
        else
            text += msg;
        System.out.println(text);
    }
    //-----------------------------------------------------
    class LockClass implements Runnable {
        String name;
        String text;
        public LockClass(String name, String text) {
            this.name = name;
            this.text = text;
        }

        @Override
        public void run() {
            boolean locked = false;
            try {
                locked = lock.tryLock(TIME_WAIT,TimeUnit.MILLISECONDS);
                if (locked) {
                    resource = text;
                    printMessage(null);
                }
                Thread.sleep(TIME_SLEEP);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally{
                // Убираем блокировку
                String text = name + " : completed work";
                printMessage(text);
                if (locked)
                    lock.unlock();
            }
        }
    }
    //-----------------------------------------------------
    public static void main(String[] args) {
        new ReentrantLockExample();
    }
}