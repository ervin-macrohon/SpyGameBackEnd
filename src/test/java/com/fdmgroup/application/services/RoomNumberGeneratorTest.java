package com.fdmgroup.application.services;

import static org.junit.Assert.*;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class RoomNumberGeneratorTest {
	private RoomNumberGenerator generator;
	private List<Integer> roomNums;
	
	@Test
	public void returns_0_then_1_then_2() {
		roomNums = new ArrayList<>();
		roomNums.add(0);
		roomNums.add(1);
		roomNums.add(2);
		generator = new RoomNumberGenerator(roomNums);
		int roomNum = generator.generateRoomNumber();
		
		assertEquals(roomNum, 0);
		
		roomNum = generator.generateRoomNumber();
		
		assertEquals(roomNum, 1);
		
		roomNum = generator.generateRoomNumber();
		
		assertEquals(roomNum, 2);
	}
	
	@Test
	public void returns_minus_1_when_no_more_available_rooms() {
		roomNums = new ArrayList<>();
		generator = new RoomNumberGenerator(roomNums);
		
		int roomNum = generator.generateRoomNumber();

		assertEquals(-1, roomNum);
	}
}
