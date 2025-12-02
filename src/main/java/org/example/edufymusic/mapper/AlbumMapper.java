package org.example.edufymusic.mapper;

import org.example.edufymusic.model.dto.PostAlbumDto;
import org.example.edufymusic.model.dto.TrackDto;
import org.example.edufymusic.model.entity.Album;
import org.example.edufymusic.model.entity.AlbumSong;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

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

        List<TrackDto> tracks = album.getAlbumSongs().stream()
                .sorted(Comparator.comparing(AlbumSong::getTrackNumber))
                .map(as -> {
                    TrackDto trackDto = new TrackDto();
                    trackDto.setSongId(as.getSong().getId());
                    trackDto.setTrackNumber(as.getTrackNumber());
                    return trackDto;
                })
                .collect(Collectors.toList());

        dto.setTracks(tracks);
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
