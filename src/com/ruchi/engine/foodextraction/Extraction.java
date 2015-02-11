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
    ArrayList<String[]> rest_list=new ArrayList<String[]>();

    public void load(OpenNLP sent){
        this.sent=sent;
    }
    
    public void load(OpenNLP sent,DatabaseConnector dc){
        this.sent=sent;
    }
    
    public String[] predict(String line){
        String[] tokens=sent.getTokens(line);					//white spaced tokens
        Span nameSpans[] = sent.getNames(tokens);				//get predicted names from ner model
        String[] array=Span.spansToStrings(nameSpans,tokens);	//change spans into string
        return array;
    }

    public static void main(String args[]){
        Extraction exe=new Extraction();
        OpenNLP nlp=new OpenNLP();
        nlp.loadModel();
        exe.load(nlp);
        String[] output=exe.predict("We tried delicious spartan special orange pizza in two consecutive nights");
        System.out.println(output[0]);
    }
}
