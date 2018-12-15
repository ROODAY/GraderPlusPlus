package model;

import java.util.*;
import java.math.BigDecimal;

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

	public void setAssigenmentInfo(){
		for (int i = 0; i < courseAssignmentList.size();i++){
		    double[] scoreList = new double[studentList.size()];
		    for(int j = 0; j < studentList.size();j++){
		        Student stu = studentList.get(j);
                List<StudentAssignment> stulist = stu.getAssignments();
                scoreList[j] = stulist.get(i).getScore();
            }
            double average;
            double sum = 0;
            double min = Integer.MAX_VALUE;
            double max = Integer.MIN_VALUE;
            for (double score:scoreList) {
                sum += score;
                if (score < min) {
                    min = score;
                }
                if (score > max) {
                    max = score;
                }
            }
            courseAssignmentList.get(i).setMax(max);
            courseAssignmentList.get(i).setMin(min);
            average = sum/studentList.size();
            BigDecimal dg = new BigDecimal(average);
            double formatAverage = dg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
            courseAssignmentList.get(i).setAverge(formatAverage);
            Arrays.sort(scoreList);
            double median = scoreList[scoreList.length/2];
            BigDecimal dm = new BigDecimal(median);
            double formatMedian = dm.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
            courseAssignmentList.get(i).setMedian(formatMedian);
        }
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

	public void setCourseAssignmentList(List<CourseAssignment> coulist) {
		this.courseAssignmentList = coulist;
	}

	public List<CourseAssignment> getCourseAssignmentList() {
		return courseAssignmentList;
	}


	public void addStudent(Student stu){
		this.studentList.add(stu);
	}

	public List<Student> getStudentList() {
		return studentList;
	}
	// assignment view

	// test use
	public void printmoreStudentInfo(){
		for (int i = 0; i < studentList.size(); i++) {
			System.out.print("Student Name: " + studentList.get(i).getName()+ "        ");
			System.out.print("Student Email: " + studentList.get(i).getEmail()+ "        ");
			System.out.print("Student Group: " + studentList.get(i).getGroup()+ "        ");
			System.out.print("Student ID: " + studentList.get(i).getsID()+ "        ");
			System.out.print("Student GPA " + studentList.get(i).getGPA()+ "        ");
			System.out.println();
		}
	}

	public void printAssignmentInfo(){
		for (int i = 0; i < courseAssignmentList.size(); i++) {
			System.out.print("Assignment Name: " + courseAssignmentList.get(i).getAssigmentName()+ "        ");
			System.out.print("Assignment Comment: "+courseAssignmentList.get(i).getAssignmentComments()+ "        ");
			System.out.print("Total Points: "+ courseAssignmentList.get(i).getTotalPoints()+ "        ");
            System.out.print("Average: "+courseAssignmentList.get(i).getAverge()+ "        ");
            System.out.print("Median: "+courseAssignmentList.get(i).getMedian()+ "        ");
            System.out.print("Max: "+courseAssignmentList.get(i).getMax()+ "        ");
            System.out.print("Min: "+courseAssignmentList.get(i).getMin()+ "        ");
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
