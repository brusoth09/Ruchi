package com.ruchi.engine.preprocessing;

public class TextUtilizer {
	public static String utilizeText(String text){
		/*RULES
    	 * Remove contents inside braces
    	 * Remove everything other than number,character,apostrophe,comma,hyphen
    	 * Reduce multiple spaces into single space
    	 * Replace hyphen into space
    	 */
        text=text.replaceAll("\\(.*\\)", "").replaceAll("[^a-zA-Z0-9', -]", "").replaceAll("\\s+"," ").replaceAll("-"," ");
        return text;
	}
	
	public static String pluralToSingular(String word){
        if(word.endsWith("es")){
            return word.substring(0,word.length()-1);
        }
        else if(word.endsWith("s")){
            return word.substring(0,word.length()-1);
        }
        else if(word.endsWith("ies")){
        	//
        }
        return word;
    }
}
