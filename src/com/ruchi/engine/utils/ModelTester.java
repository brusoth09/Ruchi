package com.ruchi.engine.utils;

import com.ruchi.engine.database.DatabaseConnector;
import com.ruchi.engine.foodextraction.Extraction;
import com.ruchi.engine.foodextraction.FoodClassifier;
import com.ruchi.engine.foodextraction.OpenNLP;
import com.ruchi.engine.models.Sentence;
import com.ruchi.engine.preprocessing.SentenceProcessing;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * Created by brusoth on 12/27/2014.
 */
public class ModelTester {
    private OpenNLP sent;
    Extraction exe;
    String original;
    ArrayList<Sentence> list;
    FoodClassifier wc;
    public static void main(String args[]){
//        DatabaseConnector db=new DatabaseConnector();
//        db.connect();
//        ArrayList<String> food_list=new ArrayList<String>();
//        db.getFoodNames(food_list);
//        System.out.println(food_list.size());
//        TextEditors.writeFoodNamesToTextFile(food_list);

        //new Tester().getSentencesFromReviews();
        new Tester().TestReview();
    }

    public void testModel(){
        BufferedReader br = null;
        sent=new OpenNLP();
        sent.loadModel();
        exe=new Extraction();
        exe.load(sent);
        list=new ArrayList<Sentence>();
        wc=new FoodClassifier();

        try {

            String sCurrentLine;

            br = new BufferedReader(new FileReader("res/modeli.txt"));

            while ((sCurrentLine = br.readLine()) != null) {
                original=sCurrentLine.trim();
                if(original.length()>0) {
                    //predictfoods(sCurrentLine.trim());
                }
                else{
                    writeTextFile("###");
                }

            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (br != null)br.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        wc.classify();
    }

    public  void predictfoods(String sentence,String[] predictedvalues) {
        Sentence line = new Sentence(sentence);
        String output = "";
        sentence = sentence.replaceAll("\\.-", " ").replace("\\.", "");
        sentence = SentenceProcessing.remove_symbols(sentence);
        String[] tokens = predictedvalues;
        List<String> predictions = new ArrayList<String>(Arrays.asList(tokens));
        String[] toks = sent.getWordTokens(sentence);
        String[] toks1 = sent.getWordTokens(sentence.replace(",", " "));
        String[] tags = sent.getWordTags(toks1);
        ArrayList<String> features = sent.findFeatures(tags, toks);
        int processed = 0;
        Iterator<String> iter;
        System.out.print(sentence);
        iter = predictions.iterator();
        for (String fea : features) {

            while (iter.hasNext()) {
                if (fea.contains(iter.next())) {
                    output = output.concat(fea).concat(",");
                    line.addFood(fea);
                    wc.addFood(fea);
                    iter.remove();
                    break;
                }

            }
        }
    }

    public static void writeTextFile(String sentence)
    {
        try(PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("res/model2result", true)))) {
            out.println(sentence);
        }catch (IOException e) {
            System.out.println(e);
        }
    }
}
