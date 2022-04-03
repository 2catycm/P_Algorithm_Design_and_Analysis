package lab4;

import java.util.BitSet;
import java.util.PriorityQueue;

class PrimMaximumSpanningTree<Weight extends Number & Comparable<Weight>>{
    private BitSet isVisited;
    private PriorityQueue<AbstractEdgeWeightedGraph<Weight>.Edge> pq;
    private void visit(int vertex){
        isVisited.set(vertex);
        for (var edge:graph.relativesOf(vertex)){
            //这个edge是由内指向外的。
            if (!isVisited.get(edge.other))
                pq.add(edge);
        }
    }
    private void prim(int start){
        cost = 0;
        isVisited = new BitSet();
        pq = new PriorityQueue<>((o1, o2) -> o2.getWeight().compareTo(o1.getWeight())); //大顶堆
        visit(start);
        while (!pq.isEmpty()){
            final var top = pq.poll();
            if (!isVisited.get(top.other)){
                lastAddedEdge = top;
                cost+=top.weight.longValue();
                visit(top.other);
            }
        }
    }
    AbstractEdgeWeightedGraph<Weight>.Edge lastAddedEdge;
    public Weight getLastAddedEdgeWeight(){
        return lastAddedEdge.getWeight();
    }
    private long cost;
    public long getCost() {
        return cost;
    }
    public PrimMaximumSpanningTree(EdgeWeightedUndirectedGraph<Weight> graph, int start) {
        this.graph = graph;
        prim(start);
    }
    private EdgeWeightedUndirectedGraph<Weight> graph;
}