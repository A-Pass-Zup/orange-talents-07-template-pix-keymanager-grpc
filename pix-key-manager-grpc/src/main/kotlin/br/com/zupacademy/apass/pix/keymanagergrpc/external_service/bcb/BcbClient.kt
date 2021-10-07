package br.com.zupacademy.apass.pix.keymanagergrpc.external_service.bcb

import io.micronaut.http.MediaType
import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Delete
import io.micronaut.http.annotation.PathVariable
import io.micronaut.http.annotation.Post
import io.micronaut.http.client.annotation.Client
import javax.validation.Valid

@Client("\${external-service.bcb.base-uri}")
interface BcbClient {

    @Post(
        uri = "\${external-service.bcb.pix-create-urn}",
        produces = [MediaType.APPLICATION_XML],
        consumes = [MediaType.APPLICATION_XML]
    )
    fun createPixKey(@Valid @Body request: CreatePixKeyRequest): @Valid CreatePixKeyResponse

    @Delete(
        uri = "\${external-service.bcb.pix-delete-urn}",
        produces = [MediaType.APPLICATION_XML],
        consumes = [MediaType.APPLICATION_XML]
    )
    fun deletePixKey(@PathVariable key: String, @Valid @Body request: DeletePixKeyRequest): @Valid DeletePixKeyResponse
}