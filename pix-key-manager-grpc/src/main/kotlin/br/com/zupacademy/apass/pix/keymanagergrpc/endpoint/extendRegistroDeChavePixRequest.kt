package br.com.zupacademy.apass.pix.keymanagergrpc.endpoint.extend

import br.com.zupacademy.apass.pix.keymanager.endpoint.RegistroDeChavePixRequest
import br.com.zupacademy.apass.pix.keymanagergrpc.endpoint.toModel
import br.com.zupacademy.apass.pix.keymanagergrpc.model.RegistroDeChavePix

fun RegistroDeChavePixRequest.toModel(): RegistroDeChavePix {

    return RegistroDeChavePix(clientId = this.clienteId,
            tipoChavePix = this.tipoDeChave.toModel(),
            valorChave = this.valorChavePix,
            tipoConta = this.tipoConta.toModel())
}