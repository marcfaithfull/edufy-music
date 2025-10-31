package org.example.microservicemusic.model.entity;

import jakarta.persistence.*;
import org.example.microservicemusic.model.enumeration.Genre;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "song")
public class Song {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "song_id")
    private Long id;

    @Column(name = "song_title")
    private String title;

    @Column(name = "song_length")
    private int lengthInSeconds;

    @Enumerated(EnumType.STRING)
    @Column(name = "song_genre")
    private Genre genre;

    @ManyToOne
    @JoinColumn(name = "artist_id")
    private Artist artist;

    /*@OneToMany(mappedBy = "song")
    private List<UserSongReaction> reactions;*/

    @ManyToMany(mappedBy = "songs")
    private Set<Album> albums = new HashSet<>();

    public Song() {
    }

    public Song(String title, int lengthInSeconds, Artist artist, Genre genre) {
        this.title = title;
        this.lengthInSeconds = lengthInSeconds;
        this.artist = artist;
        this.genre = genre;
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

    public int getLengthInSeconds() {
        return lengthInSeconds;
    }

    public void setLengthInSeconds(int length) {
        this.lengthInSeconds = length;
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
