package com.luma.model.game;

import org.dyn4j.dynamics.World;

import lombok.Getter;

@Getter
public class Game {
	
	private final String name;
	
	private final Level level;
	
	private World world;
	
	public Game(String name, Level level) {
		this.name = name;
		this.level = level;	
		this.world = new World();
	}

	public void start() {
		// TODO Auto-generated method stub
		
	}

}
