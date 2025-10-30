package org.example.microservicemusic.mapper;

import org.example.microservicemusic.model.dto.SongAlbumArtistDto;
import org.example.microservicemusic.model.entity.Song;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SongAlbumArtistMapper {

    public SongAlbumArtistDto toDto(Song song) {
        SongAlbumArtistDto dto = new SongAlbumArtistDto();
        dto.setUrl("https://stream.edufy.com/song/" + song.getId());
        dto.setId(song.getId());
        dto.setTitle(song.getTitle());
        dto.setLength(song.getLength());
        dto.setGenre(song.getGenre());
        dto.setAlbums(song.getAlbums());
        dto.setArtist(song.getArtist());
        return dto;
    }

    public List<SongAlbumArtistDto> listToDto(List<Song> songs) {
        List<SongAlbumArtistDto> dTos = new ArrayList<>();
        for (Song song : songs) {
            SongAlbumArtistDto dto = toDto(song);
            dTos.add(dto);
        }
        return dTos;
    }
}
