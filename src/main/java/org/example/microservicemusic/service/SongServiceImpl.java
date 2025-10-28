package org.example.microservicemusic.service;

import org.example.microservicemusic.exception.RequestNotValidException;
import org.example.microservicemusic.model.dto.SongDto;
import org.example.microservicemusic.model.entity.User;
import org.example.microservicemusic.model.entity.Artist;
import org.example.microservicemusic.model.entity.Song;
import org.example.microservicemusic.exception.ResourceNotFoundException;
import org.example.microservicemusic.mapper.SongMapper;
import org.example.microservicemusic.model.entity.UserSongReaction;
import org.example.microservicemusic.model.enumeration.Reaction;
import org.example.microservicemusic.repository.ArtistRepository;
import org.example.microservicemusic.repository.SongRepository;
import org.example.microservicemusic.repository.UserRepository;
import org.example.microservicemusic.repository.UserSongReactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class SongServiceImpl implements SongService {
    SongRepository songRepository;
    SongMapper songMapper;
    ArtistRepository artistRepository;
    UserRepository userRepository;
    UserSongReactionRepository userSongReactionRepository;

    @Autowired
    public SongServiceImpl(SongRepository songRepository, SongMapper songMapper, ArtistRepository artistRepository,
                           UserRepository userRepository, UserSongReactionRepository userSongReactionRepository) {
        this.songRepository = songRepository;
        this.songMapper = songMapper;
        this.artistRepository = artistRepository;
        this.userRepository = userRepository;
        this.userSongReactionRepository = userSongReactionRepository;
    }

    // CRUD

    public Long createSong(SongDto songDto) {
        if (songDto.getTitle() == null || songDto.getTitle().isBlank()) {
            throw new RequestNotValidException("title is required");
        }
        if (songDto.getLength() == null) {
            throw new RequestNotValidException("length is required");
        }
        if (songDto.getArtistId() == null || songDto.getArtistId() == 0) {
            throw new RequestNotValidException("artistId is required");
        }
        if (songDto.getGenre() == null) {
            throw new RequestNotValidException("genre is required");
        }
        Artist artist = artistRepository.findById(songDto.getArtistId())
                .orElseThrow(() -> new ResourceNotFoundException("Artist with " + songDto.getArtistId() + " not found"));

        Song song = new Song(
                songDto.getTitle(),
                songDto.getLength(),
                artist,
                songDto.getGenre());

        songRepository.save(song);
        return song.getId();
    }

    public SongDto getSongById(Long id) {
        Song song = songRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Song Not Found"));
        return songMapper.toDto(song);
    }

    public void updateSong(Long id, SongDto songDto) {
        Song song = songRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Song Not Found"));
        if (!(songDto.getArtistId() == null || songDto.getArtistId() == 0)) {
            Artist artist = artistRepository.findById(songDto.getArtistId())
                    .orElseThrow(() -> new ResourceNotFoundException("Artist Not Found"));
            song.setArtist(artist);
        }
        if (!(song.getTitle() == null)) {
            song.setTitle(songDto.getTitle());
        }
        if (!(song.getLength() == null)) {
            song.setLength(songDto.getLength());
        }
        if (songDto.getGenre() != null) {
            song.setGenre(songDto.getGenre());
        }
        songRepository.save(song);
    }

    public void deleteSongById(Long id) {
        songRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Song Not Found"));
        songRepository.deleteById(id);
    }

    // OTHER ENDPOINTS

    public List<SongDto> getAllSongs() {
        List<Song> songs = songRepository.findAll();
        return songMapper.listToDto(songs);
    }

    @Override
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
    }

    @Override
    public void randomiseSongStats() {
        List<Song> songs = songRepository.findAll();
        Random randomSong = new Random();
        Song song = songs.get(randomSong.nextInt(songs.size()));

        List<User> users = userRepository.findAll();
        Random randomUser = new Random();
        User user = users.get(randomUser.nextInt(users.size()));

        Random randomReaction = new Random();
        Reaction[] reactions = Reaction.values();

        UserSongReaction userSongReaction = new UserSongReaction();
        userSongReaction.setSong(song);
        userSongReaction.setUser(user);
        userSongReaction.setReaction(reactions[randomReaction.nextInt(reactions.length)]);
        userSongReactionRepository.save(userSongReaction);
    }

    @Override
    public List<SongDto> searchResults(SongDto search) {
        List<Song> songs = songRepository.findAll();
        List<Song> filteredSongs = new ArrayList<>();
        for (Song song : songs) {
            if (song.getTitle().toLowerCase().contains(search.getTitle().toLowerCase())) {
                filteredSongs.add(song);
            }
        }
        return songMapper.listToDto(filteredSongs);
    }
}
