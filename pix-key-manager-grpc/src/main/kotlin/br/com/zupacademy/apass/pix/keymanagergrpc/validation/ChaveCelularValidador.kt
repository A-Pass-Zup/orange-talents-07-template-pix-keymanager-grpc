package br.com.zupacademy.apass.pix.keymanagergrpc.validation

class ChaveCelularValidador : ChaveValidator {

    private val mensagem = "Número de telefone inválido!"

    override fun valida(valor: String?): ChaveValidacaoResult {
        if (valor.isNullOrBlank()) {
            return ChaveValidacaoResult.chaveInvalidaResult(this.mensagem)
        }

        if (!valor.matches("^\\+[1-9][0-9]\\d{1,14}\$".toRegex())) {
            return ChaveValidacaoResult.chaveInvalidaResult(this.mensagem)
        }

        return ChaveValidacaoResult.chaveValidaResult()
    }
}