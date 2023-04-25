import java.util.ArrayList;
import java.util.Collections;
import java.lang.Math.*;
import java.math.BigInteger;
/**
 * This is the StatsLibrary for Project 2 of Probability and Applied Stats.
 *
 * Dillon Groh
 */
public class StatsLibrary
{
   public double findMean(ArrayList<Integer> inputNumbers) {
       //Initialize sum
       double sum = 0;
       
       //Add all the numbers in the ArrayList together
       for (int singleElement : inputNumbers) {
           sum += singleElement;
       }
       
       //Divide the sum of the numbers by the amount of numbers in the ArrayList
       double result = sum / inputNumbers.size();
       
       //Return the mean
       return result;
   }
   
   public double findMedian(ArrayList<Integer> inputNumbers) {
       //This sorts the ArrayList from least to greatest
       // https://www.geeksforgeeks.org/collections-in-java-2/ was used to help figure out how to sort the arraylist
       Collections.sort(inputNumbers);
       
       //Middle is a variable to find the middle of the ArrayList. When dividing by two, you get the right most part of the middle of the list.
       int middle = inputNumbers.size() / 2;
       
       //https://beginnersbook.com/2014/02/java-program-to-check-even-or-odd-number/ was used in order to see if the array was odd or even
       //If the size of the ArrayList is odd then it runs the if loop and the median is found to be the middle number in the list
       if (inputNumbers.size() % 2 == 1) {
           //This is the middle of the list and returns the median
           return inputNumbers.get(middle);
       }
       //If the size of the ArrayList is even then the else runs
       else {
           //The first number that will be used is the left middle number and this is added to the right middle number. These are divided by 2 to get the median. This value is returned as the median.
           return (inputNumbers.get(middle - 1) + inputNumbers.get(middle))/2;
       }
   }
   
   //This method bugged me for a while because I was never able to figure out how to implement it if there are duplicate modes.
   //I left some of my failed attempt in the comments of the method. I feel like I was on the right track and
   //Wanted to delete the previous mode added to the arraylist if it was smaller than one that came after it, but never was able to figure it out.
   public double findMode(ArrayList<Integer> inputNumbers) {
        //Initializes the count and mode variables
        double count = 0;
        double mode = 0;
        //ArrayList mode = new ArrayList(); failed duplicate attempt
        
        //Runs the for loop to go through each element and count how many times this number pops up
        for (int i = 0; i < inputNumbers.size(); i++) {
            //This temp variable is initialized and reset to keep track of the number.
            int temp = 0;
            
            for (int j = 0; j < inputNumbers.size(); j++) {
                
                //If the number matches another number then it adds one to the temp count
                if (inputNumbers.get(j) == inputNumbers.get(i)) {
                    temp++;
                }
            }
            
            //The if loop to see if the the temp count element is greater than the highest count method.
            if (temp >= count) {
                //Count is then set to the temp number, temp is reset at the beginning of the loop, and now the count number is higher and the one to beat.
                count = temp;
                //The mode is set to be the number that has appeared the most in this loop
                mode = inputNumbers.get(i);
                //mode.add(temp); failed duplicate attempt
            }
        }
        
        //Returns the mode
        return mode;
    }
   
   public static double findStandardDeviation(ArrayList<Integer> inputNumbers) {
        //Initializes the variables to 0
        double sum = 0;
        double sd = 0;

        //Add all the numbers in the ArrayList together
        for(int singleElement : inputNumbers) {
            sum += singleElement;
        }
        
        //Divide the sum of the numbers by the amount of numbers in the ArrayList
        double mean = sum/inputNumbers.size();
        
        //^Copied the findMean method for this ^

        //Adds the number squared and subtract it from the mean to the sd sum
        for(int singleElement: inputNumbers) {
            sd += Math.pow(singleElement - mean, 2);
        }
        
        //Returns the square root of the sd sum divided by the amount of numbers in the ArrayList
        return Math.sqrt(sd/inputNumbers.size());
    }
    
