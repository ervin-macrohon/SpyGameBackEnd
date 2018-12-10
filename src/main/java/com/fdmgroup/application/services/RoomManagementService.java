package com.fdmgroup.application.services;

import java.util.Hashtable;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fdmgroup.application.exceptions.DuplicateNicknameException;
import com.fdmgroup.application.exceptions.ExceededRoomCapacityException;
import com.fdmgroup.application.exceptions.ExceededRoomsCapacityException;
import com.fdmgroup.application.exceptions.RoomNotFoundException;
import com.fdmgroup.application.factories.RoomFactory;
import com.fdmgroup.application.game.Room;

@Service
public class RoomManagementService implements IRoomManagementService {
	@Resource
	private IRoomNumberGenerator rnGenerator;
	@Resource
	private RoomFactory roomFactory;
	private Map<Integer, Room> rooms;
	
	@PostConstruct
	public void init() {
		rooms = new Hashtable<>();
	}

	@Override
	public int createNewRoom() throws ExceededRoomsCapacityException {
		int roomNum = rnGenerator.generateRoomNumber();
		if (roomNum == -1)
			throw new ExceededRoomsCapacityException();
		rooms.put(Integer.valueOf(roomNum), roomFactory.createRoom());
		return roomNum;
	}

	public Map<Integer, Room> getRooms() {
		return rooms;
	}

	public void setRooms(Map<Integer, Room> rooms) {
		this.rooms = rooms;
	}

	@Override
	public String addToRoom(int roomId, String nickname) throws ExceededRoomCapacityException, DuplicateNicknameException, RoomNotFoundException {
		if (rooms.get(roomId) == null)
			throw new RoomNotFoundException();
		Room room = rooms.get(roomId);
		room.addPlayer(nickname);
		return room.getNumPlayers() + "/10";
	}
}
