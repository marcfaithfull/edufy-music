package org.example.microservicemusic.model.entity;

import jakarta.persistence.*;
import org.example.microservicemusic.model.enumeration.Genre;
import org.example.microservicemusic.model.enumeration.Reaction;

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
    private Double length;

    @ManyToMany(mappedBy = "songs")
    private Set<Album> albums = new HashSet<>();

    @ManyToOne
    @JoinColumn(name ="artist_id")
    private Artist artist;

    @Enumerated(EnumType.STRING)
    @Column(name = "song_reaction")
    private Reaction reaction;

    @Column(name = "song_genre")
    private Genre genre;

    public Song() {
        this.reaction = Reaction.NEUTRAL;
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

    public double getLength() {
        return length;
    }

    public void setLength(double length) {
        this.length = length;
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

    public Reaction getReaction() {
        return reaction;
    }

    public void setReaction(Reaction reaction) {
        this.reaction = reaction;
    }
}
