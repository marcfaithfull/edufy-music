package org.example.microservicemusic.service;

import org.example.microservicemusic.exception.RequestNotValidException;
import org.example.microservicemusic.exception.ResourceNotFoundException;
import org.example.microservicemusic.mapper.ArtistMapper;
import org.example.microservicemusic.model.dto.ArtistDto;
import org.example.microservicemusic.model.entity.Artist;
import org.example.microservicemusic.repository.ArtistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ArtistServiceImpl implements ArtistService {
    ArtistRepository artistRepository;
    ArtistMapper artistMapper;

    @Autowired
    public ArtistServiceImpl(ArtistRepository artistRepository, ArtistMapper artistMapper) {
        this.artistRepository = artistRepository;
        this.artistMapper = artistMapper;
    }

    // CRUD

    @Override
    public Artist createArtist(ArtistDto artistDto) {
        if (artistDto.getName() == null || artistDto.getName().isBlank()) {
            throw new RequestNotValidException("\"title\" is required");
        }
        Artist artist = new Artist();
        artist.setName(artistDto.getName());
        artistRepository.save(artist);
        return artist;
    }

    @Override
    public ArtistDto getArtistById(Long id) {
        Artist artist = artistRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Artist not found"));
        return artistMapper.toDto(artist);
    }

    @Override
    public void updateArtist(Long id, ArtistDto artistDto) {
        Artist artist = artistRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Artist not found"));
        artist.setName(artistDto.getName());
        artistRepository.save(artist);
    }

    @Override
    public Artist deleteArtistById(Long id) {
        Artist artist = artistRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Artist not found"));
        artistRepository.deleteById(id);
        return artist;
    }

    // OTHER ENDPOINTS

    @Override
    public List<ArtistDto> getAllArtists() {
        List<Artist> artists = artistRepository.findAll();
        return artistMapper.listToDto(artists);
    }

    @Override
    public List<ArtistDto> searchResults(ArtistDto search) {
        List<Artist> artists = artistRepository.findAll();
        List<Artist> filteredArtists = new ArrayList<>();
        for (Artist artist : artists) {
            if (artist.getName().toLowerCase().contains(search.getName().toLowerCase())) {
                filteredArtists.add(artist);
            }
        }
        return artistMapper.listToDto(filteredArtists);
    }
}
