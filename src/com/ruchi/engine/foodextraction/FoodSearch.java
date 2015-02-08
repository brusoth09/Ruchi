package com.ruchi.engine.foodextraction;

import java.util.ArrayList;

import com.ruchi.engine.database.DBConnection;
import com.ruchi.engine.database.DatabaseConnector;
import com.ruchi.engine.mapper.Mapper;
import com.ruchi.engine.preprocessing.TextUtilizer;

/**
 * Created by brusoth on 11/18/2014.
 */
public class FoodSearch {
    private ArrayList<String> dictionary = new ArrayList<String>();
    private DatabaseConnector db;

    public void loadFood()
    {
        db=new DatabaseConnector();
        db.connect();
        dictionary=db.getInitFoodNames();
        db.disconect();
    }

    public String search(String chunk)
    {
        String output="";
        String[] tokens=chunk.toLowerCase().split("\\s+");
        if(tokens.length==1)
        {
            if(dictionary.contains(TextUtilizer.pluralToSingular(tokens[0].trim())))
            {
                return " <START:food> "+tokens[0]+" <END> ";
            }
            return tokens[0];
        }
        output=chunk.trim().toLowerCase();
        for(int i=tokens.length;i>0;i--)
        {
            for(int j=1;j<tokens.length-i+2;j++)
            {
                String s="";
                for(int k=j-1;k<j+i-1;k++)
                {
                    s=s.concat(" "+tokens[k].trim());
                }
                if(dictionary.contains(TextUtilizer.pluralToSingular(s.trim())))
                {
                    chunk=chunk.replace(s.trim(),"").replaceAll("\\s+"," ");
                    output=output.replace(s.trim(), " <START:food> "+s+" <END> ");
                    return output;
                }
            }

        }
        return output;
    }
    
    public static void main(String args[])
    {
        FoodSearch fs=new FoodSearch();
        fs.loadFood();
        System.out.println(fs.search("cinnamon  rolls  as big as your head absolutely scrumptious ".replace("  "," ")));
        System.out.println("the   day".replaceAll("\\s+", " "));
    }
}
