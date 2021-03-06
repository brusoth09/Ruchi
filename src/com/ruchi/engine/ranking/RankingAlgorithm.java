package com.ruchi.engine.ranking;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import edu.stanford.nlp.ling.HasWord;
import edu.stanford.nlp.ling.TaggedWord;
import edu.stanford.nlp.tagger.maxent.MaxentTagger;


/*
 * This algorithm is based Vaishak Suresh Resturant Review Rankings,
 * Resturant domain lexicon build by our own system and some general word get it from AFNN word list
 * an adverb or an adjective as an opinion word collected resturant reviews
 * Note: Polarity of each Sentence is calculated by our sentiment model(not include here)
 * 
 * Evaluation (vary humman subjective measure and check rateEvaluation model)
 * precison 0.660377358490566
 * recall 0.7
 * fmeasure 0.6796116504854369 
 *  
 * 
 */
public class RankingAlgorithm {

	static MaxentTagger tagger = new MaxentTagger(
			"english-left3words-distsim.tagger");

	private RankingAlgorithm() {
	}

	public static void main(String[] args) throws Exception {
		System.out.print(negativeScore("food is bad"));
	}

	public static int positiveScore(String review) {
		
		List<TaggedWord> taggedWords = annotedText(review);
		int tooPosition = -2;
		int score = 4;
		for (int i = 0; i < taggedWords.size(); i++) {
			TaggedWord word = taggedWords.get(i);
			String value = word.value();
			String tag = word.tag();
			if (tag.equalsIgnoreCase("RB") && value.equalsIgnoreCase("too")) {
				// save too word position
				tooPosition = i;
			}
			if (value.equalsIgnoreCase("and")) {
				// Continue the too rule 
			}
			//building of Lexicon/mater list is not included
			if (SentimentStrings.pos1.indexOf(value) > -1
					|| SentimentStrings.pos2.indexOf(value) > -1
					|| SentimentStrings.pos3.indexOf(value) > -1
					|| SentimentStrings.pos4.indexOf(value) > -1
					|| SentimentStrings.pos5.indexOf(value) > -1
					|| SentimentStrings.int1.indexOf(value) > -1
					|| SentimentStrings.int2.indexOf(value) > -1
					|| SentimentStrings.int3.indexOf(value) > -1) {
				// superlative sentiment the score is increase or decrease
				//POS-tagged as comparative sentiment the score is increase or decrease by 1
				if (tag.equalsIgnoreCase("RBS") || tag.equalsIgnoreCase("JJS")) {
					score = 5;
				}
				if (tag.equalsIgnoreCase("RBR") || tag.equalsIgnoreCase("JJR")) {
					score = 5;
				}
				if (SentimentStrings.pos5.indexOf(value) > -1
						|| SentimentStrings.pos3.indexOf(value) > -1
						|| SentimentStrings.pos4.indexOf(value) > -1) {
					score = 5;
				}
				if (tooPosition == i - 1) {
					score = 5;
				}
			}
		}
		return score;
	}

	public static int negativeScore(String review) {
		List<TaggedWord> taggedWords = annotedText(review);
		int tooPosition = -2;
		int score = 3;
		for (int i = 0; i < taggedWords.size(); i++) {

			TaggedWord word = taggedWords.get(i);
			String value = word.value();
			String tag = word.tag();
			if (tag.equalsIgnoreCase("RB") && value.equalsIgnoreCase("too")) {
				// save too word position
				tooPosition = i;
			}
			if (value.equalsIgnoreCase("and")) {
				// Continue the too rule
			}
			if (SentimentStrings.neg1.indexOf(value) > -1
					|| SentimentStrings.neg2.indexOf(value) > -1
					|| SentimentStrings.neg3.indexOf(value) > -1
					|| SentimentStrings.neg4.indexOf(value) > -1
					|| SentimentStrings.neg5.indexOf(value) > -1
					|| SentimentStrings.int1.indexOf(value) > -1
					|| SentimentStrings.int2.indexOf(value) > -1
					|| SentimentStrings.int3.indexOf(value) > -1) {
				if (score == 3) {
					score = 2;
				}
				//POS-tagged as superlative sentiment the score is increase or decrease
				//POS-tagged as comparative sentiment the score is increase or decrease 
				if (tag.equalsIgnoreCase("RBS") || tag.equalsIgnoreCase("JJS")) {
					score = 1;
				}
				if (tag.equalsIgnoreCase("RBR") || tag.equalsIgnoreCase("JJR")) {
					score = 1;
				}
				if (SentimentStrings.neg2.indexOf(value) > -1
						|| SentimentStrings.neg3.indexOf(value) > -1
						|| SentimentStrings.neg4.indexOf(value) > -1
						|| SentimentStrings.neg5.indexOf(value) > -1) {
					score = 1;
				}
				if (tooPosition == i - 1) {
					score = 1;
				}
			}
		}
		return score;
	}

	public static List<TaggedWord> annotedText(String text) {
		List<List<HasWord>> sentences = MaxentTagger
				.tokenizeText(new StringReader(text));
		List<TaggedWord> tSentence = tagger.tagSentence(sentences.get(0));
		return tSentence;
	}

	public static double avgScore(ArrayList<Integer> scores) {
		double total = 0.0;
		double score;
		int size = scores.size();
		for (int i : scores) {
			total += i;
		}
		score = total / size;
		return score;

	}

	public static double avgScoreDouble(ArrayList<Double> scores) {
		double total = 0.0;
		double score;
		int size = scores.size();
		for (double i : scores) {
			total += i;
		}
		score = total / size;
		return score;

	}
}
