package controller.student;

import java.io.IOException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;
//import org.controlsfx.control.Notifications;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
//import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.stage.Stage;
import model.QuestionAndAns;



public class StudentQuestionController {
	@FXML
	Label title, timing, question;
	@FXML
	RadioButton answer1, answer2, answer3, answer4;

	public void setTitle(String value) {
		title.setText(value);
	}

	public void setTiming(String value) {
		timing.setText(value);
	}

	public void setQuestion(String value) {
		question.setText(value);
	}

	public void setAnswer1(String value) {
		answer1.setText(value);
		;
	}

	public void setAnswer2(String value) {
		answer2.setText(value);
	}

	public void setAnswer3(String value) {
		answer3.setText(value);
	}

	public void setAnswer4(String value) {
		answer4.setText(value);
	}

	private long min, sec, hr, totalSec = 0; 
	private Timer timer;

	private String format(long value) {
		if (value < 10) {
			return 0 + "" + value;
		}

		return value + "";
	}

	public void convertTime() {
		min = TimeUnit.SECONDS.toMinutes(totalSec);
		sec = totalSec - (min * 60);
		hr = TimeUnit.MINUTES.toHours(min);
		min = min - (hr * 60);
		timing.setText(format(hr) + ":" + format(min) + ":" + format(sec));
		totalSec--;
	}

	public void setTimer(Time t) {
		totalSec = (t.toLocalTime().toSecondOfDay());
		this.timer = new Timer();
		TimerTask timerTask = new TimerTask() {
			@Override
			public void run() {
				Platform.runLater(new Runnable() {
					@Override
					public void run() {
						convertTime();
						if (totalSec <= 0) {
							timer.cancel();
							timing.setText("00:00:00");
//							saveing data to database
							
						}
					}
				});
			}
		};
		timer.schedule(timerTask, 0, 1000);
	}
	List<QuestionAndAns> questionAndAnsList = new ArrayList<>();

	int currentIndex=1;
	@FXML public void nextQuestion() {
		timer.cancel();
		if (currentIndex>questionAndAnsList.size()-1) {
			this.currentIndex=0;
		}
		this.setQuestion(this.questionAndAnsList.get(currentIndex).getQuestion());
		this.setAnswer1(this.questionAndAnsList.get(currentIndex).getOption().get(0));
		this.setAnswer2(this.questionAndAnsList.get(currentIndex).getOption().get(1));
		this.setAnswer3(this.questionAndAnsList.get(currentIndex).getOption().get(2));
		this.setAnswer4(this.questionAndAnsList.get(currentIndex).getOption().get(3));
		currentIndex++;
	}
	@FXML
	public void submit(ActionEvent event) throws IOException {
		timer.cancel();
//		saving data to database
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/student/FinalScore.fxml"));
		Parent root = loader.load();
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.show();

		
	}
	public void getData(List<QuestionAndAns> value) {
		this.questionAndAnsList=value;
	}
}
