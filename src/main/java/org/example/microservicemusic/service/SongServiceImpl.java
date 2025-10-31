package org.example.microservicemusic.service;

import jakarta.transaction.Transactional;
import org.example.microservicemusic.exception.RequestNotValidException;
import org.example.microservicemusic.mapper.SongAlbumArtistMapper;
import org.example.microservicemusic.model.dto.PostSongDto;
import org.example.microservicemusic.model.dto.SongAlbumArtistDto;
import org.example.microservicemusic.model.entity.Album;
import org.example.microservicemusic.model.entity.Artist;
import org.example.microservicemusic.model.entity.Song;
import org.example.microservicemusic.exception.ResourceNotFoundException;
import org.example.microservicemusic.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class SongServiceImpl implements SongService {
    SongRepository songRepository;
    SongAlbumArtistMapper songAlbumArtistMapper;
    ArtistRepository artistRepository;
    AlbumRepository albumRepository;

    @Autowired
    public SongServiceImpl(SongRepository songRepository,
                           SongAlbumArtistMapper songAlbumArtistMapper, ArtistRepository artistRepository,
                           AlbumRepository albumRepository) {
        this.songRepository = songRepository;
        this.songAlbumArtistMapper = songAlbumArtistMapper;
        this.artistRepository = artistRepository;
        this.albumRepository = albumRepository;
    }

    // CRUD

    @Transactional
    public Song createSong(PostSongDto postSongDto) {
        if (postSongDto.getTitle() == null || postSongDto.getTitle().isBlank()) {
            throw new RequestNotValidException("title is required");
        }
        if (postSongDto.getLength() == 0) {
            throw new RequestNotValidException("length is required");
        }
        if (postSongDto.getArtistId() == null || postSongDto.getArtistId() == 0) {
            throw new RequestNotValidException("artistId is required");
        }
        if (postSongDto.getGenre() == null) {
            throw new RequestNotValidException("genre is required");
        }
        Artist artist = artistRepository.findById(postSongDto.getArtistId())
                .orElseThrow(() -> new ResourceNotFoundException("Artist with " + postSongDto.getArtistId() + " not found"));

        Set<Album> albums = new HashSet<>();
        Long albumId = postSongDto.getAlbumId();
        if (albumId != null && !albumId.equals(0L)) {
            Album album = albumRepository.findById(postSongDto.getAlbumId())
                    .orElseThrow(() -> new ResourceNotFoundException("Album with " + postSongDto.getAlbumId() + " not found"));
            albums.add(album);
        }

        Song song = new Song(
                postSongDto.getTitle(),
                postSongDto.getLength(),
                artist,
                postSongDto.getGenre());

        for (Album album : albums) {
            song.getAlbums().add(album);
            album.getSongs().add(song);
        }

        songRepository.save(song);
        return song;
    }

    public SongAlbumArtistDto getSongById(Long id) {
        Song song = songRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Song Not Found"));
        return songAlbumArtistMapper.toDto(song);
    }

    public void updateSong(Long id, PostSongDto postSongDto) {
        Song song = songRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Song Not Found"));
        if (!(postSongDto.getArtistId() == null || postSongDto.getArtistId() == 0)) {
            Artist artist = artistRepository.findById(postSongDto.getArtistId())
                    .orElseThrow(() -> new ResourceNotFoundException("Artist Not Found"));
            song.setArtist(artist);
        }

        Set<Album> albums = new HashSet<>();
        Long albumId = postSongDto.getAlbumId();
        if (albumId != null && !albumId.equals(0L)) {
            Album album = albumRepository.findById(postSongDto.getAlbumId())
                    .orElseThrow(() -> new ResourceNotFoundException("Album with " + postSongDto.getAlbumId() + " not found"));
            albums.add(album);
        }

        for (Album album : albums) {
            song.getAlbums().add(album);
            album.getSongs().add(song);
        }

        if (!(song.getTitle() == null)) {
            song.setTitle(postSongDto.getTitle());
        }
        if (!(song.getLengthInSeconds() == 0)) {
            song.setLengthInSeconds(postSongDto.getLength());
        }
        if (postSongDto.getGenre() != null) {
            song.setGenre(postSongDto.getGenre());
        }
        songRepository.save(song);
    }

    @Transactional
    public Song deleteSongById(Long id) {
        Song song = songRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Song Not Found"));

        Set<Album> albums = song.getAlbums();
        for (Album album : albums) {
            album.getSongs().remove(song);
            album.setTracks(album.getSongs().size());
            Set<Song> songs = album.getSongs();

            int totalLength = 0;
            for (Song songLength : songs) {
                totalLength += songLength.getLengthInSeconds();
            }
            album.setLength(totalLength);
        }

        songRepository.deleteById(id);
        return song;
    }

    // OTHER ENDPOINTS

    public List<SongAlbumArtistDto> getAllSongs() {
        List<Song> songs = songRepository.findAll();
        return songAlbumArtistMapper.listToDto(songs);
    }

    /*@Override
    public void likeSong(Long id, Principal principal) {
        Song song = songRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Song Not Found"));
        User principalUser = userRepository.findByUsername(principal.getName());
        User databaseUser = userRepository.findById(principalUser.getId())
                .orElseThrow(() -> new ResourceNotFoundException("User Not Found"));

        UserSongReaction userSongReaction = new UserSongReaction();
        userSongReaction.setUser(databaseUser);
        userSongReaction.setSong(song);
        userSongReaction.setReaction(Reaction.LIKE);
        userSongReactionRepository.save(userSongReaction);
    }

    @Override
    public void dislikeSong(Long id, Principal principal) {
        Song song = songRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Song Not Found"));
        User principalUser = userRepository.findByUsername(principal.getName());
        User databaseUser = userRepository.findById(principalUser.getId())
                .orElseThrow(() -> new ResourceNotFoundException("User Not Found"));

        UserSongReaction userSongReaction = new UserSongReaction();
        userSongReaction.setUser(databaseUser);
        userSongReaction.setSong(song);
        userSongReaction.setReaction(Reaction.DISLIKE);
        userSongReactionRepository.save(userSongReaction);
    }*/

    @Override
    public SongAlbumArtistDto getRandomSong() {
        List<Song> songs = songRepository.findAll();
        Random randomSong = new Random();
        Song song = songs.get(randomSong.nextInt(songs.size()));
        System.out.println(song.getArtist().getName());
        return songAlbumArtistMapper.toDto(song);
    }

    /*@Override
    public void randomiseSongStats() {
        List<Song> songs = songRepository.findAll();
        Random randomSong = new Random();
        Song song = songs.get(randomSong.nextInt(songs.size()));

        List<User> users = userRepository.findAll();
        Random randomUser = new Random();
        User user = users.get(randomUser.nextInt(users.size()));

        Random randomReaction = new Random();
        Reaction[] reactions = Reaction.values();
        Reaction reaction = reactions[randomReaction.nextInt(reactions.length)];

        UserSongReaction userSongReaction = new UserSongReaction();
        userSongReaction.setSong(song);
        userSongReaction.setUser(user);
        userSongReaction.setReaction(reaction);
        userSongReactionRepository.save(userSongReaction);
    }*/

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
