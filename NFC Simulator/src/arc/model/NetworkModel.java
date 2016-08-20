package arc.model;

import java.beans.PropertyChangeListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import javax.swing.event.SwingPropertyChangeSupport;

import com.google.common.base.Function;
import com.google.common.base.Supplier;

import edu.uci.ics.jung.algorithms.layout.AbstractLayout;
import edu.uci.ics.jung.graph.DirectedSparseGraph;
import edu.uci.ics.jung.graph.DirectedSparseMultigraph;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.Hypergraph;
import edu.uci.ics.jung.io.GraphIOException;
import edu.uci.ics.jung.io.GraphMLWriter;
import edu.uci.ics.jung.io.graphml.EdgeMetadata;
import edu.uci.ics.jung.io.graphml.GraphMLReader2;
import edu.uci.ics.jung.io.graphml.GraphMetadata;
import edu.uci.ics.jung.io.graphml.HyperEdgeMetadata;
import edu.uci.ics.jung.io.graphml.NodeMetadata;
import arc.core.Edge;
import arc.core.IntermediaryVertex;
import arc.core.Network;
import arc.core.Sink;
import arc.core.Source;
import arc.core.Vertex;
import arc.core.functions.ArithmeticSum;
import arc.core.messages.IntegerMessage;
import arc.core.messages.MessageType;
import arc.ui.visualization.VisualizationView;


/**
 * Keeps track of our computation network. Also manages saving and loading graphs.
 */
public class NetworkModel {
	private SwingPropertyChangeSupport propChange;
	private Network network;
	private File file; //This is for keeping track which file is currently open.
	private static final GraphMLWriter<Vertex, Edge> graphWriter = new GraphMLWriter<Vertex, Edge>();
	private static final Supplier <IntermediaryVertex> vertexFactory = new Supplier<IntermediaryVertex>() {
        public IntermediaryVertex get() {
            return new IntermediaryVertex();
        }
    };
    
    /**
     * These suppliers provide methods for the controller/view hybrid VisualizationView 
     * to create its own elements of the graph with ModalMouse while updating our model here.
     */
	private static Supplier <Edge> edgeFactory = new Supplier<Edge>() {
        public Edge get() {
            return new Edge(1);
        }
    };
    
    private static Supplier <Source> sourceFactory = new Supplier<Source>() {
        public Source get() {
            return new Source(new IntegerMessage(0));
        }
    };
    
    private static Supplier <Sink> sinkFactory = new Supplier<Sink>() {
        public Sink get() {
            return new Sink();
        }
    };
	
	public NetworkModel(){
		super();
	    this.network= new Network(new ArithmeticSum(), MessageType.INTEGER);
	    this.propChange = new SwingPropertyChangeSupport(this);
	}
	
	 public void addListener(PropertyChangeListener prop) {
	        propChange.addPropertyChangeListener(prop);
	 }

	public Network getNetwork() {
		return network;
	}
	
	@Override
	public String toString() {
		return "MainModel [network=" + network + "]";
	}

	public static Supplier<IntermediaryVertex> getVertexFactory() {
		return vertexFactory;
	}

	public static Supplier<Edge> getEdgeFactory() {
		return edgeFactory;
	}

	public void save(File graph_file, final AbstractLayout layout) throws IOException {
		if(graph_file==null){
			return;
		}
		
		
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(graph_file)));
		graphWriter.addGraphData("Target function", null, "0",
			    new Function<Hypergraph<Vertex, Edge>, String>() {
			        public String apply(Hypergraph g) {
			            return ((Network)g).getTargetFunction().toString();
			        }
			    }
			);
		
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
		
		graphWriter.addVertexData("type", null, "0",
			    new Function<Vertex, String>() {
			        public String apply(Vertex v) {
			            return v.getClass().getSimpleName();
			        }
			    }
			);
		
		graphWriter.save(this.network, out);
		this.setFile(graph_file);
		
	}
	/**
	 * Clear model and visualization and reset Vertex id counter.
	 */
	public void clear(){
		//Clear the model and then the view.
		//We reset the AtomicInteger for vertex ids back to zero.
		IntermediaryVertex.resetNextId();
		Source.resetNextId();
		Sink.resetNextId();
		this.setNetwork(new Network(new ArithmeticSum(), MessageType.INTEGER));
		
	}
	public void load(File graph_file, final AbstractLayout layout) throws IOException, GraphIOException {
		if(graph_file==null){
			return;
		}
		clear();
		
		BufferedReader fileReader = new BufferedReader(new FileReader(graph_file));
		
		/* Create the Graph Function */
		Function<GraphMetadata, Graph<Vertex, Edge>>
		graphFunction = new Function<GraphMetadata,
		      Graph<Vertex, Edge>>() {
				public Graph<Vertex, Edge>
			      apply(GraphMetadata metadata) {
					if(metadata.getProperty("Target function").equals("Arithmetic Sum")){
						return new Network(new ArithmeticSum(), MessageType.INTEGER);
			    	}
					else{
						return new Network(null, MessageType.INTEGER);
					}
		  };
		};
		
		/* Create the Vertex Function */
		Function<NodeMetadata, Vertex> vertexFunction
		= new Function<NodeMetadata, Vertex>() {
		    public Vertex apply(NodeMetadata metadata) {
		    	if(metadata.getProperty("type").equals("Sink")){
		    		Sink v = sinkFactory.get();
				    v.setX(Double.parseDouble(metadata.getProperty("x")));
				    v.setY(Double.parseDouble(metadata.getProperty("y")));
				    return v;
		    	}
		    	else if(metadata.getProperty("type").equals("Source")){
		    		 Source v = sourceFactory.get();
				     v.setX(Double.parseDouble(metadata.getProperty("x")));
				     v.setY(Double.parseDouble(metadata.getProperty("y")));
				     return v;
		    	}
		    	else{
		    		Vertex v = vertexFactory.get();
				    v.setX(Double.parseDouble(metadata.getProperty("x")));
				    v.setY(Double.parseDouble(metadata.getProperty("y")));
				    return v;
		    	}
		       
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
		 this.setFile(graph_file);
		 this.setNetwork((Network)graphReader.readGraph());
	}

	public void setNetwork(Network new_network) {
		//Notify listeners that network has changed.
		propChange.firePropertyChange("network", this.network, new_network);
		this.network = new_network;
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public static Supplier<Source> getSourceFactory() {
		return sourceFactory;
	}

	public static Supplier<Sink> getSinkFactory() {
		return sinkFactory;
	}

}
