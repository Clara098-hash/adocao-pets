package com.example.adocao_pet.controller

import com.example.adocao_pet.service.PetService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import java.util.Base64

@RestController
@RequestMapping("/pets")
class PetController(private val petService: PetService) {

    @PostMapping("/cadastro")
    fun cadastrarPet(
        @RequestParam nome: String,
        @RequestParam idade: Int,
        @RequestParam especie: String,
        @RequestParam raca: String?,
        @RequestParam descricao: String?,
        @RequestParam(required = false) foto: MultipartFile?
    ): ResponseEntity<Map<String, Any>> {
        return try {
            if (nome.isBlank()) {
                return ResponseEntity.badRequest().body(
                    mapOf("status" to "erro", "mensagem" to "O nome do pet não pode ser vazio.")
                )
            }
            if (idade <= 0) {
                return ResponseEntity.badRequest().body(
                    mapOf("status" to "erro", "mensagem" to "A idade do pet deve ser um número positivo.")
                )
            }

            // Converte a foto para ByteArray (se fornecida)
            val fotoBytes = foto?.bytes

            // Chama o serviço para cadastrar o pet
            petService.cadastrarPet(nome, idade, especie, raca, descricao, fotoBytes)

            ResponseEntity.ok(
                mapOf("status" to "sucesso", "mensagem" to "Pet '$nome' cadastrado com sucesso!")
            )
        } catch (e: Exception) {
            ResponseEntity.badRequest().body(
                mapOf("status" to "erro", "mensagem" to "Erro ao cadastrar pet: ${e.message}")
            )
        }
    }

    @GetMapping("/listar")
    fun listarPets(): ResponseEntity<List<Map<String, Any>>> {
        val pets = petService.listarPets()

        // Mapeia os pets para incluir a imagem codificada em Base64 e outras propriedades
        val petsComImagens = pets.map { pet ->
            mapOf(
                "id" to (pet.id ?: 0),
                "nome" to pet.nome,
                "idade" to pet.idade,
                "especie" to pet.especie,
                "raca" to (pet.raca ?: ""),
                "descricao" to (pet.descricao ?: ""),
                "foto" to (pet.foto?.let { Base64.getEncoder().encodeToString(it) } ?: ""),
                "status" to pet.status // Inclui o status do pet
            )
        }

        return ResponseEntity.ok(petsComImagens)
    }

    @PutMapping("/{id}/status")
    fun atualizarStatus(@PathVariable id: Long, @RequestParam status: String): ResponseEntity<Map<String, Any>> {
        return try {
            val sucesso = petService.atualizarStatus(id, status)
            if (sucesso) {
                ResponseEntity.ok(mapOf("status" to "sucesso", "mensagem" to "Status atualizado com sucesso!"))
            } else {
                ResponseEntity.badRequest().body(
                    mapOf("status" to "erro", "mensagem" to "Pet não encontrado.")
                )
            }
        } catch (e: Exception) {
            ResponseEntity.badRequest().body(
                mapOf("status" to "erro", "mensagem" to "Erro ao atualizar status: ${e.message}")
            )
        }
    }
}