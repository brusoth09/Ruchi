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
			double precision= (SentiChecker.truePositive)/(SentiChecker.truePositive+SentiChecker.FalsePositive);
			double recall=(SentiChecker.truePositive)/(SentiChecker.truePositive+SentiChecker.FalseNegative);
			double f_measure=(2*precision*recall)/(precision+recall);
			System.out.println("accuracy:"+(SentiChecker.truePositive+SentiChecker.TrueNegative)/533); //plz change total number
			System.out.println("recall:"+recall);
			System.out.println("precision:"+precision);
			System.out.println("f-measure:"+f_measure);
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
