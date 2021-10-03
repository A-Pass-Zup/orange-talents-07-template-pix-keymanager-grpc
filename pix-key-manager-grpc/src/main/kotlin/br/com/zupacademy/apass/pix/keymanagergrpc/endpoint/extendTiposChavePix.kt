package br.com.zupacademy.apass.pix.keymanagergrpc.endpoint

import br.com.zupacademy.apass.pix.keymanagergrpc.model.TiposDeChavePix as TiposDeChavePixModel

fun TiposDeChavePix.toModel() : TiposDeChavePixModel {
    return when(this) {
        TiposDeChavePix.CELULAR -> TiposDeChavePixModel.CELULAR
        TiposDeChavePix.EMAIL -> TiposDeChavePixModel.EMAIL
        TiposDeChavePix.CPF -> TiposDeChavePixModel.CPF
        TiposDeChavePix.ALEATORIA -> TiposDeChavePixModel.ALEATORIA
        else -> throw IllegalArgumentException("Tipo de chave pix inválida!")
    }
}

fun tiposDeChavePixToRequest(tiposDeChavePix: TiposDeChavePixModel) : TiposDeChavePix {
    return when(tiposDeChavePix) {
        TiposDeChavePixModel.CELULAR -> TiposDeChavePix.CELULAR
        TiposDeChavePixModel.EMAIL -> TiposDeChavePix.EMAIL
        TiposDeChavePixModel.CPF -> TiposDeChavePix.CPF
        TiposDeChavePixModel.ALEATORIA -> TiposDeChavePix.ALEATORIA
    }
}