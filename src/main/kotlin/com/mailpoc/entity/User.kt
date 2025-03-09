package com.mailpoc.entity

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import lombok.AllArgsConstructor
import lombok.Getter
import lombok.NoArgsConstructor

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
data class User (

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private val id: Long = 0,
    private var name: String= "",
    private var email: String = "",
    private var status : String= "",
    private var age : Int= 0,
){
    fun updateStatus(status: String) : User {
        return User(
            id = this.id,
            name = this.name,
            email = this.email,
            status = status,
            age = this.age
        )
    }
}
