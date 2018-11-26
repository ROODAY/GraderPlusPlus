package grader;

import javafx.scene.control.Button;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.util.Optional;

public class Controller {
    public Pane sidebar;
    public VBox semesterContainer;

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
}
