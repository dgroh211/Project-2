import java.math.BigInteger;
import java.util.ArrayList;

public class TestStatsLibrary {

    public static void main(String[] args) {
        StatsLibrary test = new StatsLibrary();
        
        //Problem: A deck of cards contains 20 cards. 6 red cards and 14 black cards. 5 are drawn randomly without replacement. What is the probability that 4 red cards will be drawn?
        System.out.println("The probability of picking 4 red cards being chosen using Hypergeometric distribution is: " + test.findHypergeometricDistribution(BigInteger.valueOf(5), BigInteger.valueOf(20), BigInteger.valueOf(6), BigInteger.valueOf(4)) + "%");
  
        //Problem: A dataset has a minimum value of 20, a maximum value of 80, a mean of 50,, and standard deviation of 10.
        System.out.println("The Chebyshev's Theorem says that the % of data that falls within 10 is: " + test.findChebyshevsTheorem(20, 80, 50, 10) + "%");

        //Problem: A call center receives an average of 20 calls per hour. What is the probability that the call center will have 25 calls this hour?
        System.out.println("Using the Poisson Distribution, we can infer that the probability is: " + test.findPoissonDistribution(20, BigInteger.valueOf(25)) + "%");
    }
}