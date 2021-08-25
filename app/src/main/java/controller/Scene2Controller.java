package controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import model.DatabaseConnection;
import model.QuestionAndAns;
import model.TableAnswer;
import model.TableAnswerImplementation;

public class Scene2Controller {
	private Stage stage;
	private Scene scene;
	private Parent root;

	@FXML
	private Label nameLabel;

	@FXML
	private Button insertButton;

	@FXML
	Pane ScenePane;

	@FXML
	private TextArea questionText;

	@FXML
	private RadioButton rButton1, rButton2, rButton3, rButton4;

	@FXML
	private TextField answer1, answer2, answer3, answer4;

	private String name;

	public void displayName(String username) {
		nameLabel.setText(username);
		name = username;
	}

	@FXML
	public void logout(ActionEvent event) {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Logout");
		alert.setHeaderText("You're about to logout!");
		alert.setContentText("Do you want to save before exiting?:");
		if (alert.showAndWait().get() == ButtonType.OK) {
			Stage stage = (Stage) ScenePane.getScene().getWindow();
			stage.close();
		}
	}

	@FXML
	public void insert(ActionEvent event) {
		Connection con = null;
		try {
			con = DatabaseConnection.getConnection();
			con.setAutoCommit(false);
			try {
				String query = "INSERT INTO `new_schema`.`question_tbl` (question, userCreated)" + " values (?, ?)";
				PreparedStatement preparedStmt = con.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);
				preparedStmt.setString(1, questionText.getText());
				preparedStmt.setString(2, name);
				preparedStmt.execute();
				con.commit();
				con.setAutoCommit(true);
				ResultSet rs = preparedStmt.getGeneratedKeys();
				int currentId = 0;
				if (rs.next()) {
					currentId = rs.getInt(1);
				}
				addAns(currentId, answer1.getText(), rButton1.isSelected() ? 1 : 0);
				addAns(currentId, answer2.getText(), rButton2.isSelected() ? 1 : 0);
				addAns(currentId, answer3.getText(), rButton3.isSelected() ? 1 : 0);
				addAns(currentId, answer4.getText(), rButton4.isSelected() ? 1 : 0);
			} catch (Exception e) {
				con.rollback();
//				Alert alert=new A
			}
			showAlertWithoutHeaderText();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
		} 
//		finally {
//			try {
//				con.close();
//			} catch (SQLException | NullPointerException e) {
//			}
//		}
	}

	void addAns(int currentID, String answer, int isCorrectedOrNot) throws SQLException {
		TableAnswer ans = new TableAnswer();
		ans.setQuestionID(currentID);
		ans.setAnswser(answer);
		ans.setIsCorrectedOrNot(isCorrectedOrNot);
		TableAnswerImplementation ansDao = new TableAnswerImplementation();
		ansDao.add(ans);
	}

	private void showAlertWithoutHeaderText() {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Test Connection");
		alert.setHeaderText(null);
		alert.setContentText("Insert successfully!");
		alert.showAndWait();
	}

	@FXML
	public void switchScene3(ActionEvent event) throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Scene3.fxml"));
		root = loader.load();
		Scene3Controller scene3Controller = loader.getController();
		scene3Controller.displayName(name);
		stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		stage.setX(200);
		stage.setY(50);
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();

	}

	public void displayUpdateValue(QuestionAndAns qa) {
		questionText.setPromptText(qa.getQuestion());
		answer1.setText(qa.getOption1());
		answer2.setText(qa.getOption2());
		answer3.setText(qa.getOption3());
		answer4.setText(qa.getOption4());
		if (qa.getOption5().equals("Option1")) {
			rButton1.setSelected(true);
		}else {
			if (qa.getOption5().equals("Option2")) {
				rButton2.setSelected(true);
			}else {
				if (qa.getOption5().equals("Option3")) {
					rButton3.setSelected(true);
				}else {
					if (qa.getOption5().equals("Option4")) {
						rButton4.setSelected(true);
					}else {
						
					}
				}
			}
		}
		insertButton.setText("Update");
		EventHandler<ActionEvent> event = new EventHandler<ActionEvent>(){

			@Override
			public void handle(ActionEvent arg0) {
				Connection con = null;
				try {
					con = DatabaseConnection.getConnection();
					con.setAutoCommit(false);
					try {
						String query= "update `new_schema`.`answer_tbl` set answers=?, isCorrectedOrNot=? where id=?;";
						PreparedStatement preparedStmt = con.prepareStatement(query);
						preparedStmt.setString(1, answer1.getText());
						preparedStmt.setInt(2, rButton1.isSelected()?1:0);
						preparedStmt.setInt(3, qa.getAnswerID().get(0));
						preparedStmt.addBatch();
						preparedStmt.setString(1, answer2.getText());
						preparedStmt.setInt(2, rButton2.isSelected()?1:0);
						preparedStmt.setInt(3, qa.getAnswerID().get(1));
						preparedStmt.addBatch();
						preparedStmt.setString(1, answer3.getText());
						preparedStmt.setInt(2, rButton3.isSelected()?1:0);
						preparedStmt.setInt(3, qa.getAnswerID().get(2));
						preparedStmt.addBatch();
						preparedStmt.setString(1, answer4.getText());
						preparedStmt.setInt(2, rButton4.isSelected()?1:0);
						preparedStmt.setInt(3, qa.getAnswerID().get(3));
						preparedStmt.addBatch();
						preparedStmt.setString(1, questionText.getText());
						preparedStmt.setInt(2, qa.getQuestionID());
						preparedStmt.executeBatch();
						con.commit();
						con.setAutoCommit(true);
						Alert alert = new Alert(AlertType.INFORMATION);
						alert.setTitle("Test Connection");
						alert.setHeaderText(null);
						alert.setContentText("Update successfully!");
						alert.showAndWait();
					} catch (Exception e) {
						con.rollback();
						e.printStackTrace();
					}
				} catch (SQLException e) {
					e.printStackTrace();
				} catch (Exception e) {
				} 
				
			}
		};
		insertButton.setOnAction(event);
	}
}
