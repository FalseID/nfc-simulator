package arc.core;

<<<<<<< HEAD

public abstract class Vertex {
	protected int input;
	protected int id;
	protected String label;
	protected double x;
	protected double y;
	
	public int getInput() {
		return input;
	}
	public void setInput(int input) {
		this.input = input;
	}
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public double getX() {
		return x;
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
	
=======
import java.util.concurrent.atomic.AtomicInteger;

public class Vertex {
	protected static AtomicInteger NextId = new AtomicInteger();
	protected int id;
    protected String label;
    private int message;
    private double x;
    private double y;
    
    
    public int getMessage() {
		return message;
	}

	public void setMessage(int message) {
		this.message = message;
	}
	
	public Vertex(){
		int nextid = Vertex.NextId.incrementAndGet();
		this.label=""+nextid;
		this.id = nextid;
	 }

    public Vertex(String label){
		this.label=label;
		this.id = Vertex.NextId.incrementAndGet();
    }
    
    public static void resetNextId(){
    	Vertex.NextId.set(0);
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
		return x;
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
>>>>>>> branch 'master' of https://github.com/FalseID/nfc-simulator
}
