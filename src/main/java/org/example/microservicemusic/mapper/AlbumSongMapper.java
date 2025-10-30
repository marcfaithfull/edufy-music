package org.example.microservicemusic.mapper;

import org.example.microservicemusic.model.dto.AlbumArtistSongDto;
import org.example.microservicemusic.model.dto.SongDto;
import org.example.microservicemusic.model.entity.Album;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AlbumSongMapper {
    SongMapper songMapper;

    @Autowired
    public AlbumSongMapper(SongMapper songMapper) {
        this.songMapper = songMapper;
    }

    public AlbumArtistSongDto toDto(Album album) {
        AlbumArtistSongDto dto = new AlbumArtistSongDto();
        dto.setUrl("https://streaming.edufy.com/albums");
        dto.setId(album.getId());
        dto.setTitle(album.getTitle());
        dto.setArtist(album.getArtist());
        Set<SongDto> songDtos = album.getSongs().stream()
                .map(songMapper::songToDto)
                .collect(Collectors.toSet());
        dto.setSongs(songDtos);
        return dto;
    }

    public List<AlbumArtistSongDto> listToDto(List<Album> albums) {
        List<AlbumArtistSongDto> dTos = new ArrayList<>();
        for (Album album : albums) {
            AlbumArtistSongDto dto = toDto(album);
            dTos.add(dto);
        }
        return dTos;
    }
}
