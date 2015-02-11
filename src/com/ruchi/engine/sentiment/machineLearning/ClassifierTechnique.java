package com.ruchi.engine.sentiment.machineLearning;

import weka.attributeSelection.InfoGainAttributeEval;
import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.classifiers.bayes.NaiveBayes;
import weka.classifiers.bayes.NaiveBayesUpdateable;
import weka.classifiers.rules.DecisionTable;
import weka.classifiers.rules.PART;
import weka.classifiers.trees.J48;
import weka.classifiers.trees.REPTree;
import weka.classifiers.trees.RandomForest;
import weka.classifiers.trees.RandomTree;
import weka.core.Attribute;
import weka.core.DenseInstance;
import weka.core.FastVector;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;
import weka.core.converters.LibSVMLoader;
import weka.core.tokenizers.NGramTokenizer;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.StringToWordVector;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;

import weka.classifiers.functions.MultilayerPerceptron;
import weka.classifiers.functions.SMO;
import weka.classifiers.functions.supportVector.RegSMO;;


public class ClassifierTechnique {

    Attribute segmentLength,lastWord,sentence,bagOfWords;
    Attribute ClassAttribute;
    ArrayList<String> featureVectorClassValues;
    ArrayList<Attribute> featureVectorAttributes;
    Instances TrainingSet;
    Instances TestingSet;
    Hashtable table;
    SentiWordNet sentiwordnet;
    int hashval=0;
    String pathToSWN = "IOfolder/senti.txt";

     

    public static void main(String[] args) throws IOException {
    String testingDataFileLocation= "IOfolder/sentiment_test_set.txt";
    String trainingDataFileLocation="IOfolder/sentiment_train_set.txt";
    ClassifierTechnique ct=  new ClassifierTechnique();
    ct.classify(trainingDataFileLocation,testingDataFileLocation);
    }
    
    public ClassifierTechnique(){

        table=new Hashtable<String,Integer>();

        // Declare numeric feature vector
        segmentLength = new Attribute("segmentLength");
        lastWord= new Attribute("lastWord");
        bagOfWords=new Attribute("bagOfWords"); //assign number to each word. 
        
        //Decleare String attribute all words as feature vector but accuracy low, check the analysis in TextClassifier
        FastVector attributes = new FastVector();
        sentence= new Attribute("attr", (FastVector) null);
        

        // Declare the class attribute with positive and negative
        featureVectorClassValues=new ArrayList<String>();
        featureVectorClassValues.add("POS");
        featureVectorClassValues.add("NEG");
        

        ClassAttribute = new Attribute("theClass", featureVectorClassValues);

        // Declare the feature vector
        featureVectorAttributes = new ArrayList<Attribute>();
        featureVectorAttributes.add(segmentLength);
        featureVectorAttributes.add(lastWord);
        featureVectorAttributes.add(bagOfWords);
        //featureVectorAttributes all the words are in 
        
        featureVectorAttributes.add(ClassAttribute);
      
        // Create an empty training set
        TrainingSet = new Instances("Rel", featureVectorAttributes,1000);

        // Set class index
        TrainingSet.setClassIndex(featureVectorAttributes.size() - 1);

        // Create an empty testing set
        TestingSet = new Instances("Rel", featureVectorAttributes, 1000);
        // Set class index
        TestingSet.setClassIndex(featureVectorAttributes.size() - 1);
        try {
			sentiwordnet=new SentiWordNet(pathToSWN);
		} catch (IOException e) {
			e.printStackTrace();
		}


    }

    public void readFromFile(String location,Instances set) throws IOException {
    	
        BufferedReader br = new BufferedReader(new FileReader(new File(location)));
        String line;
        
        while ((line = br.readLine()) != null) {
           // parsing Data set from file
            String key=line.substring(0, 3);
            String val=line.substring(3).trim();
       	      String var = val.replace(".","");
            var=var.replace("//'","");
            var=var.replace("\"", "");
            var=var.replace("!", "");
            var=var.replace("'","");
            var=var.trim();
            String[] words = var.split("\\s+");

            Instance temp = new DenseInstance(4);

            /*Attribute messageAtt = set.attribute("Message");
            temp.setValue(messageAtt, messageAtt.addStringValue(var));*/
          temp.setValue(featureVectorAttributes.get(0),words.length);
          temp.setValue(featureVectorAttributes.get(1),getHashValue(words[words.length-1]));
          
          double averageValue=(double)usingOurBagOfModel( words);////(double)usingOurBagOfModel( words);(double)usingSentiwordnet(words);
     
          temp.setValue(featureVectorAttributes.get(2),averageValue);
          temp.setValue(featureVectorAttributes.get(featureVectorAttributes.size() - 1),key);
            
          temp.setDataset(TrainingSet);
          set.add(temp);
        }
        br.close();
    }
    


