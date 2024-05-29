package com.example.Gustomate.controller;

import com.example.Gustomate.api.KakaoMapClient;
import com.example.Gustomate.dto.MemberDTO;
import com.example.Gustomate.service.MemberService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor //MemberService에 대한 멤버를 사용 가능
public class MemberController {

    // 생성자 주입
    private final MemberService memberService;
    private final KakaoMapClient kakaoMapClient;

    @PostMapping("/locations/{id}")
    public void addRegion(
            @AuthenticationPrincipal UserDetails loginUser,
            @PathVariable("id") Long regionId
    ) {
        memberService.addRegion(loginUser.getUsername(), regionId);
    }

    /**
     * 내 동네 인증
     *
     * @param userDetails         security 에 저장된 유저 정보
     * @param userPositionRequest 현재 유저 위치 정보
     * @param userRegionId        유저 동네 ID
     */
    @PostMapping("/locations/{userRegionId}/auth")
    public void authenticateRegion(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestBody UserPositionRequest userPositionRequest,
            @PathVariable Long userRegionId

    ) {

        // 위도 경도 -> 지번 주소
        ApiAddressResponse addressDto = kakaoMapClient.getAddressByPosition(
                userPositionRequest.getLatitude(),
                userPositionRequest.getLongitude());

        userService.authenticateUserRegion(
                userDetails.getUsername(),
                convertAddressResponseToRegionDto(addressDto),
                userRegionId);

    }

    private RegionDto convertAddressResponseToRegionDto(ApiAddressResponse addressDto) {
        return RegionDto.builder()
                .state(addressDto.getRegion1DepthName())
                .city(addressDto.getRegion2DepthName())
                .town(addressDto.getRegion3DepthName())
                .build();
    }


    // 회원가입 페이지 출력 요청
    @GetMapping("/member/save")
    public String saveForm() {
        return "save";
    }

    @PostMapping("/member/save")    // name값을 requestparam에 담아온다
    public String save(@ModelAttribute MemberDTO memberDTO) {
        System.out.println("MemberController.save");
        System.out.println("memberDTO = " + memberDTO);
        memberService.save(memberDTO);

        return "login";
    }

    @GetMapping("/member/login")
    public String loginForm(){
        return "login";
    }


    @PostMapping("/member/login") // session : 로그인 유지
    public String login(@ModelAttribute MemberDTO memberDTO, HttpSession session) {
        MemberDTO loginResult = memberService.login(memberDTO);
        if (loginResult != null) {
            // login 성공
            session.setAttribute("loginEmail", loginResult.getMemberEmail());
            return "main";
        } else {
            // login 실패
            return "login";
        }
    }

    @GetMapping("/member/")
    public String findAll(Model model) {
        List<MemberDTO> memberDTOList = memberService.findAll();
        // 어떠한 html로 가져갈 데이터가 있다면 model 사용
        model.addAttribute("memberList", memberDTOList);
        return "list";

    }
}