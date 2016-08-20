package arc.core;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import edu.uci.ics.jung.graph.DirectedSparseGraph;
import arc.core.functions.ArithmeticSum;
import arc.core.messages.Message;
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
    			//Encoder node is a source node, outgoing edges will contain its input.
    			if (v instanceof Source){ 
    				for (Edge e : MyNetwork.getOutEdges(v)){
    					e.setCode(v.getInput());
    				}
    			}
    			//Condition 2
    			//Encoder node has only one incoming edge. Outgoing edges will act as a relay.
    			else if (MyNetwork.getInEdges(v).size()==1){
    				Message code = MyNetwork.getInEdges(v).iterator().next().getCode();
    				v.setInput(code);
    				for (Edge e : MyNetwork.getOutEdges(v)){
    					e.setCode(code);
    				}
    			}
    			//Condition 3
    			//Encoder node may have many predecessors, so successors will receive the function of all
    			//encoders inputs as their own input.
    			else {
    				//Find the targetfunction of all predecessor messages.
    				ArrayList<Message> messages = new ArrayList<Message>();
    				for (Edge in_e : MyNetwork.getInEdges(v)){
    					messages.add(in_e.getCode());
    				}
    				
    				Message code = MyNetwork.getTargetFunction().compute(messages, MyNetwork.getType());
    				v.setInput(code);
    				for (Edge out_e : MyNetwork.getOutEdges(v)){
    					out_e.setCode(code);
    				}
    			}
    		}
    		//Successors of current encoders will be the new encoders.
    		Set<Vertex> new_encoders = new HashSet<Vertex>();
    		for (Vertex v : encoders){
    			new_encoders.addAll(MyNetwork.getSuccessors(v));
    		}
    		encoders.clear();
    		encoders.addAll(new_encoders);
    	}
    	//End of encoding proccess
    	return results(MyNetwork);
    	
    }
	
	private static String results(Network network) {
		String results = "";
		
		ArrayList<Message> source_messages = new ArrayList<Message>();
		for(Source s : network.getSources()){
			source_messages.add(s.getInput());
		}
		
		Message correct_result = network.getTargetFunction().compute(source_messages, network.getType());
		
		for (Sink s : network.getSinks()){
			if (s.getInput().equals(correct_result)){
				results += s.getLabel() +":"+ s.getInput().toString() + " Correct \n";
			}
			else{
				results += s.getLabel() +":"+ s.getInput().toString() + " Error, should be " + correct_result + "\n";
			}
	    }
		return results;
	}
	
}
