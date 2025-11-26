package org.example.edufymusic.service;

import org.example.edufymusic.exception.ResourceNotFoundException;
import org.example.edufymusic.mapper.MemberArtistMapper;
import org.example.edufymusic.mapper.MemberMapper;
import org.example.edufymusic.model.dto.MemberArtistDto;
import org.example.edufymusic.model.dto.MemberDto;
import org.example.edufymusic.model.entity.Member;
import org.example.edufymusic.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MemberServiceImpl implements MemberService {
    private final MemberRepository memberRepository;
    private final MemberMapper memberMapper;
    private final MemberArtistMapper memberArtistMapper;

    @Autowired
    public MemberServiceImpl(MemberRepository memberRepository, MemberMapper memberMapper, MemberArtistMapper memberArtistMapper) {
        this.memberRepository = memberRepository;
        this.memberMapper = memberMapper;
        this.memberArtistMapper = memberArtistMapper;
    }

    @Override
    public MemberDto getMemberById(Long id) {
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Member not found"));
        return memberMapper.toDto(member);
    }

    @Override
    public List<MemberDto> searchResults(MemberDto search) {
        List<Member> members = memberRepository.findAll();
        List<Member> filteredMembers = new ArrayList<>();
        for (Member member : members) {
            if (member.getName().toLowerCase().contains(search.getName().toLowerCase())) {
                filteredMembers.add(member);
            }
        }
        return memberMapper.listToDto(filteredMembers);
    }

    @Override
    public List<MemberArtistDto> advancedSearchResults(MemberArtistDto search) {
        List<Member> members = memberRepository.findAll();
        List<Member> filteredMembers = new ArrayList<>();
        for (Member member : members) {
            if (member.getName().toLowerCase().contains(search.getMusician().getName().toLowerCase())) {
                filteredMembers.add(member);
            }
        }
        return memberArtistMapper.listToDto(filteredMembers);
    }

    @Override
    public List<MemberArtistDto> getAllMembers() {
        List<Member> members = memberRepository.findAll();
        return memberArtistMapper.listToDto(members);
    }
}

