/**
 * 
 */
package test.com.ruchi.engine.foodextractionEvaluvation;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.mockito.Mockito.mock;

import com.ruchi.engine.foodextractionEvaluvation.ActualIdentifier;

/**
 * @author Parane
 *
 */
public class TestActualIdentifier {

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	/**
	 * Test method for {@link com.ruchi.engine.foodextractionEvaluvation.ActualIdentifier#getReviewSentence()}.
	 */
	@Test
	public void testGetReviewSentence() {
		ActualIdentifier actualIdentifier =mock(ActualIdentifier.class);
		assertEquals(null,actualIdentifier.getReviewSentence());
	}

	/**
	 * Test method for {@link com.ruchi.engine.foodextractionEvaluvation.ActualIdentifier#getIdentifiedFoods()}.
	 */
	@Test
	public void testGetIdentifiedFoods() {
		
	}

}
