package com.ruchi.engine.System;

import java.io.IOException;
import java.util.ArrayList;

import com.ruchi.engine.database.DatabaseConnector;
import com.ruchi.engine.foodextraction.OpenNLP;
import com.ruchi.engine.foodextraction.Train;
import com.ruchi.engine.mapper.Mapper;
import com.ruchi.engine.preprocessing.GoogleLanguageDetectionTool;
import com.ruchi.engine.preprocessing.LanguageDectectionTool;
import com.ruchi.engine.preprocessing.TextUtilizer;

public class TrainingSystem {
	
	private static TrainingSystem instance=null;
	
    private LanguageDectectionTool ld;
    private OpenNLP sent;
    private DatabaseConnector dc;
    
    private TrainingSystem(){
    	ld=new GoogleLanguageDetectionTool();
    	sent=new OpenNLP();
        ld.loadModule();
        sent.loadModel();
        dc=new DatabaseConnector();
        dc.connect();
    }
    
    public static TrainingSystem getInstance(){
    	if(instance==null){
    		instance=new TrainingSystem();
    	}
    	return instance;
    }
	
	public void addToTrainingSet(String line){
		if(line!=null && line.length()>0){
			try {
				sent.tagSentence(line);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void trainFromDatabase(){
		ArrayList<String[]> res_list=dc.getRestIDAndName();
		
		for(String[] s:res_list)
        {
            ArrayList<String[]> reviews=dc.getRestaurantReviewsFromIDTrain(s[0]);
            for(String[] s1:reviews)
            {
                if(ld.findLanguage(s1[1]))
                {

                    ArrayList<String> sentences=sent.getSentence(s1[1]);
                    for(String s2:sentences)
                    {
                        String sen=TextUtilizer.utilizeText(s2);
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
        
	}
	
	public void trainModel(){
		try {
			new Train().train();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
