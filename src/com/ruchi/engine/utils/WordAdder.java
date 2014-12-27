package com.ruchi.engine.utils;

import com.ruchi.engine.database.DatabaseConnector;

/**
 * Created by brusoth on 11/26/2014.
 */
public class WordAdder {
    public void divide(String text,DatabaseConnector db)
    {
        int count=3;
        String[] tok=text.split("\\s+");
        for(String s:tok)
        {
            if(s.length()>2)
            {
                db.insertWord(s.trim());
            }
        }
    }
}
