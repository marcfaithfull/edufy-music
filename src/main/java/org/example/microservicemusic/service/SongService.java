package org.example.microservicemusic.service;

import org.example.microservicemusic.model.dto.SaveSongDto;
import org.example.microservicemusic.model.dto.SongAlbumArtistDto;
import org.example.microservicemusic.model.dto.SongDto;

import java.util.List;

public interface SongService {

    Long createSong(SaveSongDto saveSongDto);

    SongAlbumArtistDto getSongById(Long id);

    void updateSong(Long id, SaveSongDto saveSongDto);

    void deleteSongById(Long id);

    List<SongDto> getAllSongs();

    //void likeSong(Long id, Principal principal);

    //void dislikeSong(Long id, Principal principal);

    SongAlbumArtistDto getRandomSong();

    //void randomiseSongStats();

    List<SongAlbumArtistDto> searchResults(SongAlbumArtistDto search);

}
