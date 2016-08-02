package arc.model;

import arc.core.Network;
import arc.functions.ArithmeticSum;

/**
 * We save and load objects from a file.
 */
public class NetworkModel {
	//Model keeps track of our current computation network.
	private Network current_network;
	
	public NetworkModel(){
		super();
	    this.current_network= new Network(new ArithmeticSum());
	}

	public Network getCurrent_network() {
		return current_network;
	}


	public void setCurrent_network(Network current_network) {
		this.current_network = current_network;
	}



	@Override
	public String toString() {
		return "MainModel [current_network=" + current_network + "]";
	}

}
