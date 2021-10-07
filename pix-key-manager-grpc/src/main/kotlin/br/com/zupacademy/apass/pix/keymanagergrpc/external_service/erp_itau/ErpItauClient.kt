package br.com.zupacademy.apass.pix.keymanagergrpc.external_service.erp_itau

import br.com.zupacademy.apass.pix.keymanagergrpc.model.TiposDeConta
import io.micronaut.http.MediaType
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.PathVariable
import io.micronaut.http.annotation.QueryValue
import io.micronaut.http.client.annotation.Client

@Client("\${external-service.erp-itau.base-uri}")
interface ErpItauClient {

    @Get(uri = "\${external-service.erp-itau.consulta-conta-urn}", consumes = [MediaType.APPLICATION_JSON])
    fun consultaConta(@PathVariable clienteId: String, @QueryValue tipo: TiposDeConta): DadosDaContaResponse
}