package org.example.edufymusic.service;

import jakarta.transaction.Transactional;
import org.example.edufymusic.exception.RequestNotValidException;
import org.example.edufymusic.mapper.SongAlbumArtistMapper;
import org.example.edufymusic.model.dto.PostSongDto;
import org.example.edufymusic.model.dto.SongAlbumArtistDto;
import org.example.edufymusic.model.entity.Album;
import org.example.edufymusic.model.entity.AlbumSong;
import org.example.edufymusic.model.entity.Artist;
import org.example.edufymusic.model.entity.Song;
import org.example.edufymusic.exception.ResourceNotFoundException;
import org.example.edufymusic.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class SongServiceImpl implements SongService {
    private final SongRepository songRepository;
    private final SongAlbumArtistMapper songAlbumArtistMapper;
    private final ArtistRepository artistRepository;
    private final AlbumRepository albumRepository;

    @Autowired
    public SongServiceImpl(SongRepository songRepository,
                           SongAlbumArtistMapper songAlbumArtistMapper, ArtistRepository artistRepository,
                           AlbumRepository albumRepository) {
        this.songRepository = songRepository;
        this.songAlbumArtistMapper = songAlbumArtistMapper;
        this.artistRepository = artistRepository;
        this.albumRepository = albumRepository;
    }

    @Override
    @Transactional
    public Song createSong(PostSongDto postSongDto) {
        if (postSongDto.getTitle() == null || postSongDto.getTitle().isBlank()) {
            throw new RequestNotValidException("title is required");
        }
        if (postSongDto.getLengthInSeconds() == 0) {
            throw new RequestNotValidException("lengthInSeconds is required");
        }
        if (postSongDto.getArtistId() == null || postSongDto.getArtistId() == 0) {
            throw new RequestNotValidException("artistId is required");
        }
        if (postSongDto.getGenre() == null) {
            throw new RequestNotValidException("genre is required");
        }
        Artist artist = artistRepository.findById(postSongDto.getArtistId())
                .orElseThrow(() -> new ResourceNotFoundException("Artist with id: " + postSongDto.getArtistId() + " not found"));

        Song song = new Song(
                postSongDto.getTitle(),
                postSongDto.getLengthInSeconds(),
                artist,
                postSongDto.getGenre());

        Long albumId = postSongDto.getAlbumId();
        if (albumId != null && !albumId.equals(0L)) {
            Album album = albumRepository.findById(postSongDto.getAlbumId())
                    .orElseThrow(() -> new ResourceNotFoundException("Album with id: " + postSongDto.getAlbumId() + " not found"));

            AlbumSong  albumSong = new AlbumSong();
            albumSong.setAlbum(album);
            albumSong.setSong(song);

            album.getAlbumSongs().add(albumSong);
            song.getAlbumSongs().add(albumSong);
        }

        songRepository.save(song);
        return song;
    }

    @Override
    public SongAlbumArtistDto getSongById(Long id) {
        Song song = songRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Song Not Found"));
        return songAlbumArtistMapper.toDto(song);
    }


    @Override
    public void updateSong(Long id, PostSongDto postSongDto) {
        Song song = songRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Song Not Found"));

        if (!(postSongDto.getArtistId() == null || postSongDto.getArtistId() == 0)) {
            Artist artist = artistRepository.findById(postSongDto.getArtistId())
                    .orElseThrow(() -> new ResourceNotFoundException("Artist Not Found"));
            song.setArtist(artist);
        }

        Long albumId = postSongDto.getAlbumId();
        if (albumId != null && !albumId.equals(0L)) {
            Album album = albumRepository.findById(postSongDto.getAlbumId())
                    .orElseThrow(() -> new ResourceNotFoundException("Album with " + postSongDto.getAlbumId() + " not found"));

            AlbumSong albumSong = new AlbumSong();
            albumSong.setAlbum(album);
            albumSong.setSong(song);

            song.getAlbumSongs().add(albumSong);
            album.getAlbumSongs().add(albumSong);
        }

        if (!(postSongDto.getTitle() == null)) {
            song.setTitle(postSongDto.getTitle());
        }

        if (!(postSongDto.getLengthInSeconds() == 0)) {
            song.setLengthInSeconds(postSongDto.getLengthInSeconds());
        }

        if (postSongDto.getGenre() != null) {
            song.setGenre(postSongDto.getGenre());
        }

        songRepository.save(song);
    }

    @Override
    @Transactional
    public Song deleteSongById(Long id) {
        Song song = songRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Song Not Found"));

        for (AlbumSong albumSong : song.getAlbumSongs()) {
            Album album = albumSong.getAlbum();
            album.getAlbumSongs().remove(albumSong);
            album.setTracks(album.getAlbumSongs().size());

            int totalLength = album.getAlbumSongs().stream()
                    .mapToInt(as -> as.getSong().getLengthInSeconds())
                    .sum();
            album.setLengthInSeconds(totalLength);
        }

        songRepository.deleteById(id);
        return song;
    }

    @Override
    public List<SongAlbumArtistDto> getAllSongs() {
        List<Song> songs = songRepository.findAll();
        return songAlbumArtistMapper.listToDto(songs);
    }

    @Override
    public SongAlbumArtistDto getRandomSong() {
        List<Song> songs = songRepository.findAll();
        Random randomSong = new Random();
        Song song = songs.get(randomSong.nextInt(songs.size()));
        System.out.println(song.getArtist().getName());
        return songAlbumArtistMapper.toDto(song);
    }

    @Override
    public List<SongAlbumArtistDto> searchResults(SongAlbumArtistDto search) {
        List<Song> songs = songRepository.findAll();
        List<Song> filteredSongs = new ArrayList<>();
        for (Song song : songs) {
            if (song.getTitle().toLowerCase().contains(search.getTitle().toLowerCase())) {
                filteredSongs.add(song);
            }
        }
        return songAlbumArtistMapper.listToDto(filteredSongs);
    }
}
