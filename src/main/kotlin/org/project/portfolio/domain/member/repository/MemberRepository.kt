package org.project.portfolio.domain.member.repository

import org.project.portfolio.domain.member.entity.Member
import org.project.portfolio.domain.member.entity.MemberRole
import org.springframework.data.jpa.repository.JpaRepository

interface MemberRepository : JpaRepository<Member, Long> {
    fun findByLoginId(loginId: String): Member?
}

interface MemberRoleRepository : JpaRepository<MemberRole, Long>