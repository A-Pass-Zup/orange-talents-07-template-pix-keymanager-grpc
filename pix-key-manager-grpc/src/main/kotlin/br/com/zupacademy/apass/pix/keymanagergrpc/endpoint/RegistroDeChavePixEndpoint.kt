package br.com.zupacademy.apass.pix.keymanagergrpc.endpoint

import br.com.zupacademy.apass.pix.keymanagergrpc.endpoint.extend.toModel
import br.com.zupacademy.apass.pix.keymanagergrpc.external_service.bcb.BcbClient
import br.com.zupacademy.apass.pix.keymanagergrpc.external_service.bcb.CreatePixKeyRequest
import br.com.zupacademy.apass.pix.keymanagergrpc.external_service.erp_itau.ErpItauClient
import br.com.zupacademy.apass.pix.keymanagergrpc.model.ValorDeChavePixInvalidoException
import br.com.zupacademy.apass.pix.keymanagergrpc.repository.ChavePixRepository
import io.grpc.Status
import io.grpc.StatusRuntimeException
import io.grpc.stub.StreamObserver
import io.micronaut.http.HttpStatus
import io.micronaut.http.client.exceptions.HttpClientResponseException
import jakarta.inject.Singleton
import org.slf4j.LoggerFactory
import javax.validation.ConstraintViolationException

@Singleton
class RegistroDeChavePixEndpoint(
    val chavePixRepository: ChavePixRepository,
    val bcbClient: BcbClient,
    val erpItauClient: ErpItauClient
) : RegistroDeChavePixServiceGrpc.RegistroDeChavePixServiceImplBase() {

    private val logger = LoggerFactory.getLogger(this.javaClass)

    override fun registra(
        request: RegistroDeChavePixRequest?,
        responseObserver: StreamObserver<RegistroDeChavePixReply>?
    ) {
        if (request == null) {
            responseObserver?.onError(
                StatusRuntimeException(
                    Status.INVALID_ARGUMENT
                        .withDescription("Requisição é obrigatória!")
                )
            )
            return
        }

        if (this.chavePixRepository.existsByValorChave(request.valorChavePix)) {
            responseObserver?.onError(
                StatusRuntimeException(
                    Status.ALREADY_EXISTS.withDescription("Chave pix já cadastrada!")
                )
            )
            return
        }

        try {
            request.toModel().let {
                val response = this.bcbClient.createPixKey(
                    CreatePixKeyRequest(
                        chavePix = it,
                        conta = this.erpItauClient.consultaConta(clienteId = it.clienteId, tipo = it.tipoConta)
                    )
                )

                it.valorChave = response.key
                this.chavePixRepository.save(it)
            }

            responseObserver?.onNext(
                RegistroDeChavePixReply
                    .newBuilder()
                    .setMensagem("Sucesso!")
                    .build()
            )

            responseObserver?.onCompleted()

        } catch (chaveInvalidaExcpetion: ValorDeChavePixInvalidoException) {
            responseObserver?.onError(
                StatusRuntimeException(
                    Status.INVALID_ARGUMENT.withDescription(chaveInvalidaExcpetion.message)
                )
            )

        } catch (illegalArgs: IllegalArgumentException) {
            responseObserver?.onError(
                StatusRuntimeException(
                    Status.INVALID_ARGUMENT.withDescription(illegalArgs.message)
                )
            )

        } catch (constraintVioletion: ConstraintViolationException) {
            responseObserver?.onError(
                StatusRuntimeException(
                    Status.INVALID_ARGUMENT.withDescription("Erro de restrição/validação!")
                )
            )
        } catch (httpException: HttpClientResponseException) {
            if (httpException.status.code == HttpStatus.UNPROCESSABLE_ENTITY.code) {
                responseObserver?.onError(
                    StatusRuntimeException(
                        Status.ALREADY_EXISTS.withDescription("Chave PIX já registrada!")
                    )
                )
                return
            }

            responseObserver?.onError(
                StatusRuntimeException(
                    Status.UNKNOWN.withDescription("Falha desconhecida ao registra a chave PIX")
                )
            )
            this.logger.error(httpException.message, httpException)
            return
        }
    }
}