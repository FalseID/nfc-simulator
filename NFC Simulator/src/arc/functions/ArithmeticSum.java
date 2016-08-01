package arc.functions;

import java.util.ArrayList;
import java.util.HashSet;

import arc.core.Source;
import arc.core.Vertex;

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

	@Override
	public int direct_compute(HashSet<Source> sources) {
		int sum = 0;
		for (Source s : sources){
			sum += s.getMessage();
		}
		return sum;
	}

}
