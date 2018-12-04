package model;

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
	// together fucntion

	// student view
	public void setStudentList(List<Student> stulist) {
		// dummy set
		this.studentList = stulist;
	}

	public void setCourseAssignmentList(List<CourseAssignment> coulist) {
		this.courseAssignmentList = coulist;
	}

	public void sortStdentListByName() {

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
	public void printmoreStudentInfo(){
		for (int i = 0; i < studentList.size(); i++) {
			System.out.print(studentList.get(i).getName()+ "        ");
			System.out.print(studentList.get(i).getEmail()+ "        ");
			System.out.print(studentList.get(i).getGroup()+ "        ");
			System.out.print(studentList.get(i).getsID()+ "        ");
			System.out.print(studentList.get(i).getGPA()+ "        ");
			System.out.println();
		}
	}

	public void printAssignmentInfo(){
		for (int i = 0; i < courseAssignmentList.size(); i++) {
			System.out.print(courseAssignmentList.get(i).getAssigmentName()+ "        ");
			System.out.print(courseAssignmentList.get(i).getAssignmentComments()+ "        ");
			System.out.print(courseAssignmentList.get(i).getTotalPoints()+ "        ");
			System.out.println();
		}

	}

	public void printStudentScore(){
        System.out.print("Name      ");
	    for (CourseAssignment assignment: courseAssignmentList){
            System.out.print(assignment.getAssigmentName() + "      ");
        }
        System.out.print("GPA       ");
        System.out.println();
        for (Student stu:studentList) {
            System.out.print(stu.getName()+"        ");
            List<StudentAssignment> asslist = stu.getAssignments();
            for (StudentAssignment ass: asslist) {
                System.out.print(ass.getScore() + "     ");
            }
            System.out.println(stu.getGPA());
        }
    }


}
