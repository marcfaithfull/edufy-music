package org.example.microservicemusic.mapper;

import org.example.microservicemusic.model.dto.AlbumDto;
import org.example.microservicemusic.model.entity.Album;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AlbumMapper {

    public AlbumDto toDto(Album album) {
        AlbumDto dto = new AlbumDto();
        dto.setId(album.getId());
        dto.setTitle(album.getTitle());
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
