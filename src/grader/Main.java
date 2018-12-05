package grader;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.SplitPane;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
//        Parent root = FXMLLoader.load(getClass().getResource("scene.fxml"));
//        primaryStage.setTitle("Grader++");
//        primaryStage.setScene(new Scene(root));
//        primaryStage.setMaximized(true);
//        primaryStage.show();
//
//        SplitPane splitPane = (SplitPane) primaryStage.getScene().lookup("#splitPane");
//        splitPane.setDividerPositions(0.15);
    }


    public static void main(String[] args) {
        //launch(args);
        
        StudentClass student = new StudentClass();
        
        //student.create(1345, "John", "Smith", "john@bu.edu", "Undergrad");
        
        student.uploadStudentsCSV("/Users/miguelvaldez/Desktop/students_roster.csv");
      
    }
}
