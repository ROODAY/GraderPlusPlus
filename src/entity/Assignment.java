/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 *
 * @author miguelvaldez
 */
@Entity
public class Assignment extends RecursiveTreeObject<Assignment> implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    
    
    private int id;
    private int courseId;
    private String name;
    private int totalPoints;
    private String dateAssigned;
    private String type;
    //private due date

    
    
    public Assignment(int id, int courseId, String name, int totalPoints, String type, String dateAssigned) {
        super();
        this.id = id;
        this.courseId = courseId;
        this.name = name;
        this.totalPoints = totalPoints;
        this.type = type;
        this.dateAssigned = dateAssigned;
    }

    
    
    public Assignment() {
        super();
    }

    public Button getInfoButton() {
        Button button = new JFXButton("More Info");
        button.getStyleClass().add("flatBtn");
        button.setOnAction(event -> {
            JFXDialog dialog = new JFXDialog();
            VBox dialogVbox = new VBox(20);
            dialogVbox.getChildren().add(new Text("   Assignment Min:  " + 0 + "        "));
            dialogVbox.getChildren().add(new Text("   Assignment Max: " + 0 + "        "));
            dialogVbox.getChildren().add(new Text("   Assignment median: " + 0 + "        "));
            dialogVbox.getChildren().add(new Text("   Assignemnt Total Points: " + totalPoints + "        "));
            dialogVbox.getChildren().add(new Text("   Assignment Comments : "  + "        "));
            dialog.setContent(dialogVbox);

            StackPane root = (StackPane) button.getScene().lookup("#dialogPane");

            dialog.show(root);
        });

        return button;
    }
    
    public int getId() {
        return id;
    }

    public double getClassAverage() {
        return 0.0;
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
    
    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }
    
    public String getDateAssigned() {
        return dateAssigned;
    }

    public void setDateAssigned(String dateAssigned) {
        this.dateAssigned = dateAssigned;
    }
    
    public int getTotalPoints() {
        return totalPoints;
    }

    public void setTotalPoints(int totalPoints) {
        this.totalPoints = totalPoints;
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
        if (!(object instanceof Assignment)) {
            return false;
        }
        Assignment other = (Assignment) object;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Course[ id=" + id + " ]";
    }
    
}
