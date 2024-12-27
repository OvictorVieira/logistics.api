package com.logistics.domain

import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "users", schema = "logistics")
data class User(
    @Id
    val userId: Long = 0,
    var name: String = ""
)
