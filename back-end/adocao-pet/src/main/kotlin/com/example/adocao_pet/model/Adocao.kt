package com.example.adocao_pet.model

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
data class Adocao(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @ManyToOne
    @JoinColumn(name = "pet_id", nullable = false)
    val pet: Pet,  // Relacionamento com Pet

    @ManyToOne
    @JoinColumn(name = "adotante_id", nullable = false)
    val adotante: Adotante,  // Relacionamento com Adotante

    val dataAdocao: LocalDateTime = LocalDateTime.now()
)