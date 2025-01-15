package com.example.adocao_pet.model

import com.fasterxml.jackson.annotation.JsonFormat
import java.time.LocalDateTime

data class AdocaoDTO(
    val id: Long,  // Identificador da adoção
    val pet_id: Long,  // ID do Pet
    val pet_nome: String?,  // Nome do Pet (nullable)
    val pet_especie: String?,  // Espécie do Pet (nullable)
    val pet_raca: String?,  // Raça do Pet (nullable)
    val pet_idade: Int?,  // Idade do Pet (nullable)
    val pet_descricao: String?,  // Descrição do Pet (nullable)
    val pet_status: String?,  // Status do Pet (nullable)
    val pet_foto: String?,  // Foto do Pet (base64, nullable)

    val adotante_id: Long,  // ID do Adotante
    val adotante_nome: String?,  // Nome do Adotante (nullable)
    val adotante_endereco: String?,  // Endereço do Adotante (nullable)

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")  // Formato da data
    val dataAdocao: LocalDateTime,
)