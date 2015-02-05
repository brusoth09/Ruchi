package com.ruchi.engine.database;

import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Projections;
import org.hibernate.transform.Transformers;

import com.ruchi.engine.utils.KeyGenerator;
import com.ruchi.hibernate.model.DAO.FoodDao;
import com.ruchi.hibernate.model.DAO.FoodInitDao;
import com.ruchi.hibernate.model.DAO.RestaurantDao;
import com.ruchi.hibernate.model.DAO.RestaurantFoodDao;
import com.ruchi.hibernate.model.DAO.ReviewDao;
import com.ruchi.hibernate.model.DAO.ReviewFoodDao;
import com.ruchi.hibernate.model.DAO.ReviewTrainDao;
import com.ruchi.hibernate.util.HibernateUtil;

/**
 * 
 * @author Thamayanthy The class used to store and retrieve data from the
 *         database which is communicating the Dao Objects
 */
public class DataStore {

	/*
	 * main method for the checking purposed. not to be used by the system
	 */

	public static void main(String[] args) {
		DataStore dataStore = new DataStore();
//		 dataStore.insertReviewRating("1408182490042", 4f);
		// dataStore.insertRestRating("111", 2f);

		 System.out.println(dataStore.insertFood("sample food6"));
		 System.out.println(dataStore.insertFoodInit("sample food6"));
		// System.out.println(dataStore.insertReviewFood("1408182490041",
		// "2015-01-06 10:16:28", 4f));
		// System.out.println(dataStore.insertRestFood("1408181771009",
		// "2015-01-06 10:16:28", 1f));

//		System.out.print(dataStore.getReviewsByRestName("Ruchi").size()
//				+ "    "
//				+ dataStore.getReviewsByRestName("Ruchi").get(0).getRest_id());

		// System.out.println(dataStore.getRestaurantIds().size()+"   "+dataStore.getRestaurantIds().get(0).getRest_id());

		// System.out.println(dataStore.insertFood("pizza1"));
		// System.out.println(dataStore.getRestaurantIds().get(0).getRest_id());
		System.out.println("done");

	}

