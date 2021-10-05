package br.com.zupacademy.apass.pix.keymanagergrpc.endpoint.extend

import br.com.zupacademy.apass.pix.keymanagergrpc.endpoint.RegistroDeChavePixRequest
import br.com.zupacademy.apass.pix.keymanagergrpc.endpoint.toModel
import br.com.zupacademy.apass.pix.keymanagergrpc.model.ChavePix

fun RegistroDeChavePixRequest.toModel(): ChavePix {

    return ChavePix(clienteId = this.clienteId,
            tipoChavePix = this.tipoDeChave.toModel(),
            valorChave = this.valorChavePix,
            tipoConta = this.tipoConta.toModel())
}