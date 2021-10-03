package br.com.zupacademy.apass.pix.keymanagergrpc

import br.com.zupacademy.apass.pix.keymanagergrpc.model.TiposDeChavePix
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

//@MicronautTest(transactional = false)
class ValidationsUnitTest {

    @Test
    fun `quando o cpf for valido`() {
        assertEquals(true, TiposDeChavePix.CPF.valida("28125877096").valida)
    }

    @Test
    fun `quando o cpf for invalido`() {
        assertEquals(false, TiposDeChavePix.CPF.valida(null).valida)
        assertEquals(false, TiposDeChavePix.CPF.valida("").valida)
        assertEquals(false, TiposDeChavePix.CPF.valida("99999999999").valida)
    }
}