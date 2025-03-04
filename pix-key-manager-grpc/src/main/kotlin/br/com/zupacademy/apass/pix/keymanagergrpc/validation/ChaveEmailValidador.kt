package br.com.zupacademy.apass.pix.keymanagergrpc.validation

import org.hibernate.validator.internal.constraintvalidators.hv.EmailValidator


class ChaveEmailValidador : ChaveValidator {

    private val mensagem = "E-mail inválido!"

    override fun valida(valor: String?): ChaveValidacaoResult {
        if (valor.isNullOrBlank()) {
            return ChaveValidacaoResult.chaveInvalidaResult(this.mensagem)
        }

        EmailValidator().run {
            initialize(null)
            isValid(valor, null)
        }.let {
            return if (it) {
                ChaveValidacaoResult.chaveValidaResult()
            } else {
                ChaveValidacaoResult.chaveInvalidaResult(this.mensagem)
            }
        }
    }
}