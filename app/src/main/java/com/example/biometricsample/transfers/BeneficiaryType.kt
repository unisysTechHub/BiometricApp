package com.example.biometricsample.transfers

import androidx.annotation.DrawableRes
import com.example.biometricsample.R
import com.example.biometricsample.animations.MoneyServiceTypeModel
import com.example.biometricsample.animations.UTMoneyServiceTypes

enum class UTBeneficiaryTypes {
    Intra,
    Interanl,
    DomesticWire,
    DomesticACH,
    International
}

data class UTUTBeneficiaryTypeModel (  val title: String, @DrawableRes val image: Int,val  onItemClicked : () -> Unit = {})
fun UTBeneficiaryTypes.cardModel() : MoneyServiceTypeModel =  when (this) {


    UTBeneficiaryTypes.Intra -> MoneyServiceTypeModel(
        title = name,
        image = R.drawable.ic_launcher_background
    )
    UTBeneficiaryTypes.Interanl -> MoneyServiceTypeModel(
        title = name,
        image = R.drawable.ic_launcher_background
    )

    UTBeneficiaryTypes.DomesticWire -> MoneyServiceTypeModel(
        title = name,
        image = R.drawable.ic_launcher_background
    )
    UTBeneficiaryTypes.DomesticACH ->  MoneyServiceTypeModel(
        title = name,
        image = R.drawable.ic_launcher_background
    )
    UTBeneficiaryTypes.International ->  MoneyServiceTypeModel(
        title = name,
        image = R.drawable.ic_launcher_background
    )
}
