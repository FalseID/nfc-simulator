package arc.core;

import java.util.concurrent.atomic.AtomicInteger;

import arc.core.messages.Message;

/**
 * Source node are the source of all inputs in the network and must have an initial value.
 * Source nodes should not have any in-edges and should not get their values from elsewhere.
 * @author Janar
 */
public class Source extends Vertex implements Comparable{
	protected static AtomicInteger NextId = new AtomicInteger();
    
    public Source(Message input){
    	int nextid = Source.NextId.incrementAndGet();
    	this.id = nextid;
    	this.input = input;
    	this.label = "S"+this.id;
    }
    
    public static void resetNextId(){
    	Source.NextId.set(0);
    }
    
    @Override
    public String toString() {
    	return this.label+":"+getInput().toString();
    }

	public int compareTo(Object o) {
		if (o instanceof Source){
			return this.id - ((Source)o).id;
		}
		return 0;
	}
}
