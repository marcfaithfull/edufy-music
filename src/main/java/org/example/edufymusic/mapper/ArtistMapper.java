package org.example.edufymusic.mapper;

import org.example.edufymusic.model.entity.Artist;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ArtistMapper {

    public org.example.edufymusic.model.dto.ArtistDto toDto(Artist artist) {
        org.example.edufymusic.model.dto.ArtistDto dto = new org.example.edufymusic.model.dto.ArtistDto();
        dto.setUrl("https://stream.edufy.com/artist/" +
                artist.getId() + "/" +
                artist.getName().replaceAll("\\s+", "-").toLowerCase());
        dto.setId(artist.getId());
        dto.setName(artist.getName());
        dto.setGenre(artist.getGenre());
        return dto;
    }

    public List<org.example.edufymusic.model.dto.ArtistDto> listToDto(List<Artist> artists) {
        List<org.example.edufymusic.model.dto.ArtistDto> dTos = new ArrayList<>();
        for (Artist artist : artists) {
            org.example.edufymusic.model.dto.ArtistDto dto = toDto(artist);
            dTos.add(dto);
        }
        return dTos;
    }
}
