package update.gautamsolar.creda.retrofit.network

import retrofit2.http.*
import update.gautamsolar.creda.models.QuarterListModel

interface ApiService {

    companion object{
        const val BASE_URL = "http://gautamsolar.co.in/"
    }

    @POST("pumpall_api/getallList_quaterly_v1.php")
    @FormUrlEncoded
    suspend fun getQuarterlyList(@FieldMap params: HashMap<String,String>): QuarterListModel

    @POST("pumpall_api/quaterly_update_v1.php")
    @FormUrlEncoded
    suspend fun postDetails(@FieldMap params: HashMap<String,String>): QuarterListModel
}