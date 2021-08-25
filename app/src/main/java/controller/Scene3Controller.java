package controller;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import com.mysql.cj.conf.ConnectionUrlParser.Pair;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.Pagination;
import javafx.scene.control.SelectionMode;
//import javafx.scene.control.Pagination;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import model.DatabaseConnection;
import model.QuestionAndAns;

public class Scene3Controller implements Initializable {
	private Stage stage;
	private Scene scene;
	private Parent root;

	@FXML
	private Label nameLabel;

	@FXML
	Pane ScenePane;

	@FXML
	private TableView<QuestionAndAns> table;
	@FXML
	private TableColumn<QuestionAndAns, String> questionColumn, answerColumn1, answerColumn2, answerColumn3,
			answerColumn4, answerColumn5;
	private ObservableList<QuestionAndAns> questionAndAnsList;
	private final static int rowsPerPage = 15;

	@FXML
	Pagination pagination;
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

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		try {
			questionAndAnsList = FXCollections.observableArrayList(new QuestionAndAns());
			questionColumn.setCellValueFactory(new PropertyValueFactory<QuestionAndAns, String>("question"));
			answerColumn1.setCellValueFactory(new PropertyValueFactory<QuestionAndAns, String>("option1"));
			answerColumn2.setCellValueFactory(new PropertyValueFactory<QuestionAndAns, String>("option2"));
			answerColumn3.setCellValueFactory(new PropertyValueFactory<QuestionAndAns, String>("option3"));
			answerColumn4.setCellValueFactory(new PropertyValueFactory<QuestionAndAns, String>("option4"));
			answerColumn5.setCellValueFactory(new PropertyValueFactory<QuestionAndAns, String>("option5"));
			Connection con = null;
			con = DatabaseConnection.getConnection();
			try {
				String query = "SELECT question_ID, question, answers, isCorrectedOrNot, a.id from question_tbl q join answer_tbl a WHERE q.id=a.question_ID order by a.question_ID;";
				Statement stmt = con.createStatement();
				ResultSet rs = stmt.executeQuery(query);
				int id = 0;
				List<Pair<String, Integer>> option = new ArrayList<Pair<String, Integer>>();
				QuestionAndAns qa = new QuestionAndAns();
				while (rs.next()) {
					if (Integer.parseInt(rs.getString(1)) == id) {
						qa.setQuestion(rs.getString(2));
						qa.setQuestionID(id);
						option.add(new Pair<String, Integer>(rs.getString(3), Integer.parseInt(rs.getString(4))));
						qa.getAnswerID().add(Integer.valueOf(rs.getString(5)));
					} else {
						id = Integer.parseInt(rs.getString(1));
						qa.setQuestionID(id);
						if (id != 1) {
							qa.setOption1(option.get(0).left);
							qa.setOption2(option.get(1).left);
							qa.setOption3(option.get(2).left);
							qa.setOption4(option.get(3).left);
							if (option.get(0).right == 1) {
								qa.setOption5("Option1");
							} else {
								if (option.get(1).right == 1) {
									qa.setOption5("Option2");
								} else {
									if (option.get(2).right == 1) {
										qa.setOption5("Option3");
									} else {
										if (option.get(3).right == 1) {
											qa.setOption5("Option4");
										}
									}
								}
							}
							questionAndAnsList.add(qa);
							qa = new QuestionAndAns();
							option.clear();
						}
						qa.setQuestion(rs.getString(2));
						option.add(new Pair<String, Integer>(rs.getString(3), Integer.parseInt(rs.getString(4))));
						qa.getAnswerID().add(Integer.parseInt(rs.getString(5)));
						qa.setQuestionID(id);
					}
				}
				qa.setOption1(option.get(0).left);
				qa.setOption2(option.get(1).left);
				qa.setOption3(option.get(2).left);
				qa.setOption4(option.get(3).left);
				if (option.get(0).right == 1) {
					qa.setOption5("Option1");
				} else {
					if (option.get(1).right == 1) {
						qa.setOption5("Option2");
					} else {
						if (option.get(2).right == 1) {
							qa.setOption5("Option3");
						} else {
							if (option.get(3).right == 1) {
								qa.setOption5("Option4");
							}
						}
					}
				}
				questionAndAnsList.add(qa);
			} catch (Exception e) {
				e.printStackTrace();
			}
//			   Pagination pagination = new Pagination();
			table.getItems().clear();
//			table.setItems(questionAndAnsList);
		} catch (NullPointerException e) {
			e.printStackTrace();
		}
		pagination.setMaxPageIndicatorCount(
				(questionAndAnsList.size() != 0) ? questionAndAnsList.size() / rowsPerPage +1: 1);
		pagination.setPageCount((questionAndAnsList.size() != 0) ? questionAndAnsList.size() / rowsPerPage +1: 1);
		pagination.setPageFactory(this::createPage);
		table.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

	}

	private Node createPage(int pageIndex) {
		int fromIdex = pageIndex * rowsPerPage + 1;
		int toIndex = Math.min(fromIdex + rowsPerPage, questionAndAnsList.size());
		table.setItems(FXCollections.observableArrayList(questionAndAnsList.subList(fromIdex, toIndex)));
		return table;

	}

	@FXML
	public void delete(ActionEvent event) {
		QuestionAndAns selected = table.getSelectionModel().getSelectedItem();
		questionAndAnsList.remove(selected);
		table.getItems().remove(selected);
		Connection con = null;
		try {
			con = DatabaseConnection.getConnection();
			con.setAutoCommit(false);
			try {
				String query = "delete from answer_tbl where id=?";
				PreparedStatement preparedStmt = con.prepareStatement(query);
				preparedStmt.setInt(1, selected.getAnswerID().get(0));
				preparedStmt.addBatch();
				preparedStmt.setInt(1, selected.getAnswerID().get(1));
				preparedStmt.addBatch();
				preparedStmt.setInt(1, selected.getAnswerID().get(2));
				preparedStmt.addBatch();
				preparedStmt.setInt(1, selected.getAnswerID().get(3));
				preparedStmt.addBatch();
				preparedStmt.setInt(1, selected.getQuestionID());
				preparedStmt.addBatch();
				preparedStmt.executeBatch();
				con.commit();
				con.setAutoCommit(true);
			} catch (Exception e) {
				con.rollback();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
		}

	}

	@FXML
	public void update(ActionEvent event) throws IOException {
		QuestionAndAns selected = table.getSelectionModel().getSelectedItem();
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Scene2.fxml"));
		root = loader.load();
		Scene2Controller scene2Controller = loader.getController();
		scene2Controller.displayName(name);
		scene2Controller.displayUpdateValue(selected);
		stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}

	@FXML
	public void switchToScene2(ActionEvent event) throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Scene2.fxml"));
		root = loader.load();
		Scene2Controller scene2Controller = loader.getController();
		scene2Controller.displayName(name);
		stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();

	}

	@FXML
	public void createQuizz(ActionEvent event) throws Exception {
		ObservableList<QuestionAndAns> qList =FXCollections.observableArrayList();
		for (int totalValue = 0; totalValue < table.getSelectionModel().getSelectedItems().size(); totalValue++) {
			TablePosition<?, ?> pos = table.getSelectionModel().getSelectedCells().get(totalValue);
			int row = pos.getRow();
			qList.add(table.getItems().get(row));
		}
		if(qList.isEmpty()) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle(null);
			alert.setHeaderText(null);
			alert.setContentText("Can't create this quiz. Please choose question(s) and try again!");
			alert.showAndWait();
		}else {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Scene4.fxml"));
		root = loader.load();
		Scene4Controller scene4Controller = loader.getController();
		scene4Controller.displayName(name);
		scene4Controller.displayList(qList);
		stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
		}
	}

}
