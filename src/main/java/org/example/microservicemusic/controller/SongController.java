package org.example.microservicemusic.controller;

import org.example.microservicemusic.model.dto.SongDto;
import org.example.microservicemusic.mapper.ResponseMapper;
import org.example.microservicemusic.service.SongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/edufy/music")
public class SongController {
    SongService songService;

    @Autowired
    public SongController(SongService songService) {
        this.songService = songService;
    }

    // CRUD

    @PostMapping("/create/song")
    public ResponseEntity<Map<String, Object>> createSong(@RequestBody SongDto songDto) {
        Long id = songService.createSong(songDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(ResponseMapper.mapResponse(
                201,
                "Song with id " + id + " has been created"
        ));
    }

    @GetMapping("/read/song/{id}")
    public ResponseEntity<SongDto> getSongById(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(songService.getSongById(id));
    }

    @PutMapping("/update/song/{id}")
    public ResponseEntity<Map<String, Object>> updateSong(@PathVariable Long id, @RequestBody SongDto songDto) {
        songService.updateSong(id, songDto);
        return ResponseEntity.status(HttpStatus.OK).body(ResponseMapper.mapResponse(
                200,
                "Song with id " + id + " has been updated"
        ));
    }

    @DeleteMapping("/delete/song/{id}")
    public ResponseEntity<Map<String, Object>> deleteSongById(@PathVariable Long id) {
        songService.deleteSongById(id);
        return ResponseEntity.status(HttpStatus.OK).body(ResponseMapper.mapResponse(
                200,
                "Song with id " + id + " has been deleted"
        ));
    }

    // OTHER ENDPOINTS

    @GetMapping("/songs")
    public ResponseEntity<List<SongDto>> getAllSongs() {
        return ResponseEntity.status(HttpStatus.OK).body(songService.getAllSongs());
    }

    @PutMapping("/like/song/{id}")
    public ResponseEntity<Map<String, Object>> likeSong(@PathVariable Long id) {
        songService.likeSong(id);
        return ResponseEntity.status(HttpStatus.OK).body(ResponseMapper.mapResponse(
                200,
                "You liked song with id " + id
        ));
    }

    @PutMapping("/dislike/song/{id}")
    public ResponseEntity<Map<String, Object>> dislikeSong(@PathVariable Long id) {
        songService.dislikeSong(id);
        return ResponseEntity.status(HttpStatus.OK).body(ResponseMapper.mapResponse(
                200,
                "You disliked song with id " + id
        ));
    }

    @PutMapping("/randomise/song")
    public ResponseEntity<Map<String, Object>> randomiseSong() {
        songService.randomiseSongStats();
        return ResponseEntity.status(HttpStatus.OK).body(ResponseMapper.mapResponse(
                200,
                "You randomised the song stats"
        ));
    }

    @PostMapping("/search/song")
    public ResponseEntity<List<SongDto>> searchSong(@RequestBody SongDto search) {
        List<SongDto> searchResults = songService.searchResults(search);
        return ResponseEntity.status(HttpStatus.OK).body(searchResults);
    }
}