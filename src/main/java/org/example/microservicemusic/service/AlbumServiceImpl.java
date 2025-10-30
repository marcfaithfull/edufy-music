package org.example.microservicemusic.service;

import org.example.microservicemusic.exception.RequestNotValidException;
import org.example.microservicemusic.exception.ResourceNotFoundException;
import org.example.microservicemusic.mapper.AlbumMapper;
import org.example.microservicemusic.mapper.AlbumSongMapper;
import org.example.microservicemusic.mapper.SearchAlbumsMapper;
import org.example.microservicemusic.model.dto.SearchAlbumDto;
import org.example.microservicemusic.model.dto.PostAlbumDto;
import org.example.microservicemusic.model.dto.AlbumArtistSongDto;
import org.example.microservicemusic.model.entity.Album;
import org.example.microservicemusic.model.entity.Artist;
import org.example.microservicemusic.model.entity.Song;
import org.example.microservicemusic.repository.AlbumRepository;
import org.example.microservicemusic.repository.ArtistRepository;
import org.example.microservicemusic.repository.SongRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class AlbumServiceImpl implements AlbumService {
    AlbumRepository albumRepository;
    AlbumMapper albumMapper;
    ArtistRepository artistRepository;
    SongRepository songRepository;
    AlbumSongMapper albumSongMapper;
    SearchAlbumsMapper searchAlbumsMapper;

    @Autowired
    public AlbumServiceImpl(AlbumRepository albumRepository, AlbumMapper albumMapper, ArtistRepository artistRepository,
                            SongRepository songRepository, AlbumSongMapper albumSongMapper, SearchAlbumsMapper searchAlbumsMapper) {
        this.albumRepository = albumRepository;
        this.albumMapper = albumMapper;
        this.artistRepository = artistRepository;
        this.songRepository = songRepository;
        this.albumSongMapper = albumSongMapper;
        this.searchAlbumsMapper = searchAlbumsMapper;
    }

    // CRUD

    @Override
    public Long createAlbum(PostAlbumDto postAlbumDto) {
        if (postAlbumDto.getTitle() == null || postAlbumDto.getTitle().isBlank()) {
            throw new RequestNotValidException("title is required");
        }
        if (postAlbumDto.getArtistId() == null || postAlbumDto.getArtistId().equals(0L)) {
            throw new RequestNotValidException("artistId is required");
        }
        Artist artist = artistRepository.findById(postAlbumDto.getArtistId())
                .orElseThrow(() -> new ResourceNotFoundException("Artist not found"));

        Album album = new Album();
        album.setTitle(postAlbumDto.getTitle());
        album.setArtist(artist);

        Set<Song> songs = new HashSet<>();

        if (postAlbumDto.getSongId() != null) {
            for (Long songId : postAlbumDto.getSongId()) {
                Song song = songRepository.findById(songId)
                        .orElseThrow(() -> new ResourceNotFoundException("Song not found"));
                songs.add(song);
                song.getAlbums().add(album);
            }
        }
        album.setSongs(songs);
        albumRepository.save(album);
        return album.getId();
    }

    @Override
    public AlbumArtistSongDto getAlbumById(Long id) {
        Album album = albumRepository.findByIdWithSongs(id)
                .orElseThrow(() -> new RequestNotValidException("Album Not Found"));
        return albumSongMapper.toDto(album);
    }

    @Override
    public void updateAlbum(Long id, PostAlbumDto postAlbumDto) {
        Album album = albumRepository.findById(id)
                .orElseThrow(() -> new RequestNotValidException("Album Not Found"));
        album.setTitle(postAlbumDto.getTitle());
        albumRepository.save(album);
    }

    @Override
    public Album deleteAlbumById(Long id) {
        Album album = albumRepository.findById(id)
                .orElseThrow(() -> new RequestNotValidException("Album Not Found"));
        albumRepository.delete(album);
        return  album;
    }

    // OTHER ENDPOINTS

    @Override
    public List<AlbumArtistSongDto> getAllAlbums() {
        List<Album> albums = albumRepository.findAll();
        return albumSongMapper.listToDto(albums);
    }

    @Override
    public List<SearchAlbumDto> searchResults(SearchAlbumDto search) {
        List<Album> albums = albumRepository.findAll();
        List<Album> filteredAlbums = new ArrayList<>();
        for (Album album : albums) {
            if (album.getTitle().toLowerCase().contains(search.getTitle().toLowerCase())) {
                filteredAlbums.add(album);
            }
        }
        return searchAlbumsMapper.listToDto(filteredAlbums);
    }
}
