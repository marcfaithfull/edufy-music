package org.example.edufymusic.service;

import org.example.edufymusic.model.dto.PostSongDto;
import org.example.edufymusic.model.dto.SongAlbumArtistDto;
import org.example.edufymusic.model.entity.Song;

import java.util.List;

public interface SongService {

    Song createSong(PostSongDto postSongDto);

    SongAlbumArtistDto getSongById(Long id);

    void updateSong(Long id, PostSongDto postSongDto);

    Song deleteSongById(Long id);

    List<SongAlbumArtistDto> getAllSongs();

    SongAlbumArtistDto getRandomSong();

    List<SongAlbumArtistDto> searchResults(SongAlbumArtistDto search);

}
