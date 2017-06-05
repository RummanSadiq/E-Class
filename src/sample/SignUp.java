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
        DBConnect connect = new DBConnect();


        connect.storeData(firstName.getText(), lastName.getText(), email.getText(), password.getText(), date.getValue().toString(), findMonth(month.getValue().toString()), year.getValue().toString(), phone.getText(), country.getValue().toString());


        Stage primaryStage = (Stage) signUpButton.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("confirmScene.fxml"));
        primaryStage.setTitle("Classmate - Code Confirmation");
        primaryStage.setScene(new Scene(root, 1366,768));//width, hight
        primaryStage.show();
        primaryStage.setMaximized(true);
    }

    private int findMonth(String s) {
        int monthNo = 0;
        if(s.equalsIgnoreCase("January")) {
            monthNo = 1;
        } else if(s.equalsIgnoreCase("February")) {
            monthNo = 2;
        } else if(s.equalsIgnoreCase("March")) {
            monthNo = 3;
        } else if(s.equalsIgnoreCase("April")) {
            monthNo = 4;
        } else if(s.equalsIgnoreCase("May")) {
            monthNo = 5;
        } else if(s.equalsIgnoreCase("June")) {
            monthNo = 6;
        } else if(s.equalsIgnoreCase("July")) {
            monthNo = 7;
        } else if(s.equalsIgnoreCase("August")) {
            monthNo = 8;
        } else if(s.equalsIgnoreCase("September")) {
            monthNo = 9;
        } else if(s.equalsIgnoreCase("October")) {
            monthNo = 10;
        } else if(s.equalsIgnoreCase("November")) {
            monthNo = 11;
        } else if(s.equalsIgnoreCase("December")) {
            monthNo = 12;
        }
        return monthNo;
    }
}
