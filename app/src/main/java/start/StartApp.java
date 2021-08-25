package start;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class StartApp extends Application {

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage stage){
//		
		Parent root = null;
		try {
			root = FXMLLoader.load(getClass().getResource("/fxml/loginFXML.fxml"));
		} catch (IOException e) {
			e.printStackTrace();
		}
//		
		Scene scene = new Scene(root);
		String css = this.getClass().getResource("/style/application.css").toExternalForm();
		scene.getStylesheets().add(css);
		Image icon = new Image("/icon/icon.png");
		stage.getIcons().add(icon);
		stage.setTitle("Demo JavaFX Program");
		stage.setScene(scene);
		stage.setX(300);
		stage.setY(100);
		stage.show();
		stage.setOnCloseRequest(event -> {
			event.consume();
			logout(stage);
		});
	}

	
	
	public void logout(Stage stage) {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Logout");
		alert.setHeaderText("You're about to exit!");
		alert.setContentText("Do you want to exit?:");
		if (alert.showAndWait().get() == ButtonType.OK) {
			stage.close();
		}
	}


}