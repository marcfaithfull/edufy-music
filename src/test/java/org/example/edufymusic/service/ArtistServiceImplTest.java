package org.example.edufymusic.service;

import org.example.edufymusic.exception.RequestNotValidException;
import org.example.edufymusic.exception.ResourceNotFoundException;
import org.example.edufymusic.mapper.ArtistMapper;
import org.example.edufymusic.model.dto.ArtistDto;
import org.example.edufymusic.model.entity.Artist;
import org.example.edufymusic.repository.ArtistRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ArtistServiceImplTest {
    @Mock
    private ArtistRepository artistRepository;
    @Mock
    private ArtistMapper artistMapper;
    @InjectMocks
    private ArtistServiceImpl artistServiceImpl;

    private ArtistDto artistDto;

    private Artist artist;

    @BeforeEach
    void setUp() {
        artistDto = new ArtistDto();
        artistDto.setId(1L);
        artistDto.setName("Test ArtistDto");

        artist = new Artist();
        artist.setId(1L);
        artist.setName("Test Artist");
    }

    @Test
    void createArtist_Successful() {
        Artist result = artistServiceImpl.createArtist(artistDto);

        assertNotNull(result);
        assertEquals(artistDto.getName(), result.getName());

        verify(artistRepository).save(any(Artist.class));
    }

    @Test
    void createArtist_Failed_TitleIsNull() {
        Artist result = artistServiceImpl.createArtist(artistDto);
        artistDto.setName(null);

        RequestNotValidException e = assertThrows(RequestNotValidException.class,
                () -> artistServiceImpl.createArtist(artistDto));

        assertNotNull(result);
        assertNotEquals(artistDto.getName(), result.getName());
        assertEquals("\"title\" is required", e.getMessage());

        verify(artistRepository).save(any(Artist.class));
    }

    @Test
    void createArtist_Failed_NameIsEmpty() {
        Artist result = artistServiceImpl.createArtist(artistDto);
        artistDto.setName("");

        RequestNotValidException e = assertThrows(RequestNotValidException.class,
                () -> artistServiceImpl.createArtist(artistDto));

        assertNotNull(result);
        assertNotEquals(artistDto.getName(), result.getName());
        assertEquals("\"title\" is required", e.getMessage());

        verify(artistRepository).save(any(Artist.class));
    }

    @Test
    void getArtistById_Successful() {
        when(artistRepository.findById(1L)).thenReturn(Optional.of(artist));
        when(artistMapper.toDto(artist)).thenReturn(artistDto);

        ArtistDto result = artistServiceImpl.getArtistById(1L);

        assertNotNull(result);

        verify(artistRepository).findById(1L);
        verify(artistMapper).toDto(artist);
    }

    @Test
    void getArtistById_Failed_IdIsNull() {
        when(artistRepository.findById(2L)).thenReturn(Optional.empty());

        ResourceNotFoundException e = assertThrows(ResourceNotFoundException.class,
                () -> artistServiceImpl.getArtistById(2L));

        assertEquals("Artist not found", e.getMessage());

        verify(artistRepository).findById(2L);
    }

    @Test
    void updateArtist_Successful() {
        artist.setName("Old Name");
        artistDto.setName("New Name");

        when(artistRepository.findById(1L)).thenReturn(Optional.of(artist));

        artistServiceImpl.updateArtist(1L, artistDto);

        assertEquals("New Name", artist.getName());
        verify(artistRepository).save(artist);
    }

    @Test
    void updateArtist_Failed_ArtistNotFound() {
        when(artistRepository.findById(2L)).thenReturn(Optional.empty());

        ResourceNotFoundException e = assertThrows(ResourceNotFoundException.class,
                () -> artistServiceImpl.updateArtist(2L, artistDto));

        assertEquals("Artist not found", e.getMessage());
        verify(artistRepository).findById(2L);
    }

    @Test
    void deleteArtistById_Successful() {
        when(artistRepository.findById(1L)).thenReturn(Optional.of(artist));

        artistServiceImpl.deleteArtistById(1L);

        verify(artistRepository).deleteById(1L);
    }

    @Test
    void deleteArtistById_Failed_ArtistNotFound() {
        when(artistRepository.findById(1L)).thenReturn(Optional.empty());

        ResourceNotFoundException e  = assertThrows(ResourceNotFoundException.class,
                () -> artistServiceImpl.deleteArtistById(1L));

        assertEquals("Artist not found", e.getMessage());
        verify(artistRepository).findById(1L);
    }

    @Test
    void getAllArtists_Successful() {
        List<Artist> artists = Collections.singletonList(artist);
        List<ArtistDto> artistDtos = Collections.singletonList(artistDto);

        when(artistRepository.findAll()).thenReturn(artists);
        when(artistMapper.listToDto(anyList())).thenReturn(artistDtos);

        List<ArtistDto> result = artistServiceImpl.getAllArtists();

        assertEquals(artistDtos, result);
        verify(artistRepository).findAll();
        verify(artistMapper).listToDto(artists);
    }

    @Test
    void searchResults() {
        Artist artist2 = new Artist();
        artist2.setId(2L);
        artist2.setName("Test Artist2");

        List<Artist> allArtists = Arrays.asList(artist, artist2);

        when(artistRepository.findAll()).thenReturn(allArtists);

        ArtistDto searchDto = new ArtistDto();
        searchDto.setName("test");

        when(artistMapper.listToDto(allArtists))
                .thenReturn(Collections.singletonList(artistDto));

        List<ArtistDto> result = artistServiceImpl.searchResults(searchDto);

        assertEquals(1, result.size());
        assertEquals("Test ArtistDto", result.get(0).getName());

        verify(artistRepository).findAll();
        verify(artistMapper).listToDto(allArtists);
    }

}