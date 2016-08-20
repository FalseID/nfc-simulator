package arc.core;

import arc.core.messages.Message;

public class Edge {
    private int capacity;
    private String label;
    private Message code;
    
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

	public Message getCode() {
		return code;
	}

	public void setCode(Message code) {
		this.code = code;
	}
    
    
    
}
