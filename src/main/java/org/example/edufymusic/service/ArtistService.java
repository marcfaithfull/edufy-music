package org.example.edufymusic.service;

import org.example.edufymusic.model.entity.Artist;

import java.util.List;

public interface ArtistService {

    Artist createArtist(org.example.edufymusic.model.dto.ArtistDto artistDto);

    org.example.edufymusic.model.dto.ArtistDto getArtistById(Long id);

    void updateArtist(Long id, org.example.edufymusic.model.dto.ArtistDto artistDto);

    Artist deleteArtistById(Long id);

    List<org.example.edufymusic.model.dto.ArtistDto> getAllArtists();

    List<org.example.edufymusic.model.dto.ArtistDto> searchResults(org.example.edufymusic.model.dto.ArtistDto search);

}
