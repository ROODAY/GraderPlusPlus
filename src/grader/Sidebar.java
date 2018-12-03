package grader;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.Optional;

public class Sidebar extends AnchorPane {
    @FXML private VBox semesterContainer;

    public Sidebar() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("sidebar.fxml"));
        loader.setRoot(this);
        loader.setController(this);

        try {
            loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


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

    private TextInputDialog generateDialog(String defaultVal, String title, String content) {
        TextInputDialog dialog = new TextInputDialog(defaultVal);
        dialog.setTitle(title);
        dialog.setContentText(content);
        dialog.setHeaderText(null);
        return dialog;
    }

    private void fixWidths(Control node, Pane contentWrapper) {
        node.setMinWidth(semesterContainer.getWidth());
        semesterContainer.widthProperty().addListener((obs, oldScene, newScene) -> node.setMinWidth(semesterContainer.getWidth()));
        int index = contentWrapper.getChildren().size() - 1;
        contentWrapper.getChildren().add(index, node);
    }

    @FXML public void createNewSemester() {
        TextInputDialog dialog = generateDialog("2018/Fall", "Add New Semester", "Please enter the semester:");
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
        TextInputDialog dialog = generateDialog("CS 591", "Add New Class", "Please enter the Class:");
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
        TextInputDialog dialog = generateDialog("A1", "Add New Section", "Please enter the Section:");
        Optional<String> result = dialog.showAndWait();
        result.ifPresent(sectionName -> {
            Button section = new Button(sectionName);
            section.setAlignment(Pos.BASELINE_LEFT);
            fixWidths(section, vbox);
        });
    }
}
