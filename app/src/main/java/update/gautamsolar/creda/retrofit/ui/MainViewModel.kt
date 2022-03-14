package update.gautamsolar.creda.retrofit.ui

import android.graphics.Bitmap
import android.util.Base64
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import update.gautamsolar.creda.retrofit.MainRepository
import update.gautamsolar.creda.retrofit.util.ApiState
import java.io.ByteArrayOutputStream
import java.util.Base64.getEncoder
import javax.inject.Inject

@HiltViewModel
class MainViewModel
@Inject
constructor(private val mainRepository: MainRepository) : ViewModel() {

    val response:MutableState<ApiState> = mutableStateOf(ApiState.Empty)

 fun getOrderList(id:String) = viewModelScope.launch {
        val params = HashMap<String, String>()
        params.put("eng_id", id)

        mainRepository.getQuarterlyList(params)
            .onStart {
                response.value = ApiState.Loading
            }.catch {
                response.value = ApiState.Failure(it)
            }.collect {
                response.value = ApiState.QuarterlyList(it)
            }
    }
 fun postDetails(hashMap: HashMap<String, String>) = viewModelScope.launch {

        mainRepository.postDetails(hashMap)
            .onStart {
                response.value = ApiState.Loading
            }.catch {
                response.value = ApiState.Failure(it)
            }.collect {
                response.value = ApiState.QuarterlyList(it)
            }
    }

    fun convertImageFileToBase64(bm: Bitmap): String {
    val baos = ByteArrayOutputStream()
    bm.compress(Bitmap.CompressFormat.JPEG, 40, baos) //bm is the bitmap object
    val b = baos.toByteArray()
    val encodedImage = Base64.encodeToString(b, Base64.CRLF)
    return encodedImage
    }
}