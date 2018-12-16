package model;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import java.util.ArrayList;
import java.util.List;

public class AddStudent extends Application {
    private Course course;
    Button btn;
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
    public static void main(String[] args) {
        launch(args);
    }

    public void setBtn() {
        this.btn = new Button("Add Individual Student");
        btn.setOnAction(
                new EventHandler<javafx.event.ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        final Stage dialog = new Stage();
                        dialog.initModality(Modality.APPLICATION_MODAL);

                        GridPane grid = new GridPane();
                        grid.setPadding(new Insets(10, 10, 10, 10));
                        grid.setVgap(50);
                        grid.setHgap(50);
                        //Defining the Name text field
                        final TextField name = new TextField();
                        name.setPromptText("Enter Student name.");
                        name.setPrefColumnCount(10);
                        GridPane.setConstraints(name, 0, 0);
                        grid.getChildren().add(name);
                        //Defining the email text field
                        final TextField email = new TextField();
                        email.setPromptText("Enter Student Email.");
                        GridPane.setConstraints(email, 0, 1);
                        grid.getChildren().add(email);
//                      //Defining the grade text field
                        List<CourseAssignment> cas = course.getCourseAssignmentList();
                        List<TextField> grades = new ArrayList<>(){};

                        for (int i = 0; i < cas.size(); i++) {
                            grades.add(new TextField());
                        }

                        for (int i = 0; i <cas.size();i++){
                            grades.get(i).setPrefColumnCount(15);
                            grades.get(i).setPromptText("Enter " +cas.get(i).getAssigmentName()+" Grade.");
                            GridPane.setConstraints(grades.get(i), 0, i+2);
                            grid.getChildren().add(grades.get(i));
                        }


                        //Defining the Submit button
                        Button submit = new Button("Submit");
                        GridPane.setConstraints(submit, 1, 0);
                        grid.getChildren().add(submit);
                        //Defining the Clear button
                        Button clear = new Button("Clear");
                        GridPane.setConstraints(clear, 1, 1);
                        grid.getChildren().add(clear);

                        //Adding a Label
                        final Label label = new Label();
                        GridPane.setConstraints(label, 1, 2);
                        GridPane.setColumnSpan(label, 2);
                        grid.getChildren().add(label);

                        //Setting an action for the Submit button
                        submit.setOnAction(new EventHandler<ActionEvent>() {

                            @Override
                            public void handle(ActionEvent e) {
                                if (    (name.getText() != null && !name.getText().isEmpty())&&
                                        (email.getText() != null && !email.getText().isEmpty())
                                        ) {
                                    label.setText("Student " + name.getText() + " have been added");
                                } else {
                                    label.setText("Please enter all information");
                                }
                            }
                        });

                        //Setting an action for the Clear button
                        clear.setOnAction(new EventHandler<ActionEvent>() {

                            @Override
                            public void handle(ActionEvent e) {
                                name.clear();
                                email.clear();
                                label.setText(null);
                            }
                        });

                        Scene dialogScene = new Scene(grid, 400, 450);

                        dialog.setScene(dialogScene);
                        dialog.initModality(Modality.NONE);
                        dialog.show();
                    }
                });
    }

    @Override
    public void start(Stage stage) {
        setCouse();
        List<CourseAssignment> cas = course.getCourseAssignmentList();
        System.out.println(cas.get(1).getAssigmentName());
        setBtn();
        HBox hbox = new HBox();
        hbox.getChildren().addAll(btn);
        Scene scene = new Scene(hbox);
        stage.setTitle("Table View Sample");
        stage.setWidth(800);
        stage.setHeight(500);
        stage.setScene(scene);
        stage.show();
    }
}
