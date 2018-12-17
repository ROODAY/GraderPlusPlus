
package entity;

import com.jfoenix.controls.*;
import com.jfoenix.controls.cells.editors.TextFieldEditorBuilder;
import com.jfoenix.controls.cells.editors.base.GenericEditableTreeTableCell;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import model.AssignmentClass;
import model.StudentClass;
import model.WeightClass;

import java.io.IOException;
import java.io.Serializable;
import java.util.Collection;
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
    
    
    
    public Student(int bu_id, int sectionId, int courseId, String name, String last_name, String email, String program, String comments, double ec) {
        super();
        this.id = bu_id;
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
        Student localstudent = this;
        Button button = new JFXButton("Grade");
        button.getStyleClass().add("flatBtn");
        button.setOnAction(event -> {
            JFXDialog dialog = new JFXDialog();

            try {
                VBox dialogVbox = FXMLLoader.load(getClass().getResource("../view/gradeStudentModal.fxml"));

                ((Text)dialogVbox.lookup("#header")).setText(first_name + " " + last_name);
                ((Label)dialogVbox.lookup("#currentGrade")).setText("Current Grade: " + getGrade());
                ((Label)dialogVbox.lookup("#program")).setText("Program: " + program);
                ((Label)dialogVbox.lookup("#email")).setText("Email: " + email);
                ((Label)dialogVbox.lookup("#buid")).setText("BU ID: U" + bu_id);
                ((JFXTextField)dialogVbox.lookup("#participation")).setText("");
                ((JFXTextField)dialogVbox.lookup("#extraCredit")).setText(String.valueOf(ec));
                ((JFXTextArea)dialogVbox.lookup("#comments")).setText(localstudent.comments);

                dialog.setContent(dialogVbox);

                StackPane root = (StackPane) button.getScene().lookup("#dialogPane");

                Collection<StudentAssignment> dbassignments = StudentClass.getAssignmentsbyStudent(id);
                ObservableList<StudentAssignment> assignments = FXCollections.observableArrayList();
                assignments.addAll(dbassignments);

                JFXTreeTableColumn<StudentAssignment, String> nameColumn = new JFXTreeTableColumn<>("Name");
                nameColumn.setPrefWidth(150);
                nameColumn.setCellValueFactory((TreeTableColumn.CellDataFeatures<StudentAssignment, String> param) -> {
                    StudentAssignment obj = param.getValue().getValue();
                    return new ReadOnlyObjectWrapper<>(obj.getName());
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

                JFXTreeTableColumn<StudentAssignment, Number> totalPointsColumn = new JFXTreeTableColumn<>("Total Points");
                totalPointsColumn.setPrefWidth(150);
                totalPointsColumn.setCellValueFactory((TreeTableColumn.CellDataFeatures<StudentAssignment, Number> param) ->
                        new ReadOnlyObjectWrapper<>(param.getValue().getValue().getTotalPoints()));

                final TreeItem<StudentAssignment> tableroot = new RecursiveTreeItem<>(assignments, RecursiveTreeObject::getChildren);
                JFXTreeTableView<StudentAssignment> treeView = (JFXTreeTableView<StudentAssignment>) dialogVbox.lookup("#table");
                treeView.setShowRoot(false);
                treeView.setRoot(tableroot);
                treeView.setEditable(true);
                treeView.getColumns().setAll(nameColumn, gradeColumn, totalPointsColumn);
                treeView.setColumnResizePolicy(TreeTableView.CONSTRAINED_RESIZE_POLICY);


                JFXButton saveChanges = (JFXButton) dialogVbox.lookup("#saveGrades");
                saveChanges.setOnAction(saveEvent -> {
                    for (StudentAssignment sa : assignments) {
                        StudentClass.updateStudentAssignment(sa);
                    }

                    localstudent.comments = ((JFXTextArea)dialogVbox.lookup("#comments")).getText();
                    StudentClass.updateStudent(localstudent);

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
        Collection<StudentAssignment> assignments = StudentClass.getAllStudentAssignments(id);
        int weightType = program.equals("Undergrad") ? 0 : 1;
        Collection<Weights> courseWeights = WeightClass.getWeightsForCourse(courseId);
        Weights weights = courseWeights.stream()
                .filter(uw -> uw.getType() == weightType)
                .findAny()
                .orElse(new Weights(courseId, weightType));

        //assignments.stream().filter(a -> a.ge)

        //Homework Quiz Exam

        return 0.0;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }
}
