package test.com.ruchi.engine.sentiment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.ruchi.engine.models.Sentence;
import com.ruchi.engine.sentiment.TypedDependencyEngine;

public class TypedDependencyEngineTest {
	TypedDependencyEngine typedDependencyEngine;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		new TypedDependencyEngine();
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
		String inText = "pizza was awesome and chinese pasta was awful";
		Sentence sentence = new Sentence(inText);
		Integer[] location = new Integer[2];
		location[0] = 0;
		location[1] = 1;
		Integer[] location2 = new Integer[2];
		location2[0] = 4;
		location2[1] = 2;
		sentence.addFood("pizza", location);
		sentence.addFood("pasta", location2);
		HashMap<String, Double> foodSentiment = TypedDependencyEngine.foodSentiment(sentence);
		Set<String> keySet = foodSentiment.keySet();
		for (String key : keySet) {
			if(key.equals("pizza")){
				Assert.assertEquals(5.0, (double) foodSentiment.get(key));
			}
			else{
				Assert.assertEquals(1.0, (double) foodSentiment.get(key));
			}
		}
	}

	@Test
	public void testSentimentAnalysis() {
		String inText = "pizza was awesome";
		String inText2 = "chinese pasta was awful";
		ArrayList<String> sent = TypedDependencyEngine.sentimentAnalysis(inText);
		Assert.assertEquals("positive", sent.get(0));
		sent = TypedDependencyEngine.sentimentAnalysis(inText2);
		Assert.assertEquals("negative", sent.get(0));
	}

}
