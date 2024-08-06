package org.project.portfolio.domain.member.service

import org.project.portfolio.domain.member.dto.LoginDto
import org.project.portfolio.domain.member.dto.MemberDtoRequest
import org.project.portfolio.domain.member.dto.MemberDtoResponse
import org.project.portfolio.domain.member.entity.Member
import org.project.portfolio.domain.member.entity.MemberRole
import org.project.portfolio.domain.member.repository.MemberRepository
import org.project.portfolio.domain.member.repository.MemberRoleRepository
import org.project.portfolio.global.authority.JwtTokenProvider
import org.project.portfolio.global.authority.TokenInfo
import org.project.portfolio.global.exception.InvalidInputException
import org.project.portfolio.global.status.ROLE
import org.springframework.data.repository.findByIdOrNull
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class MemberService(
    private val memberRepository: MemberRepository,
    private val memberRoleRepository: MemberRoleRepository,
    private val authenticationManagerBuilder: AuthenticationManagerBuilder,
    private val jwtTokenProvider: JwtTokenProvider,
    private val encoder: PasswordEncoder,
) {
    /**
     * 회원가입
     */
    fun signUp(memberDtoRequest: MemberDtoRequest): String {
        // ID 중복검사
        var member: Member? = memberRepository.findByLoginId(memberDtoRequest.loginId)
        if (member != null) {
            throw InvalidInputException("loginId", "이미 등록된 ID 입니다.")
        }
        // 비밀번호 암호화 및 Member 객체 생성
        val encodedPassword = encoder.encode(memberDtoRequest.password)
        member = memberDtoRequest.toMember(encodedPassword)

        memberRepository.save(member)

        val memberRole:MemberRole = MemberRole(null, ROLE.MEMBER, member)
        memberRoleRepository.save(memberRole)

        return "회원가입이 완료되었습니다."
    }

    /**
     * 로그인 -> 토큰 발행
     */
    fun login(longinDto: LoginDto): TokenInfo {
        val member = memberRepository.findByLoginId(longinDto.loginId)
            ?: throw InvalidInputException("loginId", "존재하지 않는 ID입니다.")

        if (!encoder.matches(longinDto.password, member.password)) {
            throw InvalidInputException("password", "비밀번호가 일치하지 않습니다.")
        }

        val authenticationToken = UsernamePasswordAuthenticationToken(longinDto.loginId, longinDto.password)
        val authentication = authenticationManagerBuilder.`object`.authenticate(authenticationToken)

        return jwtTokenProvider.createToken(authentication)
    }

    /**
     * 내 정보 조회
     */
    fun searchMyInfo(id: Long): MemberDtoResponse {
        val member: Member = memberRepository.findByIdOrNull(id) ?: throw InvalidInputException("id", "회원번호(${id})가 존재하지 않는 유저입니다.")
        return member.toDto()
    }

    /**
     * 내 정보 수정
     */
    fun modifyMyInfo(memberDtoRequest: MemberDtoRequest): String {
        val encodedPassword = encoder.encode(memberDtoRequest.password)
        val member: Member = memberDtoRequest.toMember(encodedPassword)
        memberRepository.save(member)

        return "회원정보 수정완료"
    }
}