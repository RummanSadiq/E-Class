package sample;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Rumman Sadiq on 12/27/2016.
 */
public class QuestionDetail implements Initializable {
    public Label title, detail;
    public Button back;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        DbConnect connect = new DbConnect();
        DashBoard db = new DashBoard();
        connect.getAllQuestion(connect.findID(db.getStringField()));
//        connect.getAllQuestion(connect.getCount());
        title.setText(connect.getQuestion_title());
        detail.setText(connect.getQuestion_detail());
    }

    public void handleBack(ActionEvent actionEvent) throws IOException {
        WritePost wp = new WritePost();

        wp.goToDash(back);
    }
}
