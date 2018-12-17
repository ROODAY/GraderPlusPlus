package controller;

import com.jfoenix.controls.JFXTabPane;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.SplitPane;
import javafx.scene.control.Tab;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class HomeScreen {
    public Sidebar sidebar;

    public void passStage(Stage stage) {
        sidebar.setRootStage(stage);
    }
}
