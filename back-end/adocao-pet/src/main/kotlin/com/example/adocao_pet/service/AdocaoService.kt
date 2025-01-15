package com.example.adocao_pet.service

import com.example.adocao_pet.model.Adocao
import com.example.adocao_pet.model.AdocaoDTO
import com.example.adocao_pet.repository.AdocaoRepository
import com.example.adocao_pet.repository.PetRepository
import com.example.adocao_pet.repository.AdotanteRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime
import java.util.Base64

@Service
class AdocaoService(
    private val adocaoRepository: AdocaoRepository,
    private val petRepository: PetRepository,
    private val adotanteRepository: AdotanteRepository
) {

    @Transactional
    fun registrarAdocao(petId: Long, adotanteId: Long) {
        // Verifica se o pet existe no banco
        val pet = petRepository.findById(petId).orElseThrow { Exception("Pet não encontrado") }

        // Verifica se o adotante existe no banco
        val adotante = adotanteRepository.findById(adotanteId).orElseThrow { Exception("Adotante não encontrado") }

        // Cria uma nova adoção e salva
        val adocao = Adocao(pet = pet, adotante = adotante)
        adocaoRepository.save(adocao)

        // Atualiza o status do pet para "Adotado"
        pet.status = "Adotado"  // Modificando a propriedade do objeto pet

        // Salva as alterações no pet
        petRepository.save(pet)
    }

    // Método para listar todas as adoções
    fun listarAdocoes(): List<AdocaoDTO> {
        val adocoes = adocaoRepository.findAll()

        return adocoes.map { adocao ->
            // Verificando se pet e adotante não são nulos
            val pet = petRepository.findById(adocao.pet.id).orElse(null)
            val adotante = adocao.adotante.id?.let { adotanteRepository.findById(it).orElse(null) }

            if (pet != null && adotante != null) {
                AdocaoDTO(
                    id = adocao.id ?: 0,
                    pet_id = pet.id ?: 0,
                    pet_nome = pet.nome,
                    pet_especie = pet.especie,
                    pet_raca = pet.raca ?: "Não disponível",  // Verificando se a raça é nula
                    pet_idade = pet.idade ?: 0,  // Garantindo valor para idade (caso nulo)
                    pet_descricao = pet.descricao ?: "Não disponível",  // Verificando se descrição é nula
                    pet_status = pet.status,
                    pet_foto = pet.foto?.let { encodeImageToBase64(it) },  // Convertendo a foto para base64

                    adotante_id = adotante.id ?: 0,
                    adotante_nome = adotante.nome,
                    adotante_endereco = adotante.endereco ?: "Não disponível",  // Verificando se endereço é nulo

                    dataAdocao = adocao.dataAdocao ?: LocalDateTime.now()  // Fallback para data caso seja nulo
                )
            } else {
                throw RuntimeException("Pet ou Adotante não encontrado para a Adoção ID: ${adocao.id}")
            }
        }
    }

    // Função para converter a foto do pet (ByteArray) para Base64
    private fun encodeImageToBase64(image: ByteArray): String {
        return Base64.getEncoder().encodeToString(image)
    }
}