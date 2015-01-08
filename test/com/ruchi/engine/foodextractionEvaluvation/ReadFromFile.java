package com.ruchi.engine.foodextractionEvaluvation;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class ReadFromFile {
	
	 public void readFromActualFile(String location) throws IOException {
		 
	        BufferedReader br = new BufferedReader(new FileReader(new File(location)));
	        String line;
	        
	        while ((line = br.readLine()) != null) {
	        	line.replaceAll(",#", "#");
	        	String[] twoValue=line.split("###");
	        	
	        	if(twoValue.length==0){
	        		ActualIdentifier actualIdentifier=new ActualIdentifier();
	        		actualIdentifier.setReviewSentence("");
	        		Constant.actualIdentifierList.add(actualIdentifier);
	        	}
	        	else if(twoValue.length==1)
	        	{
	        		ActualIdentifier actualIdentifier=new ActualIdentifier();
	        		actualIdentifier.setReviewSentence(twoValue[0].trim());
	        		Constant.actualIdentifierList.add(actualIdentifier);
	        	}
	        	else
	        	{
	        		ActualIdentifier actualIdentifier=new ActualIdentifier();
	        		actualIdentifier.setReviewSentence(twoValue[1].trim());
	        		Constant.actualIdentifierList.add(actualIdentifier);
	        		if(twoValue[0]!=null&&!twoValue[0].equalsIgnoreCase(""))
	        		{
	        			String[] foodList=twoValue[0].toLowerCase().trim().split("\\s*,\\s*");
	        			actualIdentifier.setIdentifiedFoods(new ArrayList<String>(Arrays.asList(foodList)));
	        			//System.out.println(foodList[0]);
	        		}
	        		
	        		
	        	}
	            

	            



	        }
	        br.close();
	    }
	 
	 public void readFromModelFile(String location) throws IOException {
		 
	        BufferedReader br = new BufferedReader(new FileReader(new File(location)));
	        String line;
	        while ((line = br.readLine()) != null) {
	        	line.replaceAll(",#", "#");
	        	String[] twoValue=line.split("###");
	        	
	        	if(twoValue.length==0){
	        		ModelIdentifier modelIdentifier=new ModelIdentifier();
	        		modelIdentifier.setReviewSentence("");
	        		Constant.modelIdentifierList.add(modelIdentifier);
	        	}
	        	else if(twoValue.length==1)
	        	{
	        		ModelIdentifier modelIdentifier=new ModelIdentifier();
	        		modelIdentifier.setReviewSentence(twoValue[0].trim());
	        		Constant.modelIdentifierList.add(modelIdentifier);
	        	}
	        	else
	        	{
	        		ModelIdentifier modelIdentifier=new ModelIdentifier();
	        		modelIdentifier.setReviewSentence(twoValue[1].trim());
	        		Constant.modelIdentifierList.add(modelIdentifier);
	        		if(twoValue[0]!=null&&!twoValue[0].equalsIgnoreCase(""))
	        		{
	        			String[] foodList=twoValue[0].toLowerCase().trim().split("\\s*,\\s*");
	        			modelIdentifier.setIdentifiedFoods(new ArrayList<String>(Arrays.asList(foodList)));
	        		//	System.out.println(foodList[0]);
	        		}
	        		
	        		
	        	}
	            

	            



	        }
	        br.close();
	    }
}
