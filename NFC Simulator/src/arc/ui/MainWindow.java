package arc.ui;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;


public class MainWindow {
	private Parent root;
	
	public MainWindow(){
		try {
			this.root = FXMLLoader.load(MainWindow.class.getResource("MainWindow.fxml"));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	public Parent getRoot() {
		return root;
	}

	public void setRoot(Parent root) {
		this.root = root;
	}
}

