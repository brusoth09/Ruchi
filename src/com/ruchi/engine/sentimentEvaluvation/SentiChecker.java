package com.ruchi.engine.sentimentEvaluvation;

/*
 * count TP,TN,FP,FN
 */

public class SentiChecker {

public static int total;
public static float truePositive;
public static float FalseNegative;
public static float FalsePositive;
public static float TrueNegative;

public static void countElements()
{
	for(int i=0;i<Constant.modelIdentifierListSenti.size();i++)
	{
		//System.out.print(i+">");
		if(Constant.modelIdentifierListSenti.get(i).getSentimentTag().equalsIgnoreCase("neg")&&Constant.actualIdentifierListSenti.get(i).getSentimentTag().equalsIgnoreCase("pos"))
		{
			System.out.println("Sentence: "+Constant.modelIdentifierListSenti.get(i).getSentence()+"\nActual Outcome: "+Constant.actualIdentifierListSenti.get(i).getSentimentTag()+"\nModel Outcome: "+Constant.modelIdentifierListSenti.get(i).getSentimentTag());
			FalseNegative++;
		}
		
		else if(Constant.modelIdentifierListSenti.get(i).getSentimentTag().equalsIgnoreCase("pos")&&Constant.actualIdentifierListSenti.get(i).getSentimentTag().equalsIgnoreCase("neg"))
		{
			System.out.println("Sentence: "+Constant.modelIdentifierListSenti.get(i).getSentence()+"\nActual Outcome: "+Constant.actualIdentifierListSenti.get(i).getSentimentTag()+"\nModel Outcome: "+Constant.modelIdentifierListSenti.get(i).getSentimentTag());
			FalsePositive++;
		}
		
		else if(Constant.modelIdentifierListSenti.get(i).getSentimentTag().equalsIgnoreCase("neg")&&Constant.actualIdentifierListSenti.get(i).getSentimentTag().equalsIgnoreCase("neg"))
		{
       //  System.out.println("Sentence: "+Constant.modelIdentifierListSenti.get(i).getSentence()+"\nActual Outcome: "+Constant.actualIdentifierListSenti.get(i).getSentimentTag()+"\nModel Outcome: "+Constant.modelIdentifierListSenti.get(i).getSentimentTag());
			TrueNegative++;
		}
		
		else
		{
		//	System.out.println("Sentence: "+Constant.modelIdentifierListSenti.get(i).getSentence()+"\nActual Outcome: "+Constant.actualIdentifierListSenti.get(i).getSentimentTag()+"\nModel Outcome: "+Constant.modelIdentifierListSenti.get(i).getSentimentTag());
			truePositive++; 
		}
	}
}

}
