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

import jp.co.cyzennt.rs.rs_spring_alier.model.dao.RoomDao;
import jp.co.cyzennt.rs.rs_spring_alier.model.dao.entity.RoomEntity;
import jp.co.cyzennt.rs.rs_spring_alier.model.logic.impl.RoomLogicImpl;

/**
 * This class test the Room Logic Implementation
 * @author Alier Torrenueva
 * 01/31/2024
 */

//These annotations enable Mockito and Spring support in JUnit 5.
@ExtendWith(MockitoExtension.class)
public class RoomLogicImplTest {

	// Mock the RoomDao interface
	@Mock
	private RoomDao roomDao;

	// Inject mocks into the RoomLogicImpl instance
	@InjectMocks
	private RoomLogicImpl roomLogic;

	// Test method to verify the availability of rooms
	@Test
	public void testAvailRoom() {
	    // Create mock room entities
	    List<RoomEntity> mockRoomEntities = new ArrayList<>();
	    mockRoomEntities.add(new RoomEntity());

	    // Define the behavior of the roomDao mock
	    when(roomDao.findAvailRoom()).thenReturn(mockRoomEntities);

	    // Call the method under test
	    List<RoomEntity> result = roomLogic.availRoom();

	    // Assert that the result is not null and has the expected size
	    assertNotNull(result);
	    assertEquals(mockRoomEntities.size(), result.size());
	}

}
