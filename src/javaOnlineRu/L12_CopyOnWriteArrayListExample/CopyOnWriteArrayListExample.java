package javaOnlineRu.L12_CopyOnWriteArrayListExample;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class CopyOnWriteArrayListExample
{
    List<String> list;

    public CopyOnWriteArrayListExample() {
        List<String> lst = new ArrayList<String>();
        lst.add("Java");
        lst.add("J2EE");
        lst.add("J2SE");
        lst.add("Collection");
        lst.add("Concurrent");

        list = new CopyOnWriteArrayList<String>(lst);

        System.out.println("Cycle with changes");
        printCollection(true);
        System.out.println("\nCycle without changes");
        printCollection(false);

    }

    private void printCollection(boolean change) {
        Iterator<String> iterator = list.iterator();
        while(iterator.hasNext()){
            String element = iterator.next();
            System.out.printf("  %s %n", element);
            if (change) {
                if (element.equals("Collection")) {
                    list.add("New line");
                    list.remove(element);
                }
            }
        }
    }

    public static void main(String args[]) {
        new CopyOnWriteArrayListExample();
        System.exit(0);
    }
}