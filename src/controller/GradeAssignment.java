package controller;

import com.jfoenix.controls.JFXScrollPane;
import entity.Course;
import entity.Section;
import entity.Semester;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.VBox;
import model.CourseClass;
import model.SemesterClass;

import java.io.IOException;
import java.util.Collection;

public class GradeAssignment {
    public GradeAssignment() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/gradeAssignmentModal.fxml"));
        loader.setRoot(this);
        loader.setController(this);

        try {
            loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
