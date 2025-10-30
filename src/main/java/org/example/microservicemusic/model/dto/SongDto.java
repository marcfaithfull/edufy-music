package org.example.microservicemusic.model.dto;

import org.example.microservicemusic.model.enumeration.Genre;

import java.math.BigDecimal;

public class SongDto {
    private String url;
    private  Long id;
    private String title;
    private BigDecimal length;
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

    public BigDecimal getLength() {
        return length;
    }

    public void setLength(BigDecimal length) {
        this.length = length;
    }

    public Genre getGenre() {
        return genre;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }
}