    public int usingOurBagOfModel(String[] words)
    {
    	 //bag of words are depend on Resturant Context
    	int averageValue=0;
        for(int i=0;i<words.length;i++){
      	  if (SentimentStrings.neg5.indexOf(words[i]) > -1) {
      		  averageValue-=5;
			} else if (SentimentStrings.neg4.indexOf(words[i]) > -1) {
				averageValue-=4;
			} else if (SentimentStrings.neg3.indexOf(words[i]) > -1) {
				averageValue-=3;
			} else if (SentimentStrings.neg2.indexOf(words[i]) > -1) {
				averageValue-=2;
			} else if (SentimentStrings.neg1.indexOf(words[i]) > -1) {
				averageValue-=1;
			}
      	  
      	  if (SentimentStrings.pos5.indexOf(words[i]) > -1) {
      		  averageValue+=5;
			} else if (SentimentStrings.pos4.indexOf(words[i]) > -1) {
				averageValue+=4;
			} else if (SentimentStrings.pos3.indexOf(words[i]) > -1) {
				averageValue+=3;
			} else if (SentimentStrings.pos2.indexOf(words[i]) > -1) {
				averageValue+=2;
			} else if (SentimentStrings.pos1.indexOf(words[i]) > -1) {
				averageValue+=1;
			}  
        }
        return averageValue;
    }
    
    public double usingSentiwordnet(String[] words)
    {
    	 //sentiwordnet
    	double averageValue=0.0;
        for(int i=0;i<words.length;i++){
        	averageValue+=SentiWordNet.extract(words[i], sentiwordnet);
        }
        return averageValue;
    }

    public void classify(String trainingFile,String testingFile) {

        try {
        	readFromFile(trainingFile,TrainingSet);
        	readFromFile(testingFile,TestingSet);
            
            //TO-Do change the model different model SMO, , MultilayerPerceptron, PART,DecisionTable,RandomForest,J48
        	Classifier[] classifiers={new  DecisionTable(), new PART(),new RandomForest(), new MultilayerPerceptron(),new J48(),new REPTree(), new SMO(), new NaiveBayes(), new RandomTree() };
        	String[] names={"DecisionTable", "PART","RandomForest","MultilayerPerceptron","J48","REPTree","SMO", "NaiveBayes","RandomTree"};
        	for(int j=0;j<classifiers.length;j++){
        	Classifier  cModel = classifiers[j];
            cModel.buildClassifier(TrainingSet);

            Evaluation eTest = new Evaluation(TrainingSet);
            
            eTest.evaluateModel(cModel, TestingSet);
            
          
           double[] prediction=cModel.distributionForInstance(TestingSet.get(2));

            //output predictions
            for(int i=0; i<prediction.length; i=i+1)
            {
                System.out.println("Probability of class "+
                		TestingSet.classAttribute().value(i)+
                                   " : "+Double.toString(prediction[i]));
            }
            //print out the results
            System.out.println("========================================Result for "+names[j]+"=============================");
            System.out.println("Results for "+this.getClass().getSimpleName());
            String strSummary = eTest.toSummaryString();
            System.out.println(strSummary);

            System.out.println("F-measure : "+eTest.weightedFMeasure());
            System.out.println("precision : "+eTest.weightedPrecision());
            System.out.println("recall : "+eTest.weightedRecall());
            System.out.println("=====================================================================");

        }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    
    //assign each word to integer mapper
    int getHashValue(String word){

        if(table.containsKey(word)){
            return (Integer)table.get(word);
        }else{
            table.put(word,hashval++);
            return getHashValue(word);
        }
        
    

    }

    //unit Test case
    public void setFeatureVectorAttributes(  ArrayList<Attribute> featureVectorAttribute){
    	featureVectorAttributes=featureVectorAttribute;
    }

    public ArrayList<Attribute> getFeatureVectorAttributes( ){
    	return featureVectorAttributes;
    }
}