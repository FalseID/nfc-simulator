package arc.controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.GridPane;

/**
 * Controller for the input manager.
 * @author Janar
 *
 */
public class InputManagerController implements Initializable, ChildController{
	private MainController parent_controller;
	@FXML //  fx:id="grid"
	private GridPane grid;

	public void initialize(URL location, ResourceBundle resources) {
		
	}

	public void setParentController(MainController controller) {
		this.parent_controller=controller;
	}
	
}
