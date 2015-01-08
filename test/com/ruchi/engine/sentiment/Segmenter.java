package com.ruchi.engine.sentiment;

import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

import edu.stanford.nlp.ling.BasicDocument;
import edu.stanford.nlp.ling.Word;
import edu.stanford.nlp.process.WordToSentenceProcessor;

/**
 * Interfaces with Stanford parser to segment raw input into words and sentences
 * 
 * @author Bernard Bou <bbou@ac-toulouse.fr>
 */
public class Segmenter
{
	/**
	 * Get document from text
	 * 
	 * @param thisText
	 *            text
	 * @return basic document (list of words)
	 */
	static public BasicDocument<Word> getDocument(final String thisText)
	{
		if (thisText == null || thisText.isEmpty())
			throw new IllegalArgumentException("empty sentence");
		return BasicDocument.init(thisText);
	}

	/**
	 * Get document from text
	 * 
	 * @param thisReader
	 *            reader
	 * @param thisTitle
	 *            title
	 * @return basic document (list of words)
	 * @throws IOException
	 */
	static public BasicDocument<Word> getDocument(final Reader thisReader, final String thisTitle) throws IOException
	{
		return BasicDocument.init(thisReader, thisTitle, true);
	}

	/**
	 * Get sentences from words
	 * 
	 * @param theseWords
	 *            words
	 * @return list of sentences
	 */
	static public List<List<Word>> getTokenizedSentences(final List<Word> theseWords)
	{
		final WordToSentenceProcessor<Word> thisSentenceProcessor = new WordToSentenceProcessor<Word>();
		return thisSentenceProcessor.process(theseWords);
	}

	/**
	 * Get sentences from words
	 * 
	 * @param thisDocument
	 *            words
	 * @return list of sentences
	 */
	static public Pair<List<String>, List<List<Word>>> getSentences(final BasicDocument<Word> thisDocument)
	{
		final WordToSentenceProcessor<Word> thisSentenceProcessor = new WordToSentenceProcessor<Word>();
		final List<List<Word>> theseTokenizedSentences = thisSentenceProcessor.process(thisDocument);

		final String thisText = thisDocument.originalText();
		final List<String> theseSentences = new ArrayList<String>();

		for (final List<Word> thisTokenizedSentence : theseTokenizedSentences)
		{
			final int from = thisTokenizedSentence.get(0).beginPosition();
			final int to = thisTokenizedSentence.get(thisTokenizedSentence.size() - 1).endPosition();
			final String thisSentence = thisText.substring(from, to);
			theseSentences.add(thisSentence);
		}
		return new Pair<List<String>, List<List<Word>>>(theseSentences, theseTokenizedSentences);
	}
}
