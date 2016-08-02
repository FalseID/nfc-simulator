package arc.controller;

import arc.model.NetworkModel;

import java.awt.Dimension;

import javax.swing.JComponent;

import com.google.common.base.Supplier;

import arc.core.Edge;
import arc.core.Vertex;
import edu.uci.ics.jung.algorithms.layout.CircleLayout;
import edu.uci.ics.jung.algorithms.layout.Layout;
import edu.uci.ics.jung.visualization.VisualizationViewer;
import edu.uci.ics.jung.visualization.control.CrossoverScalingControl;
import edu.uci.ics.jung.visualization.control.EditingModalGraphMouse;
import edu.uci.ics.jung.visualization.control.ModalGraphMouse.Mode;
import edu.uci.ics.jung.visualization.control.ScalingControl;
import edu.uci.ics.jung.visualization.decorators.ToStringLabeller;
import edu.uci.ics.jung.visualization.renderers.Renderer.VertexLabel.Position;
/**
 * Creates the JComponent with network visualisation.
 * @author Janar
 *
 */
public class Visualizer {
	private static NetworkModel mainmodel;
	private static MainController controller;
	private static Supplier <Vertex> vertexFactory;
	private static Supplier<Edge> edgeFactory;
	private static EditingModalGraphMouse<Integer, String> modalMouse;
	private static Layout<Integer, String> layout;
	private VisualizationViewer<Integer,String> vv;
	
	
	
	public Visualizer(NetworkModel mainmodel, MainController controller){
		super();
		Visualizer.mainmodel = mainmodel;
		Visualizer.controller = controller;
		
		//Factories for adding vertices and edges.
		vertexFactory = new Supplier<Vertex>() {
            public Vertex get() {
                return new Vertex();
            }
        };
        edgeFactory = new Supplier<Edge>() {
            public Edge get() {
                return new Edge(1);
            }
        };
        
        layout = new CircleLayout(mainmodel.getCurrent_network());
        
        layout.setSize(new Dimension(400,400)); // sets the initial size of the space
		 // The BasicVisualizationServer<V,E> is parameterized by the edge types
		 vv = new VisualizationViewer<Integer,String>(layout);
		 resize();
		 ScalingControl visualizationViewerScalingControl
	         = new CrossoverScalingControl();
		 visualizationViewerScalingControl.scale(vv, 1 / 1.1f, vv.getCenter());
		 vv.scaleToLayout(visualizationViewerScalingControl);
		 
		Visualizer.modalMouse = new EditingModalGraphMouse(vv.getRenderContext(), 
		             vertexFactory, 
		             edgeFactory);
		modalMouse.setMode(Mode.TRANSFORMING);
		vv.setGraphMouse(modalMouse);
		vv.addKeyListener(modalMouse.getModeKeyListener());
		 
		vv.getRenderContext().setVertexLabelTransformer(new ToStringLabeller());
		vv.getRenderContext().setEdgeLabelTransformer(new ToStringLabeller());
		vv.getRenderer().getVertexLabelRenderer().setPosition(Position.CNTR);
	}
	
	public JComponent getVisualization(){
		return vv;
	}
	
	public void resize(){
		 vv.setSize(new Dimension((int)controller.getVisual_pane().getPrefHeight(),
					(int)controller.getVisual_pane().getPrefWidth()));
		 vv.validate();
		 vv.repaint();
	}

	public static void setModalMouseMode(Mode mode) {
		Visualizer.modalMouse.setMode(mode);
	}
	
	
	
}
