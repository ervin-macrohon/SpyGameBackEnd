package com.fdmgroup.application.controllers;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fdmgroup.application.exceptions.DuplicateNicknameException;
import com.fdmgroup.application.exceptions.ExceededRoomCapacityException;
import com.fdmgroup.application.exceptions.ExceededRoomsCapacityException;
import com.fdmgroup.application.exceptions.RoomNotFoundException;
import com.fdmgroup.application.services.IRoomManagementService;
import com.fdmgroup.application.util.JsonWrapper;

@RestController
public class RoomController {
	@Resource
	private IRoomManagementService rmService;
	
	@RequestMapping(value="/room", method=RequestMethod.POST)
	public String createRoom(HttpServletResponse resp) {
		int roomId = -1;
		try {
			roomId = rmService.createNewRoom();
		} catch (ExceededRoomsCapacityException e) {
			resp.setStatus(404);
		}
		return JsonWrapper.wrap("roomId", roomId);
	}
	
	@RequestMapping(value="/room/{roomId}/{nickname}", method=RequestMethod.PUT)
	public String joinRoom(@PathVariable int roomId, @PathVariable String nickname, HttpServletResponse res) {
		try {
			return rmService.addToRoom(roomId, nickname);
		} catch (ExceededRoomCapacityException|DuplicateNicknameException e) {
			res.setStatus(403);
			return JsonWrapper.wrap("message", e.getMessage());
		} catch (RoomNotFoundException e) {
			res.setStatus(404);
			return JsonWrapper.wrap("message", e.getMessage());
		}
	}
	
	@RequestMapping(value="/room/{roomId}", method=RequestMethod.GET)
	public String roomStatus(@PathVariable int roomId, HttpServletResponse res) {
		try {
			return rmService.getRoomStatus(roomId);
		} catch (RoomNotFoundException e) {
			res.setStatus(404);
			return JsonWrapper.wrap("message", e.getMessage());
		}
	}
}
