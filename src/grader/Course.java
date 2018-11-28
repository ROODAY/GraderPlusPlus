package grader;

import java.util.*;

public class Course {
	private Integer year;
	private String semester;
	private String courseName;
	private int[] weights;
	private List<Student> studentList;
	private List<CourseAssignment> courseAssignmentList;

	public void setWeights(int[] weights) {
		this.weights = weights;
	}

	public void setSemester(String semester) {
		this.semester = semester;
	}

	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	public void setYear(Integer year) {
		this.year = year;
	}

	public void setAllGPA() {
		for (int i = 0; i < studentList.size();i++){
			studentList.get(i).setGPA(weights);
		}
	}

	public double[] getAllGPA() {
		double[] GPAlist = new double[studentList.size()];
		for (int i = 0; i < studentList.size();i++){
			GPAlist[i] = studentList.get(i).getGPA();
		}
		return GPAlist;
	}


	// student view
	public void setStudentList(List<Student> stulist) {
		// dummy set
		this.studentList = stulist;
	}

	public void setCourseAssignmentList() {

	}

	public void sortStdentListByName(){

	}

	public void sortStudentListByGrade(){

	}

	public void sortStudentByGroup(){

	}

	public void addStudent(Student stu){
		this.studentList.add(stu);
	}

	// assignment view

	public void sortAssignmentByGrade(){

	}

	public void getSortAssignmentBysubmit(){

	}

	public void addAssignment(){

	}

	public void showresult(){

	}

	// test use
	public void printStudentInfo(){
		for (int i = 0; i < studentList.size(); i++) {
			System.out.println(studentList.get(i).getName());
			System.out.println(studentList.get(i).getEmail());
			System.out.println(studentList.get(i).getGroup());
			System.out.println(studentList.get(i).getsID());
			System.out.println(studentList.get(i).getGPA());
			System.out.println("*******************");
		}
	}


}
