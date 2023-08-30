package kr.codesquad.secondhand.member.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.codesquad.secondhand.jwt.domain.Jwt;
import kr.codesquad.secondhand.jwt.service.JwtService;
import kr.codesquad.secondhand.member.domain.Member;
import kr.codesquad.secondhand.member.domain.MemberAddress;
import kr.codesquad.secondhand.member.domain.SignInType;
import kr.codesquad.secondhand.member.dto.OAuthSignInResponse;
import kr.codesquad.secondhand.member.repository.MemberAddressRepositoryImpl;
import kr.codesquad.secondhand.member.repository.MemberRepository;
import kr.codesquad.secondhand.member.repository.SignInTypeRepositoryImpl;
import kr.codesquad.secondhand.oauth.domain.OAuthProfile;
import kr.codesquad.secondhand.oauth.service.OAuthService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberService {

	private final OAuthService oAuthService;
	private final JwtService jwtService;

	private final SignInTypeRepositoryImpl signInTypeRepository;
	private final MemberRepository memberRepository;
	private final MemberAddressRepositoryImpl memberAddressRepository;

	@Transactional
	public OAuthSignInResponse signInOrSignUp(String providerName, String authorizationCode) {
		OAuthProfile oAuthProfile = oAuthService.requestOauthProfile(providerName, authorizationCode);
		SignInType signInType = signInTypeRepository.findByProvider(
			providerName); // TODO: 메모리 저장소에 캐싱해두고 사용할 수 있게 개선하는 방법 고민중
		Member member = oAuthProfile.toMember(signInType);

		// DB에 회원 저장 유무 판단
		Optional<Member> findMember = memberRepository.findBySignInTypeIdAndEmail(
			member.getSignInType().getId(),
			member.getEmail()
		);

		if (findMember.isEmpty()) {
			Long memberId = signUp(member);
			return signIn(member);
		}
		return signIn(findMember.get());
	}

	private Long signUp(Member member) {
		Long memberId = memberRepository.save(member);
		return memberId;
	}

	private OAuthSignInResponse signIn(Member member) {
		Jwt jwt = jwtService.issueJwt(member.getId());
		Optional<List<MemberAddress>> memberAddress = memberAddressRepository.findAllByMemberId(member.getId());
		if (memberAddress.isPresent()) {
			return OAuthSignInResponse.of(jwt, memberAddress.get(), member);
		}
		return OAuthSignInResponse.of(jwt, new ArrayList<>(), member);
	}
}
