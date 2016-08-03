package arc.ui;

import java.awt.Dimension;
import java.awt.LayoutManager;
import java.awt.geom.Point2D;

import com.google.common.base.Function;
import com.google.common.base.Supplier;

import arc.core.Network;
import arc.core.Vertex;
import arc.core.Edge;
import edu.uci.ics.jung.algorithms.layout.AbstractLayout;
import edu.uci.ics.jung.algorithms.layout.CircleLayout;
import edu.uci.ics.jung.algorithms.layout.Layout;
import edu.uci.ics.jung.algorithms.layout.StaticLayout;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.visualization.VisualizationViewer;
import edu.uci.ics.jung.visualization.control.CrossoverScalingControl;
import edu.uci.ics.jung.visualization.control.EditingModalGraphMouse;
import edu.uci.ics.jung.visualization.control.ScalingControl;
import edu.uci.ics.jung.visualization.control.ModalGraphMouse.Mode;
import edu.uci.ics.jung.visualization.decorators.ToStringLabeller;
import edu.uci.ics.jung.visualization.renderers.Renderer.VertexLabel.Position;

/**
 * Creates the swing component containing the network visualization.
 * @author Janar
 *
 */
public class VisualizationView {
	private final Layout<Integer, String> layout;
	private final VisualizationViewer<Integer,String> vv;
	private EditingModalGraphMouse<Integer, String> modalMouse;
	
	public VisualizationView(Network network, Supplier VertexFactory, Supplier EdgeFactory){
		this.layout = new StaticLayout(network.getGraph(), new Function<Vertex, Point2D>(){
			//This layout has a Function for associating vertex coordinates with actual points on the graph.
			public Point2D apply(Vertex v) {
			       Point2D p = new Point2D.Double(v.getX(), v.getY());
			       return p;
			   }   
		});
        this.layout.setSize(new Dimension(400,400)); // sets the initial size of the space
		// The BasicVisualizationServer<V,E> is parameterized by the edge types
		vv = new VisualizationViewer<Integer,String>(layout);
		
		ScalingControl visualizationViewerScalingControl
	         = new CrossoverScalingControl();
		visualizationViewerScalingControl.scale(vv, 1 / 1.1f, vv.getCenter());
		vv.scaleToLayout(visualizationViewerScalingControl);
		 
		vv.getRenderContext().setVertexLabelTransformer(new ToStringLabeller());
		vv.getRenderContext().setEdgeLabelTransformer(new ToStringLabeller());
		vv.getRenderer().getVertexLabelRenderer().setPosition(Position.CNTR);
		refresh();
		
		this.modalMouse = new EditingModalGraphMouse(vv.getRenderContext(), 
				 VertexFactory, 
				 EdgeFactory);
		this.modalMouse.setMode(Mode.TRANSFORMING);
		this.vv.setGraphMouse(this.modalMouse);
		this.vv.addKeyListener(this.modalMouse.getModeKeyListener());
	}
	
	public VisualizationViewer<Integer,String> getVisualizationViewer(){
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

	public Layout<Integer, String> getLayout() {
		return layout;
	}

}
