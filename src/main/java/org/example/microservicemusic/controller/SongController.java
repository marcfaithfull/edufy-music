package org.example.microservicemusic.controller;

import org.example.microservicemusic.model.dto.SongDto;
import org.example.microservicemusic.mapper.ResponseMapper;
import org.example.microservicemusic.service.SongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/edufy/music")
public class SongController {
    private final SongService songService;

    @Autowired
    public SongController(SongService songService) {
        this.songService = songService;
    }

    // CRUD

    @Secured("ROLE_USER")
    @PostMapping("/create/song")
    public ResponseEntity<Map<String, Object>> createSong(@RequestBody SongDto songDto) {
        Long id = songService.createSong(songDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(ResponseMapper.mapResponse(
                201,
                "Song with id " + id + " has been created"
        ));
    }

    @Secured("ROLE_USER")
    @GetMapping("/read/song/{id}")
    public ResponseEntity<SongDto> getSongById(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(songService.getSongById(id));
    }

    @Secured("ROLE_USER")
    @PutMapping("/update/song/{id}")
    public ResponseEntity<Map<String, Object>> updateSong(@PathVariable Long id, @RequestBody SongDto songDto) {
        songService.updateSong(id, songDto);
        return ResponseEntity.status(HttpStatus.OK).body(ResponseMapper.mapResponse(
                200,
                "Song with id " + id + " has been updated"
        ));
    }

    @Secured("ROLE_USER")
    @DeleteMapping("/delete/song/{id}")
    public ResponseEntity<Map<String, Object>> deleteSongById(@PathVariable Long id) {
        songService.deleteSongById(id);
        return ResponseEntity.status(HttpStatus.OK).body(ResponseMapper.mapResponse(
                200,
                "Song with id " + id + " has been deleted"
        ));
    }

    // OTHER ENDPOINTS

    @Secured("ROLE_USER")
    @GetMapping("/songs")
    public ResponseEntity<List<SongDto>> getAllSongs() {
        return ResponseEntity.status(HttpStatus.OK).body(songService.getAllSongs());
    }

    @Secured("ROLE_USER")
    @PutMapping("/like/song/{id}")
    public ResponseEntity<Map<String, Object>> likeSong(@PathVariable Long id, Principal principal) {
        songService.likeSong(id, principal);
        return ResponseEntity.status(HttpStatus.OK).body(ResponseMapper.mapResponse(
                200,
                "You liked song with id " + id
        ));
    }

    @Secured("ROLE_USER")
    @PutMapping("/dislike/song/{id}")
    public ResponseEntity<Map<String, Object>> dislikeSong(@PathVariable Long id, Principal principal) {
        songService.dislikeSong(id, principal);
        return ResponseEntity.status(HttpStatus.OK).body(ResponseMapper.mapResponse(
                200,
                "You disliked song with id " + id
        ));
    }

    @Secured("ROLE_USER")
    @PutMapping("/randomise/song")
    public ResponseEntity<Map<String, Object>> randomiseSong() {
        songService.randomiseSongStats();
        return ResponseEntity.status(HttpStatus.OK).body(ResponseMapper.mapResponse(
                200,
                "You randomised the song stats"
        ));
    }

    @Secured("ROLE_USER")
    @PostMapping("/search/song")
    public ResponseEntity<List<SongDto>> searchSong(@RequestBody SongDto search) {
        List<SongDto> searchResults = songService.searchResults(search);
        return ResponseEntity.status(HttpStatus.OK).body(searchResults);
    }
}