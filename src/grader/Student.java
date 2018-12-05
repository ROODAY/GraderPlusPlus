package grader;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.math.BigDecimal;
import java.util.List;
public class Student {
	private SimpleStringProperty Name;
	private SimpleStringProperty sID;
	private SimpleStringProperty email;
	private SimpleStringProperty group;
	private double GPA;
	private List<StudentAssignment> assignments;
	private Button button;

	public void setButton() {
		this.button = new Button("more Info");
		button.setOnAction(
				new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent event) {
						showinfo();
//						final Stage dialog = new Stage();
//						dialog.initModality(Modality.APPLICATION_MODAL);
//						dialog.initOwner(primaryStage);
//						VBox dialogVbox = new VBox(20);
//						dialogVbox.getChildren().add(new Text("This is a Dialog"));
//						Scene dialogScene = new Scene(dialogVbox, 300, 200);
//						dialog.setScene(dialogScene);
//						dialog.initModality(Modality.NONE);
//						dialog.show();
					}
				});
	}

	public String getEmail() {
		return email.get();
	}

	public String getGroup() {
		return group.get();
	}

	public String getName() {
		return Name.get();
	}

	public String getsID() {
		return sID.get();
	}

	public void setGroup(String group) {
		this.group = new SimpleStringProperty(group);
	}

	public void setName(String name) {
		this.Name = new SimpleStringProperty(name);
	}

	public void setEmail(String email) {
		this.email = new SimpleStringProperty(email);
	}

	public void setsID(String sID) {
		this.sID = new SimpleStringProperty(sID);
	}

	public void setAssignments(List<StudentAssignment> salist) {
		// dummy set
		this.assignments = salist;

	}

	public void setGPA(int[] weights) {
		double[] calculateScore = new double[weights.length];
		for (int i = 0; i < weights.length;i++){
			calculateScore[i] = weights[i] * assignments.get(i).getScore() / 100;
		}
		double sum = 0;
		for (double i : calculateScore) {
			sum += i;
		}
		double gpa = sum * 4 / 100;
		BigDecimal dg = new BigDecimal(gpa);
		this.GPA = dg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
	}

	public double getGPA() {
		return GPA;
	}

	public String getStringGPA(){
		return Double.toString(this.GPA);
	}

	public List<StudentAssignment> getAssignments() {
		return this.assignments;
	}

	public Button getButton() {
		return button;
	}

	public void showinfo(){
		System.out.print("Student Name: " + this.getName()+ "        ");
		System.out.print("Student Email: " + this.getEmail()+ "        ");
		System.out.print("Student Group: " + this.getGroup()+ "        ");
		System.out.print("Student ID: " + this.getsID()+ "        ");
		System.out.print("Student GPA " + this.getGPA()+ "        ");
	}
}
