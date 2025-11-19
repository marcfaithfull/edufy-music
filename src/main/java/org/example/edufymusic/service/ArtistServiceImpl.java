package org.example.edufymusic.service;

import org.example.edufymusic.exception.RequestNotValidException;
import org.example.edufymusic.exception.ResourceNotFoundException;
import org.example.edufymusic.mapper.ArtistMapper;
import org.example.edufymusic.model.entity.Artist;
import org.example.edufymusic.repository.ArtistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ArtistServiceImpl implements ArtistService {
    private final ArtistRepository artistRepository;
    private final ArtistMapper artistMapper;

    @Autowired
    public ArtistServiceImpl(ArtistRepository artistRepository, ArtistMapper artistMapper) {
        this.artistRepository = artistRepository;
        this.artistMapper = artistMapper;
    }

    // CRUD

    @Override
    public Artist createArtist(org.example.edufymusic.model.dto.ArtistDto artistDto) {
        if (artistDto.getName() == null || artistDto.getName().isBlank()) {
            throw new RequestNotValidException("\"title\" is required");
        }
        Artist artist = new Artist();
        artist.setName(artistDto.getName());
        artist.setGenre(artistDto.getGenre());
        artistRepository.save(artist);
        return artist;
    }

    @Override
    public org.example.edufymusic.model.dto.ArtistDto getArtistById(Long id) {
        Artist artist = artistRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Artist not found"));
        return artistMapper.toDto(artist);
    }

    @Override
    public void updateArtist(Long id, org.example.edufymusic.model.dto.ArtistDto artistDto) {
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
    public List<org.example.edufymusic.model.dto.ArtistDto> getAllArtists() {
        List<Artist> artists = artistRepository.findAll();
        return artistMapper.listToDto(artists);
    }

    @Override
    public List<org.example.edufymusic.model.dto.ArtistDto> searchResults(org.example.edufymusic.model.dto.ArtistDto search) {
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
