package model;

public class CourseAssignment {
    private String introduction;
    private String assignmentComments;
    private double totalPoints;
    private String assignmentName;
    private double averge;
    private double median;
    private double max;
    private double min;
    public void setAssignmentComments(String assignmentComments) {
        this.assignmentComments = assignmentComments;
    }

    public void setAssigmentName(String assigmentName) {
        this.assignmentName = assigmentName;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public void setTotalPoints(double totalPoints) {
        this.totalPoints = totalPoints;
    }

    public double getTotalPoints() {
        return totalPoints;
    }

    public String getAssigmentName() {
        return assignmentName;
    }

    public String getAssignmentComments() {
        return assignmentComments;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setAverge(double averge) {
        this.averge = averge;
    }

    public void setMedian(double median) {
        this.median = median;
    }

    public double getAverge() {
        return averge;
    }

    public double getMedian() {
        return median;
    }

    public void setMax(double max) {
        this.max = max;
    }

    public void setMin(double min) {
        this.min = min;
    }

    public double getMax() {
        return max;
    }

    public double getMin() {
        return min;
    }
}
