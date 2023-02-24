package javaOnlineRu.L10_SequenceGeneratorExample;

import java.util.ArrayList;
import java.util.List;

public class SequenceGeneratorExample
{
    public static void main(String[] args) {
        SequenceGenerator sg = new SequenceGenerator();
        List<Sequence> sequences = new ArrayList<Sequence>();
        for (int i = 0; i < 10; i++) {
            Sequence seq = new Sequence(i + 1, 3, sg);
            sequences.add(seq);
        }
        System.out.println("\nCalculation of sequences\n");

        int summa;
        do {
            summa = 0;
            for (int i = 0; i < sequences.size(); i++) {
                if (!sequences.get(i).thread.isAlive()) {
                    sequences.get(i).printSequence();
                    summa++;
                }
            }
            try {
                Thread.sleep(100);
            } catch (InterruptedException ignored) {}
        } while (summa < sequences.size());

        System.out.println("\n\nWork of threads - completed");
        System.exit(0);
    }
}