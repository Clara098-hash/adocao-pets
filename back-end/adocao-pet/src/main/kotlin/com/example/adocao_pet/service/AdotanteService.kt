package com.example.adocao_pet.service

import com.example.adocao_pet.model.Adotante
import com.example.adocao_pet.repository.AdotanteRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class AdotanteService(private val adotanteRepository: AdotanteRepository) {

    // Função para cadastrar um adotante
    @Transactional
    fun cadastrarAdotante(nome: String, telefone: String, email: String, endereco: String?) {
        // Criação do objeto Adotante
        val adotante = Adotante(
            nome = nome,
            telefone = telefone,
            email = email,
            endereco = endereco  // Não precisa passar o id, o banco gera automaticamente
        )

        // Salvando o adotante no banco de dados
        adotanteRepository.save(adotante)  // O id será gerado automaticamente
    }

    // Função para listar todos os adotantes
    fun listarAdotantes(): List<Adotante> {
        return adotanteRepository.findAll() // Retorna todos os adotantes cadastrados
    }
}