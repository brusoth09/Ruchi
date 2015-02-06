package com.ruchi.engine.foodextraction;
/**
 * Created by brusoth on 11/15/2014.
 */
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.util.ArrayList;

import opennlp.tools.chunker.ChunkerME;
import opennlp.tools.chunker.ChunkerModel;
import opennlp.tools.cmdline.PerformanceMonitor;
import opennlp.tools.cmdline.postag.POSModelLoader;
import opennlp.tools.namefind.NameFinderME;
import opennlp.tools.namefind.TokenNameFinderModel;
import opennlp.tools.postag.POSModel;
import opennlp.tools.postag.POSTaggerME;
import opennlp.tools.sentdetect.SentenceDetectorME;
import opennlp.tools.sentdetect.SentenceModel;
import opennlp.tools.tokenize.Tokenizer;
import opennlp.tools.tokenize.TokenizerME;
import opennlp.tools.tokenize.TokenizerModel;
import opennlp.tools.tokenize.WhitespaceTokenizer;
import opennlp.tools.util.ObjectStream;
import opennlp.tools.util.PlainTextByLineStream;
import opennlp.tools.util.Span;

import com.google.common.base.Joiner;
import com.ruchi.engine.utils.TextEditors;

/**
 * Created by brusoth on 11/15/2014.
 */
public class OpenNLP {

    private InputStream sent_model,token_model,person_model,is;
    private POSModel model;
    private PerformanceMonitor perfMon;
    private POSTaggerME tagger;
    private SentenceModel sentence_model;
    private ChunkerModel cModel;
    private ChunkerME chunkerME;
    private SentenceDetectorME detect;
    private FoodSearch fs;
    private Tokenizer tokenizer;
    private NameFinderME nameFinder;

    public void loadModel()
    {
        try {
            sent_model = new FileInputStream("en-sent.zip");			//load trained file for sentence detection
            token_model = new FileInputStream("en-token.zip");			//load trained model for token stream
            person_model= new FileInputStream("res/en-food.train");		//load trained food stream	
            model = new POSModelLoader().load(new File("en-pos-maxent.zip"));//load POS tagger stream
            is = new FileInputStream("en-chunker.zip");					//load chunker stream

            TokenNameFinderModel modelers = new TokenNameFinderModel(person_model);
            nameFinder = new NameFinderME(modelers);

            perfMon = new PerformanceMonitor(System.err, "sent");
            tagger = new POSTaggerME(model);
            perfMon.start();

            sentence_model=new SentenceModel(sent_model);
            detect = new SentenceDetectorME(sentence_model);

            cModel = new ChunkerModel(is);
            chunkerME = new ChunkerME(cModel);

            TokenizerModel model = new TokenizerModel(token_model);
            tokenizer = new TokenizerME(model);

            fs=new FoodSearch();
            fs.loadFood();
        }
        catch(Exception e)
        {
            System.out.println(e);
        }
    }
    
    
    public void unloadModel(){
        try {
            sent_model.close();
            token_model.close();
            person_model.close();
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public ArrayList<String> getSentence(String review)
    {
        ArrayList<String> list=new ArrayList<String>();
        try {
            String sentences[] = detect.sentDetect(review);

            for(String s:sentences)
            {
                list.add(s);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return list;
    }

    public String[] getTokens(String sentence)
    {
            String tokens[] = tokenizer.tokenize(sentence);
            return tokens;
    }


    public Span[] getNames(String[] tokens) {
        Span[] nameSpans = nameFinder.find(tokens);
        return nameSpans;

    }

    public void tagSentence(String sentence) throws IOException {

        ObjectStream<String> lineStream = new PlainTextByLineStream(new StringReader(sentence));
        String line;
        String whitespaceTokenizerLine[] = null;

        String[] tags = null;
        while ((line = lineStream.read()) != null) {
            whitespaceTokenizerLine = WhitespaceTokenizer.INSTANCE
                    .tokenize(line);
            tags = tagger.tag(whitespaceTokenizerLine);
            //POSSample sample = new POSSample(whitespaceTokenizerLine, tags);
        }

        //chunker implementation
        //String result[] = chunkerME.chunk(whitespaceTokenizerLine, tags);
        Span[] span = chunkerME.chunkAsSpans(whitespaceTokenizerLine, tags);
        
        //System.out.println(Arrays.toString(tags));
        String[] array=Span.spansToStrings(span, whitespaceTokenizerLine);
        String sent="";

        for(String s:array)
        {
           sent=sent.concat(fs.search(s)+" ");
        }
        if(sent.contains("<START:food>"))
            TextEditors.writeTextFile(sent);

    }

    public String[] getWordTokens(String line){
        String whitespaceTokenizerLine[] = null;
        whitespaceTokenizerLine = WhitespaceTokenizer.INSTANCE.tokenize(line);
        return whitespaceTokenizerLine;
    }

    public String[] getWordTags(String[] tokens){
        String[] tags = null;
        tags = tagger.tag(tokens);
        return tags;
    }

    public ArrayList<String>  findFeatures(String[] tags,String[] tokens){
        ArrayList<String> feature_list=new ArrayList<String>();
        ArrayList<String> list=new ArrayList<String>();
        for(int i=0;i<tags.length;i++)
        {
            if(tags[i].trim().equalsIgnoreCase("NN")||tags[i].trim().equalsIgnoreCase("NNS")||tags[i].trim().equalsIgnoreCase("NNP")||tags[i].trim().equalsIgnoreCase("NNPS")){
                list.add(tokens[i]);
            }
            else{
                if(list.size()>0)
                {
                    String[] words=Joiner.on(" ").join(list).split(",");   //identify comma separated items as single items
                    for(String word:words)
                    {
                        feature_list.add(word.replaceAll("[^\\w\\s\\']","").trim());
                    }

                }
                list=new ArrayList<String>();
            }
        }
        //this is for last iteration of the loop
        if(list.size()>1)
        {
            String[] words=Joiner.on(" ").join(list).split(",");
            for(String word:words)
            {
                feature_list.add(word.replaceAll("[^\\w\\s\\']","").trim());
            }
        }
        
        return feature_list;
    }

    public static void main(String args[]) throws IOException {
        OpenNLP sent=new OpenNLP();
        sent.loadModel();
        sent.tagSentence("We tried Wicked Spoon and Bacchanal in two consecutive nights");
    }
}

