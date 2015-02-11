package com.ruchi.engine.preprocessing;

public interface LanguageDectectionTool {
	
	public void loadModule();
	public boolean findLanguage(String text);
}
