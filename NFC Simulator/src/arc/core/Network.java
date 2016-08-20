package arc.core;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import edu.uci.ics.jung.graph.DirectedSparseGraph;
import edu.uci.ics.jung.graph.DirectedSparseMultigraph;
import edu.uci.ics.jung.graph.Hypergraph;
import arc.core.functions.TargetFunction;
import arc.core.messages.Message;
import arc.core.messages.MessageType;

public class Network extends DirectedSparseGraph<Vertex, Edge> implements Hypergraph<Vertex,Edge>{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1423506516282698173L;
	private TargetFunction targetFunction;
	private MessageType type;
    
    public Network(TargetFunction targetFunction, MessageType type){
		super();
		this.targetFunction = targetFunction;
		this.type = type;
    }
    
    public TargetFunction getTargetFunction() {
		return targetFunction;
	}

	public void setTargetFunction(TargetFunction targetFunction) {
		this.targetFunction = targetFunction;
	}

    public List<Source> getSources(){
		ArrayList<Source> sources = new ArrayList<Source>();
		for(Vertex vertex : this.getVertices()){
		    if(vertex instanceof Source){
		    	sources.add((Source)vertex);
		    }
		}
		return sources;
    }
    
    public HashSet<Sink> getSinks(){
		HashSet<Sink> sinks = new HashSet<Sink>();
		for(Vertex vertex : this.getVertices()){
		    if(vertex instanceof Sink){
		    	sinks.add((Sink)vertex);
		    }
		}
		return sinks;
    }

	@Override
	public String toString() {
		return "Network [targetFunction=" + targetFunction + "]";
	}

	public MessageType getType() {
		return type;
	}

	public void setType(MessageType type) {
		this.type = type;
	}
}
