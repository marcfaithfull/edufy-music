package org.example.microservicemusic.controller;

import org.example.microservicemusic.mapper.ResponseMapper;
import org.example.microservicemusic.model.dto.AlbumDto;
import org.example.microservicemusic.service.AlbumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/edufy/music")
public class AlbumController {
    private final AlbumService albumService;

    @Autowired
    public AlbumController(AlbumService albumService) {
        this.albumService = albumService;
    }

    // CRUD

    @Secured("ROLE_USER")
    @PostMapping("/create/albuminfo")
    public ResponseEntity<Map<String, Object>> createAlbum(@RequestBody AlbumDto albumDto) {
        Long id = albumService.createAlbum(albumDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(ResponseMapper.mapResponse(
                201,
                "Album with id " + id + " has been created",
                "/create/albuminfo/" + id
        ));
    }

    @Secured("ROLE_USER")
    @GetMapping("/read/album/{id}")
    public ResponseEntity<AlbumDto> getAlbumById(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(albumService.getAlbumById(id));
    }

    @Secured("ROLE_USER")
    @PutMapping("/update/album/{id}")
    public ResponseEntity<Map<String, Object>> updateAlbum(@PathVariable Long id, @RequestBody AlbumDto albumDto) {
        albumService.updateAlbum(id, albumDto);
        return ResponseEntity.status(HttpStatus.OK).body(ResponseMapper.mapResponse(
                200,
                "Album with id " + id + " has been updated",
                "/update/album/" + id
        ));
    }

    @Secured("ROLE_USER")
    @DeleteMapping("/delete/album/{id}")
    public ResponseEntity<Map<String, Object>> deleteAlbum(@PathVariable Long id) {
        albumService.deleteAlbumById(id);
        return ResponseEntity.status(HttpStatus.OK).body(ResponseMapper.mapResponse(
                200,
                "Album with id " + id + " has been deleted",
                "/delete/album/" + id
        ));
    }

    // OTHER ENDPOINTS

    @Secured("ROLE_USER")
    @GetMapping("/albums")
    public ResponseEntity<List<AlbumDto>> getAllAlbums() {
        return ResponseEntity.status(HttpStatus.OK).body(albumService.getAllAlbums());
    }

    @Secured("ROLE_USER")
    @PostMapping("/search/album")
    public ResponseEntity<List<AlbumDto>> searchAlbum(@RequestBody AlbumDto search) {
        List<AlbumDto> searchResults = albumService.searchResults(search);
        return ResponseEntity.status(HttpStatus.OK).body(searchResults);
    }
}
