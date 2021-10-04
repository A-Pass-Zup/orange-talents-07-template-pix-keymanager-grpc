package br.com.zupacademy.apass.pix.keymanagergrpc

import br.com.zupacademy.apass.pix.keymanagergrpc.endpoint.tiposDeChavePixToRequest
import br.com.zupacademy.apass.pix.keymanagergrpc.endpoint.toModel
import br.com.zupacademy.apass.pix.keymanagergrpc.model.TiposDeChavePix
import br.com.zupacademy.apass.pix.keymanagergrpc.endpoint.TiposDeChavePix as TiposDeChavePixRequest

import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

@MicronautTest(transactional = false)
class TiposDeChavePixTest {

    @Test
    fun `conversao de tipo de chave email endpoint para dominio`() {
        assertEquals(TiposDeChavePix.EMAIL, TiposDeChavePixRequest.EMAIL.toModel())
    }

    @Test
    fun `conversao de tipo de chave celular endpoint para dominio`() {
        assertEquals(TiposDeChavePix.CELULAR, TiposDeChavePixRequest.CELULAR.toModel())
    }

    @Test
    fun `conversao de tipo de chave cpf endpoint para domionio`() {
        assertEquals(TiposDeChavePix.CPF, TiposDeChavePixRequest.CPF.toModel())
    }

    @Test
    fun `conversao de tipo de chave aleatoria endpoint para dominio`() {
        assertEquals(TiposDeChavePix.ALEATORIA, TiposDeChavePixRequest.ALEATORIA.toModel())
    }

    @Test
    fun `conversao de tipo de chave cpf dominio para endpoint`() {
        assertEquals(TiposDeChavePixRequest.CPF, tiposDeChavePixToRequest(TiposDeChavePix.CPF))
    }

    @Test
    fun `conversao de tipo de chave aleatoria dominio para endpoint`() {
        assertEquals(TiposDeChavePixRequest.ALEATORIA, tiposDeChavePixToRequest(TiposDeChavePix.ALEATORIA))
    }

    @Test
    fun `conversao de tipo de chave email dominio para endpoint`() {
        assertEquals(TiposDeChavePixRequest.EMAIL, tiposDeChavePixToRequest(TiposDeChavePix.EMAIL))
    }

    @Test
    fun `conversao de tipo de chave celular dominio para endpoint`() {
        assertEquals(TiposDeChavePixRequest.CELULAR, tiposDeChavePixToRequest(TiposDeChavePix.CELULAR))
    }

    @Test
    fun `quando tentar converter chave pix endpoint para dominio invalida deve lancar excecao`() {
        assertThrows<IllegalArgumentException> {
            TiposDeChavePixRequest.TIPO_CHAVE_UNKNOWN.toModel()
        }

        assertThrows<IllegalArgumentException> {
            TiposDeChavePixRequest.UNRECOGNIZED.toModel()
        }
    }
}