	/*
	 * insert the rating value to each review reviews table will be updated
	 */
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

		} catch (org.hibernate.exception.ConstraintViolationException e) {
			e.printStackTrace();
		} catch (HibernateException e) {
			session.close();
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	/*
	 * insert the rating value to the reviews_train table
	 */
	public boolean insertReviewTrainRating(String review_id, float rating) {
		Session session = null;
		try {
			session = HibernateUtil.getSessionFactory().getCurrentSession();
			session.beginTransaction();
			ReviewTrainDao reviewTrainDao = (ReviewTrainDao) session.get(
					ReviewTrainDao.class, review_id);
			System.out.println(reviewTrainDao.getRating() + " "
					+ reviewTrainDao.getRest_id() + " "
					+ reviewTrainDao.getReview() + " "
					+ reviewTrainDao.getReview_id());
			reviewTrainDao.setRating(rating);
			session.update(reviewTrainDao);
			session.getTransaction().commit();
			return true;
			//
		} catch (org.hibernate.exception.ConstraintViolationException e) {
			e.printStackTrace();
		} catch (HibernateException e) {
			session.close();
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	/*
	 * insert rating value for the restaurant
	 */
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

		} catch (org.hibernate.exception.ConstraintViolationException e) {
			e.printStackTrace();
		} catch (HibernateException e) {
			session.close();
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	/*
	 * insert a food name found among the reviews
	 */
	public String insertFood(String food_name) {

		try {
			String food_id = getFood_id(food_name);

			// Transacstion transaction;
			if (!(food_id == null)) {
				System.out.println("food name already exists");
				return food_id;
			} else {
				insertNewFood(food_name);
				System.out.println("food name added");
				return getFood_id(food_name);
			}

		} catch (org.hibernate.exception.ConstraintViolationException e) {
			e.printStackTrace();
		} catch (HibernateException e) {
			// session.close();
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/*
	 * insert food name to food_init table which is used to find the food items
	 * from the reviews initially
	 */
	public String insertFoodInit(String food_name) {

		try {
			String food_id =null;
			food_id=getFoodInit_id(food_name);

			// Transacstion transaction;
			if (!(food_id == null)) {
				System.out.println("food name already exists");
				return food_id;
			} else {
				insertNewFoodInit(food_name);
				System.out.println("food name added");
				return getFoodInit_id(food_name);
			}

		} catch (org.hibernate.exception.ConstraintViolationException e) {
			e.printStackTrace();
		} catch (HibernateException e) {
			// session.close();
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/*
	 * inserting and assigning food name to relevant review review_food table
	 * will be updated
	 */
	public boolean insertReviewFood(String review_id, String food_id,
			float rating) {

		Session session = null;
		session = HibernateUtil.getSessionFactory().getCurrentSession();
		final Transaction transaction = session.beginTransaction();
		try {

			ReviewFoodDao reviewFoodDao = new ReviewFoodDao();
			reviewFoodDao.setReview_id(review_id);
			SimpleDateFormat dateFormat = new SimpleDateFormat(
					"yyyy-MM-dd hh:mm:ss");
			// Date parsedDate = dateFormat.parse(food_id);
			// Timestamp timestamp = new
			// java.sql.Timestamp(parsedDate.getTime());
			reviewFoodDao.setFood_id(food_id);
			reviewFoodDao.setRating(rating);
			session.save(reviewFoodDao);
			session.getTransaction().commit();
			return true;

		} catch (org.hibernate.exception.ConstraintViolationException e) {
			e.printStackTrace();
		} catch (HibernateException e) {
			transaction.rollback();
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return false;
	}

	/*
	 * 
	 */

	public boolean insertRestFood(String rest_id, String food_id, float rating) {
		Session session = null;
		try {
			session = HibernateUtil.getSessionFactory().getCurrentSession();
			session.beginTransaction();
			RestaurantFoodDao restaurantFoodDao = new RestaurantFoodDao();
			restaurantFoodDao.setRest_id(rest_id);
			SimpleDateFormat dateFormat = new SimpleDateFormat(
					"yyyy-MM-dd hh:mm:ss");
			// Date parsedDate = dateFormat.parse(food_id);
			// Timestamp timestamp = new
			// java.sql.Timestamp(parsedDate.getTime());
			restaurantFoodDao.setFood_id(food_id);
			restaurantFoodDao.setRating(rating);
			session.save(restaurantFoodDao);
			session.getTransaction().commit();
			return true;

		} catch (org.hibernate.exception.ConstraintViolationException e) {
			e.printStackTrace();
		} catch (HibernateException e) {
			// session.close();
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	private String getFood_id(String food_name) {
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
				// System.out.println("inserted"+results.get(0).getFood_id());
				return results.get(0).getFood_id();
			}
			//
		} catch (org.hibernate.exception.ConstraintViolationException e) {
			e.printStackTrace();
		} catch (HibernateException e) {
			// session.close();
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		session.close();
		return null;
	}

	private String getFoodInit_id(String food_name) {
		Session session = null;
		try {
			session = HibernateUtil.getSessionFactory().getCurrentSession();
			session.beginTransaction();
			FoodInitDao foodDao;
			String hql = "FROM FoodInitDao F WHERE F.food_name = '" + food_name
					+ "'";
			Query query = session.createQuery(hql);
			@SuppressWarnings("unchecked")
			List<FoodInitDao> results = query.list();
			if (!results.isEmpty()) {
				session.close();
				// System.out.println("inserted"+results.get(0).getFood_id());
				return results.get(0).getFood_id();
			}
			//
		} catch (org.hibernate.exception.ConstraintViolationException e) {
			e.printStackTrace();
		} catch (HibernateException e) {
			// session.close();
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		session.close();
		return null;
	}

	private String insertNewFood(String food_name) {
		Session session = null;
		session = HibernateUtil.getSessionFactory().getCurrentSession();
		try {

			session.beginTransaction();
			FoodDao foodDao;// = (FoodDao) session.get(FoodDao.class,
							// food_name);
			foodDao = new FoodDao();
			foodDao.setFood_name(food_name);
			String timestamp = KeyGenerator.uniqueCurrentTimeMS();
			foodDao.setFood_id(timestamp);
			session.save(foodDao);
			session.getTransaction().commit();
			// Timestamp food_id_new = getFood_id(food_name);
			// System.out.println("inserted"+food_id_new);
			// session.close();
			return timestamp;
		} catch (org.hibernate.exception.ConstraintViolationException e) {
			e.printStackTrace();
		} catch (HibernateException exception) {

			exception.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		session.close();
		return null;
	}

	private String insertNewFoodInit(String food_name) {
		Session session = null;
		session = HibernateUtil.getSessionFactory().getCurrentSession();
		try {

			session.beginTransaction();
			FoodInitDao foodDao;// = (FoodDao) session.get(FoodDao.class,
			// food_name);
			foodDao = new FoodInitDao();
			foodDao.setFood_name(food_name);
			String timestamp = KeyGenerator.uniqueCurrentTimeMS();
			foodDao.setFood_id(timestamp);
			session.save(foodDao);
			session.getTransaction().commit();
			// Timestamp food_id_new = getFood_id(food_name);
			// System.out.println("inserted"+food_id_new);
			// session.close();
			return timestamp;
		} catch (org.hibernate.exception.ConstraintViolationException e) {
			e.printStackTrace();
		} catch (HibernateException exception) {

			exception.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		session.close();
		return null;
	}

	public List<FoodDao> getFoodNames() {
		Session session = null;
		List<FoodDao> results = new ArrayList<FoodDao>();
		try {
			session = HibernateUtil.getSessionFactory().getCurrentSession();
			session.beginTransaction();
			String hql = "FROM FoodDao F";

			Query query = session.createQuery(hql);
			// System.out.println(query);
			// @SuppressWarnings("unchecked")
			results = query.list();
			// session.save(restaurantFoodDao);
			session.getTransaction().commit();

		} catch (org.hibernate.exception.ConstraintViolationException e) {
			e.printStackTrace();
		} catch (HibernateException e) {
			// session.close();
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return results;
	}

	public List<FoodInitDao> getFoodInitNames() {
		Session session = null;
		List<FoodInitDao> results = new ArrayList<FoodInitDao>();
		try {
			session = HibernateUtil.getSessionFactory().getCurrentSession();
			session.beginTransaction();
			String hql = "FROM FoodInitDao F";

			Query query = session.createQuery(hql);
			// System.out.println(query);
			// @SuppressWarnings("unchecked")
			results = query.list();
			// session.save(restaurantFoodDao);
			session.getTransaction().commit();

		} catch (org.hibernate.exception.ConstraintViolationException e) {
			e.printStackTrace();
		} catch (HibernateException e) {
			// session.close();
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return results;
	}

	public String getRestId(String restaurant_name) {
		Session session = null;
		List<RestaurantDao> results = new ArrayList<RestaurantDao>();
		try {
			session = HibernateUtil.getSessionFactory().getCurrentSession();
			session.beginTransaction();
			String hql = "FROM RestaurantDao R where R.rest_name= ?";

			Query query = session.createQuery(hql)
					.setString(0, restaurant_name);
			// System.out.println(query);
			// @SuppressWarnings("unchecked")
			results = query.list();
			// session.save(restaurantFoodDao);
			session.getTransaction().commit();

		} catch (org.hibernate.exception.ConstraintViolationException e) {
			e.printStackTrace();
		} catch (HibernateException e) {
			// session.close();
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return results.get(0).getRest_id();
	}

	public String getRestName(String restaurant_id) {
		Session session = null;
		List<RestaurantDao> results = new ArrayList<RestaurantDao>();
		try {
			session = HibernateUtil.getSessionFactory().getCurrentSession();
			session.beginTransaction();
			String hql = "FROM RestaurantDao R where R.rest_id= ?";

			Query query = session.createQuery(hql).setString(0, restaurant_id);
			// System.out.println(query);
			// @SuppressWarnings("unchecked")
			results = query.list();
			// session.save(restaurantFoodDao);
			session.getTransaction().commit();

		} catch (org.hibernate.exception.ConstraintViolationException e) {
			e.printStackTrace();
		} catch (HibernateException e) {
			// session.close();
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return results.get(0).getRest_name();
	}

	public List<ReviewDao> getReviewsByRestName(String restaurant_name) {
		Session session = null;
		List<ReviewDao> results = new ArrayList<ReviewDao>();
		String rest_id = getRestId(restaurant_name);
		try {
			session = HibernateUtil.getSessionFactory().getCurrentSession();
			session.beginTransaction();
			String hql = "FROM ReviewDao R where R.rest_id= ?";

			Query query = session.createQuery(hql).setString(0, rest_id);
			// System.out.println(query);
			// @SuppressWarnings("unchecked")
			results = query.list();
			// session.save(restaurantFoodDao);
			session.getTransaction().commit();

		} catch (org.hibernate.exception.ConstraintViolationException e) {
			e.printStackTrace();
		} catch (HibernateException e) {
			// session.close();
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return results;
	}

	public List<ReviewTrainDao> getReviewsTrainByRestName(String restaurant_name) {
		Session session = null;
		List<ReviewTrainDao> results = new ArrayList<ReviewTrainDao>();
		String rest_id = getRestId(restaurant_name);
		try {
			session = HibernateUtil.getSessionFactory().getCurrentSession();
			session.beginTransaction();
			String hql = "FROM ReviewTrainDao R where R.rest_id= ?";

			Query query = session.createQuery(hql).setString(0, rest_id);
			// System.out.println(query);
			// @SuppressWarnings("unchecked")
			results = query.list();
			// session.save(restaurantFoodDao);
			session.getTransaction().commit();

		} catch (org.hibernate.exception.ConstraintViolationException e) {
			e.printStackTrace();
		} catch (HibernateException e) {
			// session.close();
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return results;
	}

	@SuppressWarnings("unchecked")
	public List<ReviewDao> getReviewsByRestId(String restaurant_id) {
		Session session = null;
		List<ReviewDao> results = new ArrayList<ReviewDao>();
		try {
			session = HibernateUtil.getSessionFactory().getCurrentSession();
			session.beginTransaction();

			String hql = "FROM ReviewDao R where R.rest_id= ?";

			Query query = session.createQuery(hql).setString(0, restaurant_id);
			results = query.list();
			session.getTransaction().commit();

		} catch (org.hibernate.exception.ConstraintViolationException e) {
			e.printStackTrace();
		} catch (HibernateException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return results;
	}

	public List<ReviewTrainDao> getReviewsTrainByRestId(String restaurant_id) {
		Session session = null;
		List<ReviewTrainDao> results = new ArrayList<ReviewTrainDao>();
		try {
			session = HibernateUtil.getSessionFactory().getCurrentSession();
			session.beginTransaction();

			String hql = "FROM ReviewTrainDao R where R.rest_id= ?";

			Query query = session.createQuery(hql).setString(0, restaurant_id);
			results = query.list();
			session.getTransaction().commit();

		} catch (org.hibernate.exception.ConstraintViolationException e) {
			e.printStackTrace();
		} catch (HibernateException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return results;
	}

	@SuppressWarnings({ "unchecked" })
	public List<RestaurantDao> getRestaurantIds() {
		Session session = null;
		List<RestaurantDao> results = new ArrayList<RestaurantDao>();
		try {
			session = HibernateUtil.getSessionFactory().getCurrentSession();
			session.beginTransaction();
			String hql = "R.rest_id FROM ResaurantDao R";
			Criteria cr = session
					.createCriteria(RestaurantDao.class)
					.setProjection(
							Projections.projectionList().add(
									Projections.property("rest_id"), "rest_id"))
					.setResultTransformer(
							Transformers.aliasToBean(RestaurantDao.class));

			// Query query = session.createQuery(hql);
			// System.out.println(query);
			// @SuppressWarnings("unchecked")
			results = cr.list();
			// session.save(restaurantFoodDao);
			session.getTransaction().commit();

		} catch (org.hibernate.exception.ConstraintViolationException e) {
			e.printStackTrace();
		} catch (HibernateException e) {
			// session.close();
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return results;
	}

	public List<RestaurantDao> getRestaurantDaos() {
		Session session = null;
		List<RestaurantDao> results = new ArrayList<RestaurantDao>();
		try {
			session = HibernateUtil.getSessionFactory().getCurrentSession();
			session.beginTransaction();
			String hql = "FROM ResaurantDao R";
			Criteria cr = session
					.createCriteria(RestaurantDao.class)
					.setProjection(
							Projections.projectionList().add(
									Projections.property("rest_id"), "rest_id").add(
											Projections.property("rest_name"), "rest_name"))
					.setResultTransformer(
							Transformers.aliasToBean(RestaurantDao.class));

			// Query query = session.createQuery(hql);
			// System.out.println(query);
			// @SuppressWarnings("unchecked")
			results = cr.list();
			// session.save(restaurantFoodDao);
			session.getTransaction().commit();

		} catch (org.hibernate.exception.ConstraintViolationException e) {
			e.printStackTrace();
		} catch (HibernateException e) {
			// session.close();
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return results;
	}

	public List<RestaurantDao> getRestaurantNames() {
		Session session = null;
		List<RestaurantDao> results = new ArrayList<RestaurantDao>();
		try {
			session = HibernateUtil.getSessionFactory().getCurrentSession();
			session.beginTransaction();
			String hql = "R.rest_name FROM ResaurantDao R";
			Criteria cr = session
					.createCriteria(RestaurantDao.class)
					.setProjection(
							Projections.projectionList().add(
									Projections.property("rest_name"),
									"rest_name"))
					.setResultTransformer(
							Transformers.aliasToBean(RestaurantDao.class));

			// Query query = session.createQuery(hql);
			// System.out.println(query);
			// @SuppressWarnings("unchecked")
			results = cr.list();
			// session.save(restaurantFoodDao);
			session.getTransaction().commit();

		} catch (org.hibernate.exception.ConstraintViolationException e) {
			e.printStackTrace();
		} catch (HibernateException e) {
			// session.close();
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return results;
	}

	public boolean removeFoodName(String foodName) {
		Session session = null;
		int b = 0;
		// List<RestaurantDao> results = new ArrayList<RestaurantDao>();
		try {
			session = HibernateUtil.getSessionFactory().getCurrentSession();
			session.beginTransaction();
			String hql = "delete FROM FoodDao F where F.food_name=?";

			Query query = session.createQuery(hql).setString(0, foodName);
			// System.out.println(query);
			// @SuppressWarnings("unchecked")
			b = query.executeUpdate();
			// session.save(restaurantFoodDao);
			session.getTransaction().commit();

		} catch (org.hibernate.exception.ConstraintViolationException e) {
			e.printStackTrace();
		} catch (HibernateException e) {
			// session.close();
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (b > 0) {
			return true;
		} else
			return false;
	}

	public boolean removeFoodInitName(String foodName) {
		Session session = null;
		int b = 0;
		// List<RestaurantDao> results = new ArrayList<RestaurantDao>();
		try {
			session = HibernateUtil.getSessionFactory().getCurrentSession();
			session.beginTransaction();
			String hql = "delete FROM FoodInitDao F where F.food_name=?";

			Query query = session.createQuery(hql).setString(0, foodName);
			// System.out.println(query);
			// @SuppressWarnings("unchecked")
			b = query.executeUpdate();
			// session.save(restaurantFoodDao);
			session.getTransaction().commit();

		} catch (org.hibernate.exception.ConstraintViolationException e) {
			e.printStackTrace();
		} catch (HibernateException e) {
			// session.close();
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (b > 0) {
			return true;
		} else
			return false;
	}

}
