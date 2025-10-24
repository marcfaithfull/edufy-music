package org.example.microservicemusic.service;

import org.example.microservicemusic.model.dto.SongDto;

import java.util.List;

public interface SongService {

    Long createSong(SongDto songDto);

    SongDto getSongById(Long id);

    void updateSong(Long id, SongDto songDto);

    void deleteSongById(Long id);

    List<SongDto> getAllSongs();

    void likeSong(Long id);

    void dislikeSong(Long id);

    void randomiseSongStats();

    List<SongDto> searchResults(SongDto search);
}
