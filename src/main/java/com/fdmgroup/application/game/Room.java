package com.fdmgroup.application.game;

import java.util.Hashtable;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.fdmgroup.application.exceptions.DuplicateNicknameException;
import com.fdmgroup.application.factories.StateFactory;

@Component
public class Room {
	@Resource
	private StateFactory factory;
	private Map<String, State> states;
	
	public Room() {
		states = new Hashtable<>();
	}

	public void addPlayer(String nickname) throws DuplicateNicknameException {
		if (states.keySet().contains(nickname))
			throw new DuplicateNicknameException();
		System.out.println(nickname);
		System.out.println(factory);
		states.put(nickname, factory.createState());
	}

	public int getNumPlayers() {
		return 0;
	}

	public Map<String, State> getStates() {
		return states;
	}

}
