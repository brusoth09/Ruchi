package test.com.ruchi.engine.sentiment;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.HashMap;
import java.util.Set;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.ruchi.engine.foodextractionEvaluvation.ActualIdentifier;
import com.ruchi.engine.models.Sentence;
import com.ruchi.engine.sentiment.TypedDependencyEngine;

public class TypedDependencyEngineTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testTypedDependencyEngine() {
		
	}

	@Test
	public void testMain() {
	
	}

	@Test
	public void testFoodSentiment() {
		TypedDependencyEngine typedDependencyEngine	=new TypedDependencyEngine();
		Sentence sentence=mock(Sentence.class);
		when(sentence.getSentence()).thenReturn("test");
		HashMap<String, Integer[]> foodMap = mock(HashMap.class);
		when(sentence.getFoodMap()).thenReturn(foodMap);		;
		Set<String> keySet = mock(Set.class);
		when(foodMap.keySet()).thenReturn(keySet);
		when(sentence.getSentence()).thenReturn("test");
		typedDependencyEngine.foodSentiment(sentence);
	}

	@Test
	public void testSentimentAnalysis() {
	
	}

	@Test
	public void testSentiTyped() {
		
	}

	@Test
	public void testGenerateTypedDependency() {
		
	}

	@Test
	public void testSegmentText() {
		
	}

	@Test
	public void testGenerateSentence() {
	
	}

}
