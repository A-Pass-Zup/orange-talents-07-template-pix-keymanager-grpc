package br.com.zupacademy.apass.pix.keymanagergrpc

import br.com.zupacademy.apass.pix.keymanagergrpc.endpoint.tiposDeContasToRequest
import br.com.zupacademy.apass.pix.keymanagergrpc.endpoint.toModel
import br.com.zupacademy.apass.pix.keymanagergrpc.model.TiposDeConta
import br.com.zupacademy.apass.pix.keymanagergrpc.endpoint.TiposDeConta as TiposDeContasRequest
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

@MicronautTest(transactional = false)
class TiposDeContaTest {

    @Test
    fun `conversao tipo de conta poupanca endpoint para dominio`() {
        assertEquals(TiposDeContasRequest.CONTA_POUPANCA.toModel(), TiposDeConta.CONTA_POUPANCA)
    }

    @Test
    fun `conversao tipo de conta corrente endpoint para dominio`() {
        assertEquals(TiposDeContasRequest.CONTA_CORRENTE.toModel(), TiposDeConta.CONTA_CORRENTE)
    }

    @Test
    fun `conversao tipo de conta poupanca domionio para endpoint`() {
        assertEquals(TiposDeContasRequest.CONTA_POUPANCA, tiposDeContasToRequest(TiposDeConta.CONTA_POUPANCA))
    }

    @Test
    fun `conversao tipo de conta corrente domionio para endpoint`() {
        assertEquals(TiposDeContasRequest.CONTA_CORRENTE, tiposDeContasToRequest(TiposDeConta.CONTA_CORRENTE))
    }

    @Test
    fun `quando converter tipo de conta endpoint para dominio invalida deve lancar excecao`() {
        assertThrows<IllegalArgumentException> {
            TiposDeContasRequest.TIPO_CONTA_UNKNOWN.toModel()
        }
        assertThrows<IllegalArgumentException> {
            TiposDeContasRequest.UNRECOGNIZED.toModel()
        }
    }
}