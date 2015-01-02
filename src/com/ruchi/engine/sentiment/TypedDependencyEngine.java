package com.ruchi.engine.sentiment;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import com.ruchi.engine.models.Sentence;
import com.ruchi.engine.ranking.RankingAlgorithm;
import com.ruchi.engine.sentiment.Parser.Parse;

import edu.stanford.nlp.ling.BasicDocument;
import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.ling.IndexedWord;
import edu.stanford.nlp.ling.Word;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.sentiment.SentimentCoreAnnotations;
import edu.stanford.nlp.trees.TypedDependency;
import edu.stanford.nlp.util.CoreMap;

public class TypedDependencyEngine {

	private static ArrayList<String> relationList = new ArrayList<String>();
	private static StanfordCoreNLP pipeline;
	private static StanfordCoreNLP tokenizer;

	public TypedDependencyEngine() {
		Properties pipelineProps = new Properties();
		Properties tokenizerProps = new Properties();
		pipelineProps.setProperty("sentiment.model", "model.ser.gz");
		pipelineProps.setProperty("annotators", "parse, sentiment");
		pipelineProps.setProperty("enforceRequirements", "false");
		tokenizerProps.setProperty("annotators", "tokenize, ssplit");
		pipeline = new StanfordCoreNLP(pipelineProps);
		tokenizer = new StanfordCoreNLP(tokenizerProps);
		Parser.init();
		Analyzer.init();
		relationList.add("amod");
		relationList.add("nsubj");
		relationList.add("advmod");
	}

	public static void main(String args[]) throws Exception {
		new TypedDependencyEngine();
		// BufferedReader reader = new BufferedReader(new FileReader(
		// "test_sentences2_sanjith.txt"));
		// File out_file = new File("sentiment_out_sanjith.txt");
		// out_file.createNewFile();
		// FileWriter writer = new FileWriter(out_file);
		// String line;
		// while ((line = reader.readLine()) != null) {
		// String sentiTyped = sentiTyped(line);
		// writer.append(sentiTyped).append("\n");
		// }
		// reader.close();
		// writer.flush();
		// writer.close();
		String inText = "I had pizza it was awesome and chinese pasta was awful";
		Sentence sentence = new Sentence(inText);
		Integer[] location = new Integer[2];
		location[0] = 2;
		location[1] = 1;
		Integer[] location2 = new Integer[2];
		location2[0] = 7;
		location2[1] = 2;
		sentence.addFood("pizza", location);
		sentence.addFood("pasta", location2);
		HashMap<String, Double> foodSentiment = foodSentiment(sentence);
		sentence.setFoodSentiment(foodSentiment);
		System.out.println(foodSentiment);
	}

	public static HashMap<String, Double> foodSentiment(Sentence sentence) {
		HashMap<String, Double> foodSentiment = new HashMap<String, Double>();
		List<List<MyWord>> myWordLists = sentiTyped(sentence.getSentence());
		HashMap<String, Integer[]> foodMap = sentence.getFoodMap();
		Set<String> keySet = foodMap.keySet();
		String sentenceLine = sentence.getSentence();
		for (String food : keySet) {
			Integer[] location = foodMap.get(food);
			Double score = null;
			ArrayList<Integer> scores = new ArrayList<Integer>();
			for (List<MyWord> myWords : myWordLists) {
				for (MyWord myWord : myWords) {
					if (myWord.getIndex() >= location[0] + 1
							&& myWord.getIndex() <= location[0] + location[1]) {
						String generatedSentence = generateSentence(myWords);
						String polarity = sentimentAnalysis(generatedSentence).get(0);
						System.out.println(generatedSentence);
						if(polarity.equalsIgnoreCase("Positive")){
							scores.add(RankingAlgorithm.positiveScore(generatedSentence));
						}
						else {
							scores.add(RankingAlgorithm.negativeScore(generatedSentence));
						}
						break;
					}
				}
			}
			if(scores.isEmpty()){
				String polarity = sentimentAnalysis(sentenceLine).get(0);
				if(polarity.equalsIgnoreCase("Positive")){
					score = (double) RankingAlgorithm.positiveScore(sentenceLine);
				}
				else{
					score = (double) RankingAlgorithm.negativeScore(sentenceLine);
				}
				
			}
			else{
				score = RankingAlgorithm.avgScore(scores);
			}
			foodSentiment.put(food, score);
		}
		return foodSentiment;
	}

	public static ArrayList<String> sentimentAnalysis(String review) {
		ArrayList<String> sentiments = new ArrayList<String>();
		Annotation annotation = tokenizer.process(review);
		pipeline.annotate(annotation);
		for (CoreMap sentence : annotation
				.get(CoreAnnotations.SentencesAnnotation.class)) {
			String polarity = sentence
					.get(SentimentCoreAnnotations.ClassName.class);
			sentiments.add(polarity);
		}
		return sentiments;
	}

