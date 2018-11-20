import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Class Main - Main program
 * 
 * @author Andrew P. Albanese
 *
 */
public class AirPockeyMain extends Application { 
	
	@Override
	public void start(Stage primaryStage) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/AirPockey.fxml"));
			Parent root = loader.load();
			AirPockeyController controller = loader.getController();
	        Scene scene = new Scene(root);
	        controller.initialize(scene);
	        
	        primaryStage.setTitle("Air Pockey");
	        primaryStage.setScene(scene);
	        primaryStage.show();
	        primaryStage.setResizable(false);
	        
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
