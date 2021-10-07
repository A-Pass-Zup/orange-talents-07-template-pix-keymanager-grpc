package br.com.zupacademy.apass.pix.keymanagergrpc.validation

import java.util.*

class ChaveAleatoriaValidador : ChaveValidator {

    private val mensagem = "Chave aleatória inválida!"

    override fun valida(valor: String?): ChaveValidacaoResult {
        if (valor.isNullOrBlank()) {
            return ChaveValidacaoResult.chaveInvalidaResult(this.mensagem)
        }

        try {
            UUID.fromString(valor)
        } catch (illegalArgExcpt:IllegalArgumentException){
            return ChaveValidacaoResult.chaveInvalidaResult(this.mensagem)
        }

        return ChaveValidacaoResult.chaveValidaResult()

    }
}