package org.example.microservicemusic.service;

import org.example.microservicemusic.model.dto.ArtistDto;

import java.util.List;

public interface ArtistService {

    Long createArtist(ArtistDto artistDto);

    ArtistDto getArtistById(Long id);

    void updateArtist(Long id, ArtistDto artistDto);

    void deleteArtistById(Long id);

    List<ArtistDto> getAllArtists();

    List<ArtistDto> searchResults(ArtistDto search);

}
