package model;

public class CourseInfo {
    private Integer year;
    private String semester;
    private String courseName;

    public CourseInfo(Integer year,String semester,String courseName){
        this.year = year;
        this.semester = semester;
        this.courseName = courseName;
    }

    public Integer getYear() {
        return year;
    }

    public String getCourseName() {
        return courseName;
    }

    public String getSemester() {
        return semester;
    }
}
