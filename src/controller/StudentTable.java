package controller;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import java.io.IOException;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Course;
import model.CourseAssignment;
import model.Student;
import model.StudentAssignment;
import java.util.ArrayList;
import java.util.List;

public class StudentTable extends AnchorPane{
    @FXML private JFXTreeTableView<Student> table;
    @FXML private JFXTextField filter;
    private final ObservableList<Student> data = FXCollections.observableArrayList();
    private Course course;

    public StudentTable() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../view/studentTable.fxml"));
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
        List<Student> students = course.getStudentList();
        for (Student stu:students){
            data.add(stu);
        }
    }

    private void initializeTable() {
        setCouse();
        addData();
        filter.setPromptText("Search...");

        List<CourseAssignment> CA = course.getCourseAssignmentList();

        JFXTreeTableColumn<Student, String> nameColumn = new JFXTreeTableColumn<>("Name");
        nameColumn.setPrefWidth(150);
        nameColumn.setCellValueFactory((TreeTableColumn.CellDataFeatures<Student, String> param) ->{
            if(nameColumn.validateValue(param)) return param.getValue().getValue().getName();
            else return nameColumn.getComputedValue(param);
        });
        List<JFXTreeTableColumn<Student, Number>> gradeColumns = new ArrayList<>(CA.size());
        for (int i  = 0 ; i < CA.size();i++){
            grade = new JFXTreeTableColumn<>(CA.get(i).getAssignmentName().toString());
            gradeColumns[i].setMinWidth(150);
            final int temp= i;
            gradeColumns[i].setCellValueFactory((TreeTableColumn.CellDataFeatures<Student,Number> param) ->{
                if(gradeColumns[temp].validateValue(param)) return param.getValue().getValue().getAssignments().get(temp-1).getShowscore();
                else return gradeColumns[temp].getComputedValue(param);
            });
        }

        JFXTreeTableColumn<Student, Number> GPAColumn = new JFXTreeTableColumn<>("GPA ");
        GPAColumn.setPrefWidth(150);
        GPAColumn.setCellValueFactory((TreeTableColumn.CellDataFeatures<Student, Number> param) ->{
            if(GPAColumn.validateValue(param)) return param.getValue().getValue().getGPA();
            else return GPAColumn.getComputedValue(param);
        });

        JFXTreeTableColumn<Student, Button> actionColumn = new JFXTreeTableColumn<>("");
        actionColumn.setPrefWidth(150);
        actionColumn.setSortable(false);
        actionColumn.setCellValueFactory((TreeTableColumn.CellDataFeatures<Student, Button> param) ->{
            ObservableValue<Button> observableBtn = new ReadOnlyObjectWrapper<>(param.getValue().getValue().getButton());
            if(actionColumn.validateValue(param)) return observableBtn;
            else return actionColumn.getComputedValue(param);
        });

        final TreeItem<Student> root = new RecursiveTreeItem<>(data, RecursiveTreeObject::getChildren);
        table.setRoot(root);
        table.setShowRoot(false);
        table.setEditable(true);
        table.getColumns().add(nameColumn);
        for (int i = 0; i < gradeColumns.length; i++) {
            table.getColumns().add(gradeColumns[i]);
        }
        table.getColumns().add(actionColumn);
        table.getColumns().setAll(nameColumn,actionColumn);

        table.setColumnResizePolicy(TreeTableView.CONSTRAINED_RESIZE_POLICY);

        filter.textProperty().addListener((o,oldVal,newVal)->{
            table.setPredicate(studentss -> studentss.getValue().getName().get().contains(newVal)
//                            || studentss.getValue().getAssignments().;
            );
        });

        Label size = new Label();
        size.textProperty().bind(Bindings.createStringBinding(()-> table.getCurrentItemsCount()+"",
                table.currentItemsCountProperty()));
    }

//    public void addTableContent(){
//        List<CourseAssignment> CA = course.getCourseAssignmentList();
//        TableColumn[] tableColumns = new TableColumn[CA.size()+3];
//        for (int i  = 1 ; i < CA.size()+1;i++){
//            //tableColumns[i] = new TableColumn(CA.get(i-1).getAssigmentName());
//            tableColumns[i].setMinWidth(100);
//            final int temp= i-1;
//            tableColumns[i].setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Student, String>, ObservableValue<String>>() {
//                @Override
//                public ObservableValue<String> call(TableColumn.CellDataFeatures<Student, String> arg0) {
//                    return new SimpleStringProperty(arg0.getValue().getAssignments().get(temp).getStringScore());
//                }
//            });
//        }
//
//        tableColumns[0] = new TableColumn("Student Name");
//        tableColumns[0].setMinWidth(100);
//        tableColumns[0].setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Student, String>, ObservableValue<String>>() {
//            @Override
//            public ObservableValue<String> call(TableColumn.CellDataFeatures<Student, String> arg0) {
//                return new SimpleStringProperty(arg0.getValue().getName());
//            }
//        });
//
//        tableColumns[CA.size() +1] = new TableColumn("GPA");
//        tableColumns[CA.size() +1].setMinWidth(100);
//        tableColumns[CA.size() +1].setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Student, String>, ObservableValue<String>>() {
//            @Override
//            public ObservableValue<String> call(TableColumn.CellDataFeatures<Student, String> arg0) {
//                return new SimpleStringProperty(arg0.getValue().getStringGPA());
//            }
//        });
//        tableColumns[CA.size() + 2] = new TableColumn("Action");
//        tableColumns[CA.size() + 2].setMinWidth(100);
//        tableColumns[CA.size() + 2].setCellValueFactory(new PropertyValueFactory<Student,String>("button"));
//
//        table.setItems(data);
//        table.getColumns().addAll(tableColumns);
//    }
}
