package controller;

import com.jfoenix.controls.*;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import entity.Assignment;
import entity.Student;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ObservableValue;
import javafx.css.PseudoClass;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import java.io.IOException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.util.Duration;
import model.AssignmentClass;
import model.Course;
import model.CourseAssignment;
import model.StudentClass;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Random;

public class AssignmentTable extends AnchorPane{
    @FXML private AnchorPane assignmentPane;
    @FXML private JFXTreeTableView<CourseAssignment> table;
    @FXML private JFXTextField filter;
    @FXML private JFXButton addAssignment;
    private final ObservableList<CourseAssignment> data = FXCollections.observableArrayList();
    private Course course;

    public AssignmentTable() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../view/assignmentTable.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
            //initializeTable();
            initControls();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    private void initControls() {
        filter.setPromptText("Search...");
        addAssignment.setOnAction(action -> {
            JFXDialog dialog = new JFXDialog();
            try {
                GridPane node = FXMLLoader.load(getClass().getResource("../view/addAssignmentModal.fxml"));
                dialog.setContent(node);
                StackPane root = (StackPane) addAssignment.getScene().lookup("#dialogPane");
                dialog.show(root);
            } catch (IOException e) {
                e.printStackTrace();
            }

        });
    }

    public void initializeTable(String sectionName, int sectionId) {
        JFXSnackbar bar = new JFXSnackbar(assignmentPane);
        Collection<Assignment> dbAssignments = AssignmentClass.findAll();
        Collection<Student> dbStudents = StudentClass.findAll();

        if (dbAssignments.size() == 0) {
            bar.enqueue(new JFXSnackbar.SnackbarEvent(new JFXSnackbarLayout("No Assignments found for Section " + sectionName)));
        }
        if (dbStudents.size() == 0) {
            bar.enqueue(new JFXSnackbar.SnackbarEvent(new JFXSnackbarLayout("No Students found for Section " + sectionName)));
        }





        JFXTreeTableColumn<CourseAssignment, String> nameColumn = new JFXTreeTableColumn<>("Name");
        nameColumn.setPrefWidth(150);
        nameColumn.setCellValueFactory((TreeTableColumn.CellDataFeatures<CourseAssignment, String> param) ->{
            if(nameColumn.validateValue(param)) return param.getValue().getValue().assignmentName;
            else return nameColumn.getComputedValue(param);
        });

        JFXTreeTableColumn<CourseAssignment, String> typeColumn = new JFXTreeTableColumn<>("Type");
        typeColumn.setPrefWidth(150);
        typeColumn.setCellValueFactory((TreeTableColumn.CellDataFeatures<CourseAssignment, String> param) ->{
            if(typeColumn.validateValue(param)) return param.getValue().getValue().assignmentType;
            else return typeColumn.getComputedValue(param);
        });

        JFXTreeTableColumn<CourseAssignment, String> dateAssignedColumn = new JFXTreeTableColumn<>("Date Assigned");
        dateAssignedColumn.setPrefWidth(150);
        dateAssignedColumn.setCellValueFactory((TreeTableColumn.CellDataFeatures<CourseAssignment, String> param) ->{
            if(dateAssignedColumn.validateValue(param)) return param.getValue().getValue().dateAssigned;
            else return dateAssignedColumn.getComputedValue(param);
        });

        JFXTreeTableColumn<CourseAssignment, Number> averageColumn = new JFXTreeTableColumn<>("Class Average");
        averageColumn.setPrefWidth(150);
        averageColumn.setCellValueFactory((TreeTableColumn.CellDataFeatures<CourseAssignment, Number> param) ->{
            if(averageColumn.validateValue(param)) return param.getValue().getValue().classAverage;
            else return averageColumn.getComputedValue(param);
        });

        JFXTreeTableColumn<CourseAssignment, Button> actionColumn = new JFXTreeTableColumn<>("");
        actionColumn.setPrefWidth(150);
        actionColumn.setSortable(false);
        actionColumn.setCellValueFactory((TreeTableColumn.CellDataFeatures<CourseAssignment, Button> param) ->{
            ObservableValue<Button> observableBtn = new ReadOnlyObjectWrapper<>(param.getValue().getValue().getButton());

            if(actionColumn.validateValue(param)) return observableBtn;
            else return actionColumn.getComputedValue(param);
        });


        ObservableList<CourseAssignment> assignments = FXCollections.observableArrayList();

        String[] types = {"Homework", "Quiz", "Exam"};
        for (int i = 0; i < 1000; i++) {
            int index = new Random().nextInt(3);
            double points = new Random().nextInt(30) + 20;

            Random rnd = new Random();
            long ms = -946771200000L + (Math.abs(rnd.nextLong()) % (70L * 365 * 24 * 60 * 60 * 1000));
            Date dt = new Date(ms);


            DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
            String date = df.format(dt);

            assignments.add(new CourseAssignment(types[index] + " " + i, types[index], points, date));
        }

        final TreeItem<CourseAssignment> root = new RecursiveTreeItem<>(assignments, RecursiveTreeObject::getChildren);
        table.setRoot(root);
        table.setShowRoot(false);
        table.setEditable(true);
        table.getColumns().setAll(nameColumn, typeColumn, dateAssignedColumn, averageColumn, actionColumn);
        table.setColumnResizePolicy(TreeTableView.CONSTRAINED_RESIZE_POLICY);

        filter.textProperty().addListener((o,oldVal,newVal)->{
            table.setPredicate(assignment -> assignment.getValue().assignmentName.get().contains(newVal)
                    || assignment.getValue().dateAssigned.get().contains(newVal)
                    || assignment.getValue().assignmentType.get().contains(newVal));
        });

        Label size = new Label();
        size.textProperty().bind(Bindings.createStringBinding(()-> table.getCurrentItemsCount()+"",
                table.currentItemsCountProperty()));
    }

}
