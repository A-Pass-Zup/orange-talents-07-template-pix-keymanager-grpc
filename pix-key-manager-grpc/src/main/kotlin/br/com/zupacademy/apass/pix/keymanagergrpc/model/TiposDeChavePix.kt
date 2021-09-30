package br.com.zupacademy.apass.pix.keymanagergrpc.model

import br.com.zupacademy.apass.pix.keymanagergrpc.validation.*

enum class TiposDeChavePix(private val chaveValidator: ChaveValidator) {

    CPF(ChaveCpfValidador()),

    ALEATORIA(ChaveAleatoriaValidador()),

    EMAIL(ChaveEmailValidador()),

    CELULAR(ChaveCelularValidador());

    fun valida(valor: String?): ChaveValidacaoResult = this.chaveValidator.valida(valor)
}