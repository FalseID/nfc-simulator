package arc.functions;

import java.util.ArrayList;
import java.util.HashSet;

import arc.core.Source;

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
	
	public int compute(ArrayList<Integer> integers) {
		int sum = 0;
		for (int a :integers){
			sum+=a;
		}
		return sum;
	}

	public int direct_compute(HashSet<Source> sources) {
		int sum = 0;
		for (Source s : sources){
<<<<<<< HEAD
			sum += s.getInput();
=======
			sum += s.getMessage();
>>>>>>> branch 'master' of https://github.com/FalseID/nfc-simulator
		}
		return sum;
	}

}
