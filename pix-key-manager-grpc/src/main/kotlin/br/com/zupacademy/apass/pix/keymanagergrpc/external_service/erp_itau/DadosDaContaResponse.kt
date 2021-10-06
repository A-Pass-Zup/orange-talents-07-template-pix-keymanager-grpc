package br.com.zupacademy.apass.pix.keymanagergrpc.external_service.erp_itau

import br.com.zupacademy.apass.pix.keymanagergrpc.model.TiposDeConta

data class DadosDaContaResponse(
        val tipo: TiposDeConta,
        val instituicao: InstituicaoResponse,
        val agencia: String,
        val numero: String,
        val titular: TitularResponse
)