package arc.core;

import java.util.concurrent.atomic.AtomicInteger;

import arc.core.messages.Message;

/**
 * Vertex for intermediary nodes in the graph and the basis of the source and sink nodes. 
 * Normal vertices however, do not have an initial input value but receive it from source nodes.
 * @author Janar
 *
 */
public class IntermediaryVertex extends Vertex{
	protected static AtomicInteger NextId = new AtomicInteger();
	protected int id;
    protected String label;
	
	public IntermediaryVertex(){
		int nextid = IntermediaryVertex.NextId.incrementAndGet();
		this.label=""+nextid;
		this.id = nextid;
	 }

    public IntermediaryVertex(String label){
		this.label=label;
		this.id = IntermediaryVertex.NextId.incrementAndGet();
    }
    
    public Message getInput() {
		return input;
	}

	public void setInput(Message input) {
		this.input = input;
	}
    
    public static void resetNextId(){
    	IntermediaryVertex.NextId.set(0);
    }
    
    @Override
    public String toString() {
    	return label;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

	public double getX() {
		return this.x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}
}