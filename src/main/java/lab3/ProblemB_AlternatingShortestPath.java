package lab3;

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
        out.println(solve(digraph, p));
    }

    private static int solve(EdgeWeightedDirectedGraph digraph, int p) {

        return 0;
    }

    public static int spelledDistance(int x, int time, int p) {
        switch (time % 4) {
            case 0: {
                return x;
            }
            case 1: {
                final var inv = NumberTheoryToolBox.primeModularInverse(1 - x, p);
                return (int) ((long) (1 + x) * inv % p);
            }
            case 2: {
                return NumberTheoryToolBox.primeModularInverse(-x, p);
            }
            case 3: {
                final var inv = NumberTheoryToolBox.primeModularInverse(x + 1, p);
                return (int) ((long) (x - 1) * inv % p);
            }
        }
        return 0;
    }
}
//#include "OnlineJudgeIO.java"
//#include "NumberTheoryToolBox.java"
//#include "EdgeWeightedGraphs.java"