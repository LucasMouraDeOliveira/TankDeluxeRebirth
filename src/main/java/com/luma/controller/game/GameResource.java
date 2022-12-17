package com.luma.controller.game;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.luma.dto.game.GameDTO;
import com.luma.model.game.Level;
import com.luma.service.game.GameService;
import com.luma.service.game.LevelService;

@RestController
@RequestMapping("${apiPrefix}/games")
public class GameResource {

	@Autowired
	private GameService gameService;
	
	@Autowired
	private LevelService levelService;

	@GetMapping
	public ResponseEntity<List<GameDTO>> listGames() {
		return ResponseEntity.ok(this.listAllGames());
	}

	private List<GameDTO> listAllGames() {
		return this.gameService.listGames()
				.stream()
				.map(gameService::toDTO)
				.collect(Collectors.toList());
	}

	@PostMapping
	public ResponseEntity<Void> create(@RequestBody GameDTO gameDTO) {
		Level level = this.levelService.loadLevel(gameDTO.getLevel());
		this.gameService.createGame(gameDTO.getName(), level);
		return ResponseEntity.ok().build();
	}

}