package com.ruchi.engine.database;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.lucene.queries.function.valuesource.ReciprocalFloatFunction;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.ruchi.hibernate.model.DAO.CityDao;
import com.ruchi.hibernate.model.DAO.FoodDao;
import com.ruchi.hibernate.model.DAO.RestaurantDao;
import com.ruchi.hibernate.model.DAO.RestaurantFoodDao;
import com.ruchi.hibernate.model.DAO.ReviewDao;
import com.ruchi.hibernate.model.DAO.ReviewFoodDao;
import com.ruchi.hibernate.util.HibernateUtil;

import edu.stanford.nlp.time.SUTime.Time;

public class DataStore {
	public static void main(String[] args) {
		DataStore dataStore = new DataStore();
		// dataStore.insertReviewRating("1408182490042", 4f);
		// dataStore.insertRestRating("111", 2f);

//		System.out.println(dataStore.insertFood("sample food6"));
//		System.out.println(dataStore.insertReviewFood("1408182490041", "2015-01-06 10:16:28", 4f));
		System.out.println(dataStore.insertRestFood("1408181771009", "2015-01-06 10:16:28", 1f));
		System.out.println("done");
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

		try {
			Timestamp food_id = getFood_id(food_name);

			// Transacstion transaction;
			if (!(food_id == null)) {
				return food_id;
			} else {
				 insertNewFood(food_name);
				 return getFood_id(food_name);
			}

		} catch (HibernateException e) {
			// session.close();
			e.printStackTrace();
		}
		return null;
	}

	public boolean insertReviewFood(String review_id, String food_id,
			float rating) {

		Session session = null;
		session = HibernateUtil.getSessionFactory().getCurrentSession();
		final Transaction transaction = session.beginTransaction();
		try {

			ReviewFoodDao reviewFoodDao = new ReviewFoodDao();
			reviewFoodDao.setReview_id(review_id);
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		    Date parsedDate = dateFormat.parse(food_id);
		    Timestamp timestamp = new java.sql.Timestamp(parsedDate.getTime());
			reviewFoodDao.setFood_id(timestamp);
			reviewFoodDao.setRating(rating);
			session.save(reviewFoodDao);
			session.getTransaction().commit();
			return true;

		} catch (HibernateException | ParseException e) {
			transaction.rollback();
			
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
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		    Date parsedDate = dateFormat.parse(food_id);
		    Timestamp timestamp = new java.sql.Timestamp(parsedDate.getTime());
			restaurantFoodDao.setFood_id(timestamp);
			restaurantFoodDao.setRating(rating);
			session.save(restaurantFoodDao);
			session.getTransaction().commit();
			return true;

		} catch (HibernateException | ParseException e) {
//			session.close();
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
			String hql = "FROM FoodDao F WHERE F.food_name = '" + food_name
					+ "'";
			Query query = session.createQuery(hql);
			@SuppressWarnings("unchecked")
			List<FoodDao> results = query.list();
			if (!results.isEmpty()) {
				session.close();
//				System.out.println("inserted"+results.get(0).getFood_id());
				return results.get(0).getFood_id();
			}	
			//
		} catch (HibernateException e) {
//			session.close();
			e.printStackTrace();
		}
		session.close();
		return null;
	}

	private Timestamp insertNewFood(String food_name) {
		Session session = null;
		session = HibernateUtil.getSessionFactory().getCurrentSession();
		try {

			session.beginTransaction();
			FoodDao foodDao;// = (FoodDao) session.get(FoodDao.class,
							// food_name);
			foodDao = new FoodDao();
			foodDao.setFood_name(food_name);
			Timestamp timestamp=new Timestamp(Calendar.getInstance()
					.getTimeInMillis());
			foodDao.setFood_id(timestamp);
			session.save(foodDao);
			session.getTransaction().commit();
//			Timestamp food_id_new = getFood_id(food_name);
//			System.out.println("inserted"+food_id_new);
//			session.close();
			return timestamp;
		} catch (HibernateException exception) {
			
			exception.printStackTrace();
		}
		session.close();
		return null;
	}
}
