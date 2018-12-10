package com.fdmgroup.application.game;

import static org.junit.Assert.*;

import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.mockito.Mockito.*;

import com.fdmgroup.application.exceptions.DuplicateNicknameException;
import com.fdmgroup.application.factories.StateFactory;

public class RoomTest {
	@InjectMocks
	private Room room;
	@Mock
	private StateFactory factory;
	private Map<String, State> states;
	@Mock
	private State state;
	
	@Before 
	public void setup() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void add_player_adds_new_state_to_map() throws DuplicateNicknameException {
		when(factory.createState()).thenReturn(state);

		room.addPlayer("dae");
		
		assertEquals(state, room.getStates().get("dae"));
	}
	
	@Test(expected=DuplicateNicknameException.class)
	public void duplicate_nicknames_throws_exception() throws DuplicateNicknameException {
		when(factory.createState()).thenReturn(state);

		room.addPlayer("dae");
		room.addPlayer("dae");
	}

}
