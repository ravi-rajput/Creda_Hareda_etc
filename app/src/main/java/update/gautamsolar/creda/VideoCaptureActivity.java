package update.gautamsolar.creda;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import com.bumptech.glide.Glide;

import update.gautamsolar.creda.BuildConfig;
import update.gautamsolar.creda.R;

import java.io.File;
import java.util.List;
import java.util.Objects;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import update.gautamsolar.creda.Interfaces.APIService;
import update.gautamsolar.creda.models.responce_model;

import static android.content.ContentValues.TAG;

public class VideoCaptureActivity extends AppCompatActivity {
    private static final int CAMERA_CAPTURE_VIDEO_REQUEST_CODE = 200;
    String storage_video, eng_id, regNo;
    SharedPreferences sharedPreferences;
    ProgressDialog pb;
    VideoView videoView;
    ImageView play;
    public static final int MEDIA_TYPE_VIDEO = 2;
    Button retake, upload;
    public static final String VIDEO_EXTENSION = "mp4";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.video_capture_activity);
        pb = new ProgressDialog(this);
        pb.setMessage("Uploading");
        pb.setCancelable(false);
        videoView = findViewById(R.id.videoView);
        play = findViewById(R.id.play);
        retake = findViewById(R.id.retake);
        upload = findViewById(R.id.upload);
        storage_video = "";
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        eng_id = sharedPreferences.getString("eng_id", "");
        regNo = getIntent().getStringExtra("regnnumber");
        Log.d("keyValues",eng_id+" , "+regNo);
        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                videoView.setBackground(null);
                videoView.start();
            }
        });
        retake.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                capture();
            }
        });
        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(storage_video.equals("")){
                    Toast.makeText(VideoCaptureActivity.this, "Please capture video", Toast.LENGTH_SHORT).show();
                }else{
                sendPost();
            }}
        });
        capture();
    }

    @Override
    protected void onActivityResult(int requestCode,
                                    int resultCode, Intent data) {

        if (requestCode == CAMERA_CAPTURE_VIDEO_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                videoView.setVideoPath(storage_video);
                Bitmap bmThumbnail = ThumbnailUtils.createVideoThumbnail(storage_video,
                        MediaStore.Images.Thumbnails.MINI_KIND);
                Drawable d = new BitmapDrawable(getResources(), bmThumbnail);
                videoView.setBackground(d);
            } else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(this, "Video recording cancelled.",
                        Toast.LENGTH_LONG).show();
                storage_video = "";
            } else {
                Toast.makeText(this, "Failed to record video",
                        Toast.LENGTH_LONG).show();
                storage_video = "";
            }
        }
    }

    public void capture() {
        if (hasCamera()) {
            Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);

            File file = CameraUtils.getOutputMediaFile(MEDIA_TYPE_VIDEO);
            if (file != null) {
                storage_video = file.getAbsolutePath();
            }

            Uri fileUri = CameraUtils.getOutputMediaFileUri(getApplicationContext(), file);

            intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);

//            // set video quality
//            intent.putExtra(MediaStore.EXTRA_DURATION_LIMIT, 30); // Duration in Seconds
            intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 0); // Quality Low
            intent.putExtra(MediaStore.EXTRA_SIZE_LIMIT, 15*1024*1024); // 15MB
            //        intent.putExtra(MediaStore.Video.Thumbnails.HEIGHT, 240);
//        intent.putExtra(MediaStore.Video.Thumbnails.WIDTH, 120);


            // start the video capture Intent
            startActivityForResult(intent, CAMERA_CAPTURE_VIDEO_REQUEST_CODE);
        } else {
            Toast.makeText(this, "No Camera Detected", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean hasCamera() {
        if (getPackageManager().hasSystemFeature(
                PackageManager.FEATURE_CAMERA_FRONT)) {
            return true;
        } else {
            return false;
        }
    }

    public void sendPost() {
        pb.show();
        File file = new File(storage_video);
        RequestBody fbody = RequestBody.create(MediaType.parse("video/*"), file);
        Log.d("Filename", storage_video);
        MultipartBody.Part vFile = MultipartBody.Part.createFormData("myFile", file.getName(), fbody);

        RequestBody body1 = RequestBody.create(MediaType.parse("text/plain"), regNo);
        RequestBody body2 = RequestBody.create(MediaType.parse("text/plain"), eng_id);
        RequestBody body3 = null;
        RequestBody body4 = null;
        if (sharedPreferences.getString("lead_phase", "").equalsIgnoreCase("HAREDA_PHASE3")||sharedPreferences.getString("lead_phase","").equalsIgnoreCase("HAREDA_PHASE4")||sharedPreferences.getString("lead_phase","").equalsIgnoreCase("GALO_PHASE1")) {
            body3 = RequestBody.create(MediaType.parse("text/plain"), sharedPreferences.getString("lead_phase", ""));
        }else{
            body3 = RequestBody.create(MediaType.parse("text/plain"), "");
        }

        if (getIntent().getStringExtra("route")!=null && getIntent().getStringExtra("route").equalsIgnoreCase("material")) {
            body4 = RequestBody.create(MediaType.parse("text/plain"),  "material");
        }else if (getIntent().getStringExtra("route")!=null && getIntent().getStringExtra("route").equalsIgnoreCase("structure")) {
            body4 = RequestBody.create(MediaType.parse("text/plain"),  "structure");
        }else{
            body4 = RequestBody.create(MediaType.parse("text/plain"), "other");
        }

            APIService mAPIService = RetrofitClient.getClient().create(APIService.class);
        mAPIService.savePost(vFile, body1, body2, body3,body4).enqueue(new Callback<List<responce_model>>() {
            @Override
            public void onResponse(Call<List<responce_model>> call, retrofit2.Response<List<responce_model>> response) {

                if (response.isSuccessful()) {
                    List<responce_model> responce_ = response.body();
                    Log.d("responsevideo",responce_.toString());
                    Log.d(TAG, "post submitted to API." + responce_.get(0).getStatus());
                    Toast.makeText(VideoCaptureActivity.this, responce_.get(0).getStatus(), Toast.LENGTH_SHORT).show();
                    pb.dismiss();
                    finish();
                }
            }

            @Override
            public void onFailure(Call<List<responce_model>> call, Throwable t) {
                Log.d(TAG, "post submitted to API." + t.toString());
                Toast.makeText(VideoCaptureActivity.this, t.toString(), Toast.LENGTH_SHORT).show();
                pb.dismiss();
            }
        });
    }

}
