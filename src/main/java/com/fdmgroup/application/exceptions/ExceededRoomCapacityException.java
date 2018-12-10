package com.fdmgroup.application.exceptions;

public class ExceededRoomCapacityException extends Exception {
	private static final long serialVersionUID = 274759576311203735L;

	public ExceededRoomCapacityException() {
		super("This room seems to be full. Check the room id or check with the room master.");
	}
}
