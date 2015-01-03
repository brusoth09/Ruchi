package com.ruchi.engine.foodextractionEvaluvation;

import java.util.HashSet;
import java.util.Set;

public class FoodExtractionChecker {

public static int total;
public static float truePositive;
public static float FalseNegative;
public static float FalsePositive;
public static float TrueNegative;

/*check count*/
 public static void countElements()
 {
	total=Constant.actualIdentifierList.size();
	System.out.println("Manual Tagged Foods-----Model Tagged Foods--------Intersect Food-------Sentences");
	for(int i=0;i<Constant.modelIdentifierList.size();i++)
	{
		//System.out.print(i+">");
		if(Constant.modelIdentifierList.get(i).getIdentifiedFoods().size()==0&&Constant.actualIdentifierList.get(i).getIdentifiedFoods().size()!=0)
		{
			System.out.println("Sentence: "+Constant.modelIdentifierList.get(i).getReviewSentence()+"\nActual food List:   "+Constant.actualIdentifierList.get(i).getIdentifiedFoods()+"\nModel Food List:    "+Constant.modelIdentifierList.get(i).getIdentifiedFoods()+"\nintersect :\n");
			FalseNegative++;
		}
		
		else if(Constant.modelIdentifierList.get(i).getIdentifiedFoods().size()!=0&&Constant.actualIdentifierList.get(i).getIdentifiedFoods().size()==0)
		{
		System.out.println("Sentence: "+Constant.modelIdentifierList.get(i).getReviewSentence()+"\nActual food List:   "+Constant.actualIdentifierList.get(i).getIdentifiedFoods()+"\nModel Food List:    "+Constant.modelIdentifierList.get(i).getIdentifiedFoods()+"\nintersect :\n");
			FalsePositive++;
		}
		
		else if(Constant.modelIdentifierList.get(i).getIdentifiedFoods().size()==0&&Constant.actualIdentifierList.get(i).getIdentifiedFoods().size()==0)
		{
	//	System.out.println("Sentence: "+Constant.modelIdentifierList.get(i).getReviewSentence()+"\nActual food List:   "+Constant.actualIdentifierList.get(i).getIdentifiedFoods()+"\nModel Food List:    "+Constant.modelIdentifierList.get(i).getIdentifiedFoods()+"\nintersect :\n");
			TrueNegative++;
		}
		
		else
		{
			 Set<String> intersect = new HashSet<String>(Constant.modelIdentifierList.get(i).getIdentifiedFoods());
			    intersect.retainAll(Constant.actualIdentifierList.get(i).getIdentifiedFoods());
			    // System.out.println(intersect.size()); // prints "2"
			    //System.out.println(intersect); // prints "[milan, dingo]"

			    if(Constant.modelIdentifierList.get(i).getIdentifiedFoods().size()==Constant.actualIdentifierList.get(i).getIdentifiedFoods().size()&&intersect.size()==Constant.actualIdentifierList.get(i).getIdentifiedFoods().size())
			    {
			    	truePositive++;
			    	System.out.println("Sentence: "+Constant.modelIdentifierList.get(i).getReviewSentence()+"\nActual food List:   "+Constant.actualIdentifierList.get(i).getIdentifiedFoods()+"\nModel Food List:    "+Constant.modelIdentifierList.get(i).getIdentifiedFoods()+"\nIntersect Food List:"+intersect);
			    	System.out.println();
			    }
			    else if(intersect.size()!=Constant.actualIdentifierList.get(i).getIdentifiedFoods().size()||intersect.size()!=Constant.modelIdentifierList.get(i).getIdentifiedFoods().size())
			    {
			    	
			    	truePositive=truePositive+(float)intersect.size()/(Constant.actualIdentifierList.get(i).getIdentifiedFoods().size()+Constant.modelIdentifierList.get(i).getIdentifiedFoods().size()-intersect.size());
			    	FalseNegative=FalseNegative+(float)(Constant.actualIdentifierList.get(i).getIdentifiedFoods().size()-intersect.size())/(Constant.actualIdentifierList.get(i).getIdentifiedFoods().size()+Constant.modelIdentifierList.get(i).getIdentifiedFoods().size()-intersect.size());
			    	FalsePositive=FalsePositive+(float)(Constant.modelIdentifierList.get(i).getIdentifiedFoods().size()-intersect.size())/(Constant.actualIdentifierList.get(i).getIdentifiedFoods().size()+Constant.modelIdentifierList.get(i).getIdentifiedFoods().size()-intersect.size());
			    	System.out.println("Sentence: "+Constant.modelIdentifierList.get(i).getReviewSentence()+"\nActual food List:   "+Constant.actualIdentifierList.get(i).getIdentifiedFoods()+"\nModel Food List:    "+Constant.modelIdentifierList.get(i).getIdentifiedFoods()+"\nIntersect Food List:"+intersect);
			    	System.out.println();
			    }
			    else
			    {
			    	// nothing come here!!!
			    	System.out.println("Sentence: "+Constant.modelIdentifierList.get(i).getReviewSentence()+"\nActual food List:   "+Constant.actualIdentifierList.get(i).getIdentifiedFoods()+"\nModel Food List:    "+Constant.modelIdentifierList.get(i).getIdentifiedFoods()+"\nIntersect Food List:"+intersect);
			    	System.out.println();
			    }
		}
	}
 }

}
