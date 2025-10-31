package org.example.microservicemusic.controller;

import org.example.microservicemusic.mapper.ResponseMapper;
import org.example.microservicemusic.model.dto.AlbumDto;
import org.example.microservicemusic.model.dto.PostAlbumDto;
import org.example.microservicemusic.model.dto.AlbumArtistSongDto;
import org.example.microservicemusic.model.entity.Album;
import org.example.microservicemusic.service.AlbumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class AlbumController {
    private final AlbumService albumService;

    @Autowired
    public AlbumController(AlbumService albumService) {
        this.albumService = albumService;
    }

    // CRUD

    @Secured("ROLE_ADMIN")
    @PostMapping("/create/album")
    public ResponseEntity<Map<String, Object>> createAlbum(@RequestBody PostAlbumDto postAlbumDto) {
        Long id = albumService.createAlbum(postAlbumDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(ResponseMapper.mapResponse(
                201,
                "Album with id " + id + " has been created",
                "/album/" + id
        ));
    }

    @Secured("ROLE_USER")
    @GetMapping("/album/{id}")
    public ResponseEntity<AlbumArtistSongDto> getAlbumById(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(albumService.getAlbumById(id));
    }

    @Secured("ROLE_ADMIN")
    @PutMapping("/update/album/{id}")
    public ResponseEntity<Map<String, Object>> updateAlbum(@PathVariable Long id, @RequestBody PostAlbumDto postAlbumDto) {
        albumService.updateAlbum(id, postAlbumDto);
        return ResponseEntity.status(HttpStatus.OK).body(ResponseMapper.mapResponse(
                200,
                "Album with id " + id + " has been updated",
                "/update/album/" + id
        ));
    }

    @Secured("ROLE_ADMIN")
    @DeleteMapping("/delete/album/{id}")
    public ResponseEntity<Map<String, Object>> deleteAlbum(@PathVariable Long id) {
        Album album = albumService.deleteAlbumById(id);
        return ResponseEntity.status(HttpStatus.OK).body(ResponseMapper.mapResponse(
                200,
                album.getTitle() + " has been deleted",
                "/delete/album/" + id
        ));
    }

    // OTHER ENDPOINTS

    @Secured("ROLE_USER")
    @GetMapping("/albums")
    public ResponseEntity<List<AlbumArtistSongDto>> getAllAlbums() {
        return ResponseEntity.status(HttpStatus.OK).body(albumService.getAllAlbums());
    }

    @Secured("ROLE_USER")
    @PostMapping("/search/album")
    public ResponseEntity<List<AlbumDto>> searchAlbum(@RequestBody AlbumDto search) {
        List<AlbumDto> searchResults = albumService.searchResults(search);
        return ResponseEntity.status(HttpStatus.OK).body(searchResults);
    }
}
