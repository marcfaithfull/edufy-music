package org.example.microservicemusic.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import org.example.microservicemusic.model.enumeration.Genre;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "artist")
public class Artist {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "artist_id")
    private Long id;

    @Column(name = "artist_name")
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "artist_genre")
    private Genre genre;

    @JsonIgnore
    @OneToMany(mappedBy = "artist", cascade = CascadeType.REMOVE)
    private Set<Album> albums = new HashSet<>();

    @JsonIgnore
    @OneToMany(mappedBy = "artist", cascade = CascadeType.REMOVE)
    private Set<Song> songs = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Genre getGenre() {
        return genre;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }

    public Set<Album> getAlbums() {
        return albums;
    }

    public void setAlbums(Set<Album> albums) {
        this.albums = albums;
    }

    public Set<Song> getSongs() {
        return songs;
    }

    public void setSongs(Set<Song> songs) {
        this.songs = songs;
    }
}
