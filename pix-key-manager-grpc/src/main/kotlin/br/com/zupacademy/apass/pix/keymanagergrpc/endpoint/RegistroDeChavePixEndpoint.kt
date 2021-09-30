package br.com.zupacademy.apass.pix.keymanagergrpc.endpoint

import br.com.zupacademy.apass.pix.keymanager.endpoint.RegistroDeChavePixReply
import br.com.zupacademy.apass.pix.keymanager.endpoint.RegistroDeChavePixRequest
import br.com.zupacademy.apass.pix.keymanager.endpoint.RegistroDeChavePixServiceGrpc
import br.com.zupacademy.apass.pix.keymanagergrpc.endpoint.extend.toModel
import br.com.zupacademy.apass.pix.keymanagergrpc.model.ValorDeChavePixInvalidoException
import br.com.zupacademy.apass.pix.keymanagergrpc.repository.RegistroDeChavePixRepository
import io.grpc.Status
import io.grpc.StatusRuntimeException
import io.grpc.stub.StreamObserver
import jakarta.inject.Singleton
import javax.validation.ConstraintViolationException

@Singleton
class RegistroDeChavePixEndpoint(val registroDeChavePixRepository: RegistroDeChavePixRepository)
    : RegistroDeChavePixServiceGrpc.RegistroDeChavePixServiceImplBase() {

    override fun registra(request: RegistroDeChavePixRequest?, responseObserver: StreamObserver<RegistroDeChavePixReply>?) {
        if (request == null) {
            responseObserver?.onError(StatusRuntimeException(
                    Status.INVALID_ARGUMENT
                            .withDescription("Requisição é obrigatória!")))
            return
        }

        if (this.registroDeChavePixRepository
                        .existsByValorChave(request.valorChavePix)) {

            responseObserver?.onError(StatusRuntimeException(
                    Status.ALREADY_EXISTS.withDescription("Chave pix já cadastrada!")
            ))
            return
        }

        try {

            request.toModel().let {
                this.registroDeChavePixRepository.save(it)
            }

            responseObserver?.onNext(
                    RegistroDeChavePixReply
                            .newBuilder()
                            .setMessage("Chave PIX cadastrada com sucesso!")
                            .build())

            responseObserver?.onCompleted()

        } catch (illegalArgs: IllegalArgumentException) {
            responseObserver?.onError(
                    StatusRuntimeException(
                            Status.INVALID_ARGUMENT
                                    .augmentDescription(illegalArgs.message)))

        } catch (chaveInvalidaExcpetion: ValorDeChavePixInvalidoException) {
            responseObserver?.onError(
                    StatusRuntimeException(
                            Status.INVALID_ARGUMENT
                                    .withDescription(chaveInvalidaExcpetion.message)))

        } catch (constraintVioletion: ConstraintViolationException) {
            responseObserver?.onError(
                    StatusRuntimeException(
                            Status.INVALID_ARGUMENT
                                    .augmentDescription("Erro de restrição/validação!")))
        }
    }
}