package controller;
import com.jfoenix.controls.*;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import entity.Assignment;
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
import model.*;

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
            } catch (IOException e) {
                e.printStackTrace();
            }

        });
    }

    public void initializeTable(String sectionName, int sectionId) {
        JFXSnackbar bar = new JFXSnackbar(studentPane);
        Collection<entity.Student> dbStudents = StudentClass.findAll();
        if (dbStudents.size() == 0) {
            bar.enqueue(new JFXSnackbar.SnackbarEvent(new JFXSnackbarLayout("No Students found for Section " + sectionName)));
        }


        JFXTreeTableColumn<Student, String> fnameColumn = new JFXTreeTableColumn<>("First Name");
        fnameColumn.setPrefWidth(150);
        fnameColumn.setCellValueFactory((TreeTableColumn.CellDataFeatures<Student, String> param) ->{
            if(fnameColumn.validateValue(param)) return param.getValue().getValue().firstName;
            else return fnameColumn.getComputedValue(param);
        });

        JFXTreeTableColumn<Student, String> lnameColumn = new JFXTreeTableColumn<>("Last Name");
        lnameColumn.setPrefWidth(150);
        lnameColumn.setCellValueFactory((TreeTableColumn.CellDataFeatures<Student, String> param) ->{
            if(lnameColumn.validateValue(param)) return param.getValue().getValue().lastName;
            else return lnameColumn.getComputedValue(param);
        });

        JFXTreeTableColumn<Student, Number> gradeColumn = new JFXTreeTableColumn<>("Current Grade");
        gradeColumn.setPrefWidth(150);
        gradeColumn.setCellValueFactory((TreeTableColumn.CellDataFeatures<Student, Number> param) ->{
            if(gradeColumn.validateValue(param)) return param.getValue().getValue().currentGrade;
            else return gradeColumn.getComputedValue(param);
        });

        JFXTreeTableColumn<Student, Button> actionColumn = new JFXTreeTableColumn<>("");
        actionColumn.setPrefWidth(150);
        actionColumn.setSortable(false);
        actionColumn.setCellValueFactory((TreeTableColumn.CellDataFeatures<Student, Button> param) ->{
            ObservableValue<Button> observableBtn = new ReadOnlyObjectWrapper<>(param.getValue().getValue().getButton());

            if(actionColumn.validateValue(param)) return observableBtn;
            else return actionColumn.getComputedValue(param);
        });


        ObservableList<Student> students = FXCollections.observableArrayList();

        for (int i = 0; i < 1000; i++) {
            double grade = new Random().nextInt(100);

            byte[] arr1 = new byte[5]; // length is bounded by 7
            new Random().nextBytes(arr1);
            String fname = new String(arr1, Charset.forName("UTF-8"));

            byte[] arr2 = new byte[7]; // length is bounded by 7
            new Random().nextBytes(arr2);
            String lname = new String(arr2, Charset.forName("UTF-8"));

            students.add(new Student(fname, lname, grade));
        }

        final TreeItem<Student> root = new RecursiveTreeItem<>(students, RecursiveTreeObject::getChildren);
        table.setRoot(root);
        table.setShowRoot(false);
        table.setEditable(true);
        table.getColumns().setAll(fnameColumn, lnameColumn, gradeColumn, actionColumn);
        table.setColumnResizePolicy(TreeTableView.CONSTRAINED_RESIZE_POLICY);

        filter.textProperty().addListener((o,oldVal,newVal)->{
            table.setPredicate(assignment -> assignment.getValue().firstName.get().contains(newVal)
                    || assignment.getValue().lastName.get().contains(newVal));
        });

        count.textProperty().bind(Bindings.createStringBinding(()-> table.getCurrentItemsCount()+" Records",
                table.currentItemsCountProperty()));
    }

}
