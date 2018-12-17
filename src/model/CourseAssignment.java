package model;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class CourseAssignment extends RecursiveTreeObject<CourseAssignment> {
    private String introduction;
    private String assignmentComments;
    private double averge;
    private double median;
    private double max;
    private double min;
    private String submitted;
    private JFXButton button;


    private DoubleProperty totalPoints;
    private StringProperty assignmentName;
    private StringProperty dateAssigned;
    private DoubleProperty classAverage;
    private StringProperty assignmentType;

    public CourseAssignment(String name, String type, double points, String date) {
        this.assignmentName = new SimpleStringProperty(name);
        this.assignmentType = new SimpleStringProperty(type);
        this.totalPoints = new SimpleDoubleProperty(points);
        this.dateAssigned = new SimpleStringProperty(date);
        initInfoButton();
    }

    public void setAssignmentComments(String assignmentComments) {
        this.assignmentComments = assignmentComments;
    }

    public StringProperty getAssignmentName() {
        return assignmentName;
    }

    public DoubleProperty getClassAverage() {
        return classAverage;
    }

    public StringProperty getAssignmentType() {
        return assignmentType;
    }

    public StringProperty getDateAssigned() {
        return dateAssigned;
    }

    public void initInfoButton() {
        this.button = new JFXButton("More Info");
        this.button.getStyleClass().add("flatBtn");
        this.button.setOnAction(
                event -> {
                    JFXDialog dialog = new JFXDialog();
                    VBox dialogVbox = new VBox(20);
                    dialogVbox.getChildren().add(new Text("   Assignment Min:  " + min + "        "));
                    dialogVbox.getChildren().add(new Text("   Assignment Max: " + max + "        "));
                    dialogVbox.getChildren().add(new Text("   Assignment median: " + median + "        "));
                    dialogVbox.getChildren().add(new Text("   Assignment Total Points: " + totalPoints.getValue() + "        "));
                    dialogVbox.getChildren().add(new Text("   Assignment Comments : " + assignmentComments + "        "));
                    dialog.setContent(dialogVbox);

                    StackPane root = (StackPane) button.getScene().lookup("#dialogPane");

                    dialog.show(root);
                });
    }

    /*public void setDateAssigned(String dateAssigned) {
        this.dateAssigned = dateAssigned;
    }

    public void setTotalPoints(double totalPoints) {
        this.totalPoints = totalPoints;
    }*/

    public void setSubmitted(String submitted) {
        this.submitted = submitted;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }


    public Button getButton() {
        return button;
    }

    /*public double getTotalPoints() {
        return totalPoints;
    }

    public String getDateAssigned() {
        return dateAssigned;
    }

    public String getAssigmentName() {
        return assignmentName;
    }*/

    public String getSubmitted() {
        return submitted;
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

    public String getStringAverage() {
        return Double.toString(this.averge);
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
