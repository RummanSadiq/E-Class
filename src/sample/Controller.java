package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public class Controller {

    public Button signIn, signUp, changePassword;
    public VBox time, lock, qua;
    public TextField email;
    public PasswordField password;
    public Hyperlink forget;
    public Button confirmation;

    public void handleSignIn() throws IOException {

        DbConnect connect = new DbConnect();
        WritePost wp = new WritePost();

        if(connect.checkId(email.getText().toString(), password.getText().toString())){
            wp.goToDash(signIn);
        }
        else{
            email.setStyle("-fx-border-color: red;");
            password.setStyle("-fx-border-color: red;");
        }

    }


    private void slideShow() {
        int i = 0;
        int totalImages = 17;
        for (i = 0; i < totalImages; i++) {

        }
    }

    public void handleTime() {
        time.setStyle("-fx-background-image: url(/icons/sbtime.png); -fx-background-position: left center; -fx-background-repeat: stretch;-fx-background-size: 100%;");
    }

    public void handleTimeExit() {
        time.setStyle("-fx-background-image: url(/icons/stime.png); -fx-background-position: left center; -fx-background-repeat: stretch;-fx-background-size: 100%;");
    }

    public void handleLock() {
        lock.setStyle("-fx-background-image: url(/icons/sblock.png); -fx-background-position: left center; -fx-background-repeat: stretch;-fx-background-size: 100%;");
    }

    public void handleLockExit() {
        lock.setStyle("-fx-background-image: url(/icons/slock.png); -fx-background-position: left center; -fx-background-repeat: stretch;-fx-background-size: 100%;");
    }

    public void handleQua() {
        qua.setStyle("-fx-background-image: url(/icons/sbqua.png); -fx-background-position: left center; -fx-background-repeat: stretch;-fx-background-size: 100%;");
    }

    public void handleQuaExit() {
        qua.setStyle("-fx-background-image: url(/icons/squa.png); -fx-background-position: left center; -fx-background-repeat: stretch;-fx-background-size: 100%;");
    }

    public void handleSignUp() throws IOException {
        Stage primaryStage = (Stage) signUp.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("signupScene.fxml"));
        primaryStage.setTitle("Classmate - Sign Up");
        primaryStage.setScene(new Scene(root, 1024, 690));//width, hight
        primaryStage.show();
        primaryStage.setMaximized(true);
    }



    public void haldleForgetPassword() throws IOException {
        Stage primaryStage = (Stage) forget.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("codeReset.fxml"));
        primaryStage.setTitle("Classmate - Reset Password!");
        primaryStage.setScene(new Scene(root, 1024, 705));//width, hight
        primaryStage.show();
        primaryStage.setMaximized(true);
    }

    public void handleConfirmation() throws IOException {

        goToLogin(confirmation);
    }

    public void haldleChangePassword() throws IOException {

        goToLogin(changePassword);
    }

    public void goToLogin(Button b) throws IOException {
        Stage primaryStage = (Stage) b.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Classmate - Sign In");
        primaryStage.setScene(new Scene(root, 1024, 705));//width, hight
        primaryStage.show();
        primaryStage.setMaximized(true);
    }
}
