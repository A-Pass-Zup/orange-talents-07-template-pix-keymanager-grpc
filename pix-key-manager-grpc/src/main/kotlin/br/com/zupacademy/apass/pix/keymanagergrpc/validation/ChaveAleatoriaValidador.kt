package br.com.zupacademy.apass.pix.keymanagergrpc.validation

class ChaveAleatoriaValidador : ChaveValidator {
    override fun valida(valor: String?): ChaveValidacaoResult {
        if (valor.isNullOrBlank()) {
            return ChaveValidacaoResult.chaveInvalidaResult("Chave aleatórioa inválida!")
        }

        return ChaveValidacaoResult.chaveValidaResult()
    }
}