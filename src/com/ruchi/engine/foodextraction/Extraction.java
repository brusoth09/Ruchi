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

    //unused method only for testing purpose
    public void readReviews()
    {
        for(String rest:rest_list){
            ArrayList<String[]> review_set=Mapper.getRestaurantReviewsAndIdsByRestId(rest.trim());
            for(String[] review:review_set)
            {
                ArrayList<String> sentences=sent.getSentence(review[1]);
                for(String sentence:sentences){
                    String[] tokens=predict(sentence.trim());
                    String[] toks=sent.getWordTokens(sentence);
                    String[] tags=sent.getWordTags(toks);
                    ArrayList<String> features=sent.findFeatures(tags,toks);

                    for(String fea:features)
                    {
                        for(String pre:tokens)
                        {
                            if(fea.contains(pre)){
                                System.out.println(fea);
                                break;
                            }
                        }
                    }
                }
            }
        }
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
        exe.readReviews();
    }
}
