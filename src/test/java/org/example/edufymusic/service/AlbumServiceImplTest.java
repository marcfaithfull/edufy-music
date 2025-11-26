package org.example.edufymusic.service;

import org.example.edufymusic.exception.RequestNotValidException;
import org.example.edufymusic.exception.ResourceNotFoundException;
import org.example.edufymusic.mapper.AlbumSongMapper;
import org.example.edufymusic.mapper.SearchAlbumsMapper;
import org.example.edufymusic.model.dto.AlbumArtistSongDto;
import org.example.edufymusic.model.dto.AlbumDto;
import org.example.edufymusic.model.dto.PostAlbumDto;
import org.example.edufymusic.model.entity.Album;
import org.example.edufymusic.model.entity.Artist;
import org.example.edufymusic.model.entity.Song;
import org.example.edufymusic.repository.AlbumRepository;
import org.example.edufymusic.repository.ArtistRepository;
import org.example.edufymusic.repository.SongRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AlbumServiceImplTest {
    @Mock
    private SongRepository songRepository;
    @Mock
    private AlbumRepository albumRepository;
    @Mock
    private ArtistRepository artistRepository;
    @Mock
    private AlbumSongMapper albumSongMapper;
    @Mock
    private SearchAlbumsMapper searchAlbumsMapper;
    @InjectMocks
    AlbumServiceImpl albumServiceImpl;

    private PostAlbumDto postAlbumDto;
    private AlbumArtistSongDto albumArtistSongDto;
    private AlbumDto albumDto;

    private Song song;
    private Album album;
    private Artist artist;

    @BeforeEach
    void setUp() {
        postAlbumDto = new PostAlbumDto();
        postAlbumDto.setId(1L);

        albumArtistSongDto = new AlbumArtistSongDto();
        albumArtistSongDto.setId(1L);
        albumArtistSongDto.setTitle("Test Album");

        albumDto = new AlbumDto();
        albumDto.setId(1L);
        albumDto.setTitle("Test Album");

        song = new Song();
        song.setId(1L);
        song.setTitle("Test Song");

        album = new Album();
        album.setId(1L);
        album.setTitle("Test Album");

        artist = new Artist();
        artist.setId(1L);
        artist.setName("Test Artist");
    }

    @Test
    void createAlbum_Success() {
        Set<Long> songs = new HashSet<>();
        songs.add(song.getId());

        postAlbumDto.setTitle("Test Title");
        postAlbumDto.setYear(2025);
        postAlbumDto.setSongs(songs);
        postAlbumDto.setArtistId(artist.getId());

        when(artistRepository.findById(1L)).thenReturn(Optional.of(artist));
        when(songRepository.findById(song.getId())).thenReturn(Optional.of(song));

        album = albumServiceImpl.createAlbum(postAlbumDto);

        assertNotNull(album);
        assertEquals("Test Title", album.getTitle());
        assertEquals(artist, album.getArtist());

        verify(albumRepository, times(1)).save(album);

        assertTrue(song.getAlbums().contains(album));
    }

    @Test
    void createAlbum_Failed_MissingTitle() {
        postAlbumDto.setArtistId(1L);

        Exception e = assertThrows(RequestNotValidException.class, () -> albumServiceImpl.createAlbum(postAlbumDto));

        assertEquals("title is required", e.getMessage());

        verify(albumRepository, never()).save(any());
    }

    @Test
    void createAlbum_Failed_MissingArtistId() {
        postAlbumDto.setTitle("Test Title");

        Exception e = assertThrows(RequestNotValidException.class, () -> albumServiceImpl.createAlbum(postAlbumDto));

        assertEquals("artistId is required", e.getMessage());

        verify(albumRepository, never()).save(any());
    }

    @Test
    void createAlbum_Failed_ArtistNotFound() {
        postAlbumDto.setTitle("Test Title");
        postAlbumDto.setArtistId(1L);

        when(artistRepository.findById(1L)).thenReturn(Optional.empty());

        ResourceNotFoundException e = assertThrows(ResourceNotFoundException.class, () -> albumServiceImpl.createAlbum(postAlbumDto));

        assertEquals("Artist not found", e.getMessage());

        verify(artistRepository, never()).save(any());
    }

    @Test
    void createAlbum_Failed_SongNotFound() {
        postAlbumDto.setTitle("Test Title");
        postAlbumDto.setArtistId(artist.getId());

        Set<Long> songs = new HashSet<>();
        songs.add(song.getId());

        postAlbumDto.setSongs(songs);

        when(artistRepository.findById(1L)).thenReturn(Optional.of(artist));
        when(songRepository.findById(1L)).thenReturn(Optional.empty());

        ResourceNotFoundException e = assertThrows(ResourceNotFoundException.class,
                () -> albumServiceImpl.createAlbum(postAlbumDto));

        assertEquals("Song not found", e.getMessage());

        verify(albumRepository, never()).save(any());
    }

    @Test
    void getAlbumById_Success() {

        when(albumRepository.findByIdWithSongs(1L)).thenReturn(Optional.of(album));
        when(albumSongMapper.toDto(album)).thenReturn(albumArtistSongDto);

        AlbumArtistSongDto result = albumServiceImpl.getAlbumById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Test Album", result.getTitle());

        verify(albumRepository, times(1)).findByIdWithSongs(1L);
        verify(albumSongMapper, times(1)).toDto(album);
    }

    @Test
    void updateAlbum_Success() {
        song.setAlbums(new HashSet<>());
        Set<Song> songs = new HashSet<>();
        songs.add(song);

        album.setSongs(songs);

        postAlbumDto.setArtistId(1L);
        postAlbumDto.setTitle("New Title");
        postAlbumDto.setSongs(Set.of(1L));

        when(albumRepository.findById(1L)).thenReturn(Optional.of(album));
        when(artistRepository.findById(1L)).thenReturn(Optional.of(artist));
        when(songRepository.findById(1L)).thenReturn(Optional.of(song));

        albumServiceImpl.updateAlbum(1L, postAlbumDto);

        assertEquals("New Title", album.getTitle());
        assertEquals(artist, album.getArtist());
        assertTrue(album.getSongs().contains(song));
        assertEquals(1, album.getSongs().size());

        verify(albumRepository).save(album);
    }

    @Test
    void deleteAlbum_Success() {
        when(albumRepository.findById(1L)).thenReturn(Optional.of(album));

        albumServiceImpl.deleteAlbumById(1L);

        verify(albumRepository).delete(album);
    }

    @Test
    void getAllAlbums_Success() {
        List<Album> albums = new ArrayList<>();
        albums.add(album);

        when(albumRepository.findAll()).thenReturn(albums);

        albumServiceImpl.getAllAlbums();

        verify(albumRepository, times(1)).findAll();
    }

    @Test
    void searchResults_Success() {
        List<Album> albums = Collections.singletonList(album);

        when(albumRepository.findAll()).thenReturn(albums);

        AlbumDto search = new AlbumDto();
        search.setTitle("test");

        when(searchAlbumsMapper.listToDto(List.of(album))).thenReturn(List.of(albumDto));

        List<AlbumDto> result = albumServiceImpl.searchResults(search);

        assertEquals(1, result.size());
        assertEquals("Test Album", result.get(0).getTitle());

        verify(albumRepository).findAll();
        verify(searchAlbumsMapper).listToDto(List.of(album));
    }
}