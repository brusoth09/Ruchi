package com.ruchi.engine.database;

import java.sql.*;
import java.util.ArrayList;

/**
 * Created by brusoth on 11/7/2014.
 */
public class DatabaseConnector {
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static String DB_URL = "jdbc:mysql://localhost/synergy_training_sample";

    static final String USER = "root";
    static final String PASS = "";

    Connection conn = null;
    Statement stmt = null;
    PreparedStatement pstmt = null;
    
    public DatabaseConnector(){
    	
    }
    
    public DatabaseConnector(boolean isTest){
    	if(isTest==true){
    		DB_URL = "jdbc:mysql://localhost/synergy_test_data";
    	}
    }

    public void connect()
    {
        try{
            Class.forName("com.mysql.jdbc.Driver");
            System.out.println("Connecting to database...");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
        }
        catch (Exception e) {
            System.out.println(e);
        }
    }

    public void disconect()
    {
        try {
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<String> getRestaurantReviews(String res_name)
    {
        String query="SELECT * FROM "+"reviews"+" NATURAL JOIN "+"restaurants"+" WHERE rest_name =?";

        ArrayList<String> list=new ArrayList<String>();

        try {
            pstmt = conn.prepareStatement(query);
            pstmt.setString(1,res_name);
            ResultSet res = pstmt.executeQuery();
            while(res.next()){
                String review = res.getString("review");
                list.add(review);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    public ArrayList<String> getRestaurants()
    {
        ArrayList<String> list=new ArrayList<String>();
        String query="SELECT rest_name FROM restaurants";

        try {
            pstmt=conn.prepareStatement(query);
            ResultSet res = pstmt.executeQuery();
            while(res.next()){
                String rest = res.getString("rest_name");
                System.out.println(rest);
                list.add(rest);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public void removeFoodItem(String food)
    {
        String query="DELETE FROM foods WHERE food_name = ?";

        try {
            pstmt = conn.prepareStatement(query) ;
            pstmt.setString(1,food);
            int value=pstmt.executeUpdate();
            System.out.println(value);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void getFoodNames(ArrayList<String> dictionary)
    {

        try{
            Statement st = conn.createStatement();
            ResultSet res = st.executeQuery
                    ("SELECT food_name FROM "+"foods" );

            while(res.next()){
                String name = res.getString("food_name");
                dictionary.add(name.toLowerCase().trim());
            }

        }
        catch (SQLException s){
            System.out.println("SQL statement is not executed!"+s);
        }
        catch (Exception e){
            System.out.println(e);
        }
    }

    public void insertFoodItem(String food)
    {
        String query="INSERT INTO foods VALUES (?,?)";

        try {
            pstmt = conn.prepareStatement(query) ;
            pstmt.setString(1,food);
            pstmt.setString(2,"");
            int value=pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void insertFood(String food)
    {
        String query="INSERT INTO food_names VALUES (?,?)";

        try {
            pstmt = conn.prepareStatement(query) ;
            pstmt.setString(1,food);
            pstmt.setString(2,"");
            int value=pstmt.executeUpdate();
            //System.out.println(value);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void insertWord(String word)
    {
        String query="INSERT INTO word_names VALUES (?)";

        try {
            pstmt = conn.prepareStatement(query) ;
            pstmt.setString(1,word);
            int value=pstmt.executeUpdate();
            System.out.println(value);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void getTestData(String word)
    {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            System.out.println("Connecting to database...");
            conn = DriverManager.getConnection("jdbc:mysql://localhost/synergy_test_data", USER, PASS);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

}
