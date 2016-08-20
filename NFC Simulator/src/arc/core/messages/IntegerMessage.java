package arc.core.messages;

public class IntegerMessage extends Message{
	private int message;
	
	public IntegerMessage(int message){
		super();
		this.message = message;
	}

	public Integer getMessage() {
		return message;
	}

	public void setMessage(String message) throws NumberFormatException {
		this.message = Integer.parseInt(message);
	}

	@Override
	public String toString() {
		return Integer.toString(message);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + message;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		IntegerMessage other = (IntegerMessage) obj;
		if (message != other.message)
			return false;
		return true;
	}
}
