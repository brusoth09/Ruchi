package com.ruchi.engine.System;

import java.io.IOException;

import com.ruchi.engine.database.DatabaseConnector;
import com.ruchi.engine.foodextraction.OpenNLP;
import com.ruchi.engine.preprocessing.LanguageDetector;

public class TrainingSystem {
	
	private static TrainingSystem instance=null;
	
	private DatabaseConnector db;
    private LanguageDetector ld;
    private OpenNLP sent;
    
    private TrainingSystem(){
    	db=new DatabaseConnector();
    	ld=new LanguageDetector();
    	sent=new OpenNLP();
    	db.connect();
        ld.load_profile();
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
		
	}

}
