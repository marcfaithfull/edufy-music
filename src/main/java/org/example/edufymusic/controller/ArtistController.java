package org.example.edufymusic.controller;

import org.example.edufymusic.mapper.ResponseMapper;
import org.example.edufymusic.model.dto.ArtistDto;
import org.example.edufymusic.model.entity.Artist;
import org.example.edufymusic.service.ArtistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/music")
public class ArtistController {
    private final ArtistService artistService;

    @Autowired
    public ArtistController(ArtistService artistService) {
        this.artistService = artistService;
    }

    @PostMapping("/create/artist")
    public ResponseEntity<Map<String, Object>> createArtist(@RequestBody org.example.edufymusic.model.dto.ArtistDto artistDto) {
        Artist artist = artistService.createArtist(artistDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(ResponseMapper.mapResponse(
                201,
                artist.getName() + " has been created",
                "/artist/" + artist.getId()
        ));
    }

    @GetMapping("/artist/{id}")
    public ResponseEntity<org.example.edufymusic.model.dto.ArtistDto> getArtistById(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(artistService.getArtistById(id));
    }

    @PutMapping("/update/artist/{id}")
    public ResponseEntity<Map<String, Object>> updateArtist(@PathVariable Long id, @RequestBody org.example.edufymusic.model.dto.ArtistDto artistDto) {
        artistService.updateArtist(id, artistDto);
        return ResponseEntity.status(HttpStatus.OK).body(ResponseMapper.mapResponse(
                200,
                "Artist with id " + id + " has been updated",
                "/update/artist/" + id
        ));
    }

    @DeleteMapping("/delete/artist/{id}")
    public ResponseEntity<Map<String, Object>> deleteArtist(@PathVariable Long id) {
        Artist artist = artistService.deleteArtistById(id);
        return ResponseEntity.status(HttpStatus.OK).body(ResponseMapper.mapResponse(
                200,
                artist.getName() + " has been deleted as well as all associated songs",
                "/delete/artist/" + id
        ));
    }

    @GetMapping("/artists")
    public ResponseEntity<List<ArtistDto>> getAllArtists() {
        return ResponseEntity.status(HttpStatus.OK).body(artistService.getAllArtists());
    }

    @GetMapping("/search/artist")
    public ResponseEntity<List<ArtistDto>> searchArtist(@RequestBody org.example.edufymusic.model.dto.ArtistDto search) {
        List<org.example.edufymusic.model.dto.ArtistDto> searchResults = artistService.searchResults(search);
        return ResponseEntity.status(HttpStatus.OK).body(searchResults);
    }
}
