package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class QuizImplement {
	Connection con = DatabaseConnection.getConnection();

	public void add(Quiz q) {
		String query = "insert into `new_schema`.`quiz_tbl` (quiz_title, quiz_author, quiz_startDate, quiz_endDate, quiz_timeLimit)"
				+ " values (?, ?, ?, ?, ?)";
		try {
			con.setAutoCommit(false);
			PreparedStatement preparedStmt = con.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);
			preparedStmt.setString(1, q.getTitle());
			preparedStmt.setString(2, q.getAuthor());
			preparedStmt.setObject(3, q.getStartDate());
			preparedStmt.setObject(4, q.getEndDate());
			preparedStmt.setObject(5, q.getTimeLimit());		
			preparedStmt.executeUpdate();
			con.commit();
			con.setAutoCommit(true);
			ResultSet rs = preparedStmt.getGeneratedKeys();
			int currentId = 0;
			if (rs.next()) {
				currentId = rs.getInt(1);
			}
			final int id= currentId;
			con.setAutoCommit(false);
			q.getQuestionList().forEach((element)->{
				final String query1 ="insert into `new_schema`.`questionbyquiz_tbl` (questionByQuiz_quizID, questionByQuiz_questionID)"
						+ " values (?, ?)";
				try {
					final PreparedStatement preparedStmt1 = con.prepareStatement(query1);
					preparedStmt1.setInt(1, id);
					preparedStmt1.setInt(2, element);
					preparedStmt1.executeUpdate();
				} catch (SQLException e) {
					e.printStackTrace();
				}				
			});
			con.commit();
			con.setAutoCommit(true);
		} catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle(null);
			alert.setHeaderText(null);
			alert.setContentText("Sorry, something went wrong! Please try again!");
			alert.showAndWait();
		} catch(Exception e2) {
			e2.printStackTrace();
		}
	}
}
