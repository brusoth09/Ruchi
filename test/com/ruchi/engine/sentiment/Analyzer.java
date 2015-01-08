package com.ruchi.engine.sentiment;

import java.util.Collection;

import edu.stanford.nlp.trees.GrammaticalStructure;
import edu.stanford.nlp.trees.GrammaticalStructureFactory;
import edu.stanford.nlp.trees.PennTreebankLanguagePack;
import edu.stanford.nlp.trees.Tree;
import edu.stanford.nlp.trees.TreebankLanguagePack;
import edu.stanford.nlp.trees.TypedDependency;

/**
 * Interfaces with Stanford parser to produce typed dependencies
 * 
 * @author Bernard Bou <bbou@ac-toulouse.fr>
 */
public class Analyzer
{
	static public enum TypedDependenciesFactory
	{
		ALL, ALLNOEXTRA, COLLAPSED, COLLAPSEDNOEXTRA, COLLAPSEDTREE, CCPROCESSED, CCPROCESSEDNOEXTRA;

		public Collection<TypedDependency> make(GrammaticalStructure thisStructure)
		{
			switch (this)
			{
			case ALL:
				return thisStructure.typedDependencies(true);
			case ALLNOEXTRA:
				return thisStructure.typedDependencies(false);
			case COLLAPSED:
				return thisStructure.typedDependenciesCollapsed(true);
			case COLLAPSEDNOEXTRA:
				return thisStructure.typedDependenciesCollapsed(false);
			case COLLAPSEDTREE:
				return thisStructure.typedDependenciesCollapsedTree();
			case CCPROCESSED:
				return thisStructure.typedDependenciesCCprocessed(true);
			case CCPROCESSEDNOEXTRA:
				return thisStructure.typedDependenciesCCprocessed(false);
			}
			return null;
		}
	}

	/**
	 * Currently operating factory
	 */
	static public TypedDependenciesFactory theFactory = TypedDependenciesFactory.ALL;

	/**
	 * Set factory
	 * 
	 * @param thisFactoryName
	 *            factory name
	 */
	static public void setFactory(String thisFactoryName)
	{
		theFactory = TypedDependenciesFactory.valueOf(thisFactoryName);
	}

	/**
	 * Language pack
	 */
	static private TreebankLanguagePack theLanguagePack = null;

	/**
	 * Grammatical structure factory
	 */
	static private GrammaticalStructureFactory theGrammaticalStructureFactory = null;

	/**
	 * Init analyzer
	 */
	static void init()
	{
		theLanguagePack = new PennTreebankLanguagePack();
		theGrammaticalStructureFactory = Analyzer.theLanguagePack.grammaticalStructureFactory();
	}

	/**
	 * Parse sentence and get typed dependencies
	 * 
	 * @param thisTree
	 *            tree
	 * @return (tree,typed dependencies)
	 */
	static public Collection<TypedDependency> dependencies(Tree thisTree)
	{
		GrammaticalStructure thisStructure = Analyzer.theGrammaticalStructureFactory.newGrammaticalStructure(thisTree);
		return theFactory.make(thisStructure);
	}
}
