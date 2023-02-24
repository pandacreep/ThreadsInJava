package javaOnlineRu.L07_CyclicBarrierExample;

import java.util.concurrent.CyclicBarrier;

public class CyclicBarrierExample
{
    private static CyclicBarrier ferryBarrier;
    private static final int ferryBoat_size = 3;

    public static class FerryBoat implements Runnable
    {
        @Override
        public void run() {
            try {
                // Задержка на переправе
                System.out.println("\nCars loading...");
                Thread.sleep(500);
                System.out.println("Vessel carried cars\n");
            } catch (InterruptedException e) {}
        }
    }

    // Класс автомобиля
    public static class Car implements Runnable
    {
        private int carNumber;

        public Car(int carNumber) {
            this.carNumber = carNumber;
        }

        @Override
        public void run() {
            try {
                System.out.printf("Car reached barrier %d\n", carNumber);
                // Вызов метода await при подходе к
                // барьеру; поток блокируется в ожидании
                // прихода остальных потоков
                ferryBarrier.await();
                System.out.printf("Car %d continue movement\n", carNumber);
            } catch (Exception e) {}
        }
    }

    public static void main(String[] args) throws InterruptedException {
        ferryBarrier = new CyclicBarrier(ferryBoat_size,new FerryBoat());
        for (int i = 1; i < 10; i++) {
            new Thread(new Car(i)).start();
            Thread.sleep(400);
        }
    }
}
