package update.gautamsolar.creda.retrofit.util

import update.gautamsolar.creda.models.QuarterListModel


sealed class ApiState{

    class QuarterlyList(val data: QuarterListModel) : ApiState()
    class Failure(val msg:Throwable) : ApiState()
    object Loading : ApiState()
    object Empty : ApiState()

}
