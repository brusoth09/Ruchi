package com.ruchi.engine.System;

import com.ruchi.engine.database.DatabaseConnector;
import com.ruchi.engine.foodextraction.OpenNLP;
import com.ruchi.engine.preprocessing.LanguageDetector;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by brusoth on 11/7/2014.
 */
public class main {
    static String res="Pine Cone Restaurant";
    public static void main(String[] args)
    {
        DatabaseConnector db=new DatabaseConnector();
        LanguageDetector ld=new LanguageDetector();

        db.connect();
        ld.load_profile();


//        TextEditors te=new TextEditors();
 //         new FoodRemover().add();
//        db.connect();
//        ArrayList<String> list=db.getRestaurantReviews(res);
//        te.writeTextFile(list);
//        db.getRestaurants();
        //new FoodRemover().remove();
        /*ArrayList<String> list=new ArrayList<String>();
        list.add("Pretty good dinner with a nice selection of food. Open 24 hours and provide nice service. I usually go here after a night of partying. My favorite dish is the Fried Chicken Eggs Benedict.\n" +
                "Good truck stop dining at the right price. We love coming here on the weekends when we don't feel like cooking.");*/
        //new LanguageDetector().process(list);

        ArrayList<String> res_list=db.getRestaurants();
        OpenNLP sent=new OpenNLP();
        sent.loadModel();
        for(String s:res_list)
        {
            ArrayList<String> reviews=db.getRestaurantReviews(s);
            for(String s1:reviews)
            {
                if(ld.check_Language(s1))
                {

                    ArrayList<String> sentences=sent.getSentence(s1);
                    for(String s2:sentences)
                    {
                        String sen=LanguageDetector.remove_symbols(s2);
                        try {
                            if(sen.length()>1)
                            sent.tagSentence(sen);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
        db.disconect();
      }
}
