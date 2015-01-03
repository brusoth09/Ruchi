package com.ruchi.hibernate.main;

import com.ruchi.hibernate.model.DAO.CityDao;
import com.ruchi.hibernate.model.DAO.FoodDao;
import com.ruchi.hibernate.model.DAO.RestaurantDao;
import com.ruchi.hibernate.model.DAO.ReviewDao;
import com.ruchi.hibernate.util.HibernateUtil;

import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.Iterator;
import java.util.List;

/**
 * Created by Thamayanthy on 12/22/2014.
 */
public class HibernateMain {
	public static void main(String[] args) {

		Session session = null;// =
								// HibernateUtil.getSessionFactory().getCurrentSession();
		try {
			session = HibernateUtil.getSessionFactory().getCurrentSession();
			RestaurantDao restaurant = new RestaurantDao();
			// restaurant.setCity_id("111111");
			restaurant.setRest_id("1408181771009");
			// restaurant.setRest_name("restaurant_name");
			//

			CityDao cityDao = new CityDao();
			cityDao.setCity_id("1408181750584");
			cityDao.setCity("colombo");

			FoodDao foodDao = new FoodDao();
			foodDao.setFood_id("1212212121");

			ReviewDao reviewDao = new ReviewDao();
			reviewDao.setRest_id("112121212");
			reviewDao.setReview("sfdasfasfasfaf");
			reviewDao.setReview_id("23232323");

			// session = HibernateUtil.getSessionFactory().getCurrentSession();
			// start transaction
			session.beginTransaction();
			CityDao tempCity = (CityDao) session.get(CityDao.class,
					"1408181750583");
			System.out.println(tempCity.getCity());
			System.out.println(session.save(cityDao));
			// Save the Model object
			session.save(cityDao);
			System.out.println("ss");
			// Commit transaction
			session.getTransaction().commit();
			// System.out.println("Restaurant ID="+restaurant.getRest_id());
			// System.out.println(session.get(Restaurant.class,
			// (java.io.Serializable) restaurant.getRest_id()));
			// terminate session factory, otherwise program won't end
			// HibernateUtil.getSessionFactory().close();
		} catch (Exception e) {
			e.printStackTrace();

		} finally {
			session.close();
			System.out.println("done");
		}

		/*
		 * Session sess = null; Transaction tran = null; Session session =
		 * null;//= HibernateUtil.getSessionFactory().getCurrentSession(); try {
		 * session = HibernateUtil.getSessionFactory().getCurrentSession(); tran
		 * = session.beginTransaction(); List list =
		 * session.createQuery("from RestaurantDao").list(); Iterator itr =
		 * list.iterator(); while (itr.hasNext()) { RestaurantDao restaurant =
		 * (RestaurantDao) itr.next(); System.out.print("restName: " +
		 * restaurant.getRest_name());
		 * 
		 * System.out.println(); } tran.commit(); } catch (Exception ex) {
		 * ex.printStackTrace(); } finally { try { session.close(); }catch
		 * (Exception e){
		 * System.out.println("\n\nexception captured in finally"); }
		 * System.out.println("done"); }
		 */

	}
}
