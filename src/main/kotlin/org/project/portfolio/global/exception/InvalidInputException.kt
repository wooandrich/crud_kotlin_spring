package org.project.portfolio.global.exception

class InvalidInputException(
    val fieldName: String = "",
    message: String = "Invalid input"
): RuntimeException(message)