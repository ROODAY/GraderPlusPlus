package grader;

import java.util.*;

public class Student {
	private String name;
	private String sID;
	private String email;
	private String group;
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

	public void setAssignments() {

	}

	public void getSetAssignments() {

	}
}
