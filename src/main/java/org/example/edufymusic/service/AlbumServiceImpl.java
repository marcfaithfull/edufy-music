package org.example.edufymusic.service;

import org.example.edufymusic.exception.RequestNotValidException;
import org.example.edufymusic.exception.ResourceNotFoundException;
import org.example.edufymusic.mapper.AlbumSongMapper;
import org.example.edufymusic.mapper.SearchAlbumsMapper;
import org.example.edufymusic.model.dto.AlbumDto;
import org.example.edufymusic.model.dto.PostAlbumDto;
import org.example.edufymusic.model.dto.AlbumArtistSongDto;
import org.example.edufymusic.model.dto.TrackDto;
import org.example.edufymusic.model.entity.Album;
import org.example.edufymusic.model.entity.AlbumSong;
import org.example.edufymusic.model.entity.Artist;
import org.example.edufymusic.model.entity.Song;
import org.example.edufymusic.repository.AlbumRepository;
import org.example.edufymusic.repository.ArtistRepository;
import org.example.edufymusic.repository.SongRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AlbumServiceImpl implements AlbumService {
    private final AlbumRepository albumRepository;
    private final ArtistRepository artistRepository;
    private final SongRepository songRepository;
    private final AlbumSongMapper albumSongMapper;
    private final SearchAlbumsMapper searchAlbumsMapper;

    @Autowired
    public AlbumServiceImpl(AlbumRepository albumRepository, ArtistRepository artistRepository, SongRepository songRepository,
                            AlbumSongMapper albumSongMapper, SearchAlbumsMapper searchAlbumsMapper) {
        this.albumRepository = albumRepository;
        this.artistRepository = artistRepository;
        this.songRepository = songRepository;
        this.albumSongMapper = albumSongMapper;
        this.searchAlbumsMapper = searchAlbumsMapper;
    }

    @Override
    public Album createAlbum(PostAlbumDto postAlbumDto) {
        if (postAlbumDto.getTitle() == null || postAlbumDto.getTitle().isBlank()) {
            throw new RequestNotValidException("title is required");
        }
        if (postAlbumDto.getArtistId() == null || postAlbumDto.getArtistId().equals(0L)) {
            throw new RequestNotValidException("artistId is required");
        }
        Artist artist = artistRepository.findById(postAlbumDto.getArtistId())
                .orElseThrow(() -> new ResourceNotFoundException("Artist not found"));

        Album album = new Album();
        album.setTitle(postAlbumDto.getTitle());
        album.setArtist(artist);
        album.setYear(postAlbumDto.getYear());

        List<AlbumSong> albumSongs = new ArrayList<>();

        if (postAlbumDto.getTracks() != null) {
            for (TrackDto trackDto : postAlbumDto.getTracks()) {
                Song song = songRepository.findById(trackDto.getSongId())
                        .orElseThrow(() -> new ResourceNotFoundException("Song not found"));

                AlbumSong albumSong = new AlbumSong();
                albumSong.setAlbum(album);
                albumSong.setSong(song);
                albumSong.setTrackNumber(trackDto.getTrackNumber());

                albumSongs.add(albumSong);
            }
        }
        album.setAlbumSongs(albumSongs);

        int countedSongs = albumSongs.size();
        album.setTracks(countedSongs);

        int totalLength = albumSongs.stream()
                        .mapToInt(as -> as.getSong().getLengthInSeconds())
                                .sum();
        album.setLengthInSeconds(totalLength);

        albumRepository.save(album);
        return album;
    }

    @Override
    public AlbumArtistSongDto getAlbumById(Long id) {
        Album album = albumRepository.findById(id)
                .orElseThrow(() -> new RequestNotValidException("Album Not Found"));
        return albumSongMapper.toDto(album);
    }

    @Override
    public void updateAlbum(Long id, PostAlbumDto postAlbumDto) {
        Album album = albumRepository.findById(id)
                .orElseThrow(() -> new RequestNotValidException("Album Not Found"));
        Artist artist = artistRepository.findById(postAlbumDto.getArtistId())
                .orElseThrow(() -> new ResourceNotFoundException("Artist Not Found"));
        if (postAlbumDto.getTitle() != null && !postAlbumDto.getTitle().isBlank()) {
            album.setTitle(postAlbumDto.getTitle());
        }
        if (postAlbumDto.getArtistId() != null && !postAlbumDto.getArtistId().equals(0L)) {
            album.setArtist(artist);
        }

        if (postAlbumDto.getTracks() != null) {
            album.getAlbumSongs().clear();

            List<AlbumSong> newSongs = new ArrayList<>();
            for (TrackDto trackDto : postAlbumDto.getTracks()) {
                Song song = songRepository.findById(trackDto.getSongId())
                        .orElseThrow(() -> new ResourceNotFoundException("Song not found"));

                AlbumSong albumSong = new AlbumSong();
                albumSong.setAlbum(album);
                albumSong.setSong(song);
                albumSong.setTrackNumber(trackDto.getTrackNumber());

                newSongs.add(albumSong);
            }

            album.setAlbumSongs(newSongs);

            album.setTracks(newSongs.size());

            int totalLength = newSongs.stream()
                    .mapToInt(as -> as.getSong().getLengthInSeconds())
                    .sum();
            album.setLengthInSeconds(totalLength);
        }

        albumRepository.save(album);
    }

    @Override
    public Album deleteAlbumById(Long id) {
        Album album = albumRepository.findById(id)
                .orElseThrow(() -> new RequestNotValidException("Album Not Found"));
        albumRepository.delete(album);
        return album;
    }

    @Override
    public List<AlbumArtistSongDto> getAllAlbums() {
        List<Album> albums = albumRepository.findAll();
        return albumSongMapper.listToDto(albums);
    }

    @Override
    public List<AlbumDto> searchResults(AlbumDto search) {
        List<Album> albums = albumRepository.findAll();
        List<Album> filteredAlbums = new ArrayList<>();
        for (Album album : albums) {
            if (album.getTitle().toLowerCase().contains(search.getTitle().toLowerCase())) {
                filteredAlbums.add(album);
            }
        }
        return searchAlbumsMapper.listToDto(filteredAlbums);
    }
}
