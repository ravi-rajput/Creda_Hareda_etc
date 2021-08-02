package update.gautamsolar.creda.Creda_Rajasthan;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
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
import android.graphics.drawable.ColorDrawable;
import android.location.LocationManager;
import android.media.ExifInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.provider.Settings;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import update.gautamsolar.creda.R;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
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

import update.gautamsolar.creda.CameraUtils;
import update.gautamsolar.creda.Constants.Constants;
import update.gautamsolar.creda.Database.Installation_Table_Rajsthan;
import update.gautamsolar.creda.MySingleton;
import update.gautamsolar.creda.SharedPrefManager;
import update.gautamsolar.creda.UploadAll;

public class Installation_Rajasthan extends AppCompatActivity {

    public static final String GALLERY_DIRECTORY_NAME = "GSPLCREDA";
    public static final String IMAGE_EXTENSION = "jpg";
    public static final String VIDEO_EXTENSION = "mp4";
    public static final int MEDIA_TYPE_IMAGE = 189;

    static final int CAMERA_CAPTURE_VIDEO_REQUEST_CODE = 760;

    public static final int MEDIA_TYPE_VIDEO = 461;
    //public static final int MEDIA_TYPE_VIDEO = 190;
    public static final String KEY_IMAGE_STORAGE_PATH = "image_path";
    public static final int BITMAP_SAMPLE_SIZE = 8;
    private static String imageStoragePath;
    Dialog dialog, Localdialog;
    LocationManager locationManager;
    boolean isGPSEnabled = false;
    int Status_video;
    //private static final int CAMERA_CAPTURE_VIDEO_REQUEST_CODE = 200;
    VideoView video;
    TextView status_installation, status_video;
    static final int REQUEST_LOCATION = 15;
    private Button btninst_upload, uploadvideo;
    private static final int PERMISSION_REQUEST_CODE11 = 415;
    private static final int IMAGE_CAPTURE11 = 411;
    ImageView imageinst_one, imageinst_two, imageinst_three, imageinst_four, imageinst_five, imageinst_six, imageinst_seven, play;
    Bitmap bitmap1 = null, bitmap2 = null, bitmap3 = null, bitmap4 = null, bitmap5 = null, bitmap6 = null, bitmap7 = null;
    String KEYPHOTO1, KEYPHOTO2, KEYPHOTO3, KEYPHOTO4, KEYPHOTO5, KEYPHOTO6, KEYPHOTO7, ICOMPLETE_STATUS_STRING;

    TextView Fatheri, Contactidi, Villageidei, Pumpidei, Contactidei, Blockidi, districtidi, numberid;
    int Status_installation;
    String url1 = null, url2 = null, url3 = null, url4 = null, url5 = null, url6 = null, url7 = null;

    private static String storage_video;
    public static final String myurlnew11 = "http://gautamsolar.co.in/rajasthan/RjAndroidApi/instimage_uploadnewjatin.php";
    private String FETCHIMAGE11 = "http://gautamsolar.co.in/credanew/credaAndroidApi/fetch_inst_pic_new1.php";
    ProgressDialog progressDialog;
    String Engineer_contact, reg_no, lat, lon, radioinscomplete_string, regnnumber, benifname, fathername, contact, block, village, installation_status;
    RadioGroup radioinscomplete;
    RadioButton inscomplete, insuncomplete;


    //1 means data is synced and 0 means data is not synced
    public static final int NAME_SYNCED_WITH_SERVERI = 1;
    public static final int NAME_NOT_SYNCED_WITH_SERVERI = 0;
    //a broadcast to know weather the data is synced or not
    public static final String DATA_SAVED_BROADCASTI = "net.simplifiedcoding.datasavedi";

    //Broadcast receiver to know the sync status
    private BroadcastReceiver broadcastReceiveri;

