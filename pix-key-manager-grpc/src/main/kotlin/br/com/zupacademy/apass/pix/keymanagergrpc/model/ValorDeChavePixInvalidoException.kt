package br.com.zupacademy.apass.pix.keymanagergrpc.model

import java.lang.IllegalArgumentException

class ValorDeChavePixInvalidoException(message: String): IllegalArgumentException(message) {
}