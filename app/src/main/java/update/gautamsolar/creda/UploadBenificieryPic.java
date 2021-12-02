package update.gautamsolar.creda;

import android.Manifest;
import android.annotation.SuppressLint;
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
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.provider.Settings;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

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
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

import update.gautamsolar.creda.Constants.Constants;
import update.gautamsolar.creda.Database.InstalltionTable;

public class UploadBenificieryPic extends AppCompatActivity {

    public static final String GALLERY_DIRECTORY_NAME = "GSPLCREDA";
    public static final String IMAGE_EXTENSION = "jpg";
    public static final String VIDEO_EXTENSION = "mp4";
    public static final int MEDIA_TYPE_IMAGE = 189;
    static final int CAMERA_CAPTURE_VIDEO_REQUEST_CODE = 309;
String img_no;
    public static final int MEDIA_TYPE_VIDEO = 310;
    //public static final int MEDIA_TYPE_VIDEO = 190;
    public static final String KEY_IMAGE_STORAGE_PATH = "image_path";
    public static final int BITMAP_SAMPLE_SIZE = 4;
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
    private static final int PERMISSION_REQUEST_CODE11 = 115;
    private static final int IMAGE_CAPTURE11 = 111, IMAGE_CAPTURE12 = 112,
            IMAGE_CAPTURE13 = 113, IMAGE_CAPTURE14 = 114, IMAGE_CAPTURE15 = 303, IMAGE_CAPTURE16 = 304, IMAGE_CAPTURE17 = 305,IMAGE_CAPTURE18 = 306;
    ImageView imageinst_one, imageinst_two, imageinst_three, imageinst_four, imageinst_five, imageinst_six, imageinst_seven,imageinst_eight, play;
    Bitmap bitmap1 = null, bitmap2 = null, bitmap3 = null, bitmap4 = null, bitmap5 = null, bitmap6 = null, bitmap7 = null,bitmap8 = null;
    String KEYPHOTO1, KEYPHOTO2, KEYPHOTO3, KEYPHOTO4, KEYPHOTO5, KEYPHOTO6, KEYPHOTO7,KEYPHOTO8, ICOMPLETE_STATUS_STRING;

    TextView Fatheri, Contactidi, Villageidei, Pumpidei, Contactidei, Blockidi, districtidi,numberidi;
    int Status_installation;
    String url1 = null, url2 = null, url3 = null, url4 = null, url5 = null, url6 = null, url7 = null;
    Constants constants;
    private static String storage_video;
    ProgressDialog progressDialog;
    String Engineer_contact, reg_no, lat, lon, radioinscomplete_string, regnnumber, benifname, fathername, contact, block, village, installation_status;
    RadioGroup radioinscomplete;
    RadioButton inscomplete, insuncomplete;

    String strDate="2021-09-10";
    String pic_date,project;
    //1 means data is synced and 0 means data is not synced
    public static final int NAME_SYNCED_WITH_SERVERI = 1;
    public static final int NAME_NOT_SYNCED_WITH_SERVERI = 0;
    //a broadcast to know weather the data is synced or not
    public static final String DATA_SAVED_BROADCASTI = "net.simplifiedcoding.datasavedi";

    //Broadcast receiver to know the sync status
    private BroadcastReceiver broadcastReceiveri;

