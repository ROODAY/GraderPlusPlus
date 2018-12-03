package grader;

import javafx.scene.control.Button;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.AnchorPane;
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
            Button addButton = new Button("Add Class");
            VBox tpVBox = new VBox(addButton);
            AnchorPane tpLayout = new AnchorPane(tpVBox);
            AnchorPane.setTopAnchor(tpVBox, 0.0);
            AnchorPane.setRightAnchor(tpVBox, 0.0);
            AnchorPane.setBottomAnchor(tpVBox, 0.0);
            AnchorPane.setLeftAnchor(tpVBox, 20.0);
            TitledPane semesterPane = new TitledPane(semester, tpLayout);

            addButton.setOnAction(action -> createNewClass(tpVBox));

            semesterPane.setExpanded(false);
            semesterPane.setMinWidth(semesterContainer.getWidth());

            semesterContainer.widthProperty().addListener((obs, oldScene, newScene) -> semesterPane.setMinWidth(semesterContainer.getWidth()));
            semesterContainer.getChildren().add(semesterPane);
        });
    }

    private void createNewClass(VBox vbox) {
        TextInputDialog dialog = new TextInputDialog("CS 591");
        dialog.setTitle("Add New Class");
        dialog.setContentText("Please enter the Class:");
        Optional<String> result = dialog.showAndWait();
        result.ifPresent(classname -> {
            Button addButton = new Button("Add Section");
            VBox tpVBox = new VBox(addButton);
            AnchorPane tpLayout = new AnchorPane(tpVBox);
            AnchorPane.setTopAnchor(tpVBox, 0.0);
            AnchorPane.setRightAnchor(tpVBox, 0.0);
            AnchorPane.setBottomAnchor(tpVBox, 0.0);
            AnchorPane.setLeftAnchor(tpVBox, 20.0);
            TitledPane classPane = new TitledPane(classname, tpLayout);

            addButton.setOnAction(action -> createNewSection(tpVBox));

            classPane.setExpanded(false);
            classPane.setMinWidth(semesterContainer.getWidth());
            semesterContainer.widthProperty().addListener((obs, oldScene, newScene) -> classPane.setMinWidth(semesterContainer.getWidth()));
            int index = vbox.getChildren().size() - 1;
            vbox.getChildren().add(index, classPane);
        });
    }

    private void createNewSection(VBox vbox) {
        TextInputDialog dialog = new TextInputDialog("A1");
        dialog.setTitle("Add New Section");
        dialog.setContentText("Please enter the section:");
        Optional<String> result = dialog.showAndWait();
        result.ifPresent(section -> {
            VBox tpVBox = new VBox();
            AnchorPane tpLayout = new AnchorPane(tpVBox);
            AnchorPane.setTopAnchor(tpVBox, 0.0);
            AnchorPane.setRightAnchor(tpVBox, 0.0);
            AnchorPane.setBottomAnchor(tpVBox, 0.0);
            AnchorPane.setLeftAnchor(tpVBox, 20.0);
            TitledPane sectionPane = new TitledPane(section, tpLayout);

            sectionPane.setExpanded(false);
            sectionPane.setMinWidth(semesterContainer.getWidth());
            semesterContainer.widthProperty().addListener((obs, oldScene, newScene) -> sectionPane.setMinWidth(semesterContainer.getWidth()));
            int index = vbox.getChildren().size() - 1;
            vbox.getChildren().add(index, sectionPane);
        });
    }
}
