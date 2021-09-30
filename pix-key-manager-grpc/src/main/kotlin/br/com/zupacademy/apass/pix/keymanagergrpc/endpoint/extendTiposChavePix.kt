package br.com.zupacademy.apass.pix.keymanagergrpc.endpoint

import br.com.zupacademy.apass.pix.keymanager.endpoint.TiposDeChavePix
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