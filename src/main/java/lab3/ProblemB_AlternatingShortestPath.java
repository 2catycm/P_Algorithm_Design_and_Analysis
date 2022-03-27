package lab3;

import java.util.Arrays;

//#pragma OJ main
public class ProblemB_AlternatingShortestPath {
    private static OJReader in = new OJReader();
    private static OJWriter out = new OJWriter();

    public static void main(String[] args) {
        final var N = in.nextInt();
        final var M = in.nextInt();
        final var p = in.nextInt();
        final var digraph = new EdgeWeightedDirectedGraph(N);
        for (int i = 0; i < M; i++) {
            digraph.addEdge(in.nextInt(), in.nextInt(), in.nextInt());
        }
        final var solver = new AlternatingShortestPathSolver(digraph, p);
        out.println(solver.getSolution());
    }
}
//做出一个新的类，是为了方法之间变量共享不开启栈空间，同时保持封装性不被外面的类随意访问（花里胡哨, 有什么用？）
class AlternatingShortestPathSolver{
    private EdgeWeightedDirectedGraph digraph;
    private int N, M, modulus;
    public AlternatingShortestPathSolver(EdgeWeightedDirectedGraph digraph, int modulus) {
        this.digraph = digraph;
        this.N = digraph.verticesCnt; this.M = digraph.edgesCnt;
        this.modulus = modulus;
        src = 1; dst = N;
        solve();
    }
    public long getSolution(){
        return solution;
    }
    private long solution = Long.MAX_VALUE; //注意距离没有mod p，所以完全可能达到long
//    private int[][] knownDist;
//    knownDist = new int[N+1][4];
//        for (int i = 0; i < knownDist.length; i++) {
//            Arrays.fill(knownDist[i], Integer.MAX_VALUE);
//        } 这也是一种写法
    private int src, dst;
    private void solve(){
        final var iq = new IndexInferiorityQueue<Long>
                (4*(N + 1)); //存储当前最小值为value。key是一种编码
        Arrays.fill(iq.keys, Long.MAX_VALUE);
        iq.offer(new VertexAndTime(src, 0).index(), 0L);
        while (!iq.isEmpty()) {
            final var topIndex = iq.pollIndex();
            final var topNode = new VertexAndTime(topIndex); //解码
            //是否已经到达终点？
            if (topNode.vertex==dst) {
                solution = iq.keyOf(topIndex);
                return;
            }
            for (var directedEdge:digraph.relativesOf(topNode.vertex)){
                final var newCost = spelledDistance(directedEdge.weight, (topNode.time+1)%4) + iq.keyOf(topIndex);
                final var relativeNodeIndex = new VertexAndTime(directedEdge.other, (topNode.time+1)%4).index();
                if (newCost < iq.keyOf(relativeNodeIndex)){
                    if (iq.contains(relativeNodeIndex)) iq.updateKey(relativeNodeIndex, newCost);
                    else iq.offer(relativeNodeIndex, newCost);
                }
            }
        }
        throw new IllegalArgumentException("Unreachable destination! ");
    }

    //每行有四个元素，第0行空出来。
    private class VertexAndTime {
        private int vertex, time;

        public VertexAndTime(int vertex, int time) {
            this.vertex = vertex;
            this.time = time;
        }

        public VertexAndTime(int index) {
            this.vertex = index / 4;
            this.time = index % 4;
        }

        public int index() {
            return vertex * 4 + time;
        }
    }
    private int spelledDistance(int x, int time) {
        switch (time % 4) {
            case 0: {
                return x;
            }
            case 1: {
                final var inv = NumberTheoryToolBox.primeModularInverse(1 - x, modulus);
                return (int) ((long) (1 + x) * inv % modulus);
            }
            case 2: {
                return NumberTheoryToolBox.primeModularInverse(-x, modulus);
            }
            case 3: {
                final var inv = NumberTheoryToolBox.primeModularInverse(x + 1, modulus);
                return (int) ((long) (x - 1) * inv % modulus);
            }
        }
        return 0;
    }
}
//#include "OnlineJudgeIO.java"
//#include "NumberTheoryToolBox.java"
//#include "EdgeWeightedGraphs.java"
//#include "优先队列.java"