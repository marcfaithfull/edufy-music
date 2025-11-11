package org.example.edufymusic.mapper;

import org.example.edufymusic.model.dto.PostAlbumDto;
import org.example.edufymusic.model.dto.SongDto;
import org.example.edufymusic.model.entity.Album;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AlbumMapper {
    SongMapper songMapper;

    @Autowired
    public AlbumMapper(SongMapper songMapper) {
        this.songMapper = songMapper;
    }

    public PostAlbumDto toDto(Album album) {
        PostAlbumDto dto = new PostAlbumDto();
        dto.setId(album.getId());
        dto.setTitle(album.getTitle());
        dto.setArtistId(album.getArtist().getId());
        dto.getSongId().forEach((songId) -> {
            SongDto songDto = new SongDto();
            songDto.setId(songId);
        });
        return dto;
    }

    public List<PostAlbumDto> listToDto(List<Album> albums) {
        List<PostAlbumDto> dTos = new ArrayList<>();
        for (Album album : albums) {
            PostAlbumDto dto = toDto(album);
            dTos.add(dto);
        }
        return dTos;
    }
}
