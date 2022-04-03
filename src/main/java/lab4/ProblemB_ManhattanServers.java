package lab4;

//#pragma OJ main
public class ProblemB_ManhattanServers {
    private static OJReader in = new OJReader();
    private static OJWriter out = new OJWriter();

    public static void main(String[] args) {
        final var N = in.nextInt();
        int[] chebyshevX = new int[N];
        int[] chebyshevY = new int[N];
        for (int i = 0; i < N; i++) {
            final var manhattanX = in.nextInt();
            final var manhattanY = in.nextInt();
            chebyshevX[i] = manhattanX+manhattanY;
            chebyshevY[i] = manhattanX-manhattanY;
        }
        out.println(solve(chebyshevX, chebyshevY));
    }

    static int solve(int[] chebyshevX, int[] chebyshevY) {
        int N = chebyshevX.length; assert (chebyshevY.length==N);
        final var xMinMax = getMaxAndMinIndexWithValues(chebyshevX);
        var xMid = xMinMax[3] + (xMinMax[2]-xMinMax[3])/2;
        final var yMinMax = getMaxAndMinIndexWithValues(chebyshevY);
        var yMid = yMinMax[3] + (yMinMax[2]-yMinMax[3])/2;
        var graph = new EdgeWeightedUndirectedGraph<Integer>(N);
        buildGraph(chebyshevX, xMinMax, xMid, graph);
        buildGraph(chebyshevY, yMinMax, yMid, graph);
        final var primMaximumSpanningTree = new PrimMaximumSpanningTree<Integer>(graph, 1);
        return primMaximumSpanningTree.getLastAddedEdgeWeight();
    }

    static void buildGraph(int[] chebyshev, int[] MinMax, int Mid, EdgeWeightedUndirectedGraph<Integer> graph) {
        for (int i = 0; i < chebyshev.length; i++) {
            final var chebyshevI = chebyshev[i];
            if (chebyshevI>Mid)
                graph.addEdge(i+1, MinMax[1]+1, chebyshevI-MinMax[3]); //大于中值的和最小值连线.
            else
                graph.addEdge(i+1, MinMax[0]+1, MinMax[2]-chebyshevI); //小于等于中值的和最大值连线.
        }
    }

    static int[] getMaxAndMinIndexWithValues(int[] array){
        int[] maxAndMinIndexWithValues = {0, 0, array[0], array[0]};
        for (int i = 1; i < array.length; i++) {
            if (array[i]>maxAndMinIndexWithValues[2]){
                maxAndMinIndexWithValues[0] = i;
                maxAndMinIndexWithValues[2] = array[i];
            }
            if (array[i]<maxAndMinIndexWithValues[3]){
                maxAndMinIndexWithValues[1] = i;
                maxAndMinIndexWithValues[3] = array[i];
            }
        }
        return maxAndMinIndexWithValues;
    }
}
//#include "OnlineJudgeIO.java"
//#include "EdgeWeightedGraphs.java"
//#include "MaximumSpanningTree.java"