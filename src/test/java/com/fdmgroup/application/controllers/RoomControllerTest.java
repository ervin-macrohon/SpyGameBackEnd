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
	public void room_not_found_calls_set_status_404() throws ExceededRoomsCapacityException {
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
		assertEquals("{\"message\":\"" + error + "\"}", message);
	}
	
	@Test
	public void when_service_throws_duplicate_nickname_excpetion_when_joining_room_set_status_403() throws ExceededRoomCapacityException, DuplicateNicknameException, RoomNotFoundException {
		String error = stubErrorMessage(mock(DuplicateNicknameException.class), "Trying to join a room with a nickname that already exists. Please try another nickname");

		String message = controller.joinRoom(123456, "dae", res);
		
		verify(res).setStatus(403);
		assertEquals("{\"message\":\"" + error + "\"}", message);
	}
	
	@Test
	public void when_service_throws_room_not_found_when_joining_room_set_status_404() throws ExceededRoomCapacityException, DuplicateNicknameException, RoomNotFoundException {
		String error = stubErrorMessage(mock(RoomNotFoundException.class), "Room not found. Check room number and try again.");
		
		String message = controller.joinRoom(123456, "dae", res);
		
		verify(res).setStatus(404);
		assertEquals("{\"message\":\"" + error + "\"}", message);
	}

	private String stubErrorMessage(Exception e, String error) throws ExceededRoomCapacityException, DuplicateNicknameException, RoomNotFoundException {
		doThrow(e).when(rmService).addToRoom(123456, "dae");
		when(e.getMessage()).thenReturn(error);
		return error;
	}
}
