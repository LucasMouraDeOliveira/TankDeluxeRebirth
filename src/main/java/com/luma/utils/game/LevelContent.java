package com.luma.utils.game;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class LevelContent {
	
	private String name;
	
	private int width;
	
	private int height;
	
	private List<List<String>> ground;
	
	private List<List<String>> obstacle;
	
	private List<List<Boolean>> spawn;

}