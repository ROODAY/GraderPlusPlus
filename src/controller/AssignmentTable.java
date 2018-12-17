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

public class AssignmentTable extends AnchorPane implements Table {
    @FXML private AnchorPane assignmentPane;
    @FXML private JFXTreeTableView<Assignment> table;
    @FXML private JFXTextField filter;
    @FXML private JFXButton addAssignment;
    @FXML private Label count;

    private ObservableList<Assignment> assignments = FXCollections.observableArrayList();

    public AssignmentTable() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../view/assignmentTable.fxml"));
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
        addAssignment.setOnAction(action -> {
            JFXDialog dialog = new JFXDialog();
            try {
                GridPane node = FXMLLoader.load(getClass().getResource("../view/addAssignmentModal.fxml"));
                dialog.setContent(node);
                StackPane root = (StackPane) addAssignment.getScene().lookup("#dialogPane");
                dialog.show(root);

                Button createBtn = (Button)node.lookup("#createButton");
                createBtn.setOnAction(act -> {
                    String name = ((JFXTextField)node.lookup("#nameField")).getText();
                    int points = Integer.parseInt(((JFXTextField)node.lookup("#pointsField")).getText());
                    String type = ((Label)((JFXComboBox)node.lookup("#typeField")).getValue()).getText();
                    String date = new SimpleDateFormat("MM/dd/yyyy").format(new Date());

                    Assignment assignment = AssignmentClass.create(Sidebar.getCurrentCourseId(), name, points, type, date, "");
                    AssignmentClass.addAssignmentToCourse(assignment);
                    assignments.add(assignment);
                    dialog.close();
                });
            } catch (IOException e) {
                e.printStackTrace();
            }

        });
    }

    public void initializeTable(String sectionName, int sectionId) {
        assignments.clear();
        JFXSnackbar bar = new JFXSnackbar(assignmentPane);
        Collection<Assignment> dbAssignments = AssignmentClass.findAll();
        if (dbAssignments.size() == 0) {
            bar.enqueue(new JFXSnackbar.SnackbarEvent(new JFXSnackbarLayout("No Assignments found for Section " + sectionName)));
        } else {
            assignments.addAll(dbAssignments);
        }

        addAssignment.setDisable(false);


        JFXTreeTableColumn<Assignment, String> nameColumn = new JFXTreeTableColumn<>("Name");
        nameColumn.setPrefWidth(150);
        nameColumn.setCellValueFactory((TreeTableColumn.CellDataFeatures<Assignment, String> param) ->
                new ReadOnlyObjectWrapper<>(param.getValue().getValue().getName()));

        JFXTreeTableColumn<Assignment, String> typeColumn = new JFXTreeTableColumn<>("Type");
        typeColumn.setPrefWidth(150);
        typeColumn.setCellValueFactory((TreeTableColumn.CellDataFeatures<Assignment, String> param) ->
                new ReadOnlyObjectWrapper<>(param.getValue().getValue().getType()));

        JFXTreeTableColumn<Assignment, String> dateAssignedColumn = new JFXTreeTableColumn<>("Date Assigned");
        dateAssignedColumn.setPrefWidth(150);
        dateAssignedColumn.setCellValueFactory((TreeTableColumn.CellDataFeatures<Assignment, String> param) ->
                new ReadOnlyObjectWrapper<>(param.getValue().getValue().getDateAssigned()));

        JFXTreeTableColumn<Assignment, Number> averageColumn = new JFXTreeTableColumn<>("Class Average");
        averageColumn.setPrefWidth(150);
        averageColumn.setCellValueFactory((TreeTableColumn.CellDataFeatures<Assignment, Number> param) ->
                new ReadOnlyObjectWrapper<>(param.getValue().getValue().getClassAverage()));

        JFXTreeTableColumn<Assignment, Button> actionColumn = new JFXTreeTableColumn<>("");
        actionColumn.setPrefWidth(150);
        actionColumn.setSortable(false);
        actionColumn.setCellValueFactory((TreeTableColumn.CellDataFeatures<Assignment, Button> param) ->{
            ObservableValue<Button> observableBtn = new ReadOnlyObjectWrapper<>(param.getValue().getValue().getInfoButton());

            if(actionColumn.validateValue(param)) return observableBtn;
            else return actionColumn.getComputedValue(param);
        });




        final TreeItem<Assignment> root = new RecursiveTreeItem<>(assignments, RecursiveTreeObject::getChildren);
        table.setRoot(root);
        table.setShowRoot(false);
        table.setEditable(true);
        table.getColumns().setAll(nameColumn, typeColumn, dateAssignedColumn, averageColumn, actionColumn);
        table.setColumnResizePolicy(TreeTableView.CONSTRAINED_RESIZE_POLICY);

        filter.textProperty().addListener((o,oldVal,newVal)->{
            table.setPredicate(assignment -> assignment.getValue().getName().contains(newVal)
                    || assignment.getValue().getDateAssigned().contains(newVal)
                    || assignment.getValue().getType().contains(newVal));
        });

        //count.textProperty().bind(Bindings.createStringBinding(()-> table.getCurrentItemsCount()+" Records", table.currentItemsCountProperty()));
    }

}
