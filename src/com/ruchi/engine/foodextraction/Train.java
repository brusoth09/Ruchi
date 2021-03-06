package com.ruchi.engine.foodextraction;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collections;

import opennlp.tools.namefind.NameFinderME;
import opennlp.tools.namefind.NameSample;
import opennlp.tools.namefind.NameSampleDataStream;
import opennlp.tools.namefind.TokenNameFinderModel;
import opennlp.tools.util.ObjectStream;
import opennlp.tools.util.PlainTextByLineStream;

/**
 * Created by brusoth on 11/25/2014.
 */
public class Train {
    TokenNameFinderModel model;
    public static void main(String args[]) throws IOException {
        try {
            new Train().train();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void train() throws IOException {
        FileReader fileReader = new FileReader("res/training");							//read trained text
        ObjectStream<String> fileStream = new PlainTextByLineStream(fileReader);		
        ObjectStream<NameSample> sampleStream = new NameSampleDataStream(fileStream);
        model = NameFinderME.train("pt-br", "train", sampleStream, Collections.<String, Object>emptyMap());	//train own ner model
        saveFile();
    }

    public void saveFile(){			//serialize ner object

        try {
            BufferedOutputStream modelOut = new BufferedOutputStream(new FileOutputStream("src/en-food.train"));
            model.serialize(modelOut);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