    //adapterobject for list view
    String eng_id, installation_api, photo11, photo12, photo13, photo14, photo15, photo16, photo17,photo18, Engineer_Contact;
    ProgressDialog pb;
    SharedPreferences sharedPreferences;


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_benificiery_pic);
        LocalDate startDate = LocalDate.of(2021, 9, 1); //start date
        long start = startDate.toEpochDay();
        System.out.println(start);

        LocalDate endDate = LocalDate.of(2021, 9, 30); //start date

        long end = endDate.toEpochDay();
        System.out.println(start);

        long randomEpochDay = ThreadLocalRandom.current().longs(start, end).findAny().getAsLong();
        Log.d("randomDate",LocalDate.ofEpochDay(randomEpochDay).toString());
        strDate = LocalDate.ofEpochDay(randomEpochDay).toString();


        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        Engineer_Contact = sharedPreferences.getString("engcontact", "");
        constants = new Constants();
        installation_api = constants.INSTALLATION_API;
        eng_id = sharedPreferences.getString("eng_id", "");
        project = sharedPreferences.getString("project", "");


        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);

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

        dialog = new Dialog(UploadBenificieryPic.this); // Context, this, etc.
        Localdialog = new Dialog(UploadBenificieryPic.this); // Context, this, etc.


        dialog.setContentView(R.layout.uploaddialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(false);

        Localdialog.setContentView(R.layout.localdialog);
        Localdialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        Localdialog.setCancelable(false);


        //initializing views and objects
        //db = new DatabaseHelper( this );


        btninst_upload = findViewById(R.id.btninst_upload);

        imageinst_one = findViewById(R.id.imageinst_one);
        imageinst_two = findViewById(R.id.imageinst_two);
        imageinst_three = findViewById(R.id.imageinst_three);
        imageinst_four = findViewById(R.id.imageinst_four);
        imageinst_five = findViewById(R.id.imageinst_five);
        imageinst_six = findViewById(R.id.imageinst_six);
        imageinst_seven = findViewById(R.id.imageinst_seven);
        imageinst_eight = findViewById(R.id.imageinst_eight);
        Fatheri = (TextView) findViewById(R.id.fatheride);
        Contactidi = (TextView) findViewById(R.id.contactide);
        Villageidei = (TextView) findViewById(R.id.villageide);
        Pumpidei = (TextView) findViewById(R.id.benificiaryide);
        Contactidei = (TextView) findViewById(R.id.contactide);
        Blockidi = (TextView) findViewById(R.id.blockide);
        numberidi=findViewById(R.id.numberid);
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
        KEYPHOTO5 = bundleUploadB.getString("instimg5");
        KEYPHOTO6 = bundleUploadB.getString("instimg6");
        KEYPHOTO7 = bundleUploadB.getString("instimg7");
        KEYPHOTO8 = bundleUploadB.getString("fondimg5");
        if(project.equals("PEDA")){
            pic_date = getDateTime();
        }else {
            pic_date = bundleUploadB.getString("pic_date");
        }if (!KEYPHOTO1.equals("null")) {
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
        if (!KEYPHOTO5.equals("null")) {
            imageinst_five.setBackgroundResource(R.mipmap.tickclick);
        }
        if (!KEYPHOTO6.equals("null")) {
            imageinst_six.setBackgroundResource(R.mipmap.tickclick);
        }
        if (!KEYPHOTO7.equals("null")) {
            imageinst_seven.setBackgroundResource(R.mipmap.tickclick);
        }
        if (!KEYPHOTO8.equals("null")) {
            imageinst_eight.setBackgroundResource(R.mipmap.tickclick);
        }


        Fatheri.setText(fathername);
        Contactidi.setText(contact);
        Villageidei.setText(village);
        Blockidi.setText(block);
        Pumpidei.setText(benifname);
        numberidi.setText(regnnumber);

        // fetchImage1();

        photo11 = "noimage";
        photo12 = "noimage";
        photo13 = "noimage";
        photo14 = "noimage";
        photo15 = "noimage";
        photo16 = "noimage";
        photo17 = "noimage";
        photo18 = "noimage";
lat="0";
lon="0";

        imageinst_one.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (CameraUtils.checkPermissions(getApplicationContext())) {
                    captureImage1();
                } else {
                    requestCameraPermission1(MEDIA_TYPE_IMAGE);
                }
img_no="1";
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
                img_no="2";
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
                img_no="3";
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
                img_no="4";
            }
        });

        imageinst_five.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (CameraUtils.checkPermissions(getApplicationContext())) {
                    captureImage1();
                } else {
                    requestCameraPermission1(MEDIA_TYPE_IMAGE);
                }
                img_no="5";
            }
        });

        imageinst_six.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (CameraUtils.checkPermissions(getApplicationContext())) {
                    captureImage1();
                } else {
                    requestCameraPermission1(MEDIA_TYPE_IMAGE);
                }
                img_no="6";
            }
        });

        imageinst_seven.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (CameraUtils.checkPermissions(getApplicationContext())) {
                    captureImage1();
                } else {
                    requestCameraPermission1(MEDIA_TYPE_IMAGE);
                }
                img_no="7";
            }
        });
        imageinst_eight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (CameraUtils.checkPermissions(getApplicationContext())) {
                    captureImage1();
                } else {
                    requestCameraPermission1(MEDIA_TYPE_IMAGE);
                }
                img_no="8";

            }
        });

        btninst_upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
                isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);

