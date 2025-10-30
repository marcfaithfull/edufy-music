package org.example.microservicemusic.service;

import org.example.microservicemusic.model.dto.UploadSongDto;
import org.example.microservicemusic.model.dto.SongAlbumArtistDto;
import org.example.microservicemusic.model.dto.SongDto;
import org.example.microservicemusic.model.entity.Song;

import java.util.List;

public interface SongService {

    Long createSong(UploadSongDto uploadSongDto);

    SongAlbumArtistDto getSongById(Long id);

    void updateSong(Long id, UploadSongDto uploadSongDto);

    Song deleteSongById(Long id);

    List<SongAlbumArtistDto> getAllSongs();

    //void likeSong(Long id, Principal principal);

    //void dislikeSong(Long id, Principal principal);

    SongAlbumArtistDto getRandomSong();

    //void randomiseSongStats();

    List<SongAlbumArtistDto> searchResults(SongAlbumArtistDto search);

}
