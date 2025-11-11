package org.example.edufymusic.service;

import org.example.edufymusic.model.dto.AlbumDto;
import org.example.edufymusic.model.dto.PostAlbumDto;
import org.example.edufymusic.model.dto.AlbumArtistSongDto;
import org.example.edufymusic.model.entity.Album;

import java.util.List;

public interface AlbumService {

    Album createAlbum(PostAlbumDto postAlbumDto);

    AlbumArtistSongDto getAlbumById(Long id);

    void updateAlbum(Long id, PostAlbumDto postAlbumDto);

    Album deleteAlbumById(Long id);

    List<AlbumArtistSongDto> getAllAlbums();

    List<AlbumDto> searchResults(AlbumDto search);
}
