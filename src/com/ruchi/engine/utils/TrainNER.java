package com.ruchi.engine.utils;

import opennlp.tools.namefind.NameFinderME;
import opennlp.tools.namefind.NameSampleDataStream;
import opennlp.tools.namefind.TokenNameFinderModel;
import opennlp.tools.util.ObjectStream;
import opennlp.tools.util.PlainTextByLineStream;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Serializable;
import java.util.Collections;

/**
 * Created by brusoth on 12/13/2014.
 */
public class TrainNER implements Serializable{
    NameFinderME nfm;
    public void train(){
        FileReader fileReader = null;
        try {
            fileReader = new FileReader("res/review_train");
            ObjectStream fileStream = new PlainTextByLineStream(fileReader);
            ObjectStream sampleStream = new NameSampleDataStream(fileStream);
            TokenNameFinderModel model = NameFinderME.train("pt-br", "train", sampleStream, Collections.<String, Object>emptyMap());
            nfm = new NameFinderME(model);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public NameFinderME getobject(){
        return nfm;
    }
}
