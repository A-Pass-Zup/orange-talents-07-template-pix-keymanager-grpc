package br.com.zupacademy.apass.pix.keymanagergrpc

import br.com.zupacademy.apass.pix.keymanagergrpc.endpoint.*
import br.com.zupacademy.apass.pix.keymanagergrpc.model.ChavePix
import br.com.zupacademy.apass.pix.keymanagergrpc.model.TiposDeChavePix as TiposDeChavePixModel
import br.com.zupacademy.apass.pix.keymanagergrpc.model.TiposDeConta as TiposDeContaModel
import br.com.zupacademy.apass.pix.keymanagergrpc.repository.ChavePixRepository
import io.grpc.ManagedChannel
import io.grpc.Status
import io.grpc.StatusRuntimeException
import io.micronaut.context.annotation.Factory
import io.micronaut.grpc.annotation.GrpcChannel
import io.micronaut.grpc.server.GrpcServerChannel
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import jakarta.inject.Singleton
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.util.*

@MicronautTest(transactional = false)
internal class RegistroChavePixEndpointTest(
        val chavePixRepository: ChavePixRepository,
        val grpcClient: RegistroDeChavePixServiceGrpc.RegistroDeChavePixServiceBlockingStub) {

    @BeforeEach
    fun beforeEach() {
        this.chavePixRepository.deleteAll()
    }

    @Test
    fun `quando registrar chave aleatoria valida deve cadastrar a chave pix`() {

        val clienteId = UUID.randomUUID().toString();

        val response = grpcClient.registra(
                RegistroDeChavePixRequest
                        .newBuilder()
                        .setClienteId(clienteId)
                        .setTipoConta(TiposDeConta.CONTA_POUPANCA)
                        .setTipoDeChave(TiposDeChavePix.ALEATORIA)
                        .setValorChavePix("")
                        .build())


        assertEquals("Sucesso!", response.mensagem)
        val salvos = this.chavePixRepository.findAll();
        assertEquals(1, salvos.size)
        assertNotNull(salvos[0].id)
        assertEquals(clienteId, salvos[0].clienteId)
        assertEquals(TiposDeConta.CONTA_POUPANCA.toModel(), salvos[0].tipoConta)
        assertEquals(TiposDeChavePix.ALEATORIA.toModel(), salvos[0].tipoChavePix)
        assertEquals(false, salvos[0].valorChave.isBlank())
    }

    @Test
    fun `quando registrar chave cpf valida deve cadastrar a chave pix`() {
        val response = grpcClient.registra(
                RegistroDeChavePixRequest
                        .newBuilder()
                        .setClienteId(UUID.randomUUID().toString())
                        .setTipoConta(TiposDeConta.CONTA_POUPANCA)
                        .setTipoDeChave(TiposDeChavePix.CPF)
                        .setValorChavePix("28125877096")
                        .build())


        assertEquals("Sucesso!", response.mensagem)
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

        assertEquals("Sucesso!", response.mensagem)
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

        assertEquals("Sucesso!", response.mensagem)
    }

    @Test
    fun `quando registrar chave aleatoria invalida nao deve cadastrar a chave pix`() {

        assertThrows<StatusRuntimeException> {
            grpcClient.registra(
                    RegistroDeChavePixRequest
                            .newBuilder()
                            .setClienteId(UUID.randomUUID().toString())
                            .setTipoConta(TiposDeConta.CONTA_POUPANCA)
                            .setTipoDeChave(TiposDeChavePix.ALEATORIA)
                            .setValorChavePix(UUID.randomUUID().toString())
                            .build())
        }.let {
            assertEquals(Status.INVALID_ARGUMENT.code, it.status.code)
        }
    }

    @Test
    fun `quando registrar chave cpf invalida deve nao deve cadastrar a chave pix`() {
        assertThrows<StatusRuntimeException> {
            grpcClient.registra(
                    RegistroDeChavePixRequest
                            .newBuilder()
                            .setClienteId(UUID.randomUUID().toString())
                            .setTipoConta(TiposDeConta.CONTA_POUPANCA)
                            .setTipoDeChave(TiposDeChavePix.CPF)
                            .setValorChavePix("2812587709")
                            .build())

        }.let {
            assertEquals(Status.INVALID_ARGUMENT.code, it.status.code)
        }
    }

    @Test
    fun `quando registrar chave email invalida nao deve cadastrar a chave pix`() {
        assertThrows<StatusRuntimeException> {
            grpcClient.registra(
                    RegistroDeChavePixRequest
                            .newBuilder()
                            .setClienteId(UUID.randomUUID().toString())
                            .setTipoConta(TiposDeConta.CONTA_POUPANCA)
                            .setTipoDeChave(TiposDeChavePix.EMAIL)
                            .setValorChavePix("user_domio.com")
                            .build())
        }.let {
            assertEquals(Status.INVALID_ARGUMENT.code, it.status.code)
        }

    }

    @Test
    fun `quando registrar chave celular invalida nao deve cadastrar a chave pix`() {
        assertThrows<StatusRuntimeException> {
            grpcClient.registra(
                    RegistroDeChavePixRequest
                            .newBuilder()
                            .setClienteId(UUID.randomUUID().toString())
                            .setTipoConta(TiposDeConta.CONTA_POUPANCA)
                            .setTipoDeChave(TiposDeChavePix.CELULAR)
                            .setValorChavePix("66988887777")
                            .build())
        }.let {
            assertEquals(Status.INVALID_ARGUMENT.code, it.status.code)
        }
    }

    fun `quando tentar cadastrar uma chave ja existente deve falhar`() {

        val chaveExistente = ChavePix(
                clienteId = UUID.randomUUID().toString(),
                tipoConta = TiposDeContaModel.CONTA_POUPANCA,
                tipoChavePix = TiposDeChavePixModel.CPF,
                valorChave = "28125877096")

        this.chavePixRepository.save(chaveExistente)

        val requisicao = RegistroDeChavePixRequest.newBuilder()
                .setClienteId(chaveExistente.clienteId)
                .setTipoConta(tiposDeContasToRequest(chaveExistente.tipoConta))
                .setTipoDeChave(tiposDeChavePixToRequest(chaveExistente.tipoChavePix))
                .setValorChavePix(chaveExistente.valorChave)
                .build()

        assertThrows<StatusRuntimeException> {
            this.grpcClient.registra(requisicao)
        }.let {
            assertEquals(Status.ALREADY_EXISTS.code, it.status.code)
        }
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