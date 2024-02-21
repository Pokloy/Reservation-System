package Logic;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import jp.co.cyzennt.rs.rs_spring_alier.model.dao.TimeDao;
import jp.co.cyzennt.rs.rs_spring_alier.model.dao.entity.TimeEntity;
import jp.co.cyzennt.rs.rs_spring_alier.model.logic.impl.TimeLogicImpl;
/**
 * This class test the Time Logic Implementation
 * @author Alier Torrenueva
 * 01/31/2024
 */

//These annotations enable Mockito and Spring support in JUnit 5.
@ExtendWith(MockitoExtension.class)
public class TimeLogicImplTest {
	
	// Mock the TimeDao interface
	@Mock
	private TimeDao timeDao;

	// Inject mocks into the TimeLogicImpl instance
	@InjectMocks
	private TimeLogicImpl timeLogic;

	// Test method to verify the availability of time
	@Test
	public void testAvailTime() {
	    // Create mock time entities
	    List<TimeEntity> mockTimeEntities = new ArrayList<>();
	    mockTimeEntities.add(new TimeEntity(/* pass required constructor arguments here */));

	    // Define the behavior of the timeDao mock
	    when(timeDao.findAvailTime()).thenReturn(mockTimeEntities);

	    // Call the method under test
	    List<TimeEntity> result = timeLogic.availTime();

	    // Assert that the result is not null and has the expected size
	    assertNotNull(result);
	    assertEquals(mockTimeEntities.size(), result.size());
	}


}
