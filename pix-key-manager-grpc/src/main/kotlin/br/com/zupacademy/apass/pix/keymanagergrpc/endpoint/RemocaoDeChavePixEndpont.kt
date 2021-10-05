package br.com.zupacademy.apass.pix.keymanagergrpc.endpoint

import br.com.zupacademy.apass.pix.keymanagergrpc.endpoint.RemocaoDeChavePixServiceGrpc.RemocaoDeChavePixServiceImplBase
import br.com.zupacademy.apass.pix.keymanagergrpc.model.ChavePix
import br.com.zupacademy.apass.pix.keymanagergrpc.repository.ChavePixRepository
import io.grpc.Status
import io.grpc.StatusRuntimeException
import io.grpc.stub.StreamObserver
import jakarta.inject.Singleton
import java.util.*

@Singleton
class RemocaoDeChavePixEndpont(
  val chavePixRepository: ChavePixRepository
) : RemocaoDeChavePixServiceImplBase() {

    override fun remove(request: RemocaoDeChavePixRequest?, responseObserver: StreamObserver<RemocaoDeChavePixReply>?) {
        if(request == null) {
            responseObserver?.onError(
                    StatusRuntimeException(
                            Status.INVALID_ARGUMENT
                                    .withDescription("Requisicao não pode ser nula!")))
            return
        }


        val possivelChavePix = this.chavePixRepository.findByIdentificador(UUID.fromString(request.chavePixId))

        if(possivelChavePix.isEmpty) {
            responseObserver?.onError(StatusRuntimeException(
                    Status.NOT_FOUND.withDescription("Chave PIX com identificador " + request.chavePixId + " não foi encontrada!")
            ))
            return
        }

        if(possivelChavePix.get().clienteId != request.clienteId) {
            responseObserver?.onError(StatusRuntimeException(
                    Status.PERMISSION_DENIED
                            .withDescription("Opera não permitida. Somente o próprietário da chave PIX pode removê-la!")
            ))
            return
        }

        this.chavePixRepository.delete(possivelChavePix.get())

        responseObserver?.onNext(
                RemocaoDeChavePixReply
                        .newBuilder()
                        .setMensagem("Chave PIX deletada com sucesso!")
                        .build())

        responseObserver?.onCompleted()
    }
}