package br.com.zupacademy.apass.pix.keymanagergrpc.validation

import org.hibernate.validator.internal.constraintvalidators.hv.br.CPFValidator

class ChaveCpfValidador() : ChaveValidator {

    private val mensagem = "CPF inválido";

    override fun valida(valor: String?): ChaveValidacaoResult {
        if (valor.isNullOrBlank()) {
            return ChaveValidacaoResult.chaveInvalidaResult(mensagem)
        }

        if (!valor.matches("^[0-9]{11}\$".toRegex())) {
            return ChaveValidacaoResult.chaveInvalidaResult(mensagem)
        }

        CPFValidator().run {
            initialize(null)
            isValid(valor, null)
        }.let {
            return if(it) {
                ChaveValidacaoResult.chaveValidaResult()
            } else {
                ChaveValidacaoResult.chaveInvalidaResult(mensagem)
            }
        }
    }
}