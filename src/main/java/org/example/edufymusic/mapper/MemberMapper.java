package org.example.edufymusic.mapper;

import org.example.edufymusic.model.dto.MemberDto;
import org.example.edufymusic.model.entity.Member;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MemberMapper {

    public MemberDto toDto(Member member) {
        MemberDto dto = new MemberDto();
        dto.setUrl("https://stream.edufy.com/member/" +
                member.getId() + "/" +
                member.getName().replaceAll("\\s+", "-").toLowerCase());
        dto.setId(member.getId());
        dto.setName(member.getName());
        return dto;
    }

    public List<MemberDto> listToDto(List<Member> members) {
        List<MemberDto> dTos = new ArrayList<>();
        for (Member member : members) {
            MemberDto dto = toDto(member);
            dTos.add(dto);
        }
        return dTos;
    }
}