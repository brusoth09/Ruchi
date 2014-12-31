package com.ruchi.engine.models;

import java.util.ArrayList;

/**
 * Created by brusoth on 12/12/2014.
 */
public class Review {
    String id;
    ArrayList<Sentence> list=new ArrayList<Sentence>();

    public void addReview(Sentence object){
        list.add(object);
    }
}
