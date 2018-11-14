package grader;

public class StudentAssignment {
	private double totalScore;
	private double lostpoints;
	private double score;
	private String comments;

	public double getTotalscoreScore() {
		return totalScore;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public void setTotalscoreScore(double totalscore) {
		this.totalScore = totalscore;
	}

	public void setLostpoints(double lostpoints) {
		this.lostpoints = lostpoints;
	}

	public void  setScore(){
		this.score = (this.totalScore - this.lostpoints) / this.totalScore;
	}



}
