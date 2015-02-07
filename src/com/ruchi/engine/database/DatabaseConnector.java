package com.ruchi.engine.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.ruchi.engine.utils.KeyGenerator;

/**
 * Created by brusoth on 11/7/2014.
 */
public class DatabaseConnector {
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static String DB_URL = "jdbc:mysql://localhost/ruchidb";

    static final String USER = "root";
    static final String PASS = "";

    Connection conn = null;
    Statement stmt = null;
    PreparedStatement pstmt = null;
    
    public DatabaseConnector(){
    	
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
    
    public ArrayList<String[]> getRestIDAndName(){
    	ArrayList<String[]> list=new ArrayList<String[]>();
        String query="SELECT rest_id,rest_name FROM restaurants";
        try {
            pstmt=conn.prepareStatement(query);
            ResultSet res = pstmt.executeQuery();
            while(res.next()){
            	String[] array=new String[2];
                String rest = res.getString("rest_id");
                String name=res.getString("rest_name");
                array[0]=rest;
                array[1]=name;
                list.add(array);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
    
    public ArrayList<String[]> getRestaurantReviewsFromID(String id)
    {
        String query="SELECT * FROM "+"reviews"+" NATURAL JOIN "+"restaurants"+" WHERE rest_id =?";

        ArrayList<String[]> list=new ArrayList<String[]>();

        try {
            pstmt = conn.prepareStatement(query);
            pstmt.setString(1,id);
            ResultSet res = pstmt.executeQuery();
            while(res.next()){
            	String review_id=res.getString("review_id");
                String review = res.getString("review");
                list.add(new String[]{review_id,review});
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }
    
    public ArrayList<String[]> getRestaurantReviewsFromIDTrain(String id)
    {
        String query="SELECT * FROM "+"reviews_train"+" NATURAL JOIN "+"restaurants"+" WHERE rest_id =?";

        ArrayList<String[]> list=new ArrayList<String[]>();

        try {
            pstmt = conn.prepareStatement(query);
            pstmt.setString(1,id);
            ResultSet res = pstmt.executeQuery();
            while(res.next()){
            	String review_id=res.getString("id");
                String review = res.getString("review");
                list.add(new String[]{review_id,review});
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

    public void insertFood(String id,String food)
    {
        String query="INSERT INTO foods VALUES (?,?,?)";

        try {
            pstmt = conn.prepareStatement(query) ;
            pstmt.setString(1,id);
            pstmt.setString(2,food);
            pstmt.setString(3,null);
            int value=pstmt.executeUpdate();
            //System.out.println(value);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public String inserNewFood(String food){
    	String select="select food_id FROM foods where food_name=?";
    	try {
			PreparedStatement statement = conn.prepareStatement(select);
			statement.setString(1,food);
			ResultSet rs=statement.executeQuery();
			while(rs.next()){
                return rs.getString("food_id");
            }
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	String id=KeyGenerator.uniqueCurrentTimeMS();
    	insertFood(id, food);
    	return id;
    }
   
    public void insert_rest_food(String rest_id,String food_id,double rating){
    	String query="INSERT INTO rest_food VALUES (?,?,?)";

        try {
            pstmt = conn.prepareStatement(query) ;
            pstmt.setString(1,rest_id);
            pstmt.setString(2,food_id);
            pstmt.setDouble(3,rating);
            int value=pstmt.executeUpdate();
            //System.out.println(value);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public void insert_review_food(String review_id,String food_id,double rating){
    	String query="INSERT INTO review_food VALUES (?,?,?)";

        try {
            pstmt = conn.prepareStatement(query) ;
            pstmt.setString(1,review_id);
            pstmt.setString(2,food_id);
            pstmt.setDouble(3,rating);
            int value=pstmt.executeUpdate();
            //System.out.println(value);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public void updateRestarantRating(String id,double rating){
    	String update="UPDATE restaurants SET rest_rating = ?"+
    						" WHERE rest_id = ?";
    	try {
			pstmt= conn.prepareStatement(update);
			pstmt.setDouble(1, rating);
			pstmt.setString(2, id);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
	public void updateReviewRating(String id,double rating){
			String update="UPDATE reviews SET rating = ?"+
							" WHERE review_id = ?";
			try {
					pstmt= conn.prepareStatement(update);
					pstmt.setDouble(1, rating);
					pstmt.setString(2, id);
					pstmt.executeUpdate();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	}
    public static void main(String args[]){
    	DatabaseConnector db=new DatabaseConnector();
    	db.connect();
    	System.out.println(db.inserNewFood("pizzaaa"));
    	//db.insert_rest_food("1408181771009", "1423171966949", 4.0);
    	//db.insert_review_food("1408201920908", "1423171966949", 4.0);
    	db.updateReviewRating("1408201920908", 4.0);
    }

}
