package org.example.edufymusic.service;

import org.example.edufymusic.exception.RequestNotValidException;
import org.example.edufymusic.exception.ResourceNotFoundException;
import org.example.edufymusic.mapper.SongAlbumArtistMapper;
import org.example.edufymusic.model.dto.PostSongDto;
import org.example.edufymusic.model.dto.SongAlbumArtistDto;
import org.example.edufymusic.model.entity.Album;
import org.example.edufymusic.model.entity.Artist;
import org.example.edufymusic.model.entity.Song;
import org.example.edufymusic.model.enumeration.Genre;
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
class SongServiceImplTest {
    @Mock
    private SongRepository songRepository;
    @Mock
    private AlbumRepository albumRepository;
    @Mock
    private ArtistRepository artistRepository;
    @Mock
    private SongAlbumArtistMapper  songAlbumArtistMapper;
    @InjectMocks
    SongServiceImpl songServiceImpl;

    private PostSongDto postSongDto;
    private SongAlbumArtistDto songAlbumArtistDto;
    private Song song;
    private Album album;
    private Artist artist;

    @BeforeEach
    void setUp() {
        postSongDto = new PostSongDto();
        songAlbumArtistDto = new SongAlbumArtistDto();
        song = new Song();
        album = new Album();
        artist = new Artist();
    }

    @Test
    void createSong_Successful() {
        // Arrange
        postSongDto.setId(1L);
        postSongDto.setTitle("testSong");
        postSongDto.setLengthInSeconds(1000);
        postSongDto.setGenre(Genre.METAL);

        artist.setId(1L);
        artist.setName("testArtist");

        postSongDto.setArtistId(artist.getId());

        when(artistRepository.findById(1L)).thenReturn(Optional.of(artist));

        // Act
        song = songServiceImpl.createSong(postSongDto);

        // Assert
        assertEquals("testSong", song.getTitle());
        assertEquals(1000, song.getLengthInSeconds());
        assertEquals(Genre.METAL, song.getGenre());
        assertEquals(artist, song.getArtist());
        verify(songRepository).save(song);
    }

    @Test
    void createSong_Failed_ArtistIdIsRequired() {
        postSongDto.setId(1L);
        postSongDto.setTitle("testSong");
        postSongDto.setLengthInSeconds(1000);
        postSongDto.setGenre(Genre.METAL);

        artist.setId(null);

        postSongDto.setArtistId(artist.getId());

        RequestNotValidException exception = assertThrows(
                RequestNotValidException.class,
                () -> songServiceImpl.createSong(postSongDto)
        );

        assertEquals("artistId is required", exception.getMessage());
    }

    @Test
    void createSong_Failed_GenreNotFound() {
        postSongDto.setId(1L);
        postSongDto.setTitle("testSong");
        postSongDto.setLengthInSeconds(1000);
        postSongDto.setGenre(null);

        artist.setId(1L);
        artist.setName("testArtist");

        postSongDto.setArtistId(artist.getId());

        RequestNotValidException exception = assertThrows(
                RequestNotValidException.class,
                () -> songServiceImpl.createSong(postSongDto)
        );

        assertEquals("genre is required", exception.getMessage());
    }

    @Test
    void createSong_Failed_LengthInSecondsNotFound() {
        postSongDto.setId(1L);
        postSongDto.setTitle("testSong");
        postSongDto.setGenre(Genre.METAL);
        postSongDto.setLengthInSeconds(0);

        artist.setId(1L);
        artist.setName("testArtist");

        postSongDto.setArtistId(artist.getId());

        RequestNotValidException exception = assertThrows(
                RequestNotValidException.class,
                () -> songServiceImpl.createSong(postSongDto)
        );

        assertEquals("lengthInSeconds is required", exception.getMessage());
    }

    @Test
    void createSong_Failed_TitleNotFound() {
        postSongDto.setId(1L);
        postSongDto.setTitle(null);
        postSongDto.setLengthInSeconds(1000);
        postSongDto.setGenre(Genre.METAL);

        artist.setId(1L);
        artist.setName("testArtist");

        postSongDto.setArtistId(artist.getId());

        RequestNotValidException exception = assertThrows(
                RequestNotValidException.class,
                () -> songServiceImpl.createSong(postSongDto)
        );

        assertEquals("title is required", exception.getMessage());
    }

    @Test
    void createSong_Failed_ArtistIdNotFound() {
        postSongDto.setId(1L);
        postSongDto.setTitle("testSong");
        postSongDto.setLengthInSeconds(1000);
        postSongDto.setGenre(Genre.METAL);

        artist.setId(1L);
        artist.setName("testArtist");

        postSongDto.setArtistId(artist.getId());

        ResourceNotFoundException exception = assertThrows(
                ResourceNotFoundException.class,
                () -> songServiceImpl.createSong(postSongDto)
        );

        assertEquals("Artist with id: " + postSongDto.getArtistId() + " not found", exception.getMessage());
    }

    @Test
    void createSong_Failed_AlbumIdNotFound() {
        postSongDto.setId(1L);
        postSongDto.setTitle("testSong");
        postSongDto.setLengthInSeconds(1000);
        postSongDto.setGenre(Genre.METAL);
        postSongDto.setAlbumId(1L);

        artist.setId(1L);
        artist.setName("testArtist");

        postSongDto.setArtistId(artist.getId());

        when(artistRepository.findById(1L)).thenReturn(Optional.of(artist));

        ResourceNotFoundException exception = assertThrows(
                ResourceNotFoundException.class,
                () -> songServiceImpl.createSong(postSongDto)
        );

        assertEquals("Album with id: " + postSongDto.getAlbumId() + " not found", exception.getMessage());
    }

