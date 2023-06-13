package controller_view;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * Class Definition
 * 
 * @author Umid Muzrapov, Second Author Jose Bernardo Montano
 *
 */
public class LoginCreateAccountPane extends HBox {

//  private Label label = new Label("Login or Create Account");
//  private Label label2 = new Label("What is up bitches!");
	private Button loginButton = new Button("Login or Create Account");

	public LoginCreateAccountPane() {
		this.getChildren().addAll(loginButton);
		this.setAlignment(Pos.CENTER);

//    this.add(label2, 1, 1);
	}
}