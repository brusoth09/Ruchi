package com.ruchi.engine.foodextraction;

import com.ruchi.engine.database.DatabaseConnector;
import com.ruchi.engine.preprocessing.Stemming;
import com.ruchi.engine.utils.TextEditors;
import com.ruchi.engine.utils.WordAdder;
import opennlp.tools.stemmer.Stemmer;

import javax.xml.soap.Text;
import java.util.ArrayList;

/**
 * Created by brusoth on 11/18/2014.
 */
public class FoodSearch {
    ArrayList<String> dictionary = new ArrayList<String>();
    DatabaseConnector db;
    WordAdder wa;

    public static void main(String args[])
    {
        FoodSearch fs=new FoodSearch();
        fs.loadFood();
        fs.search("cinnamon  rolls  as big as your head absolutely scrumptious ".replace("  "," "));
    }
    public void loadFood()
    {
        db=new DatabaseConnector();
        db.connect();
        db.getFoodNames(dictionary);
        //TextEditors.writeFoodNamesToTextFile(dictionary);
       // db.disconect();
    }

    public String search(String chunk)
    {
        String output="";
        wa=new WordAdder();
        String[] tokens=chunk.toLowerCase().replace("  "," ").trim().split(" ");
        if(tokens.length==1)
        {
            if(dictionary.contains(Stemming.pluralToSingular(tokens[0].trim())))
            {
                //wa.divide(tokens[0],db);
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
                if(dictionary.contains(Stemming.pluralToSingular(s.trim())))
                {
                    chunk=chunk.replace(s.trim(),"").replace("  "," ");
                    //wa.divide(s,db);
                    output=output.replace(s.trim(), " <START:food> "+s+" <END> ");
                    return output;
                }
                //System.out.println(s);
            }

        }
        //System.out.println(output);
        return output;
    }
}
