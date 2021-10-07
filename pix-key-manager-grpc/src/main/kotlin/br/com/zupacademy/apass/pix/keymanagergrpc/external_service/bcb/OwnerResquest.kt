package br.com.zupacademy.apass.pix.keymanagergrpc.external_service.bcb

import br.com.zupacademy.apass.pix.keymanagergrpc.external_service.erp_itau.TitularResponse
import io.micronaut.core.annotation.Introspected
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull
import javax.validation.constraints.Pattern

@Introspected
class OwnerResquest(
    titular: TitularResponse
) {
    @field:NotNull
    val type = OwnerType.NATURAL_PERSON

    @field:NotBlank
    val name = titular.nome

    @field:NotBlank
    @field:Pattern(regexp = "^\\d{11}\$")
    val taxIdNumber: String = titular.cpf
}