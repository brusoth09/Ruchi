package com.ruchi.engine.foodextractionEvaluvation;

import java.io.IOException;

import com.ruchi.engine.sentimentEvaluvation.SentiChecker;


public class Main {
public static void main(String[] arg)
{
	String actualDataFileLocation= "IOfolder/actual.txt";
	String actualModelFileLocation= "IOfolder/model.txt";
	int totalEntries=1219;
	ReadFromFile readFromFile=new ReadFromFile();
	try {
		readFromFile.readFromActualFile(actualDataFileLocation);
		readFromFile.readFromModelFile(actualModelFileLocation);
		if(Constant.actualIdentifierList.size()==Constant.modelIdentifierList.size())
		{
			
			System.out.println(Constant.actualIdentifierList.size());
			FoodExtractionChecker.countElements();
			System.out.println("*****************Result*********************");
			System.out.println("truePositive:"+FoodExtractionChecker.truePositive+"\nfalsePoitive"+FoodExtractionChecker.FalsePositive+"\nfalseNegative"
					+FoodExtractionChecker.FalseNegative+"\nTrue Negative"+FoodExtractionChecker.TrueNegative);
			
			double precision=(FoodExtractionChecker.truePositive)/(FoodExtractionChecker.truePositive+FoodExtractionChecker.FalsePositive);
			double recall=(FoodExtractionChecker.truePositive)/(FoodExtractionChecker.truePositive+FoodExtractionChecker.FalseNegative);
			double f_measure=(2*precision*recall)/(precision+recall);
			System.out.println("accuracy:"+(FoodExtractionChecker.truePositive+FoodExtractionChecker.TrueNegative)/totalEntries);
			System.out.println("precision:"+precision);
			System.out.println("recall:"+recall);
			System.out.println("f_measure:"+f_measure);
			System.out.println("********************************************");
		}
		else
		{
			System.out.println("Error Ocuured Reading two files");
		}
	} catch (IOException e) {
		e.printStackTrace();
	}
	
}
}
