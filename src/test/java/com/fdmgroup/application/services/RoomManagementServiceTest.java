package com.fdmgroup.application.services;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.fdmgroup.application.exceptions.DuplicateNicknameException;
import com.fdmgroup.application.exceptions.ExceededRoomCapacityException;
import com.fdmgroup.application.exceptions.ExceededRoomsCapacityException;
import com.fdmgroup.application.exceptions.RoomNotFoundException;
import com.fdmgroup.application.factories.RoomFactory;
import com.fdmgroup.application.game.Room;
import com.fdmgroup.application.util.JsonWrapper;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.*;

import java.util.Hashtable;
import java.util.Map;

public class RoomManagementServiceTest {
	@Mock
	private IRoomNumberGenerator rnGenerator;
	@Mock
	private RoomFactory roomFactory;
	@InjectMocks
	private RoomManagementService rmService;
	@Mock
	private Room room;
	private Map<Integer, Room> testMap;
	
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		rmService.init();
		testMap = new Hashtable<>();
	}
	
	@Test
	public void create_new_room_returns_gernerated_room_number() throws ExceededRoomsCapacityException {
		when(rnGenerator.generateRoomNumber()).thenReturn(123456);
		when(roomFactory.createRoom()).thenReturn(room);
		
		int roomNum = rmService.createNewRoom();
		
		verify(roomFactory).createRoom();
		assertEquals(roomNum, 123456);
		assertSame(rmService.getRooms().get(123456), room);
	}
	
	@Test
	public void calls_add_player_on_nickname_for_given_room_id() throws ExceededRoomCapacityException, DuplicateNicknameException, RoomNotFoundException {
		testMap.put(1234, room);
		rmService.setRooms(testMap);
		when(room.getNumPlayers()).thenReturn(5);
		
		String capacity = rmService.addToRoom(1234, "dae");
		
		verify(room).addPlayer("dae");
		assertEquals(JsonWrapper.wrap("capacity", "5/10"), capacity);
	}
	
	
	@Test(expected=RoomNotFoundException.class)
	public void calling_add_to_room_on_a_room_that_doesnt_exit_throws_exception() throws ExceededRoomCapacityException, DuplicateNicknameException, RoomNotFoundException {
		rmService.setRooms(testMap);
		
		String capacity = rmService.addToRoom(1234, "dae");
	}
	
	@Test
	public void room_status_returns_roomsc_current_capacity() throws RoomNotFoundException, DuplicateNicknameException {
		testMap.put(1234, room);
		rmService.setRooms(testMap);
		when(room.getNumPlayers()).thenReturn(2);
		
		String roomStatus = rmService.getRoomStatus(1234);
		
		assertEquals(JsonWrapper.wrap("capacity", "2/10"), roomStatus);
	}
}
