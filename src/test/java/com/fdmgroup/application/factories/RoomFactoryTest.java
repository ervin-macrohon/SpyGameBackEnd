package com.fdmgroup.application.factories;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.BeanFactory;

import com.fdmgroup.application.game.Room;

public class RoomFactoryTest {
	@InjectMocks
	private RoomFactory factory;
	@Mock
	private Room room;
	@Mock
	private BeanFactory beanFactory;
	
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		factory.setBeanFactory(beanFactory);
	}
	
	@Test
	public void create_room_creates_new_room() {
		when(beanFactory.getBean(Room.class)).thenReturn(room);
		
		Room room = factory.createRoom();
		
		assertNotNull(room);
		assertTrue(room instanceof Room);
	}

}
