package org.project.portfolio.domain.member.service

import org.project.portfolio.domain.member.dto.LoginDto
import org.project.portfolio.domain.member.dto.MemberDtoRequest
import org.project.portfolio.domain.member.entity.Member
import org.project.portfolio.domain.member.entity.MemberRole
import org.project.portfolio.domain.member.repository.MemberRepository
import org.project.portfolio.domain.member.repository.MemberRoleRepository
import org.project.portfolio.global.authority.JwtTokenProvider
import org.project.portfolio.global.authority.TokenInfo
import org.project.portfolio.global.exception.InvalidInputException
import org.project.portfolio.global.status.ROLE
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class MemberService(
    private val memberRepository: MemberRepository,
    private val memberRoleRepository: MemberRoleRepository,
    private val authenticationManagerBuilder: AuthenticationManagerBuilder,
    private val jwtTokenProvider: JwtTokenProvider,
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
        member = memberDtoRequest.toMember()

        memberRepository.save(member)

        val memberRole:MemberRole = MemberRole(null, ROLE.MEMBER, member)
        memberRoleRepository.save(memberRole)

        return "회원가입이 완료되었습니다."
    }

    /**
     * 로그인 -> 토큰 발행
     */
    fun login(longinDto: LoginDto): TokenInfo {
        val authenticationToken = UsernamePasswordAuthenticationToken(longinDto.loginId, longinDto.password)
        val authentication = authenticationManagerBuilder.`object`.authenticate(authenticationToken)

        return jwtTokenProvider.createToken(authentication)
    }
}