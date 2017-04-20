package sample;

import javafx.scene.control.DatePicker;

import java.sql.*;

/**
 * Created by Rumman Sadiq on 12/23/2016.
 */
public class DbConnect {
    private Connection con;
    private Statement st;
    private ResultSet rs;
    private PreparedStatement ps;
    private String sessionName;
    private String sessionFirstName;
    private String sessionLastName;
    private int sessionId;
    private String sessionEmail;
    private int question_id;
    private String question_title;
    private String question_detail;
    private String question_date;
    private String question_user;
    private int question_userId;
    private int question_bfrom;
    private int question_bto;
    private String question_subject;
    private int count = 0;
    public int searchId[];

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    private boolean flag = false;

    public DbConnect(){



        try{
            Class.forName("com.mysql.jdbc.Driver");

            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/Classmate", "root", "");
            st = con.createStatement();

        }catch(Exception ex){
            ex.printStackTrace();
        }
    }

    public void storeQuestion(int userId, String title, String detail,Date deadLine, String pointFrom, String pointTo, String file){
        String query = "insert into question (q_id, ref_id, title, detail, deadline, b_from, b_to, attach)"
                + "values (?, ?, ?, ?, ?, ?, ?, ?)";
        try{
            ps = con.prepareStatement(query);

            ps = con.prepareStatement(query);
            ps.setString(1, null);
            ps.setInt(2, userId);
            ps.setString(3, title);
            ps.setString(4, detail);
            ps.setDate(5, deadLine);
            ps.setString(6, pointFrom);
            ps.setString(7, pointTo);
            ps.setString(8, file);

            ps.execute();
            con.close();


        } catch (Exception ex){
            ex.printStackTrace();
        }
    }

    public void storeData(String firstName, String lastName, String email, String password, String date, String month, String year, String phone, String country){
        String query = " insert into accInfo (id, f_name, l_name, email, password, date_dob, month_dob, year_dob, number, country_code)"
                + " values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            ps = con.prepareStatement(query);
            ps.setString(1, null);
            ps.setString(2, firstName);
            ps.setString(3, lastName);
            ps.setString(4, email);
            ps.setString(5, password);
            ps.setString(6, date);
            ps.setString(7, month);
            ps.setString(8, year);
            ps.setString(9, phone);
            ps.setString(10, country);

            ps.execute();
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void searchQuestion(String str){
        try{
            String query  = "select * from question";
            rs = st.executeQuery(query);
            getAllQuestion(0);
            searchId = new int[count];
            int i = 0;
            while(rs.next()){
                question_id = rs.getInt("q_id");
                question_userId = rs.getInt("ref_id");
                question_title = rs.getString("title");
                question_detail = rs.getString("detail");
                  if(question_title.contains(str)) {
                      searchId[i] = question_id;
                  } else if(question_detail.contains(str)){
                      searchId[i] = question_id;
                  }
                  i++;
            }

        }catch(Exception ex){
            ex.printStackTrace();
        }
    }

    public void getAllQuestion(int id){

        try{
            String query  = "select * from question";
            rs = st.executeQuery(query);
            count = 0;
            while(rs.next()){
                count++;
                question_id = rs.getInt("q_id");
                if(question_id == id){
                    question_userId = rs.getInt("ref_id");
                    question_title = rs.getString("title");
                    question_detail = rs.getString("detail");
                    question_bfrom = rs.getInt("b_from");
                    question_bto = rs.getInt("b_to");
                    question_date = rs.getString("deadline");
                }
            }

        }catch(Exception ex){
            ex.printStackTrace();
        }
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
        return question_user;
    }

    public void setQuestion_user( int i) {
        try{
            String query  = "select * from AccInfo";
            rs = st.executeQuery(query);
            while(rs.next()){
                if(i == rs.getInt("id")){
                    String f = rs.getString("f_name");
                    String l = rs.getString("l_name");
                    question_user = f+l;
                }
            }

        }catch(Exception ex){
            ex.printStackTrace();
        }
    }

    public boolean checkId(String email, String pass){
        boolean flagMail = false, flagPass = false;
        try{
            String queryEmail = "select * from AccInfo";
            rs = st.executeQuery(queryEmail);
            while(rs.next()){
                String m = rs.getString("email");
                if(m.equalsIgnoreCase(email)){
                    flagMail = true;
                    break;
                }
            }
            String queryPass = "select * from AccInfo";
            rs = st.executeQuery(queryPass);
            while(rs.next() && flagMail == true){
                String p = rs.getString("password");
                if(p.equalsIgnoreCase(pass)){
                    sessionEmail = email;
                    sessionId = rs.getInt("id");
                    sessionFirstName = rs.getString("f_name");
                    sessionLastName = rs.getString("l_name");
                    sessionName = sessionFirstName + sessionLastName;
                    flagPass = true;
                    break;
                }
            }

        }catch(Exception ex){
            ex.printStackTrace();
        }

        return flagPass;
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

    public int getQuestion_bfrom() {
        return question_bfrom;
    }

    public int getQuestion_bto() {
        return question_bto;
    }

    public int getCount() {
        return count;
    }


    public int getQuestion_userId() {
        return question_userId;
    }
}