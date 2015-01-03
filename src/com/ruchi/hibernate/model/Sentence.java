package com.ruchi.hibernate.model;

/**
 * Created by Thamayanthy on 12/22/2014.
 */
public class Sentence {
    private String review_id;
    private int sentence_location;
    private String food_id;
    private float sentiment_value;

    public String getReview_id() {
        return review_id;
    }

    public void setReview_id(String review_id) {
        this.review_id = review_id;
    }

    public int getSentence_location() {
        return sentence_location;
    }

    public void setSentence_location(int sentence_location) {
        this.sentence_location = sentence_location;
    }

    public String getFood_id() {
        return food_id;
    }

    public void setFood_id(String food_id) {
        this.food_id = food_id;
    }

    public float getSentiment_value() {
        return sentiment_value;
    }

    public void setSentiment_value(float sentiment_value) {
        this.sentiment_value = sentiment_value;
    }
}
