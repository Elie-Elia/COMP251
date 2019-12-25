public class BellmanFord{

	
	/**
	 * Utility class. Don't use.
	 */
	public class BellmanFordException extends Exception{
		private static final long serialVersionUID = -4302041380938489291L;
		public BellmanFordException() {super();}
		public BellmanFordException(String message) {
			super(message);
		}
	}
	
	/**
	 * Custom exception class for BellmanFord algorithm
	 * 
	 * Use this to specify a negative cycle has been found 
	 */
	public class NegativeWeightException extends BellmanFordException{
		private static final long serialVersionUID = -7144618211100573822L;
		public NegativeWeightException() {super();}
		public NegativeWeightException(String message) {
			super(message);
		}
	}
	
	/**
	 * Custom exception class for BellmanFord algorithm
	 *
	 * Use this to specify that a path does not exist
	 */
	public class PathDoesNotExistException extends BellmanFordException{
		private static final long serialVersionUID = 547323414762935276L;
		public PathDoesNotExistException() { super();} 
		public PathDoesNotExistException(String message) {
			super(message);
		}
	}
	
    private int[] distances = null;
    private int[] predecessors = null;
    private int source;

    BellmanFord(WGraph g, int source) throws BellmanFordException{
    	distances = new int[g.getNbNodes()];
		predecessors = new int[g.getNbNodes()];
    	this.source = source;
		for (int j =0; j < predecessors.length; j++) {
			if (j != source) predecessors[j] = -1;
		}
		distances[source] = 0;
		predecessors[source] = source;
		
		for (Edge e: g.getEdges()) {
			distances[e.nodes[0]] = Integer.MAX_VALUE;
			distances[e.nodes[1]] = Integer.MAX_VALUE;
			if (e.nodes[0] == source) {
				distances[e.nodes[0]] = 0;
			}
			if (e.nodes[1] == source) {
				distances[e.nodes[1]] = 0;
			}
		}
		
		for (int k = 0; k < g.getNbNodes() - 1; k++) {
			for (Edge e: g.getEdges()) {
				if (distances[e.nodes[0]] != Integer.MAX_VALUE && distances[e.nodes[1]] > e.weight + distances[e.nodes[0]]) {
					distances[e.nodes[1]] = e.weight + distances[e.nodes[0]];
					predecessors[e.nodes[1]] = e.nodes[0];
					}
			}
		}
        for (Edge e: g.getEdges()) 
        { 
            if (distances[e.nodes[0]] != Integer.MAX_VALUE && distances[e.nodes[0]] + e.weight < distances[e.nodes[1]]) 
            	throw new NegativeWeightException("There is a negative cycle"); 
        } 
	}

    public int[] shortestPath(int destination) throws BellmanFordException{
    	int previous = destination;
		if (distances[destination] == Integer.MAX_VALUE) throw new PathDoesNotExistException("There is no path from the source to the destination"); 
		int[] pathShortest = new int[distances.length];
		int idx =0;
		 while (previous != this.source){
			 pathShortest[idx++] = (previous);
	            previous = predecessors[previous];
	        }
	        pathShortest[idx++] = (this.source);
	        int[] pathFinal = new int[idx];
	 
	        for (int j = 0; j < idx; j++) {
	            pathFinal[j] = pathShortest[(idx - 1 -j)];
	        }
	        return pathFinal;
	}

    public void printPath(int destination){
        /*Print the path in the format s->n1->n2->destination
         *if the path exists, else catch the Error and 
         *prints it
         */
        try {
            int[] path = this.shortestPath(destination);
            for (int i = 0; i < path.length; i++){
                int next = path[i];
                if (next == destination){
                    System.out.println(destination);
                }
                else {
                    System.out.print(next + "-->");
                }
            }
        }
        catch (BellmanFordException e){
            System.out.println(e);
        }
    }

    public static void main(String[] args){

        String file = args[0];
        WGraph g = new WGraph(file);
        try{
            BellmanFord bf = new BellmanFord(g, g.getSource());
            bf.printPath(g.getDestination());
        }
        catch (BellmanFordException e){
            System.out.println(e);
        }

   } 
}
