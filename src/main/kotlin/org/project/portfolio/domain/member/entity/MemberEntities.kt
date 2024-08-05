package org.project.portfolio.domain.member.entity

import jakarta.persistence.*
import org.project.portfolio.global.common.domain.BaseEntity

@Entity
@Table(
    uniqueConstraints = [UniqueConstraint(name = "uk_member_login_id", columnNames = ["loginId"])]
)
class Member(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long? = null,

    @Column(nullable = false, length = 30, updatable = false)
    val loginId: String,

    @Column(nullable = false, length = 100)
    val password: String,

    @Column(nullable = false, length = 10)
    val username: String,

    @Column(nullable = false, length = 30)
    val email: String,

    @Column(nullable = false, length = 100)
    val phoneNumber: String,

    ) : BaseEntity()