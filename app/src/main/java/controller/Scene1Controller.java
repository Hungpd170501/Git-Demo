package controller;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Users;
import model.UserRepository;


public class Scene1Controller {

	private Stage stage;
	private Scene scene;
	private Parent root;

	@FXML
	private TextField userName;

	@FXML
	private PasswordField password;

	@FXML
	public void login(ActionEvent event) throws IOException {
		if (userName.getText().length() < 6 && password.getText().length() < 6) {
			if (userName.getText() != null && password.getText() != null) {
				Users u = new Users(userName.getText(), password.getText());
				if (UserRepository.checkUser(u)) {
					switchScene(event);
				}
				else showAlert();
			}
			else showAlert();
		} else {
			showAlert();
		}
	}

	public void switchScene(ActionEvent event) throws IOException{
		String userNamee=userName.getText();
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Scene2.fxml"));
		root = loader.load();
		Scene2Controller scene2Controller = loader.getController();
		scene2Controller.displayName(userNamee);
		stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();	
	}
	
	void showAlert() {
		Alert alert = new Alert(Alert.AlertType.ERROR);
		alert.setTitle("Login FAIL!");
		alert.setHeaderText("invalid username or password!!");
		alert.show();		
	}
	
}
