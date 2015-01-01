package com.ruchi.engine.preprocessing;

import org.tartarus.snowball.ext.PorterStemmer;


public class Stemmer {
	
	private Stemmer(){
		//static class. object can't be created.
	}
    
    public static  String doStemming(String input) {
        PorterStemmer stemmer = new PorterStemmer();
        stemmer.setCurrent(input);
        stemmer.stem();
        return stemmer.getCurrent();
    }

    public static String pluralToSingular(String word){
    	if(word.endsWith("ies")){
            return word.substring(0,word.length()-3).concat("y");
        }else if(word.endsWith("es")){
            return word.substring(0,word.length()-1);
        }
        else if(word.endsWith("s")){
            return word.substring(0,word.length()-1);
        }

        return word;
    }
    
    public static void main(String[] args) {
        String[] examples={"custards","choose","pancakes","cookies","ice creams","change","pastries"};
        for(String word:examples){
            System.out.println(pluralToSingular(word));
        }
    }
}