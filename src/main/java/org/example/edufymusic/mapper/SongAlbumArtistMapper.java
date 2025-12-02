package org.example.edufymusic.mapper;

import org.example.edufymusic.model.dto.AlbumArtistSongDto;
import org.example.edufymusic.model.dto.SongAlbumArtistDto;
import org.example.edufymusic.model.entity.AlbumSong;
import org.example.edufymusic.model.entity.Song;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class SongAlbumArtistMapper {

    private final AlbumSongMapper albumSongMapper;

    public SongAlbumArtistMapper(AlbumSongMapper albumSongMapper) {
        this.albumSongMapper = albumSongMapper;
    }

    public SongAlbumArtistDto toDto(Song song) {
        if (song == null)
            return null;

        SongAlbumArtistDto dto = new SongAlbumArtistDto();

        dto.setUrl("https://stream.edufy.com/song/" +
                song.getId() + "/" +
                song.getTitle().replaceAll("\\s+", "-").toLowerCase());
        dto.setId(song.getId());
        dto.setTitle(song.getTitle());
        dto.setLengthInSeconds(song.getLengthInSeconds());
        dto.setGenre(song.getGenre());
        dto.setArtist(song.getArtist());
        dto.setReleaseDate(song.getReleaseDate());

        List<AlbumArtistSongDto> albumArtistSongDto = song.getAlbumSongs() == null ?
                new ArrayList<>() :
                song.getAlbumSongs().stream()
                        .map(AlbumSong::getAlbum)
                        .filter(Objects::nonNull)
                        .map(albumSongMapper::toDto)
                        .collect(Collectors.toList());

        return dto;
    }

    public List<SongAlbumArtistDto> listToDto(List<Song> songs) {
        List<SongAlbumArtistDto> dTos = new ArrayList<>();
        for (Song song : songs) {
            SongAlbumArtistDto dto = toDto(song);
            dTos.add(dto);
        }
        return dTos;
    }
}
