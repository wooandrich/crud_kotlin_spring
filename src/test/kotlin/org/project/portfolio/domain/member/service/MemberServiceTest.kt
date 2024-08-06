package org.project.portfolio.domain.member.service

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.project.portfolio.domain.member.dto.LoginDto
import org.project.portfolio.domain.member.dto.MemberDtoRequest
import org.project.portfolio.domain.member.dto.MemberDtoResponse
import org.project.portfolio.domain.member.entity.Member
import org.project.portfolio.domain.member.repository.MemberRepository
import org.project.portfolio.global.authority.TokenInfo
import org.project.portfolio.global.exception.InvalidInputException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.repository.findByIdOrNull
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.transaction.annotation.Transactional
import kotlin.test.assertEquals

@SuppressWarnings("NonAsciiCharacters")
@SpringBootTest
@Transactional
@AutoConfigureMockMvc
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
            assertEquals("이미 등록된 ID 입니다.", assertion.message)
        }
    }

//    @Nested
//    inner class 로그인_테스트 {
//        @BeforeEach
//        fun 회원가입() {
//            val memberDtoRequest1 = MemberDtoRequest(
//                null,
//                "test1",
//                "Test12345%%",
//                "username",
//                "test@test",
//                "010-1234-5678")
//
//            memberService.signUp(memberDtoRequest1)
//        }
//
//        @Test
//        fun 로그인을_할_수_있다() {
//            // given
//            val loginDto = LoginDto("test1", "Test12345%%")
//
//            // when
//            val result = memberService.login(loginDto)
//
//            // then
//            assertNotNull(result)
//
//        }
//    }

    @Nested
    inner class 내_정보_조회_테스트 {
        @BeforeEach
        fun 회원가입() {
            val memberDtoRequest1 = MemberDtoRequest(
                null,
                "test1",
                "Test12345%%",
                "username",
                "test@test",
                "010-1234-5678")

            memberService.signUp(memberDtoRequest1)
        }

        @Test
        fun 내_정보를_조회할_수_있다() {
            // given
            val member: Member? = memberRepository.findByIdOrNull(1L)

            // when
            val searchMember: MemberDtoResponse = memberService.searchMyInfo(member?.id!!)

            // then
            assertEquals(member.id, searchMember.id)
            assertEquals(member.loginId, searchMember.loginId)
            assertEquals(member.username, searchMember.username)
            assertEquals(member.email, searchMember.email)
            assertEquals(member.phoneNumber, searchMember.phoneNumber)
        }

    }


}