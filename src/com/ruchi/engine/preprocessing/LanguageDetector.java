package com.ruchi.engine.preprocessing;

import com.cybozu.labs.langdetect.Detector;
import com.cybozu.labs.langdetect.DetectorFactory;
import com.cybozu.labs.langdetect.LangDetectException;
import com.cybozu.labs.langdetect.Language;

import java.util.ArrayList;

/**
 * Created by brusoth on 11/7/2014.
 */
public class LanguageDetector {
	
	private Detector detector;
 
    public void load_profile()
    {
    	//path to profile directory
        String path=System.getProperty("user.dir")+"/profiles";
        
        try {
            DetectorFactory.loadProfile(path);						//load directory
            detector = DetectorFactory.create();
        } catch (LangDetectException e) {
            e.printStackTrace();
        }
    }

    public boolean check_Language(String review)
    {
        try {
            detector.append(review);
            ArrayList<Language> langlist = detector.getProbabilities();
            for(Language lan:langlist)
            {
            	//if probability for English is more than 0.8 then return true
                if(lan.lang.equalsIgnoreCase("en") && lan.prob>0.8)
                {
                    return true;
                }
            }

        } catch (LangDetectException e) {
            e.printStackTrace();
        }
        return false;
    }

}
