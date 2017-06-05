package sample;

import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;

/**
 * Created by Rumman Sadiq on 12/24/2016.
 */
public class WritePost implements Initializable {
    public Button chooseFile, postQuestion, back;
    public TextField filePath, questionTitle;
    public TextArea questionDetail;
    public ChoiceBox subjectChoice;
    private DBConnect connect;

    public void chooseFile() {
        FileChooser fc = new FileChooser();
        fc.setInitialDirectory(new File("C:\\Users\\Rumman Sadiq\\Pictures\\Screenshots"));
        fc.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("PDF Files", "*.pdf"),
                new FileChooser.ExtensionFilter("PNG Files", "*.png")
        );

        File selectedFile = fc.showOpenDialog(null);

        if(selectedFile != null) {
            filePath.setText(selectedFile.getAbsolutePath());
        }
    }

    public void postQuestion() throws IOException {





        int id = connect.findSubjectID(subjectChoice.getValue().toString());
        connect.storeQuestion(questionTitle.getText(), questionDetail.getText(), filePath.getText(), id);


        goToDash(postQuestion);
    }

    public void handleBack() throws IOException {

        goToDash(back);
    }

    public void goToDash(Button b) throws IOException {


        Stage primaryStage = (Stage) b.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("dashboard.fxml"));
        primaryStage.setTitle("Classmate - Dashboard");
        primaryStage.setScene(new Scene(root, 1366,706));//width, hight
        primaryStage.show();
        primaryStage.setMaximized(true);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        connect = new DBConnect();
        ArrayList<String> list;
        list = connect.getSubjectNames();
        subjectChoice.getItems().addAll(list);
    }
}
