package br.com.zupacademy.apass.pix.keymanagergrpc.external_service.bcb

import br.com.zupacademy.apass.pix.keymanagergrpc.external_service.erp_itau.DadosDaContaResponse
import br.com.zupacademy.apass.pix.keymanagergrpc.model.ChavePix
import br.com.zupacademy.apass.pix.keymanagergrpc.model.TiposDeChavePix
import br.com.zupacademy.apass.pix.keymanagergrpc.model.TiposDeConta

class CreatePixKeyRequest(chavePix: ChavePix, conta: DadosDaContaResponse) {

    val keyType: KeyType = chavePix.tipoChavePix.run {
        when(this) {
            TiposDeChavePix.CPF -> KeyType.CPF
            TiposDeChavePix.CELULAR -> KeyType.PHONE
            TiposDeChavePix.EMAIL -> KeyType.EMAIL
            TiposDeChavePix.ALEATORIA -> KeyType.RANDOM
        }
    }

    val key = chavePix.valorChave

    val banckAccount = BankAccountRequest(
            participant = conta.instituicao.ispb,
            branch = conta.agencia,
            accountNumber = conta.numero,
            accountType = when(conta.tipo) {
                TiposDeConta.CONTA_POUPANCA -> AccountType.SVGS
                TiposDeConta.CONTA_CORRENTE -> AccountType.CACC
            }
    )
}
