package com.ruchi.engine.models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.ruchi.engine.database.DatabaseConnector;
import com.ruchi.engine.mapper.Mapper;
import com.ruchi.engine.preprocessing.TextUtilizer;
import com.ruchi.engine.ranking.RankingAlgorithm;

/**
 * Created by brusoth on 12/12/2014.
 */
public class Restaurant {
	private String id;
	private String name;

	private ArrayList<Review> review_list = new ArrayList<Review>();
	private HashMap<String, Integer> food_map = new HashMap<String, Integer>();
	private HashMap<String, Double> foodRating=new HashMap<String, Double>();
	private double rating=0.0;

	public Restaurant(String name) {
		this.setId(name);
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void addReview(Review object) {
		review_list.add(object);
	}

	public ArrayList<Review> getReview() {
		return review_list;
	}

	public void addFood(String food) {
		String stemmed_food = TextUtilizer.pluralToSingular(food).toLowerCase();

		if (food_map.containsKey(stemmed_food)) {
			food_map.put(stemmed_food, food_map.get(stemmed_food) + 1);
		} else {
			food_map.put(stemmed_food, 1);
		}
	}

	public void generateFoodRating() {
		HashMap<String, ArrayList<Double>> foodScoreMap = null;
		for (Review review : review_list) {
			if (review.isContainFood()) {
				if (foodScoreMap == null) {
					foodScoreMap = new HashMap<String, ArrayList<Double>>();
				}
				HashMap<String, Double> sentenceFoodSentiment = review
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
			
			Set<String> keySet = foodScoreMap.keySet();
			for (String key : keySet) {
				double score = RankingAlgorithm.avgScoreDouble(foodScoreMap
						.get(key));
				foodRating.put(key, score);
			}
		}
	}

	public HashMap<String, Double> getFoodRating() {
		return foodRating;
	}

	public void setFoodRating(HashMap<String, Double> foodRating) {
		this.foodRating = foodRating;
	}

	public void restaurantRating() {
		ArrayList<Double> scoreList = new ArrayList<Double>();
		for (Review review : review_list) {
			scoreList.add(review.getRating());
		}
		rating = RankingAlgorithm.avgScoreDouble(scoreList);
	}

	public double getRating() {
		return rating;
	}

	public void setRating(double rating) {
		this.rating = rating;
	}
	
	public void updateDatabase(){
		for (Map.Entry<String, Double> entry : foodRating.entrySet())
		{
		   String foodKey=Mapper.insertFood(entry.getKey());
		   Mapper.insertRestFood(this.id,foodKey, entry.getValue().floatValue());
		}
		Mapper.insertRestRating(this.id, (float)rating);
	}
	
	public void updateDatabase(DatabaseConnector dc){
		for (Map.Entry<String, Double> entry : foodRating.entrySet())
		{
		   String foodKey=dc.inserNewFood(entry.getKey());
		   dc.insert_rest_food(this.id,foodKey, entry.getValue());
		}
		dc.updateRestarantRating(this.id,rating);
	}

}
