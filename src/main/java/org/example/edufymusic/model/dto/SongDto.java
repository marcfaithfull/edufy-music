package org.example.edufymusic.model.dto;

import org.example.edufymusic.model.enumeration.Genre;

public class SongDto {
    private String url;
    private Long id;
    private String title;
    private int lengthInSeconds;
    private Genre genre;

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

    public void setLength(int lengthInSeconds) {
        this.lengthInSeconds = lengthInSeconds;
    }

    public Genre getGenre() {
        return genre;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }
}
