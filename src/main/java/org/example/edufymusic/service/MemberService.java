package org.example.edufymusic.service;

import org.example.edufymusic.model.dto.MemberArtistDto;
import org.example.edufymusic.model.dto.MemberDto;
import org.example.edufymusic.model.entity.Member;

import java.util.List;

public interface MemberService {

    MemberDto getMemberById(Long id);

    List<MemberDto> searchResults(MemberDto search);

    List<MemberArtistDto> advancedSearchResults(MemberArtistDto search);

    List<MemberArtistDto> getAllMembers();
}
