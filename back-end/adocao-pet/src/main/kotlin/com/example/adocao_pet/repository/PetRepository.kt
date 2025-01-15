package com.example.adocao_pet.repository

import com.example.adocao_pet.model.Pet
import org.springframework.data.jpa.repository.JpaRepository

interface PetRepository : JpaRepository<Pet, Long> {
    fun existsByNome(nome: String): Boolean
}