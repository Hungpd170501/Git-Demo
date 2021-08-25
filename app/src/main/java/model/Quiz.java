package model;

import lombok.Generated;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.List;

public class Quiz {
	private int id;
	private String title, author;
	private Time timeLimit;
	private Timestamp startDate, endDate;
	private List<Integer> questionList;

	public Quiz() {
	}

	public Quiz(int id, String title, String author, Timestamp startDate, Timestamp endDate, Time timeLimit,
			List<Integer> questionList) {
		super();
		this.id = id;
		this.title = title;
		this.author = author;
		this.startDate = startDate;
		this.endDate = endDate;
		this.timeLimit = timeLimit;
		this.questionList = questionList;
	}

	public Time getTimeLimit() {
		return timeLimit;
	}

	public Timestamp getStartDate() {
		return startDate;
	}

	public Timestamp getEndDate() {
		return endDate;
	}

	public void setTimeLimit(Time timeLimit) {
		this.timeLimit = timeLimit;
	}

	public void setStartDate(Timestamp startDate) {
		this.startDate = startDate;
	}

	public void setEndDate(Timestamp endDate) {
		this.endDate = endDate;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public List<Integer> getQuestionList() {
		return questionList;
	}

	public void setQuestionList(List<Integer> questionList) {
		this.questionList = questionList;
	}

	@Override
	public String toString() {
		return "Quiz [title=" + title + ", startDate=" + startDate + ", endDate=" + endDate + ", timeLimit=" + timeLimit
				+ "]";
	}

}
