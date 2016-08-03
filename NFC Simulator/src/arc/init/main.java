package arc.init;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import arc.core.Network;
import arc.ui.MainWindow;


public class main extends Application {
	//This starts the process of FXML-loading, including initalizing MainController, Model and 
	//the visualization engine. Check the MainController init method for specifics.
	public static final MainWindow mainwindow = new MainWindow();
	
	 public static void main(String[] args) {
	        launch(args);
	    }
	 
	@Override
    public void start(Stage primaryStage) {

	Scene scene = new Scene(mainwindow.getRoot());
    primaryStage.setScene(scene);
    primaryStage.setTitle("Network Computation Simulator");
    primaryStage.show();
    }

}
