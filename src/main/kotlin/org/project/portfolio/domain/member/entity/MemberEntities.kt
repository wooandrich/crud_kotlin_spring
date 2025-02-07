package org.project.portfolio.domain.member.entity

import jakarta.persistence.*
import org.project.portfolio.domain.member.dto.MemberDtoResponse
import org.project.portfolio.global.common.domain.BaseEntity
import org.project.portfolio.global.status.ROLE

@Entity
@Table(
    uniqueConstraints = [UniqueConstraint(name = "uk_member_login_id", columnNames = ["loginId"])]
)
class Member(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "member_id")
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

    ) : BaseEntity() {
        @OneToMany(fetch = FetchType.LAZY, mappedBy = "member")
        val memberRole: List<MemberRole>? = null

    fun toDto(): MemberDtoResponse =
        MemberDtoResponse(id!!, loginId, username, email, phoneNumber)
    }



@Entity
class MemberRole(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long? = null,

    @Column(nullable = false, length = 30)
    @Enumerated(EnumType.STRING)
    val role: ROLE,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(foreignKey = ForeignKey(name = "fk_member_role_id"))
    val member: Member,
)