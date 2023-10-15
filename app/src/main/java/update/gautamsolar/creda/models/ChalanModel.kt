package update.gautamsolar.creda.models

import com.google.gson.annotations.SerializedName

data class ChalanModel (

    @SerializedName("reg_no_new") val reg_no_new : Int,
    @SerializedName("fname") val fname : String,
    @SerializedName("fathername") val fathername : String,
    @SerializedName("contact_no") val contact_no : Double,
    @SerializedName("village") val village : String,
    @SerializedName("block") val block : String,
    @SerializedName("district") val district : String,
    @SerializedName("Chalan_saral_no") val chalanSaralNo : String,
    @SerializedName("Chalan_register_date") val chalanRegisterDate : String,
    @SerializedName("saral_chalan_image") val saralChalanImage : String,

)