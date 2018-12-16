package model;

import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.beans.value.*;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.util.*;
import model.Course;
import model.CourseAssignment;
import model.Student;
import model.StudentAssignment;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Table extends Application {

    private TableView<Student> table = new TableView<Student>();
    private final ObservableList<Student> data = FXCollections.observableArrayList();
    private Course course;

    public static void main(String[] args) {
        launch(args);
    }

    public void setCouse(){

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
        CourseAssignment CS591_CA2 = new CourseAssignment();
        CS591_CA2.setAssigmentName("Midterm");
        CS591_CA2.setAssignmentComments("Midterm about OOD");
        CS591_CA2.setTotalPoints(99);
        CS591_CA2.setDate("010/01");
        CS591_CA2.setSubmitted("30/30");
        CourseAssignment CS591_CA3 = new CourseAssignment();
        CS591_CA3.setAssigmentName("Lab");
        CS591_CA3.setAssignmentComments("Lab experiment");
        CS591_CA3.setTotalPoints(66);
        CS591_CA3.setDate("11/01");
        CS591_CA3.setSubmitted("22/30");
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

    public void addData(){
        List<Student> students = course.getStudentList();
        for (Student stu:students){
            data.add(stu);
        }
    }

    public void setTable(){
        table.setEditable(true);
        setCouse();
        addData();
        addTableContent();
    }

    public void addTableContent(){
        List<CourseAssignment> CA = course.getCourseAssignmentList();
        TableColumn[] tableColumns = new TableColumn[CA.size()+3];
        for (int i  = 1 ; i < CA.size()+1;i++){
            tableColumns[i] = new TableColumn(CA.get(i-1).getAssigmentName());
            tableColumns[i].setMinWidth(100);
            final int temp= i-1;
            tableColumns[i].setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Student, String>, ObservableValue<String>>() {
                @Override
                public ObservableValue<String> call(CellDataFeatures<Student, String> arg0) {
                    return new SimpleStringProperty(arg0.getValue().getAssignments().get(temp).getStringScore());
                }
            });
        }

        tableColumns[0] = new TableColumn("Student Name");
        tableColumns[0].setMinWidth(100);
        tableColumns[0].setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Student, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(CellDataFeatures<Student, String> arg0) {
                return new SimpleStringProperty(arg0.getValue().getName());
            }
        });

        tableColumns[CA.size() +1] = new TableColumn("GPA");
        tableColumns[CA.size() +1].setMinWidth(100);
        tableColumns[CA.size() +1].setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Student, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(CellDataFeatures<Student, String> arg0) {
                return new SimpleStringProperty(arg0.getValue().getStringGPA());
            }
        });
        tableColumns[CA.size() + 2] = new TableColumn("Action");
        tableColumns[CA.size() + 2].setMinWidth(100);
        tableColumns[CA.size() + 2].setCellValueFactory(new PropertyValueFactory<Student,String>("button"));

        table.setItems(data);
        table.getColumns().addAll(tableColumns);
    }
    @Override
    public void start(Stage stage) {
        Scene scene = new Scene(new Group());
        stage.setTitle("Table View Sample");
        stage.setWidth(800);
        stage.setHeight(500);

//        final Label label = new Label("Student View");
//        label.setFont(new Font("Arial", 20));

        setTable();

        final VBox vbox = new VBox();
        vbox.setSpacing(5);
        vbox.setPadding(new Insets(10, 0, 0, 10));
        vbox.getChildren().addAll(table);

        ((Group) scene.getRoot()).getChildren().addAll(vbox);
        stage.setScene(scene);
        stage.show();
    }


}