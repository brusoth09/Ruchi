package com.ruchi.hibernate.model.DAO;

import javax.persistence.*;

/**
 * Created by Thamayanthy on 12/22/2014.
 */


@Entity
@Table(name = "review_food", uniqueConstraints = {@UniqueConstraint(columnNames = {"review_id","food_id"})})
public class ReviewFoodDao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @Column(name = "review_id", nullable = false, unique = false, length = 50)
    private String review_id;

    @Column(name = "food_id", nullable = false, unique = false, length = 50)
    private String food_id;

    @Column(name = "rating", nullable = false, unique = false, length = 50)
    private float rating;

    public String getReview_id() {
        return review_id;
    }

    public void setReview_id(String review_id) {
        this.review_id = review_id;
    }

    public String getFood_id() {
        return food_id;
    }

    public void setFood_id(String food_id) {
        this.food_id = food_id;
    }

	public float getRating() {
		return rating;
	}

	public void setRating(float rating) {
		this.rating = rating;
	}

    
}