package org.project.portfolio.domain.member.dto

data class MemberDtoRequest (
    val id: Long?,
    private val _loginId: String?,
    private val _password: String?,
    private val _username: String?,
    private val _email: String?,
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
}