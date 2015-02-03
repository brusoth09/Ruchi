package com.ruchi.engine.preprocessing;

import org.tartarus.snowball.ext.PorterStemmer;

public class SnowballPorterStemmer implements Stemmer{
	PorterStemmer stemmer=null;
	
	public SnowballPorterStemmer() {
		stemmer = new PorterStemmer();
	}

	@Override
	public String doStemming(String input) {
		stemmer.setCurrent(input);
        stemmer.stem();
        return stemmer.getCurrent();
	}
}
