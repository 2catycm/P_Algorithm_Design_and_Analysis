package random;

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
}
