package org.example.microservicemusic.mapper;

import org.example.microservicemusic.model.dto.SongDto;
import org.example.microservicemusic.model.entity.Song;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SongMapper {

    public SongDto toDto(Song song) {
        SongDto dto = new SongDto();
        dto.setId(song.getId());
        dto.setTitle(song.getTitle());
        dto.setLength(song.getLength());
        dto.setReaction(song.getReaction());
        return dto;
    }

    public List<SongDto> listToDto(List<Song> songs) {
        List<SongDto> dTos = new ArrayList<>();
        for (Song song : songs) {
            SongDto dto = toDto(song);
            dTos.add(dto);
        }
        return dTos;
    }
}
