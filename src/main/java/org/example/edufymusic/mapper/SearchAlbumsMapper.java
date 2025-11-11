package org.example.edufymusic.mapper;

import org.example.edufymusic.model.dto.AlbumDto;
import org.example.edufymusic.model.entity.Album;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SearchAlbumsMapper {

    public AlbumDto toDto(Album album) {
        AlbumDto dto = new AlbumDto();
        dto.setUrl("Https://stream.edufy.com/album/" + album.getId());
        dto.setId(album.getId());
        dto.setTitle(album.getTitle());
        dto.setLength(album.getLength());
        dto.setYear(album.getYear());
        dto.setTracks(album.getTracks());
        return dto;
    }

    public List<AlbumDto> listToDto(List<Album> albums) {
        List<AlbumDto> dTos = new ArrayList<>();
        for (Album album : albums) {
            AlbumDto dto = toDto(album);
            dTos.add(dto);
        }
        return dTos;
    }
}
