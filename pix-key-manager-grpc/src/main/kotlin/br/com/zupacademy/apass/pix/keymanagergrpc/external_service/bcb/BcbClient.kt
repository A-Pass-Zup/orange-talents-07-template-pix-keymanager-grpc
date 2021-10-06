package br.com.zupacademy.apass.pix.keymanagergrpc.external_service.bcb

import io.micronaut.http.annotation.Post
import io.micronaut.http.client.annotation.Client

@Client("\${external-service.bcb.base-uri}")
interface BcbClient {

    @Post
    fun registerPixKey()
}