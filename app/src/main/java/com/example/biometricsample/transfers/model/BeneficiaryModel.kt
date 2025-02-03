package com.example.biometricsample.transfers.model

data class BeneficiaryModel(
    val accountNumber: String = "",
    val bankName: String = "",
    val beneficiaryName: String = "",
    val accountType: String = "",
    val transferType: String = "",
    val transferTypeACH: String = "",
    val phoneNumber: String = "",
    val email: String = "",
    val country: String = "US",
    val currency: String = "usd",
    val iban: String = "",
    val swiftBicCode: String = ""
)

 fun BeneficiaryModel.toApiModel(): com.example.api.request.BeneficiaryModel {
    return com.example.api.request.BeneficiaryModel(
        accountNumber = this.accountNumber,
        bankName = this.bankName,
        beneficiaryName = this.beneficiaryName,
        accountType = this.accountType,
        transferType = this.transferType,
        transferTypeACH = this.transferTypeACH,
        phoneNumber = this.phoneNumber,
        email = this.email,
        country = this.country,
        currency = this.currency,
        iban = this.iban,
        swiftBicCode = this.swiftBicCode
    )
}