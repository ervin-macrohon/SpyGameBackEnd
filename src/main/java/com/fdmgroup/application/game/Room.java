package com.fdmgroup.application.game;

import java.util.Hashtable;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.fdmgroup.application.exceptions.DuplicateNicknameException;
import com.fdmgroup.application.factories.StateFactory;

@Component
@Scope(value="prototype")  
public class Room {
	@Resource
	private StateFactory factory;
	private Map<String, State> states;
	private int numPlayers = 0;
	
	public Room() {
		states = new Hashtable<>();
	}

	public void addPlayer(String nickname) throws DuplicateNicknameException {
		if (states.keySet().contains(nickname))
			throw new DuplicateNicknameException();
		states.put(nickname, factory.createState());
		numPlayers++;
	}

	public int getNumPlayers() {
		return numPlayers;
	}

	public Map<String, State> getStates() {
		return states;
	}

}
