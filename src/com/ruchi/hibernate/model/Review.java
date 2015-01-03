package com.ruchi.hibernate.model;

import java.util.Date;

/**
 * Created by Thamayanthy on 12/22/2014.
 */
public class Review {
    private String review_id;
    private String rest_id;
    private String review;
    private Date timestamp;
    private float sent_value;

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

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public float getSent_value() {
        return sent_value;
    }

    public void setSent_value(float sent_value) {
        this.sent_value = sent_value;
    }
}
