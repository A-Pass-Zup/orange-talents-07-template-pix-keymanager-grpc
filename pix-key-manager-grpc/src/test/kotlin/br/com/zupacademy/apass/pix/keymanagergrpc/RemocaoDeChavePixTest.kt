package br.com.zupacademy.apass.pix.keymanagergrpc

import br.com.zupacademy.apass.pix.keymanagergrpc.endpoint.RemocaoDeChavePixRequest
import br.com.zupacademy.apass.pix.keymanagergrpc.endpoint.RemocaoDeChavePixServiceGrpc
import br.com.zupacademy.apass.pix.keymanagergrpc.endpoint.tiposDeChavePixToRequest
import br.com.zupacademy.apass.pix.keymanagergrpc.model.ChavePix
import br.com.zupacademy.apass.pix.keymanagergrpc.model.TiposDeChavePix
import br.com.zupacademy.apass.pix.keymanagergrpc.model.TiposDeConta
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
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.util.*

@MicronautTest(transactional = false)
class RemocaoDeChavePixTest(
        val clientGrpc: RemocaoDeChavePixServiceGrpc.RemocaoDeChavePixServiceBlockingStub,
        val chavePixRepository: ChavePixRepository
) {

    @BeforeEach
    fun beforeEach() {
        this.chavePixRepository.deleteAll()
    }

    @Test
    fun `quado solicitado para remover chave pix com os dados corretos deve remover a chave pix`() {
        val chavePix = this.chavePixRepository.save(
                ChavePix(
                        clienteId = UUID.randomUUID().toString(),
                        tipoConta = TiposDeConta.CONTA_POUPANCA,
                        tipoChavePix = TiposDeChavePix.CELULAR,
                        valorChave = "+5566988887777"))

        val response = this.clientGrpc.remove(RemocaoDeChavePixRequest
                .newBuilder()
                .setClienteId(chavePix.clienteId)
                .setChavePixId(chavePix.identificador.toString())
                .build())

        assertEquals("Chave PIX deletada com sucesso!", response.mensagem)
        assertEquals(Optional.empty<ChavePix>(), this.chavePixRepository.findByIdentificador(chavePix.identificador))
    }

    @Test
    fun `quando remover chave pix nao sendo proprietario nao deve removar a chave pix`() {
        val chavePix = this.chavePixRepository.save(
                ChavePix(
                        clienteId = UUID.randomUUID().toString(),
                        tipoConta = TiposDeConta.CONTA_POUPANCA,
                        tipoChavePix = TiposDeChavePix.CELULAR,
                        valorChave = "+5566988887777"))

        assertThrows<StatusRuntimeException> {
            val response = this.clientGrpc.remove(RemocaoDeChavePixRequest
                    .newBuilder()
                    .setClienteId(UUID.randomUUID().toString())
                    .setChavePixId(chavePix.identificador.toString())
                    .build())
        }.let {
            assertEquals(Status.PERMISSION_DENIED.code, it.status.code)
        }

        assertEquals(Optional.of(chavePix), this.chavePixRepository.findByIdentificador(chavePix.identificador))
    }

    @Test
    fun `quando tentar remover chave pix que nao existe deve retornar NOT_FOUND`() {
        assertThrows<StatusRuntimeException> {
            val response = this.clientGrpc.remove(RemocaoDeChavePixRequest
                    .newBuilder()
                    .setClienteId(UUID.randomUUID().toString())
                    .setChavePixId(UUID.randomUUID().toString())
                    .build())
        }.let {
            assertEquals(Status.NOT_FOUND.code, it.status.code)
        }
    }

    @Factory
    class RemocaoChavePixServiceFactory {
        @Singleton
        fun create(@GrpcChannel(GrpcServerChannel.NAME) channel: ManagedChannel): RemocaoDeChavePixServiceGrpc.RemocaoDeChavePixServiceBlockingStub {
            return RemocaoDeChavePixServiceGrpc
                    .newBlockingStub(channel)
        }
    }
}