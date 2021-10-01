package br.com.zupacademy.apass.pix.keymanagergrpc.repository

import br.com.zupacademy.apass.pix.keymanagergrpc.model.RegistroDeChavePix
import io.micronaut.data.annotation.Repository
import io.micronaut.data.jpa.repository.JpaRepository

@Repository
interface RegistroDeChavePixRepository : JpaRepository<RegistroDeChavePix, Long> {
    fun existsByValorChave(valorChave: String) : Boolean
}