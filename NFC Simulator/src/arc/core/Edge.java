package arc.core;

public class Edge {
    private int capacity;
    private String label;
    
    public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public Edge(int capacity){
	this.capacity = capacity;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

	@Override
	public String toString() {
		return "";
	}
    
    
    
}
