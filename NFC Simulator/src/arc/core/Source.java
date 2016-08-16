package arc.core;

<<<<<<< HEAD
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Source node are the source of all inputs in the network and must have an initial value.
 * Source nodes should not have any in-edges and should not get their values from elsewhere.
 * @author Janar
 */
public class Source extends Vertex{
	protected static AtomicInteger NextId = new AtomicInteger();
    
    public Source(int input){
    	int nextid = Source.NextId.incrementAndGet();
    	this.id = nextid;
    	this.input = input;
    	this.label = "S"+this.id+":"+Integer.toString(this.input);
    }
    
    public static void resetNextId(){
    	Source.NextId.set(0);
    }
    
    @Override
    public String toString() {
    	return this.label;
=======
public class Source extends Vertex{
    private int message;
    
    public Source(String label, int message) {
	super(label);
	this.message = message;
    }

    public int getMessage() {
        return message;
    }

    public void setMessage(int message) {
        this.message = message;
    }

    @Override
    public String toString() {
	return this.label + " " + this.message;
>>>>>>> branch 'master' of https://github.com/FalseID/nfc-simulator
    }
}
