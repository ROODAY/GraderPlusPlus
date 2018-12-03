package grader;

import entity.Course;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javafx.scene.control.Button;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.util.Optional;

public class Controller {
    public Pane sidebar;
    public VBox semesterContainer;
    public Pane classesContainer;

    public void createNewSemester() {
        TextInputDialog dialog = new TextInputDialog("2018/Fall");
        dialog.setTitle("Add New Semester");
        dialog.setContentText("Please enter the semester:");
        Optional<String> result = dialog.showAndWait();
        result.ifPresent(semester -> {
            TitledPane semesterPane = new TitledPane(semester, new Button("Add Class"));
            semesterPane.setExpanded(false);
            semesterPane.setMinWidth(semesterContainer.getWidth());
            semesterContainer.widthProperty().addListener((obs, oldScene, newScene) -> {
                semesterPane.setMinWidth(semesterContainer.getWidth());
            });
            semesterContainer.getChildren().add(semesterPane);
        });
    }
    
    
    public void coursesTable() {

        List<Button> buttonlist = new ArrayList<>(); //our Collection to hold newly created Buttons
        CourseClass courses= new CourseClass();
        Collection<Course> result = courses.findAll();

        for (Course o : result){
            String courseTitle = o.getName(); //extract button text, adapt the String to the columnname that you are interested in
            buttonlist.add(new Button(courseTitle));
        }

        classesContainer.getChildren().addAll(buttonlist); //then add all your Buttons that you just created

    }
}
