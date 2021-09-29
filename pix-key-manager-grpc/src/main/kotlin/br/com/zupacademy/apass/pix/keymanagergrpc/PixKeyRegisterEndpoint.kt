package br.com.zupacademy.apass.pix.keymanagergrpc

import br.com.zupacademy.apass.pix.keymanager.PixKeyManagerServiceGrpc
import br.com.zupacademy.apass.pix.keymanager.PixKeyRegisterReply
import br.com.zupacademy.apass.pix.keymanager.PixKeyRegisterRequest
import io.grpc.stub.StreamObserver

class PixKeyRegisterEndpoint : PixKeyManagerServiceGrpc.PixKeyManagerServiceImplBase() {

    override fun send(request: PixKeyRegisterRequest?, responseObserver: StreamObserver<PixKeyRegisterReply>?) {
        super.send(request, responseObserver)
    }
}