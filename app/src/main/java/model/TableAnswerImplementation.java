package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class TableAnswerImplementation {
	Connection con = DatabaseConnection.getConnection();

	public void add(TableAnswer ans) throws SQLException {
		String query = "INSERT INTO `new_schema`.`answer_tbl` (question_ID, answers, isCorrectedOrNot)"+" values (?, ?, ?)";
		con.setAutoCommit(false);
		try{
		PreparedStatement preparedStmt = con.prepareStatement(query);
		preparedStmt.setInt(1, ans.getQuestionID());
		preparedStmt.setString (2, ans.getAnswser());
		preparedStmt.setInt(3, ans.getIsCorrectedOrNot());
		preparedStmt.executeUpdate();
		con.commit();
		con.setAutoCommit(true);
		}catch (Exception e) {
			con.rollback();
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle(null);
			alert.setHeaderText(null);
			alert.setContentText("Sorry, something went wrong! Please try again!");
			alert.showAndWait();
		}
	}

}
