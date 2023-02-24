package javaOnlineRu.L10_SequenceGeneratorExample;

import java.math.BigInteger;
import java.util.concurrent.atomic.AtomicReference;

public class SequenceGenerator
{
    private static BigInteger MULTIPLIER;
    private AtomicReference<BigInteger> element;

    public SequenceGenerator() {
        if (MULTIPLIER == null) {
            MULTIPLIER = BigInteger.valueOf(2);
        }
        element = new AtomicReference<BigInteger>(BigInteger.ONE);
    }

    public BigInteger next() {
        BigInteger value;
        BigInteger next;
        do {
            value = element.get();
//            next = value.multiply(MULTIPLIER);
            next = value.add(BigInteger.ONE);
        } while (!element.compareAndSet(value, next));
        return value;
    }
}
