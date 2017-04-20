package sample;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Created by Rumman Sadiq on 12/23/2016.
 */
public class SignUp {
    public Button signUpButton;
    public TextField firstName, lastName, email, phone;
    public PasswordField password;
    public ChoiceBox date, month, year, country;


    public void haldleSignUpButton() throws IOException {
        DbConnect connect = new DbConnect();

        connect.storeData(firstName.getText().toString(), lastName.getText().toString(), email.getText().toString(), password.getText().toString(), date.getValue().toString(), month.getValue().toString(), year.getValue().toString(), phone.getText().toString(), country.getValue().toString());


        Stage primaryStage = (Stage) signUpButton.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("confirmScene.fxml"));
        primaryStage.setTitle("Classmate - Code Confirmation");
        primaryStage.setScene(new Scene(root, 1024, 705));//width, hight
        primaryStage.show();
        primaryStage.setMaximized(true);
    }
}
