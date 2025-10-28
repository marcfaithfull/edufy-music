package org.example.microservicemusic.mapper;

import org.example.microservicemusic.model.dto.ArtistDto;
import org.example.microservicemusic.model.entity.Artist;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ArtistMapper {

    public ArtistDto toDto(Artist artist) {
        ArtistDto dto = new ArtistDto();
        dto.setId(artist.getId());
        dto.setName(artist.getName());
        return dto;
    }

    public List<ArtistDto> listToDto(List<Artist> artists) {
        List<ArtistDto> dTos = new ArrayList<>();
        for (Artist artist : artists) {
            ArtistDto dto = toDto(artist);
            dTos.add(dto);
        }
        return dTos;
    }
}
