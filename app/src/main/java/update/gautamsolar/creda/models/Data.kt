package update.gautamsolar.creda.models

import com.squareup.moshi.Json

data class Data (

	@Json(name="reg_no_new") val reg_no_new : String ?="",
	@Json(name="lat") val lat : String ?="",
	@Json(name="long") val long : String ?="",
	@Json(name="quaterly_reportimage_1") val quaterly_reportimage_1 : String ?="",
	@Json(name="panel_image_1") val panel_image_1 : String ?="",
	@Json(name="Controller_image_1") val controller_image_1 : String ?="",
	@Json(name="fname") val fname : String ?="",
	@Json(name="fathername") val fathername : String ?="",
	@Json(name="contact_no") val contact_no : String ?="",
	@Json(name="village") val village : String ?="",
	@Json(name="block") val block : String ?="",
	@Json(name="district") val district : String ?="",
	@Json(name="reg_no") val reg_no : String ?="",
	@Json(name="pump_capacity") val pump_capacity : String ?="",
	@Json(name="controler_rms_id") val controler_rms_id : String ?="",
	@Json(name="controller_srno") val controller_srno : String ?="",
	@Json(name="motor_srno") val motor_srno : String ?="",
	@Json(name="pump_head") val pump_head : String ?="",
	@Json(name="surce_type") val surce_type : String ?="",
	@Json(name="phase") val phase : String ?="",
	@Json(name="gender") val gender : String ?="",
	@Json(name="category") val category : String ?="",
	@Json(name="saral_no") val saral_no : String ?="",
	@Json(name="system_available_status") val system_available_status : String ?="",
	@Json(name="remarks") val remarks : String ?=""
)