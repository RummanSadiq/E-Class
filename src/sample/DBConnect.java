package sample;

import java.sql.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 * Created by Rumman Sadiq on 12/23/2016.
 */
public class DBConnect {
    private Connection con;
    private Statement st;
    private ResultSet rs;
    private PreparedStatement ps;
    private static String sessionName;
    private static String sessionFirstName;
    private static String sessionLastName;
    private static int sessionId;
    private static String sessionEmail;
    private static int sessionPoints;
    private int question_id;
    private String question_title;
    private String question_detail;
    private String question_date;
    private String question_user;
    private int question_userId;
    private int question_subject_id;
    private int count = 0;
    public int searchId[];
    private String question_attachment;
    private int question_helpful;
    private int question_authorId;
    private String question_subject_name;
    private int answer_id;
    private String answer_detail;
    private String answer_date;
    private int answer_authorId;
    private String answer_author_name;

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    private boolean flag = false;

    public DBConnect(){



        try{
            Class.forName("com.mysql.jdbc.Driver");

            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/Classmate", "root", "");
            st = con.createStatement();

        }catch(Exception ex){
            ex.printStackTrace();
        }
    }

    public void storeQuestion(String title, String detail, String attachment, int subjectID){
        int uniqueID = findUniqueID("question");
        String query = "insert into question values(?,?,?,now(),?,?,?,?,?)";
        try{
            ps = con.prepareStatement(query);

            ps = con.prepareStatement(query);
            ps.setInt(1, uniqueID);
            ps.setString(2, title);
            ps.setString(3, detail);
            ps.setString(4, attachment);
            ps.setInt(5, 0);
            ps.setInt(6, 0);
            ps.setInt(7, sessionId);
            ps.setInt(8, subjectID);

            ps.execute();


        } catch (Exception ex){
            ex.printStackTrace();
        }
    }

