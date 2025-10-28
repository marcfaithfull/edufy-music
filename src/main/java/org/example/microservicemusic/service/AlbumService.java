package org.example.microservicemusic.service;

import org.example.microservicemusic.model.dto.AlbumDto;

import java.util.List;

public interface AlbumService {

    Long createAlbum(AlbumDto albumDto);

    AlbumDto getAlbumById(Long id);

    void updateAlbum(Long id, AlbumDto albumDto);

    void deleteAlbumById(Long id);

    List<AlbumDto> getAllAlbums();

    List<AlbumDto> searchResults(AlbumDto search);
}
