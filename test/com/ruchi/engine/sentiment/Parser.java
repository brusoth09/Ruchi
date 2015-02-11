package com.ruchi.engine.sentiment;

import java.util.Collection;
import java.util.List;

import edu.stanford.nlp.ling.Word;
import edu.stanford.nlp.parser.lexparser.LexicalizedParser;
import edu.stanford.nlp.parser.lexparser.Options;
import edu.stanford.nlp.trees.Tree;
import edu.stanford.nlp.trees.TypedDependency;

/**
 * Interfaces with Stanford parser to produce parse trees
 * 
 * @author Bernard Bou <bbou@ac-toulouse.fr>
 */
public class Parser
{
	/**
	 * Class to transmit results
	 */
	static class Parse extends Pair<Tree, Collection<TypedDependency>>
	{
		public Parse(final Tree t, final Collection<TypedDependency> tds)
		{
			super(t, tds);
		}
	}

	/**
	 * Parser factory
	 */
	static private String theLexicalizedParserName = "englishPCFG.ser.gz";

	/**
	 * Set parser name (must be called before init()
	 * 
	 * @param thisLexicalizedParserName
	 *            parser filename
	 */
	static public void setGrammar(final String thisLexicalizedParserName)
	{
		theLexicalizedParserName = thisLexicalizedParserName;
	}

	/**
	 * Parser instance
	 */
	static private LexicalizedParser theLexicalizedParser = null;

	/**
	 * Init parser
	 */
	static void init()
	{
		try
		{
			Options theseOptions = new Options();
			theseOptions.setOptions(new String[] {});
			String theseExtraFlags[] = new String[] { "-maxLength", "256", "-retainTmpSubcategories" };
			theLexicalizedParser = LexicalizedParser.loadModel(theLexicalizedParserName, theseOptions, theseExtraFlags);
		}
		catch (final Exception e)
		{
			System.err.println("ERR " + e.toString());
		}
	}

	/**
	 * Parse tokenized sentence
	 * 
	 * @param sentence
	 *            list of words
	 * @return (tree,typed dependencies)
	 */
	static public Parse parse(final List<Word> sentence)
	{
		final Tree thisTree = Parser.theLexicalizedParser.parseTree(sentence);
		final Collection<TypedDependency> theseDependencies = Analyzer.dependencies(thisTree);
		return new Parse(thisTree, theseDependencies);
	}
}
