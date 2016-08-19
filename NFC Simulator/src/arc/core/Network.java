package arc.core;
import java.util.HashSet;

import edu.uci.ics.jung.graph.DirectedSparseGraph;
import edu.uci.ics.jung.graph.DirectedSparseMultigraph;
import edu.uci.ics.jung.graph.Hypergraph;
import arc.core.functions.TargetFunction;

public class Network extends DirectedSparseGraph<Vertex, Edge> implements Hypergraph<Vertex,Edge>{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1423506516282698173L;
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
	
	public String results() {
		 String results = "";
		int correct_result = this.getTargetFunction().direct_compute(this.getSources());
		for (Sink s : this.getSinks()){
			if (s.getInput() == correct_result){	
				results += s.getLabel() +":"+ Integer.toString(s.getInput()) + " Correct \n";
			}
			else{
				results += s.getLabel() +":"+ Integer.toString(s.getInput()) + " Error, should be " + correct_result + "\n";
			}
	    }
		return results;
	}
	

    public HashSet<Source> getSources(){
		HashSet<Source> sources = new HashSet<Source>();
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
}