    @Test
    void getSongById_Successful() {
        songAlbumArtistDto.setId(1L);

        song.setId(1L);

        when(songRepository.findById(1L)).thenReturn(Optional.of(song));
        when(songAlbumArtistMapper.toDto(song)).thenReturn(songAlbumArtistDto);

        assertEquals(songAlbumArtistDto, songServiceImpl.getSongById(1L));
        assertEquals(songAlbumArtistDto, songAlbumArtistMapper.toDto(song));
        verify(songRepository).findById(1L);
        verify(songAlbumArtistMapper, times(2)).toDto(song);
    }

    @Test
    void getSongById_Failed_SongNotFound() {
        songAlbumArtistDto.setId(1L);

        song.setId(1L);

        when(songRepository.findById(1L)).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(
                ResourceNotFoundException.class,
                () -> songServiceImpl.getSongById(1L)
        );

        when(songAlbumArtistMapper.toDto(song)).thenReturn(songAlbumArtistDto);

        assertEquals(songAlbumArtistDto, songAlbumArtistMapper.toDto(song));
        assertEquals("Song Not Found", exception.getMessage());
        verify(songRepository).findById(1L);
        verify(songAlbumArtistMapper).toDto(song);
    }

    @Test
    void updateSong_Successful() {
        song.setId(1L);
        song.setTitle("Old Title");
        song.setAlbums(new HashSet<>());

        artist.setId(1L);
        artist.setName("Test Artist");

        album.setId(1L);
        album.setTitle("Test Album");
        album.setSongs(new HashSet<>());

        postSongDto.setArtistId(1L);
        postSongDto.setAlbumId(1L);
        postSongDto.setTitle("New Title");

        when(songRepository.findById(1L)).thenReturn(Optional.of(song));
        when(artistRepository.findById(1L)).thenReturn(Optional.of(artist));
        when(albumRepository.findById(1L)).thenReturn(Optional.of(album));

        songServiceImpl.updateSong(1L, postSongDto);

        assertTrue(song.getAlbums().contains(album));
        assertTrue(album.getSongs().contains(song));
        assertEquals(artist, song.getArtist());

        assertEquals("New Title", song.getTitle());

        verify(songRepository).save(song);
    }

    @Test
    void updateSong_Failed_SongNotFound() {
        when(songRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> songServiceImpl.updateSong(1L, postSongDto));
    }

    @Test
    void updateSong_Failed_ArtistNotFound() {
        song.setId(1L);
        artist.setId(1L);

        postSongDto.setArtistId(1L);

        when(songRepository.findById(1L)).thenReturn(Optional.of(song));
        when(artistRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> songServiceImpl.updateSong(1L, postSongDto));
    }

    @Test
    void updateSong_Failed_AlbumNotFound() {
        song.setId(1L);
        artist.setId(1L);
        album.setId(1L);

        postSongDto.setAlbumId(1L);

        when(songRepository.findById(1L)).thenReturn(Optional.of(song));
        when(albumRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> songServiceImpl.updateSong(1L, postSongDto));
    }

    @Test
    void deleteSongById_Successful() {
        song.setId(1L);

        when(songRepository.findById(1L)).thenReturn(Optional.of(song));

        songServiceImpl.deleteSongById(1L);

        verify(songRepository).deleteById(1L);
    }

    @Test
    void deleteSongById_Failed_SongNotFound() {
        song.setId(1L);

        when(songRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> songServiceImpl.deleteSongById(1L));
    }

    @Test
    void getAllSongs_Success() {
        song.setId(1L);
        song.setTitle("Test Song");

        List<Song> songs = Collections.singletonList(song);

        songAlbumArtistDto.setId(1L);
        songAlbumArtistDto.setTitle("Test Song");

        List<SongAlbumArtistDto> songAlbumArtistDtos = Collections.singletonList(songAlbumArtistDto);

        when(songRepository.findAll()).thenReturn(songs);
        when(songAlbumArtistMapper.listToDto(songs)).thenReturn(songAlbumArtistDtos);

        List<SongAlbumArtistDto> result = songServiceImpl.getAllSongs();

        assertEquals(1, result.size());
        assertEquals("Test Song", result.get(0).getTitle());

        verify(songRepository).findAll();
        verify(songAlbumArtistMapper).listToDto(songs);
    }

    @Test
    void getRandomSong_Success() {
        song.setId(1L);
        song.setTitle("Test Song");

        artist.setId(1L);

        song.setArtist(artist);

        songAlbumArtistDto.setId(1L);
        songAlbumArtistDto.setTitle("Test Song");
        songAlbumArtistDto.setArtist(artist);

        when(songRepository.findAll()).thenReturn(Collections.singletonList(song));
        when(songAlbumArtistMapper.toDto(song)).thenReturn(songAlbumArtistDto);

        SongAlbumArtistDto result = songServiceImpl.getRandomSong();

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Test Song", result.getTitle());

        verify(songRepository).findAll();
        verify(songAlbumArtistMapper).toDto(song);
    }

    @Test
    void searchResults_Success() {
        song.setId(1L);
        song.setTitle("Test Song");
        List<Song> songs = Collections.singletonList(song);

        songAlbumArtistDto.setId(1L);
        songAlbumArtistDto.setTitle("Test Song");

        when(songRepository.findAll()).thenReturn(songs);

        SongAlbumArtistDto search = new SongAlbumArtistDto();
        search.setTitle("test");

        when(songAlbumArtistMapper.listToDto(List.of(song))).thenReturn(List.of(songAlbumArtistDto));

        List<SongAlbumArtistDto> result = songServiceImpl.searchResults(search);

        assertEquals(1, result.size());
        assertEquals("Test Song", result.get(0).getTitle());

        verify(songRepository).findAll();
        verify(songAlbumArtistMapper).listToDto(List.of(song));
    }
}