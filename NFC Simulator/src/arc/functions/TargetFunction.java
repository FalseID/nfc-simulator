package arc.functions;

import java.util.ArrayList;
import java.util.HashSet;

import arc.core.Source;

public abstract class TargetFunction {
	
	public TargetFunction(){
	}
	
	public abstract int compute(ArrayList<Integer> integers);
	public abstract int direct_compute(HashSet<Source> sources);
	
}
