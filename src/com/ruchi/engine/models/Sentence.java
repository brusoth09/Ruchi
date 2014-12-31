package com.ruchi.engine.models;

import com.ruchi.engine.preprocessing.Stemming;

import java.util.ArrayList;

/**
 * Created by brusoth on 12/12/2014.
 */
public class Sentence {
    String id;
    String line;
    ArrayList<String> food=new ArrayList<String>();

    public Sentence(String line){
        this.line=line;
    }

    public boolean isContainFood(){
        return !(food.isEmpty());
    }

    public void addFood(String item){
        if(!food.contains(Stemming.pluralToSingular(item))){
            food.add(item);
        }


    }
}
