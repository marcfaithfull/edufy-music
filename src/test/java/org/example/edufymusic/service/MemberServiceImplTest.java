package org.example.edufymusic.service;

import org.example.edufymusic.exception.ResourceNotFoundException;
import org.example.edufymusic.mapper.MemberArtistMapper;
import org.example.edufymusic.mapper.MemberMapper;
import org.example.edufymusic.model.dto.MemberArtistDto;
import org.example.edufymusic.model.dto.MemberDto;
import org.example.edufymusic.model.entity.Member;
import org.example.edufymusic.repository.MemberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MemberServiceImplTest {
    @Mock
    private MemberRepository memberRepository;
    @Mock
    private MemberMapper memberMapper;
    @Mock
    private MemberArtistMapper memberArtistMapper;
    @InjectMocks
    private MemberServiceImpl memberServiceImpl;

    private Member member;

    private MemberDto memberDto;
    private MemberDto musicianDto;

    private MemberArtistDto memberArtistDto;

    @BeforeEach
    void setUp() {
        member = new Member();
        member.setId(1L);
        member.setName("Test Member");

        memberDto = new MemberDto();
        memberDto.setId(1L);
        memberDto.setName("Test MemberDto");

        musicianDto = new MemberDto();
        musicianDto.setId(1L);
        musicianDto.setName("Test MusicianDto");

        memberArtistDto = new MemberArtistDto();
        memberArtistDto.setMusician(musicianDto);
    }

    @Test
    void getMemberById_Success() {
        when(memberRepository.findById(1L)).thenReturn(Optional.of(member));
        when(memberMapper.toDto(member)).thenReturn(memberDto);

        memberServiceImpl.getMemberById(1L);

        verify(memberRepository).findById(1L);
        verify(memberMapper).toDto(member);
    }

    @Test
    void getMemberById_Fail_ThrowsException() {
        when(memberRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> memberServiceImpl.getMemberById(1L));

        verify(memberRepository).findById(1L);
    }

    @Test
    void searchResults_Success() {
        member.setName("Musician");
        musicianDto.setName("Musician");
        memberDto.setName("Musician");

        List<Member> members = Collections.singletonList(member);

        when(memberRepository.findAll()).thenReturn(members);

        MemberDto search = new MemberDto();
        search.setName("musician");

        when(memberMapper.listToDto(List.of(member))).thenReturn(List.of(memberDto));

        List<MemberDto> searchResults = memberServiceImpl.searchResults(search);

        assertEquals(1, searchResults.size());
        assertEquals("Musician", searchResults.get(0).getName());

        verify(memberRepository).findAll();
        verify(memberMapper).listToDto(List.of(member));
    }

    @Test
    void searchResults_Fail_NoMatch() {
        member.setName("Musician");

        when(memberRepository.findAll()).thenReturn(List.of(member));

        MemberDto search = new MemberDto();
        search.setName("test");

        when(memberMapper.listToDto(anyList())).thenReturn(List.of());

        List<MemberDto> searchResults = memberServiceImpl.searchResults(search);

        assertEquals(0, searchResults.size());

        verify(memberRepository).findAll();
        verify(memberMapper).listToDto(argThat(List::isEmpty));
    }

    @Test
    void advancedSearchResults_Success() {
        member.setName("Musician");
        musicianDto.setName("Musician");

        List<Member> members = Collections.singletonList(member);

        when(memberRepository.findAll()).thenReturn(members);

        MemberArtistDto search = new MemberArtistDto();
        search.setMusician(musicianDto);

        when(memberArtistMapper.listToDto(anyList())).thenReturn(List.of(memberArtistDto));

        List<MemberArtistDto> searchResults = memberServiceImpl.advancedSearchResults(search);

        assertEquals(1, searchResults.size());
        assertEquals("Musician", searchResults.get(0).getMusician().getName());

        verify(memberRepository).findAll();
        verify(memberArtistMapper).listToDto(anyList());
    }

    @Test
    void advancedSearchResults_Fail_NoMatch() {
        when(memberRepository.findAll()).thenReturn(List.of(member));

        MemberArtistDto search = new MemberArtistDto();
        search.setMusician(musicianDto);

        when(memberArtistMapper.listToDto(anyList())).thenReturn(List.of());

        List<MemberArtistDto> searchResults = memberServiceImpl.advancedSearchResults(search);

        assertTrue(searchResults.isEmpty());
        verify(memberRepository).findAll();
        verify(memberArtistMapper).listToDto(Collections.emptyList());
    }

    @Test
    void getAllMembers_Success() {
        List<Member> members = Collections.singletonList(member);

        when(memberRepository.findAll()).thenReturn(members);
        when(memberArtistMapper.listToDto(anyList())).thenReturn(List.of(memberArtistDto));

        memberServiceImpl.getAllMembers();

        verify(memberRepository).findAll();
        verify(memberArtistMapper).listToDto(anyList());
    }

}