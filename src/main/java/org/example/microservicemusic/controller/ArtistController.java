package org.example.microservicemusic.controller;

import org.example.microservicemusic.mapper.ResponseMapper;
import org.example.microservicemusic.model.dto.ArtistDto;
import org.example.microservicemusic.service.ArtistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/edufy/music")
public class ArtistController {
    private final ArtistService artistService;

    @Autowired
    public ArtistController(ArtistService artistService) {
        this.artistService = artistService;
    }

    // CRUD

    @Secured("ROLE_USER")
    @PostMapping("/create/artist")
    public ResponseEntity<Map<String, Object>> createArtist(@RequestBody ArtistDto artistDto) {
        Long id = artistService.createArtist(artistDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(ResponseMapper.mapResponse(
                201,
                "Artist with id " + id + " has been created",
                "/create/artist"
        ));
    }

    @Secured("ROLE_USER")
    @GetMapping("/find/artist/{id}")
    public ResponseEntity<ArtistDto> getArtistById(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(artistService.getArtistById(id));
    }

    @Secured("ROLE_USER")
    @PutMapping("/update/artist/{id}")
    public ResponseEntity<Map<String, Object>> updateArtist(@PathVariable Long id, @RequestBody ArtistDto artistDto) {
        artistService.updateArtist(id, artistDto);
        return ResponseEntity.status(HttpStatus.OK).body(ResponseMapper.mapResponse(
                200,
                "Artist with id " + " has been updated",
                "/update/artist/" + id
        ));
    }

    @Secured("ROLE_USER")
    @DeleteMapping("/delete/artist/{id}")
    public ResponseEntity<Map<String, Object>> deleteArtist(@PathVariable Long id) {
        artistService.deleteArtistById(id);
        return ResponseEntity.status(HttpStatus.OK).body(ResponseMapper.mapResponse(
                200,
                "Artist with id " + " has been deleted",
                "/delete/artist/" + id
        ));
    }

    // OTHER ENDPOINTS

    @Secured("ROLE_USER")
    @GetMapping("/artists")
    public ResponseEntity<List<ArtistDto>> getAllArtists() {
        return ResponseEntity.status(HttpStatus.OK).body(artistService.getAllArtists());
    }

    /*@PutMapping("/like/artist/{id}")
    public ResponseEntity<Map<String, Object>> likeArtist(@PathVariable Long id) {
        artistService.likeArtist(id);
        return ResponseEntity.status(HttpStatus.OK).body(ResponseMapper.mapResponse(
                200,
                "You liked song with id " + id
        ));
    }

    @PutMapping("/dislike/song/{id}")
    public ResponseEntity<Map<String, Object>> dislikeArtist(@PathVariable Long id) {
        artistService.dislikeArtist(id);
        return ResponseEntity.status(HttpStatus.OK).body(ResponseMapper.mapResponse(
                200,
                "You disliked song with id " + id
        ));
    }

    @PutMapping("/randomise/artist")
    public ResponseEntity<Map<String, Object>> randomiseArtist() {
        artistService.randomiseArtistStats();
        return ResponseEntity.status(HttpStatus.OK).body(ResponseMapper.mapResponse(
                200,
                "You randomised the album stats"
        ));
    }*/

    @Secured("ROLE_USER")
    @PostMapping("/search/artist")
    public ResponseEntity<List<ArtistDto>> searchArtist(@RequestBody ArtistDto search) {
        List<ArtistDto> searchResults = artistService.searchResults(search);
        return ResponseEntity.status(HttpStatus.OK).body(searchResults);
    }
}
