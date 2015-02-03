package com.ruchi.hibernate.model.DAO;

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
@Table(name = "reviews_train", uniqueConstraints = {@UniqueConstraint(columnNames = {"review_id"})})
public class ReviewTrainDao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @Column(name = "review_id", nullable = false, unique = true, length = 20)
    private String review_id;

    @Column(name = "rest_id", nullable = false, unique = false, length = 20)
    private String rest_id;

    @Column(name = "review", nullable = true, unique = false, length = 200)
    private String review;

    @Column(name = "rating", nullable = true, unique = false, length = 50)
    private Float rating;

	public String getReview_id() {
		return review_id;
	}

	public void setReview_id(String review_id) {
		this.review_id = review_id;
	}

	public String getRest_id() {
		return rest_id;
	}

	public void setRest_id(String rest_id) {
		this.rest_id = rest_id;
	}

	public String getReview() {
		return review;
	}

	public void setReview(String review) {
		this.review = review;
	}

	public Float getRating() {
		return rating;
	}

	public void setRating(Float rating) {
		this.rating = rating;
	}  
}
