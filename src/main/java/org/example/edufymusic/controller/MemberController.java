package org.example.edufymusic.controller;

import org.example.edufymusic.mapper.ResponseMapper;
import org.example.edufymusic.model.dto.MemberArtistDto;
import org.example.edufymusic.model.dto.MemberDto;
import org.example.edufymusic.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/music")
public class MemberController {
    private final MemberService memberService;

    @Autowired
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping("/findMemberById/{id}")
    public ResponseEntity<Map<String, Object>> findMemberById(@PathVariable Long id) {
        MemberDto memberDto = memberService.getMemberById(id);
        return ResponseEntity.status(HttpStatus.OK).body(ResponseMapper.mapResponse(
                200,
                memberDto.getName(),
                "/member/" + id
        ));
    }

    @GetMapping("/search/member")
    public ResponseEntity<List<MemberDto>> searchMember(@RequestBody MemberDto search) {
        List<MemberDto> searchResults = memberService.searchResults(search);
        return ResponseEntity.status(HttpStatus.OK).body(searchResults);
    }

    @GetMapping("/advanced/search/member")
    public ResponseEntity<List<MemberArtistDto>> advancedSearchMember(@RequestBody MemberArtistDto search) {
        List<MemberArtistDto> searchResults = memberService.advancedSearchResults(search);
        return ResponseEntity.status(HttpStatus.OK).body(searchResults);
    }

    @GetMapping("/members")
    public ResponseEntity<List<MemberArtistDto>> getAllMembers() {
        return ResponseEntity.status(HttpStatus.OK).body(memberService.getAllMembers());
    }
}
