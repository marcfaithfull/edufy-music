package org.example.microservicemusic.model.dto;

import org.example.microservicemusic.model.entity.Album;
import org.example.microservicemusic.model.entity.Artist;
import org.example.microservicemusic.model.enumeration.Genre;

import java.util.Set;

public class SongAlbumArtistDto {
    private String url;
    private Long id;
    private String title;
    private int lengthInSeconds;
    private Genre genre;
    private Set<Album> albums;
    private Artist artist;

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

    public int getLength() {
        return lengthInSeconds;
    }

    public void setLength(int lengthInMinutes) {
        this.lengthInSeconds = lengthInMinutes;
    }

    public Set<Album> getAlbums() {
        return albums;
    }

    public void setAlbums(Set<Album> albums) {
        this.albums = albums;
    }

    public Artist getArtist() {
        return artist;
    }

    public void setArtist(Artist artist) {
        this.artist = artist;
    }

    public Genre getGenre() {
        return genre;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }
}
