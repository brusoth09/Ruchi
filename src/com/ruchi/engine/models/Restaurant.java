package com.ruchi.engine.models;

import com.ruchi.engine.preprocessing.Stemming;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by brusoth on 12/12/2014.
 */
public class Restaurant {
    String name;
    ArrayList<Review> list=new ArrayList<Review>();
    private HashMap<String,Integer> map=new HashMap<String,Integer>();

    public void addReview(Review object){
        list.add(object);
    }

    public void addFood(String food){
        String stemmed_food= Stemming.removeStopWordsAndStem(food).toLowerCase();
        if(map.containsKey(stemmed_food)){
            map.put(stemmed_food,map.get(stemmed_food)+1);
        }
        else{
            map.put(stemmed_food,1);
        }
        //System.out.println(map.get("pancake"));
    }
}
