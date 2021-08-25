package model;

import java.util.ArrayList;
import java.util.List;

public class QuestionAndAns {
	private String question, option1, option2, option3, option4, option5;
	int questionID;
	List<String> option;
	
	ArrayList<Integer> answerID;

	public List<String> getOption() {
		return option;
	}

	public void setOption(List<String> option) {
		this.option = option;
	}

	public ArrayList<Integer> getAnswerID() {
		return answerID;
	}

	public void setAnswerID(ArrayList<Integer> answerID) {
		this.answerID = answerID;
	}

	public int getQuestionID() {
		return questionID;
	}

	public void setQuestionID(int questionID) {
		this.questionID = questionID;
	}

	public QuestionAndAns(String question, String option1, String option2, String option3, String option4,
			String option5, int questionID, List<String> option) {
		super();
		this.question = question;
		this.option1 = option1;
		this.option2 = option2;
		this.option3 = option3;
		this.option4 = option4;
		this.option5 = option5;
		this.questionID = questionID;
		this.option = option;
	}

	public QuestionAndAns(String question, String option1, String option2, String option3, String option4,
			String option5) {
		super();
		this.question = question;
		this.option1 = option1;
		this.option2 = option2;
		this.option3 = option3;
		this.option4 = option4;
		this.option5 = option5;
	}

	public QuestionAndAns() {
		this.answerID=new ArrayList<Integer>();
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public String getOption1() {
		return option1;
	}

	public void setOption1(String option1) {
		this.option1 = option1;
	}

	public String getOption2() {
		return option2;
	}

	public void setOption2(String option2) {
		this.option2 = option2;
	}

	public String getOption3() {
		return option3;
	}

	public void setOption3(String option3) {
		this.option3 = option3;
	}

	public String getOption4() {
		return option4;
	}

	public void setOption4(String option4) {
		this.option4 = option4;
	}

	public String getOption5() {
		return option5;
	}

	public void setOption5(String option5) {
		this.option5 = option5;
	}

	@Override
	public String toString() {
		return "[" + question + "] ";
	}

	
}
