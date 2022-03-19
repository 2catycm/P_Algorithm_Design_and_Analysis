package lab2;

import java.util.Arrays;

//#pragma OJ main
public class ProblemB_BunnyTopia {
    static OJReader in = new OJReader();
    static OJWriter out = new OJWriter();
    public static void main(String[] args) {
        final int N = in.nextInt();
        final int M = in.nextInt();
        final int[] villageReward = in.nextIntArray(N);
        final var graph = new EdgeWeightedUndirectedGraph<Integer>(N);
        for (int i = 0; i < M; i++) {
            graph.addEdge(in.nextInt(), in.nextInt(), in.nextInt()); //注意图的编号从1开始
        } //允许平行边和自环
        out.println(solve(N, M, villageReward, graph));
    }

    static int solve(int n, int m, int[] villageReward, EdgeWeightedUndirectedGraph<Integer> graph) {
        final var longReward = Arrays.stream(villageReward).mapToLong(Long::valueOf).toArray();
        for (int i = 0; i < n; i++) {
            longReward[i]<<=1;
            for (var relative:graph.relativesOf(i+1)){
//                villageReward[i]+=(relative.weight>>1);
//                villageReward[i]+=(relative.weight); //可以不除2，无损的
                longReward[i]+=(relative.weight); //不能不除2，否则分数太多了
//                if (relative.other==i+1){//自环
//                    villageReward[i]+=(relative.weight>>1); //再加一次
//                }
            }
        }
        long P = 0;
        Arrays.sort(longReward);
        for (int i = n-1; i >=0 ; i-=2) {
            if (i-1>=0)
                P+=longReward[i]-longReward[i-1];
            else
                P+=longReward[i];
        }
        return (int)(P>>1);
    }
}
//#include "OnlineJudgeIO.java"
//#include "EdgeWeightedGraphs.java"

