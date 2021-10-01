package br.com.zupacademy.apass.pix.keymanagergrpc.model

import java.util.*
import javax.persistence.*
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

@Entity
class RegistroDeChavePix(
        @field:NotBlank
        val clientId: String,

        @field:Enumerated(EnumType.STRING)
        @field:NotNull
        val tipoConta: TiposDeConta,

        @field:Enumerated(EnumType.STRING)
        @field:NotNull
        val tipoChavePix: TiposDeChavePix,

        valorChave: String? = null
) {
    @field:Id
    @field:GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    @field:NotNull
    @field:Column(unique = true, nullable = false)
    val valorChave: String = valorChave ?: UUID.randomUUID().toString()

    @field:NotNull
    @field:Column(nullable = false, unique = true)
    val pixId = UUID.randomUUID()


    init {
        if (this.tipoChavePix == TiposDeChavePix.ALEATORIA && !valorChave.isNullOrBlank()) {
            throw ValorDeChavePixInvalidoException("Chave ALEATORIA não pode ter valor definido!")
        }

        tipoChavePix.valida(valorChave).let {
            if (!it.valida) {
                throw ValorDeChavePixInvalidoException(it.mensagem!!)
            }
        }
    }
}