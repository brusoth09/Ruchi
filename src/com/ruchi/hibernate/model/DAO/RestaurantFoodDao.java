package com.ruchi.hibernate.model.DAO;

import javax.persistence.*;

/**
 * Created by Thamayanthy on 12/22/2014.
 */


@Entity
@Table(name = "rest_food", uniqueConstraints = {@UniqueConstraint(columnNames = {"rest_id", "food_id"})})
public class RestaurantFoodDao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @Column(name = "rest_id", nullable = false, unique = false, length = 50)
    private String rest_id;

    @Column(name = "food_id", nullable = false, unique = false, length = 50)
    private String food_id;

    @Column(name = "rating", nullable = true, unique = false, length = 50)
    private String rating;

    public String getRest_id() {
        return rest_id;
    }

    public void setRest_id(String rest_id) {
        this.rest_id = rest_id;
    }

    public String getFood_id() {
        return food_id;
    }

    public void setFood_id(String food_id) {
        this.food_id = food_id;
    }

	public String getRating() {
		return rating;
	}

	public void setRating(String rating) {
		this.rating = rating;
	}
  
    
}
