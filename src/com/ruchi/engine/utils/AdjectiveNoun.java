package com.ruchi.engine.utils;

import java.util.Arrays;
import java.util.List;

public class AdjectiveNoun {
	static List<String> list=Arrays.asList("italian","indian","american","french","japanese","latio","asian","special","fried");
	
	public static boolean checkitem(String item){
		if(list.contains(item.toLowerCase())){
			return true;
		}
		return false;
	}
}
