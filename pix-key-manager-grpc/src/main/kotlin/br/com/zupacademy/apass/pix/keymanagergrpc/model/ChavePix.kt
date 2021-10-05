package br.com.zupacademy.apass.pix.keymanagergrpc.model

import java.util.*
import javax.persistence.*
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

@Entity
class ChavePix(
        @field:NotBlank
        val clienteId: String,

        @field:Enumerated(EnumType.STRING)
        @field:NotNull
        val tipoConta: TiposDeConta,

        @field:Enumerated(EnumType.STRING)
        @field:NotNull
        val tipoChavePix: TiposDeChavePix,

        @field:NotNull
        @field:Column(unique = true, nullable = false)
        var valorChave: String
) {
    @field:Id
    @field:GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    @field:NotNull
    @field:Column(nullable = false, unique = true)
    val identificador = UUID.randomUUID()!!

    init {
        if (this.tipoChavePix == TiposDeChavePix.ALEATORIA) {
            if(!valorChave.isNullOrBlank()) {
                throw ValorDeChavePixInvalidoException("Chave ALEATORIA não pode ter valor definido!")
            }

            this.valorChave = UUID.randomUUID().toString()
        }

        // Válida que a chave não será nula
        tipoChavePix.valida(this.valorChave).let {
            if (!it.valida) {
                throw ValorDeChavePixInvalidoException(it.mensagem!!)
            }
        }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ChavePix

        if (clienteId != other.clienteId) return false
        if (tipoConta != other.tipoConta) return false
        if (tipoChavePix != other.tipoChavePix) return false
        if (valorChave != other.valorChave) return false
        if (identificador != other.identificador) return false

        return true
    }

    override fun hashCode(): Int {
        var result = clienteId.hashCode()
        result = 31 * result + tipoConta.hashCode()
        result = 31 * result + tipoChavePix.hashCode()
        result = 31 * result + valorChave.hashCode()
        result = 31 * result + identificador.hashCode()
        return result
    }

}