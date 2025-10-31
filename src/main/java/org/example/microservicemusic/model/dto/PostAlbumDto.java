package org.example.microservicemusic.model.dto;

import org.example.microservicemusic.model.entity.Song;

import java.util.Set;

public class PostAlbumDto {
    Long id;
    String title;
    Long artistId;
    int year;
    Set<Long> songId;

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

    public Long getArtistId() {
        return artistId;
    }

    public void setArtistId(Long artistId) {
        this.artistId = artistId;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public Set<Long> getSongId() {
        return songId;
    }

    public Set<Song> setSongs(Set<Long> songId) {
        this.songId = songId;
        return null;
    }
}
