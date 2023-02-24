package javaOnlineRu.L09_PhaserExample;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Phaser;

public class PhaserExample {
    private static Phaser PHASER;

    private static String OPEN  = "     door opening";
    private static String CLOSE = "     door closing";

    private static String WAIT  = " wait on station ";
    private static String ENTER = " enter the wagon";
    private static String EXIT  = " get out of the wagon";
    private static String SPACE = "     ";

    public static class Passenger implements Runnable {
        int id;
        int departure;
        int destination;

        public Passenger(int id, int departure, int destination) {
            this.id = id;
            this.departure = departure;
            this.destination = destination;
            System.out.println(this + WAIT + departure);
        }

        @Override
        public void run() {
            try {
                System.out.println(SPACE + this + ENTER);
                //-----------------------------------------------
                // Заявляем об участии и ждем станции назначения
                while (PHASER.getPhase() < destination)
                    PHASER.arriveAndAwaitAdvance();
                //----------------------------------------------
                Thread.sleep(500);
                System.out.println(SPACE + this + EXIT);
                // Отмена регистрации
                PHASER.arriveAndDeregister();
            } catch (InterruptedException e) {
            }
        }

        @Override
        public String toString() {
            return "Passenger " + id + " {" + departure + " -> " + destination + '}';
        }
    }

    public static void main(String[] args)  throws InterruptedException {
        // Регистрация объекта синхронизации
        PHASER = new Phaser(1);

        List<Passenger> passengers = new ArrayList<>();
        getPassengersList(passengers);

        // Фазы 0 и 6 - конечные станции
        // Фазы 1...5 - промежуточные станции
        for (int i = 0; i < 7; i++) {
            switch (i) {
                case 0:
                    System.out.println("Metro living the dock");
                    PHASER.arrive();
                    break;
                case 6:
                    System.out.println("Metro enter the dock");
                    PHASER.arriveAndDeregister();
                    break;
                default:
                    int currentStation = PHASER.getPhase();
                    System.out.println("Station " + currentStation);
                    // Проверка наличия пассажиров
                    // на станции
                    for (Passenger pass : passengers)
                        if (pass.departure == currentStation) {
                            // Регистрация участника
                            PHASER.register();
                            new Thread(pass).start();
                        }
                    System.out.println(OPEN);

                    // Phaser ожидает завершения фазы
                    // всеми участниками
                    PHASER.arriveAndAwaitAdvance();

                    System.out.println(CLOSE);
            }
        }
    }

    private static void getPassengersList(List<Passenger> passengers) {
        // Формирование массива пассажиров
        for (int i = 1; i < 5; i++) {
            if ((int) (Math.random() * 2) > 0)
                // Этот пассажир проезжает одну станцию
                passengers.add(new Passenger(10 + i,i,i + 1));
            if ((int) (Math.random() * 2) > 0) {
                // Этот пассажир едет до конечной
                Passenger p = new Passenger(20 + i, i, 5);
                passengers.add(p);
            }
        }
    }
}