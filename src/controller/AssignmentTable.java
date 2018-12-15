package controller;

import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.JFXTreeTableView;
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
import javafx.util.Callback;
import model.Course;
import model.CourseAssignment;
import model.Student;
import model.StudentAssignment;
import java.util.ArrayList;
import java.util.List;

public class AssignmentTable extends AnchorPane{
    @FXML private JFXTreeTableView<CourseAssignment> table;
    private final ObservableList<CourseAssignment> data = FXCollections.observableArrayList();
    private Course course;

    public AssignmentTable() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../view/assigenmentTable.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
            setTable();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    private void setCourse(){

        StudentAssignment sa1_1 = new StudentAssignment();
        sa1_1.setComments("not bad");
        sa1_1.setLostpoints(11);
        sa1_1.setTotalScore(30);
        sa1_1.setScore();

        StudentAssignment sa1_2 = new StudentAssignment();
        sa1_2.setComments("good job");
        sa1_2.setLostpoints(10);
        sa1_2.setTotalScore(90);
        sa1_2.setScore();

        StudentAssignment sa1_3 = new StudentAssignment();
        sa1_3.setComments("keep working");
        sa1_3.setLostpoints(40);
        sa1_3.setTotalScore(66);
        sa1_3.setScore();

        List<StudentAssignment> salist_1 = new ArrayList<>();
        salist_1.add(sa1_1);
        salist_1.add(sa1_2);
        salist_1.add(sa1_3);

        Student s1 = new Student();
        s1.setAssignments(salist_1);
        s1.setEmail("123@bu.edu");
        s1.setGroup("undergraduate");
        s1.setName("John");
        s1.setsID("U12377");
        s1.setButton();


        StudentAssignment sa2_1 = new StudentAssignment();
        sa2_1.setComments("good job");
        sa2_1.setLostpoints(1);
        sa2_1.setTotalScore(30);
        sa2_1.setScore();

        StudentAssignment sa2_2 = new StudentAssignment();
        sa2_2.setComments("good job");
        sa2_2.setLostpoints(5);
        sa2_2.setTotalScore(90);
        sa2_2.setScore();

        StudentAssignment sa2_3 = new StudentAssignment();
        sa2_3.setComments("good job");
        sa2_3.setLostpoints(5);
        sa2_3.setTotalScore(66);
        sa2_3.setScore();

        List<StudentAssignment> salist = new ArrayList<>();
        salist.add(sa2_1);
        salist.add(sa2_2);
        salist.add(sa2_3);

        Student s2 = new Student();
        s2.setAssignments(salist);
        s2.setEmail("789@bu.edu");
        s2.setGroup("graduate");
        s2.setName("Charles");
        s2.setsID("U7235");
        s2.setButton();

        CourseAssignment CS591_CA1 = new CourseAssignment();
        CS591_CA1.setAssigmentName("HW1");
        CS591_CA1.setAssignmentComments("Homework about BlackJack");
        CS591_CA1.setDate("09/01");
        CS591_CA1.setSubmitted("27/30");
        CS591_CA1.setTotalPoints(30);
        CS591_CA1.setButton();
        CourseAssignment CS591_CA2 = new CourseAssignment();
        CS591_CA2.setAssigmentName("Midterm");
        CS591_CA2.setAssignmentComments("Midterm about OOD");
        CS591_CA2.setTotalPoints(99);
        CS591_CA2.setDate("010/01");
        CS591_CA2.setSubmitted("30/30");
        CS591_CA2.setButton();
        CourseAssignment CS591_CA3 = new CourseAssignment();
        CS591_CA3.setAssigmentName("Lab");
        CS591_CA3.setAssignmentComments("Lab experiment");
        CS591_CA3.setTotalPoints(66);
        CS591_CA3.setDate("11/01");
        CS591_CA3.setSubmitted("22/30");
        CS591_CA3.setButton();
        List<CourseAssignment> coulist = new ArrayList<>();
        coulist.add(CS591_CA1);
        coulist.add(CS591_CA2);
        coulist.add(CS591_CA3);

        List<Student> stulist = new ArrayList<>();
        int[] weights_CS591 = new int[]{30,30,40};
        Course CS591 = new Course();
        CS591.setCourseName("CS591");
        CS591.setSemester("Fall");
        CS591.setYear(2018);

        CS591.setStudentList(stulist);

        CS591.addStudent(s1);

        CS591.addStudent(s2);
        CS591.setWeights(weights_CS591);
        CS591.setAllGPA();
        CS591.setCourseAssignmentList(coulist);
        CS591.printStudentScore();
        CS591.setAssigenmentInfo();


        this.course =  CS591;
    }

    private void addData(){
        List<CourseAssignment> assignments = course.getCourseAssignmentList();
        data.addAll(assignments);
    }

    private void setTable(){
        table.setEditable(true);
        setCourse();
        addData();
        addTreeData();
    }

