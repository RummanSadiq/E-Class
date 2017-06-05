package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Rumman Sadiq on 12/27/2016.
 */
public class QuestionDetail implements Initializable {
    public Label title, detail;
    public Button back;
    public TextField answer;
    public GridPane answerGrid;
    public GridPane outerGrid;
    public Pane delete;
    private DBConnect connect;
    private int questionID;

    public VBox[] mainNode;
    public HBox[] answerby;
    public Label[] a_detail, a_date;
    public Hyperlink[] a_user;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        connect = new DBConnect();
        DashBoard db = new DashBoard();
        questionID = connect.findID(db.getStringField());
        connect.getQuestion(questionID);
//        connect.getQuestion(connect.getCount());
        title.setText(connect.getQuestion_title());
        detail.setText(connect.getQuestion_detail());
        int[] arr = connect.getAllAnswers(questionID);
//        outerGrid.getRowConstraints().get(6).setMinHeight(200);
        populateAnswers(arr.length, arr);
    }

    private void populateAnswers(int size, int[] array) {
        int key = size;
        size++;
        int j = 0;


        answerby = new HBox[size];
        answerby[j] = new HBox();

        a_detail = new Label[size];
        a_detail[j] = new Label();

        a_date = new Label[size];
        a_date[j] = new Label();

        a_user = new Hyperlink[size];
        a_user[j] = new Hyperlink();

        mainNode = new VBox[size];
        mainNode[j] = new VBox();


        int index = 0;
        while(key != 0){
            connect.getAnswer(array[key-1]);
            if(connect.checkQuestionValidity(array[key-1])) {
                delete.setVisible(true);
            } else {
                delete.setId("");
                delete.setVisible(false);
            }
            String detail = connect.getAnswer_detail();

            a_user[j].setText(connect.getAnswer_author_name());
            a_detail[j].setText(detail);
            a_detail[j].setWrapText(true);
//            a_detail[j].setMaxHeight(1000);
            a_date[j].setText(connect.getAnswer_date());
//            System.out.println(connect.getAnswer_date());

            Label space = new Label(" - ");
            answerby[j].getChildren().addAll(a_date[j]);
            answerby[j].getChildren().addAll(space);
            answerby[j].getChildren().addAll(a_user[j]);

            answerby[j].setAlignment(Pos.BOTTOM_LEFT);
            a_detail[j].setStyle("-fx-font-size: 18; -fx-text-fill: #282E34;");
//            a_detail[j].applyCss();

//            mainNode[j].setMinHeight(a_detail[j].getLayoutBounds().getWidth() +100);



            mainNode[j].getChildren().addAll(a_detail[j]);
            mainNode[j].getChildren().addAll(answerby[j]);


            mainNode[j].setPadding(new Insets(15, 20, 15, 20));


//            System.out.println(array[key-1]);

            key--;


            mainNode[j].setStyle(" -fx-border-width: 2 0 0 0; -fx-border-color: #D7D7D7;");
            mainNode[j].setId("answerRow");
            try{
                answerGrid.add(mainNode[j], 0, index, 1, 1);

            } catch (Exception ex){
                ex.printStackTrace();
            }
//            GridPane.setMargin(mainNode[j], new Insets(20, 20, 20, 20));
//            GridPane.setVgrow(mainNode[j], Priority.SOMETIMES);
            index++;
//            height += mainNode[j].getMinHeight();
//            System.out.println(height);
            j++;
            mainNode[j] = new VBox();
            a_date[j] = new Label();
            answerby[j] = new HBox();
            a_detail[j] = new Label();
            a_user[j] = new Hyperlink();


        }


    }

    public void handleBack() throws IOException {
        WritePost wp = new WritePost();

        wp.goToDash(back);
    }

    public void handlePost(ActionEvent actionEvent) throws IOException {
        connect.storeAnswer(answer.getText(), questionID);
        Stage primaryStage = (Stage) answerGrid.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("QuestionDetail.fxml"));
        primaryStage.setTitle("Classmate - Question Details!");
        primaryStage.setScene(new Scene(root, 1366,706));//width, hight
        primaryStage.show();
        primaryStage.setMaximized(true);
    }

    public void handleDelete(MouseEvent mouseEvent) {
            connect.deleteQuestion(connect.getQuestion_autherId());
    }
}
