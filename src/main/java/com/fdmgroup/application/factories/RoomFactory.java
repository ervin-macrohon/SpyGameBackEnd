package com.fdmgroup.application.factories;

import org.springframework.stereotype.Component;

import com.fdmgroup.application.game.Room;

@Component
public class RoomFactory {

	public Room createRoom() {
		return new Room();
	}

}