    //https://www.geeksforgeeks.org/biginteger-class-in-java/ where I learned how to use BigInteger terms and methodology
    //https://stackoverflow.com/questions/17908260/finding-the-factorial-using-recursion-with-the-biginteger-class is where I got the help for BigInteger factorial
    //BigInteger is used because of Java's limit with outputting numbers from binary
    public static BigInteger findFactorial(BigInteger n) {
        
        //The if loop checks if n is 0 and outputs 1 because that is the factorial of 0
        //The loop runs until n is 0
        if (n.compareTo(BigInteger.ZERO) == 0) {
            return BigInteger.ONE;
        } 
        //When n is not 0, uses the formula of a factorial by recursively calling the method with (n-1)
        else {
            return n.multiply(findFactorial(n.subtract(BigInteger.ONE)));
        }
    }
    
    //Used formula from book, once I had findFactorial made, permutation and combination were not too challending. Just BigInteger stuff
    //BigInteger is used because of Java's limit with outputting numbers from binary
    public static BigInteger findPermutation(BigInteger n, BigInteger r) {
        //Runs the findFactorial method for n for the numerator
        BigInteger numerator = findFactorial(n);
        //Runs the findFactorial method for (n-r) for the denominator
        BigInteger denominator = findFactorial(n.subtract(r));
        //Returns the statement for setting the numerator and denominator in a fraction
        return numerator.divide(denominator);
    }
    
    //BigInteger is used because of Java's limit with outputting numbers from binary
    public static BigInteger findCombinations(BigInteger n, BigInteger r) {
        //Initializes the numerator with the findFactorial method for n       
        BigInteger numerator = findFactorial(n);
        //Initializes the denominator with the findFactorial method for (n-r) and multiplies it by the factorial of r       
        BigInteger denominator = findFactorial(r).multiply(findFactorial(n.subtract(r)));
        //Returns the statement for setting the numerator and denominator in a fraction        
        return numerator.divide(denominator);
    }
    
    public static double findBinomialDistribution(int n, int y, double p) {
        //Initializes q as the function 1-p
        double q = 1 - p;
        //Note that because the findFactorial method was in BigInteger the factorials need to be turned into doubles. https://www.javatpoint.com/java-int-to-double#:~:text=We%20can%20convert%20int%20value,valueOf()%20method.
        //Initializes the numerator by using the findFactorial method for n and multiplying it by p to the y power and q to the n-y power
        double numerator = findFactorial(BigInteger.valueOf(n)).doubleValue() * Math.pow(p, y) * Math.pow(q, n - y);
        //Initializes the denominator by using the findFactorial method for y and multiplying it by the factorial of (n-y)
        double denominator = findFactorial(BigInteger.valueOf(y)).doubleValue() * findFactorial(BigInteger.valueOf(n - y)).doubleValue();
        //Initializes the probability by putting the numerator over the denominator and divding                
        double probability = numerator / denominator;
        //Returns the probability as a percentage
        return probability * 100;
    }
    
    public static double findGeometricDistribution(double p, int y) {
        //Initializes q as the function 1-p        
        double q = 1.0 - p;
        //Initializes the probability by multiplying p by q to the power of (y-1)
        double probability = Math.pow(q, y - 1) * p;
        //Returns the probability as a percentage
        return probability * 100;
    }
    
    public static double hypergeometricDistribution(BigInteger n, BigInteger N, BigInteger K, BigInteger k) {
        
        // calculate numerator and denominator of the hypergeometric distribution formula
        BigInteger numerator = findCombinations(K, k).multiply(findCombinations(N.subtract(K), n.subtract(k)));
        BigInteger denominator = findCombinations(N, n);
        
        // convert to double and return result
        double result = numerator.doubleValue() / denominator.doubleValue();
        return result;
    }
    
    //https://vitalflux.com/chebyshevs-theorem-concepts-formula-examples/#:~:text=The%20Chebyshev%20theorem%20states%20that,mean%20(%CE%BC%20%C2%B1%202%CF%83).
     public double chebyshevsTheorem(ArrayList<Integer> inputNumbers, int k) {
        double mean = findMean(inputNumbers);
        double sd = findStandardDeviation(inputNumbers);
        
        double rangeMin = mean - k * sd;
        double rangeMax = mean + k * sd;
        
        double percentage = 1 - 1.0 / (k * k);
        
        return percentage;
    }
    
    public static double poissonDistribution(double lambda, BigInteger k) {
        double numerator = Math.pow(lambda, k.doubleValue()) * Math.exp(-lambda);
        double denominator = findFactorial(k).doubleValue();
        
        return numerator / denominator;
    }
}
