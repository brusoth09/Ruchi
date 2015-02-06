package test.com.ruchi.engine.database;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.ruchi.engine.database.DatabaseConnector;

import static org.mockito.Mockito.*;

public class DatabaseConnectorTest {

	static String DB_URL = "jdbc:mysql://localhost/synergy_training_sample";

    static final String USER = "root";
    static final String PASS = "";
    
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
	public void testDatabaseConnector() {
		fail("Not yet implemented");
	}

	@Test
	public void testDatabaseConnectorBoolean() {
		fail("Not yet implemented");
	}

	@Test
	public void testConnect() {
		 Connection conn=mock(Connection.class);
		 DriverManager driverManager=mock(DriverManager.class);
		try {
		when(driverManager.getConnection(DB_URL, USER, PASS)).thenReturn(conn);
		DatabaseConnector databaseConnector=new DatabaseConnector();
		databaseConnector.connect();
		verify(DriverManager.getConnection(DB_URL, USER, PASS), times(1));
		} catch (SQLException e) {
		}
	}

	@Test
	public void testDisconect() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetRestaurantReviews() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetRestaurants() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetRestID() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetRestaurantReviewsFromID() {
		fail("Not yet implemented");
	}

	@Test
	public void testRemoveFoodItem() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetFoodNames() {
		fail("Not yet implemented");
	}

	@Test
	public void testInsertFoodItem() {
		fail("Not yet implemented");
	}

	@Test
	public void testInsertFood() {
		fail("Not yet implemented");
	}

	@Test
	public void testInsertWord() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetTestData() {
		fail("Not yet implemented");
	}

}
