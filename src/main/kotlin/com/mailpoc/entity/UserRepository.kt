package com.mailpoc.entity

import org.springframework.batch.item.Chunk
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository : JpaRepository<User, Long> {
    fun findByStatus(status: String): List<User>
    fun save(user: User) : Unit
}
