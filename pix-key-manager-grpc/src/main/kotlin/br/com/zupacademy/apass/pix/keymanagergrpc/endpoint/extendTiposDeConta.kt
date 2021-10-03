package br.com.zupacademy.apass.pix.keymanagergrpc.endpoint

import br.com.zupacademy.apass.pix.keymanagergrpc.model.TiposDeConta as TiposDeContaModel

fun TiposDeConta.toModel() : TiposDeContaModel {
    return when(this) {
        TiposDeConta.CONTA_CORRENTE -> TiposDeContaModel.CONTA_CORRENTE
        TiposDeConta.CONTA_POUPANCA -> TiposDeContaModel.CONTA_POUPANCA
        else -> throw IllegalArgumentException("Tipo de conta inválido!")
    }
}

fun tiposDeContasToRequest(tipoDeConta: TiposDeContaModel) : TiposDeConta {
    return when(tipoDeConta) {
        TiposDeContaModel.CONTA_CORRENTE -> TiposDeConta.CONTA_CORRENTE
        TiposDeContaModel.CONTA_POUPANCA -> TiposDeConta.CONTA_POUPANCA
    }
}