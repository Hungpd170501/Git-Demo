package controller;

import java.io.IOException;

import controller.student.StudentMainScreenController;
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
import model.UserRepository;
import model.Users;

public class loginFXMLController {
	private Stage stage;
	private Scene scene;
	private Parent root;

	@FXML
	private TextField studentName, adminName;

	@FXML
	private PasswordField studentPassword, adminPassword;
	

	
	@FXML
	public void loginStudent(ActionEvent event) throws IOException {
		if (studentName.getText().length() < 6 && studentPassword.getText().length() < 6) {
			if (studentName.getText() != null && studentPassword.getText() != null) {
				Users u = new Users(studentName.getText(), studentPassword.getText());
				if (UserRepository.checkUser(u)) {
					switchSceneStudent(event);
				}
				else showAlert();
			}
			else showAlert();
		} else {
			showAlert();
		}
	}

	public void switchSceneStudent(ActionEvent event) throws IOException{
		String userNamee=studentName.getText();
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/student/StudentMainScreen.fxml"));
		root = loader.load();
		StudentMainScreenController studentMainScreenController = loader.getController();
		studentMainScreenController.displayName(userNamee);
		stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();	
	}
	
	@FXML
	public void loginAdmin(ActionEvent event) throws IOException {
		if (adminName.getText().length() < 6 && adminPassword.getText().length() < 6) {
			if (adminName.getText() != null && adminPassword.getText() != null) {
				Users u = new Users(adminName.getText(), adminPassword.getText());
				if (UserRepository.checkUser(u)) {
					switchSceneAdmin(event);
				}
				else showAlert(); 
			}
			else showAlert();
		} else {
			showAlert();
		}
	}

	public void switchSceneAdmin(ActionEvent event) throws IOException{
		String adminNamee=adminName.getText();
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Scene2.fxml"));
		root = loader.load();
		Scene2Controller scene2Controller = loader.getController();
		scene2Controller.displayName(adminNamee);
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
