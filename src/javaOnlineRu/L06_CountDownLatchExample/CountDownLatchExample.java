package javaOnlineRu.L06_CountDownLatchExample;

import java.util.concurrent.CountDownLatch;

public class CountDownLatchExample
{
    private static final int RIDERS_COUNT = 5;
    private static CountDownLatch LATCH;
    private static final int trackLength = 3_000;

    public static class Rider implements Runnable
    {
        private int riderNumber;
        private int riderSpeed ;

        public Rider(int riderNumber, int riderSpeed)
        {
            this.riderNumber = riderNumber;
            this.riderSpeed  = riderSpeed;
        }

        @Override
        public void run() {
            try {
                System.out.printf("Rider %d at start position\n", riderNumber);
                LATCH.countDown();
                LATCH.await();
                Thread.sleep(trackLength / riderSpeed * 10);
                System.out.printf("Rider %d finished\n", riderNumber);
            } catch (InterruptedException ignored) {}
        }
    }
    public static Rider createRider(final int number)
    {
        return new Rider(number, (int) (Math.random() * 10 + 5));
    }

    public static void main(String[] args)
            throws InterruptedException
    {
        LATCH = new CountDownLatch(RIDERS_COUNT + 3);
        for (int i = 1; i <= RIDERS_COUNT; i++) {
            new Thread (createRider(i)).start();
            Thread.sleep(1_000);
        }

        while (LATCH.getCount() > 3)
            Thread.sleep(100);

        Thread.sleep(1000);
        System.out.println("One!");
        LATCH.countDown();  // Уменьшаем счетчик на 1
        Thread.sleep(1000);
        System.out.println("Two!");
        LATCH.countDown(); // Уменьшаем счетчик на 1
        Thread.sleep(1000);
        System.out.println("Three! Go!");
        LATCH.countDown(); // Уменьшаем счетчик на 1
    }
}
