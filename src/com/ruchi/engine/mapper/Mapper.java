package com.ruchi.engine.mapper;

import java.util.ArrayList;
import java.util.List;

import com.ruchi.engine.database.DataStore;
import com.ruchi.hibernate.model.DAO.FoodDao;
import com.ruchi.hibernate.model.DAO.RestaurantDao;
import com.ruchi.hibernate.model.DAO.ReviewDao;

public class Mapper {
	DataStore dataStore = new DataStore();

	private Mapper() {

	}

	public ArrayList<String> getRestaurantReviews(String res_name) {
		ArrayList<String> rest_reviews = new ArrayList<String>();
		List<ReviewDao> daos = dataStore.getReviewsByRestName(res_name);
		for (ReviewDao r : daos) {
			rest_reviews.add(r.getReview());
		}

		return rest_reviews;
	}
	
	public ArrayList<String> getRestaurantNames(){
		ArrayList<String> rest_names = new ArrayList<String>();
		List<RestaurantDao> daos = dataStore.getRestaurantNames();
		for(RestaurantDao r:daos){
			rest_names.add(r.getRest_name());
		}
		return rest_names;
	}
	
	public ArrayList<String> getRestaurantIDs () {
		ArrayList<String> rest_ids = new ArrayList<String>();
		List<RestaurantDao> daos = dataStore.getRestaurantIds();
		for(RestaurantDao r:daos){
			rest_ids.add(r.getRest_id());
		}
		return rest_ids;		
	}
	
	public ArrayList<String> getRestaurantReview(String rest_id) {
		ArrayList<String> rest_reviews = new ArrayList<String>();
		List<ReviewDao> daos = dataStore.getReviewsByRestId(rest_id);
		for(ReviewDao r:daos){
			rest_reviews.add(r.getReview());
		}
		return rest_reviews;		
	}
	
	public void removeFood(String food_name) {
		dataStore.removeFoodName(food_name);
	}
	
	public void getFoodNames(ArrayList<String> dictionary){
		List<FoodDao> daos = dataStore.getFoodNames();
		for(FoodDao r:daos){
			dictionary.add(r.getFood_name());
		}
	}
	
	public void insertFood(String food_name){
		dataStore.insertFood(food_name);
	}
	
	

}
