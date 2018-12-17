package controller;
import com.jfoenix.controls.*;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import entity.Assignment;
import entity.Student;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import java.io.IOException;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.util.Callback;
import model.SectionClass;
import model.StudentClass;

import java.nio.charset.Charset;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class StudentTable extends AnchorPane implements Table{
    @FXML private AnchorPane studentPane;
    @FXML private JFXTreeTableView<Student> table;
    @FXML private JFXTextField filter;
    @FXML private JFXButton addStudent;
    @FXML private Label count;

    private ObservableList<Student> students = FXCollections.observableArrayList();

    public StudentTable() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../view/studentTable.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
            initControls();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    private void initControls() {
        filter.setPromptText("Search...");
        addStudent.setOnAction(action -> {
            JFXDialog dialog = new JFXDialog();
            try {
                GridPane node = FXMLLoader.load(getClass().getResource("../view/addStudentModal.fxml"));
                dialog.setContent(node);
                StackPane root = (StackPane) addStudent.getScene().lookup("#dialogPane");
                dialog.show(root);

                Button createBtn = (Button)node.lookup("#createButton");
                createBtn.setOnAction(act -> {
                    String fname = ((JFXTextField)node.lookup("#fnameField")).getText();
                    String lname = ((JFXTextField)node.lookup("#lnameField")).getText();
                    int buid = Integer.parseInt(((JFXTextField)node.lookup("#buidField")).getText());
                    String email = ((JFXTextField)node.lookup("#emailField")).getText();
                    String program = ((Label)((JFXComboBox)node.lookup("#programField")).getValue()).getText();

                    Student student = StudentClass.create(buid, Sidebar.getCurrentSectionId(), Sidebar.getCurrentCourseId(), fname, lname, email, program);
                    students.add(student);
                    dialog.close();
                });

            } catch (IOException e) {
                e.printStackTrace();
            }

        });
    }

    public void initializeTable(String sectionName, int sectionId) {
        JFXSnackbar bar = new JFXSnackbar(studentPane);
        Collection<Student> dbStudents = SectionClass.getStudents(sectionId);
        if (dbStudents.size() == 0) {
            bar.enqueue(new JFXSnackbar.SnackbarEvent(new JFXSnackbarLayout("No Students found for Section " + sectionName)));
        } else {
            students.addAll(dbStudents);
        }

        addStudent.setDisable(false);

        JFXTreeTableColumn<Student, String> fnameColumn = new JFXTreeTableColumn<>("First Name");
        fnameColumn.setPrefWidth(150);
        fnameColumn.setCellValueFactory((TreeTableColumn.CellDataFeatures<Student, String> param) ->
                new ReadOnlyObjectWrapper<>(param.getValue().getValue().getFirst_name()));

        JFXTreeTableColumn<Student, String> lnameColumn = new JFXTreeTableColumn<>("Last Name");
        lnameColumn.setPrefWidth(150);
        lnameColumn.setCellValueFactory((TreeTableColumn.CellDataFeatures<Student, String> param) ->
                new ReadOnlyObjectWrapper<>(param.getValue().getValue().getLastName()));

        JFXTreeTableColumn<Student, String> programColumn = new JFXTreeTableColumn<>("Program");
        lnameColumn.setPrefWidth(150);
        programColumn.setCellValueFactory((TreeTableColumn.CellDataFeatures<Student, String> param) ->
                new ReadOnlyObjectWrapper<>(param.getValue().getValue().getProgram()));

        JFXTreeTableColumn<Student, Number> gradeColumn = new JFXTreeTableColumn<>("Current Grade");
        gradeColumn.setPrefWidth(150);
        gradeColumn.setCellValueFactory((TreeTableColumn.CellDataFeatures<Student, Number> param) ->
                new ReadOnlyObjectWrapper<>(param.getValue().getValue().getGrade()));

        JFXTreeTableColumn<Student, Button> actionColumn = new JFXTreeTableColumn<>("");
        actionColumn.setPrefWidth(150);
        actionColumn.setSortable(false);
        actionColumn.setCellValueFactory((TreeTableColumn.CellDataFeatures<Student, Button> param) ->{
            ObservableValue<Button> observableBtn = new ReadOnlyObjectWrapper<>(param.getValue().getValue().getInfoButton());

            if(actionColumn.validateValue(param)) return observableBtn;
            else return actionColumn.getComputedValue(param);
        });


        final TreeItem<Student> root = new RecursiveTreeItem<>(students, RecursiveTreeObject::getChildren);
        table.setRoot(root);
        table.setShowRoot(false);
        table.setEditable(true);
        table.getColumns().setAll(fnameColumn, lnameColumn, programColumn, gradeColumn, actionColumn);
        table.setColumnResizePolicy(TreeTableView.CONSTRAINED_RESIZE_POLICY);

        filter.textProperty().addListener((o,oldVal,newVal)->{
            table.setPredicate(assignment -> assignment.getValue().getFirst_name().contains(newVal)
                    || assignment.getValue().getLastName().contains(newVal));
        });

        //count.textProperty().bind(Bindings.createStringBinding(()-> table.getCurrentItemsCount()+" Records", table.currentItemsCountProperty()));
    }

}
