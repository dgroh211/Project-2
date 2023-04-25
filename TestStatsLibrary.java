import java.math.BigInteger;
import java.util.ArrayList;

public class TestStatsLibrary {

    public static void main(String[] args) {
        StatsLibrary test = new StatsLibrary();
        
        
        System.out.println("The hypergeometric distribution : " + test.findHypergeometricDistribution(BigInteger.valueOf(5), BigInteger.valueOf(10), BigInteger.valueOf(3), BigInteger.valueOf(2)));
        System.out.println("The hypergeometric distribution : " + test.findHypergeometricDistribution(BigInteger.valueOf(4), BigInteger.valueOf(20), BigInteger.valueOf(6), BigInteger.valueOf(2)));
        
        ArrayList<Integer> inputNumbers = new ArrayList<Integer>();
        inputNumbers.add(3);
        inputNumbers.add(3);
        inputNumbers.add(3);
        inputNumbers.add(3);
        inputNumbers.add(3);

        ArrayList<Integer> inputNumbers2 = new ArrayList<Integer>();
        inputNumbers2.add(1);
        inputNumbers2.add(2);
        inputNumbers2.add(3);
        inputNumbers2.add(4);
        inputNumbers2.add(5);
        System.out.println("The Chebyshev's Theorem : " + test.findChebyshevsTheorem(inputNumbers, 2));
        System.out.println("The Chebyshev's Theorem : " + test.findChebyshevsTheorem(inputNumbers2, 3));
        
        System.out.println("The Poisson Distribution : " + test.findPoissonDistribution(2.5, BigInteger.valueOf(4)));
        System.out.println("The Poisson Distribution : " + test.findPoissonDistribution(5.0, BigInteger.valueOf(2)));
    }
}