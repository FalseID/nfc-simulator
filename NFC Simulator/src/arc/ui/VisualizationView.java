package arc.ui;

import java.awt.Dimension;

import arc.core.Network;
import edu.uci.ics.jung.algorithms.layout.AbstractLayout;
import edu.uci.ics.jung.algorithms.layout.CircleLayout;
import edu.uci.ics.jung.algorithms.layout.Layout;
import edu.uci.ics.jung.visualization.VisualizationViewer;
import edu.uci.ics.jung.visualization.control.CrossoverScalingControl;
import edu.uci.ics.jung.visualization.control.ScalingControl;
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
	
	public VisualizationView(Network network){
		this.layout = new CircleLayout(network.getGraph());
        
        this.layout.setSize(new Dimension(400,400)); // sets the initial size of the space
		// The BasicVisualizationServer<V,E> is parameterized by the edge types
		vv = new VisualizationViewer<Integer,String>(layout);
		refresh();
		ScalingControl visualizationViewerScalingControl
	         = new CrossoverScalingControl();
		visualizationViewerScalingControl.scale(vv, 1 / 1.1f, vv.getCenter());
		vv.scaleToLayout(visualizationViewerScalingControl);
		 
		vv.getRenderContext().setVertexLabelTransformer(new ToStringLabeller());
		vv.getRenderContext().setEdgeLabelTransformer(new ToStringLabeller());
		vv.getRenderer().getVertexLabelRenderer().setPosition(Position.CNTR);
	}
	
	public VisualizationViewer<Integer,String> getVisualizationViewer(){
		return vv;
	}
	
	public void refresh(){
		 //vv.setSize(new Dimension((int)controller.getVisual_pane().getPrefHeight(),
		//			(int)controller.getVisual_pane().getPrefWidth()));
		 vv.validate();
		 vv.repaint();
	}

	public Layout<Integer, String> getLayout() {
		return layout;
	}

}
