package arc.controller;

import java.net.URL;
import java.util.ResourceBundle;

import edu.uci.ics.jung.visualization.control.ModalGraphMouse.Mode;
import arc.model.NetworkModel;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.embed.swing.SwingNode;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SplitPane;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.AnchorPane;

public class MainController implements Initializable{
	private static NetworkModel mainmodel;
	private static Visualizer visualizer;
	@FXML //  fx:id="root_pane"
	private SplitPane root_pane;
	@FXML //  fx:id="visual_node"
	private SwingNode visual_node;
	@FXML //  fx:id="visual_pane"
	private AnchorPane visual_pane;
	@FXML //  fx:id="quit_button"
	private MenuItem quit_button;
	@FXML //  fx:id="picking_button"
	private ToggleButton picking_button;
	@FXML //  fx:id="editing_button"
	private ToggleButton editing_button;
	@FXML //  fx:id="transforming_button"
	private ToggleButton transforming_button;

	/**Runs once all injections are complete.
	 * Initalizes the main model.
	 **/
	public void initialize(URL arg0, ResourceBundle arg1) {
		mainmodel = new NetworkModel();
		visualizer = new Visualizer(mainmodel, this);
		visual_node.setContent(visualizer.getVisualization());
		
		
		root_pane.widthProperty().addListener(new ChangeListener<Number>() {
		    public void changed(ObservableValue<? extends Number> observableValue, Number oldSceneWidth, Number newSceneWidth) {
		        visualizer.resize();
		    }
		});
		root_pane.heightProperty().addListener(new ChangeListener<Number>() {
		    public void changed(ObservableValue<? extends Number> observableValue, Number oldSceneHeight, Number newSceneHeight) {
		    	visualizer.resize();
		    }
		});
		
		quit_button.setOnAction(new EventHandler<ActionEvent>() {

            public void handle(ActionEvent event) {
            	Platform.exit();
            }
        });
		
		picking_button.setOnAction(new EventHandler<ActionEvent>() {

            public void handle(ActionEvent event) {
            	Visualizer.setModalMouseMode(Mode.PICKING);
            }
        });
		
		editing_button.setOnAction(new EventHandler<ActionEvent>() {

            public void handle(ActionEvent event) {
            	Visualizer.setModalMouseMode(Mode.EDITING);
            }
        });
		
		transforming_button.setOnAction(new EventHandler<ActionEvent>() {

            public void handle(ActionEvent event) {
            	Visualizer.setModalMouseMode(Mode.TRANSFORMING);
            }
        });
		
		
	}

	public AnchorPane getVisual_pane() {
		return visual_pane;
	}

	public void setVisual_pane(AnchorPane visual_pane) {
		this.visual_pane = visual_pane;
	}

}
