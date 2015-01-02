package com.ruchi.engine.sentiment;

import java.util.ArrayList;
import java.util.Properties;

import com.ruchi.engine.ranking.RankingAlgorithm;

import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.ling.CoreAnnotations.TextAnnotation;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.sentiment.SentimentCoreAnnotations;
import edu.stanford.nlp.util.CoreMap;

public class ReviewRating {
	private static StanfordCoreNLP pipeline;
	private static StanfordCoreNLP pipelineBinary;
	private static StanfordCoreNLP tokenizer;

	public ReviewRating() {
		Properties binaryProps = new Properties();
		Properties pipelineProps = new Properties();
		Properties tokenizerProps = new Properties();
		binaryProps.setProperty("sentiment.model", "model.ser.gz");
		binaryProps.setProperty("annotators", "parse, sentiment");
		binaryProps.setProperty("enforceRequirements", "false");
		pipelineProps.setProperty("annotators", "parse, sentiment");
		pipelineProps.setProperty("enforceRequirements", "false");
		tokenizerProps.setProperty("annotators", "tokenize, ssplit");
		// pipelineBinary = new StanfordCoreNLP(binaryProps);
		pipeline = new StanfordCoreNLP(pipelineProps);
		tokenizer = new StanfordCoreNLP(tokenizerProps);
	}

	public static void main(String[] args) {
		new ReviewRating();
		String review = "Restaurant was in nice location. Ambience was good. Food was tasty. But they took too much time to serve the food";
		double rateReview = rateReview(review);
		System.out.println(rateReview);
	}

	public static ArrayList<String[]> sentimentReview(String review) {
		ArrayList<String[]> sentiments = new ArrayList<String[]>();
		Annotation annotation = tokenizer.process(review);
		pipeline.annotate(annotation);
		for (CoreMap sentence : annotation
				.get(CoreAnnotations.SentencesAnnotation.class)) {
			String polarity = sentence
					.get(SentimentCoreAnnotations.ClassName.class);
			String text = sentence.get(TextAnnotation.class);
			String[] sentiSentence = new String[2];
			sentiSentence[0] = text;
			sentiSentence[1] = polarity;
			sentiments.add(sentiSentence);
		}
		return sentiments;
	}

	public static double rateReview(String review) {
		ArrayList<String[]> sentimentReview = sentimentReview(review);
		int[] scores = new int[sentimentReview.size()];
		int i = 0;
		for (String[] strings : sentimentReview) {
			if (strings[1].equalsIgnoreCase("Positive")
					|| strings[1].equalsIgnoreCase("Very positive")) {
				scores[i] = RankingAlgorithm.positiveScore(strings[0]);
			} else if (strings[1].equalsIgnoreCase("Negative")
					|| strings[1].equalsIgnoreCase("Very negative")) {
				scores[i] = RankingAlgorithm.negativeScore(strings[0]);
			} else {
				scores[i] = 3;
			}
			i++;
		}
		double avgScore = avgScore(scores);
		return avgScore;
	}

	public static double avgScore(int[] scores) {
		double total = 0.0;
		double score;
		int size = scores.length;
		for (int i : scores) {
			total += i;
		}
		score = total / size;
		return score;

	}

}