    //adapterobject for list view
    String installation_api, eng_id, photo11, photo12, photo13, photo14, photo15, photo16, photo17, Engineer_Contact;
    ProgressDialog pb;
    Constants constants;
    SharedPreferences sharedPreferences;
    String img_no;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_installation__rajasthan);
        getSupportActionBar().setTitle("Pump Installation RAJASTHAN");
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        Engineer_Contact = sharedPreferences.getString("engcontact", "");
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        eng_id = sharedPreferences.getString("eng_id", "");
        constants = new Constants();
        installation_api = constants.INSTALLATION_API;

        if (!isGPSEnabled) {
            // no network provider is enabled
            Intent intent1 = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            startActivity(intent1);
        }

        if (!CameraUtils.isDeviceSupportCamera(getApplicationContext())) {
            Toast.makeText(getApplicationContext(),
                    "Sorry! Your device doesn't support camera",
                    Toast.LENGTH_LONG).show();
            // will close the app if the device doesn't have camera
            finish();
        }
        pb = new ProgressDialog(this, R.style.MyGravity);
        pb.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        pb.setIndeterminateDrawable(getResources().getDrawable(R.drawable.custom_progress_dialog));
        pb.setCancelable(false);

        dialog = new Dialog(Installation_Rajasthan.this); // Context, this, etc.
        Localdialog = new Dialog(Installation_Rajasthan.this); // Context, this, etc.


        dialog.setContentView(R.layout.uploaddialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(false);

        Localdialog.setContentView(R.layout.localdialog);
        Localdialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        Localdialog.setCancelable(false);
        btninst_upload = findViewById(R.id.btninst_uploadr);

        imageinst_one = findViewById(R.id.imageinst_oner);
        imageinst_two = findViewById(R.id.imageinst_twor);
        imageinst_three = findViewById(R.id.imageinst_threer);
        imageinst_four = findViewById(R.id.imageinst_fourr);
        Fatheri = (TextView) findViewById(R.id.fatheride);
        Contactidi = (TextView) findViewById(R.id.contactide);
        Villageidei = (TextView) findViewById(R.id.villageide);
        Pumpidei = (TextView) findViewById(R.id.benificiaryide);
        Contactidei = (TextView) findViewById(R.id.contactide);
        Blockidi = (TextView) findViewById(R.id.blockide);
        numberid = findViewById(R.id.numberid);
        photo11 = "noimage";
        photo12 = "noimage";
        photo13 = "noimage";
        photo14 = "noimage";
        lat = "0";
        lon = "0";

        Engineer_contact = SharedPrefManager.getInstance(this).getUserContact();
        Bundle bundleUploadB = getIntent().getExtras();
        fathername = bundleUploadB.getString("fathername");
        regnnumber = bundleUploadB.getString("regnnumber");
        benifname = bundleUploadB.getString("benifname");
        village = bundleUploadB.getString("village");
        block = bundleUploadB.getString("block");
        contact = bundleUploadB.getString("contact");
        KEYPHOTO1 = bundleUploadB.getString("instimg1");
        KEYPHOTO2 = bundleUploadB.getString("instimg2");
        KEYPHOTO3 = bundleUploadB.getString("instimg3");
        KEYPHOTO4 = bundleUploadB.getString("instimg4");

        if (!KEYPHOTO1.equals("null")) {
            imageinst_one.setBackgroundResource(R.mipmap.tickclick);
        }
        if (!KEYPHOTO2.equals("null")) {
            imageinst_two.setBackgroundResource(R.mipmap.tickclick);

        }
        if (!KEYPHOTO3.equals("null")) {
            imageinst_three.setBackgroundResource(R.mipmap.tickclick);
        }
        if (!KEYPHOTO4.equals("null")) {
            imageinst_four.setBackgroundResource(R.mipmap.tickclick);
        }

        Fatheri.setText(fathername);
        Contactidi.setText(contact);
        Villageidei.setText(village);
        Blockidi.setText(block);
        Pumpidei.setText(benifname);
        numberid.setText(regnnumber);

        // fetchImage1();


        imageinst_one.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (CameraUtils.checkPermissions(getApplicationContext())) {
                    captureImage1();
                } else {
                    requestCameraPermission1(MEDIA_TYPE_IMAGE);
                }
                img_no = "1";
            }
        });
        imageinst_two.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (CameraUtils.checkPermissions(getApplicationContext())) {
                    captureImage1();
                } else {
                    requestCameraPermission1(MEDIA_TYPE_IMAGE);
                }
                img_no = "2";

            }
        });
        imageinst_three.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (CameraUtils.checkPermissions(getApplicationContext())) {
                    captureImage1();
                } else {
                    requestCameraPermission1(MEDIA_TYPE_IMAGE);
                }
                img_no = "3";
            }
        });
        imageinst_four.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (CameraUtils.checkPermissions(getApplicationContext())) {
                    captureImage1();
                } else {
                    requestCameraPermission1(MEDIA_TYPE_IMAGE);
                }
                img_no = "4";
            }
        });


        btninst_upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
                isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);

