package org.example.microservicemusic.service;

import org.example.microservicemusic.model.dto.AlbumDto;
import org.example.microservicemusic.model.dto.PostAlbumDto;
import org.example.microservicemusic.model.dto.AlbumArtistSongDto;
import org.example.microservicemusic.model.entity.Album;

import java.util.List;

public interface AlbumService {

    Album createAlbum(PostAlbumDto postAlbumDto);

    AlbumArtistSongDto getAlbumById(Long id);

    void updateAlbum(Long id, PostAlbumDto postAlbumDto);

    Album deleteAlbumById(Long id);

    List<AlbumArtistSongDto> getAllAlbums();

    List<AlbumDto> searchResults(AlbumDto search);
}
