package br.com.zupacademy.apass.pix.keymanagergrpc.external_service.bcb

data class BankAccountRequest(
        val participant: String,
        val branch: String,
        val accountNumber: String,
        val accountType: AccountType
)
