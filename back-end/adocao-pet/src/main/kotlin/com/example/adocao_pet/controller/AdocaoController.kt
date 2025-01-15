package com.example.adocao_pet.controller

import com.example.adocao_pet.model.AdocaoDTO
import com.example.adocao_pet.service.AdocaoService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/adocoes")
class AdocaoController(private val adocaoService: AdocaoService) {

    // Endpoint para registrar uma adoção
    @PostMapping("/registrar")
    fun registrarAdocao(@RequestBody adocaoDTO: AdocaoDTO): ResponseEntity<Map<String, String>> {
        return try {
            // Logs para depuração
            println("Recebendo requisição para registrar adoção: $adocaoDTO")

            // Verificando se os IDs são válidos
            val petId = adocaoDTO.pet_id
            val adotanteId = adocaoDTO.adotante_id

            println("Verificando IDs: petId=$petId, adotanteId=$adotanteId")

            // Chama o serviço para registrar a adoção
            adocaoService.registrarAdocao(petId, adotanteId)

            println("Adoção registrada com sucesso!")

            ResponseEntity.ok(mapOf("status" to "sucesso", "mensagem" to "Adoção registrada com sucesso!"))
        } catch (e: Exception) {
            println("Erro ao registrar adoção: ${e.message}")
            ResponseEntity.badRequest().body(mapOf("status" to "erro", "mensagem" to "Erro ao registrar adoção: ${e.message}"))
        }
    }


    // Endpoint para listar todas as adoções
    @GetMapping("/listar-adotados")
    fun listarAdotados(): ResponseEntity<List<AdocaoDTO>> {
        val adocoes = adocaoService.listarAdocoes()

        return ResponseEntity.ok(adocoes)
    }

}