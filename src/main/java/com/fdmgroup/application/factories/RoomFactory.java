package com.fdmgroup.application.factories;


import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.stereotype.Component;

import com.fdmgroup.application.game.Room;

@Component
public class RoomFactory implements BeanFactoryAware{
	
	private BeanFactory beanFactory;

	public Room createRoom() {
		return beanFactory.getBean(Room.class);
	}

	@Override
	public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
		this.beanFactory = beanFactory;
	}

}
