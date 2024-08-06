package org.project.portfolio.domain.member.dto

import com.fasterxml.jackson.annotation.JsonProperty
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Pattern
import org.project.portfolio.domain.member.entity.Member
import org.springframework.security.crypto.password.PasswordEncoder

data class MemberDtoRequest(
    val id: Long?,

    @field:NotBlank
    @JsonProperty("loginId")
    private val _loginId: String?,

    @field:NotBlank
    @JsonProperty("password")
    @field:Pattern(
        regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d{5,})(?=.*[!@#\$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>/?]{2,}).+\$",
        message = "대소문자 포함, 숫자 5개 이상, 특수문자 포함 2개 이상 넣어야합니다."
    )
    private val _password: String?,

    @field:NotBlank
    @JsonProperty("username")
    private val _username: String?,

    @field:NotBlank
    @JsonProperty("email")
    @field:Email
    private val _email: String?,

    @field:NotBlank
    @JsonProperty("phoneNumber")
    @field:Pattern(
        regexp = "^01([0|1|6|7|8|9])-([0-9]{4})-([0-9]{4})\$",
        message = "숫자와 하이폰으로 구성된 형식이어야 합니다."

    )
    private val _phoneNumber: String?,

) {
    val loginId: String
        get() = _loginId!!
    val password: String
        get() = _password!!
    val username: String
        get() = _username!!
    val email: String
        get() = _email!!
    val phoneNumber: String
        get() = _phoneNumber!!

    fun toMember(encodedPassword: String): Member =
        Member(id, loginId, encodedPassword, username, email, phoneNumber)

}

data class LoginDto(
    @field:NotBlank
    @JsonProperty("loginId")
    private val _loginId: String?,

    @field:NotBlank
    @JsonProperty("password")
    private val _password: String?,
) {
    val loginId: String
        get() = _loginId!!
    val password: String
        get() = _password!!
}