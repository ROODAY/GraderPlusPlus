package model;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.math.BigDecimal;
import java.util.List;
public class Student extends RecursiveTreeObject<Student> {
	private StringProperty Name;
	private String sID;
	private String email;
	private String group;
	private DoubleProperty GPA;
	private List<StudentAssignment> assignments;
	private JFXButton button;

	public Student(String name, String sID, String group,String email) {
		this.Name = new SimpleStringProperty(name);
		this.sID = sID;
		this.group = group;
		this.email = email;
		initInfoButton();
	}

	public void initInfoButton() {
		this.button = new JFXButton("More Info");
		this.button.getStyleClass().add("flatBtn");
		this.button.setOnAction(
				event -> {
					JFXDialog dialog = new JFXDialog();
					VBox dialogVbox = new VBox(20);
					dialogVbox.getChildren().add(new Text("   Student Name:  " + Name + "        "));
					dialogVbox.getChildren().add(new Text("   Student Email: " + email + "        "));
					dialogVbox.getChildren().add(new Text("   Student ID: " + sID + "        "));
					dialogVbox.getChildren().add(new Text("   Student Group: " + group + "        "));
					dialog.setContent(dialogVbox);

					StackPane root = (StackPane) button.getScene().lookup("#dialogPane");

					dialog.show(root);
				});
	}

	public String getEmail() {
		return email;
	}

	public String getGroup() {
		return group;
	}

	public StringProperty getName() {
		return Name;
	}

	public String getsID() {
		return sID;
	}

	public void setGroup(String group) {
		this.group = group;
	}


	public void setEmail(String email) {
		this.email = email;
	}

	public void setsID(String sID) {
		this.sID = sID;
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
		this.GPA = new SimpleDoubleProperty(dg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
	}

	public DoubleProperty getGPA() {
		return GPA;
	}

//	public String getStringGPA(){
//		return Double.toString(this.GPA);
//	}

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
