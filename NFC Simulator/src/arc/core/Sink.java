package arc.core;

<<<<<<< HEAD
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Sink nodes are the final destination of all the inputs and store the results of the network computation.
 * @author Janar
 *
 */

public class Sink extends Vertex {
	protected static AtomicInteger NextId = new AtomicInteger();
    
    public Sink(){
    	int nextid = Sink.NextId.incrementAndGet();
    	this.id = nextid;
    	this.label = "R"+this.id+":"+Integer.toString(this.input);
    }
    
    public static void resetNextId(){
    	Sink.NextId.set(0);
    }
    
    @Override
    public String toString() {
    	return this.label;
=======
public class Sink extends Vertex {

    public Sink(String label) {
	super(label);
	// TODO Auto-generated constructor stub
>>>>>>> branch 'master' of https://github.com/FalseID/nfc-simulator
    }

}
