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
import model.Student;
import model.StudentAssignment;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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

        Student s1 = new Student("John","U12377","undergraduate","331@bu.edu");
        s1.setAssignments(salist_1);


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

        List<StudentAssignment> salist_2 = new ArrayList<>();
        salist_2.add(sa2_1);
        salist_2.add(sa2_2);
        salist_2.add(sa2_3);


        Student s2 = new Student("Charles","U7235","graduate","34@gmail.com");
        s2.setAssignments(salist_2);

        CourseAssignment CS591_CA1 = new CourseAssignment("HW1","Homework",30,"01/10/2018");
        CourseAssignment CS591_CA2 = new CourseAssignment("Midterm","Exam",99,"01/20/2018");
        CS591_CA2.setAssignmentComments("Midterm about OOD");
        CourseAssignment CS591_CA3 = new CourseAssignment("Lab1","Lab",66,"01/02/2018");
        CS591_CA3.setAssignmentComments("Lab experiment");
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
        CS591.setAssigenmentInfo();


        this.course =  CS591;
    }

    public void addData(){
        List<CourseAssignment> assignments = course.getCourseAssignmentList();
        for (CourseAssignment ca:assignments){
            data.add(ca);
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


//        ObservableList<CourseAssignment> assignments = FXCollections.observableArrayList();
//        String[] types = {"Homework", "Quiz", "Exam"};
//        for (int i = 0; i < 1000; i++) {
//            int index = new Random().nextInt(3);
//            double points = new Random().nextInt(30) + 20;
//
//            Random rnd = new Random();
//            long ms = -946771200000L + (Math.abs(rnd.nextLong()) % (70L * 365 * 24 * 60 * 60 * 1000));
//            Date dt = new Date(ms);
//
//
//            DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
//            String date = df.format(dt);
//
//            assignments.add(new CourseAssignment(types[index] + " " + i, types[index], points, date));
//        }
        setCouse();
        addData();

        final TreeItem<CourseAssignment> root = new RecursiveTreeItem<>(data, RecursiveTreeObject::getChildren);
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
