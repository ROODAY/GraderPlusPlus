package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXScrollPane;
import entity.Course;
import entity.Section;
import entity.Semester;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import model.CourseClass;
import model.SectionClass;
import model.SemesterClass;

import java.io.IOException;
import java.util.Collection;
import java.util.Optional;

public class Sidebar extends AnchorPane {
    @FXML private VBox semesterContainer;
    @FXML private ScrollPane scrollContainer;
    @FXML private AnchorPane sidebar;

    public Sidebar() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/sidebar.fxml"));
        loader.setRoot(this);
        loader.setController(this);

        try {
            loader.load();
            JFXScrollPane.smoothScrolling(scrollContainer);

            Collection<Semester> semesters = SemesterClass.findAll();
            for (Semester semester : semesters) {
                VBox semesterPaneContent = initSemesterPane(semester.getName(), semester.getId());
                Collection<Course> courses = SemesterClass.getCourses(semester.getId());

                for (Course course : courses) {
                    VBox classPaneContent = initClassPane(course.getName(), course.getId(), semesterPaneContent);
                    Collection<Section> sections = CourseClass.getSections(course.getId());

                    for (Section section : sections) {
                        initSectionButton(section.getName(), section.getId(), classPaneContent);
                    }
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private HBox generateSidebarTP(String paneText, String buttonText) {
        try {
            HBox wrapper = FXMLLoader.load(getClass().getResource("../view/sidebarTitledPane.fxml"));
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
        node.setPrefWidth(contentWrapper.getWidth());
        contentWrapper.widthProperty().addListener((obs, oldScene, newScene) -> node.setPrefWidth(contentWrapper.getWidth()));
        int index = contentWrapper.getChildren().size() - 1;
        contentWrapper.getChildren().add(index, node);
    }

    private VBox initSemesterPane(String semesterName, int semesterId) {
        HBox wrapper = generateSidebarTP(semesterName, "Add Class");
        TitledPane semesterPane = (TitledPane) wrapper.getChildren().toArray()[0];
        Button addButton = ((Button)semesterPane.getContent().lookup("#addButton"));
        VBox content = ((VBox)semesterPane.getContent().lookup("#contentWrapper"));
        content.getProperties().put("semesterId", semesterId);
        addButton.setOnAction(action -> createNewClass(content));

        semesterPane.setExpanded(false);
        semesterPane.setPrefWidth(scrollContainer.getWidth()-5);

        scrollContainer.widthProperty().addListener((obs, oldScene, newScene) -> semesterPane.setPrefWidth(scrollContainer.getWidth()-5));
        semesterContainer.getChildren().add(wrapper);

        return content;
    }

    private VBox initClassPane(String className, int classId, VBox vbox) {
        HBox wrapper = generateSidebarTP(className, "Add Section");
        TitledPane classPane = (TitledPane) wrapper.getChildren().toArray()[0];
        Button addButton = ((Button)classPane.getContent().lookup("#addButton"));
        VBox content = ((VBox)classPane.getContent().lookup("#contentWrapper"));
        content.getProperties().put("classId", classId);
        addButton.setOnAction(action -> createNewSection(content));

        classPane.setExpanded(false);
        fixWidths(classPane, vbox);

        return content;
    }

    private void initSectionButton(String sectionName, int sectionId, VBox vbox) {
        JFXButton section = new JFXButton(sectionName);
        section.getStyleClass().add("flatBtn");
        section.setAlignment(Pos.BASELINE_LEFT);
        fixWidths(section, vbox);
    }

    @FXML public void createNewSemester() {
        TextInputDialog dialog = generateDialog("2018/Fall", "Add New Semester", "Please enter the semester:");
        Optional<String> result = dialog.showAndWait();
        result.ifPresent(semester -> {
            int semesterId = SemesterClass.create(semester);
            initSemesterPane(semester, semesterId);
        });
    }

    private void createNewClass(VBox vbox) {
        TextInputDialog dialog = generateDialog("CS 591", "Add New Class", "Please enter the Class:");
        Optional<String> result = dialog.showAndWait();
        result.ifPresent(classname -> {
            int semesterId = (int) vbox.getProperties().get("semesterId");
            int classId = CourseClass.create(classname, 1, semesterId);
            initClassPane(classname, classId, vbox);
        });
    }

    private void createNewSection(VBox vbox) {
        TextInputDialog dialog = generateDialog("A1", "Add New Section", "Please enter the Section:");
        Optional<String> result = dialog.showAndWait();
        result.ifPresent(sectionName -> {
            int classId = (int) vbox.getProperties().get("classId");
            int sectionId = SectionClass.create(sectionName, classId);
            initSectionButton(sectionName, sectionId, vbox);
        });
    }
}
