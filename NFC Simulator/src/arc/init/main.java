package arc.init;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import arc.controller.Visualizer;
import arc.core.Network;
import arc.ui.MainWindow;


public class main extends Application {
	public static MainWindow mainwindow;
	public static Visualizer visualizer;
	public static Network network;
	
	 public static void main(String[] args) {
	        launch(args);
	    }
	 
	@Override
    public void start(Stage primaryStage) {
	//The NFC simulator currently implements the greedy encoding solution.
	//Assume integer alphabet.
	//Network Creation, uniform unit edge capacity.
	
	//This starts the process of FXML-loading, including initalizing MainController, Model and 
	//the visualization engine. Check the MainController init method for specifics.
	mainwindow = new MainWindow();
	 
	Scene scene = new Scene(mainwindow.getRoot());
    primaryStage.setScene(scene);
    primaryStage.setTitle("Network Computation Simulator");
    primaryStage.show();
	 /*
	 JFrame frame = new JFrame("Simple Graph View");
	 frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	 frame.getContentPane().add(vv);
	 frame.pack();
	 frame.setVisible(true);
	 */

    }

	public static Network getNetwork() {
		return network;
	}

	public void setNetwork(Network network) {
		this.network = network;
	}

}
