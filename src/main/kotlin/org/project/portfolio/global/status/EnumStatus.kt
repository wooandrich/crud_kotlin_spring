package org.project.portfolio.global.status

enum class ResultCode(val msg: String) {
    SUCCESS("정상 처리 되었습니다."),
    ERROR("에러가 발생했습니다.")
}

enum class ROLE {
    MEMBER
}
