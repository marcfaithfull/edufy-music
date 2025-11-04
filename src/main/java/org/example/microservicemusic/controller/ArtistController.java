package org.example.microservicemusic.controller;

import org.example.microservicemusic.mapper.ResponseMapper;
import org.example.microservicemusic.model.dto.ArtistDto;
import org.example.microservicemusic.model.entity.Artist;
import org.example.microservicemusic.service.ArtistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class ArtistController {
    private final ArtistService artistService;

    @Autowired
    public ArtistController(ArtistService artistService) {
        this.artistService = artistService;
    }

    // CRUD

    @Secured("ROLE_ADMIN")
    @PostMapping("/create/artist")
    public ResponseEntity<Map<String, Object>> createArtist(@RequestBody ArtistDto artistDto) {
        Artist artist = artistService.createArtist(artistDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(ResponseMapper.mapResponse(
                201,
                artist.getName() + " has been created",
                "/artist/" + artist.getId()
        ));
    }

    @Secured("ROLE_USER")
    @GetMapping("/artist/{id}")
    public ResponseEntity<ArtistDto> getArtistById(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(artistService.getArtistById(id));
    }

    @Secured("ROLE_ADMIN")
    @PutMapping("/update/artist/{id}")
    public ResponseEntity<Map<String, Object>> updateArtist(@PathVariable Long id, @RequestBody ArtistDto artistDto) {
        artistService.updateArtist(id, artistDto);
        return ResponseEntity.status(HttpStatus.OK).body(ResponseMapper.mapResponse(
                200,
                "Artist with id " + id + " has been updated",
                "/update/artist/" + id
        ));
    }

    @Secured("ROLE_ADMIN")
    @DeleteMapping("/delete/artist/{id}")
    public ResponseEntity<Map<String, Object>> deleteArtist(@PathVariable Long id) {
        Artist artist = artistService.deleteArtistById(id);
        return ResponseEntity.status(HttpStatus.OK).body(ResponseMapper.mapResponse(
                200,
                artist.getName() + " has been deleted as well as all associated songs",
                "/delete/artist/" + id
        ));
    }

    // OTHER ENDPOINTS

    @Secured("ROLE_USER")
    @GetMapping("/artists")
    public ResponseEntity<List<ArtistDto>> getAllArtists() {
        return ResponseEntity.status(HttpStatus.OK).body(artistService.getAllArtists());
    }

    @Secured("ROLE_USER")
    @PostMapping("/search/artist")
    public ResponseEntity<List<ArtistDto>> searchArtist(@RequestBody ArtistDto search) {
        List<ArtistDto> searchResults = artistService.searchResults(search);
        return ResponseEntity.status(HttpStatus.OK).body(searchResults);
    }
}
