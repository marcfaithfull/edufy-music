package org.example.microservicemusic.service;

import org.example.microservicemusic.mapper.ResponseMapper;
import org.example.microservicemusic.model.dto.SongDto;
import org.example.microservicemusic.model.entity.Song;
import org.example.microservicemusic.exception.ResourceNotFoundException;
import org.example.microservicemusic.mapper.SongMapper;
import org.example.microservicemusic.model.enumeration.Reaction;
import org.example.microservicemusic.repository.SongRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class SongServiceImpl implements SongService {
    SongRepository songRepository;
    SongMapper songMapper;

    @Autowired
    public SongServiceImpl(SongRepository songRepository, SongMapper songMapper) {
        this.songRepository = songRepository;
        this.songMapper = songMapper;
    }

    // CRUD

    public Long createSong(SongDto songDto) {
        Song song = new Song();
        song.setTitle(songDto.getTitle());
        song.setLength(songDto.getLength());
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
        song.setTitle(songDto.getTitle());
        song.setLength(songDto.getLength());
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
        System.out.println(songs);
        return songMapper.listToDto(songs);
    }

    @Override
    public void likeSong(Long id) {
        Song song = songRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Song Not Found"));
        song.setReaction(Reaction.LIKE);
        songRepository.save(song);
    }

    @Override
    public void dislikeSong(Long id) {
        Song song = songRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Song Not Found"));
        song.setReaction(Reaction.DISLIKE);
        songRepository.save(song);
    }

    @Override
    public void randomiseSongStats() {
        List<Song> songs = songRepository.findAll();
        Random randomSong = new Random();
        Song song = songs.get(randomSong.nextInt(songs.size()));
        Random randomReaction = new Random();
        Reaction[] reactions = Reaction.values();
        song.setReaction(reactions[randomReaction.nextInt(reactions.length)]);
        songRepository.save(song);
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
