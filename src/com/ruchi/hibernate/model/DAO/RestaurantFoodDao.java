package com.ruchi.hibernate.model.DAO;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * Created by Thamayanthy on 12/22/2014.
 */

@Entity
@Table(name = "rest_food", uniqueConstraints = { @UniqueConstraint(columnNames = {
		"rest_id", "food_id" }) })
public class RestaurantFoodDao {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "rest_id", nullable = false, unique = false, length = 50)
	private String rest_id;

	@Column(name = "food_id", nullable = false, unique = false, length = 50)
	private Timestamp food_id;

	@Column(name = "rating", nullable = true, unique = false, length = 50)
	private Float rating;

	public String getRest_id() {
		return rest_id;
	}

	public void setRest_id(String rest_id) {
		this.rest_id = rest_id;
	}

	public Timestamp getFood_id() {
		return food_id;
	}

	public void setFood_id(Timestamp food_id) {
		this.food_id = food_id;
	}

	public Float getRating() {
		return rating;
	}

	public void setRating(Float rating) {
		this.rating = rating;
	}

}
