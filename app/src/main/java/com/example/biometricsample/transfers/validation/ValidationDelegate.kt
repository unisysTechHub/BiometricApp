package com.example.biometricsample.transfers.validation

class ValidationDelegate {
    fun validate(property: String, value: String): String? {
        return when (property) {
            "accountNumber" -> if (value.length != 9) "Account Number must be 9 digits" else null
            "bankName" -> if (value.isBlank()) "Bank Name cannot be empty" else null
            "beneficiaryName" -> if (value.isBlank()) "Beneficiary Name cannot be empty" else null
            "accountType" -> if (value !in listOf("Saving", "Current")) "Invalid Account Type" else null
            "transferType" -> if (value.isBlank()) "Transfer Type is required" else null
            "phoneNumber" -> if (!value.matches("\\d{10}".toRegex())) "Phone Number must be 10 digits" else null
            "email" -> if (!value.contains("@")) "Invalid Email Address" else null
            "iban" -> if (value.isBlank()) "IBAN cannot be empty" else null
            "swiftBicCode" -> if (value.length != 8) "SWIFT/BIC Code must be 8 characters" else null
            else -> null
        }
    }
}
