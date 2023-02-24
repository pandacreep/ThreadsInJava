package javaOnlineRu.L08_Exchanger;

import java.util.concurrent.Exchanger;

public class ExchangerExample
{
    // Обменник почтовыми письмами
    private static Exchanger<Letter> EXCHANGER;

    static String msg1 = "Postman %s got letters : %s, %s\n";
    static String msg2 = "Postman %s started from %s to %s\n";
    static String msg3 = "Postman %s arrived to point X\n";
    static String msg4 = "Postman %s received leters for %s\n";
    static String msg5 = "Postman %s brought to %s : %s, %s\n";

    public static class Postman implements Runnable {
        private String   id;
        private String   departure;
        private String   destination;
        private Letter[] letters;

        public Postman(String id, String departure,
                       String destination,
                       Letter[] letters) {
            this.id          = id;
            this.departure   = departure;
            this.destination = destination;
            this.letters     = letters;
        }

        @Override
        public void run() {
            try {
                System.out.printf(msg1, id, letters[0], letters[1]);
                System.out.printf(msg2, id, departure, destination);
                Thread.sleep((long) (Math.random() * 5000) + 5000);
                System.out.printf(msg3, id);
                // Самоблокировка потока для
                // обмена письмами
                letters[1] = EXCHANGER.exchange(letters[1]);
                // Обмен письмами
                System.out.printf(msg4, id, destination);
                Thread.sleep(1000 + (long) (Math.random() * 5000));
                System.out.printf(msg5, id, destination, letters[0], letters[1]);
            } catch (InterruptedException ignored) {}
        }
    }

    public static class Letter {
        private String address;

        public Letter(final String address) {
            this.address = address;
        }

        public String toString()
        {
            return address;
        }
    }

    public static void main(String[] args) throws InterruptedException {
        EXCHANGER = new Exchanger<Letter>();
        // Формирование отправлений
        Letter[] posts1 = new Letter[2];
        Letter[] posts2 = new Letter[2];

        posts1[0] = new Letter("p.C - Petrov");
        posts1[1] = new Letter("p.D - Kissa");
        posts2[0] = new Letter("p.D - Ostap");
        posts2[1] = new Letter("p.C - Ivanov");
        // Отправление почтальонов
        new Thread(new Postman("a","A","C",posts1)).start();
        Thread.sleep(100);
        new Thread(new Postman("b","B","D",posts2)).start();
    }
}
