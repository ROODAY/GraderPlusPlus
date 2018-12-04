package grader;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.SplitPane;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("/assets/logo.png")));

        Parent root = FXMLLoader.load(getClass().getResource("scene.fxml"));
        primaryStage.setTitle("GradeBook");
        primaryStage.setScene(new Scene(root));
        primaryStage.setMaximized(true);
        primaryStage.show();

        SplitPane splitPane = (SplitPane) primaryStage.getScene().lookup("#splitPane");
        splitPane.setDividerPositions(0.15);

        ChangeListener<Number> stageSizeListener = (observable, oldValue, newValue) ->
                splitPane.setDividerPositions(0.15);

        primaryStage.widthProperty().addListener(stageSizeListener);
        primaryStage.heightProperty().addListener(stageSizeListener);
    }


    public static void main(String[] args) {
        launch(args);
    }
}
