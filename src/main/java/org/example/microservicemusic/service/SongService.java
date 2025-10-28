package org.example.microservicemusic.service;

import org.example.microservicemusic.model.dto.SongDto;

import java.security.Principal;
import java.util.List;

public interface SongService {

    Long createSong(SongDto songDto);

    SongDto getSongById(Long id);

    void updateSong(Long id, SongDto songDto);

    void deleteSongById(Long id);

    List<SongDto> getAllSongs();

    void likeSong(Long id, Principal principal);

    void dislikeSong(Long id, Principal principal);

    void randomiseSongStats();

    List<SongDto> searchResults(SongDto search);

}
