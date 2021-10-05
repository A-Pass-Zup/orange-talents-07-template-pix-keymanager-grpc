package br.com.zupacademy.apass.pix.keymanagergrpc.external_service.bcb

import br.com.zupacademy.apass.pix.keymanagergrpc.model.ChavePix
import br.com.zupacademy.apass.pix.keymanagergrpc.model.TiposDeChavePix

class CreatePixKeyRequest(chavePix: ChavePix) {

    val keyType: KeyType = chavePix.tipoChavePix.run {
        when(this) {
            TiposDeChavePix.CPF -> KeyType.CPF
            TiposDeChavePix.CELULAR -> KeyType.PHONE
            TiposDeChavePix.EMAIL -> KeyType.EMAIL
            TiposDeChavePix.ALEATORIA -> KeyType.RANDOM
        }
    }

    val key = chavePix.valorChave

}
