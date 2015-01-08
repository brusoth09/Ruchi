package com.ruchi.engine.database;

import java.sql.*;
import java.util.ArrayList;
import org.junit.Test;

/**
 * Created by parane
 */
public class DatabaseConnectorTest {
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static String DB_URL = "jdbc:mysql://localhost/synergy_training_sample";

    static final String USER = "root";
    static final String PASS = "";

    Connection conn = null;
    Statement stmt = null;
    PreparedStatement pstmt = null;
    
    
    @Test 
    public testDatabaseConnector(){
    	
    	
    	assertEquals("10 x 0 must be 0", 0, tester.multiply(10, 0));
    }
    
    public DatabaseConnector(boolean isTest){
    	
    }

    public void connect()
    {
        
    }

    public void disconect()
    {
        
    	
    }

    public ArrayList<String> getRestaurantReviews(String res_name)
    {
        
    }

    public ArrayList<String> getRestaurants()
    {
     
    }

    public void removeFoodItem(String food)
    {
       
    }

    public void getFoodNames(ArrayList<String> dictionary)
    {

   
    }

    public void insertFoodItem(String food)
    {
        
    }

    public void insertFood(String food)
    {
       
    }

    public void insertWord(String word)
    {
     
    }

    public void getTestData(String word)
    {
        
    }

}
