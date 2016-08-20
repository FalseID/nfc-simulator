package arc.core;

import arc.core.messages.Message;

public abstract class Vertex {
	protected Message input;
	protected int id;
	protected String label;
	protected double x;
	protected double y;
	
	public Message getInput() {
		return input;
	}
	
	public void setInput(Message input) {
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
}
