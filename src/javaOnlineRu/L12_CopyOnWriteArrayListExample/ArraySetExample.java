package javaOnlineRu.L12_CopyOnWriteArrayListExample;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArraySet;

public class ArraySetExample
{
    List<User> list  ;
    CopyOnWriteArraySet<User> cowSet;

    public ArraySetExample() {
        list = new ArrayList<User>();
        list.add(new User ("Prohor "));
        list.add(new User ("Georgiy"));
        list.add(new User ("Mikhail" ));

        cowSet = new CopyOnWriteArraySet<User>(list);

        System.out.println("Cycle with changes");

        Iterator<User> itr = cowSet.iterator();
        int cnt = 0;
        while (itr.hasNext()) {
            User user = itr.next();
            System.out.println("  " + user.name);
            if (++cnt == 2) {
                cowSet.add(new User("Pavel"));
                user.name += " Ivanovich";
            }
        }

        System.out.println("\nCycle with changes");
        itr = cowSet.iterator();
        while (itr.hasNext()) {
            User user = itr.next();
            System.out.println("  " + user.name);
        }
    }

    class User {
        private String name;
        public User(String name) {
            this.name = name;
        }
    }

    public static void main(String args[]) {
        new ArraySetExample();
    }
}
