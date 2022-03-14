package update.gautamsolar.creda.models

import com.squareup.moshi.Json

data class QuarterListModel (

	@Json(name="status") val status : String?=null,
	@Json(name="error") val error : Boolean?=null,
	@Json(name="message") val message : String?=null,
	@Json(name="Data") val data : List<Data>?=null
)