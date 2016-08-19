package arc.core.functions;

import java.util.ArrayList;
import java.util.HashSet;

import arc.core.Source;

public abstract class TargetFunction {
	
	public TargetFunction(){
	}
	
	/**
	 * This is the computation associated with vertices in our graph. Each vertex will do the following according to the inputs.
	 * The inputs should be received from neighboring vertices.
	 * @param integers
	 * @return
	 */
	public abstract int compute(ArrayList<Integer> integers);
	/**
	 * This is for computing the correct result of a function. This represents the global result that must be correct and 
	 * as such is used for checking if network function computation was successful.
	 * @param sources
	 * @return
	 */
	public abstract int direct_compute(HashSet<Source> sources);
	
}
