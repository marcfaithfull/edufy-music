package org.example.edufymusic.service;

import org.example.edufymusic.exception.RequestNotValidException;
import org.example.edufymusic.exception.ResourceNotFoundException;
import org.example.edufymusic.mapper.AlbumSongMapper;
import org.example.edufymusic.mapper.SearchAlbumsMapper;
import org.example.edufymusic.model.dto.AlbumDto;
import org.example.edufymusic.model.dto.PostAlbumDto;
import org.example.edufymusic.model.dto.AlbumArtistSongDto;
import org.example.edufymusic.model.entity.Album;
import org.example.edufymusic.model.entity.Artist;
import org.example.edufymusic.model.entity.Song;
import org.example.edufymusic.repository.AlbumRepository;
import org.example.edufymusic.repository.ArtistRepository;
import org.example.edufymusic.repository.SongRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class AlbumServiceImpl implements AlbumService {
    AlbumRepository albumRepository;
    ArtistRepository artistRepository;
    SongRepository songRepository;
    AlbumSongMapper albumSongMapper;
    SearchAlbumsMapper searchAlbumsMapper;

    @Autowired
    public AlbumServiceImpl(AlbumRepository albumRepository, ArtistRepository artistRepository, SongRepository songRepository,
                            AlbumSongMapper albumSongMapper, SearchAlbumsMapper searchAlbumsMapper) {
        this.albumRepository = albumRepository;
        this.artistRepository = artistRepository;
        this.songRepository = songRepository;
        this.albumSongMapper = albumSongMapper;
        this.searchAlbumsMapper = searchAlbumsMapper;
    }

    // CRUD

    @Override
    public Album createAlbum(PostAlbumDto postAlbumDto) {
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
        album.setYear(postAlbumDto.getYear());

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

        Set<Song> countSongs = album.getSongs();
        int countedSongs = countSongs.size();
        album.setTracks(countedSongs);

        int totalLength = 0;
        for (Song song : countSongs) {
            int length = song.getLengthInSeconds();
            totalLength = totalLength + (length);
        }
        album.setLength(totalLength);

        albumRepository.save(album);
        return album;
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
        Artist artist = artistRepository.findById(postAlbumDto.getArtistId())
                .orElseThrow(() -> new ResourceNotFoundException("Artist Not Found"));
        if (postAlbumDto.getTitle() != null || postAlbumDto.getTitle().isBlank()) {
            album.setTitle(postAlbumDto.getTitle());
        }
        if (postAlbumDto.getArtistId() != null || postAlbumDto.getArtistId().equals(0L)) {
            album.setArtist(artist);
        }

        if (postAlbumDto.getSongId() != null) {
            for (Song oldSongs : album.getSongs()) {
                oldSongs.getAlbums().remove(album);
            }
            album.getSongs().clear();
            Set<Song> newSongs = new HashSet<>();
            for (Long songId : postAlbumDto.getSongId()) {
                Song song = songRepository.findById(songId)
                        .orElseThrow(() -> new ResourceNotFoundException("Song not found"));
                newSongs.add(song);
                song.getAlbums().add(album);
            }
            album.setSongs(newSongs);

            Set<Song> countSongs = album.getSongs();
            int countedSongs = countSongs.size();
            album.setTracks(countedSongs);

            int totalLength = 0;
            for (Song song : countSongs) {
                int length = song.getLengthInSeconds();
                totalLength = totalLength + (length);
            }
            album.setLength(totalLength);

            albumRepository.save(album);
        }
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
    public List<AlbumDto> searchResults(AlbumDto search) {
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
