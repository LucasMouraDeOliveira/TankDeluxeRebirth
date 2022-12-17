package com.luma.service.game;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.luma.dto.game.GameDTO;
import com.luma.model.game.Game;
import com.luma.model.game.Level;

import jakarta.annotation.PostConstruct;

@Service
public class GameService {
	
	private List<Game> games;
	
	@PostConstruct
	private void init() {
		this.games = new ArrayList<>();
	}
	
	public List<Game> listGames() {
		return this.games;
	}
	
	public void createGame(String name, Level level) {
		Game game = new Game(name, level);
		games.add(game);
	}
	
	public void startGame(String name) {
		Game game = this.games.stream().filter(g -> g.getName().equals(name)).findFirst().orElseThrow();
		game.start();
	}
	
	public GameDTO toDTO(Game game) {
		var gameDTO = new GameDTO();
		gameDTO.setName(game.getName());
		return gameDTO;
	}

}