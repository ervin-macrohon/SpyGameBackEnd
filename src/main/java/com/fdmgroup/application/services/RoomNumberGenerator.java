package com.fdmgroup.application.services;


import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class RoomNumberGenerator implements IRoomNumberGenerator {
	private final static int MAX_ROOMS = 1000;
	private List<Integer> roomNums;
	
	public RoomNumberGenerator() {
		roomNums = new ArrayList<>();
		for (int i = 0; i < MAX_ROOMS; i++) {
			roomNums.add(i);
		}
	}
	
	public RoomNumberGenerator(List roomNums) {
		this.roomNums = roomNums;
	}

	@Override
	public int generateRoomNumber() {
		synchronized (roomNums) {
			return roomNums.isEmpty() ? -1 : roomNums.remove(0);
		}
	}

}
