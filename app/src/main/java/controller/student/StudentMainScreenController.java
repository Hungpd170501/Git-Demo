package controller.student;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import model.DatabaseConnection;
import model.Quiz;

public class StudentMainScreenController implements Initializable {

	@FXML
	FlowPane flowPane;
	@FXML
	Label studentName;
	@FXML
	Pane ScenePane;

	public void displayName(String name) {
		studentName.setText(name);
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

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		Connection con = DatabaseConnection.getConnection();
		try {
			Statement stm = con.createStatement();
			ResultSet rs = stm.executeQuery("SELECT * from new_schema.quiz_tbl where quiz_endDate>=now() ;");
			ResultSetMetaData rsMetaData = rs.getMetaData();
			while (null != rs && rs.next()) {
				Quiz q = new Quiz();
				for (int i = 1; i < rsMetaData.getColumnCount(); i++) {
					q.setId(rs.getInt(rs.findColumn("quiz_id")));
					q.setTitle(rs.getString(rs.findColumn("quiz_title")));
					q.setStartDate(rs.getTimestamp(rs.findColumn("quiz_startDate")));
					q.setEndDate(rs.getTimestamp(rs.findColumn("quiz_endDate")));
					q.setTimeLimit(rs.getTime(rs.findColumn("quiz_timeLimit")));
					PreparedStatement preStm = con.prepareStatement(
							"SELECT questionByQuiz_questionID from questionbyquiz_tbl where questionByQuiz_quizID=?");
					preStm.setInt(1, q.getId());
					ResultSet rs1 = preStm.executeQuery();
					List<Integer> list = new ArrayList<>();
					list.clear();
					while (null != rs1 && rs1.next()) {
						list.add(rs1.getInt(rs1.findColumn("questionByQuiz_questionID")));
					}
					Collections.shuffle(list);
					q.setQuestionList(list);
				}
				addQuizListScreen(q);
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void addQuizListScreen(Quiz q) throws IOException {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/student/QuizCard.fxml"));
		try {
			Node node = fxmlLoader.load();
			QuizCardController quizCardController = fxmlLoader.getController();
			quizCardController.displayQuiz(q);
			flowPane.getChildren().add(node);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
