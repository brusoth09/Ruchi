package com.ruchi.engine.database;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import org.apache.lucene.queries.function.valuesource.ReciprocalFloatFunction;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

import com.ruchi.hibernate.model.DAO.CityDao;
import com.ruchi.hibernate.model.DAO.FoodDao;
import com.ruchi.hibernate.model.DAO.RestaurantDao;
import com.ruchi.hibernate.model.DAO.RestaurantFoodDao;
import com.ruchi.hibernate.model.DAO.ReviewDao;
import com.ruchi.hibernate.model.DAO.ReviewFoodDao;
import com.ruchi.hibernate.util.HibernateUtil;

public class DataStore {
	public static void main(String[] args) {
		DataStore dataStore = new DataStore();
		// dataStore.insertReviewRating("1408182490042", 4f);
		// dataStore.insertRestRating("111", 2f);
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
			RestaurantDao restaurantDao = (RestaurantDao) session.get(
					RestaurantDao.class, rest_Id);
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
			FoodDao foodDao;// = (FoodDao) session.get(FoodDao.class,
							// food_name);
			session.getTransaction().commit();

			Timestamp food_id = getFood_id(food_name);
			if (!(food_id == null)) {
				return food_id;
			} else if (food_id == null) {
				foodDao = new FoodDao();
				foodDao.setFood_name(food_name);
				session.save(foodDao);
				session.getTransaction().commit();
				Timestamp food_id_new = getFood_id(food_name);
				return food_id_new;
			}

		} catch (HibernateException e) {
			session.close();
			e.printStackTrace();
		}
		return null;
	}

	public boolean insertReviewFood(String review_id, String food_id,
			float rating) {

		Session session = null;
		try {
			session = HibernateUtil.getSessionFactory().getCurrentSession();
			session.beginTransaction();
			ReviewFoodDao reviewFoodDao = new ReviewFoodDao();
			reviewFoodDao.setReview_id(review_id);
			reviewFoodDao.setFood_id(food_id);
			reviewFoodDao.setRating(rating);
			session.save(reviewFoodDao);
			session.getTransaction().commit();
			return true;

		} catch (HibernateException e) {
			session.close();
			e.printStackTrace();
		}
		return false;
	}

	public boolean insertRestFood(String rest_id, String food_id, float rating) {
		Session session = null;
		try {
			session = HibernateUtil.getSessionFactory().getCurrentSession();
			session.beginTransaction();
			RestaurantFoodDao restaurantFoodDao = new RestaurantFoodDao();
			restaurantFoodDao.setRest_id(rest_id);
			restaurantFoodDao.setFood_id(food_id);
			restaurantFoodDao.setRating(rating);
			session.save(restaurantFoodDao);
			session.getTransaction().commit();
			return true;

		} catch (HibernateException e) {
			session.close();
			e.printStackTrace();
		}
		return false;
	}

	private Timestamp getFood_id(String food_name) {
		Session session = null;
		try {
			session = HibernateUtil.getSessionFactory().getCurrentSession();
			session.beginTransaction();
			FoodDao foodDao;
			String hql = "FROM FoodDao F WHERE F.food_name = '" + food_name+"'";
			Query query = session.createQuery(hql);
			List<FoodDao> results = query.list();
			if (!results.isEmpty()) {
				return results.get(0).getFood_id();
			}
			//
		} catch (HibernateException e) {
			session.close();
			e.printStackTrace();
		}
		return null;
	}
}
