package com.example.adocao_pet.model

import jakarta.persistence.*

@Entity
@Table(name = "adotantes")
data class Adotante(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    val nome: String,

    val telefone: String? = null,

    val email: String? = null,

    val endereco: String? = null
)