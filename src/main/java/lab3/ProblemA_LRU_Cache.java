package lab3;

import java.util.InputMismatchException;

//#pragma OJ main
public class ProblemA_LRU_Cache {
    private static OJReader in = new OJReader();
    private static OJWriter out = new OJWriter();

    public static void main(String[] args) {
        final var N = in.nextInt(); //young list size
        final var M = in.nextInt(); //old list size
        final var K = in.nextInt(); //number of operations
        final var innoDBLikeCache = new InnoDBLikeCache<Integer, Integer>(N, M);
        for (int i = 0; i < K; i++) {
            final var op = in.next();
            switch (op){
                case "put":{
                    innoDBLikeCache.put(in.nextInt(), in.nextInt());
                    break;
                }
                case "get":{
                    out.println(innoDBLikeCache.getOrDefault(in.nextInt(), -1));
                    break;
                }
                case "print":{
                    out.println(innoDBLikeCache);
                    break;
                }
                default:{
                    throw new InputMismatchException("Wrong Operation. ");
                }
            }
        }


    }
}
//#include "OnlineJudgeIO.java"
//#include "InnoDBLikeCache.java"
