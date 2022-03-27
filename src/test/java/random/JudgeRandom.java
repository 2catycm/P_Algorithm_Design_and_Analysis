package random;

import java.math.BigInteger;
import java.util.Random;

public class JudgeRandom {
    private static final Random random = new Random();
    public static int[] nextRandomPermutation(int size){
        final var permutation = new int[size];
        for (int i = 0; i < size; i++) {
            permutation[i] = i;
        }
        knuthShuffle(permutation);
        return permutation;
    }
    public static void knuthShuffle(int[] arrayA){
        int r;
        for (int i = 1; i < arrayA.length; i++) {
            r = random.nextInt(i+1);//i的位置也要取得到, 可以跟自己交换
            int temp = arrayA[r];
            arrayA[r] = arrayA[i];
            arrayA[i] = temp;
        }
    }
    public static int[] randomIntArray(int startInclusive, int endInclusive, int length){
        final int[] ints = new int[length];
        for (int i = 0; i < length; i++) {
            ints[i] = randomInt(startInclusive, endInclusive);
        }
        return ints;
    }
    public static int randomInt(int startInclusive, int endInclusive){
        return random.nextInt(endInclusive-startInclusive+1)+startInclusive;
    }

    public static int intRandomPrime() {
        BigInteger prime = BigInteger.probablePrime(30, new Random());
        return prime.intValueExact();
    }
}
