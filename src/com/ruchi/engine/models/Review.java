package com.ruchi.engine.models;

import java.util.ArrayList;

/**
 * Created by brusoth on 12/12/2014.
 */
public class Review {
    private String id;
    private ArrayList<Sentence> list=new ArrayList<Sentence>();

    public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void addReview(Sentence object){
        list.add(object);
    }
    
    public ArrayList<Sentence> getSentences(){
    	return list;
    }
}
