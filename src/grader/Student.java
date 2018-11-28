package grader;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.*;
public class Student {
	private String name;
	private String sID;
	private String email;
	private String group;
	private double GPA;
	private List<StudentAssignment> assignments;

	public String getEmail() {
		return email;
	}

	public String getGroup() {
		return group;
	}

	public String getName() {
		return name;
	}

	public String getsID() {
		return sID;
	}

	public void setGroup(String group) {
		this.group = group;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setsID(String sID) {
		this.sID = sID;
	}

	public void setAssignments(List<StudentAssignment> salist) {
		// dummy set
		this.assignments = salist;

	}

	public void setGPA(int[] weights) {
		double[] calculateScore = new double[weights.length];
		for (int i = 0; i < weights.length;i++){
			calculateScore[i] = weights[i] * assignments.get(i).getScore() / 100;
		}
		double sum = 0;
		for (double i : calculateScore) {
			sum += i;
		}
		double gpa = sum * 4 / 100;
		BigDecimal dg = new BigDecimal(gpa);
		this.GPA = dg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();

	}

	public double getGPA() {
		return GPA;
	}

	public List<StudentAssignment> getAssignments() {
		return this.assignments;
	}
}
