/*
package org.example.microservicemusic.mapper;

import org.example.microservicemusic.model.dto.SongAlbumArtistDto;
import org.example.microservicemusic.model.dto.SongDto;
import org.example.microservicemusic.model.entity.Song;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SongMapper {

    */
/*public SongAlbumArtistDto toDto(Song song) {
        SongAlbumArtistDto dto = new SongAlbumArtistDto();
        dto.setId(song.getId());
        dto.setTitle(song.getTitle());
        dto.setLength(song.getLength());
        dto.setGenre(song.getGenre());
        //dto.setArtistId(song.getArtist().getId());
        dto.setArtist(song.getArtist());
        dto.setAlbums(song.getAlbums());
        return dto;
    }

    public List<SongAlbumArtistDto> listToDto(List<Song> songs) {
        List<SongAlbumArtistDto> dTos = new ArrayList<>();
        for (Song song : songs) {
            SongAlbumArtistDto dto = toDto(song);
            dTos.add(dto);
        }
        return dTos;
    }*//*


    public SongDto songToDto(Song song) {
        SongDto dto = new SongDto();
        dto.setUrl("https://stream.edufy.com/song/" + song.getId());
        dto.setId(song.getId());
        dto.setTitle(song.getTitle());
        dto.setLength(song.getLength());
        dto.setGenre(song.getGenre());
        return dto;
    }

    public List<SongDto> songListToDto(List<Song> songs) {
        List<SongDto> dTos = new ArrayList<>();
        for (Song song : songs) {
            SongDto dto = songToDto(song);
            dTos.add(dto);
        }
        return dTos;
    }
}
*/
