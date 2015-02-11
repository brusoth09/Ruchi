package com.ruchi.engine.foodextractionEvaluvation;

import java.util.ArrayList;
import java.util.List;

public class ModelIdentifier {
	private String reviewSentence;
	private List<String> identifiedFoods=new ArrayList<String>();
	
    public String getReviewSentence() {
		return reviewSentence;
	}
	public void setReviewSentence(String reviewSentence) {
		this.reviewSentence = reviewSentence;
	}
	public List<String> getIdentifiedFoods() {
		return identifiedFoods;
	}
	public void setIdentifiedFoods(ArrayList<String> arrayList) {
		this.identifiedFoods = arrayList;
	}


}
