package br.com.zupacademy.apass.pix.keymanagergrpc.endpoint

import br.com.zupacademy.apass.pix.keymanagergrpc.endpoint.RemocaoDeChavePixServiceGrpc.RemocaoDeChavePixServiceImplBase
import br.com.zupacademy.apass.pix.keymanagergrpc.external_service.bcb.BcbClient
import br.com.zupacademy.apass.pix.keymanagergrpc.external_service.bcb.DeletePixKeyRequest
import br.com.zupacademy.apass.pix.keymanagergrpc.external_service.erp_itau.ErpItauClient
import br.com.zupacademy.apass.pix.keymanagergrpc.repository.ChavePixRepository
import io.grpc.Status
import io.grpc.StatusRuntimeException
import io.grpc.stub.StreamObserver
import io.micronaut.http.HttpStatus
import io.micronaut.http.client.exceptions.HttpClientResponseException
import jakarta.inject.Singleton
import java.util.*

@Singleton
class RemocaoDeChavePixEndpont(
    private val chavePixRepository: ChavePixRepository,
    private val erpItauClient: ErpItauClient,
    private val bcbClient: BcbClient
) : RemocaoDeChavePixServiceImplBase() {

    override fun remove(request: RemocaoDeChavePixRequest?, responseObserver: StreamObserver<RemocaoDeChavePixReply>?) {
        if (request == null) {
            responseObserver?.onError(
                StatusRuntimeException(
                    Status.INVALID_ARGUMENT
                        .withDescription("Requisicao não pode ser nula!")
                )
            )
            return
        }

        //TODO: Validação do UUID
        val possivelChavePix = this.chavePixRepository.findByIdentificador(UUID.fromString(request.chavePixId))

        if (possivelChavePix.isEmpty) {
            responseObserver?.onError(
                StatusRuntimeException(
                    Status.NOT_FOUND.withDescription("Chave PIX com identificador " + request.chavePixId + " não foi encontrada!")
                )
            )
            return
        }

        possivelChavePix.get().let {
            if (it.clienteId != request.clienteId) {
                responseObserver?.onError(
                    StatusRuntimeException(
                        Status.PERMISSION_DENIED
                            .withDescription("Opera não permitida. Somente o próprietário da chave PIX pode removê-la!")
                    )
                )
                return
            }

            try {
                this.bcbClient.deletePixKey(
                    key = it.valorChave!!,
                    request = DeletePixKeyRequest(
                        key = it.valorChave!!,
                        participant = this.erpItauClient.consultaConta(
                            clienteId = it.clienteId,
                            tipo = it.tipoConta
                        ).instituicao.ispb
                    )
                )
                this.chavePixRepository.delete(it)
            } catch (httpClientResponseException: HttpClientResponseException) {
                when (httpClientResponseException.status.code) {
                    HttpStatus.NOT_FOUND.code -> {
                    }
                    HttpStatus.FORBIDDEN.code -> {
                        responseObserver?.onError(
                            StatusRuntimeException(
                                Status.PERMISSION_DENIED.withDescription("Não foi permitida realizar a operação!")
                            )
                        )
                        return
                    }
                    else -> {
                        responseObserver?.onError(
                            StatusRuntimeException(
                                Status.UNKNOWN.withDescription("Erro desconhecido ao tentar realizar a exclusão da chave PIX!")
                            )
                        )
                        return
                    }
                }
            }
        }

        responseObserver?.onNext(
            RemocaoDeChavePixReply
                .newBuilder()
                .setMensagem("Chave PIX deletada com sucesso!")
                .build()
        )

        responseObserver?.onCompleted()
    }
}