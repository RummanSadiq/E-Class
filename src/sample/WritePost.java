package sample;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

/**
 * Created by Rumman Sadiq on 12/24/2016.
 */
public class WritePost {
    public Button chooseFile, postQuestion, back;
    public TextField filePath, questionTitle, questionFrom, questionTo;
    public TextArea questionDetail;
    public DatePicker questionDate;

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
        LocalDate localDate = questionDate.getValue();
        Instant instant = Instant.from(localDate.atStartOfDay(ZoneId.systemDefault()));
        Date date = Date.from(instant);

        DbConnect connect = new DbConnect();
        java.sql.Timestamp sqlTimestamp = new java.sql.Timestamp(date.getTime());
        java.sql.Date dat = new java.sql.Date(sqlTimestamp.getTime());
        connect.storeQuestion(connect.getSessionId(),questionTitle.getText().toString(), questionDetail.getText().toString(), dat ,questionFrom.getText().toString(), questionTo.getText().toString(), filePath.getText().toString());



        goToDash(postQuestion);
    }

    public void handleBack() throws IOException {

        goToDash(back);
    }

    public void goToDash(Button b) throws IOException {


        Stage primaryStage = (Stage) b.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("dashboard.fxml"));
        primaryStage.setTitle("Classmate - Dashboard");
        primaryStage.setScene(new Scene(root, 1024, 705));//width, hight
        primaryStage.show();
        primaryStage.setMaximized(true);
    }
}