//                if (isGPSEnabled) {
                getLocation();
                ConnectivityManager cm = (ConnectivityManager) Installation_Rajasthan.this.getSystemService(Context.CONNECTIVITY_SERVICE);
                final NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
                if (activeNetwork != null) {

                    if (lat.equals("0") && lon.equals("0")) {
                        getLocation();
                    } else {
                        uploaduserimage11();
                    }
                } else {
                    if (lat.equals("0") && lon.equals("0")) {
                        getLocation();
                    } else {
                        save_local();
                    }
                }
            }
        });


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        switch (requestCode) {
            case PERMISSION_REQUEST_CODE11:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(Installation_Rajasthan.this, "Permission Granted Successfully! ", Toast.LENGTH_LONG).show();

                } else {
                    Toast.makeText(Installation_Rajasthan.this, "Permission Denied :( ", Toast.LENGTH_LONG).show();
                }
                break;

            case REQUEST_LOCATION:
                getLocation();
                break;

        }
    }

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
            try {
                Uri selectedImage = data.getData();
                String[] filePath = {MediaStore.Images.Media.DATA};
                Cursor c = getContentResolver().query(selectedImage, filePath, null, null, null);
                c.moveToFirst();
                int columnIndex = c.getColumnIndex(filePath[0]);
                String picturePath = c.getString(columnIndex);
                c.close();
                 imageStoragePath = picturePath;
                CameraUtils.refreshGallery(getApplicationContext(), imageStoragePath);

                // successfully captured the image
                // display it in image view
                previewCapturedImage1();
            }catch (Exception ae){
                ae.getStackTrace();
            }
            }

        if (requestCode == CAMERA_CAPTURE_VIDEO_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                Uri u = data.getData();

                video.setVideoURI(u);
                Toast.makeText(this, storage_video
                        , Toast.LENGTH_SHORT).show();

            } else if (resultCode == RESULT_CANCELED) {
                // user cancelled recording
                Toast.makeText(getApplicationContext(),
                        "User cancelled video recording", Toast.LENGTH_SHORT)
                        .show();
            } else {
                // failed to record video
                Toast.makeText(getApplicationContext(),
                        "Sorry! Failed to record video", Toast.LENGTH_SHORT)
                        .show();
            }
        }
    }

    void getLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);


        } else {

            UploadAll.getInstance().init();
            double latti = UploadAll.latitude;
            double longi = UploadAll.longitude;

            lat = String.valueOf(latti);
            lon = String.valueOf(longi);
        }
    }

