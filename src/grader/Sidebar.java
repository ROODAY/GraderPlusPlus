package grader;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.Optional;

public class Sidebar extends AnchorPane {
    @FXML private VBox semesterContainer;
    @FXML private ScrollPane scrollContainer;
    @FXML private AnchorPane sidebar;

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


    private HBox generateSidebarTP(String paneText, String buttonText) {
        try {
            HBox wrapper = FXMLLoader.load(getClass().getResource("sidebarTitledPane.fxml"));
            TitledPane pane = (TitledPane) wrapper.lookup("#titledPane");
            pane.setText(paneText);
            Button addButton = ((Button)pane.getContent().lookup("#addButton"));
            addButton.setText(buttonText);
            return  wrapper;
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
        //semesterContainer.setMinWidth(sidebar.getWidth());
        //sidebar.widthProperty().addListener((obs, oldScene, newScene) -> semesterContainer.setMinWidth(sidebar.getWidth()));
        //node.setMinWidth(semesterContainer.getWidth());
        //semesterContainer.widthProperty().addListener((obs, oldScene, newScene) -> node.setMinWidth(semesterContainer.getWidth()));
        int index = contentWrapper.getChildren().size() - 1;
        contentWrapper.getChildren().add(index, node);
    }

    @FXML public void createNewSemester() {
        TextInputDialog dialog = generateDialog("2018/Fall", "Add New Semester", "Please enter the semester:");
        Optional<String> result = dialog.showAndWait();
        result.ifPresent(semester -> {
            HBox wrapper = generateSidebarTP(semester, "Add Class");
            TitledPane semesterPane = (TitledPane) wrapper.getChildren().toArray()[0];
            Button addButton = ((Button)semesterPane.getContent().lookup("#addButton"));
            VBox content = ((VBox)semesterPane.getContent().lookup("#contentWrapper"));
            addButton.setOnAction(action -> createNewClass(content));

            semesterPane.setExpanded(false);
            //semesterPane.setMinWidth(semesterContainer.getWidth());

            //semesterContainer.widthProperty().addListener((obs, oldScene, newScene) -> semesterPane.setMinWidth(semesterContainer.getWidth()));
            semesterContainer.getChildren().add(wrapper);
        });
    }

    private void createNewClass(VBox vbox) {
        TextInputDialog dialog = generateDialog("CS 591", "Add New Class", "Please enter the Class:");
        Optional<String> result = dialog.showAndWait();
        result.ifPresent(classname -> {
            HBox wrapper = generateSidebarTP(classname, "Add Section");
            TitledPane classPane = (TitledPane) wrapper.getChildren().toArray()[0];
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
