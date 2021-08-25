package controller;

import java.net.URL;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.QuestionAndAns;
import model.Quiz;
import model.QuizImplement;
import model.TimeSpinner;

public class Scene4Controller implements Initializable {
//	private Stage stage;
//	private Scene scene;
//	private Parent root;
	private String username;

	@FXML
	Label nameLabel;
	@FXML
	ListView<QuestionAndAns> listView;
	private ObservableList<QuestionAndAns> questionAndAnsList;
	@FXML
	AnchorPane anchorPane;
	@FXML
	TextField titleTextField;
	@FXML
	DatePicker startDate, endDate;
//	@FXML
//	Spinner<LocalTime> startTime, endTime;
	@FXML
	Spinner<Integer> timeLimitSpinner;

	@FXML
	public void logOut(ActionEvent event) {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Logout");
		alert.setHeaderText("You're about to logout!");
		alert.setContentText("Do you want to save before exiting?:");
		if (alert.showAndWait().get() == ButtonType.OK) {
			Stage stage = (Stage) anchorPane.getScene().getWindow();
			stage.close();
		}

	}

	void displayName(String name) {
		nameLabel.setText("Hello: " + name.toUpperCase());
		username = name;
	}

	TimeSpinner startTime = new TimeSpinner();
	TimeSpinner endTime = new TimeSpinner();

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		startTime.setLayoutX(437.0);
		startTime.setLayoutY(207.0);
		endTime.setLayoutX(438.0);
		endTime.setLayoutY(321.0);
		anchorPane.getChildren().add(startTime);
		anchorPane.getChildren().add(endTime);
		startDate.setValue(LocalDate.now());
		endDate.setValue(LocalDate.now());
		startDate.setDayCellFactory(picker -> new DateCell() {
			public void updateItem(LocalDate date, boolean empty) {
				super.updateItem(date, empty);
				if (empty || date.compareTo(LocalDate.now()) < 0) {
					setDisable(true);
					setStyle("-fx-background-color: #ffc0cb;");
				}
			}
		});
		endDate.setDayCellFactory(picker -> new DateCell() {
			public void updateItem(LocalDate date, boolean empty) {
				super.updateItem(date, empty);
				if (empty || date.compareTo(startDate.getValue()) < 0) {
					setDisable(true);
					setStyle("-fx-background-color: #ffc0cb;");
				}
			}
		});
		timeLimitSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 76000));
	}

	
	public void displayList(ObservableList<QuestionAndAns> qList) {
		questionAndAnsList = FXCollections.observableArrayList(new QuestionAndAns());
		questionAndAnsList.clear();
		questionAndAnsList.addAll(qList);
		listView.getItems().clear();
		listView.setItems(qList);
	}

	
	@FXML
	public void submit(ActionEvent event){
		if (titleTextField.getText().isBlank()) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle(null);
			alert.setHeaderText(null);
			alert.setContentText("Please fill out title field and try again!");
			alert.showAndWait();
		} else {
			Quiz q = new Quiz();
			q.setTitle(titleTextField.getText());
			q.setAuthor(username);
			DateTimeFormatter formatterDate = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			DateTimeFormatter formatterTime = DateTimeFormatter.ofPattern("hh:mm:ss");
			Timestamp sDate = Timestamp.valueOf(
					formatterDate.format(startDate.getValue()) + " " + formatterTime.format(startTime.getValue()));
			q.setStartDate(sDate);
			q.setEndDate(Timestamp.valueOf(
					formatterDate.format(endDate.getValue()) + " " + formatterTime.format(endTime.getValue())));
			q.setTimeLimit(Time
					.valueOf(LocalTime.MIN.plus(Duration.ofMinutes(timeLimitSpinner.getValue())).toString() + ":00"));
			List<Integer> list = new ArrayList<>(); 			
			list.clear();
			q.setQuestionList(list);
			questionAndAnsList.forEach((element)->{
				q.getQuestionList().add(element.getQuestionID());
			});
			QuizImplement quizImpliment = new QuizImplement();
			quizImpliment.add(q);
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle(null);
			alert.setHeaderText(null);
			alert.setContentText("Submit successfully!");
			alert.showAndWait();
		}
	}
}
