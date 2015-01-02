package com.ruchi.engine.models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import com.ruchi.engine.preprocessing.Stemmer;
import com.ruchi.engine.ranking.RankingAlgorithm;

/**
 * Created by brusoth on 12/12/2014.
 */
public class Restaurant {
	private String id;
	private String name;

	private ArrayList<Review> review_list = new ArrayList<Review>();
	private HashMap<String, Integer> food_map = new HashMap<String, Integer>();
	private HashMap<String, Double> foodRating;
	private double rating;

	public Restaurant(String name) {
		this.setName(name);
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
		String stemmed_food = Stemmer.pluralToSingular(food).toLowerCase();

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
			foodRating = new HashMap<String, Double>();
			Set<String> keySet = foodScoreMap.keySet();
			for (String key : keySet) {
				double score = RankingAlgorithm.avgScoreDouble(foodScoreMap
						.get(key));
				foodRating.put(key, score);
			}
		}
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

}
