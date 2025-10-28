package org.example.microservicemusic.service;

import org.example.microservicemusic.exception.RequestNotValidException;
import org.example.microservicemusic.mapper.AlbumMapper;
import org.example.microservicemusic.model.dto.AlbumDto;
import org.example.microservicemusic.model.entity.Album;
import org.example.microservicemusic.repository.AlbumRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AlbumServiceImpl implements AlbumService {
    AlbumRepository albumRepository;
    AlbumMapper albumMapper;

    @Autowired
    public AlbumServiceImpl(AlbumRepository albumRepository, AlbumMapper albumMapper) {
        this.albumRepository = albumRepository;
        this.albumMapper = albumMapper;
    }

    // CRUD

    @Override
    public Long createAlbum(AlbumDto albumDto) {
        if (albumDto.getTitle() == null || albumDto.getTitle().isBlank()) {
            throw new RequestNotValidException("\"title\" is required");
        }
        Album album = new Album();
        album.setTitle(albumDto.getTitle());
        albumRepository.save(album);
        return album.getId();
    }

    @Override
    public AlbumDto getAlbumById(Long id) {
        Album album = albumRepository.findById(id)
                .orElseThrow(() -> new RequestNotValidException("Album Not Found"));
        return albumMapper.toDto(album);
    }

    @Override
    public void updateAlbum(Long id, AlbumDto albumDto) {
        Album album = albumRepository.findById(id)
                .orElseThrow(() -> new RequestNotValidException("Album Not Found"));
        album.setTitle(albumDto.getTitle());
        albumRepository.save(album);
    }

    @Override
    public void deleteAlbumById(Long id) {
        Album album = albumRepository.findById(id)
                .orElseThrow(() -> new RequestNotValidException("Album Not Found"));
        albumRepository.delete(album);
    }

    // OTHER ENDPOINTS

    @Override
    public List<AlbumDto> getAllAlbums() {
        List<Album> albums = albumRepository.findAll();
        return albumMapper.listToDto(albums);
    }

    @Override
    public List<AlbumDto> searchResults(AlbumDto search) {
        List<Album> albums = albumRepository.findAll();
        List<Album> filteredAlbums = new ArrayList<>();
        for (Album album : albums) {
            if (album.getTitle().toLowerCase().contains(search.getTitle().toLowerCase())) {
                filteredAlbums.add(album);
            }
        }
        return albumMapper.listToDto(filteredAlbums);
    }
}
