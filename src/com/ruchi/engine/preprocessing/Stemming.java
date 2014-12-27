package com.ruchi.engine.preprocessing;

import java.io.IOException;

import com.ruchi.engine.foodextraction.FoodSearch;
import org.tartarus.snowball.ext.PorterStemmer;


public class Stemming {
    public static void main(String[] args) {
        String[] examples={"custards","choose","pancakes","cookies","ice creams","change"};
        for(String word:examples){
            System.out.println(pluralToSingular(word));
        }
    }

    public static  String removeStopWordsAndStem(String input) {
        PorterStemmer stemmer = new PorterStemmer();
        stemmer.setCurrent(input);
        stemmer.stem();
        return stemmer.getCurrent();
    }

    public static String pluralToSingular(String word){
        if(word.endsWith("es")){
            return word.substring(0,word.length()-1);
        }
        else if(word.endsWith("s")){
            return word.substring(0,word.length()-1);
        }
        return word;
    }


}