package com.ruchi.engine.System;

public class TestingSystem {
	
	private static TestingSystem instance=null;
	
	private TestingSystem(){
		
	}
	
	public static TestingSystem getInstance(){
		if(instance==null){
			instance=new TestingSystem();
		}
		
		return instance;
	}

}
