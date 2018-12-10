package com.fdmgroup.application.factories;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.fdmgroup.application.game.Room;

public class RoomFactoryTest {
	@Test
	public void create_room_creates_new_room() {
		RoomFactory factory = new RoomFactory();
		
		Room room = factory.createRoom();
		
		assertNotNull(room);
		assertTrue(room instanceof Room);
	}

}
