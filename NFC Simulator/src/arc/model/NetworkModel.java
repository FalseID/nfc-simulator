package arc.model;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import com.google.common.base.Function;
import com.google.common.base.Supplier;

import edu.uci.ics.jung.algorithms.layout.AbstractLayout;
import edu.uci.ics.jung.graph.DirectedSparseGraph;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.io.GraphIOException;
import edu.uci.ics.jung.io.GraphMLWriter;
import edu.uci.ics.jung.io.graphml.EdgeMetadata;
import edu.uci.ics.jung.io.graphml.GraphMLReader2;
import edu.uci.ics.jung.io.graphml.GraphMetadata;
import edu.uci.ics.jung.io.graphml.HyperEdgeMetadata;
import edu.uci.ics.jung.io.graphml.NodeMetadata;
import arc.controller.VisualizationController;
import arc.core.Edge;
import arc.core.Network;
import arc.core.Vertex;
import arc.functions.ArithmeticSum;


/**
 * Keeps track of our computation network. Also manages saving and loading graphs.
 */
public class NetworkModel {
	private Network current_network;
	private static final GraphMLWriter<Vertex, Edge> graphWriter = new GraphMLWriter<Vertex, Edge>();
	private static final Supplier <Vertex> vertexFactory = new Supplier<Vertex>() {
        public Vertex get() {
            return new Vertex();
        }
    };
	private static Supplier <Edge> edgeFactory = new Supplier<Edge>() {
        public Edge get() {
            return new Edge(1);
        }
    };
	
	public NetworkModel(){
		super();
	    this.current_network= new Network(new ArithmeticSum(), new DirectedSparseGraph<Vertex, Edge>());
	    
	    
        
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

	public static Supplier<Vertex> getVertexFactory() {
		return vertexFactory;
	}

	public static Supplier<Edge> getEdgeFactory() {
		return edgeFactory;
	}

	public void saveAs(File graph_file, final AbstractLayout layout) throws IOException {
		if(graph_file==null){
			return;
		}
		
		
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(graph_file)));
		
		graphWriter.addVertexData("x", null, "0",
			    new Function<Vertex, String>() {
			        public String apply(Vertex v) {
			            return Double.toString(layout.getX(v));
			        }
			    }
			);
			 
			graphWriter.addVertexData("y", null, "0",
			    new Function<Vertex, String>() {
			        public String apply(Vertex v) {
			            return Double.toString(layout.getY(v));
			       }
			    }
			);
		
		graphWriter.save(this.current_network.getGraph(), out);
		
	}
	
	public void clear(){
		this.current_network = new Network(new ArithmeticSum(), new DirectedSparseGraph<Vertex, Edge>());
	}
	
	public void load(File graph_file, final AbstractLayout layout) throws IOException, GraphIOException {
		if(graph_file==null){
			return;
		}
		System.out.println("loading");
		
		BufferedReader fileReader = new BufferedReader(new FileReader(graph_file));
		
		/* Create the Graph Function */
		Function<GraphMetadata, Graph<Vertex, Edge>>
		graphFunction = new Function<GraphMetadata,
		      Graph<Vertex, Edge>>() {
				public Graph<Vertex, Edge>
			      apply(GraphMetadata metadata) {
				  return new DirectedSparseGraph<Vertex, Edge>();
		  };
		};
		
		/* Create the Vertex Function */
		Function<NodeMetadata, Vertex> vertexFunction
		= new Function<NodeMetadata, Vertex>() {
		    public Vertex apply(NodeMetadata metadata) {
		        Vertex v = vertexFactory.get();
		        //v.setX(Double.parseDouble(metadata.getProperty("x")));
		        //v.setY(Double.parseDouble(metadata.getProperty("y")));
		        return v;
		    }
		};
		
		/* Create the Edge Function */
		Function<EdgeMetadata, Edge> edgeFunction =
		 new Function<EdgeMetadata, Edge>() {
		     public Edge apply(EdgeMetadata metadata) {
		         Edge e = edgeFactory.get();
		         return e;
		     }
		 };
		 
		 /* Create the Hyperedge Function */
		 Function<HyperEdgeMetadata, Edge> hyperEdgeFunction
		 = new Function<HyperEdgeMetadata, Edge>() {
		      public Edge apply(HyperEdgeMetadata metadata) {
		          Edge e = edgeFactory.get();
		          return e;
		      }
		 };
		 
		 GraphMLReader2<Graph<Vertex, Edge>, Vertex, Edge>
		 graphReader = new
		 GraphMLReader2<Graph<Vertex, Edge>, Vertex, Edge>
		       (fileReader, graphFunction, vertexFunction,
		        edgeFunction, hyperEdgeFunction);
		 this.current_network.setGraph((DirectedSparseGraph<Vertex, Edge>) graphReader.readGraph());
		 System.out.println(current_network.toString());
		 
	}

}
