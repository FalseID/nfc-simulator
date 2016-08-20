package arc.ui;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import arc.controller.InputManagerController;
import arc.controller.MainController;

public class InputManagerWindow {
		private Parent root;
		
		public InputManagerWindow(MainController controller){
			try {
				FXMLLoader loader = new FXMLLoader(MainWindow.class.getResource("InputManagerView.fxml"));
				this.root = loader.load();
				((InputManagerController)loader.getController()).loadDynamicContent(controller);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}

		public Parent getRoot() {
			return root;
		}
}
