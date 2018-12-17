
package entity;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.property.StringProperty;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;



@Entity
public class Student extends RecursiveTreeObject<Student> implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    
    private int id;
    private int bu_id;
    private int sectionId;
    private int courseId;
    private double ec;
    private String first_name;
    private String last_name;
    private String email;
    private String program;
    
    private String comments;

    private double grade;
    
    
    
    public Student(int bu_id, int sectionId, int courseId, String name, String last_name, String email, String program, String comments, double ec) {
        super();
        this.bu_id = bu_id;
        this.courseId = courseId;
        this.first_name = name;
        this.last_name = last_name;
        this.email = email;
        this.program = program;
        this.sectionId = sectionId;
        this.comments = comments;
        this.ec = ec;
    }

    public Student( ) {
        super();
    }

    public Button getInfoButton() {
        Button button = new JFXButton("More Info");
        button.getStyleClass().add("flatBtn");
        button.setOnAction(event -> {
            JFXDialog dialog = new JFXDialog();
            VBox dialogVbox = new VBox(20);
            dialogVbox.getChildren().add(new Text("   Student Name:  " + first_name + "        "));
            dialogVbox.getChildren().add(new Text("   Student Email: " + email + "        "));
            dialogVbox.getChildren().add(new Text("   Student ID: " + bu_id + "        "));
            dialogVbox.getChildren().add(new Text("   Student Group: " + program + "        "));
            dialog.setContent(dialogVbox);

            StackPane root = (StackPane) button.getScene().lookup("#dialogPane");

            dialog.show(root);
        });

        return button;
    }
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    public int getBUId() {
        return bu_id;
    }
    
    public void setEc(double ec) {
        this.ec = ec;
    }
    
    public double getEc() {
        return ec;
    }

    public void setBUId(int bu_id) {
        this.bu_id = bu_id;
    }
    
    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String name) {
        this.first_name = name;
    }
    
    public String getLastName() {
        return last_name;
    }

    public void setLastName(String last_name) {
        this.last_name = last_name;
    }
    
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    
    public int getSectionId() {
        return sectionId;
    }

    public void setSectionId(int sectionId) {
        this.sectionId = sectionId;
    }
    
    public String getProgram() {
        return program;
    }

    public void setProgram(String program) {
        this.program = program;
    }
    
    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
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
        if (!(object instanceof Student)) {
            return false;
        }
        Student other = (Student) object;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Student[ id=" + id + " ]";
    }

    public double getGrade() {
        return grade;
    }

    public void setGrade(double grade) {
        this.grade = grade;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }
}