	public static List<List<MyWord>> sentiTyped(String inText) {
		StringBuffer sb = new StringBuffer();

		sb.append(inText).append("\n");
		List<List<Word>> theseSentences = segmentText(inText);
		List<ArrayList<TypedDependency>> tdLists = generateTypedDependency(theseSentences);
		// for (ArrayList<TypedDependency> tdList : tdLists) {
		// for (TypedDependency typedDependency : tdList) {
		// System.out.println(typedDependency);
		// }
		// }
		ArrayList<TypedDependency> tdList = tdLists.get(0);
		List<List<MyWord>> myWordLists = new ArrayList<List<MyWord>>();
		List<TypedDependency> discoveredTdList = new ArrayList<TypedDependency>();
		for (TypedDependency typedDependency : tdList) {
			if (discoveredTdList.contains(typedDependency)) {
				continue;
			}
			String grString = typedDependency.reln().toString();
			IndexedWord gov = typedDependency.gov();
			IndexedWord dep = typedDependency.dep();
			if (grString.equals("nsubj")
					&& gov.tag().length() >= 2
					&& (gov.tag().substring(0, 2).equals("JJ") || dep.tag()
							.substring(0, 2).equals("JJ"))) {
				ArrayList<MyWord> myWordList = new ArrayList<MyWord>();
				MyWord govWord = new MyWord(gov);
				MyWord depWord = new MyWord(dep);
				myWordList.add(depWord);
				myWordList.add(govWord);
				for (TypedDependency td : tdList) {
					if (td.reln().toString().equalsIgnoreCase("root")
							|| td.reln().toString().equalsIgnoreCase("conj")) {
						continue;
					}
					if (td.gov().equals(gov) || td.gov().equals(dep)) {
						MyWord newWord = new MyWord(td.dep());
						if (!myWordList.contains(newWord)) {
							myWordList.add(newWord);
						}
						if (relationList.contains(td.reln().toString())) {
							discoveredTdList.add(td);
						}
					} else if (td.dep().equals(gov) || td.dep().equals(dep)) {
						MyWord newWord = new MyWord(td.gov());
						if (!myWordList.contains(newWord)) {
							myWordList.add(newWord);
						}
						if (relationList.contains(td.reln().toString())) {
							discoveredTdList.add(td);
						}
					}
				}
				myWordLists.add(myWordList);
			} else if (grString.equals("amod")) {
				ArrayList<MyWord> myWordList = new ArrayList<MyWord>();
				MyWord govWord = new MyWord(gov);
				MyWord depWord = new MyWord(dep);
				myWordList.add(depWord);
				myWordList.add(govWord);
				for (TypedDependency td : tdList) {
					if (td.reln().toString().equalsIgnoreCase("root")) {
						continue;
					}
					if (td.gov().equals(gov) || td.gov().equals(dep)) {
						MyWord newWord = new MyWord(td.dep());
						if (!myWordList.contains(newWord)) {
							myWordList.add(newWord);
						}
						if (relationList.contains(td.reln().toString())) {
							discoveredTdList.add(td);
						}
					} else if (td.dep().equals(gov) || td.dep().equals(dep)) {
						MyWord newWord = new MyWord(td.gov());
						if (!myWordList.contains(newWord)) {
							myWordList.add(newWord);
						}
						if (relationList.contains(td.reln().toString())) {
							discoveredTdList.add(td);
						}
					}
				}
				myWordLists.add(myWordList);
			} else if (grString.equals("advmod")) {
				ArrayList<MyWord> myWordList = new ArrayList<MyWord>();
				MyWord govWord = new MyWord(gov);
				MyWord depWord = new MyWord(dep);
				myWordList.add(depWord);
				myWordList.add(govWord);
				for (TypedDependency td : tdList) {
					if (td.reln().toString().equalsIgnoreCase("root")) {
						continue;
					}
					if (td.gov().equals(gov) || td.gov().equals(dep)) {
						MyWord newWord = new MyWord(td.dep());
						if (!myWordList.contains(newWord)) {
							myWordList.add(newWord);
						}
						if (relationList.contains(td.reln().toString())) {
							discoveredTdList.add(td);
						}
					} else if (td.dep().equals(gov) || td.dep().equals(dep)) {
						MyWord newWord = new MyWord(td.gov());
						if (!myWordList.contains(newWord)) {
							myWordList.add(newWord);
						}
						if (relationList.contains(td.reln().toString())) {
							discoveredTdList.add(td);
						}
					}
				}
				myWordLists.add(myWordList);
			}
		}
		if (myWordLists.isEmpty()) {
			ArrayList<String> sentiment = sentimentAnalysis(inText);
			// System.out.println(sentiment);
			sb.append(sentiment.get(0)).append("\n");
		}
		for (List<MyWord> list : myWordLists) {
			String generatedSentence = generateSentence(list);
			// System.out.println(generatedSentence);
			sb.append(generatedSentence).append("\n");
			ArrayList<String> sentiment = sentimentAnalysis(generatedSentence);
			// System.out.println(sentiment);
			sb.append(sentiment.get(0)).append("\n");
		}
		return myWordLists;
	}

	public static List<ArrayList<TypedDependency>> generateTypedDependency(
			List<List<Word>> theseSentences) {
		List<ArrayList<TypedDependency>> tdLists = new ArrayList<ArrayList<TypedDependency>>();
		for (List<Word> sentence : theseSentences) {
			Parse parse = Parser.parse(sentence);
			ArrayList<TypedDependency> tdList = (ArrayList<TypedDependency>) parse.second;
			tdLists.add(tdList);
		}
		return tdLists;
	}

	public static List<List<Word>> segmentText(String text) {
		BasicDocument<Word> basicDocument = BasicDocument.init(text);
		Pair<List<String>, List<List<Word>>> thisResult = Segmenter
				.getSentences(basicDocument);
		return thisResult.second;
	}

	public static String generateSentence(List<MyWord> myWordList) {
		Collections.sort(myWordList);
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < myWordList.size(); i++) {
			if (i == 0) {
				sb.append(myWordList.get(i).getValue());
			} else {
				sb.append(" ").append(myWordList.get(i).getValue());
			}
		}
		return sb.toString();
	}
}
