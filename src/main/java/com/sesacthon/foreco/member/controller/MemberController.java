package com.sesacthon.foreco.member.controller;

import com.sesacthon.foreco.member.dto.response.LoginResponseDto;
import com.sesacthon.foreco.member.dto.response.MemberInfoResponseDto;
import com.sesacthon.foreco.member.service.MemberInfoService;
import com.sesacthon.foreco.member.service.MemberSignUpService;
import com.sesacthon.global.response.DataResponse;
import com.sesacthon.global.response.MessageResponse;
import com.sesacthon.infra.feign.dto.response.KakaoUserInfoResponseDto;
import com.sesacthon.infra.feign.dto.response.KakaoUserUnlinkResponseDto;
import com.sesacthon.infra.feign.service.KakaoFeignService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@Tag(name = "멤버 관련 컨트롤러")
public class MemberController {

  private final KakaoFeignService kakaoFeignService;
  private final MemberSignUpService memberSignUpService;
  private final MemberInfoService memberInfoService;

  /**
   * 로그인 요청을 통해 인가코드를 redirect url로 발급 가능
   */
  @Operation(
      summary = "인가 코드 발급",
      description = "해당 url을 통해 로그인 화면으로 넘어간 후, 사용자가 정보를 입력하면 redirect url에서 코드를 발급할 수 있습니다.")
  @GetMapping("/api/v1/kakao/login")
  public ResponseEntity<HttpHeaders> getKakaoAuthCode(@RequestParam("redirectUri") String redirectUri)  {
    HttpHeaders httpHeaders = kakaoFeignService.kakaoLogin(redirectUri);
    return new ResponseEntity<>(httpHeaders, HttpStatus.SEE_OTHER);
  }

  @Operation(
      summary = "카카오 계정 회원가입",
      description = "토큰과 사용자의 지역정보로 요청하면, 사용자의 정보를 저장한 후 사용자의 Id를 확인할 수 있습니다.")
  @GetMapping("/api/v1/account/kakao/result")
  public ResponseEntity<DataResponse<LoginResponseDto>> kakaoLogin(
      @RequestParam("token") String token,
      @RequestParam(value = "region") String region,
      @RequestParam(value = "type") String residenceType) {
    KakaoUserInfoResponseDto kakaoUser = kakaoFeignService.getKakaoInfo(token);
    LoginResponseDto kakaoLoginResponse = memberSignUpService.loginKakaoMember(kakaoUser, region, residenceType);
    return new ResponseEntity<>(
        DataResponse.of(
            HttpStatus.CREATED, "카카오 계정으로 회원가입 성공", kakaoLoginResponse), HttpStatus.CREATED);
  }

  /**
   * 유저가 자기 자신의 정보에 대해 알 수 있다.
   */
  @Operation(
      summary = "미션탭에서 내 정보 조회",
      description = "사용자의 정보(프로필, 포인트, 이름 정보를 볼 수 있습니다.")
  //TODO: UUID 고정 빼야 함
//  @PreAuthorize("isAuthenticated()")
  @GetMapping("/api/v1/mission/dashboard")
  public ResponseEntity<DataResponse<MemberInfoResponseDto>> getMember() {
    UUID memberId = UUID.fromString("667c59d0-0524-47fb-ab5a-effdaa62b598");
    MemberInfoResponseDto memberInfo = memberInfoService.getMember(memberId);
    return new ResponseEntity<>(
        DataResponse.of(HttpStatus.OK, "멤버 정보 조회 성공", memberInfo), HttpStatus.OK);
  }

  @Operation(
      summary = "서비스 탈퇴"
  )
  @GetMapping("/api/v1/account/quit")
  public ResponseEntity<MessageResponse> kakaoLogout(
      @RequestParam("token") String token
  ) {
    KakaoUserUnlinkResponseDto response = kakaoFeignService.unlinkService(token);
    memberInfoService.deleteMemberInfo(response.getId());
    return new ResponseEntity<>(
        MessageResponse.of(HttpStatus.OK, "서비스 탈퇴 성공"), HttpStatus.OK);
  }

}
