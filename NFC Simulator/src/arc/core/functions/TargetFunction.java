package arc.core.functions;

import java.util.ArrayList;
import java.util.HashSet;

import arc.core.Source;
import arc.core.messages.Message;
import arc.core.messages.MessageType;

public abstract class TargetFunction {
	
	public TargetFunction(){
	}
	
	/**
	 * This is the computation associated with vertices in our graph. Each vertex will do the following according to the inputs.
	 * The inputs should be received from neighboring vertices.
	 * @param integers
	 * @return
	 */
	public abstract Message compute(ArrayList<Message> messages, MessageType type);
}
