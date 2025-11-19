package org.example.edufymusic.mapper;

import org.example.edufymusic.model.dto.AlbumArtistSongDto;
import org.example.edufymusic.model.dto.ArtistDto;
import org.example.edufymusic.model.dto.SongDto;
import org.example.edufymusic.model.entity.Album;
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
        dto.setUrl("https://streaming.edufy.com/album/" +
                album.getId() + "/" +
                album.getTitle().replaceAll("\\s+", "-").toLowerCase());
        dto.setId(album.getId());
        dto.setTitle(album.getTitle());

        Set<SongDto> songDtos = album.getSongs().stream()
                .map(songMapper::songToDto)
                .collect(Collectors.toSet());
        dto.setSongs(songDtos);
        return dto;
    }

    public List<AlbumArtistSongDto> listToDto(List<Album> albums) {
        List<AlbumArtistSongDto> dtos = new ArrayList<>();
        for (Album album : albums) {
            AlbumArtistSongDto dto = toDto(album);
            dtos.add(dto);
        }
        return dtos;
    }
}
