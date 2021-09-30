package br.com.zupacademy.apass.pix.keymanagergrpc.external_service.bcb

import br.com.zupacademy.apass.pix.keymanagergrpc.model.RegistroDeChavePix
import br.com.zupacademy.apass.pix.keymanagergrpc.model.TiposDeChavePix

class CreatePixKeyRequest(registroDeChavePix: RegistroDeChavePix) {

    val keyType: KeyType = registroDeChavePix.tipoChavePix.run {
        when(this) {
            TiposDeChavePix.CPF -> KeyType.CPF
            TiposDeChavePix.CELULAR -> KeyType.PHONE
            TiposDeChavePix.EMAIL -> KeyType.EMAIL
            TiposDeChavePix.ALEATORIA -> KeyType.RANDOM
        }
    }

    val key = registroDeChavePix.valorChave

}
