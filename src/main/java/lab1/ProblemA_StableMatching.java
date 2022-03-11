package lab1;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Stack;

//#pragma OJ main
public class ProblemA_StableMatching {
    private static OJReader in = new OJReader();
    private static OJWriter out = new OJWriter();
    public static void main(String[] args) {
        final int N = in.nextInt();
        final String[] boyNames = new String[N];
        final String[] girlNames = new String[N];
        final HashMap<String, Integer> boyIndices = new HashMap<>();
        final HashMap<String, Integer> girlIndices = new HashMap<>();
        for (int i = 0; i < N; i++) {
            boyNames[i] = in.next();
            boyIndices.put(boyNames[i], i);
        }
        for (int i = 0; i < N; i++) {
            girlNames[i] = in.next();
            girlIndices.put(girlNames[i], i);
        }
        int[][] boyPreference = new int[N][N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                final String next = in.next();
//                boyPreference[i][j] = girlIndices.get(next);
                boyPreference[i][girlIndices.get(next)] = N-1-j; // 这个女孩在第j, 0个出现，所以评分是n-1-j, n-1。
            }
        }
        int[][] girlPreference = new int[N][N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                final String next = in.next();
                girlPreference[i][boyIndices.get(next)] = N-1-j;
            }
        }
        final int[] solution = solve(boyPreference, girlPreference, N);
        assert solution.length==N;
        for (int i = 0; i < solution.length; i++) {
//            out.printf("%s %s\n", boyNames[i], girlNames[solution[i]]);
            out.printf("%s %s\n", boyNames[solution[i]], girlNames[i]);
        }
        out.flush();
        out.close();
    }

    /**
     * @param boyPreference  the rating for each girl
     * @param girlPreference  the rating for each boy
     * @return girl and her boy matched.
     */
    static int[] solve(int[][] boyPreference, int[][] girlPreference, int n) {
        //prepare stack
        Stack<Integer> freeBoys = new Stack<>();
        for (int i = 0; i < n; i++) {
            freeBoys.add(i);
        }
        //prepare boyPreference order preference是对每个女孩的打分，这个则是对女孩的排序。
        int[][] boyPreferenceOrder = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                boyPreferenceOrder[i][n-1-boyPreference[i][j]] = j;    // 对第零（j）个女孩的打分是n-1，那么女孩的排位是0
            }
        }
        int[] boyPreferenceOrderPointer = new int[n]; //表示第i个男生下一个要求婚的对象。
//        BitSet girlIsProposed = new BitSet();
        // 准备女孩的订婚对象。
        int[] girlPartner = new int[n];
        Arrays.fill(girlPartner, -1); //-1表示没有订婚
        //开始牵红线
        while(!freeBoys.isEmpty()){
            final Integer topMan = freeBoys.pop(); // free boy
            int highestPreferredGirl = boyPreferenceOrder[topMan][boyPreferenceOrderPointer[topMan]]; // the boy's currently not proposed best
            final int anotherBoy = girlPartner[highestPreferredGirl];
            //boy 指针移动
            boyPreferenceOrderPointer[topMan]++; //求过婚了
            if (anotherBoy ==-1){ //if the girl is free
                girlPartner[highestPreferredGirl] = topMan; // they are 订婚
            }else{
                if (girlPreference[highestPreferredGirl][anotherBoy]>=girlPreference[highestPreferredGirl][topMan]){
                    //the boy will be rejected.
                    freeBoys.push(topMan);
                }else{ // girl wants to leave and new boy wants to marry her.
                    freeBoys.push(anotherBoy);
                    girlPartner[highestPreferredGirl] = topMan;
                }
            }
        }
        return girlPartner;
    }
}
//#include "OnlineJudgeIO.java"
