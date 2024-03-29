package entity;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import java.util.Date;

@Entity
public class StudentAssignment extends RecursiveTreeObject<StudentAssignment> implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    
    
    
    private int id;
    private String name;
    private String studentName;
    private String studentLastName;
    private String description;
    private String response;
    private String type;
    private int courseId;
    private int sectionId;
    private int studentId;
    private int assignmentId;
    private int points;
    private int totalPoints;
    
    
    public StudentAssignment(int id, String name, String studentName, String studentLastName, int sectionId, int studentId, int assignmentId, int courseId,  int points, String type,
            int totalPoints) {
        super();
        this.id = id;
        this.courseId = courseId;
        this.sectionId = sectionId;
        this.studentId = studentId;
        this.assignmentId = assignmentId;
        this.name = name;
        this.studentName = studentName;
        this.studentLastName = studentLastName;
        this.points = points;
        this.totalPoints = totalPoints;
        this.type = type;
        
    }

    
    
    public StudentAssignment() {
        super();
    }
    
    
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }
    
    public String getStudentLastName() {
        return studentLastName;
    }

    public void setStudentLastName(String studentLastName) {
        this.studentLastName = studentLastName;
    }
    
     public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    
     public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }
    
     public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }
    
     public int getSectionId() {
        return sectionId;
    }

    public void setSectionId(int sectionId) {
        this.sectionId = sectionId;
    }
    
    public int getStudentId() {
        return studentId;
    }
    
     public void setTotalPoints(int totalPoints) {
        this.totalPoints = totalPoints;
    }
    
    public int getTotalPoints() {
        return totalPoints;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }
    
    public int getAssignmentId() {
        return assignmentId;
    }

    public void setAssignmentId(int assignmentId) {
        this.assignmentId = assignmentId;
    }
    
    public int getpoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }
    
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
    

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) id;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof StudentAssignment)) {
            return false;
        }
        StudentAssignment other = (StudentAssignment) object;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.StudentAssignments[ id=" + id + " ]";
    }
    
}
