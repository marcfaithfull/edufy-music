package org.example.edufymusic.mapper;

import org.example.edufymusic.model.dto.SongDto;
import org.example.edufymusic.model.entity.Song;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SongMapper {

    public SongDto songToDto(Song song) {
        SongDto dto = new SongDto();
        dto.setUrl("https://stream.edufy.com/song/" + song.getId());
        dto.setId(song.getId());
        dto.setTitle(song.getTitle());
        dto.setLength(song.getLengthInSeconds());
        dto.setGenre(song.getGenre());
        return dto;
    }

    public List<SongDto> songListToDto(List<Song> songs) {
        List<SongDto> dTos = new ArrayList<>();
        for (Song song : songs) {
            SongDto dto = songToDto(song);
            dTos.add(dto);
        }
        return dTos;
    }
}
