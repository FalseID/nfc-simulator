package arc.core;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import edu.uci.ics.jung.graph.DirectedSparseGraph;
import arc.core.functions.ArithmeticSum;
/**
 * Responsible for the network function computation. Simulates the act of computing
 * in a network according to the greedy encoding methodology in my thesis.
 * @author Janar
 *
 */
public class NetworkEncoder {
		public static String encode(Network MyNetwork){
	    	Set<Vertex> encoders = new HashSet<Vertex>();
	    	//We start at source nodes.
	    	encoders.addAll(MyNetwork.getSources());
	    	//Until there's nothing left to encode.
	    	while (encoders.size() > 0){
	    		//Encoding part
	    		for (Vertex v : encoders){
	    			//Condition 1
	    			//Encoder node is a source node, successors will receive encoders input.
	    			if (v instanceof Source){ 
	    				for (Vertex vv : MyNetwork.getSuccessors(v)){
	    					vv.setInput(v.getInput());
	    				}
	    			}
	    			//Condition 2
	    			//Encoder node has only one predecessor. Successors will receive the predecessors input.
	    			else if (MyNetwork.getPredecessorCount(v)==1){ 
	    				for (Vertex vv : MyNetwork.getSuccessors(v)){
	    					vv.setInput(MyNetwork.getPredecessors(v).iterator().next().getInput());
	    				}
	    			}
	    			//Condition 3
	    			//Encoder node may have many predecessors, so successors will receive the function of all
	    			//encoders inputs as their own input.
	    			else {
	    				//Find the targetfunction of all predecessor messages.
	    				ArrayList<Integer> integer_list = new ArrayList<Integer>();
	    				for (Vertex pre_v : MyNetwork.getPredecessors(v)){
	    					integer_list.add(pre_v.getInput());
	    				}
	    				int code = MyNetwork.getTargetFunction().compute(integer_list);
	    				for (Vertex vv : MyNetwork.getSuccessors(v)){
	    					vv.setInput(code);
	    				}
	    			}
	    		}
	    		//Successors of current encoders will be te new encoders.
	    		Set<Vertex> new_encoders = new HashSet<Vertex>();
	    		for (Vertex v : encoders){
	    			new_encoders.addAll(MyNetwork.getSuccessors(v));
	    		}
	    		encoders.clear();
	    		encoders.addAll(new_encoders);
	    	}
	    	//End of encoding proccess
	    	return MyNetwork.results();
	    	
	    }
	
}
