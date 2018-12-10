package com.fdmgroup.application.exceptions;

public class RoomNotFoundException extends Exception{
	public RoomNotFoundException() {
		super("Room not found. Check room number and try again.");
	}

}
