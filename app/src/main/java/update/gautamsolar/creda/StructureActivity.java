package update.gautamsolar.creda;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.bumptech.glide.Glide;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import update.gautamsolar.creda.Constants.Constants;


public class StructureActivity extends AppCompatActivity {

    String eng_id, regnnumber, lat, lon,img_no,photo1,photo2,photo3,photo4;
    ImageView iv1, iv2, iv3, iv4;
    Button submit, video;
    Date date;
    ProgressDialog pb;
    double latti;
    double longi;
    RadioButton radioButton,radioYes,radioNo;
    RadioGroup radioGroup;
    Bitmap bitmap1 = null;
    static final int REQUEST_LOCATION = 15;
    public static final String KEY_IMAGE_STORAGE_PATH = "image_path";
    public static final int BITMAP_SAMPLE_SIZE = 4;
    private static final int IMAGE_CAPTURE11 = 111;
    public static final String IMAGE_EXTENSION = "jpg";
    public static final String VIDEO_EXTENSION = "mp4";
    public static final int MEDIA_TYPE_IMAGE = 189;
    SharedPreferences sharedPreferences;
    private static String imageStoragePath;
    TextView Father, Contactid, Villageide, Pumpide, Contactide, Blockid, numberid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.structure_activity);
        latti = 0.0;
        longi = 0.0;
        Bundle bundlef = getIntent().getExtras();
        submit = findViewById(R.id.submit);
        video = findViewById(R.id.video);
        iv1 = findViewById(R.id.iv1);
        iv2 = findViewById(R.id.iv2);
        iv3 = findViewById(R.id.iv3);
        iv4 = findViewById(R.id.iv4);
        radioGroup=(RadioGroup)findViewById(R.id.radioGroup);
        radioYes= findViewById(R.id.radioYes);
        radioNo=findViewById(R.id.radioNo);
        Father = (TextView) findViewById(R.id.fatheride);
        Contactid = (TextView) findViewById(R.id.contactide);
        Villageide = (TextView) findViewById(R.id.villageide);
        Pumpide = (TextView) findViewById(R.id.benificiaryide);
        Blockid = (TextView) findViewById(R.id.blockide);
        numberid = findViewById(R.id.numberid);

        pb = new ProgressDialog(this);
        pb.setMessage("Please Wait");
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        eng_id = sharedPreferences.getString("eng_id", "");
        regnnumber = bundlef.getString("regnnumber");
        Father.setText(bundlef.getString("fathername"));
        Contactid.setText(bundlef.getString("contact"));
        Villageide.setText(bundlef.getString("village"));
        Blockid.setText(bundlef.getString("block"));
        Pumpide.setText(bundlef.getString("benifname"));
        numberid.setText(regnnumber);

        if(!TextUtils.isEmpty(bundlef.getString("image1"))&& !bundlef.getString("image1").equals("null")){
            loadImage(bundlef.getString("image1"),iv1);
        }
        if(!TextUtils.isEmpty(bundlef.getString("image2"))&& !bundlef.getString("image2").equals("null")){
            loadImage(bundlef.getString("image2"),iv2);
        }
        if(!TextUtils.isEmpty(bundlef.getString("image3"))&& !bundlef.getString("image3").equals("null")){
            loadImage(bundlef.getString("image3"),iv3);
        }
        if(!TextUtils.isEmpty(bundlef.getString("image4"))&& !bundlef.getString("image4").equals("null")){
            loadImage(bundlef.getString("image4"),iv4);
        }
        if(!TextUtils.isEmpty(bundlef.getString("status"))&& !bundlef.getString("status").equals("null")){
           if(bundlef.getString("status").equals("  YES")){
               radioYes.setChecked(true);
               radioNo.setChecked(false);
           }
           else{
               radioNo.setChecked(true);
               radioYes.setChecked(false);
           }
        }
        if(!TextUtils.isEmpty(bundlef.getString("video"))&& !bundlef.getString("video").equals("null")){
          video.setBackgroundColor(getResources().getColor(R.color.green));
        }

        iv1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (CameraUtils.checkPermissions(getApplicationContext())) {
                    captureImage1();
                    img_no = "1";
                }else {
                    requestCameraPermission1(MEDIA_TYPE_IMAGE);
                }
            }
        });
        iv2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (CameraUtils.checkPermissions(getApplicationContext())) {
                    captureImage1();
                    img_no = "2";
                }else {
                    requestCameraPermission1(MEDIA_TYPE_IMAGE);
                }
            }
        });
        iv3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (CameraUtils.checkPermissions(getApplicationContext())) {
                    captureImage1();
                    img_no = "3";
                }else {
                    requestCameraPermission1(MEDIA_TYPE_IMAGE);
                }
            }
        });
        iv4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (CameraUtils.checkPermissions(getApplicationContext())) {
                    captureImage1();
                    img_no = "4";
                }else {
                    requestCameraPermission1(MEDIA_TYPE_IMAGE);
                }
            }
        });
        video.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(StructureActivity.this, VideoCaptureActivity.class);
                i.putExtra("regnnumber", regnnumber);
                i.putExtra("route", "structure");
                startActivity(i);
                finish();
            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int selectedId = radioGroup.getCheckedRadioButtonId();
                radioButton = (RadioButton) findViewById(selectedId);
                upload(radioButton.getText().toString());

            }
        });

    }

    private void captureImage1() {
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        File file = CameraUtils.getOutputMediaFile(MEDIA_TYPE_IMAGE);
        if (file != null) {
            imageStoragePath = file.getAbsolutePath();
        }

        Uri fileUri = CameraUtils.getOutputMediaFileUri(getApplicationContext(), file);

        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);

        // start the image capture Intent
        startActivityForResult(intent, IMAGE_CAPTURE11);
    }

    private void requestCameraPermission1(final int type) {
        Dexter.withActivity(this)
                .withPermissions(Manifest.permission.CAMERA,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.RECORD_AUDIO)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        if (report.areAllPermissionsGranted()) {

                            if (type == MEDIA_TYPE_IMAGE) {
                                // capture picture
                                captureImage1();

                            } /*else {
                                captureVideo1();
                            }*/

                        } else if (report.isAnyPermissionPermanentlyDenied()) {
                            showPermissionsAlert();
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                        token.continuePermissionRequest();
                    }


                }).check();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        // save file url in bundle as it will be null on screen orientation
        // changes
        outState.putString(KEY_IMAGE_STORAGE_PATH, imageStoragePath);
    }
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        // get the file url
        imageStoragePath = savedInstanceState.getString(KEY_IMAGE_STORAGE_PATH);
    }

    private void showPermissionsAlert() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Permissions required!")
                .setMessage("Camera needs few permissions to work properly. Grant them in settings.")
                .setPositiveButton("GOTO SETTINGS", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        CameraUtils.openSettings(StructureActivity.this);
                    }
                })
                .setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).show();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (requestCode == IMAGE_CAPTURE11) {
            if (resultCode == RESULT_OK) {
                // Refreshing the gallery
                CameraUtils.refreshGallery(getApplicationContext(), imageStoragePath);

                // successfully captured the image
                // display it in image view
                previewCapturedImage1();
            } else if (resultCode == RESULT_CANCELED) {
                // user cancelled Image capture
                Toast.makeText(getApplicationContext(),
                                "User cancelled image capture", Toast.LENGTH_SHORT)
                        .show();
            } else {
                // failed to capture image
                Toast.makeText(getApplicationContext(),
                                "Sorry! Failed to capture image", Toast.LENGTH_SHORT)
                        .show();
            }
        }else if (requestCode == 2) {
            try{
                Uri selectedImage = data.getData();
                String[] filePath = {MediaStore.Images.Media.DATA};
                Cursor c = getContentResolver().query(selectedImage, filePath, null, null, null);
                c.moveToFirst();
                int columnIndex = c.getColumnIndex(filePath[0]);
                String picturePath = c.getString(columnIndex);
                c.close();
                Bitmap thumbnail = (BitmapFactory.decodeFile(picturePath));
                imageStoragePath = picturePath;
                CameraUtils.refreshGallery(getApplicationContext(), imageStoragePath);

                // successfully captured the image
                // display it in image view
                previewCapturedImage1();
            }catch (Exception ae){
                ae.getStackTrace();
            }
        }
    }

    void getLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);


        } else {

            UploadAll uploadAll = new UploadAll();
            uploadAll.getInstance().init();
            double latti=UploadAll.latitude;
            double longi= UploadAll.longitude;

            lat = String.valueOf(latti);
            lon = String.valueOf(longi);
        }
    }

    private String getDateTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        date = new Date();
        return dateFormat.format(date);
    }

    public void upload(String structureStatus) {
        try {
            UploadAll.getInstance().init();
        } catch (Exception ae) {
        }
        latti = UploadAll.latitude;
        longi = UploadAll.longitude;
        pb.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.STRUCTURE, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                pb.dismiss();

                try {
                    JSONObject obj = new JSONObject(response);
                    //  Toast.makeText(getApplicationContext(),response,Toast.LENGTH_SHORT).show();
                    Log.d("response", response);
                    String message = obj.getString("error");
                    if (message.equals("false")) {
                        pb.dismiss();
                        Toast.makeText(StructureActivity.this, "Upload Successfully", Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        pb.dismiss();
                        Toast.makeText(StructureActivity.this, "Upload Unsuccessfully", Toast.LENGTH_SHORT).show();

                    }

                } catch (JSONException e) {
                    pb.dismiss();
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                pb.dismiss();
                Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_LONG).show();

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("structure_status", structureStatus);
                if(!TextUtils.isEmpty(photo4)) {
                    params.put("farmer_paani", photo4);
                }else{
                    params.put("farmer_paani", "null");
                }
                if(!TextUtils.isEmpty(photo3)) {
                    params.put("farmer_allpanel", photo3);
                }else{
                    params.put("farmer_allpanel", "null");
                }
                if(!TextUtils.isEmpty(photo2)) {
                    params.put("structure_rafter", photo2);
                }else{
                    params.put("structure_rafter", "null");
                }
                if(!TextUtils.isEmpty(photo1)) {
                    params.put("structure_purlin", photo1);
                }else{
                    params.put("structure_purlin", "null");
                }
                params.put("lat", String.valueOf(latti));
                params.put("lon", String.valueOf(longi));
                params.put("eng_id", eng_id);
                params.put("reg_no", regnnumber);
                params.put("datetime", getDateTime());
                return params;
            }
        };

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(10000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        stringRequest.setShouldCache(false);
        MySingleton.getInstance(getApplicationContext()).addTorequestque(stringRequest);
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    private void previewCapturedImage1() {
        try {
            // hide video preview

            bitmap1 = CameraUtils.optimizeBitmap(BITMAP_SAMPLE_SIZE, imageStoragePath);
//            Bitmap result=print_img(bitmap1);
            if(!TextUtils.isEmpty(img_no)&&img_no.equals("1")){
                photo1=imageTOString(bitmap1);
                iv1.setImageBitmap(bitmap1);
            }else if(!TextUtils.isEmpty(img_no)&&img_no.equals("2")){
                photo2=imageTOString(bitmap1);
                iv2.setImageBitmap(bitmap1);
            }else if(!TextUtils.isEmpty(img_no)&&img_no.equals("3")){
                photo3=imageTOString(bitmap1);
                iv3.setImageBitmap(bitmap1);
            }else if(!TextUtils.isEmpty(img_no)&&img_no.equals("4")){
                photo4=imageTOString(bitmap1);
                iv4.setImageBitmap(bitmap1);
            }


        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }
    String imageTOString(Bitmap bitmap) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
        byte[] imageBytes = outputStream.toByteArray();
        String encodeImage1 = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodeImage1;
    }

    public Bitmap print_img(Bitmap bitmap){
        String lat = "0.0",lng="0.0";
        try {
            UploadAll uploadAll = new UploadAll();
            uploadAll.getInstance().init();
            double latti = UploadAll.latitude;
            double longi = UploadAll.longitude;
            lat = String.valueOf(latti);
            lng = String.valueOf(longi);

        }catch (Exception ae){
            Log.d("lattilongitude",ae.toString());
        }

        File f = new File(imageStoragePath);

        int inWidth = bitmap.getWidth();
        int inHeight = bitmap.getHeight();


        ExifInterface exif = null;
        try {
            exif = new ExifInterface(f.getPath());
        } catch (IOException e) {
            Toast.makeText(this, "No", Toast.LENGTH_SHORT).show();
        }
        int orientation = exif.getAttributeInt(
                ExifInterface.TAG_ORIENTATION,
                ExifInterface.ORIENTATION_NORMAL);
        int angle = 0;

        if (orientation == ExifInterface.ORIENTATION_ROTATE_90) {
            angle = 90;
        } else if (orientation == ExifInterface.ORIENTATION_ROTATE_180) {
            angle = 180;
        } else if (orientation == ExifInterface.ORIENTATION_ROTATE_270) {
            angle = 270;
        }

        Matrix mat = new Matrix();
        mat.postRotate(angle);

        Bitmap scaledBitmap = Bitmap.createScaledBitmap(bitmap, inWidth, inHeight, false);

        Bitmap rotatedBitmap = Bitmap.createBitmap(scaledBitmap, 0, 0, scaledBitmap.getWidth(), scaledBitmap.getHeight(), mat, true);
        Bitmap.Config config = rotatedBitmap.getConfig();


        Bitmap result = Bitmap.createBitmap( rotatedBitmap.getWidth(), rotatedBitmap.getHeight(),config);

        Canvas canvas = new Canvas(result);
        canvas.drawBitmap(rotatedBitmap, 0, 0, null);
        Paint paint = new Paint();
        paint.setColor(Color.BLACK);
//    paint.setStyle(Paint.Style.STROKE);
        paint.setTextSize(12);
        paint.setAntiAlias(false);

        Paint innerPaint = new Paint();
        innerPaint.setColor(Color.parseColor("#61ECECEC"));
//    innerPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
        innerPaint.setAntiAlias(true);

        return result;
    }
protected  void loadImage(String url,ImageView iv){
    Glide.with(this)
            .load(url)
            .error(Glide.with(iv).load(R.mipmap.gearclick))
            .into(iv);
}
}
