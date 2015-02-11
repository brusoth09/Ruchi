package com.ruchi.engine.models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.ruchi.engine.database.DatabaseConnector;
import com.ruchi.engine.mapper.Mapper;
import com.ruchi.engine.ranking.RankingAlgorithm;

/**
 * Created by brusoth on 12/12/2014.
 */
public class Review {
	private String id;
	private ArrayList<Sentence> list = new ArrayList<Sentence>();
	private HashMap<String, Double> foodSentiment=new HashMap<String,Double>();
	private boolean isContainFood = false;
	private double rating;

	public double getRating() {
		return rating;
	}

	public void setRating(double rating) {
		this.rating = rating;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void addReview(Sentence object) {
		list.add(object);
	}

	public ArrayList<Sentence> getSentences() {
		return list;
	}

	public void generateFoodSentiment() {
		HashMap<String, ArrayList<Double>> foodScoreMap = null;
		for (Sentence sentence : list) {
			if (sentence.isContainFood()) {
				if (foodScoreMap == null) {
					isContainFood = true;
					foodScoreMap = new HashMap<String, ArrayList<Double>>();
				}
				HashMap<String, Double> sentenceFoodSentiment = sentence
						.getFoodSentiment();
				Set<String> sentenceKeySet = sentenceFoodSentiment.keySet();
				for (String key : sentenceKeySet) {
					Double score = sentenceFoodSentiment.get(key);
					if (foodScoreMap.containsKey(key)) {
						foodScoreMap.get(key).add(score);
					} else {
						ArrayList<Double> scoreList = new ArrayList<Double>();
						scoreList.add(score);
						foodScoreMap.put(key, scoreList);
					}
				}
			}
		}
		if (foodScoreMap != null) {
			foodSentiment = new HashMap<String, Double>();
			Set<String> keySet = foodScoreMap.keySet();
			for (String key : keySet) {
				double score = RankingAlgorithm.avgScoreDouble(foodScoreMap
						.get(key));
				//need to handle dublicates
				if(!foodSentiment.containsKey(key))
					foodSentiment.put(key, score);
			}
		}
	}

	public HashMap<String, Double> getFoodSentiment() {
		return foodSentiment;
	}

	public boolean isContainFood() {
		return isContainFood;
	}
	
	public void updateDatabase(){		
		for (Map.Entry<String, Double> entry : foodSentiment.entrySet())
		{
		   String foodKey=Mapper.insertFood(entry.getKey());
		   Mapper.insertReviewFood(this.id, foodKey, entry.getValue().floatValue());
		}
		Mapper.insertReviewRating(this.id,(float)rating);
	}
	
	public void updateDatabase(DatabaseConnector dc){		
		for (Map.Entry<String, Double> entry : foodSentiment.entrySet())
		{
		   String foodKey=dc.inserNewFood(entry.getKey());
		   dc.insert_review_food(this.id, foodKey, entry.getValue());
		}
		dc.updateReviewRating(this.id,rating);
	}
}
