package com.fdmgroup.application.factories;

import org.springframework.stereotype.Component;

import com.fdmgroup.application.game.State;

@Component
public class StateFactory {

	public State createState() {
		return new State();
	}

}
