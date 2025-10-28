package org.example.microservicemusic.model.dto;

import org.example.microservicemusic.model.entity.Album;
import org.example.microservicemusic.model.enumeration.Genre;
import org.example.microservicemusic.model.enumeration.Reaction;

import java.math.BigDecimal;
import java.util.Set;

public class SongDto {
    private Long id;
    private String title;
    private BigDecimal length;
    private Reaction reaction;
    private Set<Album> albums;
    private Long artistId;
    private Genre genre;

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

    public Reaction getReaction() {
        return reaction;
    }

    public void setReaction(Reaction reaction) {
        this.reaction = reaction;
    }

    public Set<Album> getAlbums() {
        return albums;
    }

    public void setAlbums(Set<Album> albums) {
        this.albums = albums;
    }

    public Long getArtistId() {
        return artistId;
    }

    public void setArtistId(Long artistId) {
        this.artistId = artistId;
    }

    public Genre getGenre() {
        return genre;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }
}
