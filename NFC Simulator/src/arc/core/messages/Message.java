package arc.core.messages;

public abstract class Message {
	protected Object message;
	
	public abstract void setMessage(String message);
	public abstract Object getMessage();
	public abstract String toString();
	
	public abstract boolean equals(Object o);
	public abstract int hashCode();
	
}
