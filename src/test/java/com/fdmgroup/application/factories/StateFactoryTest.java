package com.fdmgroup.application.factories;

import static org.junit.Assert.*;

import org.junit.Test;

import com.fdmgroup.application.game.State;

public class StateFactoryTest {
	private final StateFactory factory = new StateFactory();
	@Test
	public void returns_not_null_state() {
		State state = factory.createState();
		
		assertNotNull(state);
		assertTrue(state instanceof State);
	}

}
