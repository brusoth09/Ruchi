package com.ruchi.engine.System;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import com.ruchi.engine.database.DatabaseConnector;
import com.ruchi.engine.foodextraction.Extraction;
import com.ruchi.engine.foodextraction.FoodClassifier;
import com.ruchi.engine.foodextraction.OpenNLP;
import com.ruchi.engine.models.Restaurant;
import com.ruchi.engine.models.Review;
import com.ruchi.engine.models.Sentence;
import com.ruchi.engine.preprocessing.GoogleLanguageDetectionTool;
import com.ruchi.engine.preprocessing.LanguageDectectionTool;
import com.ruchi.engine.preprocessing.Stemming;
import com.ruchi.engine.preprocessing.TextUtilizer;
import com.ruchi.engine.sentiment.TypedDependencyEngine;
import com.ruchi.engine.utils.TextEditors;

public class TestingSystem {
	
	private static TestingSystem instance=null;
	
	private OpenNLP sent;
	private Extraction exe;
	private ArrayList<Sentence> list;
	private FoodClassifier wc;
	
	DatabaseConnector db;
	LanguageDectectionTool ld;
	
	private TestingSystem(){
		sent=new OpenNLP();
        sent.loadModel();
        exe=new Extraction();
        exe.load(sent);
        list=new ArrayList<Sentence>();
        wc=new FoodClassifier();
        db=new DatabaseConnector(true);
        ld=new GoogleLanguageDetectionTool();
        new TypedDependencyEngine();
        
        ld.loadModule();
        db.connect();
	}
	
	public static TestingSystem getInstance(){
		if(instance==null){
			instance=new TestingSystem();
		}
		
		return instance;
	}
	
	public void getRestID(){
		
	}
	
	public void readReviews(){
		ArrayList<String[]> res_list=db.getRestID();
		
		for(String[] s:res_list)
        {
        	Restaurant rest=new Restaurant(s[0]);
        	rest.setName(s[1]);
            ArrayList<String> reviews=db.getRestaurantReviewsFromID(s[0]);
            for(String s1:reviews)
            {
                if(ld.findLanguage(s1))
                {
                	Review review=new Review();
                    ArrayList<String> sentences=sent.getSentence(s1);
                    for(String s2:sentences)
                    {
                        String sen=TextUtilizer.utilizeText(s2);
                        Sentence sentence=new Sentence(sen);
                        try {
                            if(sen.length()>1)
                            {
                            	predictfoods(sentence,rest.getName());
                            }
                            else if(sen.length()==0){
                            	
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        review.addReview(sentence);
                    }
                    
                    rest.addReview(review);
                    
                }
            }
            wc.classify();
            dependencyGeneration(rest);
            
        }
		db.disconect();
	}
	
	public void predictfoods(Sentence sentence){
        sentence.setSentence((sentence.getSentence()).replaceAll("\\.-"," ").replace("\\.",""));
        String[] tokens=exe.predict(sentence.getSentence().trim());
        List<String> predictions= new ArrayList<String>(Arrays.asList(tokens));
        String[] toks=sent.getWordTokens(sentence.getSentence());
        String[] toks1=sent.getWordTokens(sentence.getSentence().replace(","," "));
        sentence.setTokens(toks1);
        String[] tags=sent.getWordTags(toks1);
        ArrayList<String> features=sent.findFeatures(tags,toks);

        Iterator<String> iter;
        //System.out.print(sentence);
        iter=predictions.iterator();
        for(String fea:features)
        {

            while(iter.hasNext())
            {
                if(fea.contains(iter.next())){
                    
                    sentence.addFood(fea,new Integer[]{0,0});
                    wc.addFood(fea.toLowerCase());
                    iter.remove();
                    break;
                }

            }

        }
        iter=predictions.iterator();
        while(iter.hasNext())
        {
            String next=iter.next();
            sentence.addFood(next,new Integer[]{0,0});
            wc.addFood(next);
        }
        
        
    }
	
	public void predictfoods(Sentence sentence,String rest_name){
        sentence.setSentence((sentence.getSentence()).replaceAll("\\.-"," ").replace("\\.",""));
        String[] tokens=exe.predict(sentence.getSentence().trim());
        List<String> predictions= new ArrayList<String>(Arrays.asList(tokens));
        String[] toks=sent.getWordTokens(sentence.getSentence());
        String[] toks1=sent.getWordTokens(sentence.getSentence().replace(","," "));
        sentence.setTokens(toks1);
        String[] tags=sent.getWordTags(toks1);
        ArrayList<String> features=sent.findFeatures(tags,toks);

        Iterator<String> iter;
        //System.out.print(sentence);
        iter=predictions.iterator();
        for(String fea:features)
        {

            while(iter.hasNext())
            {
                if(fea.contains(iter.next())){
                    
                    sentence.addFood(fea,new Integer[]{0,0});
                    wc.addFood(fea.toLowerCase());
                    iter.remove();
                    break;
                }

            }

        }
        iter=predictions.iterator();
        while(iter.hasNext())
        {
            String next=iter.next();
            sentence.addFood(next,new Integer[]{0,0});
            wc.addFood(next);
        }
        if(sentence.getFoodMap().containsKey(TextUtilizer.pluralToSingular((rest_name))))
        	sentence.removeFood(Stemming.pluralToSingular(rest_name));
    }
	
	public void dependencyGeneration(Restaurant rest){
    	for(Review r:rest.getReview()){
    		for(Sentence s:r.getSentences()){
    			HashMap<String,Integer[]> temp=new HashMap<String, Integer[]>();
    			Iterator<Entry<String, Integer[]>> it = s.getFoodMap().entrySet().iterator();
    			while (it.hasNext()) {
    		        Entry pairs = (Entry)it.next();
    		        String food=(String) pairs.getKey();
    		        int len=food.split("\\s+").length;
    		        String modified=wc.getResult(food);
    		        int location=s.findLocation(food);
    		        if(modified.equalsIgnoreCase("notfound")){
    		        	it.remove();
    		        }
    		        else if(food.equals(modified)){
    		        	Integer[] set=new Integer[2];
    		        	set[0]=location;
    		        	set[1]=len;
    		        	pairs.setValue(set);
    		        }
    		        else{
    		        	it.remove();
    		        	Integer[] set=new Integer[2];
    		        	set[0]=location;
    		        	set[1]=len;
    		        	temp.put(modified, set);
    		        }
    		    }
    			s.addFood(temp);
    			if(s.isContainFood()){
    				TypedDependencyEngine.foodSentiment(s);
    				TextEditors.writeTestSentence(s,rest.getName());
    			}
    		}
    		r.generateFoodSentiment();
    	}
    	rest.generateFoodRating();
    }

}
