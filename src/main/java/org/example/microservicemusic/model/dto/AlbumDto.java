package org.example.microservicemusic.model.dto;

public class AlbumDto {
    private String url;
    private Long id;
    private String title;
    private int lengthInSeconds;
    private int year;
    private int tracks;

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

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getTracks() {
        return tracks;
    }

    public void setTracks(int tracks) {
        this.tracks = tracks;
    }
}
