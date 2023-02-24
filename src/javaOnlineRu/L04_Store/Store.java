package javaOnlineRu.L04_Store;

public class Store {
    private int counter = 0;

    public synchronized void get() {
        while (counter < 1) {
            try {
                wait();
            } catch (InterruptedException ignored) {}
        }
        counter--;
        System.out.println("-1 : item taken");
        System.out.println("\tnumber of items at store: " + counter);
        notify();
    }

    public synchronized void put() {
        while (counter >= 3) {
            try {
                wait();
            } catch (InterruptedException ignored) {}
        }
        counter++;
        System.out.println("+1 : item added");
        System.out.println("\tnumber of items at store: " + counter);
        notify();
    }
}
