package arc.core.functions;

import java.util.ArrayList;
import java.util.HashSet;

import arc.core.Source;
import arc.core.messages.IntegerMessage;
import arc.core.messages.Message;
import arc.core.messages.MessageType;

/***
 * A self-explanatory function that simply computes the sum of its arguments.
 * It requires an acyclic, multirooted tree graph in order to be computable in the network.
 * @author Janar
 *
 */
public class ArithmeticSum extends TargetFunction {
	
	public ArithmeticSum() {
		super();
	}
	
	public Message compute(ArrayList<Message> messages, MessageType type) {
		if(type == MessageType.INTEGER){
			int sum = 0;
			for (Message msg :messages){
				sum += ((IntegerMessage)msg).getMessage();
			}
			return new IntegerMessage(sum);
		}
		return null;
	}

	@Override
	public String toString() {
		return "Arithmetic Sum";
	}

}
