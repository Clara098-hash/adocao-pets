package com.example.adocao_pet.service

import com.example.adocao_pet.repository.PetRepository
import com.example.adocao_pet.model.Pet
import org.springframework.stereotype.Service

@Service
class PetService(private val petRepository: PetRepository) {

    fun cadastrarPet(nome: String, idade: Int, especie: String, raca: String?, descricao: String?, foto: ByteArray?) {
        val pet = Pet(
            nome = nome,
            idade = idade,
            especie = especie,
            raca = raca,
            descricao = descricao,
            foto = foto,
            status = "Disponível" // Define o status como "Disponível" ao cadastrar
        )
        petRepository.save(pet)
    }

    fun listarPets(): List<Pet> {
        return petRepository.findAll().filter { it.status == "Disponível" }  // Filtra apenas os pets com status "Disponível"
    }

    fun atualizarStatus(id: Long, novoStatus: String): Boolean {
        val petOptional = petRepository.findById(id)
        return if (petOptional.isPresent) {
            val pet = petOptional.get().copy(status = novoStatus) // Cria uma cópia com o novo status
            petRepository.save(pet)
            true
        } else {
            false // Retorna falso se o pet não for encontrado
        }
    }
}