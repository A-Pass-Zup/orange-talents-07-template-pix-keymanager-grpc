package br.com.zupacademy.apass.pix.keymanagergrpc.repository

import br.com.zupacademy.apass.pix.keymanagergrpc.model.ChavePix
import io.micronaut.data.annotation.Repository
import io.micronaut.data.jpa.repository.JpaRepository
import java.util.*

@Repository
interface ChavePixRepository : JpaRepository<ChavePix, Long> {
    fun existsByValorChave(valorChave: String) : Boolean

    fun findByIdentificador(identificador: UUID) : Optional<ChavePix>
}