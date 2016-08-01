package arc.core;

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
    }
}
