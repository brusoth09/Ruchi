package com.ruchi.engine.sentimentEvaluvation;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/*
 * Auther @Parane
 */
public class ReadFromFileSentiment {
	
/* Read Actual Tagged file */
 public void readFromActualFile(String location) throws IOException {
		 
        BufferedReader br = new BufferedReader(new FileReader(new File(location)));
        String line;
        
        while ((line = br.readLine()) != null) {
        	String key=line.substring(0, 3);
            String val=line.substring(3).trim();
            ActualIdentifierSenti actualIdentifierSenti=new ActualIdentifierSenti();
            actualIdentifierSenti.setSentence(val);
            actualIdentifierSenti.setSentimentTag(key);
            Constant.actualIdentifierListSenti.add(actualIdentifierSenti);
        }
        br.close();
    }
 
 /* Read Actual Tagged file */
 public void readFromModelFile(String location) throws IOException {
	 
        BufferedReader br = new BufferedReader(new FileReader(new File(location)));
        String line;
        while ((line = br.readLine()) != null) {
        	String key=line.substring(0, 3);
            String val=line.substring(3).trim();
            ModelIdentifierSenti modelIdentifierSenti=new ModelIdentifierSenti();
            modelIdentifierSenti.setSentence(val);
            modelIdentifierSenti.setSentimentTag(key);
            Constant.modelIdentifierListSenti.add(modelIdentifierSenti);	
        

        }
        br.close();
    }
}