    private void addTableContent(){
        List<CourseAssignment> CA = course.getCourseAssignmentList();
        TableColumn[] tableColumns = new TableColumn[5];

        tableColumns[0] = new TableColumn("Assignment Name");
        tableColumns[0].setMinWidth(100);
        tableColumns[0].setCellValueFactory(new Callback<TableColumn.CellDataFeatures<CourseAssignment, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<CourseAssignment, String> arg0) {
                return new SimpleStringProperty(arg0.getValue().getAssigmentName());
            }
        });

        tableColumns[1] = new TableColumn("Assignment Date");
        tableColumns[1].setMinWidth(100);
        tableColumns[1].setCellValueFactory(new Callback<TableColumn.CellDataFeatures<CourseAssignment, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<CourseAssignment, String> arg0) {
                return new SimpleStringProperty(arg0.getValue().getDate());
            }
        });

        tableColumns[2] = new TableColumn("Assignment Average");
        tableColumns[2].setMinWidth(100);
        tableColumns[2].setCellValueFactory(new Callback<TableColumn.CellDataFeatures<CourseAssignment, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<CourseAssignment, String> arg0) {
                return new SimpleStringProperty(arg0.getValue().getStringAverage());
            }
        });
        tableColumns[3] = new TableColumn("Submitted");
        tableColumns[3].setMinWidth(100);
        tableColumns[3].setCellValueFactory(new Callback<TableColumn.CellDataFeatures<CourseAssignment, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<CourseAssignment, String> arg0) {
                return new SimpleStringProperty(arg0.getValue().getSubmitted());
            }
        });

        tableColumns[4] = new TableColumn("Action");
        tableColumns[4].setMinWidth(100);
        tableColumns[4].setCellValueFactory(new PropertyValueFactory<CourseAssignment,String>("button"));

        //table.setItems(data);
        //table.getColumns().addAll(tableColumns);
    }

    private void addTreeData() {
        JFXTreeTableColumn<CourseAssignment, String> deptColumn = new JFXTreeTableColumn<>("Department");
        deptColumn.setPrefWidth(150);
        deptColumn.setCellValueFactory((TreeTableColumn.CellDataFeatures<CourseAssignment, String> param) ->{
            if(deptColumn.validateValue(param)) return param.getValue().getValue().department;
            else return deptColumn.getComputedValue(param);
        });

        JFXTreeTableColumn<CourseAssignment, String> empColumn = new JFXTreeTableColumn<>("Employee");
        empColumn.setPrefWidth(150);
        empColumn.setCellValueFactory((TreeTableColumn.CellDataFeatures<CourseAssignment, String> param) ->{
            if(empColumn.validateValue(param)) return param.getValue().getValue().userName;
            else return empColumn.getComputedValue(param);
        });

        JFXTreeTableColumn<CourseAssignment, String> ageColumn = new JFXTreeTableColumn<>("Age");
        ageColumn.setPrefWidth(150);
        ageColumn.setCellValueFactory((TreeTableColumn.CellDataFeatures<CourseAssignment, String> param) ->{
            if(ageColumn.validateValue(param)) return param.getValue().getValue().age;
            else return ageColumn.getComputedValue(param);
        });


        ageColumn.setCellFactory((TreeTableColumn<CourseAssignment, String> param) -> new GenericEditableTreeTableCell,
                String>(new TextFieldEditorBuilder()));
        ageColumn.setOnEditCommit((CellEditEvent<User, String> t)->{
            ((CourseAssignment) t.getTreeTableView().getTreeItem(t.getTreeTablePosition().getRow()).getValue()).age
                    .set(t.getNewValue());
        });

        empColumn.setCellFactory((TreeTableColumn<CourseAssignment, String> param) -> new GenericEditableTreeTableCell,
                String>(new TextFieldEditorBuilder()));
        empColumn.setOnEditCommit((CellEditEvent<User, String> t)->{
            ((CourseAssignment) t.getTreeTableView().getTreeItem(t.getTreeTablePosition().getRow()).getValue())
                    .userName.set(t.getNewValue());
        });

        deptColumn.setCellFactory((TreeTableColumn, String> param) ->
                new GenericEditableTreeTableCell<CourseAssignment, String>(new TextFieldEditorBuilder()));
        deptColumn.setOnEditCommit((CellEditEvent<CourseAssignment, String> t)->{
            ((CourseAssignment) t.getTreeTableView().getTreeItem(t.getTreeTablePosition().getRow()).getValue()).department.
                    set(t.getNewValue());
        });


// data
        ObservableList<User> users = FXCollections.observableArrayList();
        users.add(new User("Computer Department", "23","CD 1"));
        users.add(new User("Sales Department", "22","Employee 1"));
        users.add(new User("Sales Department", "22","Employee 2"));
        users.add(new User("Sales Department", "25","Employee 4"));
        users.add(new User("Sales Department", "25","Employee 5"));
        users.add(new User("IT Department", "42","ID 2"));
        users.add(new User("HR Department", "22","HR 1"));
        users.add(new User("HR Department", "22","HR 2"));

        for(int i = 0 ; i< 40000; i++){
            users.add(new User("HR Department", i%10+"","HR 2" + i));
        }
        for(int i = 0 ; i< 40000; i++){
            users.add(new User("Computer Department", i%20+"","CD 2" + i));
        }

        for(int i = 0 ; i< 40000; i++){
            users.add(new User("IT Department", i%5+"","HR 2" + i));
        }

// build tree
        final TreeItem<User> root = new RecursiveTreeItem<User>(users, RecursiveTreeObject::getChildren);

        JFXTreeTableView<User> treeView = new JFXTreeTableView<User>(root, users);
        treeView.setShowRoot(false);
        treeView.setEditable(true);
        treeView.getColumns().setAll(deptColumn, ageColumn, empColumn);

        JFXTextField filterField = new JFXTextField();
        filterField.textProperty().addListener((o,oldVal,newVal)->{
            treeView.setPredicate(user -> user.getValue().age.get().contains(newVal)
                    || user.getValue().department.get().contains(newVal)
                    || user.getValue().userName.get().contains(newVal));
        });

        Label size = new Label();
        size.textProperty().bind(Bindings.createStringBinding(()->treeView.getCurrentItemsCount()+"",
                treeView.currentItemsCountProperty()));
    }

}
