package arc.controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import arc.core.NetworkEncoder;
import arc.core.Source;
import arc.core.messages.IntegerMessage;
import arc.core.messages.MessageType;
import arc.model.NetworkModel;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * Controller for the input manager.
 * @author Janar
 *
 */
public class InputManagerController implements Initializable{
	private Map<Source, TextField> input_map = new HashMap<Source, TextField>();
	@FXML
	private AnchorPane root_pane;
	@FXML //  fx:id="vbox"
	private VBox vbox;

	public void initialize(URL location, ResourceBundle resources) {
	}
	
	/*
	 * Loading the contents of this window dynamically according to the model.
	 */
	public void loadDynamicContent(final MainController mainController){
		final NetworkModel mainmodel = mainController.getMainmodel();
		final List<Source> sources = mainmodel.getNetwork().getSources();
		
		Collections.sort(sources);
		
		for (Source s : sources){
			TextField textField = new TextField(s.getInput().toString());
			Label textLabel = new Label("Source " + s.getLabel() + ":");
			
			HBox hbox = new HBox();
			hbox.getChildren().addAll(textLabel, textField);
			
			this.input_map.put(s, textField);
			this.vbox.getChildren().add(hbox);
		}
		
		final Button confirm = new Button("Confirm");
		this.vbox.getChildren().add(confirm);
		
		confirm.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
        		for(Source s: sources){
        			
        			String text = input_map.get(s).getText();
        			
        			try{
        				s.getInput().setMessage(text);
        			}catch(NumberFormatException e){
        				Alert alert = new Alert(AlertType.ERROR);
        				alert.setTitle("Input Formatting Error!");
        				alert.setHeaderText("Incorrect input format.");
        				alert.setContentText("One or more fields contain inputs incompatible with the network message type.");
        				alert.showAndWait();
        				return;
        			}
        			
        			mainController.update_visual();
        			closeWindow();
        		}
            }
        });
	}
	
	private void closeWindow(){
		Stage stage = (Stage) vbox.getScene().getWindow();
		stage.close();
	}
}
