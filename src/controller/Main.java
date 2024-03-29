package controller;

import javafx.animation.FadeTransition;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.SplitPane;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("/assets/logo.png")));

        Parent splash = FXMLLoader.load(getClass().getResource("../view/splashScreen.fxml"));
        FXMLLoader homeLoader = new FXMLLoader(getClass().getResource("../view/homeScreen.fxml"));
        Parent home = homeLoader.load();
        HomeScreen controller = homeLoader.getController();
        controller.passStage(primaryStage);
        primaryStage.setTitle("GradeBook");
        primaryStage.setScene(new Scene(splash));
        primaryStage.setMaximized(true);
        primaryStage.show();

        double sceneWidth = primaryStage.getScene().getWidth();
        double sceneHeight = primaryStage.getScene().getHeight();

        FadeTransition ft = new FadeTransition(Duration.millis(1000), splash);
        ft.setFromValue(1.0);
        ft.setToValue(0.0);
        ft.play();

        ft.setOnFinished(event -> {
            FadeTransition ft2 = new FadeTransition(Duration.millis(250), home);
            ft2.setFromValue(0.0);
            ft2.setToValue(1.0);
            ft2.play();
            primaryStage.setScene(new Scene(home, sceneWidth, sceneHeight));


            SplitPane splitPane = (SplitPane) primaryStage.getScene().lookup("#splitPane");
            splitPane.setDividerPositions(0.15);

            ChangeListener<Number> stageSizeListener = (observable, oldValue, newValue) ->
                    splitPane.setDividerPositions(0.15);

            primaryStage.widthProperty().addListener(stageSizeListener);
            primaryStage.heightProperty().addListener(stageSizeListener);
        });

    }


    public static void main(String[] args) {
        launch(args);
    }
}
