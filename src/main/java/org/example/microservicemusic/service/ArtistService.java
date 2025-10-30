package org.example.microservicemusic.service;

import org.example.microservicemusic.model.dto.ArtistDto;
import org.example.microservicemusic.model.entity.Artist;

import java.util.List;

public interface ArtistService {

    Artist createArtist(ArtistDto artistDto);

    ArtistDto getArtistById(Long id);

    void updateArtist(Long id, ArtistDto artistDto);

    Artist deleteArtistById(Long id);

    List<ArtistDto> getAllArtists();

    List<ArtistDto> searchResults(ArtistDto search);

}
