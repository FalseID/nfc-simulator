package arc.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import arc.core.Edge;
import arc.core.Network;
import arc.core.Sink;
import arc.core.Source;
import arc.core.Vertex;
import arc.functions.ArithmeticSum;

public class GraphUtil {
	
	/**Generates a random network with specified number of nodes and a minimum 
	and maximum path length from sources to receivers. Messages are all zero.**/
	public static Network GenerateNetwork(int source_count, int receiver_count, int intermediaries_count, int min_cap, int max_cap){
		Network genNetwork = new Network(new ArithmeticSum());
		
		ArrayList<Source> sources = new ArrayList<Source>();
		ArrayList<Sink> receivers = new ArrayList<Sink>();
		ArrayList<Vertex> intermediaries = new ArrayList<Vertex>();
		for (int i=0; i < source_count; i++ ){
			sources.add(new Source("S"+Integer.toString(i),0));
		}
		
		for (int i=0; i < receiver_count; i++ ){
			receivers.add(new Sink("P"+Integer.toString(i)));
		}
		
		for (int i=0; i < intermediaries_count; i++ ){
			intermediaries.add(new Vertex("I"+Integer.toString(i)));
		}
		/*ArrayList<Vertex> Nodes= new ArrayList<Vertex>();
		Nodes.addAll(sources);
		Nodes.addAll(receivers);
		Nodes.addAll(intermediaries);
		*/
		for(Vertex node1: intermediaries){
			for(Vertex node2: intermediaries){
				if(!node1.equals(node2) && Math.random() < 0.25){
					int c = min_cap + (int)(Math.random() * max_cap);
						genNetwork.addEdge(new Edge(c), node1, node2);
				}
			}
		
		}
		
		return genNetwork;
	}
		
		/**Takes a network and removes cycles. Very slow for large graphs.
		 * 
		 */
		public static Network Acycler(Network network){
			for (Vertex a : network.getVertices()){
				for (Vertex b : network.getVertices()){
					if(isPath(network, a, b) && isPath(network, b, a)){
						System.out.println("Removed a path " + a.getLabel()+ "->" + b.getLabel());
						labelCyclePath(network,a,b);
					}
				}
			}
			removeLabeledEdges(network);
			return network;
		}
	
	/**Check for an existance of a path from a->...->b.
	 * 
	 * @param network
	 * @param a
	 * @param b
	 * @return
	 */
		public static boolean isPath(Network network, Vertex a, Vertex b){
			for(Vertex successor: network.getSuccessors(a)){
				if(successor.equals(b)){
					return true;
				}
				else{
					return isPath(network, successor,b);
				}
			}
			return false;
		}
		
		/**Removes any possible paths between two nodes by labeling vertices 
		 * for removal at the root vertex b.
		 * 
		 * @param network
		 * @param a
		 * @param b
		 */
		public static void labelCyclePath(Network network, Vertex a, Vertex b){
			for(Vertex successor: network.getSuccessors(a)){
				if(successor.equals(b)){
					network.findEdge(a, b).setLabel("R");
					return;
				}
				else{
					labelCyclePath(network, successor,b);
				}
			}
		}
		
		/**Removes edges marked for removal.
		 * 
		 * @param network
		 */
		public static void removeLabeledEdges(Network network){
			Collection<Edge> edges = new ArrayList<Edge>();
					edges.addAll(network.getEdges());
			for (Edge e : edges){
				if(e.getLabel().equals("R")){
					network.removeEdge(e);
				}
			}
		}
		
		public static void encode(Network MyNetwork){
	    	Set<Vertex> encoders = new HashSet<Vertex>();
	    	//We start at source nodes.
	    	encoders.addAll(MyNetwork.getSources());
	    	//Until there's nothing left to encode.
	    	while (encoders.size() > 0){
	    		//Encoding part
	    		for (Vertex v : encoders){
	    			//Condition 1
	    			if (v instanceof Source){ 
	    				for (Vertex vv : MyNetwork.getSuccessors(v)){
	    					vv.setMessage(v.getMessage());
	    				}
	    			}
	    			//Condition 2
	    			else if (MyNetwork.getPredecessorCount(v)==1){ 
	    				for (Vertex vv : MyNetwork.getSuccessors(v)){
	    					vv.setMessage(MyNetwork.getPredecessors(v).iterator().next().getMessage());
	    				}
	    			}
	    			//Condition 3
	    			else {
	    				//Find the targetfunction of all predecessor messages.
	    				ArrayList<Integer> integer_list = new ArrayList<Integer>();
	    				for (Vertex pre_v : MyNetwork.getPredecessors(v)){
	    					integer_list.add(pre_v.getMessage());
	    				}
	    				int code = MyNetwork.getTargetFunction().compute(integer_list);
	    				for (Vertex vv : MyNetwork.getSuccessors(v)){
	    					
	    					vv.setMessage(code);
	    				}
	    			}
	    		}
	    		//Cycle part
	    		Set<Vertex> new_encoders = new HashSet<Vertex>();
	    		for (Vertex v : encoders){
	    			new_encoders.addAll(MyNetwork.getSuccessors(v));
	    		}
	    		encoders.clear();
	    		encoders.addAll(new_encoders);
	    	}
	    	System.out.println(MyNetwork.results());
	    	//End of encoding proccess
	    }
	
}