    public void storeData(String firstName, String lastName, String email, String password, String date, int month, String year, String phone, String country){
        int uniqueID = findUniqueID("user");

        String dateString = year + "-" + month + "-" + date;
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        java.util.Date myDate = null;
        try {
            myDate = formatter.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        String query = "insert into user values(?,?,?,?,?,?,?,now(),?,?,?)";
        try {
            ps = con.prepareStatement(query);
            ps.setInt(1, uniqueID);
            ps.setString(2, firstName);
            ps.setString(3, lastName);
            ps.setString(4, email);
            ps.setString(5, password);
            ps.setInt(6, 0);
            ps.setInt(7, 0);
            ps.setDate(8, new java.sql.Date(myDate.getTime()));
            ps.setString(9, phone);
            ps.setString(10, country);

            ps.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }


    public ArrayList<Integer> searchQuestion(String str) {

        ArrayList<Integer> list = new ArrayList<>();
        try{
            String query  = "select question_id, title from question";
            rs = st.executeQuery(query);
            while(rs.next()){
                question_title = rs.getString("title");
                if(question_title.contains(str)){
                    list.add(rs.getInt("question_id"));
                }
            }
            query  = "select question_id, detail from question";
            rs = st.executeQuery(query);
            while(rs.next()){
                question_detail = rs.getString("detail");
                if(question_detail.contains(str)){
                    if(!list.contains(rs.getInt("question_id"))) {
                        list.add(rs.getInt("question_id"));
                    }
                }
            }

        }catch(Exception ex){
            ex.printStackTrace();
        }
        return list;
    }



    public int findID(String title) {

        try{
            String query  = "select * from question";
            rs = st.executeQuery(query);
            count = 0;
            while(rs.next()){
                count++;
                question_title = rs.getString("title");
                if(question_title.equalsIgnoreCase(title)){
                    return rs.getInt("question_id");
                }
            }

        }catch(Exception ex){
            ex.printStackTrace();
        }
        return 1;
    }

    public void getQuestion(int id){

        try{
            String query  = "select * from question where question_id = " + id;
            rs = st.executeQuery(query);
            if(rs.next()) {
                question_id = rs.getInt("question_id");
                question_title = rs.getString("title");
                question_detail = rs.getString("detail");
                question_date = rs.getString("time_of_question");
                question_attachment = rs.getString("attachment");
                question_helpful = rs.getInt("helpful");
                question_authorId = rs.getInt("user_id");
                question_subject_id = rs.getInt("subject_id");
                question_subject_name = findSubjectName(question_subject_id);

            }
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }

    public String findSubjectName(int question_subject_id) {
        String query = "select subject_name from subject where subject_id = " + question_subject_id;
        String name =null;
        try {
            rs = st.executeQuery(query);
            if(rs.next()) {
                name = rs.getString(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return name;
    }


    public void printData(){
        try{
            String query  = "select * from AccInfo";
            rs = st.executeQuery(query);
            System.out.println("Records from Database");
            while(rs.next()){
                String name = rs.getString("f_name");
                String dob = rs.getString("dob");
                String email = rs.getString("email");
                String pass = rs.getString("password");
                System.out.println("Name: " + name + "Date of Birth: " + dob + "Email: " + email + "Password: " + pass);
            }

        }catch(Exception ex){
            ex.printStackTrace();
        }
    }


    public String getQuestion_user() {
        String firstName = null;
        String lastName = null;
        try{
            String query  = "select user.first_name, user.last_name from user join question on user.user_id = question.user_id where question.question_id = " + question_id;
            rs = st.executeQuery(query);
            if (rs.next()) {
                firstName = rs.getString(1);
                lastName = rs.getString(2);
            }

        }catch(Exception ex){
            ex.printStackTrace();
        }
        return firstName + " " + lastName;
    }

    public void setQuestion_user( String i) {
        this.question_user = i;
    }

    public boolean checkId(String email, String pass){
        Boolean idFlag = false;
        try{
            String query = "select * from user where email = '" + email + "' and password = '" + pass + "'";
            rs = st.executeQuery(query);
            if(rs.next()){
                sessionEmail = rs.getString("email");
                sessionId = rs.getInt("user_id");
                sessionFirstName = rs.getString("first_name");
                sessionLastName = rs.getString("last_name");
                sessionName = sessionFirstName + " " + sessionLastName;
                sessionPoints = rs.getInt("points");
                idFlag = true;
            }
        }catch(Exception ex){
            ex.printStackTrace();
        }

        return idFlag;
    }

    public int getSessionId(){
        return sessionId;
    }

    public String getSessionName(){
        return sessionName;
    }

    public String getQuestion_title() {
        return question_title;
    }

    public String getQuestion_detail() {
        return question_detail;
    }

    public String getQuestion_date() {
        return question_date;
    }

    public int getCount() {
        String query = "select count(question_id) from question";
        try {
            rs = st.executeQuery(query);
            if(rs.next()) {

                count = rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count;
    }


    public int getQuestion_userId() {
        return question_userId;
    }

    public void storeAnswer(String text, int questionID) {
        int uniqueID = findUniqueID("answer");
        String query = "insert into answer values(?,?,?,?,?,?,?,?, now())";
        try {
            ps = con.prepareStatement(query);
            ps.setInt(1, uniqueID);
            ps.setString(2, text);
            ps.setInt(3, questionID);
            ps.setInt(4, sessionId);
            ps.setInt(5, 0);
            ps.setInt(6, 0);
            ps.setInt(7, 0);
            ps.setInt(8, 0);

            ps.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private int findUniqueID(String tableName) {
        String query = "select * from " + tableName;
        int randomNumber = 0;
        int repeat = 0;
        while(true) {
            randomNumber = (int) (99999999 * Math.random());
            try {
                rs = st.executeQuery(query);
                while(rs.next()) {
                    if(rs.getInt(1) == randomNumber) {
                        repeat++;
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            if(repeat == 0) {
                break;
            }
        }
        System.out.println(randomNumber);
        return randomNumber;
    }

    public ArrayList<String> getSubjectNames() {
        ArrayList<String> list = new ArrayList<>();
        try{
            String query  = "select subject_name from subject";
            rs = st.executeQuery(query);
            while(rs.next()){
                String name = rs.getString("subject_name");
                list.add(name);
            }

        }catch(Exception ex){
            ex.printStackTrace();
        }
        return list;
    }

    public int findSubjectID(String subjectName) {
        int id = 0;
        try{
            String query  = "select subject_id from subject where subject_name like '" + subjectName + "'";
            rs = st.executeQuery(query);
            rs.next();
            id = rs.getInt("subject_id");

        }catch(Exception ex){
            ex.printStackTrace();
        }
        return id;
    }

    public int getQuestion_subject_id() {
        return question_subject_id;
    }

    public void setQuestion_subject_id(int question_subject) {
        this.question_subject_id = question_subject;
    }

    public String getQuestion_attachment() {
        return question_attachment;
    }

    public void setQuestion_attachment(String question_attachment) {
        this.question_attachment = question_attachment;
    }

    public int getQuestion_helpful() {
        return question_helpful;
    }

    public void setQuestion_helpful(int question_helpful) {
        this.question_helpful = question_helpful;
    }

    public int getQuestion_autherId() {
        return question_authorId;
    }

    public void setQuestion_autherId(int question_outhorId) {
        this.question_authorId = question_outhorId;
    }

    public int[] getAllIDs() {
        int[] arr = new int[getCount()];
        int i = 0;
        String query = "select question_id from question";
        try {
            rs = st.executeQuery(query);
            while(rs.next()) {
                arr[i] = rs.getInt(1);
                i++;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return arr;
    }

    public String getQuestion_subject_name() {
        return question_subject_name;
    }

    public void setQuestion_subject_name(String question_subject_name) {
        this.question_subject_name = question_subject_name;
    }

    public int[] getAllAnswers(int questionID) {
        int[] arr = new int[getAnswerCount(questionID)];
        String query = "select answer_id from answer where question_id = " + questionID;
        int i = 0;
        try {
            rs = st.executeQuery(query);
            while(rs.next()) {
                arr[i] = rs.getInt("answer_id");
                i++;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return arr;
    }

    private int getAnswerCount(int questionID) {
        int c = 0;
        String query = "select count(answer_id) from answer where question_id = " + questionID;
        try {
            rs = st.executeQuery(query);
            if(rs.next()) {
                c = rs.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return c;
    }

    public void getAnswer(int answerId) {
        try{
            String query  = "select * from answer where answer_id = " + answerId;
            rs = st.executeQuery(query);
            if(rs.next()) {
                answer_id = rs.getInt("answer_id");
                answer_detail = rs.getString("description");
                answer_date = String.valueOf(rs.getTimestamp("time_of_answer"));
                answer_authorId = rs.getInt("user_id");
                answer_author_name = findAnswerAuthorName(answer_authorId);

            }
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }

    private String findAnswerAuthorName(int answer_authorId) {
        String query = "Select first_name, last_name from user where user_id = " + answer_authorId;
        String name = null;
        try {
            rs = st.executeQuery(query);
            if(rs.next()) {
                name = rs.getString(1) + " " + rs.getString(2);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return name;
    }

    public int getAnswer_id() {
        return answer_id;
    }

    public void setAnswer_id(int answer_id) {
        this.answer_id = answer_id;
    }

    public String getAnswer_detail() {
        return answer_detail;
    }

    public void setAnswer_detail(String answer_detail) {
        this.answer_detail = answer_detail;
    }

    public String getAnswer_date() {
        return answer_date;
    }

    public void setAnswer_date(String answer_date) {
        this.answer_date = answer_date;
    }

    public String getAnswer_author_name() {
        return answer_author_name;
    }

    public void setAnswer_author_name(String answer_author_name) {
        this.answer_author_name = answer_author_name;
    }

    public boolean checkQuestionValidity(int id) {
        boolean flag = false;
        String query = "select user_id from question where question_id = " + id;
        try {
            rs = st.executeQuery(query);
            if(rs.next()) {
                if(rs.getInt(1)== sessionId) {
                    flag = true;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return flag;
    }

    public void deleteQuestion(int question_autherId) {

    }
}