package com.example.adocao_pet.controller

import com.example.adocao_pet.service.AdotanteService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/adotantes")
class AdotanteController(private val adotanteService: AdotanteService) {

    // Método para cadastrar adotante (já existente)
    @PostMapping("/cadastro")
    fun cadastrarAdotante(
        @RequestParam nome: String,
        @RequestParam telefone: String,
        @RequestParam email: String,
        @RequestParam endereco: String
    ): ResponseEntity<Map<String, Any>> {
        return try {
            // Validações
            if (nome.isBlank()) {
                return ResponseEntity.badRequest().body(
                    mapOf("status" to "erro", "mensagem" to "O nome do adotante não pode ser vazio.")
                )
            }
            if (telefone.isBlank()) {
                return ResponseEntity.badRequest().body(
                    mapOf("status" to "erro", "mensagem" to "O telefone do adotante não pode ser vazio.")
                )
            }
            if (email.isBlank()) {
                return ResponseEntity.badRequest().body(
                    mapOf("status" to "erro", "mensagem" to "O email do adotante não pode ser vazio.")
                )
            }
            if (endereco.isBlank()) {
                return ResponseEntity.badRequest().body(
                    mapOf("status" to "erro", "mensagem" to "O endereço do adotante não pode ser vazio.")
                )
            }

            // Chama o serviço para cadastrar o adotante
            adotanteService.cadastrarAdotante(nome, telefone, email, endereco)

            ResponseEntity.ok(
                mapOf("status" to "sucesso", "mensagem" to "Adotante '$nome' cadastrado com sucesso!")
            )
        } catch (e: Exception) {
            ResponseEntity.badRequest().body(
                mapOf("status" to "erro", "mensagem" to "Erro ao cadastrar adotante: ${e.message}")
            )
        }
    }

    @GetMapping("/listar")
    fun listarAdotantes(): ResponseEntity<List<Map<String, Any>>> {
        val adotantes = adotanteService.listarAdotantes()

        val adotantesComDetalhes = adotantes.map { adotante ->
            mapOf(
                "id" to (adotante.id ?: 0),
                "nome" to adotante.nome,
                "telefone" to (adotante.telefone ?: ""),
                "email" to (adotante.email ?: ""),
                "endereco" to (adotante.endereco ?: "")
            )
        }

        return ResponseEntity.ok(adotantesComDetalhes)
    }

    @ExceptionHandler(Exception::class)
    fun handleException(e: Exception): ResponseEntity<Map<String, Any>> {
        return ResponseEntity.badRequest().body(
            mapOf("status" to "erro", "mensagem" to "Erro ao processar a requisição: ${e.message}")
        )
    }
}