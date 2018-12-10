package com.fdmgroup.application.services;

import com.fdmgroup.application.exceptions.DuplicateNicknameException;
import com.fdmgroup.application.exceptions.ExceededRoomCapacityException;
import com.fdmgroup.application.exceptions.ExceededRoomsCapacityException;
import com.fdmgroup.application.exceptions.RoomNotFoundException;

public interface IRoomManagementService {

	int createNewRoom() throws ExceededRoomsCapacityException;

	String addToRoom(int roomId, String nickname) throws ExceededRoomCapacityException, DuplicateNicknameException, RoomNotFoundException;

}
