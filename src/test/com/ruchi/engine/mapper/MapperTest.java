package test.com.ruchi.engine.mapper;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.mockito.*;

import com.ruchi.engine.database.DataStore;
import com.ruchi.engine.mapper.Mapper;
import com.ruchi.hibernate.model.DAO.ReviewDao;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class MapperTest {
	//test case test
	@Test
	public void testGetRestaurantReviewsByRestName() {
		DataStore dataStore=mock(DataStore.class);
		Mapper mapper=new Mapper();
		mapper.setDataSource(dataStore);
		List<ReviewDao> daos=new ArrayList<ReviewDao>();
		when(dataStore.getReviewsByRestName("test")).thenReturn(daos);
		ArrayList<String> list=mapper.getRestaurantReviewsByRestName("test");
		//assertEquals(list, daos);
		ReviewDao reviewDao=mock(ReviewDao.class);
		when(reviewDao.getReview()).thenReturn("test");
		daos.add(reviewDao);
		list=mapper.getRestaurantReviewsByRestName("test");
		assertEquals("test", list.get(0));
	}

	@Test
	public void testGetRestaurantReviewsAndIdsByRestName() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetRestaurantReviewsByRestNameTrain() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetRestaurantReviewsAndIdsByRestNameTrain() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetRestaurantNames() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetRestaurantIDs() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetRestaurantReviewsByRestId() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetRestaurantReviewsAndIdsByRestId() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetRestaurantReviewsByRestIdTrain() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetRestaurantReviewsAndIdsByRestIdTrain() {
		fail("Not yet implemented");
	}

	@Test
	public void testRemoveFood() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetFoodNames() {
		fail("Not yet implemented");
	}

	@Test
	public void testInsertFood() {
		fail("Not yet implemented");
	}

	@Test
	public void testInsertFoodInit() {
		fail("Not yet implemented");
	}

	@Test
	public void testRemoveFoodInit() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetFoodInitNames() {
		fail("Not yet implemented");
	}

	@Test
	public void testInsertReviewFood() {
		fail("Not yet implemented");
	}

	@Test
	public void testInsertRestFood() {
		fail("Not yet implemented");
	}

	@Test
	public void testInsertReviewRating() {
		fail("Not yet implemented");
	}

	@Test
	public void testInsertRestRating() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetRestName() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetAllRestaurantIdsAndNames() {
		fail("Not yet implemented");
	}

}
