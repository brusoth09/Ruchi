package com.ruchi.engine.preprocessing;

import net.didion.jwnl.data.Word;
import uk.ac.shef.wit.simmetrics.similaritymetrics.*;

/**
 * Created by brusoth on 12/17/2014.
 */
public class WordDistance {
    AbstractStringMetric metric, metric1, metric2, metric3;
    
    public void load(){
        metric = new CosineSimilarity();
        metric1 = new EuclideanDistance();
        metric2= new MongeElkan();
        metric3 = new Levenshtein();
    }

    public float getCosineSimilarity(String word1,String word2){
        return metric.getSimilarity(word1, word2);
    }

    public float getEuclideanSimilarity(String word1, String word2){
        return metric1.getSimilarity(word1, word2);
    }

    public float getMongeElkanSimilarity(String word1, String word2){
        return metric2.getSimilarity(word1, word2);
    }

    public float getLevenshteinSimilarity(String word1, String word2){
        return metric3.getSimilarity(word1, word2);
    }

    public static void main(String args[]){
        WordDistance wd=new WordDistance();
        wd.load();
        System.out.println(wd.getMongeElkanSimilarity("pancakes", "pancake"));
        System.out.println(wd.getLevenshteinSimilarity("taco salad", "taco"));
        System.out.println(wd.getCosineSimilarity("taco salad", "ca"));
        System.out.println(wd.getEuclideanSimilarity("taco salad", "ca"));
    }
}
