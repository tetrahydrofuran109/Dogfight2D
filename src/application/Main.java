package application;
	
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;

/**
 * 2D Dogfight main class, start the application
 * @author WangShuzheng
 */

public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
            Parent root = FXMLLoader.load(getClass().getResource("/View/StartWindow.fxml"));
            primaryStage.setTitle("2D Dogfight");
            primaryStage.setScene(new Scene(root));
            primaryStage.show(); 
            
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
