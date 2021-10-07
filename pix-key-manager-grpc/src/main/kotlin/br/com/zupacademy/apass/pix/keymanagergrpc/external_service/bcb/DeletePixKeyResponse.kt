package br.com.zupacademy.apass.pix.keymanagergrpc.external_service.bcb

import io.micronaut.core.annotation.Introspected
import java.time.LocalDateTime
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

@Introspected
data class DeletePixKeyResponse(

    @field:NotBlank
    private val key: String,

    @field:NotBlank
    private val participant: String,

    @field:NotNull
    private val deletedAt: LocalDateTime
)