package model;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class CourseAssignment {
    private String introduction;
    private String assignmentComments;
    private double totalPoints;
    private String assignmentName;
    private double averge;
    private double median;
    private double max;
    private double min;
    private String submitted;
    private String Date;
    private Button button;

    public void setAssignmentComments(String assignmentComments) {
        this.assignmentComments = assignmentComments;
    }

    public void setAssigmentName(String assigmentName) {
        this.assignmentName = assigmentName;
    }

    public void setButton() {
        this.button = new Button("more Info");
        button.setOnAction(
                new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
//						showinfo();

                        final Stage dialog = new Stage();
                        dialog.initModality(Modality.APPLICATION_MODAL);
//						dialog.initOwner(primaryStage);
                        VBox dialogVbox = new VBox(20);
                        dialogVbox.getChildren().add(new Text("   Assignment Min:  " + min + "        "));
                        dialogVbox.getChildren().add(new Text("   Assignment Max: " + max + "        "));
                        dialogVbox.getChildren().add(new Text("   Assignment median: " + median + "        "));
                        dialogVbox.getChildren().add(new Text("   Assignemnt Total Points: " + totalPoints + "        "));
                        dialogVbox.getChildren().add(new Text("   Assignment Comments : " + assignmentComments + "        "));
                        Scene dialogScene = new Scene(dialogVbox, 300, 200);
                        dialog.setScene(dialogScene);
                        dialog.initModality(Modality.NONE);
                        dialog.show();
                    }
                });
    }

    public void setDate(String date) {
        Date = date;
    }

    public void setSubmitted(String submitted) {
        this.submitted = submitted;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public void setTotalPoints(double totalPoints) {
        this.totalPoints = totalPoints;
    }

    public Button getButton() {
        return button;
    }

    public double getTotalPoints() {
        return totalPoints;
    }

    public String getDate() {
        return Date;
    }

    public String getSubmitted() {
        return submitted;
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
