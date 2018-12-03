package grader;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Optional;

public class Controller {
    public Pane sidebar;
    public VBox semesterContainer;

    private TitledPane generateSidebarTP(String paneText, String buttonText) {
        try {
            TitledPane semesterPane = FXMLLoader.load(getClass().getResource("sidebarTitledPane.fxml"));
            semesterPane.setText(paneText);
            Button addButton = ((Button)semesterPane.getContent().lookup("#addButton"));
            addButton.setText(buttonText);
            return  semesterPane;
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    private void fixWidths(Control node, Pane contentWrapper) {
        node.setMinWidth(semesterContainer.getWidth());
        semesterContainer.widthProperty().addListener((obs, oldScene, newScene) -> node.setMinWidth(semesterContainer.getWidth()));
        int index = contentWrapper.getChildren().size() - 1;
        contentWrapper.getChildren().add(index, node);
    }

    public void createNewSemester() {
        TextInputDialog dialog = new TextInputDialog("2018/Fall");
        dialog.setTitle("Add New Semester");
        dialog.setContentText("Please enter the semester:");
        Optional<String> result = dialog.showAndWait();
        result.ifPresent(semester -> {
            TitledPane semesterPane = generateSidebarTP(semester, "Add Class");
            Button addButton = ((Button)semesterPane.getContent().lookup("#addButton"));
            VBox content = ((VBox)semesterPane.getContent().lookup("#contentWrapper"));
            addButton.setOnAction(action -> createNewClass(content));

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
            TitledPane classPane = generateSidebarTP(classname, "Add Section");
            Button addButton = ((Button)classPane.getContent().lookup("#addButton"));
            VBox content = ((VBox)classPane.getContent().lookup("#contentWrapper"));
            addButton.setOnAction(action -> createNewSection(content));

            classPane.setExpanded(false);
            fixWidths(classPane, vbox);
        });
    }

    private void createNewSection(VBox vbox) {
        TextInputDialog dialog = new TextInputDialog("A1");
        dialog.setTitle("Add New Section");
        dialog.setContentText("Please enter the section:");
        Optional<String> result = dialog.showAndWait();
        result.ifPresent(sectionName -> {
            Button section = new Button(sectionName);
            fixWidths(section, vbox);
        });
    }
}
