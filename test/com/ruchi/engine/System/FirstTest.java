package com.ruchi.engine.System;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.ruchi.engine.database.DatabaseConnector;
import com.ruchi.engine.foodextraction.Extraction;
import com.ruchi.engine.foodextraction.FoodClassifier;
import com.ruchi.engine.foodextraction.OpenNLP;
import com.ruchi.engine.models.Restaurant;
import com.ruchi.engine.models.Review;
import com.ruchi.engine.models.Sentence;
import com.ruchi.engine.preprocessing.LanguageDetector;


public class FirstTest {
	public static void main(String args[]){
      new FirstTest().getReviewS();
	}


	private OpenNLP sent;
	private Extraction exe;
	private ArrayList<Sentence> list;
	private FoodClassifier wc;
	
	public void getReviewS(){
		sent=new OpenNLP();
        sent.loadModel();
        exe=new Extraction();
        exe.load(sent);
        list=new ArrayList<Sentence>();
        wc=new FoodClassifier();
		DatabaseConnector db=new DatabaseConnector(true);
        LanguageDetector ld=new LanguageDetector();
        
        ld.load_profile();
        db.connect();
        
        ArrayList<String> res_list=db.getRestaurants();
        OpenNLP sent=new OpenNLP();
        sent.loadModel();
        
        for(String s:res_list)
        {
        	Restaurant rest=new Restaurant(s);
            ArrayList<String> reviews=db.getRestaurantReviews(s);
            for(String s1:reviews)
            {
                if(ld.check_Language(s1))
                {
                	Review review=new Review();
                    ArrayList<String> sentences=sent.getSentence(s1);
                    for(String s2:sentences)
                    {
                        String sen=LanguageDetector.remove_symbols(s2);
                        Sentence sentence=new Sentence(sen);
                        try {
                            if(sen.length()>1)
                            {
                            	predictfoods(sentence);
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
        System.out.print(sentence);
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
	
    public void dependencyGeneration(Restaurant rest){
    	for(Review r:rest.getReview()){
    		for(Sentence s:r.getSentences()){
    			Iterator it = s.getFoodMap().entrySet().iterator();
    			while (it.hasNext()) {
    		        Map.Entry pairs = (Map.Entry)it.next();
    		        String food=(String) pairs.getKey();
    		        int len=food.split("\\s+").length;
    		        String modified=wc.getResult(food);
    		        int location=s.findLocation(food);
    		        if(modified.equalsIgnoreCase("notfound")){
    		        	s.removeFood(food);
    		        }
    		        else if(food.equals(modified)){
    		        	Integer[] set=new Integer[2];
    		        	set[0]=location;
    		        	set[1]=len;
    		        	s.addFood(modified,set);
    		        }
    		        else{
    		        	s.removeFood(food);
    		        	Integer[] set=new Integer[2];
    		        	set[0]=location;
    		        	set[1]=len;
    		        	s.addFood(modified, set);
    		        }
    		    }
    			//call type dependency for Sentence Object here
    		}
    		//call type dependency for Review object here
    	}
    	
    }

}