//                if (isGPSEnabled) {

                getLocation();
                ConnectivityManager cm = (ConnectivityManager) UploadBenificieryPic.this.getSystemService(Context.CONNECTIVITY_SERVICE);
                final NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
                if (activeNetwork != null) {
 if(lat.equals("0")&&lon.equals("0")){
                        getLocation();
                    }
                  else{  uploaduserimage11();}

                } else {
                  if(lat.equals("0")&&lon.equals("0")){
                        getLocation();
                    }  else{ save_local();}
                }

            }
        });




    }

    private void loadNamesB() {


//        Cursor cursor = db.getNames1( regnnumber );
//        if (cursor.moveToFirst()) {
//            do {
//
//
//
//                //  video_string_local = cursor.getString( cursor.getColumnIndex( DatabaseHelper.VIDEOPATH ) );
//
//
//            } while (cursor.moveToNext());
//        }
//

    }


//
//    private void uploadVideo() {
//        class UploadVideo extends AsyncTask<Void, Void, String> {
//            ProgressDialog progressDialog1;
//            @Override
//            protected void onPreExecute() {
//                super.onPreExecute();
//                progressDialog1 = new ProgressDialog(UploadBenificieryPic.this, R.style.AppTheme_Dark_Dialog);
//                progressDialog1.setIndeterminate(true);
//                progressDialog1.setMessage( "Uploading..." );
//                progressDialog1.show();
//
//            }
//
//
//            @Override
//            protected void onPostExecute (String s){
//                super.onPostExecute(s);
//                progressDialog1.dismiss();
//                if(!s.equals( "" ))
//                {
//                    Toast.makeText(getApplicationContext(),
//                            "Uploading Video Successfully", Toast.LENGTH_SHORT)
//                            .show();
//                }
//                Toast.makeText(getApplicationContext(),
//                        s, Toast.LENGTH_SHORT)
//                        .show();
//             // process_status.setText( Html.fromHtml("<b>Uploaded at <a href='" + s + "'>" + s + "</a></b>"));
//              //  process_status.setMovementMethod( LinkMovementMethod.getInstance());
//
//            }
//
//            @Override
//            protected String doInBackground (Void...params){
//
//                /*Upload u = new Upload();
//                String msg = u.uploadVideo(storage_video,reg_no);
//                return msg;*/
//
//
//                try {
//
//                    String charset = "UTF-8";
//                    File uploadFile1 = new File(storage_video);
//                    String requestURL = "http://gautamsolar.co.in/streetlightapi/uploadvideo_to_server1.php";
//
//                    MultipartUtility multipart = new MultipartUtility(requestURL, charset);
//
////            multipart.addHeaderField("User-Agent", "CodeJava");
////            multipart.addHeaderField("Test-Header", "Header-Value");
//
//                   // multipart.addFormField("friend_id", "Cool Pictures");
//                    multipart.addFormField("reg_no", regnnumber);
//
//                    multipart.addFilePart("myFile", uploadFile1);
//
//                    List<String> response = multipart.finish();
//
//                    Log.v("rht", "SERVER REPLIED:");
//
//                    for (String line : response) {
//                        Log.v("rht", "Line : "+line);
//                        Toast.makeText(UploadBenificieryPic.this, response.toString(), Toast.LENGTH_SHORT).show();
//
//                    }
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//                return "ok";
//
//            }
//        }
//
//        UploadVideo uv = new UploadVideo();
//        uv.execute();
//
//    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        switch (requestCode) {
            case PERMISSION_REQUEST_CODE11:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(UploadBenificieryPic.this, "Permission Granted Successfully! ", Toast.LENGTH_LONG).show();

                } else {
                    Toast.makeText(UploadBenificieryPic.this, "Permission Denied :( ", Toast.LENGTH_LONG).show();
                }
                break;

            case REQUEST_LOCATION:
                getLocation();
                break;

        }
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

             UploadAll uploadAll = new UploadAll();
            uploadAll.getInstance().init();
            double latti=UploadAll.latitude;
            double longi= UploadAll.longitude;

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
                                Intent i = new Intent(UploadBenificieryPic.this, UploadAll.class);
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
                params.put("photo5", photo15);
                params.put("photo6", photo16);
                params.put("photo7", photo17);
                params.put("photo8", photo18);
                params.put("eng_id", eng_id);
                params.put("lat", lat);
                params.put("lon", lon);
                params.put("reg_no", regnnumber);
                params.put("datetime", pic_date);


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
                        CameraUtils.openSettings(UploadBenificieryPic.this);
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


    @RequiresApi(api = Build.VERSION_CODES.O)
    private void previewCapturedImage1() {
        try {
            // hide video preview

            imageinst_one.setVisibility(View.VISIBLE);

            bitmap1 = CameraUtils.optimizeBitmap(BITMAP_SAMPLE_SIZE, imageStoragePath);
           Bitmap result=print_img(bitmap1);
if(!TextUtils.isEmpty(img_no)&&img_no.equals("1")){
    photo11=imageTOString(result);
    imageinst_one.setImageBitmap(result);
}else if(!TextUtils.isEmpty(img_no)&&img_no.equals("2")){
    photo12=imageTOString(result);
    imageinst_two.setImageBitmap(result);
}else if(!TextUtils.isEmpty(img_no)&&img_no.equals("3")){
    photo13=imageTOString(result);
    imageinst_three.setImageBitmap(result);
}else if(!TextUtils.isEmpty(img_no)&&img_no.equals("4")){
    photo14=imageTOString(result);
    imageinst_four.setImageBitmap(result);
}else if(!TextUtils.isEmpty(img_no)&&img_no.equals("5")){
    photo15=imageTOString(result);
    imageinst_five.setImageBitmap(result);
}else if(!TextUtils.isEmpty(img_no)&&img_no.equals("6")){
    photo16=imageTOString(result);
    imageinst_six.setImageBitmap(result);
}else if(!TextUtils.isEmpty(img_no)&&img_no.equals("7")){
    photo17=imageTOString(result);
    imageinst_seven.setImageBitmap(result);
}else if(!TextUtils.isEmpty(img_no)&&img_no.equals("8")){
    photo18=imageTOString(result);
    imageinst_eight.setImageBitmap(result);
}


        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }


//    private void restoreFromBundle(Bundle savedInstanceState) {
//        if (savedInstanceState != null) {
//            if (savedInstanceState.containsKey(KEY_IMAGE_STORAGE_PATH)) {
//                imageStoragePath = savedInstanceState.getString(KEY_IMAGE_STORAGE_PATH);
//                if (!TextUtils.isEmpty(imageStoragePath)) {
//                    if (imageStoragePath.substring(imageStoragePath.lastIndexOf(".")).equals("." + IMAGE_EXTENSION)) {
//                        previewCapturedImage1();
//                        previewCapturedImage2();
//                        previewCapturedImage3();
//                        previewCapturedImage4();
//                        previewCapturedImage5();
//                        previewCapturedImage6();
//                        previewCapturedImage7();
//                        previewCapturedImage8();
//                    }
//                }
//            }
//        }
//    }

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


        InstalltionTable installi = new InstalltionTable();
        installi.foto1 = String.valueOf(photo11);
        installi.foto2 = String.valueOf(photo12);
        installi.foto3 = String.valueOf(photo13);
        installi.foto4 = String.valueOf(photo14);
        installi.foto5 = String.valueOf(photo15);
        installi.foto6 = String.valueOf(photo16);
        installi.foto7 = String.valueOf(photo17);
        installi.foto8 = String.valueOf(photo18);
        installi.eng_id = eng_id;
        installi.Lat = lat;
        installi.Lon = lon;
        installi.Regn = regnnumber;
        installi.Dati = pic_date;
        try{
        installi.save();}catch (Exception ae){

        }
        Localdialog.findViewById(R.id.clickmedismissf).setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            public void onClick(View v) {
                Toast.makeText(UploadBenificieryPic.this, "Saved", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(UploadBenificieryPic.this, UploadAll.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i);
                finish();
                Localdialog.dismiss();
            }
        });

        Localdialog.show();
        //  Toast.makeText(getApplicationContext(),"Saved inLocal",Toast.LENGTH_SHORT).show();

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

    paint.setStyle(Paint.Style.STROKE);
    paint.setTextSize(12);
    paint.setAntiAlias(false);

    Paint innerPaint = new Paint();
    innerPaint.setColor(Color.parseColor("#61ECECEC"));
//    innerPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
    innerPaint.setAntiAlias(true);
    canvas.drawRect(180F, 50F, 0, 0, innerPaint);
    canvas.drawText("Lat - "+lat,5, 15, paint);
    canvas.drawText("Long - "+lng,5, 30, paint);
    canvas.drawText("Date - "+pic_date,5, 45, paint);
return result;
    }

}
