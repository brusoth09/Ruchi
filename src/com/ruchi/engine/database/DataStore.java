package com.ruchi.engine.database;

import java.util.Date;

import org.apache.lucene.queries.function.valuesource.ReciprocalFloatFunction;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

import com.ruchi.hibernate.model.DAO.CityDao;
import com.ruchi.hibernate.model.DAO.FoodDao;
import com.ruchi.hibernate.model.DAO.RestaurantDao;
import com.ruchi.hibernate.model.DAO.ReviewDao;
import com.ruchi.hibernate.model.sql.QueryTemplate;
import com.ruchi.hibernate.util.HibernateUtil;

public class DataStore {
	public static void main(String[] args) {

		DataStore dataStore = new DataStore();
//		dataStore.insertReviewRating("1408182490042", 4f);
//		dataStore.insertRestRating("111", 2f);
		dataStore.insertFood("sample food");
	}

	public boolean insertReviewRating(String review_id, float rating) {
		Session session = null;
		try {
			session = HibernateUtil.getSessionFactory().getCurrentSession();
			session.beginTransaction();
			ReviewDao reviewDao = (ReviewDao) session.get(ReviewDao.class,
					review_id);
			System.out.println(reviewDao.getRating() + " "
					+ reviewDao.getRest_id() + " " + reviewDao.getReview()
					+ " " + reviewDao.getReview_id());
			reviewDao.setRating(rating);
			session.update(reviewDao);
			session.getTransaction().commit();
			return true;

		} catch (HibernateException e) {
			session.close();
			e.printStackTrace();
		}
		return false;
	}

	public boolean insertRestRating(String rest_Id, float rating) {
		Session session = null;
		try {
			session = HibernateUtil.getSessionFactory().getCurrentSession();
			session.beginTransaction();
			RestaurantDao restaurantDao = (RestaurantDao) session.get(RestaurantDao.class,rest_Id);
			System.out.println(rating);
			restaurantDao.setRest_rating(rating);
			session.update(restaurantDao);
			session.getTransaction().commit();
			return true;

		} catch (HibernateException e) {
			session.close();
			e.printStackTrace();
		}
		return false;
	}

	public Date insertFood(String food_name) {
		Session session = null;
		try {
			session = HibernateUtil.getSessionFactory().getCurrentSession();
			session.beginTransaction();
			FoodDao foodDao = (FoodDao) session.get(FoodDao.class,food_name);
			System.out.println(foodDao.getFood_id());
//			FoodDao.setRest_rating(rating);
//			session.update(restaurantDao);
			session.getTransaction().commit();
			return new Date();

		} catch (HibernateException e) {
			session.close();
			e.printStackTrace();
		}
		return new Date();
	}

	public boolean insertReviewFood(String review_id, String food_id,
			float rating) {

		return false;
	}

	public boolean insertRestFood(String rest_id, String food_id, float rating) {

		return false;
	}

}
