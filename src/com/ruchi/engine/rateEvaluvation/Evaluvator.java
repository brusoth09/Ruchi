package com.ruchi.engine.rateEvaluvation;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
/*
 * This class for Evaluvate the rating model 
 * Confusion matrix of 1-5 rate between humman tagged vs model tagged
 */
public class Evaluvator {
List<ActualRate> actualList=new ArrayList<ActualRate>();
List<ModelTagRate> modelTagList=new ArrayList<ModelTagRate>();
double TP = 0.0,FN = 0.0,FP = 0.0,TN=0.0, accuracy,precison,recall, f_measure;

public static void main(String[] a)
{
	Evaluvator eva=new Evaluvator();
	try {
		eva.add();
	} catch (NumberFormatException e) {
		// NumberFormatException
		e.printStackTrace();
	} catch (IOException e) {
		// IOException
		e.printStackTrace();
	}
	eva.calculate();
	eva.printResult();
	
}
public  void add() throws NumberFormatException, IOException
{
/*	ActualRate actualRate=new ActualRate();
	actualRate.setFoodName("pizza");
	actualRate.setRate(2);
	actualList.add(actualRate);
	ModelTagRate modelTagRate=new ModelTagRate();
	modelTagRate.setFoodName("pizza");
	actualRate.setRate(2);
	modelTagList.add(modelTagRate);*/
	BufferedReader br = new BufferedReader(new FileReader(new File("IOfolder/gg.txt")));
    String line;
    
    while ((line = br.readLine()) != null) {
    	
        if(line.startsWith("**"))
        {
        	
        		String[] st=line.split("###");
        		ActualRate actualRate1=new ActualRate();
        		ModelTagRate modelTagRate1=new ModelTagRate();
        		actualRate1.setFoodName(st[0]);
        		modelTagRate1.setFoodName(st[0]);
        		double d = Double.parseDouble(st[1]);
        		modelTagRate1.setRate((int) d);
        		d = Double.parseDouble(st[2]);
        		actualRate1.setRate((int) d);
        		modelTagList.add(modelTagRate1);
        		actualList.add(actualRate1);
        	      
        }
        	
    }
    br.close();

}

public void calculate()
{
	
	if(actualList.size()!=modelTagList.size())
	 System.out.println("Error");
		
	for(int i=0;i<actualList.size();i++)
	{
		ActualRate actualRate=actualList.get(i);
		ModelTagRate modelTagRate=modelTagList.get(i);
		if(actualRate.getFoodName().equalsIgnoreCase(modelTagRate.getFoodName()))
		{
			int aRate=actualRate.getRate();
			int mRate=modelTagRate.getRate();
			
			if((aRate>3&&mRate>3))
			{
				if(aRate==mRate)
				{
					TP+=1;
				}
				else
				{
					TP+=0.75;
				
				}
			}
			else if(aRate>3&&mRate<=3){
				
					FN+=.25*(aRate-mRate);
				
			}
			
			else if(aRate<=3&&mRate>3)
			{
				FP+=.25*((mRate-aRate));
			}
			else{
				if(aRate==mRate)
					TN+=1;
				else if(Math.abs(aRate-mRate)==1)
					TN+=0.75;
				else if(Math.abs(aRate-mRate)==2)
					TN+=0.5;
			}
		}
			
		
	}
	precison=(TP)/(TP+FP);
	recall=TP/(TP+FN);
	f_measure=(2*precison*recall)/(precison+recall);
}

public void printResult()
{
	System.out.println("total "+actualList.size()+"\nTP  "+TP+"\nFP "+FP);
	System.out.println("\nprecison "+precison+"\nrecall "+recall+"\nfmeasure "+f_measure);
}
}
