package com.ruchi.engine.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;

import com.ruchi.engine.database.DatabaseConnector;
import com.ruchi.engine.preprocessing.TextUtilizer;

/**
 * Created by brusoth on 11/17/2014.
 */
public class FoodRemover {
    public static void main(String args[])
    {
        new FoodRemover().remove();
    }
    public void remove()
    {
        FileInputStream fstream = null;
        try {
            File f = new File("res/food_remove");
            fstream = new FileInputStream(f);
            BufferedReader br = new BufferedReader(new InputStreamReader(fstream));

            String strLine;
            DatabaseConnector db=new DatabaseConnector();
            //Read File Line By Line
            db.connect();
            while ((strLine = br.readLine()) != null)   {
                db.removeFoodItem(TextUtilizer.pluralToSingular(strLine.trim()));
            }
            db.disconect();

            //Close the input stream
            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void add()
    {
        FileInputStream fstream = null;

        try {
            File f = new File("res/food_add");
            fstream = new FileInputStream(f);
            BufferedReader br = new BufferedReader(new InputStreamReader(fstream));

            String strLine;
            DatabaseConnector db=new DatabaseConnector();
            //Read File Line By Line
            db.connect();
            while ((strLine = br.readLine()) != null)   {
                System.out.println(strLine);
                //db.insertFoodItem(strLine.trim());
            }
            db.disconect();

            //Close the input stream
            br.close();
        } catch (Exception e) {
            System.out.println(e);
        }

    }

    public void add_food(){
        BufferedReader br=null;
        DatabaseConnector db=new DatabaseConnector();
        db.connect();
        String currentLine;

        try {
            br = new BufferedReader(new FileReader("res/final_food_list.txt"));

            while ((currentLine = br.readLine()) != null) {
                if(currentLine.length()!=0)
                db.insertFood(currentLine);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                if (br != null)br.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
}
