package br.com.zupacademy.apass.pix.keymanagergrpc.validation

interface ChaveValidator {

    fun valida(valor: String?): ChaveValidacaoResult

}