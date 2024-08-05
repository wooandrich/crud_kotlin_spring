package org.project.portfolio.domain.member.service

import org.project.portfolio.domain.member.dto.MemberDtoRequest
import org.project.portfolio.domain.member.entity.Member
import org.project.portfolio.domain.member.repository.MemberRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class MemberService(
    private val memberRepository: MemberRepository
) {
    /**
     * 회원가입
     */
    fun signUp(memberDtoRequest: MemberDtoRequest): String {
        // ID 중복검사
        var member: Member? = memberRepository.findByLoginId(memberDtoRequest.loginId)
        if (member != null) {
            return "이미 등록된 ID 입니다."
        }
        member = Member(
            null,
            memberDtoRequest.loginId,
            memberDtoRequest.password,
            memberDtoRequest.username,
            memberDtoRequest.email,
            memberDtoRequest.phoneNumber,
        )
        memberRepository.save(member)

        return "회원가입이 완료되었습니다."
    }
}