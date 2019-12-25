import java.util.*;

public class Kruskal{

    public static WGraph kruskal(WGraph g){

    	WGraph minSpanningTree = new WGraph();
    	DisjointSets nodesOfGraph = new DisjointSets(g.getNbNodes());
    	ArrayList<Edge> listOfEdges = g.listOfEdgesSorted();
        //Iterate through list of edges of graph
    	for (Edge edge : listOfEdges) {
    		//Check if the edge to add is safe
        	if (IsSafe(nodesOfGraph, edge)) {
        		//If safe, union of the two nodes into a disjoint set
        		nodesOfGraph.union(edge.nodes[0], edge.nodes[1]);
        		//Add edge to the minimum spanning tree
        		minSpanningTree.addEdge(edge);
        	}
        }
        return minSpanningTree;
    }

    public static Boolean IsSafe(DisjointSets p, Edge e){

    	 return ((p.find(e.nodes[0])) != (p.find(e.nodes[1])));
    	    
    }
    

    public static void main(String[] args){

        String file = args[0];
        WGraph g = new WGraph(file);
        WGraph t = kruskal(g);
        System.out.println(t);

   } 
}
