package org.example.edufymusic.model.dto;

import org.example.edufymusic.model.entity.Artist;

import java.util.Set;

public class AlbumArtistSongDto {
    String url;
    Long id;
    String title;
    ArtistDto artistDto;
    Set<SongDto> songs;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public ArtistDto getArtistDto() {
        return artistDto;
    }

    public void setArtistDto(ArtistDto artistDto) {
        this.artistDto = artistDto;
    }

    public Set<SongDto> getSongs() {
        return songs;
    }

    public void setSongs(Set<SongDto> songs) {
        this.songs = songs;
    }
}
