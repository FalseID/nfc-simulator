package arc.ui;

import java.awt.Dimension;
import java.awt.geom.Point2D;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import com.google.common.base.Function;
import com.google.common.base.Supplier;

import arc.core.IntermediaryVertex;
import arc.core.Network;
import arc.core.Sink;
import arc.core.Source;
import arc.core.Vertex;
import arc.core.Edge;
import arc.visualization.NetworkEditingMouse;
import edu.uci.ics.jung.algorithms.layout.Layout;
import edu.uci.ics.jung.algorithms.layout.StaticLayout;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.visualization.VisualizationViewer;
import edu.uci.ics.jung.visualization.control.CrossoverScalingControl;
import edu.uci.ics.jung.visualization.control.ScalingControl;
import edu.uci.ics.jung.visualization.control.ModalGraphMouse.Mode;
import edu.uci.ics.jung.visualization.decorators.ToStringLabeller;
import edu.uci.ics.jung.visualization.renderers.Renderer.VertexLabel.Position;

/**
 * Creates the swing component containing the network visualization.
 * This view also serves as a controller.
 * @author Janar
 *
 */
public class VisualizationView implements PropertyChangeListener {
	private Layout<Vertex, Edge> layout;
	private VisualizationViewer<Vertex, Edge> vv;
	private NetworkEditingMouse modalMouse;
	/*
	 * Following are required for edge and vertex creation. They are received from the model and should never change.
	 */
	private final Supplier<IntermediaryVertex> vertexFactory;
	private final Supplier<Edge> edgeFactory;
	private final Supplier<Source> sourceFactory;
	private final Supplier<Sink> sinkFactory;
	
	public VisualizationView(Network network, Supplier<IntermediaryVertex> vertexFactory, 
			Supplier<Edge> edgeFactory, Supplier<Source> sourceFactory, Supplier<Sink> sinkFactory){
		super();
		this.vertexFactory = vertexFactory;
		this.edgeFactory = edgeFactory;
		this.sourceFactory = sourceFactory;
		this.sinkFactory = sinkFactory;
		initialize(network);
	}
	
	public void initialize(Network network){
		this.layout = new StaticLayout<Vertex, Edge>(network.getGraph(), new Function<Vertex, Point2D>(){
			//This layout has a Function for associating vertex coordinates with actual points on the graph.
			public Point2D apply(Vertex v) {
			       Point2D p = new Point2D.Double(v.getX(), v.getY());
			       return p;
			   }   
		});
        this.layout.setSize(new Dimension(400,400)); // sets the initial size of the space
		// The BasicVisualizationServer<V,E> is parameterized by the edge types
		vv = new VisualizationViewer<Vertex, Edge>(layout);
		
		ScalingControl visualizationViewerScalingControl
	         = new CrossoverScalingControl();
		visualizationViewerScalingControl.scale(vv, 1 / 1.1f, vv.getCenter());
		vv.scaleToLayout(visualizationViewerScalingControl);
		 
		vv.getRenderContext().setVertexLabelTransformer(new ToStringLabeller());
		vv.getRenderContext().setEdgeLabelTransformer(new ToStringLabeller());
		vv.getRenderer().getVertexLabelRenderer().setPosition(Position.CNTR);
		refresh();
		
		this.modalMouse = new NetworkEditingMouse(vv.getRenderContext(), 
				 this.vertexFactory, 
				 this.edgeFactory,
				 this.sourceFactory,
				 this.sinkFactory);
		this.modalMouse.setMode(Mode.TRANSFORMING);
		this.vv.setGraphMouse(this.modalMouse);
		this.vv.addKeyListener(this.modalMouse.getModeKeyListener());
	}
	
	public VisualizationViewer<Vertex, Edge> getVisualizationViewer(){
		return vv;
	}
	
	/**
	 * Sets a new graph to be visualized. Don't forget to call refresh() to repaint the viewer.
	 * @param network
	 */
	public void setGraph(Network network){
		this.layout.setGraph((Graph)network.getGraph());
	}
	
	public void setModalMouseMode(Mode mode) {
		this.modalMouse.setMode(mode);
	}
	
	public void refresh(){
		vv.validate();
		vv.repaint();
	}

	public Layout<Vertex, Edge> getLayout() {
		return layout;
	}
	
	/**
	 * Listens to network changes in the model.
	 */
	public void propertyChange(PropertyChangeEvent evt) {
		String propName = evt.getPropertyName();
        Object newNetwork = evt.getNewValue();
        if(propName.equals("network")){
        	initialize((Network)newNetwork);
		}
		
	}

}
