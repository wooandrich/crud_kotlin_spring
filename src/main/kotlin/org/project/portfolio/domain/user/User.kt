package org.project.portfolio.domain.user

import jakarta.persistence.*
import org.project.portfolio.global.common.domain.BaseEntity

@Entity
@Table(name = "users")
class User : BaseEntity() {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    var username: String? = null
    var password: String? = null
    var email: String? = null
    var phone: String? = null
}