package br.com.zupacademy.apass.pix.keymanagergrpc.external_service.bcb

import br.com.zupacademy.apass.pix.keymanagergrpc.external_service.erp_itau.DadosDaContaResponse
import br.com.zupacademy.apass.pix.keymanagergrpc.model.ChavePix
import br.com.zupacademy.apass.pix.keymanagergrpc.model.TiposDeChavePix
import br.com.zupacademy.apass.pix.keymanagergrpc.model.TiposDeConta
import io.micronaut.core.annotation.Introspected
import javax.validation.Valid
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

@Introspected
class CreatePixKeyRequest(chavePix: ChavePix, conta: DadosDaContaResponse) {

    @field:NotNull
    val keyType: KeyType = chavePix.tipoChavePix.run {
        when (this) {
            TiposDeChavePix.CPF -> KeyType.CPF
            TiposDeChavePix.CELULAR -> KeyType.PHONE
            TiposDeChavePix.EMAIL -> KeyType.EMAIL
            TiposDeChavePix.ALEATORIA -> KeyType.RANDOM
        }
    }

    @field:NotBlank
    val key = chavePix.valorChave

    @field:Valid
    val bankAccount = BankAccountRequest(
        participant = conta.instituicao.ispb,
        branch = conta.agencia,
        accountNumber = conta.numero,
        accountType = when (conta.tipo) {
            TiposDeConta.CONTA_POUPANCA -> AccountType.SVGS
            TiposDeConta.CONTA_CORRENTE -> AccountType.CACC
        }
    )

    @field:Valid
    val owner = OwnerResquest(conta.titular)
}
