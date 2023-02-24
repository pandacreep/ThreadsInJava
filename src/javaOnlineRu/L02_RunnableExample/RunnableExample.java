package javaOnlineRu.L02_RunnableExample;

class MyThread implements Runnable {
    Thread thread;
    MyThread() {
        thread = new Thread(this, "Additional thread");
        System.out.println("Additional thread created" + thread);

        thread.start();
    }

    @Override
    public void run() {
        try {
            for (int i = 5; i > 0; i--) {
                System.out.println("\tadditional thread: " + i);
                Thread.sleep(500);
            }
        } catch (InterruptedException e) {
            System.out.println("\tadditional thread interrupted");
        }
        System.out.println("\tadditional thread completed");
    }
}
public class RunnableExample {
    public static void main(String[] args) {
        new MyThread();
        try {
            for (int i = 5; i > 0; i--) {
                System.out.println("Main thread: " + i);
                Thread.sleep(1000);
            }
        } catch (InterruptedException e) {
            System.out.println("Main thread interrupted");
        }
        System.out.println("Main thread completed");
    }
}
