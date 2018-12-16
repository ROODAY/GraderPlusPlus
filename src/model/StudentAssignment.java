package model;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

import java.math.BigDecimal;
public class StudentAssignment {
	private double totalScore;
	private double lostPoints;
	private double score;
	private String comments;
	private DoubleProperty showscore;

	public void setComments(String comments) {


		this.comments = comments;
	}

	public void setTotalScore(double totalscore) {


		this.totalScore = totalscore;
	}

	public void setLostpoints(double lostpoints) {
		this.lostPoints = lostpoints;
	}

	public void  setScore(){
		double score = (this.totalScore - this.lostPoints)*100 / this.totalScore;
		BigDecimal dg = new BigDecimal(score);
		this.score = dg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
		showscore = new SimpleDoubleProperty(score);
	}

	public double getScore() {
		return this.score;
	}

	public DoubleProperty getShowscore() {
		return showscore;
	}

	public String getStringScore(){
		return Double.toString(this.score);
	}

	public double getTotalscoreScore() {
		return totalScore;
	}

	public String getComments() {
		return comments;
	}
}
