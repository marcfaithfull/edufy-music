package org.example.edufymusic.mapper;

import org.example.edufymusic.model.dto.MemberArtistDto;
import org.example.edufymusic.model.dto.MemberDto;
import org.example.edufymusic.model.entity.Artist;
import org.example.edufymusic.model.entity.Member;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MemberArtistMapper {

    public MemberArtistDto toDto(Member member) {
        MemberArtistDto dto = new MemberArtistDto();

        MemberDto memberDto = new MemberDto();
        memberDto.setUrl("https://stream.edufy.com/member/" +
                member.getId() + "/" +
                member.getName().replaceAll("\\s+", "-").toLowerCase());
        memberDto.setName(member.getName());
        memberDto.setId((member.getId()));
        dto.setMusician(memberDto);

        List<org.example.edufymusic.model.dto.ArtistDto> artists = new ArrayList<>();
        for (Artist artist : member.getArtists()) {
            org.example.edufymusic.model.dto.ArtistDto artistDto = new org.example.edufymusic.model.dto.ArtistDto();
            artistDto.setName(artist.getName());
            artistDto.setId(artist.getId());
            artistDto.setGenre(artist.getGenre());
            artistDto.setUrl("https://stream.edufy.com/artist/" +
                    artist.getId() + "/" +
                    artist.getName().replaceAll("\\s+", "-").toLowerCase());
            artists.add(artistDto);
        }
        dto.setMemberOf(artists);
        return dto;
    }

    public List<MemberArtistDto> listToDto(List<Member> members) {
        List<MemberArtistDto> dTos = new ArrayList<>();
        for (Member member : members) {
            MemberArtistDto dto = toDto(member);
            dTos.add(dto);
        }
        return dTos;
    }
}
