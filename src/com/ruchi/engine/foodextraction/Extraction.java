package com.ruchi.engine.foodextraction;

import com.ruchi.engine.database.DatabaseConnector;
import com.ruchi.engine.preprocessing.Stemming;
import com.ruchi.engine.utils.SerializeObject;
import opennlp.tools.namefind.NameFinderME;
import opennlp.tools.namefind.NameSampleDataStream;
import opennlp.tools.namefind.TokenNameFinderModel;
import opennlp.tools.util.ObjectStream;
import opennlp.tools.util.PlainTextByLineStream;
import opennlp.tools.util.Span;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;


/**
 * Created by Brus panda on 12/2/2014.
 */
public class Extraction {
    DatabaseConnector db=new DatabaseConnector();
    OpenNLP sent;

    NameFinderME nfm;

    ArrayList<String> food_list=new ArrayList<String>();
    ArrayList<String> rest_list=new ArrayList<String>();

    public void load(OpenNLP sent){
        db.connect();
        db.getFoodNames(food_list);
        rest_list=db.getRestaurants();
        this.sent=sent;

    }

    public void readReviews()
    {
        System.out.print(rest_list.size());
        for(String rest:rest_list){


            ArrayList<String> review_set=db.getRestaurantReviews(rest.trim());


            for(String review:review_set)
            {
                ArrayList<String> sentences=sent.getSentence(review);
                int sent_num=1;
                for(String sentence:sentences){
                    String[] tokens=predict(sentence.trim());
                    String[] toks=sent.getWordTokens(sentence);
                    String[] tags=sent.getWordTags(toks);
                    ArrayList<String> features=sent.findFeatures(tags,toks);

                    for(String fea:features)
                    {
                        for(String pre:tokens)
                        {
                            if(fea.contains(pre)){
                                System.out.println(fea);
                                break;
                            }
                        }
                    }

                    sent_num++;
                }

            }

        }


    }

    public void train(){
        try {
            FileReader fileReader = new FileReader("res/review_train");
            ObjectStream fileStream = new PlainTextByLineStream(fileReader);
            ObjectStream sampleStream = new NameSampleDataStream(fileStream);
            TokenNameFinderModel model = NameFinderME.train("pt-br", "train", sampleStream, Collections.<String, Object>emptyMap());
            nfm = new NameFinderME(model);
        }
        catch (Exception e){
            System.out.println(e);
        }
    }

    public String[] predict(String line){
        String[] tokens=sent.getTokens(line);
        Span nameSpans[] = sent.getNames(tokens);
        String[] array=Span.spansToStrings(nameSpans,tokens);
        System.out.println(Arrays.toString(array));
        return array;
    }




    public static void main(String args[]){
        Extraction exe=new Extraction();
        exe.train();
        String[] output=exe.predict("###My boyfriend ordered the hawk eye egg omelet, it was light and fluffy, and the eggs themselves tasted like an array of wonderfull cheese.");
        System.out.println(Arrays.toString(output));
    }
}
