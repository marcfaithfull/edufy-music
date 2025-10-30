package org.example.microservicemusic.mapper;

import org.example.microservicemusic.model.dto.SearchAlbumDto;
import org.example.microservicemusic.model.entity.Album;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SearchAlbumsMapper {
    SongMapper songMapper;

    @Autowired
    public SearchAlbumsMapper(SongMapper songMapper) {
        this.songMapper = songMapper;
    }

    public SearchAlbumDto toDto(Album album) {
        SearchAlbumDto dto = new SearchAlbumDto();
        dto.setUrl("Https://stream.edufy.com/album/" + album.getId());
        dto.setId(album.getId());
        dto.setTitle(album.getTitle());
        return dto;
    }

    public List<SearchAlbumDto> listToDto(List<Album> albums) {
        List<SearchAlbumDto> dTos = new ArrayList<>();
        for (Album album : albums) {
            SearchAlbumDto dto = toDto(album);
            dTos.add(dto);
        }
        return dTos;
    }
}
