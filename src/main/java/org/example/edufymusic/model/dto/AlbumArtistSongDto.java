package org.example.edufymusic.model.dto;

import org.example.edufymusic.model.entity.Artist;

import java.util.Set;

public class AlbumArtistSongDto {
    String url;
    Long id;
    String title;
    Artist artist;
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

    public Artist getArtist() {
        return artist;
    }

    public void setArtist(Artist artist) {
        this.artist = artist;
    }

    public Set<SongDto> getSongs() {
        return songs;
    }

    public void setSongs(Set<SongDto> songs) {
        this.songs = songs;
    }
}
