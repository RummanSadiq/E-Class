package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

    public class Main extends Application {

        private Stage primaryStage;

        @Override
        public void start(Stage primaryStage) throws Exception{
            this.primaryStage = primaryStage;
            Parent root = FXMLLoader.load(getClass().getResource("dashboard.fxml"));
            primaryStage.setTitle("Classmate - Sign In");
            primaryStage.setScene(new Scene(root, 1366,706));//width, hight
            primaryStage.show();
            primaryStage.setMaximized(true);
        }


    public static void main(String[] args) {

        DBConnect connect;
        connect = new DBConnect();
        launch(args);

    }

        public Stage getPrimaryStage() {
            return primaryStage;
        }
    }
