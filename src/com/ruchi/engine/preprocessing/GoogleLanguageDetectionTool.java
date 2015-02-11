package com.ruchi.engine.preprocessing;

import java.util.ArrayList;

import com.cybozu.labs.langdetect.Detector;
import com.cybozu.labs.langdetect.DetectorFactory;
import com.cybozu.labs.langdetect.LangDetectException;
import com.cybozu.labs.langdetect.Language;

public class GoogleLanguageDetectionTool implements LanguageDectectionTool{
	
	private Detector detector;

	@Override
	public void loadModule() {
        String path=System.getProperty("user.dir")+"/profiles";		//path to profile directory
        
        try {
            DetectorFactory.loadProfile(path);						//load directory
            detector = DetectorFactory.create();
        } catch (LangDetectException e) {
            e.printStackTrace();
        }
	}

	@Override
	public boolean findLanguage(String text) {
		try {
            detector.append(text);
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
