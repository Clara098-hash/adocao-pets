package com.example.adocao_pet.repository

import com.example.adocao_pet.model.Adocao
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface AdocaoRepository : JpaRepository<Adocao, Long> {
    // Ajuste os métodos para usar Long
    fun findByAdotanteId(adotanteId: Long): List<Adocao>
    fun findByPetId(petId: Long): List<Adocao>
}