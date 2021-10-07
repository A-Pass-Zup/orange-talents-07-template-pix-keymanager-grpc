package br.com.zupacademy.apass.pix.keymanagergrpc.external_service.bcb

import io.micronaut.core.annotation.Introspected
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

@Introspected
data class OwnerResponse(
    @field:NotNull
    val type: OwnerType,

    @field:NotBlank
    val name: String,

    @field:NotBlank
    val taxIdNumber: String
)
