package com.ruchi.engine.models;

import java.util.ArrayList;

/**
 * Created by brusoth on 12/12/2014.
 */
public class Review {
    ArrayList<Sentence> list=new ArrayList<Sentence>();

    public void addReview(Sentence object){
        list.add(object);
    }
}
