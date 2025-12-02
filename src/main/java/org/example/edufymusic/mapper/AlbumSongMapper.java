package org.example.edufymusic.mapper;

import org.example.edufymusic.model.dto.AlbumArtistSongDto;
import org.example.edufymusic.model.dto.TrackDto;
import org.example.edufymusic.model.entity.Album;
import org.example.edufymusic.model.entity.AlbumSong;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
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
        dto.setArtist(album.getArtist());

        List<TrackDto> tracks = album.getAlbumSongs().stream()
                .sorted(Comparator.comparing(
                        AlbumSong::getTrackNumber,
                        Comparator.nullsLast(Comparator.naturalOrder())))
                .map(as -> {
                    TrackDto trackDto = new TrackDto();
                    trackDto.setTitle(as.getSong().getTitle());
                    trackDto.setTrackNumber(as.getTrackNumber());
                    trackDto.setSongId(as.getSong().getId());
                    return trackDto;
                })
                .collect(Collectors.toList());
        dto.setTracks(tracks);

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
