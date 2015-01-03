package com.ruchi.hibernate.model;

/**
 * Created by Thamayanthy on 12/22/2014.
 */
public class Sentiment {
    private String rest_id;
    private String food_id;
    private String sentiment_value;
    private String rank;

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

    public String getSentiment_value() {
        return sentiment_value;
    }

    public void setSentiment_value(String sentiment_value) {
        this.sentiment_value = sentiment_value;
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }
}
