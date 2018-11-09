package grader;

import javafx.event.ActionEvent;
import javafx.scene.control.Label;

public class Controller {
    public Label helloWorld;

    public void sayHellowWorld(ActionEvent actionEvent) {
        helloWorld.setText("Hello World!");
    }
}
