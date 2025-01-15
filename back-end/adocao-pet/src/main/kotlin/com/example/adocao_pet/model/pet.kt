package com.example.adocao_pet.model

import jakarta.persistence.*

@Entity
@Table(name = "pets")
data class Pet(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,
    val nome: String,
    val idade: Int,
    val especie: String,
    val raca: String?,
    val descricao: String?,
    val foto: ByteArray? = null, // Armazenar a imagem como um array de bytes
    var status: String = "Disponível" // Adicionado o campo status com valor padrão
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Pet) return false

        if (id != other.id) return false
        if (nome != other.nome) return false
        if (idade != other.idade) return false
        if (especie != other.especie) return false
        if (raca != other.raca) return false
        if (descricao != other.descricao) return false
        if (foto != null) {
            if (other.foto == null) return false
            if (!foto.contentEquals(other.foto)) return false
        } else if (other.foto != null) return false
        if (status != other.status) return false // Adicionado no equals para comparar o status

        return true
    }

    override fun hashCode(): Int {
        var result = id?.hashCode() ?: 0
        result = 31 * result + nome.hashCode()
        result = 31 * result + idade
        result = 31 * result + especie.hashCode()
        result = 31 * result + (raca?.hashCode() ?: 0)
        result = 31 * result + (descricao?.hashCode() ?: 0)
        result = 31 * result + (foto?.contentHashCode() ?: 0)
        result = 31 * result + status.hashCode() // Incluído no hashCode para garantir consistência
        return result
    }
}