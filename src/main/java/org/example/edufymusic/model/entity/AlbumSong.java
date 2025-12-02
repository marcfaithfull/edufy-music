package org.example.edufymusic.model.entity;

import jakarta.persistence.*;
import org.example.edufymusic.model.AlbumSongId;

@Entity
@Table(name = "album_song")
public class AlbumSong {

    @EmbeddedId
    private AlbumSongId id = new AlbumSongId();

    @ManyToOne
    @MapsId("albumId")
    @JoinColumn(name = "album_id")
    private Album album;

    @ManyToOne
    @MapsId("songId")
    @JoinColumn(name = "song_id")
    private Song song;

    @Column(name = "track_number")
    private Integer trackNumber;

    public AlbumSongId getId() {
        return id;
    }

    public void setId(AlbumSongId id) {
        this.id = id;
    }

    public Album getAlbum() {
        return album;
    }

    public void setAlbum(Album album) {
        this.album = album;
    }

    public Song getSong() {
        return song;
    }

    public void setSong(Song song) {
        this.song = song;
    }

    public Integer getTrackNumber() {
        return trackNumber;
    }

    public void setTrackNumber(Integer trackNumber) {
        this.trackNumber = trackNumber;
    }
}
