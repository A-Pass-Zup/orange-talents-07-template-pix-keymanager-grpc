package br.com.zupacademy.apass.pix.keymanagergrpc.validation

class ChaveValidacaoResult private constructor(val valida: Boolean, val mensagem: String? = null) {
    companion object {
        fun chaveValidaResult(): ChaveValidacaoResult {
            return ChaveValidacaoResult(true)
        }

        fun chaveInvalidaResult(message: String): ChaveValidacaoResult {
            return ChaveValidacaoResult(false, message)
        }
    }
}
