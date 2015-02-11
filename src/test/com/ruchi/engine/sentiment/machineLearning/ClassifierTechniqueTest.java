package test.com.ruchi.engine.sentiment.machineLearning;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

import java.io.IOException;
import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.ruchi.engine.foodextractionEvaluvation.ActualIdentifier;
import com.ruchi.engine.sentiment.machineLearning.ClassifierTechnique;

import weka.core.Attribute;
import weka.core.Instances;

public class ClassifierTechniqueTest {

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
	public void testMain() {
		
	}

	@Test
	public void testClassifierTechnique() {
		ArrayList<Attribute> featureVectorAttribute = new ArrayList<Attribute>();
		Attribute attribute=mock(Attribute.class);
		featureVectorAttribute.add(attribute);
		ClassifierTechnique classifierTechnique=new ClassifierTechnique();
		classifierTechnique.setFeatureVectorAttributes(featureVectorAttribute);
		assertEquals(1, classifierTechnique.getFeatureVectorAttributes().size());
		classifierTechnique=new ClassifierTechnique();
		assertEquals(4, classifierTechnique.getFeatureVectorAttributes().size());
	}

	@Test
	public void testReadFromFile() {
		ArrayList<Attribute> featureVectorAttribute = new ArrayList<Attribute>();
		Attribute attribute=mock(Attribute.class);
		featureVectorAttribute.add(attribute);
		ClassifierTechnique classifierTechnique=new ClassifierTechnique();
		classifierTechnique.setFeatureVectorAttributes(featureVectorAttribute);
		Instances set=mock(Instances.class);
		try {
			classifierTechnique.readFromFile("IOfolder/test1.txt", set);
		} catch (IOException e) {
		}
		try {
			classifierTechnique.readFromFile("IOfolder/test.txt", set);
		} catch (IOException e) {
			fail("Not yet implemented");
		}catch (IndexOutOfBoundsException e) {
		}
		Attribute attribute2=mock(Attribute.class);
		Attribute attribute3=mock(Attribute.class);
		Attribute attribute4=mock(Attribute.class);
		featureVectorAttribute.add(attribute2);
		featureVectorAttribute.add(attribute3);
		featureVectorAttribute.add(attribute4);
		classifierTechnique.setFeatureVectorAttributes(featureVectorAttribute);
		try {
			classifierTechnique.readFromFile("IOfolder/test.txt", set);
		} catch (IOException e) {
			fail("Not yet implemented");
		}catch (IllegalArgumentException e) {
		}
		
		
	}

	@Test
	public void testUsingOurBagOfModel() {
		ArrayList<Attribute> featureVectorAttribute = new ArrayList<Attribute>();
		Attribute attribute=mock(Attribute.class);
		featureVectorAttribute.add(attribute);
		ClassifierTechnique classifierTechnique=new ClassifierTechnique();
		String[] words={"good"};
		int score=classifierTechnique.usingOurBagOfModel(words);
		assertEquals(3, score);
		String[] words1={"bad"};
		 score=classifierTechnique.usingOurBagOfModel(words1);
		assertEquals(-3, score);
	}

	@Test
	public void testUsingSentiwordnet() {
		ArrayList<Attribute> featureVectorAttribute = new ArrayList<Attribute>();
		Attribute attribute=mock(Attribute.class);
		featureVectorAttribute.add(attribute);
		ClassifierTechnique classifierTechnique=new ClassifierTechnique();
		String[] words={"good"};
		double score=classifierTechnique.usingSentiwordnet(words);
		assertEquals(1.956, score,0.01);
		String[] words1={"bad"};
		 score=classifierTechnique.usingSentiwordnet(words1);
		assertEquals(-1.570, score,0.001);
	}

	@Test
	public void testClassify() {
		ArrayList<Attribute> featureVectorAttribute = new ArrayList<Attribute>();
		Attribute attribute=mock(Attribute.class);
		featureVectorAttribute.add(attribute);
		ClassifierTechnique classifierTechnique=new ClassifierTechnique();
		classifierTechnique.classify("IOfolder/test.txt","IOfolder/test.txt");
	}

	

}
