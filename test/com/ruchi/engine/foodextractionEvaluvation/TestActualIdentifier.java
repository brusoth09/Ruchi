package com.ruchi.engine.foodextractionEvaluvation;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import com.ruchi.engine.foodextractionEvaluvation.ActualIdentifier;

public class TestActualIdentifier {
	private String reviewSentence;
	
	@Test 
    public String TestgetReviewSentence() {
		ActualIdentifier actualIdentifier =new ActualIdentifier();
		actualIdentifier.setReviewSentence("hello");
		assertEquals("hello",actualIdentifier.getReviewSentence());
	
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
