package br.com.zupacademy.apass.pix.keymanagergrpc

import br.com.zupacademy.apass.pix.keymanager.endpoint.RegistroDeChavePixRequest
import br.com.zupacademy.apass.pix.keymanager.endpoint.RegistroDeChavePixServiceGrpc
import br.com.zupacademy.apass.pix.keymanager.endpoint.TiposDeChavePix
import br.com.zupacademy.apass.pix.keymanager.endpoint.TiposDeConta
import io.grpc.ManagedChannel
import io.micronaut.context.annotation.Factory
import io.micronaut.grpc.annotation.GrpcChannel
import io.micronaut.grpc.server.GrpcServerChannel
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import jakarta.inject.Singleton
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.util.*

@MicronautTest(transactional = false)
internal class RegistroChavePixEndpointTest(
        val grpcClient: RegistroDeChavePixServiceGrpc.RegistroDeChavePixServiceBlockingStub) {

    @Test
    fun `quando registrar chave aleatoria valida deve cadastrar a chave pix`() {

        val response = grpcClient.registra(
                RegistroDeChavePixRequest
                        .newBuilder()
                        .setClienteId(UUID.randomUUID().toString())
                        .setTipoConta(TiposDeConta.CONTA_POUPANCA)
                        .setTipoDeChave(TiposDeChavePix.ALEATORIA)
                        .setValorChavePix("")
                        .build())


        assertEquals(response.message, "Sucesso!")
    }

    @Test
    fun `quando registrar chave cpf valida deve cadastrar a chave pix`() {
        val response = grpcClient.registra(
                RegistroDeChavePixRequest
                        .newBuilder()
                        .setClienteId(UUID.randomUUID().toString())
                        .setTipoConta(TiposDeConta.CONTA_POUPANCA)
                        .setTipoDeChave(TiposDeChavePix.CPF)
                        // cpf gerado aleatoriamente
                        .setValorChavePix("28125877096")
                        .build())


        assertEquals(response.message, "Sucesso!")
    }

    @Test
    fun `quando registrar chave email valida deve cadastrar a chave pix`() {
        val response = grpcClient.registra(
                RegistroDeChavePixRequest
                        .newBuilder()
                        .setClienteId(UUID.randomUUID().toString())
                        .setTipoConta(TiposDeConta.CONTA_POUPANCA)
                        .setTipoDeChave(TiposDeChavePix.EMAIL)
                        .setValorChavePix("user@domio.com")
                        .build())

        assertEquals(response.message, "Sucesso!")
    }

    @Test
    fun `quando registrar chave celular valida deve cadastrar a chave pix`() {
        val response = grpcClient.registra(
                RegistroDeChavePixRequest
                        .newBuilder()
                        .setClienteId(UUID.randomUUID().toString())
                        .setTipoConta(TiposDeConta.CONTA_POUPANCA)
                        .setTipoDeChave(TiposDeChavePix.CELULAR)
                        .setValorChavePix("+5566988887777")
                        .build())

        assertEquals(response.message, "Sucesso!")
    }

    @Factory
    class Clients {
        @Singleton
        fun blockingStub(@GrpcChannel(GrpcServerChannel.NAME) channel: ManagedChannel)
                : RegistroDeChavePixServiceGrpc.RegistroDeChavePixServiceBlockingStub {

            return RegistroDeChavePixServiceGrpc.newBlockingStub(channel)
        }
    }
}