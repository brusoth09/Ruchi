package com.ruchi.engine.sentimentEvaluvation;

import java.io.IOException;
/*
 * calculate accuracy,precision
 */

public class Main {
	
public static void main(String[] arg)
{
	String actualDataFileLocation= "IOfolder/sentiment_train.txt";
	String actualModelFileLocation= "IOfolder/sentiment_out.txt";
	ReadFromFileSentiment readFromFile=new ReadFromFileSentiment();
	try {
		readFromFile.readFromActualFile(actualDataFileLocation);
		readFromFile.readFromModelFile(actualModelFileLocation);
		if(Constant.actualIdentifierListSenti.size()==Constant.modelIdentifierListSenti.size())
		{
			SentiChecker.countElements();
			System.out.println("*****************************************RESULT**********************************");
			System.out.println("truePositive:"+SentiChecker.truePositive+"\nfalsePoitive"+SentiChecker.FalsePositive+"\nfalseNegative"
					+SentiChecker.FalseNegative+"\nTrue Negative"+SentiChecker.TrueNegative);
			System.out.println("accuracy:"+(SentiChecker.truePositive+SentiChecker.TrueNegative)/533); //plz change total number
			System.out.println("precision:"+(SentiChecker.truePositive)/(SentiChecker.truePositive+SentiChecker.FalsePositive));
			System.out.println("*********************************************************************************");
		}
		else
		{
			System.out.println("Error Ocuured Reading two files");
		}
	} catch (IOException e) {
		System.out.println("Error"+e);
	}
	
}
}
