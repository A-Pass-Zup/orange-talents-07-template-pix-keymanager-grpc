package br.com.zupacademy.apass.pix.keymanagergrpc

import br.com.zupacademy.apass.pix.keymanagergrpc.model.TiposDeChavePix
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.util.*

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
        assertEquals(false, TiposDeChavePix.CPF.valida("1").valida)
    }

    @Test
    fun `quando a chave aleatoria for valida`() {
        assertEquals(true, TiposDeChavePix.ALEATORIA.valida(UUID.randomUUID().toString()).valida)
    }

    @Test
    fun `quando a chave aleatoria for invalida`() {
        assertEquals(false, TiposDeChavePix.ALEATORIA.valida("").valida)
        assertEquals(false, TiposDeChavePix.ALEATORIA.valida(null).valida)

    }

    @Test
    fun `quando a chave celular for valida`() {
        assertEquals(true, TiposDeChavePix.CELULAR.valida("+5566988887777").valida)
    }

    @Test
    fun `quando a chave celular for invalida`() {
        assertEquals(false, TiposDeChavePix.CELULAR.valida("").valida)
        assertEquals(false, TiposDeChavePix.CELULAR.valida(null).valida)
        assertEquals(false, TiposDeChavePix.CELULAR.valida("7777").valida)
    }

    @Test
    fun `quando a chave email for valida`() {
        assertEquals(true, TiposDeChavePix.EMAIL.valida("user@dominio.com").valida)
    }

    @Test
    fun `quando a chave email for invalida`() {
        assertEquals(false, TiposDeChavePix.EMAIL.valida("").valida)
        assertEquals(false, TiposDeChavePix.EMAIL.valida(null).valida)
        assertEquals(false, TiposDeChavePix.EMAIL.valida("user_domionio.com").valida)
    }

}