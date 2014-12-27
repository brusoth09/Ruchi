package com.ruchi.engine.foodextraction;

import com.ruchi.engine.preprocessing.Stemming;
import com.ruchi.engine.preprocessing.WordDistance;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by brusoth on 12/17/2014.
 */
public class FoodClassifier {
    private HashMap<String,Integer> map=new HashMap<String,Integer>();
    WordDistance wd;
    public void addFood(String food){
        String stemmed_food= Stemming.pluralToSingular(food).toLowerCase();
        if(map.containsKey(stemmed_food)){
            map.put(stemmed_food,map.get(stemmed_food)+1);
        }
        else{
            map.put(stemmed_food,1);
        }
    }

    public void classify(){

        wd=new WordDistance();
        wd.load();
        ArrayList<String> top=new ArrayList<String>();
        ArrayList<String> down=new ArrayList<String>();

        for(Map.Entry<String,Integer> entry:map.entrySet()){
            System.out.println(entry.getKey()+"---->"+entry.getValue());
            if(entry.getValue()>1){
                top.add(entry.getKey());
            }
            else{
                down.add(entry.getKey());
            }
        }
        System.out.println("counts"+map.get("pancake"));

        for(String d:down){
            //System.out.println(d+"---->"+findBestMatch(top,d));
            System.out.println(d);
        }
    }

    public String findBestMatch(ArrayList<String> top,String input){
        String most_matched="";
        float highest=0;
        for(String s:top){
            float value=wd.getMongeElkanSimilarity(s.toLowerCase(),input.toLowerCase());
            float value1=wd.getCosineSimilarity(s.toLowerCase(),input.toLowerCase());
            if(value>0.5 && value1>0.4 && value>highest){
                highest=value;
                most_matched=s;
            }
        }

        return most_matched;
    }

    public static void main(String[] args){
        FoodClassifier fc=new FoodClassifier();
        fc.addFood("protein pancakes");
        fc.addFood("protein pancakes");
        fc.addFood("protein pancakes");
        fc.addFood("apple pancakes");
        fc.addFood("apple pancakes");
        fc.addFood("apple pancakes");
        fc.addFood("protien pancakes sue");
        fc.addFood("Apple cake");
        fc.addFood("Apple cake");
        fc.addFood("cake");
        fc.classify();

    }
}
