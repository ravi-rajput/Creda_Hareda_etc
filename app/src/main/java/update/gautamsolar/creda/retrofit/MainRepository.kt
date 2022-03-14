package update.gautamsolar.creda.retrofit

import android.content.Context
import update.gautamsolar.creda.retrofit.network.ApiService
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import update.gautamsolar.creda.models.QuarterListModel
import java.util.HashMap
import javax.inject.Inject

class MainRepository
@Inject
constructor(private val apiService: ApiService, @ApplicationContext private val context: Context) {

    fun getQuarterlyList(
        params: HashMap<String, String>
    ): Flow<QuarterListModel> = flow {
        emit(apiService.getQuarterlyList( params))
    }.flowOn(Dispatchers.IO)

    fun postDetails(
        params: HashMap<String, String>
    ): Flow<QuarterListModel> = flow {
        emit(apiService.postDetails( params))
    }.flowOn(Dispatchers.IO)
}