package com.fdmgroup.application.exceptions;

public class DuplicateNicknameException extends Exception{
	private static final long serialVersionUID = 8028412719669512671L;

	public DuplicateNicknameException() {
		super("Trying to join a room with a nickname that already exists. Please try another nickname");
	}
}
