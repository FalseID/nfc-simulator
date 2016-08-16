package arc.controller;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import edu.uci.ics.jung.algorithms.layout.AbstractLayout;
import edu.uci.ics.jung.io.GraphIOException;
import edu.uci.ics.jung.visualization.control.ModalGraphMouse.Mode;
import arc.model.NetworkModel;
import arc.ui.VisualizationView;
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
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;

public class MainController implements Initializable{
	private final NetworkModel mainmodel = new NetworkModel();
	private final VisualizationView visual_view = new VisualizationView(mainmodel.getCurrent_network(),
			mainmodel.getVertexFactory(), mainmodel.getEdgeFactory(), 
			mainmodel.getSourceFactory(), mainmodel.getSinkFactory());
	@FXML
	private SplitPane root_pane;
	@FXML //  fx:id="visual_node"
	private SwingNode visual_node;
	@FXML //  fx:id="visual_pane"
	private AnchorPane visual_pane;
	@FXML //  fx:id="quit_button"
	private MenuItem quit_button;
	@FXML //  fx:id="save_button"
	private MenuItem save_button;
	@FXML //  fx:id="save_as_button"
	private MenuItem save_as_button;
	@FXML //  fx:id="load_button"
	private MenuItem load_button;
	@FXML //  fx:id="clear_button"
	private MenuItem clear_button;
	@FXML //  fx:id="picking_button"
	private ToggleButton picking_button;
	@FXML //  fx:id="editing_button"
	private ToggleButton editing_button;
	@FXML //  fx:id="transforming_button"
	private ToggleButton transforming_button;
	@FXML //  fx:id="mode_group"
	private ToggleGroup mode_group;
	

	/**Runs once all injections are complete.
	 * Initalizes the main model.
	 **/
	public void initialize(URL arg0, ResourceBundle arg1) {
		//Adding visual_controller's visualization view to our swing node.
		update();
		//Make visualization view listen to mainmodel.
		mainmodel.addListener(visual_view);
		
		
		root_pane.widthProperty().addListener(new ChangeListener<Number>() {
		    public void changed(ObservableValue<? extends Number> observableValue, Number oldSceneWidth, Number newSceneWidth) {
		    	visual_view.refresh();
		    }
		});
		
		root_pane.heightProperty().addListener(new ChangeListener<Number>() {
		    public void changed(ObservableValue<? extends Number> observableValue, Number oldSceneHeight, Number newSceneHeight) {
		    	visual_view.refresh();
		    }
		});
		
		quit_button.setOnAction(new EventHandler<ActionEvent>() {

            public void handle(ActionEvent event) {
            	Platform.exit();
            }
        });
		
		clear_button.setOnAction(new EventHandler<ActionEvent>() {

            public void handle(ActionEvent event) {
            	mainmodel.clear();
            	update();
            }
        });
		
		save_button.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
            	if(mainmodel.getCurrent_file()==null){
            		saveWithChooser();
            	}else{
            		saveWithoutChooser(mainmodel.getCurrent_file());
            	}
            	
            }
        });
		
		save_as_button.setOnAction(new EventHandler<ActionEvent>() {

            public void handle(ActionEvent event) {
            	saveWithChooser();
            }
        });
		
		load_button.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
            	FileChooser fileChooser = new FileChooser();
            	fileChooser.setTitle("Open Graph .xml File");
            	File workingDirectory = new File(System.getProperty("user.dir"));
            	fileChooser.setInitialDirectory(workingDirectory);
            	fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("XML files (*.xml)", "*.xml"));
            	File graph_file = fileChooser.showOpenDialog(root_pane.getScene().getWindow());
            	if(graph_file == null) return;
            	try {
            		mainmodel.clear();
					mainmodel.load(graph_file, ((AbstractLayout)visual_view.getLayout()));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            	catch (GraphIOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            	update();
            	
            	
            }
        });
		
		picking_button.setOnAction(new EventHandler<ActionEvent>() {

            public void handle(ActionEvent event) {
            	visual_view.setModalMouseMode(Mode.PICKING);
            }
        });
		
		editing_button.setOnAction(new EventHandler<ActionEvent>() {

            public void handle(ActionEvent event) {
            	visual_view.setModalMouseMode(Mode.EDITING);
            }
        });
		
		transforming_button.setOnAction(new EventHandler<ActionEvent>() {

            public void handle(ActionEvent event) {
            	visual_view.setModalMouseMode(Mode.TRANSFORMING);
            }
        });
		
		
	}
	/**
	 * Updates the swing node content and resets togglegroup selection.
	 */
	public void update(){
		visual_node.setContent(visual_view.getVisualizationViewer());
    	mode_group.selectToggle(transforming_button);
	}
	
	private void saveWithChooser(){
		FileChooser fileChooser = new FileChooser();
    	fileChooser.setTitle("Save Graph File");
    	File workingDirectory = new File(System.getProperty("user.dir"));
    	fileChooser.setInitialDirectory(workingDirectory);
    	fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("XML files (*.xml)", "*.xml"));
    	File graph_file = fileChooser.showSaveDialog(root_pane.getScene().getWindow());
    	
    	try {
			mainmodel.save(graph_file, ((AbstractLayout)visual_view.getLayout()));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void saveWithoutChooser(File graph_file){
    	try {
			mainmodel.save(graph_file, ((AbstractLayout)visual_view.getLayout()));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	

	public AnchorPane getVisual_pane() {
		return visual_pane;
	}

	public void setVisual_pane(AnchorPane visual_pane) {
		this.visual_pane = visual_pane;
	}

}
