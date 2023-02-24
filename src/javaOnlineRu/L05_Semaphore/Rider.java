package javaOnlineRu.L05_Semaphore;

import static javaOnlineRu.L05_Semaphore.SemaphoreExample.*;

public class Rider implements Runnable
{
    private int ruderNum;

    public Rider(int ruderNum)  { this.ruderNum = ruderNum; }

    @Override
    public void run() {
        System.out.printf("Rider %d reached control area\n", ruderNum);
        try {
            SEMAPHORE.acquire();
            System.out.printf("\tRider %d is checking available controller\n", ruderNum);
            int controlNum = -1;
            synchronized (CONTROL_PLACES){
                for (int i = 0;
                     i < COUNT_CONTROL_PLACES; i++)
                    if (CONTROL_PLACES[i]) {
                        CONTROL_PLACES[i] = false;
                        controlNum = i;
                        System.out.printf("\t\tRider %d found free controller %d.\n", ruderNum, i);
                        break;
                    }
            }

            Thread.sleep((int)
                    (Math.random() * 10+1)*1000);

            synchronized (CONTROL_PLACES) {
                CONTROL_PLACES[controlNum] = true;
            }

            SEMAPHORE.release();
            System.out.printf("Rider %d completed check\n", ruderNum);
        } catch (InterruptedException ignored) {}
    }
}
