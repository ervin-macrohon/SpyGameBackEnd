package com.fdmgroup.application.exceptions;

public class ExceededRoomsCapacityException extends Exception {
	private static final long serialVersionUID = -1180428482631607082L;

	public ExceededRoomsCapacityException() {
		super("Maximum number of rooms exceeded. Try again later");
	}
}
