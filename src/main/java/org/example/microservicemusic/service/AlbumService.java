package org.example.microservicemusic.service;

import org.example.microservicemusic.model.dto.SearchAlbumDto;
import org.example.microservicemusic.model.dto.PostAlbumDto;
import org.example.microservicemusic.model.dto.AlbumArtistSongDto;
import org.example.microservicemusic.model.entity.Album;

import java.util.List;

public interface AlbumService {

    Long createAlbum(PostAlbumDto postAlbumDto);

    AlbumArtistSongDto getAlbumById(Long id);

    void updateAlbum(Long id, PostAlbumDto postAlbumDto);

    Album deleteAlbumById(Long id);

    List<AlbumArtistSongDto> getAllAlbums();

    List<SearchAlbumDto> searchResults(SearchAlbumDto search);
}
