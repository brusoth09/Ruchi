package com.ruchi.engine.models;

import com.ruchi.engine.preprocessing.Stemming;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by brusoth on 12/12/2014.
 */
public class Sentence {
    private String id;
    private String line;
    private String[] tokens;
    private HashMap<String,Integer> food=new HashMap<String,Integer>();

    public Sentence(String line){
        this.line=line;
    }
    
    public void setSentence(String line){
    	this.line=line;
    }
    
    public String getSentence(){
    	return line;
    }

    public boolean isContainFood(){
        return !(food.isEmpty());
    }

    public void addFood(String item,int location){
        food.put(item,location);
    }
    
    public void removeFood(String item){
    	food.remove(item);
    }
    
    public HashMap<String,Integer> getFoodMap(){
    	return food;
    }

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String[] getTokens() {
		return tokens;
	}

	public void setTokens(String[] tokens) {
		this.tokens = tokens;
	}
	
	public int findLocation(String word1,String word2){
		String[] wordtokens = null;
		if(word1.length()>0){
			wordtokens=word1.split("//s+");
		}
		else{
			return -1;
		}
		for(int i=0;i<tokens.length;i++){
			if(wordtokens[0].equalsIgnoreCase(tokens[i]) ||wordtokens[0].equalsIgnoreCase(Stemming.pluralToSingular(tokens[i]))){
				return i;
			}
		}
		
		if(word2.length()>0){
			wordtokens=word2.split("//s+");
		}
		else{
			return -1;
		}
		for(int i=0;i<tokens.length;i++){
			if(wordtokens[0].equalsIgnoreCase(tokens[i]) ||wordtokens[0].equalsIgnoreCase(Stemming.pluralToSingular(tokens[i]))){
				return i;
			}
		}
		return 0;
	}
}
