package javaOnlineRu.L04_Store;

public class TradeThreadDaemon {
    public static void main(String[] args) {
        Store store = new Store();
        Producer producer = new Producer(store);
        Consumer consumer = new Consumer(store);

        Thread tp = new Thread(producer);
        Thread tc = new Thread(consumer);

        tp.setDaemon(true);
        tc.setDaemon(true);

        tp.start();
        tc.start();

        try {
            Thread.sleep(100);
        } catch (InterruptedException ignored) {}

        System.out.println("\nMain thread finished\n");
        System.exit(0);
    }
}
