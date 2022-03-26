package lab3;

import java.util.BitSet;

final class NumberTheoryToolBox {
    /**
     * 基于二进制分解的迭代快速幂
     * @param a int
     * @param b positive int
     * @param modulus positive int
     * @return a^b % modulus
     */
    public static int fastPower(int a, int b, int modulus) {
        assert b >= 0; assert modulus>0;
        int result = 1;
        for (int powered = a; b != 0; powered = (int)((long) powered * powered % modulus), b >>= 1) {
            if ((b & 1) == 1)
//                result = Math.toIntExact((long) result * powered % modulus);
                result = (int)((long) result * powered % modulus); //不需要toIntExact，因为永远不会抛出异常，modulus会保证。
        }
        return result;
    }

    /**
     *
     * @param a integer
     * @param primeModulus positive integer
     * @return b such that b∈[0, modulus-1] and ab三1 (mod modulus)
     */
    public static int primeModularInverse(int a, int primeModulus){
        assert primeModulus>0;
//        assert isPrime(primeModulus);
        return normalizeMod(fastPower(a, primeModulus - 2, primeModulus), primeModulus);
    }

    /**
     * @param a integer
     * @param modulus positive integer
     * @return b such that b三a (mod modulus) and b∈[0, modulus-1]
     */
    public static int normalizeMod(int a, int modulus){
        assert modulus>0;
        a%=modulus;
        return a<0?a+modulus:a;
    }

//    private static int maxKnownPrimeBoundary = 19;
//    private static BitSet isPrime = new BitSet(); //假定不是素数
//    static {
//        isPrime.set(2); isPrime.set(3); isPrime.set(5); isPrime.set(7);
//        isPrime.set(11); isPrime.set(13); isPrime.set(17); isPrime.set(19);
//    }
//    public static boolean isPrime(int number) {
//        assert number>=0;
//        if (maxKnownPrimeBoundary>=number)
//            return isPrime.get(number); //缓存
//        maxKnownPrimeBoundary = number;
//        // 筛法
//        final var sqrt = (int)Math.sqrt(number);
//        for (int i = 2; i < sqrt; i++) {
//            if (isPrime)
//        }
//        return true;
//    }
}
