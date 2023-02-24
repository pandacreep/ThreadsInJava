package javaOnlineRu.L01_ChickenEgg;

import java.util.Random;

// https://java-online.ru/java-thread.xhtml

class Egg extends Thread {
    @Override
    public void run() {
        for (int i = 0; i < 5; i++) {
            try {
                // Приостанавливаем поток
                sleep(ChickenEgg.getTimeSleep());
                System.out.println("Egg " + Thread.currentThread().getName());
            } catch (InterruptedException e) {
            }
        }
    }
}

public class ChickenEgg {
    public static int getTimeSleep() {
        final Random random = new Random();
        int tm = random.nextInt(1000);
        if (tm < 10)
            tm *= 100;
        else if (tm < 100)
            tm *= 10;
        return tm;
    }

    public static void main(String[] args) {
        Egg egg = new Egg();
        System.out.println("Starting bet - who became earlier, egg or chicken?");
        egg.start();
        for (int i = 0; i < 5; i++) {
            try {
                // Приостанавливаем поток
                Thread.sleep(ChickenEgg.getTimeSleep());
                System.out.println("Chicken " + Thread.currentThread().getName());
            } catch (InterruptedException e) {
            }
        }
        if (egg.isAlive()) {
            try {
                egg.join();
            } catch (InterruptedException e){}

            System.out.println("Egg appeared first !!!");
        } else {
            //если оппонент уже закончил высказываться
            System.out.println("Chicken appeared first !!!");
        }
        System.out.println("Bet finished");
    }
}
