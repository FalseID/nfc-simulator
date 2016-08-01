package arc.core;

import java.util.concurrent.atomic.AtomicInteger;

public class Vertex {
	protected static AtomicInteger NextId = new AtomicInteger();
	protected int id;
    protected String label;
    private int message;
    
    
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
}
