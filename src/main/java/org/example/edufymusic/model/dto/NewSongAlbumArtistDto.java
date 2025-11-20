package org.example.edufymusic.model.dto;

import java.util.List;

public class NewSongAlbumArtistDto {
    List<SongDto> songs;
    List<AlbumDto> albums;
    ArtistDto artist;

    public List<SongDto> getSongs() {
        return songs;
    }

    public void setSongs(List<SongDto> songs) {
        this.songs = songs;
    }

    public List<AlbumDto> getAlbums() {
        return albums;
    }

    public void setAlbums(List<AlbumDto> albums) {
        this.albums = albums;
    }

    public ArtistDto getArtist() {
        return artist;
    }

    public void setArtist(ArtistDto artist) {
        this.artist = artist;
    }
}
