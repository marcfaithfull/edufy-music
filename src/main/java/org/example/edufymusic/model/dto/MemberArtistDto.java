package org.example.edufymusic.model.dto;

import java.util.List;

public class MemberArtistDto {
    private MemberDto musician;
    private List<ArtistDto> memberOf;

    public MemberDto getMusician() {
        return musician;
    }

    public void setMusician(MemberDto musician) {
        this.musician = musician;
    }

    public List<ArtistDto> getMemberOf() {
        return memberOf;
    }

    public void setMemberOf(List<ArtistDto> memberOf) {
        this.memberOf = memberOf;
    }
}
