package org.example.microservicemusic.controller;

import org.example.microservicemusic.model.dto.PostSongDto;
import org.example.microservicemusic.model.dto.SongAlbumArtistDto;
import org.example.microservicemusic.mapper.ResponseMapper;
import org.example.microservicemusic.model.entity.Song;
import org.example.microservicemusic.service.SongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class SongController {
    private final SongService songService;

    @Autowired
    public SongController(SongService songService) {
        this.songService = songService;
    }

    // CRUD

    @Secured("ROLE_ADMIN")
    @PostMapping("/upload/song")
    public ResponseEntity<Map<String, Object>> createSong(@RequestBody PostSongDto postSongDto) {
        Song song = songService.createSong(postSongDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(ResponseMapper.mapResponse(
                201,
                song.getTitle() + " has been uploaded",
                "/song/"  + song.getId()
        ));
    }

    @Secured("ROLE_USER")
    @GetMapping("/play/song/{id}")
    public ResponseEntity<Map<String, Object>> getSongById(@PathVariable Long id) {
        SongAlbumArtistDto songAlbumArtistDto = songService.getSongById(id);
        return ResponseEntity.status(HttpStatus.OK).body(ResponseMapper.mapResponse(
                200,
                songAlbumArtistDto.getTitle() + " by " + songAlbumArtistDto.getArtist().getName() + " is currently playing",
                "/play/song/" + id
        ));
    }

    @Secured("ROLE_ADMIN")
    @PutMapping("/update/song/{id}")
    public ResponseEntity<Map<String, Object>> updateSongById(@PathVariable Long id, @RequestBody PostSongDto postSongDto) {
        songService.updateSong(id, postSongDto);
        return ResponseEntity.status(HttpStatus.OK).body(ResponseMapper.mapResponse(
                200,
                "Song with id " + id + " has been updated",
                "/update/song/" + id
        ));
    }

    @Secured("ROLE_ADMIN")
    @DeleteMapping("/delete/song/{id}")
    public ResponseEntity<Map<String, Object>> deleteSongById(@PathVariable Long id) {
        Song song = songService.deleteSongById(id);
        return ResponseEntity.status(HttpStatus.OK).body(ResponseMapper.mapResponse(
                200,
                song.getTitle() + " has been deleted",
                "/delete/song/" + id
        ));
    }

    // OTHER ENDPOINTS

    @Secured("ROLE_USER")
    @GetMapping("/songs")
    public ResponseEntity<List<SongAlbumArtistDto>> getAllSongs() {
        return ResponseEntity.status(HttpStatus.OK).body(songService.getAllSongs());
    }

    @Secured("ROLE_USER")
    @GetMapping("/play/random/song")
    public ResponseEntity<Map<String, Object>> getRandomSong() {
        SongAlbumArtistDto randomSong = songService.getRandomSong();
        return ResponseEntity.status(HttpStatus.OK).body(ResponseMapper.mapResponse(
                200,
                randomSong.getTitle() + " by " + randomSong.getArtist().getName() + " is currently playing",
                "/play/song/" + randomSong.getId()
        ));
    }

    @Secured("ROLE_USER")
    @PostMapping("/search/song")
    public ResponseEntity<List<SongAlbumArtistDto>> searchSong(@RequestBody SongAlbumArtistDto search) {
        List<SongAlbumArtistDto> searchResults = songService.searchResults(search);
        return ResponseEntity.status(HttpStatus.OK).body(searchResults);
    }
}