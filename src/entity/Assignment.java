/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import com.jfoenix.controls.*;
import com.jfoenix.controls.cells.editors.TextFieldEditorBuilder;
import com.jfoenix.controls.cells.editors.base.GenericEditableTreeTableCell;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import controller.AssignmentTable;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import model.AssignmentClass;
import model.StudentClass;

import java.io.IOException;
import java.io.Serializable;
import java.util.Collection;
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
    private String description;
    //private due date

    
    
    public Assignment(int id, int courseId, String name, int totalPoints, String type, String dateAssigned, String description) {
        super();
        this.id = id;
        this.courseId = courseId;
        this.name = name;
        this.totalPoints = totalPoints;
        this.type = type;
        this.dateAssigned = dateAssigned;
        this.description = description;
    }

    
    
    public Assignment() {
        super();
    }

    public Button getInfoButton() {
        Assignment localassignment = this;

        Button button = new JFXButton("Grade");
        button.getStyleClass().add("flatBtn");
        button.setOnAction(event -> {
            JFXDialog dialog = new JFXDialog();
            try {
                VBox dialogVbox = FXMLLoader.load(getClass().getResource("../view/gradeAssignmentModal.fxml"));

                Collection<StudentAssignment> dbassignments = AssignmentClass.getAllStudentwithAssignment(id);
                ObservableList<StudentAssignment> assignments = FXCollections.observableArrayList();
                assignments.addAll(dbassignments);

                double avg = assignments.stream()
                        .mapToInt(StudentAssignment::getpoints)
                        .average()
                        .orElse(0);
                double min = assignments.stream()
                        .mapToInt(StudentAssignment::getpoints)
                        .min()
                        .orElse(0);
                double max = assignments.stream()
                        .mapToInt(StudentAssignment::getpoints)
                        .max()
                        .orElse(0);

                ((Text)dialogVbox.lookup("#header")).setText("Grade " + name + " (" + dateAssigned + ")");
                ((Label)dialogVbox.lookup("#totalPoints")).setText("Total Points: " + totalPoints);
                ((Label)dialogVbox.lookup("#average")).setText("Average Score: " + avg);
                ((Label)dialogVbox.lookup("#min")).setText("Min Score: " + min);
                ((Label)dialogVbox.lookup("#max")).setText("Max Score: " + max);
                ((JFXTextArea)dialogVbox.lookup("#comments")).setText(localassignment.description);

                assignments.addListener((ListChangeListener<StudentAssignment>) c -> {
                    while (c.next()) {
                        if (c.wasPermutated() || c.wasUpdated()) {
                            double avg1 = assignments.stream()
                                    .mapToInt(StudentAssignment::getpoints)
                                    .average()
                                    .orElse(0);
                            double min1 = assignments.stream()
                                    .mapToInt(StudentAssignment::getpoints)
                                    .min()
                                    .orElse(0);
                            double max1 = assignments.stream()
                                    .mapToInt(StudentAssignment::getpoints)
                                    .max()
                                    .orElse(0);

                            ((Label)dialogVbox.lookup("#average")).setText("Average Score: " + avg1);
                            ((Label)dialogVbox.lookup("#min")).setText("Min Score: " + min1);
                            ((Label)dialogVbox.lookup("#max")).setText("Max Score: " + max1);
                        }
                    }
                });

                dialog.setContent(dialogVbox);
                StackPane root = (StackPane) button.getScene().lookup("#dialogPane");

                JFXTreeTableColumn<StudentAssignment, String> nameColumn = new JFXTreeTableColumn<>("Name");
                nameColumn.setPrefWidth(150);
                nameColumn.setCellValueFactory((TreeTableColumn.CellDataFeatures<StudentAssignment, String> param) -> {
                        StudentAssignment obj = param.getValue().getValue();
                        return new ReadOnlyObjectWrapper<>(obj.getStudentName() + " " + obj.getStudentLastName());
                });

                JFXTreeTableColumn<StudentAssignment, Number> gradeColumn = new JFXTreeTableColumn<>("Grade");
                gradeColumn.setPrefWidth(150);
                gradeColumn.setCellValueFactory((TreeTableColumn.CellDataFeatures<StudentAssignment, Number> param) ->
                        new ReadOnlyObjectWrapper<>(param.getValue().getValue().getpoints()));


                gradeColumn.setCellFactory((TreeTableColumn<StudentAssignment, Number> param) -> new GenericEditableTreeTableCell<>(new TextFieldEditorBuilder()));
                gradeColumn.setOnEditCommit((TreeTableColumn.CellEditEvent<StudentAssignment, Number> t)->{
                    StudentAssignment sa = t.getTreeTableView().getTreeItem(t.getTreeTablePosition().getRow()).getValue();
                    sa.setPoints(Integer.parseInt(String.valueOf(t.getNewValue())));
                });

                final TreeItem<StudentAssignment> tableroot = new RecursiveTreeItem<>(assignments, RecursiveTreeObject::getChildren);
                JFXTreeTableView<StudentAssignment> treeView = (JFXTreeTableView<StudentAssignment>) dialogVbox.lookup("#table");
                treeView.setShowRoot(false);
                treeView.setRoot(tableroot);
                treeView.setEditable(true);
                treeView.getColumns().setAll(nameColumn, gradeColumn);
                treeView.setColumnResizePolicy(TreeTableView.CONSTRAINED_RESIZE_POLICY);


                JFXButton saveChanges = (JFXButton) dialogVbox.lookup("#saveGrades");
                saveChanges.setOnAction(saveEvent -> {
                    for (StudentAssignment sa : assignments) {
                        StudentClass.updateStudentAssignment(sa);
                    }

                    localassignment.description = ((JFXTextArea)dialogVbox.lookup("#comments")).getText();
                    AssignmentClass.updateAssignment(localassignment);

                    //AssignmentTable.getTable().refresh();

                    dialog.close();
                    ((JFXTreeTableView)button.getScene().lookup("#table")).refresh();
                });


                dialog.show(root);
            } catch (IOException e) {
                e.printStackTrace();
            }

        });

        return button;
    }
    
    public int getId() {
        return id;
    }

    public double getClassAverage() {
        Collection<StudentAssignment> assignments = AssignmentClass.getAllStudentwithAssignment(id);
        return Math.round(assignments.stream()
                .mapToInt(StudentAssignment::getpoints)
                .average()
                .orElse(0) / totalPoints * 100 * 100) / 100.0;
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
    
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
