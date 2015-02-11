package com.ruchi.engine.foodextraction;

import java.util.ArrayList;

import opennlp.tools.util.Span;

import com.ruchi.engine.database.DatabaseConnector;
import com.ruchi.engine.mapper.Mapper;


/**
 * Created by Brusoth on 12/2/2014.
 */
public class Extraction {
    
    private OpenNLP sent;

    ArrayList<String> food_list=new ArrayList<String>();
    ArrayList<String> rest_list=new ArrayList<String>();

    public void load(OpenNLP sent){
        Mapper.getFoodInitNames(food_list);
        rest_list=Mapper.getRestaurantIDs();
        this.sent=sent;
    }
    
    public String[] predict(String line){
        String[] tokens=sent.getTokens(line);
        Span nameSpans[] = sent.getNames(tokens);
        String[] array=Span.spansToStrings(nameSpans,tokens);
        return array;
    }

    public static void main(String args[]){
        Extraction exe=new Extraction();
        OpenNLP nlp=new OpenNLP();
        nlp.loadModel();
        exe.load(nlp);
        String[] output=exe.predict("i had a pizza");
        System.out.println(output[0]);
    }
}
