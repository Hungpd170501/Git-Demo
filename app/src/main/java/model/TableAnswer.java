package model;

public class TableAnswer {
	private int questionID;
	String answser;
	private int isCorrectedOrNot;

	public TableAnswer() {
	}
	
	public TableAnswer(int questionID, String answser, int isCorrectedOrNot) {
		super();
		this.questionID = questionID;
		this.answser = answser;
		this.isCorrectedOrNot = isCorrectedOrNot;
	}
	public int getQuestionID() {
		return questionID;
	}

	public void setQuestionID(int questionID) {
		this.questionID = questionID;
	}

	public String getAnswser() {
		return answser;
	}

	public void setAnswser(String answser) {
		this.answser = answser;
	}

	public int getIsCorrectedOrNot() {
		return isCorrectedOrNot;
	}

	public void setIsCorrectedOrNot(int isCorrectedOrNot) {
		this.isCorrectedOrNot = isCorrectedOrNot;
	}

	@Override
	public String toString() {
		return "TableAnswer [questionID=" + questionID + ", answser=" + answser + ", isCorrectedOrNot="
				+ isCorrectedOrNot + "]";
	}

}
