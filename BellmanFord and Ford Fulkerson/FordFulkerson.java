import java.io.*;
import java.util.*;




public class FordFulkerson {

	
	public static ArrayList<Integer> pathDFS(Integer source, Integer destination, WGraph graph){
		ArrayList<Integer> Stack = new ArrayList<Integer>();
		boolean[] visitedNodes = new boolean[graph.getNbNodes()];
		int nextNode = source;
		int sizeDFS = 0;
		Stack.add(0, source);
		do {
			sizeDFS++;
			boolean nodeHasNeighbour = false;
			visitedNodes[nextNode] = true;
			for (Edge e: graph.getEdges()) {
				if((nextNode == e.nodes[0]) && (!visitedNodes[e.nodes[1]]) && (e.weight > 0)) {
					nodeHasNeighbour = true;
					Stack.add(sizeDFS, e.nodes[1]);
					break;
				}
			}
			if (!nodeHasNeighbour) {
				sizeDFS--;
				Stack.remove(sizeDFS);
				if(sizeDFS == 0) {
					return new ArrayList<Integer>();
				}
				sizeDFS--;
			}
			nextNode = Stack.get(sizeDFS);
		} while (nextNode != destination);
		return Stack;
	}
	
	
	
	public static void fordfulkerson(Integer source, Integer destination, WGraph graph, String filePath){
		String answer="";
		String myMcGillID = "260759306"; //Please initialize this variable with your McGill ID
		int maxFlow = 0;
		
		WGraph residualGraph = new WGraph(graph);
		WGraph capacityGraph = new WGraph(graph);

		for (Edge edge: graph.getEdges()) {
			edge.weight = 0;
		}
		
		while (!pathDFS(source, destination, residualGraph).isEmpty()) {
			
			ArrayList<Edge> edgesOrder = new ArrayList<Edge>();
			ArrayList<Integer> verticesOrder = pathDFS(source, destination, residualGraph);
			int bottleneck = residualGraph.getEdge(verticesOrder.get(0), verticesOrder.get(1)).weight;
			for (int i = 0; i < verticesOrder.size() - 1; i++) {
				Integer currNode = verticesOrder.get(i);
				if (currNode != destination) {
					Edge e = residualGraph.getEdge(currNode, verticesOrder.get(i+1));
					edgesOrder.add(e);
					if (e.weight < bottleneck) {
						bottleneck = e.weight;
					}

				}
			}
			for (Edge e: edgesOrder) {
				Edge eGraph = graph.getEdge(e.nodes[0], e.nodes[1]);
				if(eGraph !=null) {
					eGraph.weight += bottleneck;
				}
				else {
					graph.getEdge(e.nodes[1], e.nodes[0]).weight -= bottleneck;
				}
			}

			for (Edge e: graph.getEdges()) {
				
				int edgeWeight = e.weight;
				int maxCapacity = capacityGraph.getEdge(e.nodes[0], e.nodes[1]).weight;
				if(residualGraph.getEdge(e.nodes[1], e.nodes[0]) == null) {
					Edge backEdge = new Edge(e.nodes[1], e.nodes[0], edgeWeight);
					residualGraph.addEdge(backEdge);
				}

				residualGraph.getEdge(e.nodes[0], e.nodes[1]).weight = maxCapacity - edgeWeight;
				residualGraph.getEdge(e.nodes[1], e.nodes[0]).weight = edgeWeight;
			}

			maxFlow += bottleneck;
		}
		
		
		answer += maxFlow + "\n" + graph.toString();	
		writeAnswer(filePath+myMcGillID+".txt",answer);
		System.out.println(answer);
	}
	
	
	public static void writeAnswer(String path, String line){
		BufferedReader br = null;
		File file = new File(path);
		// if file doesnt exists, then create it
		
		try {
		if (!file.exists()) {
			file.createNewFile();
		}
		FileWriter fw = new FileWriter(file.getAbsoluteFile(),true);
		BufferedWriter bw = new BufferedWriter(fw);
		bw.write(line+"\n");	
		bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (br != null)br.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
	}
	
	 public static void main(String[] args){
		 String file = args[0];
		 File f = new File(file);
		 WGraph g = new WGraph(file);
		 fordfulkerson(g.getSource(),g.getDestination(),g,f.getAbsolutePath().replace(".txt",""));
	 }
}
