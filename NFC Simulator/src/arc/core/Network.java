package arc.core;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;

import edu.uci.ics.jung.graph.DirectedSparseGraph;
import arc.functions.TargetFunction;

public class Network extends DirectedSparseGraph<Vertex, Edge> implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private TargetFunction targetFunction;
    
    public Network(TargetFunction targetFunction){
	super();
	this.targetFunction = targetFunction;
    }
    
    public TargetFunction getTargetFunction() {
		return targetFunction;
	}

	public void setTargetFunction(TargetFunction targetFunction) {
		this.targetFunction = targetFunction;
	}
	@Override
    public String toString() {
	
	return "Network [sources=" + this.getSources().toString() + ", sinks=" + this.getSinks() + ", vertices="
		+ vertices.toString() + ", edges=" + edges.toString() + ", edge_type=" + edge_type
		+ "]";
    }
	
	 public String results() {
		 String results = "";
		int correct_result = this.getTargetFunction().direct_compute(this.getSources());
		for (Sink s : this.getSinks()){
			if (s.getMessage() == correct_result){	
				results += s.getLabel() +":"+ Integer.toString(s.getMessage()) + " Correct \n";
			}
			else{
				results += s.getLabel() +":"+ Integer.toString(s.getMessage()) + " Error, should be " + correct_result + "\n";
			}
	    }
		return results;
	}
	

    public HashSet<Source> getSources(){
	HashSet<Source> sources = new HashSet<Source>();
	for(Vertex vertex : this.vertices.keySet()){
	    if(vertex instanceof Source){
		sources.add((Source)vertex);
	    }
	}
	return sources;
    }
    
    public HashSet<Sink> getSinks(){
	HashSet<Sink> sinks = new HashSet<Sink>();
	for(Vertex vertex : this.vertices.keySet()){
	    if(vertex instanceof Sink){
		sinks.add((Sink)vertex);
	    }
	}
	return sinks;
    }
    
}