//
//    public void fetchImage1() {
//        pb.show();
//
//        StringRequest stringRequest1 = new StringRequest( Request.Method.POST, FETCHIMAGE11, new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//                pb.dismiss();
//
//                try {
//
//                    JSONObject res = new JSONObject( response );
//                    JSONArray thread = res.getJSONArray( "image" );
//                    for (int i = 0; i < thread.length(); i++) {
//                        JSONObject obj = thread.getJSONObject( i );
//                        url1 = obj.getString( "photo1" );
//                        url2 = obj.getString( "photo2" );
//                        url3 = obj.getString( "photo3" );
//                        url4 = obj.getString( "photo4" );
//                        url5 = obj.getString( "photo5" );
//                        url6 = obj.getString( "photo6" );
//                        url7 = obj.getString( "photo7" );
//                        radioinscomplete_string = obj.getString( "installation_status" );
//
//
//
//
//                    }
//
//                    Picasso.with( UploadBenificieryPic.this ).load( url1 ).placeholder( R.mipmap.gearclick  ).
//                            resize( 200, 200 ).into(  imageinst_one);
//                    Picasso.with( UploadBenificieryPic.this ).load( url2 ).placeholder( R.mipmap.gearclick ).
//                            resize( 200, 200 ).into( imageinst_two );
//                    Picasso.with( UploadBenificieryPic.this ).load( url3 ).placeholder( R.mipmap.gearclick ).
//                            resize( 200, 200 ).into( imageinst_three );
//                    Picasso.with( UploadBenificieryPic.this ).load( url4 ).placeholder( R.mipmap.gearclick ).
//                            resize( 200, 200 ).into( imageinst_four );
//                    Picasso.with( UploadBenificieryPic.this ).load( url5 ).placeholder(R.mipmap.gearclick ).
//                            resize( 200, 200 ).into( imageinst_five );
//                    Picasso.with( UploadBenificieryPic.this ).load( url6 ).placeholder(R.mipmap.gearclick ).
//                            resize( 200, 200 ).into( imageinst_six );
//                    Picasso.with( UploadBenificieryPic.this ).load( url7 ).placeholder( R.mipmap.gearclick).
//                            resize( 200, 200 ).into( imageinst_seven );
//
//                  /*  imageLoader.get(url, ImageLoader.getImageListener(imageView
//                            ,0,android.R.drawable
//                                    .ic_dialog_alert));
//                    imageView.setImageUrl(url,imageLoader);
//*/
//
//
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//
//            }
//        } ) {
//            @Override
//            protected Map<String, String> getParams() throws AuthFailureError {
//                Map<String, String> params1 = new HashMap<>();
//                params1.put( "reg_no", regnnumber );
//                return params1;
//            }
//        };
//
//        MySingleton.getInstance( getApplicationContext() ).addTorequestque( stringRequest1 );
//
//    }

   /* public Bitmap getBitmap1() {
        return bitmap1;
    }
   */

    public void uploaduserimage11() {

        pb.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, installation_api, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                pb.dismiss();

                try {
                    JSONObject obj = new JSONObject(response);
                    String message = obj.getString("error");
                    if (message.equals("false")) {

                        dialog.findViewById(R.id.clickmedismiss).setOnClickListener(new View.OnClickListener() {
                            @SuppressLint("ResourceAsColor")
                            public void onClick(View v) {
                                Intent i = new Intent(Installation_Rajasthan.this, UploadAll.class);
                                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(i);
                                finish();
                                dialog.dismiss();
                            }
                        });
                        dialog.show();


                    } else {
                        save_local();

                    }
                } catch (JSONException e) {
                    save_local();
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                pb.dismiss();
                if (error instanceof NetworkError) {

                    save_local();
                } else if (error instanceof ServerError) {
                    save_local();
                } else if (error instanceof AuthFailureError) {
                    save_local();
                } else if (error instanceof ParseError) {
                    save_local();
                } else if (error instanceof NoConnectionError) {
                    save_local();
                } else if (error instanceof TimeoutError) {
                    pb.dismiss();
                    Toast.makeText(getApplicationContext(), "Oops. Timeout error!", Toast.LENGTH_LONG).show();
                }


            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();

                params.put("photo1", photo11);
                params.put("photo2", photo12);
                params.put("photo3", photo13);
                params.put("photo4", photo14);
                params.put("lat", lat);
                params.put("lon", lon);
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


    private String getDateTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        Date date = new Date();
        return dateFormat.format(date);
    }

    private Bitmap convertToBitmap(String b) {

        try {
            byte[] encodeByte = Base64.decode(b, Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            return bitmap;
        } catch (Exception e) {
            e.getMessage();
            return null;
        }

    }

    String imageTOString(Bitmap bitmap) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
        byte[] imageBytes = outputStream.toByteArray();
        String encodeImage1 = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodeImage1;

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
                    public void onPermissionRationaleShouldBeShown(List<com.karumi.dexter.listener.PermissionRequest> permissions, PermissionToken token) {
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

    /**
     * Restoring image path from saved instance state
     */
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
                        CameraUtils.openSettings(Installation_Rajasthan.this);
                    }
                })
                .setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).show();
    }


    private void captureImage1() {

        AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
        builder1.setMessage("Select Image Source");
        builder1.setCancelable(true);

        builder1.setPositiveButton(
                "Camera",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
//                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
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
                });

        builder1.setNegativeButton(
                "Gallary",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        startActivityForResult(intent, 2);

                    }
                });

        AlertDialog alert11 = builder1.create();
        alert11.show();


    }


    private void previewCapturedImage1() {
        try {
            // hide video preview


            imageinst_one.setVisibility(View.VISIBLE);

            bitmap1 = CameraUtils.optimizeBitmap(BITMAP_SAMPLE_SIZE, imageStoragePath);
            Bitmap result = print_img(bitmap1);
            if (img_no.equals("1")) {
                photo11 = imageTOString(result);
                imageinst_one.setImageBitmap(result);
            } else if (img_no.equals("2")) {
                photo12 = imageTOString(result);
                imageinst_two.setImageBitmap(result);
            } else if (img_no.equals("3")) {
                photo13 = imageTOString(result);
                imageinst_three.setImageBitmap(result);
            } else if (img_no.equals("4")) {
                photo14 = imageTOString(result);
                imageinst_four.setImageBitmap(result);
            }

        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }


    private void captureVideo() {
        Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);

        File file = CameraUtils.getOutputMediaFile(MEDIA_TYPE_VIDEO);
        if (file != null) {
            storage_video = file.getAbsolutePath();
        }

        Uri fileUri = CameraUtils.getOutputMediaFileUri(getApplicationContext(), file);

        // set video quality
        intent.putExtra(MediaStore.EXTRA_DURATION_LIMIT, 30); // Duration in Seconds
        intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 0); // Quality Low
        intent.putExtra(MediaStore.EXTRA_SIZE_LIMIT, 5491520L); // 5MB

        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri); // set the image file

        // start the video capture Intent
        startActivityForResult(intent, CAMERA_CAPTURE_VIDEO_REQUEST_CODE);
    }


    public void save_local() {

        try {
            pb.dismiss();
        } catch (Exception e) {
            e.printStackTrace();
        }

        Installation_Table_Rajsthan installation_table_rajsthan = new Installation_Table_Rajsthan();
        installation_table_rajsthan.foto1 = String.valueOf(photo11);
        installation_table_rajsthan.foto2 = String.valueOf(photo12);
        installation_table_rajsthan.foto3 = String.valueOf(photo13);
        installation_table_rajsthan.foto4 = String.valueOf(photo14);
        installation_table_rajsthan.Lat = lat;
        installation_table_rajsthan.Lon = lon;
        installation_table_rajsthan.eng_id = eng_id;
        installation_table_rajsthan.Regn = regnnumber;
        installation_table_rajsthan.Dati = getDateTime();
        try {
            installation_table_rajsthan.save();
        } catch (Exception ae) {

        }
        Localdialog.findViewById(R.id.clickmedismissf).setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            public void onClick(View v) {
                Toast.makeText(Installation_Rajasthan.this, "Saved", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(Installation_Rajasthan.this, UploadAll.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i);
                finish();
                Localdialog.dismiss();
            }
        });

        Localdialog.show();

    }

    public Bitmap print_img(Bitmap bitmap) {
        String lat = "0.0",lng="0.0";
        try {
    UploadAll.getInstance().init();
    double latti = UploadAll.latitude;
    double longi = UploadAll.longitude;
   lat = String.valueOf(latti);
   lng = String.valueOf(longi);

}catch (Exception ae){

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


        Bitmap result = Bitmap.createBitmap(rotatedBitmap.getWidth(), rotatedBitmap.getHeight(), config);

        Canvas canvas = new Canvas(result);
        canvas.drawBitmap(rotatedBitmap, 0, 0, null);
        Paint paint = new Paint();
        paint.setColor(Color.BLACK);

//    paint.setStyle(Paint.Style.STROKE);
        paint.setTextSize(15);
        paint.setAntiAlias(true);

        Paint innerPaint = new Paint();
        innerPaint.setColor(Color.parseColor("#61ECECEC"));
//    innerPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
        innerPaint.setAntiAlias(true);
        canvas.drawRect(180F, 50F, 0, 0, innerPaint);
        canvas.drawText("Lat - " + lat, 5, 15, paint);
        canvas.drawText("Long - " + lng, 5, 30, paint);
        canvas.drawText("Date - " + getDateTime(), 5, 45, paint);
        return result;
    }

}
