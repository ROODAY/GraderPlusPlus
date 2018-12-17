package controller;

import com.jfoenix.controls.*;
import entity.Course;
import entity.Section;
import entity.Semester;
import entity.Weights;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import connector.CourseConnector;
import connector.SectionConnector;
import connector.SemesterConnector;
import connector.WeightsConnector;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public class Sidebar extends AnchorPane {
    @FXML private VBox semesterContainer;
    @FXML private ScrollPane scrollContainer;
    @FXML private AnchorPane sidebar;

    private static int currentCourseId;

    private static int currentSectionId;
    private static String currentSectionName;

    public static int getCurrentCourseId() {
        return currentCourseId;
    }

    public static int getCurrentSectionId() {
        return currentSectionId;
    }

    public static String getCurrentSectionName() {
        return currentSectionName;
    }

    public Sidebar() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/sidebar.fxml"));
        loader.setRoot(this);
        loader.setController(this);

        try {
            loader.load();
            JFXScrollPane.smoothScrolling(scrollContainer);

            Collection<Semester> semesters = SemesterConnector.findAll();
            for (Semester semester : semesters) {
                VBox semesterPaneContent = initSemesterPane(semester.getName(), semester.getId());
                Collection<Course> courses = SemesterConnector.getCourses(semester.getId());

                for (Course course : courses) {
                    VBox classPaneContent = initClassPane(course.getName(), course.getId(), semesterPaneContent);
                    Collection<Section> sections = CourseConnector.getSections(course.getId());

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

        classPane.expandedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                currentCourseId = classId;
            }
        });

        ContextMenu contextMenu = new ContextMenu();
        MenuItem item1 = new MenuItem("Course Settings");
        item1.setOnAction(event -> {
            JFXDialog dialog = new JFXDialog();
            try {
                GridPane dialogContent = FXMLLoader.load(getClass().getResource("../view/courseSettingsModal.fxml"));

                Collection<Weights> weights = WeightsConnector.getWeightsForCourse(classId);

                Weights ugrad = weights.stream()
                        .filter(uw -> uw.getType() == 0)
                        .findAny()
                        .orElse(WeightsConnector.create(classId, 0, 50, 30, 15, 5));
                Weights grad = weights.stream()
                        .filter(uw -> uw.getType() == 1)
                        .findAny()
                        .orElse(WeightsConnector.create(classId, 1, 50, 30, 15, 5));

                JFXSlider hwField = ((JFXSlider)dialogContent.lookup("#hwField"));
                hwField.setValue(ugrad.getHwWeight());
                JFXSlider quizField = ((JFXSlider)dialogContent.lookup("#quizField"));
                quizField.setValue(ugrad.getQuizWeight());
                JFXSlider examField = ((JFXSlider)dialogContent.lookup("#examField"));
                examField.setValue(ugrad.getExamWeight());
                JFXSlider partField = ((JFXSlider)dialogContent.lookup("#partField"));
                partField.setValue(ugrad.getParticipationWeight());

                JFXSlider gHwField = ((JFXSlider)dialogContent.lookup("#gHwField"));
                gHwField.setValue(grad.getHwWeight());
                JFXSlider gQuizField = ((JFXSlider)dialogContent.lookup("#gQuizField"));
                gQuizField.setValue(grad.getQuizWeight());
                JFXSlider gExamField = ((JFXSlider)dialogContent.lookup("#gExamField"));
                gExamField.setValue(grad.getExamWeight());
                JFXSlider gPartField = ((JFXSlider)dialogContent.lookup("#gPartField"));
                gPartField.setValue(grad.getParticipationWeight());

                List<JFXSlider> sliders = Arrays.asList(quizField, examField, hwField, partField, gQuizField, gExamField, gHwField, gPartField);
                for (JFXSlider slider : sliders) {
                    slider.valueProperty().addListener((obs, oldval, newVal) ->
                            slider.setValue(Math.round(newVal.doubleValue())));
                }

                JFXButton saveButton = (JFXButton) dialogContent.lookup("#saveWeights");
                Label error = (Label) dialogContent.lookup("#error");

                saveButton.setOnAction(saveEvent -> {
                    if ((hwField.getValue() + quizField.getValue() + examField.getValue() + partField.getValue()) != 100) {
                        error.setText("Undergrad weights != 100!");
                    } else if ((gHwField.getValue() + gQuizField.getValue() + gExamField.getValue() + gPartField.getValue()) != 100) {
                        error.setText("Graduate weights != 100!");
                    } else {
                        error.setText("");

                        ugrad.setHwWeight((int) hwField.getValue());
                        ugrad.setQuizWeight((int) quizField.getValue());
                        ugrad.setExamWeight((int) examField.getValue());
                        ugrad.setParticipationWeight((int) partField.getValue());

                        grad.setHwWeight((int) gHwField.getValue());
                        grad.setQuizWeight((int) gQuizField.getValue());
                        grad.setExamWeight((int) gExamField.getValue());
                        grad.setParticipationWeight((int) gPartField.getValue());

                        WeightsConnector.updateWeights(ugrad);
                        WeightsConnector.updateWeights(grad);

                        dialog.close();
                    }
                });

                dialog.setContent(dialogContent);
                StackPane root = (StackPane) classPane.getScene().lookup("#dialogPane");
                dialog.show(root);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        contextMenu.getItems().addAll(item1);
        classPane.setOnContextMenuRequested(event -> contextMenu.show(classPane, event.getScreenX(), event.getScreenY()));

        return content;
    }

    private void initSectionButton(String sectionName, int sectionId, VBox vbox) {
        JFXButton section = new JFXButton(sectionName);
        section.getStyleClass().add("flatBtn");
        section.setAlignment(Pos.BASELINE_LEFT);

        section.setOnAction(action -> {
            currentSectionId = sectionId;
            currentSectionName = sectionName;

            SplitPane pane = (SplitPane)sidebar.getScene().lookup("#splitPane");
            AnchorPane apane = (AnchorPane) pane.getItems().get(1);
            ObservableList<Tab> tabs = ((JFXTabPane) apane.getChildren().get(0)).getTabs();

            for (Tab tab : tabs) {
                Table table = (Table)tab.getContent();
                table.initializeTable(sectionName, sectionId);
            }
        });

        fixWidths(section, vbox);
    }

    @FXML public void createNewSemester() {
        TextInputDialog dialog = generateDialog("2018/Fall", "Add New Semester", "Please enter the semester:");
        Optional<String> result = dialog.showAndWait();
        result.ifPresent(semester -> {
            int semesterId = SemesterConnector.create(semester);
            initSemesterPane(semester, semesterId);
        });
    }

    private void createNewClass(VBox vbox) {
        TextInputDialog dialog = generateDialog("CS 591", "Add New Class", "Please enter the Class:");
        Optional<String> result = dialog.showAndWait();
        result.ifPresent(classname -> {
            int semesterId = (int) vbox.getProperties().get("semesterId");
            int classId = CourseConnector.create(classname, 1, semesterId, "");
            initClassPane(classname, classId, vbox);
        });
    }

    private void createNewSection(VBox vbox) {
        TextInputDialog dialog = generateDialog("A1", "Add New Section", "Please enter the Section:");
        Optional<String> result = dialog.showAndWait();
        result.ifPresent(sectionName -> {
            int classId = (int) vbox.getProperties().get("classId");
            int sectionId = SectionConnector.create(sectionName, classId);
            initSectionButton(sectionName, sectionId, vbox);
        });
    }
}
