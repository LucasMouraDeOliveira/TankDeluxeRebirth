package com.luma.service.game;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Base64;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.luma.model.game.Level;
import com.luma.utils.game.LevelContent;

import jakarta.annotation.PostConstruct;

@Service
public class LevelService {
	
	private static final Logger logger = LoggerFactory.getLogger(LevelService.class);
	
	@Value("${app.map.location}")
	private String mapLocation;
	
	@Autowired
	private ObjectMapper mapper;
	
	private List<LevelContent> gameMaps;
	
	@PostConstruct
	private void loadMaps() {
		this.gameMaps = Stream.of(new File(this.mapLocation).listFiles(File::isFile))
				.sorted((f1, f2) -> (int) (f1.lastModified() - f2.lastModified()))
				.map(this::getMapContent).filter(Optional::isPresent).map(Optional::get)
				.collect(Collectors.toList());
	}
	
	private Optional<LevelContent> getMapContent(File gameMapFile) {
		try {
			Path filePath = gameMapFile.toPath();
			String content = Files.readString(filePath);
			byte[] data = Base64.getDecoder().decode(content);
			return Optional.of(mapper.readValue(data, LevelContent.class));
		} catch (IOException e) {
			logger.error("Failed to load game map", e);
			return Optional.empty();
		}
	}
	
	public Level loadLevel(int levelId) {
		LevelContent levelContent = this.gameMaps.get(levelId);
		Level level = new Level();
		return level;
	}

}
