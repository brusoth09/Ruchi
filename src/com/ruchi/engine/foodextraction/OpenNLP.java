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
import java.util.Arrays;

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
import com.ruchi.engine.utils.AdjectiveNoun;
import com.ruchi.engine.utils.TextEditors;

/**
 * Created by brusoth on 11/15/2014.
 */
public class OpenNLP {
	//jenkens test
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
            person_model= new FileInputStream("res/en-food1.train");		//load trained food stream	
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

            //fs=new FoodSearch();
            //fs.loadFood();
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
    
    public ArrayList<String> getSentence(String review)			//sentence detector
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

    public String[] getTokens(String sentence)			//tokenizer to tokenize sentence into words
    {
            String tokens[] = tokenizer.tokenize(sentence);
            return tokens;
    }


    public Span[] getNames(String[] tokens) {
        Span[] nameSpans = nameFinder.find(tokens);		//predict names
        return nameSpans;

    }

    public void tagSentence(String sentence) throws IOException {		//create tags and save it to text file

        ObjectStream<String> lineStream = new PlainTextByLineStream(new StringReader(sentence));
        String line;
        String whitespaceTokenizerLine[] = null;

        String[] tags = null;
        while ((line = lineStream.read()) != null) {
            whitespaceTokenizerLine = WhitespaceTokenizer.INSTANCE
                    .tokenize(line);
            tags = tagger.tag(whitespaceTokenizerLine);
        }

        Span[] span = chunkerME.chunkAsSpans(whitespaceTokenizerLine, tags);
        //System.out.println(Arrays.toString(span));
        String[] array=Span.spansToStrings(span, whitespaceTokenizerLine);
        //System.out.println(Arrays.toString(array));
        String sent="";

        int i=0;
        for(String s:array)
        {
        	if(span[i].getType().equalsIgnoreCase("NP"))		//only considering noun phrases
        		sent=sent.concat(fs.search(s)+" ");
        	else
        		sent=sent.concat(s+" ");
        	i++;
        }
        if(sent.contains("<START:food>"))
            TextEditors.writeTextFile(sent);					//write to text file

    }

    public String[] getWordTokens(String line){					//generate tokens
        String whitespaceTokenizerLine[] = null;
        whitespaceTokenizerLine = WhitespaceTokenizer.INSTANCE.tokenize(line);
        return whitespaceTokenizerLine;
    }

    public String[] getWordTags(String[] tokens){			//generate tags
        String[] tags = null;
        tags = tagger.tag(tokens);
        return tags;
    }

    public ArrayList<String>  findFeatures(String[] tags,String[] tokens){	//find noun features without using chunker
        ArrayList<String> feature_list=new ArrayList<String>();
        ArrayList<String> list=new ArrayList<String>();
        for(int i=0;i<tags.length;i++)
        {
            if(tags[i].trim().equalsIgnoreCase("NN")||tags[i].trim().equalsIgnoreCase("NNS")||tags[i].trim().equalsIgnoreCase("NNP")||tags[i].trim().equalsIgnoreCase("NNPS")){
                list.add(tokens[i]);										//only considering nouns and proper nouns
            }
            else if(tags[i].trim().equalsIgnoreCase("JJ") && AdjectiveNoun.checkitem(tokens[i].toLowerCase())){
            	list.add(tokens[i]);										//only considered some adjectives
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
    
    public ArrayList<String> findFeatureUsingChunker(String line){
    	ArrayList<String> feature_list=new ArrayList<String>();
    	String[] whitespaceTokenizerLine = WhitespaceTokenizer.INSTANCE
                .tokenize(line);
        String[] tags = tagger.tag(whitespaceTokenizerLine);
        Span[] span = chunkerME.chunkAsSpans(whitespaceTokenizerLine, tags);
        for(Span s:span){
        	if(s.getType().equals("NP")){
        		feature_list.add(s.toString());
        	}
        }
    	return feature_list;
    }

    public static void main(String args[]) throws IOException {
        OpenNLP sent=new OpenNLP();
        sent.loadModel();
        String line="We tried delicious french dumpling soup in two consecutive nights";
        String line1=" The waffle was okay.  It was filling and tasty, but it was lacking that crisp exterior that makes these big waffles so good.  The bacon was a little overcooked for my taste, but not bad.";
        String[] tokens=sent.getTokens(line1);
        Span nameSpans[] = sent.getNames(tokens);
        System.out.println("OpenNLP NER predictions...................");
        for(String predictions:Span.spansToStrings(nameSpans,tokens)){
        	System.out.println(predictions);
        }
        String[] array=Span.spansToStrings(nameSpans,tokens);
        System.out.println(Arrays.toString(sent.getWordTags(tokens)));
        System.out.println("POS Tagger predictions...................");
        for(String output:sent.findFeatures(sent.getWordTags(tokens),tokens))
        	System.out.println(output);
        //sent.tagSentence(line);
    }
}

