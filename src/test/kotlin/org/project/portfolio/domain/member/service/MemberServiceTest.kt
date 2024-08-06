package org.project.portfolio.domain.member.service

import jakarta.validation.ConstraintViolationException
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.project.portfolio.domain.member.dto.MemberDtoRequest
import org.project.portfolio.domain.member.entity.Member
import org.project.portfolio.domain.member.repository.MemberRepository
import org.project.portfolio.global.exception.InvalidInputException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.MethodArgumentNotValidException
import kotlin.test.assertEquals

@SuppressWarnings("NonAsciiCharacters")
@SpringBootTest
@Transactional
class MemberServiceTest {

    @Autowired
    lateinit var memberService: MemberService

    @Autowired
    lateinit var memberRepository: MemberRepository

    @Nested
    inner class 회원가입_테스트 {
        @Test
        fun 회원가입을_할_수_있다() {
            // given
            val memberDtoRequest = MemberDtoRequest(
                null,
                "test1",
                "Test12345%%",
                "username",
                "test@test",
                "010-1234-5678")

            // when
            memberService.signUp(memberDtoRequest)

            // then
            assertEquals(1,  memberRepository.findAll().count())

        }

        @Test
        fun 같은_아이디를_사용하면_오류가_난다() {
            // given
            val memberDtoRequest1 = MemberDtoRequest(
                null,
                "test1",
                "Test12345%%",
                "username",
                "test@test",
                "010-1234-5678")

            val memberDtoRequest2 = MemberDtoRequest(
                null,
                "test1",
                "Test12345%%",
                "username",
                "test@test",
                "010-1234-5678")

            // when
            memberService.signUp(memberDtoRequest1)

            // then
            val assertion = assertThrows<InvalidInputException> {
                memberService.signUp(memberDtoRequest2)
            }
            Assertions.assertEquals("이미 등록된 ID 입니다.", assertion.message)
        }
    }


}