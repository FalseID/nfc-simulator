package arc.controller;

import arc.model.NetworkModel;
import arc.ui.VisualizationView;

import edu.uci.ics.jung.visualization.control.EditingModalGraphMouse;
import edu.uci.ics.jung.visualization.control.ModalGraphMouse.Mode;


/**
 * Manages the user interaction with the visualization component.
 * @author Janar
 *
 */
public class VisualizationController {
	private VisualizationView visual_view;
	private EditingModalGraphMouse<Integer, String> modalMouse;
	private final NetworkModel mainmodel;
	
	
	
	
	public VisualizationController(NetworkModel mainmodel){
		super();
		this.mainmodel = mainmodel;
		this.visual_view = new VisualizationView(mainmodel.getCurrent_network());
		this.modalMouse = new EditingModalGraphMouse(this.visual_view.getVisualizationViewer().getRenderContext(), 
		             mainmodel.getVertexFactory(), 
		             mainmodel.getEdgeFactory());
		this.modalMouse.setMode(Mode.TRANSFORMING);
		this.visual_view.getVisualizationViewer().setGraphMouse(this.modalMouse);
		this.visual_view.getVisualizationViewer().addKeyListener(this.modalMouse.getModeKeyListener());
	}

	public void setModalMouseMode(Mode mode) {
		this.modalMouse.setMode(mode);
	}

	public VisualizationView getVisual_view() {
		return visual_view;
	}
	/**
	 * Update the visualization according to a model.
	 * @param mainmodel
	 */
	public void updateVisual_view() {
		this.visual_view = new VisualizationView(mainmodel.getCurrent_network());
	}
	
	
	
}
