package br.com.zupacademy.apass.pix.keymanagergrpc.external_service.bcb

import io.micronaut.core.annotation.Introspected
import java.time.LocalDateTime
import javax.validation.Valid
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

@Introspected
data class CreatePixKeyResponse(
    @NotNull
    private val keyType: KeyType,

    @field:NotBlank
    val key: String,

    @field:NotNull
    @field:Valid
    private val bankAccount: BankAccountRequest,

    @field:NotNull
    @field:Valid
    private val owner: OwnerResponse,

    @field:NotNull
    val createdAt: LocalDateTime
)