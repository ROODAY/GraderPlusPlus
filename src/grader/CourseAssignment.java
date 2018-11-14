package grader;

public class CourseAssignment {
    private String introduction;
    private String assignmentComments;
    private double totalPoints;
    private String assignmentName;

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

}
