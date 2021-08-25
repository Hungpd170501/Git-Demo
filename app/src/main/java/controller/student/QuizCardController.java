package controller.student;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Duration;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import model.DatabaseConnection;
import model.QuestionAndAns;
import model.Quiz;

public class QuizCardController {
	@FXML
	Label quizTitle, startDate, endDate, timeLimit, numberOfQuestion;
	Quiz quiz;

	void displayQuiz(Quiz q) {
		quiz = q;
		quizTitle.setText(q.getTitle());
		startDate.setText("Open on: " + q.getStartDate().toString());
		endDate.setText("This quiz will end on: " + q.getEndDate().toString());
		timeLimit.setText("Time Limit: " + Duration.between(LocalTime.MIN, q.getTimeLimit().toLocalTime()).toMinutes()
				+ " min(s)");
		numberOfQuestion.setText("Number of question(s): " + q.getQuestionList().size());
	}

	@FXML
	public void startQuiz(ActionEvent event) throws IOException {
		
		Date date = new Date();
		long time = date.getTime();
		Timestamp ts = new Timestamp(time);
		///
		if (ts.after(quiz.getStartDate())) {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/student/StudentQuestion.fxml"));
			Parent root = loader.load();
			StudentQuestionController studentQuestionController = loader.getController();
			studentQuestionController.setTitle(quiz.getTitle());
			Connection con = DatabaseConnection.getConnection();
			try {
				List<QuestionAndAns> questionAndAnsList = new ArrayList<>();

				quiz.getQuestionList().forEach((element) -> {
					try {
						PreparedStatement stm = con.prepareStatement(
								"SELECT q.question, a.answers from question_tbl q inner join answer_tbl a on q.id=a.question_ID and q.id=?;");
						stm.setInt(1, element);
						ResultSet rs = stm.executeQuery();
						List<String> list = new ArrayList<>();
						list.clear();
						String ans = null;
						while (null != rs && rs.next()) {
							list.add(rs.getString(rs.findColumn("answers")));
							ans = rs.getString(rs.findColumn("question"));
						}
						Collections.shuffle(list);
						QuestionAndAns q = new QuestionAndAns();
						q.setQuestion(ans);
						q.setOption(list);
						questionAndAnsList.add(q);
					} catch (SQLException e) {
						e.printStackTrace();
					}
				});
				studentQuestionController.setQuestion(questionAndAnsList.get(0).getQuestion());
				studentQuestionController.setAnswer1(questionAndAnsList.get(0).getOption().get(0));
				studentQuestionController.setAnswer2(questionAndAnsList.get(0).getOption().get(1));
				studentQuestionController.setAnswer3(questionAndAnsList.get(0).getOption().get(2));
				studentQuestionController.setAnswer4(questionAndAnsList.get(0).getOption().get(3));

				studentQuestionController.setTimer(quiz.getTimeLimit());
				studentQuestionController.getData(questionAndAnsList);
				

			} catch (Exception e) {
				e.printStackTrace();
			}
//			studentQuestionController.setTimer(Time.valueOf("00:00:10"));

			Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
			Scene scene = new Scene(root);
			stage.setScene(scene);
			stage.show();
		} else {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle(null);
			alert.setHeaderText(null);
			alert.setContentText("This quiz will open on: " + quiz.getStartDate().toString() + ". Please try again!");
			alert.showAndWait();
		}
	}

}
