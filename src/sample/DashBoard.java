package sample;

import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.ResourceBundle;

/**
 * Created by Rumman Sadiq on 12/23/2016.
 */
public class DashBoard implements Initializable {
    public Pane search, post;
    public GridPane grid;
    public ScrollBar scrollbar;
    public DBConnect connect = new DBConnect();
    public Hyperlink signout;


    public VBox[] subject, date, title;
    public HBox[] rowElements, postedBy;
    public Label[] q_title, q_postedby, q_date,  q_sub;
    public Hyperlink[] q_user;
    private static String stringField;
    public TextField searchBar;
    public Hyperlink userName;
    public Pane logo;


    public void handleSearch() {
        String str = searchBar.getText();
        ArrayList<Integer> list = connect.searchQuestion(str);
        Collections.reverse(list);
        int[] arr = list.stream().mapToInt(i -> i).toArray();
        grid.getChildren().removeAll(rowElements);
        populateFeed(arr.length, arr);

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        userName.setText(connect.getSessionName());
        int size = connect.getCount();
        int[] array = connect.getAllIDs();
        populateFeed(size, array);
//        scrollbar.setOrientation(Orientation.VERTICAL);
//        grid.add(scrollbar, 5, 0, 1, noRows);
    }

    private void populateFeed(int size, int[] array) {
        int key = size;
        size++;
        int j = 0;


        rowElements = new HBox[size];
        rowElements[j] = new HBox();

        q_title = new Label[size];
        q_title[j] = new Label();

        q_postedby = new Label[size];
        q_postedby[j] = new Label();

        q_date = new Label[size];
        q_date[j] = new Label();

        q_sub = new Label[size];
        q_sub[j] = new Label();

        q_user = new Hyperlink[size];
        q_user[j] = new Hyperlink();

        title = new VBox[size];
        title[j] = new VBox();

        subject = new VBox[size];
        subject[j] = new VBox();

        date = new VBox[size];
        date[j] = new VBox();

        postedBy = new HBox[size];
        postedBy[j] = new HBox();


        grid.setHgap(0);
        grid.setVgap(0);


        int noRows = 0;
        int index = 0;
        while(key != 0){

            title[j].setPrefHeight(92);
            title[j].setPrefWidth(600);
            title[j].setAlignment(Pos.CENTER_LEFT);
            title[j].setStyle("-fx-font-size: 17");
            q_title[j].setWrapText(true);
            postedBy[j].setPrefWidth(600);
            postedBy[j].setPrefHeight(34);
            q_postedby[j].setText("Posted by: ");
            q_postedby[j].setStyle("-fx-font-size: 12; -fx-text-fill: #828282;");
//            q_postedby[j].setPrefHeight(37);
//            q_postedby[j].setPrefWidth(85);
//            q_user[j].setPrefHeight(39);
//            q_user[j].setPrefWidth(100);
            q_user[j].setStyle("-fx-font-size: 12");

            date[j].setPrefWidth(180);
            date[j].setAlignment(Pos.CENTER);
            date[j].setStyle("-fx-font-size: 14");
            subject[j].setPrefWidth(350);
            subject[j].setPrefHeight(92);
            subject[j].setAlignment(Pos.CENTER);
            subject[j].setStyle("-fx-font-size: 14");

            rowElements[j].setStyle(" -fx-border-width: 0 0 2 0; -fx-border-color: #D7D7D7;");
//            rowElements[j].setPrefHeight(100);
////            rowElements[j].setMinHeight(200);
//            rowElements[j].setPrefWidth(860);

            if(j == 0){
                rowElements[j].setPadding(new Insets(015, 20, 015, 20));
            } else {
                rowElements[j].setPadding(new Insets(015, 20, 015, 20));
            }



            postedBy[j].getChildren().addAll(q_postedby[j], q_user[j]);
            title[j].getChildren().addAll(q_title[j], postedBy[j]);
            date[j].getChildren().addAll(q_date[j]);
            subject[j].getChildren().addAll(q_sub[j]);
            rowElements[j].getChildren().addAll(title[j], date[j], subject[j]);

            connect.getQuestion(array[key-1]);

            System.out.println(array[key-1]);

            q_user[j].setText(connect.getQuestion_user());
            q_title[j].setText(connect.getQuestion_title());
//
//            date[j].setPrefHeight(92);

            q_date[j].setText(connect.getQuestion_date());

            q_sub[j].setText(connect.findSubjectName(connect.getQuestion_subject_id()));

            key--;



            rowElements[j].setId("row");
            try{
                grid.add(rowElements[j], 0, index, 4, 1);
                rowElements[j].setOnMousePressed(new EventHandler<MouseEvent>() {
                    public void handle(MouseEvent me) {
                        try {
                            handleDetail(me);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });

            } catch (Exception ex){
                ex.printStackTrace();
            }
            GridPane.setMargin(rowElements[j], new Insets(0, 0, 0, 0));
            index++;
            j++;
            noRows++;
            rowElements[j] = new HBox();
            q_postedby[j] = new Label();
            q_title[j] = new Label();
            q_date[j] = new Label();
            q_sub[j] = new Label();
            q_user[j] = new Hyperlink();
            title[j] = new VBox();
            subject[j] = new VBox();
            date[j] = new VBox();
            postedBy[j] = new HBox();
        }
    }


    public void handleWriteQuestion() throws IOException {
        Stage primaryStage = (Stage) post.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("writePost.fxml"));
        primaryStage.setTitle("Classmate - Ask Question!");
        primaryStage.setScene(new Scene(root, 1366,768));//width, hight
        primaryStage.show();
        primaryStage.setMaximized(true);
    }

    public void handleDetail(MouseEvent e) throws IOException {

//        Integer colIndex = GridPane.getColumnIndex(grid);
//        int rowIndex = GridPane.getRowIndex(grid);

//        int row = (int)e.getSceneY();
        HBox node = (HBox) e.getSource();
        VBox vBox = (VBox) node.getChildren().get(0);
        Label label = (Label) vBox.getChildren().get(0);
        setStringField(label.getText());


        Stage primaryStage = (Stage) grid.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("QuestionDetail.fxml"));
        primaryStage.setTitle("Classmate - Question Details!");
        primaryStage.setScene(new Scene(root, 1366,706));//width, hight
        primaryStage.show();
        primaryStage.setMaximized(true);
    }

    public void handleSignOut() throws IOException {
        Stage primaryStage = (Stage) signout.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Classmate - Sign In!");
        primaryStage.setScene(new Scene(root, 1366,706));//width, hight
        primaryStage.show();
        primaryStage.setMaximized(true);
    }


    public String getStringField() {
        return stringField;
    }

    public void setStringField(String stringField) {
        this.stringField = stringField;
    }

    public void handleLogo(MouseEvent mouseEvent) throws IOException {
        Stage primaryStage = (Stage) logo.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("dashboard.fxml"));
        primaryStage.setTitle("Classmate - Dashboard");
        primaryStage.setScene(new Scene(root, 1366,705.9));//width, hight
        primaryStage.show();
        primaryStage.setMaximized(true);
    }
}
