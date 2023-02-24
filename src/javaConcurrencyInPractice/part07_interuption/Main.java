package javaConcurrencyInPractice.part07_interuption;

import java.math.BigInteger;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        List<BigInteger> primes = PrimeGenerator.aSecondOfPrimes();
        Collections.sort(primes, Comparator.reverseOrder());
        for (BigInteger prime : primes) {
            System.out.println(prime);
        }

        System.out.println("primes number: " + primes.size());
    }
}
