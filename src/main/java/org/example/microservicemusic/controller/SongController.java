package org.example.microservicemusic.controller;

import org.example.microservicemusic.model.dto.SaveSongDto;
import org.example.microservicemusic.model.dto.SongAlbumArtistDto;
import org.example.microservicemusic.mapper.ResponseMapper;
import org.example.microservicemusic.model.dto.SongDto;
import org.example.microservicemusic.service.SongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

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

    @Secured("ROLE_ADMIN")
    @PostMapping("/create/song")
    public ResponseEntity<Map<String, Object>> createSong(@RequestBody SaveSongDto saveSongDto) {
        Long id = songService.createSong(saveSongDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(ResponseMapper.mapResponse(
                201,
                "Song with id " + id + " has been created"
        ));
    }

    @Secured("ROLE_USER")
    @GetMapping("/play/song/{id}")
    public ResponseEntity<Map<String, Object>> getSongById(@PathVariable Long id) {
        SongAlbumArtistDto songAlbumArtistDto = songService.getSongById(id);
        return ResponseEntity.status(HttpStatus.OK).body(ResponseMapper.mapResponse(
                200,
                songAlbumArtistDto.getTitle() + " by " + songAlbumArtistDto.getArtist().getName() + " is currently playing"
        ));
    }

    @Secured("ROLE_ADMIN")
    @PutMapping("/update/song/{id}")
    public ResponseEntity<Map<String, Object>> updateSongById(@PathVariable Long id, @RequestBody SaveSongDto saveSongDto) {
        songService.updateSong(id, saveSongDto);
        return ResponseEntity.status(HttpStatus.OK).body(ResponseMapper.mapResponse(
                200,
                "Song with id " + id + " has been updated"
        ));
    }

    @Secured("ROLE_ADMIN")
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

    /*@Secured("ROLE_USER")
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
    }*/

    @Secured("ROLE_USER")
    @GetMapping("/randomise/song")
    public ResponseEntity<Map<String, Object>> getRandomSong() {
        SongAlbumArtistDto randomSong = songService.getRandomSong();
        return ResponseEntity.status(HttpStatus.OK).body(ResponseMapper.mapResponse(
                200,
                randomSong.getTitle() + " by " + randomSong.getArtist().getName() + " is currently playing"
        ));
    }

    /*@Secured("ROLE_USER")
    @PutMapping("/randomise/song/stats")
    public ResponseEntity<Map<String, Object>> randomiseSongStats() {
        songService.randomiseSongStats();
        return ResponseEntity.status(HttpStatus.OK).body(ResponseMapper.mapResponse(
                200,
                "You randomised the song stats"
        ));
    }*/

    @Secured("ROLE_USER")
    @PostMapping("/search/song")
    public ResponseEntity<List<SongAlbumArtistDto>> searchSong(@RequestBody SongAlbumArtistDto search) {
        List<SongAlbumArtistDto> searchResults = songService.searchResults(search);
        return ResponseEntity.status(HttpStatus.OK).body(searchResults);
    }
}