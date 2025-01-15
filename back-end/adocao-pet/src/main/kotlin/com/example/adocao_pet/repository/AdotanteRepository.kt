package com.example.adocao_pet.repository

import com.example.adocao_pet.model.Adotante
import org.springframework.data.jpa.repository.JpaRepository

interface AdotanteRepository : JpaRepository<Adotante, Long>