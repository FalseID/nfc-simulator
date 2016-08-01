package arc.model;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;

import arc.core.Edge;
import arc.core.Network;
import arc.core.Sink;
import arc.core.Source;
import arc.core.Vertex;
import arc.functions.ArithmeticSum;

/**
 * We save and load objects from a file.
 */
public class MainModel {
	//Model keeps track of available networks.
	private Network current_network;
	
	public MainModel(){
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
