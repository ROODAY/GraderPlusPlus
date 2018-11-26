package grader;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.SplitPane;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("scene.fxml"));
        primaryStage.setTitle("Grader++");
        primaryStage.setScene(new Scene(root));
        primaryStage.setMaximized(true);
        primaryStage.show();

        SplitPane splitPane = (SplitPane) primaryStage.getScene().lookup("#splitPane");
        splitPane.setDividerPositions(0.15);
    }


    public static void main(String[] args) {
        launch(args);
    }
}
