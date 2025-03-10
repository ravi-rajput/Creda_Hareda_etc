package update.gautamsolar.creda.Interfaces;


import java.util.List;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import update.gautamsolar.creda.models.ChalanModel;
import update.gautamsolar.creda.models.responce_model;

public interface APIService {

    @POST("upload_video_v1.php")
    @Multipart
    Call<List<responce_model>> savePost(@Part MultipartBody.Part photo, @Part("reg_number") RequestBody regNumber,@Part("eng_id") RequestBody eng_id, @Part("phase") RequestBody phase, @Part("route") RequestBody route);

    @POST("getsaral_chalanlist_v1.php")
    @Multipart
    Call<List<ChalanModel>> getChalans( @Part("eng_id") RequestBody eng_id);


}