package sample;

import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Rumman Sadiq on 12/27/2016.
 */
public class QuestionDetail implements Initializable {
    public Label title, detail;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        DbConnect connect = new DbConnect();
        connect.getAllQuestion(1);
        connect.getAllQuestion(connect.getCount());
        title.setText(connect.getQuestion_title());
        detail.setText(connect.getQuestion_detail());
    }
}
