package controller;

import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import java.io.IOException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Course;
import model.CourseAssignment;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class AssignmentTable extends AnchorPane{
    @FXML private JFXTreeTableView<CourseAssignment> table;
    @FXML private JFXTextField filter;
    private final ObservableList<CourseAssignment> data = FXCollections.observableArrayList();
    private Course course;

    public AssignmentTable() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../view/assignmentTable.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
            initializeTable();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    private void initializeTable() {
        filter.setPromptText("Search...");

        JFXTreeTableColumn<CourseAssignment, String> nameColumn = new JFXTreeTableColumn<>("Name");
        nameColumn.setPrefWidth(150);
        nameColumn.setCellValueFactory((TreeTableColumn.CellDataFeatures<CourseAssignment, String> param) ->{
            if(nameColumn.validateValue(param)) return param.getValue().getValue().getAssignmentName();
            else return nameColumn.getComputedValue(param);
        });

        JFXTreeTableColumn<CourseAssignment, String> typeColumn = new JFXTreeTableColumn<>("Type");
        typeColumn.setPrefWidth(150);
        typeColumn.setCellValueFactory((TreeTableColumn.CellDataFeatures<CourseAssignment, String> param) ->{
            if(typeColumn.validateValue(param)) return param.getValue().getValue().getAssignmentType();
            else return typeColumn.getComputedValue(param);
        });

        JFXTreeTableColumn<CourseAssignment, String> dateAssignedColumn = new JFXTreeTableColumn<>("Date Assigned");
        dateAssignedColumn.setPrefWidth(150);
        dateAssignedColumn.setCellValueFactory((TreeTableColumn.CellDataFeatures<CourseAssignment, String> param) ->{
            if(dateAssignedColumn.validateValue(param)) return param.getValue().getValue().getDateAssigned();
            else return dateAssignedColumn.getComputedValue(param);
        });

        JFXTreeTableColumn<CourseAssignment, Number> averageColumn = new JFXTreeTableColumn<>("Class Average");
        averageColumn.setPrefWidth(150);
        averageColumn.setCellValueFactory((TreeTableColumn.CellDataFeatures<CourseAssignment, Number> param) ->{
            if(averageColumn.validateValue(param)) return param.getValue().getValue().getClassAverage();
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
            table.setPredicate(assignment -> assignment.getValue().getAssignmentName().get().contains(newVal)
                    || assignment.getValue().getDateAssigned().get().contains(newVal)
                    || assignment.getValue().getAssignmentType().get().contains(newVal));
        });

        Label size = new Label();
        size.textProperty().bind(Bindings.createStringBinding(()-> table.getCurrentItemsCount()+"",
                table.currentItemsCountProperty()));
    }

}
