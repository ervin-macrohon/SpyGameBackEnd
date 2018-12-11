package com.fdmgroup.application.controllers;

import javax.servlet.http.HttpServletResponse;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.*;

import com.fdmgroup.application.controllers.RoomController;
import com.fdmgroup.application.exceptions.DuplicateNicknameException;
import com.fdmgroup.application.exceptions.ExceededRoomCapacityException;
import com.fdmgroup.application.exceptions.ExceededRoomsCapacityException;
import com.fdmgroup.application.exceptions.RoomNotFoundException;
import com.fdmgroup.application.services.IRoomManagementService;
import com.fdmgroup.application.util.JsonWrapper;

public class RoomControllerTest {
	@Mock
	private HttpServletResponse res;
	@Mock
	private IRoomManagementService rmService;
	@InjectMocks
	private RoomController controller;

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void returns_new_room_number_from_room_service() throws ExceededRoomsCapacityException {
		when(rmService.createNewRoom()).thenReturn(123456);

		String newRoom = controller.createRoom(res);

		assertEquals(newRoom, "{\"roomId\":123456}");
	}

	@Test
	public void vacant_room_cannot_be_found_sets_404() throws ExceededRoomsCapacityException {
		doThrow(mock(ExceededRoomsCapacityException.class)).when(rmService).createNewRoom();
		
		String newRoom = controller.createRoom(res);
		
		verify(res).setStatus(404);

	}
	
	@Test
	public void returns_capacity_of_room_when_successfully_joined() throws ExceededRoomCapacityException, DuplicateNicknameException, RoomNotFoundException {
		when(rmService.addToRoom(123456, "dae")).thenReturn("5/6");
		
		String capacity = controller.joinRoom(123456, "dae", res);
		
		assertEquals("5/6", capacity);
	}
	
	@Test
	public void when_service_throws_exception_when_joining_room_then_set_status_403() throws ExceededRoomCapacityException, DuplicateNicknameException, RoomNotFoundException {
		String error = stubErrorMessage(mock(ExceededRoomCapacityException.class), "This room seems to be full. Check the room id or check with the room master.");
		
		String message = controller.joinRoom(123456, "dae", res);
		
		verify(res).setStatus(403);
		assertEquals(JsonWrapper.wrap("message", error), message);
	}
	
	@Test
	public void when_service_throws_duplicate_nickname_excpetion_when_joining_room_set_status_403() throws ExceededRoomCapacityException, DuplicateNicknameException, RoomNotFoundException {
		String error = stubErrorMessage(mock(DuplicateNicknameException.class), "Trying to join a room with a nickname that already exists. Please try another nickname");

		String message = controller.joinRoom(123456, "dae", res);
		
		verify(res).setStatus(403);
		assertEquals(JsonWrapper.wrap("message", error), message);
	}
	
	@Test
	public void when_service_throws_room_not_found_when_joining_room_set_status_404() throws ExceededRoomCapacityException, DuplicateNicknameException, RoomNotFoundException {
		String error = stubErrorMessage(mock(RoomNotFoundException.class), "Room not found. Check room number and try again.");
		
		String message = controller.joinRoom(123456, "dae", res);
		
		verify(res).setStatus(404);
		assertEquals(JsonWrapper.wrap("message", error), message);
	}
	
	@Test
	public void returns_capacity_of_valid_room() throws RoomNotFoundException {
		String serviceResponse = JsonWrapper.wrap("capacity", "5/10");
		when(rmService.getRoomStatus(1234)).thenReturn(serviceResponse);
		
		String capacity = controller.roomStatus(1234, res);
		
		assertEquals(serviceResponse, capacity);
	}
	
	@Test
	public void when_service_throws_room_not_found_exception_return_message_and_set_status_to_404() throws RoomNotFoundException {
		String error = "Room not found. Check room number and try again.";
		Exception e = mock(RoomNotFoundException.class);
		doThrow(e).when(rmService).getRoomStatus(1234);
		when(e.getMessage()).thenReturn(error);

		String message = controller.roomStatus(1234, res);
		
		verify(res).setStatus(404);
		assertEquals(JsonWrapper.wrap("message", error), message);
	}

	private String stubErrorMessage(Exception e, String error) throws ExceededRoomCapacityException, DuplicateNicknameException, RoomNotFoundException {
		doThrow(e).when(rmService).addToRoom(123456, "dae");
		when(e.getMessage()).thenReturn(error);
		return error;
	}
}
