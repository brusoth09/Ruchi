package com.ruchi.engine.preprocessing;

import com.cybozu.labs.langdetect.Detector;
import com.cybozu.labs.langdetect.DetectorFactory;
import com.cybozu.labs.langdetect.LangDetectException;
import com.cybozu.labs.langdetect.Language;

import java.util.ArrayList;

/**
 * Created by brusoth on 11/7/2014.
 */
public class LanguageDetection {

    private int total_reviews=0;
    private int passed_reviews=0;



    public void load_profile()
    {
        String path=System.getProperty("user.dir")+"/profiles";
        System.out.println(path);
        try {
            DetectorFactory.loadProfile(path);
        } catch (LangDetectException e) {
            e.printStackTrace();
        }
    }

    public boolean check_Language(String review)
    {
        try {
            Detector detector = DetectorFactory.create();
            detector.append(review);
            ArrayList<Language> langlist = detector.getProbabilities();
            for(Language lan:langlist)
            {
                if(lan.lang.equalsIgnoreCase("en") && lan.prob>0.8)
                {
                    passed_reviews++;
                    return true;
                }
            }
            total_reviews++;

        } catch (LangDetectException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void process(ArrayList<String> list)
    {
        load_profile();
        for(String s:list)
        {
            check_Language(remove_symbols(s));
        }
        System.out.println(passed_reviews/total_reviews);
    }


    public static String remove_symbols(String review)
    {
        review=review.replaceAll("\\(.*\\)", "").replaceAll("[^a-zA-Z0-9', -]", "").replaceAll("\\s+"," ").replaceAll("-"," ");
        return review;
    }
}
