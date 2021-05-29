package update.gautamsolar.creda.Creda_Rajasthan;

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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

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
import com.gautamsolar.creda.R;
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

import update.gautamsolar.creda.CameraUtils;
import update.gautamsolar.creda.Constants.Constants;
import update.gautamsolar.creda.Database.Site_Survey_Table_Rajasthan;
import update.gautamsolar.creda.MySingleton;
import update.gautamsolar.creda.SharedPrefManager;
import update.gautamsolar.creda.UploadAll;

import static android.provider.MediaStore.Files.FileColumns.MEDIA_TYPE_IMAGE;

public class Site_Survey_Rajasthan extends AppCompatActivity {

    TextView status_sitsurvey;
    TextView Father, Contactid, Villageide, Pumpide, Contactide, Blockid, districtid,numberid;

    LocationManager locationManager;
    boolean isGPSEnabled = false;
    private static final int PICK_IMAGE_REQUEST_SITE1 = 63;
    static final int REQUEST_LOCATION = 60;
    private Button btnsite_upload;
    private static final int PERMISSION_REQUEST_CODE_SITE = 62;
    private static final int PICK_IMAGE_REQUEST_SITE2 = 64;
    private static final int PICK_IMAGE_REQUEST_SITE3 = 65;
    private static final int PICK_IMAGE_REQUEST_SITE4 = 66;
    public static final String GALLERY_DIRECTORY_NAME = "GSPLCREDA";
    public static final String IMAGE_EXTENSION = "jpg";
    public static final String VIDEO_EXTENSION = "mp4";
    public static final int MEDIA_TYPE_IMAGES = 302;
    Dialog dialog, Localdialog;
    //public static final int MEDIA_TYPE_VIDEO = 190;
    public static final String KEY_IMAGE_STORAGE_PATH = "image_path";
    public static final int BITMAP_SAMPLE_SIZE = 8;
    private static String imageStoragePathS;


    ImageView passport_size_pic, adharImage, bankpassbookimage, imagemarkedsite;
    EditText edittextwaterlevel, edittextbordepth, edittextborsize, edittextexisting_motor;
    String edittextwaterlevel_string, edittextbordepth_string, edittextborsize_string,
            radiogroupbor_string, edittextexisting_motor_string, radiocomplete_string, fathername, regnnumber, benifname, contact, block, village, survey_status;
    Bitmap bitmap1 = null, bitmap2 = null, bitmap3 = null, bitmap4 = null;
    String KEYPHOTO1, KEYPHOTO2, KEYPHOTO3, KEYPHOTO4, WATER_LEVEL_STRING, BORE_DEPTH_STRING,
            BORE_SIZE_STRING, BORESTATUS_STRING, EXISTING_MOTOR_RUNNING_STRING, SCOMPLETE_STATUS_STRING;

    RadioGroup radiogroupbor, radiocomplete;
    RadioButton borempty, borused, complete, uncomplete;
    ProgressDialog progressDialog;
    String eng_id, site_api, Engineer_contact, reg_no, lat, lon;
    String existing_moter_run,bor_depth,bor_size,water_level,photo11, photo12, photo13, photo14, Engineer_Contact, localcounti;
    int Status_SiteSurvey;
    ProgressDialog pb;
    String bore_status,url1 = null, url2 = null, url3 = null, url4 = null, localcount;
    SharedPreferences sharedPreferences;


    //1 means data is synced and 0 means data is not synced
    public static final int NAME_SYNCED_WITH_SERVERS = 1;
    public static final int NAME_NOT_SYNCED_WITH_SERVERS = 0;
    int localdatacount = 0;
    //a broadcast to know weather the data is synced or not
    public static final String DATA_SAVED_BROADCASTS = "net.simplifiedcoding.datasaveds";

    //Broadcast receiver to know the sync status
    private BroadcastReceiver broadcastReceivers;
    Constants constants;
String img_no;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_site__survey__rajasthan);
        getSupportActionBar().setTitle("Pump Installation RAJASTHAN");
        //  registerReceiver( new NetworkStateCheckerS(), new IntentFilter( ConnectivityManager.CONNECTIVITY_ACTION ) );
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        Engineer_Contact = sharedPreferences.getString("engcontact", "");

        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        constants = new Constants();
        site_api = constants.SITE_SURVEY_API;
        eng_id = sharedPreferences.getString("eng_id", "");

        if (!isGPSEnabled) {
            // no network provider is enabled
            Intent intent1 = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            startActivity(intent1);
        }
        //   db = new DatabaseHelper( this );
        pb = new ProgressDialog(this, R.style.MyGravity);
        pb.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        pb.setIndeterminateDrawable(getResources().getDrawable(R.drawable.custom_progress_dialog));
        pb.setCancelable(false);
        btnsite_upload = findViewById(R.id.btnsite_uploadrj);
        radiogroupbor = findViewById(R.id.radiogroupborrj);
        borempty = findViewById(R.id.boremptyrj);
        borused = findViewById(R.id.borusedrj);
        Father = (TextView) findViewById(R.id.fatheride);
        Contactid = (TextView) findViewById(R.id.contactide);
        Villageide = (TextView) findViewById(R.id.villageide);
        Pumpide = (TextView) findViewById(R.id.benificiaryide);
        Contactide = (TextView) findViewById(R.id.contactide);
        Blockid = (TextView) findViewById(R.id.blockide);
        numberid=findViewById(R.id.numberid);
        dialog = new Dialog(Site_Survey_Rajasthan.this); // Context, this, etc.
        Localdialog = new Dialog(Site_Survey_Rajasthan.this); // Context, this, etc.

        edittextexisting_motor = findViewById(R.id.edittextexisting_motorrj);

        edittextwaterlevel = findViewById(R.id.edittextwaterlevelrj);
        edittextbordepth = findViewById(R.id.edittextbordepthrj);
        edittextborsize = findViewById(R.id.edittextborsizerj);

        photo11 = "noimage";
        photo12 = "noimage";
        photo13 = "noimage";
        photo14 = "noimage";
        lat="0";
        lon="0";


        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        localcounti = sharedPreferences.getString("localdata", "");


        passport_size_pic = findViewById(R.id.passport_size_picrj);
        adharImage = findViewById(R.id.adharImagerj);
        bankpassbookimage = findViewById(R.id.bankpassbookimagerj);
        imagemarkedsite = findViewById(R.id.imagemarkedsiterj);
        dialog.setContentView(R.layout.uploaddialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setCancelable(false);

        Localdialog.setContentView(R.layout.localdialog);
        Localdialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        Localdialog.setCancelable(false);

        Bundle bundleUploadB = getIntent().getExtras();

        fathername = bundleUploadB.getString("fathername");
        regnnumber = bundleUploadB.getString("regnnumber");
        benifname = bundleUploadB.getString("benifname");
        village = bundleUploadB.getString("village");
        block = bundleUploadB.getString("block");
        contact = bundleUploadB.getString("contact");
        survey_status = bundleUploadB.getString("survey_status");
        KEYPHOTO1 = bundleUploadB.getString("adharimg");
        KEYPHOTO2 = bundleUploadB.getString("passbookimg");
        KEYPHOTO3 = bundleUploadB.getString("sidemarkimg");
        KEYPHOTO4 = bundleUploadB.getString("benifimg");
        water_level = bundleUploadB.getString("water_level");
        bor_size = bundleUploadB.getString("bor_size");
        bore_status = bundleUploadB.getString("bor_status");
        bor_depth = bundleUploadB.getString("bor_depth");
        existing_moter_run = bundleUploadB.getString("existing_moter_run");
        edittextbordepth.setText(bor_depth);
        edittextborsize.setText(bor_size);
        edittextexisting_motor.setText(existing_moter_run);
        edittextwaterlevel.setText(water_level);


        if (!KEYPHOTO1.equals("null")) {
            adharImage.setBackgroundResource(R.mipmap.tickclick);
        }
        if (!KEYPHOTO2.equals("null")) {
            bankpassbookimage.setBackgroundResource(R.mipmap.tickclick);

        }
        if (!KEYPHOTO3.equals("null")) {
            imagemarkedsite.setBackgroundResource(R.mipmap.tickclick);
        }
        if (!KEYPHOTO4.equals("null")) {
            passport_size_pic.setBackgroundResource(R.mipmap.tickclick);
        }
        if (bore_status.equals("empty"))
        {
            borempty.setChecked(true);
            radiogroupbor_string = "empty";

        }
        else
        {
            borused.setChecked(true);
            radiogroupbor_string = "not_used_for_three_month";
        }

        Father.setText(fathername);
        Contactid.setText(contact);
        Villageide.setText(village);
        Blockid.setText(block);
        Pumpide.setText(benifname);
numberid.setText(regnnumber);

        Engineer_contact = SharedPrefManager.getInstance(this).getUserContact();
        Bundle bundle6 = getIntent().getExtras();
        reg_no = bundle6.getString("reg_no");


        passport_size_pic.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {

                if (CameraUtils.checkPermissions(getApplicationContext())) {
                    captureImage1();
                } else {
                    requestCameraPermission1(MEDIA_TYPE_IMAGES);
                }
img_no="1";
            }
        });
        adharImage.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {

                if (CameraUtils.checkPermissions(getApplicationContext())) {
                    captureImage1();
                } else {
                    requestCameraPermission1(MEDIA_TYPE_IMAGES);
                }
img_no="2";
            }
        });
        bankpassbookimage.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {
                if (CameraUtils.checkPermissions(getApplicationContext())) {
                    captureImage1();
                } else {
                    requestCameraPermission1(MEDIA_TYPE_IMAGES);
                }
img_no="3";
            }
        });
        imagemarkedsite.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {

                if (CameraUtils.checkPermissions(getApplicationContext())) {
                    captureImage1();
                } else {
                    requestCameraPermission1(MEDIA_TYPE_IMAGES);
                }

img_no="4";
            }
        });


        radiogroupbor.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.boremptyrj) {

                    radiogroupbor_string = "empty";
                } else if (checkedId == R.id.borusedrj) {

                    radiogroupbor_string = "not_used_for_three_month";

                }


            }
        });


        btnsite_upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
                isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);

//                if (isGPSEnabled) {
                getLocation();
                ConnectivityManager cm = (ConnectivityManager) Site_Survey_Rajasthan.this.getSystemService(Context.CONNECTIVITY_SERVICE);
                final NetworkInfo activeNetwork = cm.getActiveNetworkInfo();

                if (activeNetwork != null) {
                    if (edittextexisting_motor.getText().toString().equals("")) {
                        Toast.makeText(getApplicationContext(), "Please Fill Motor Running or Not", Toast.LENGTH_LONG).show();

                    } else if (edittextbordepth.getText().toString().equals("")) {
                        Toast.makeText(getApplicationContext(), "Please Enter Bore Depth", Toast.LENGTH_LONG).show();

                    } else if (edittextborsize.getText().toString().equals("")) {
                        Toast.makeText(getApplicationContext(), "Please Enter Bore Size ", Toast.LENGTH_LONG).show();

                    } else if (edittextwaterlevel.getText().toString().equals("")) {
                        Toast.makeText(getApplicationContext(), "Please Enter Water Level", Toast.LENGTH_LONG).show();

                    } else if (radiogroupbor.getCheckedRadioButtonId() == -1) {
                        Toast.makeText(getApplicationContext(), "Please Enter Bore is in use or not from last three months", Toast.LENGTH_LONG).show();

                    } else if(lat.equals("0")&&lon.equals("0")){
                        getLocation();
                    }else {
                        uploadsitedetail();
                    }

                } else {
                    if (edittextexisting_motor.getText().toString().equals("")) {
                        Toast.makeText(getApplicationContext(), "Please Fill Motor Running or Not", Toast.LENGTH_LONG).show();

                    } else if (edittextbordepth.getText().toString().equals("")) {
                        Toast.makeText(getApplicationContext(), "Please Enter Bore Depth", Toast.LENGTH_LONG).show();

                    } else if (edittextborsize.getText().toString().equals("")) {
                        Toast.makeText(getApplicationContext(), "Please Enter Bore Size ", Toast.LENGTH_LONG).show();

                    } else if (edittextwaterlevel.getText().toString().equals("")) {
                        Toast.makeText(getApplicationContext(), "Please Enter Water Level", Toast.LENGTH_LONG).show();

                    } else if (radiogroupbor.getCheckedRadioButtonId() == -1) {
                        Toast.makeText(getApplicationContext(), "Please Enter Bore is in use or not from last three months", Toast.LENGTH_LONG).show();

                    }else if(lat.equals("0")&&lon.equals("0")){
                        getLocation();
                    } else {
                       save_local();
                    }
                }


            }
        });



        broadcastReceivers = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                //loading the names again
//                loadNamessites();
            }
        };


    }



    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        switch (requestCode) {
            case PERMISSION_REQUEST_CODE_SITE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(Site_Survey_Rajasthan.this, "Permission Granted Successfully! ", Toast.LENGTH_LONG).show();
                    /*Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(intent, IMAGE_CAPTURE1);*/
                } else {
                    Toast.makeText(Site_Survey_Rajasthan.this, "Permission Denied :( ", Toast.LENGTH_LONG).show();
                }
                break;

            case REQUEST_LOCATION:
                getLocation();
                break;

        }
    }

    public void getLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);


        } else {

            UploadAll.getInstance().init();
            double latti=UploadAll.latitude;
            double longi= UploadAll.longitude;

            lat = String.valueOf(latti);
            lon = String.valueOf(longi);
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST_SITE1) {
            if (resultCode == RESULT_OK) {
                // Refreshing the gallery
                CameraUtils.refreshGallery(getApplicationContext(), imageStoragePathS);

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
        }

    }


    public void uploadsitedetail() {



        pb.show();


        StringRequest stringRequest = new StringRequest(Request.Method.POST, site_api, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                pb.dismiss();

                try {
                //    Toast.makeText(getApplicationContext(), response, Toast.LENGTH_LONG).show();
                    JSONObject obj = new JSONObject(response);

                    String message = obj.getString("error");
                    if (message.equals("false")) {

//
                        dialog.findViewById(R.id.clickmedismiss).setOnClickListener(new View.OnClickListener() {
                            @SuppressLint("ResourceAsColor")
                            public void onClick(View v) {
                                Intent i = new Intent(Site_Survey_Rajasthan.this, UploadAll.class);
                                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(i);
                                finish();
                                dialog.dismiss();
                            }
                        });
                        dialog.show();

                    } else {
                        pb.dismiss();
                        save_local();
//
                    }
                    //       Toast.makeText( getApplicationContext(), response, Toast.LENGTH_LONG ).show();


                } catch (JSONException e) {
                    pb.dismiss();
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
                params.put("eng_id", eng_id);
                params.put("boredepth", edittextbordepth.getText().toString());
                params.put("boredsize", edittextborsize.getText().toString());
                params.put("borewaterlevel", edittextwaterlevel.getText().toString());
                params.put("radioborestatus", radiogroupbor_string);
                params.put("edittextexisting_motor_string", edittextexisting_motor.getText().toString());
                params.put("Status", survey_status);
                params.put("lat", lat);
                params.put("lon", lon);
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
        outState.putString(KEY_IMAGE_STORAGE_PATH, imageStoragePathS);
    }

    /**
     * Restoring image path from saved instance state
     */
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        // get the file url
        imageStoragePathS = savedInstanceState.getString(KEY_IMAGE_STORAGE_PATH);
    }

    private void showPermissionsAlert() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Permissions required!")
                .setMessage("Camera needs few permissions to work properly. Grant them in settings.")
                .setPositiveButton("GOTO SETTINGS", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        CameraUtils.openSettings(Site_Survey_Rajasthan.this);
                    }
                })
                .setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).show();
    }

 /*   private void captureVideo1() {
        Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);

        File file = CameraUtils.getOutputMediaFile(MEDIA_TYPE_VIDEO);
        if (file != null) {
            imageStoragePath = file.getAbsolutePath();
        }

        Uri fileUri = CameraUtils.getOutputMediaFileUri(getApplicationContext(), file);

        // set video quality
        intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);

        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri); // set the image file

        // start the video capture Intent
        startActivityForResult(intent, CAMERA_CAPTURE_VIDEO_REQUEST_CODE);
    }*/

    private void captureImage1() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        File file = CameraUtils.getOutputMediaFile(MEDIA_TYPE_IMAGES);
        if (file != null) {
            imageStoragePathS = file.getAbsolutePath();
        }

        Uri fileUri = CameraUtils.getOutputMediaFileUri(getApplicationContext(), file);

        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);

        // start the image capture Intent
        startActivityForResult(intent, PICK_IMAGE_REQUEST_SITE1);
    }


    private void previewCapturedImage1() {
        try {
            // hide video preview


            passport_size_pic.setVisibility(View.VISIBLE);

            bitmap1 = CameraUtils.optimizeBitmap(BITMAP_SAMPLE_SIZE, imageStoragePathS);
            Bitmap result=print_img(bitmap1);
            if(img_no.equals("1")){
                photo11=imageTOString(result);
                passport_size_pic.setImageBitmap(result);
            }else if(img_no.equals("2")){
                photo12=imageTOString(result);
                adharImage.setImageBitmap(result);
            }else if(img_no.equals("3")){
                photo13=imageTOString(result);
                bankpassbookimage.setImageBitmap(result);
            }else if(img_no.equals("4")){
                photo14=imageTOString(result);
                imagemarkedsite.setImageBitmap(result);
            }

        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    public void save_local() {
        try {
            pb.dismiss();
        } catch (Exception e) {
            e.printStackTrace();

        }

        edittextbordepth_string = edittextbordepth.getText().toString();
        edittextborsize_string = edittextborsize.getText().toString();
        edittextwaterlevel_string = edittextwaterlevel.getText().toString();
        edittextexisting_motor_string = edittextexisting_motor.getText().toString();


        Site_Survey_Table_Rajasthan site_survey_table_rajasthan = new Site_Survey_Table_Rajasthan();
        site_survey_table_rajasthan.foto1 = String.valueOf(photo11);
        site_survey_table_rajasthan.foto2 = String.valueOf(photo12);
        site_survey_table_rajasthan.foto3 = String.valueOf(photo13);
        site_survey_table_rajasthan.foto4 = String.valueOf(photo14);
        site_survey_table_rajasthan.eng_id = eng_id;
        site_survey_table_rajasthan.boredepth = edittextbordepth_string;
        site_survey_table_rajasthan.boresize = edittextborsize_string;
        site_survey_table_rajasthan.waterlevel = edittextwaterlevel_string;
        site_survey_table_rajasthan.borestatus = radiogroupbor_string;
        site_survey_table_rajasthan.exstmotorstring = edittextexisting_motor_string;
        site_survey_table_rajasthan.Lat = lat;
        site_survey_table_rajasthan.Lon = lon;
        site_survey_table_rajasthan.Regn = regnnumber;
        site_survey_table_rajasthan.Dati = getDateTime();
        try{
        site_survey_table_rajasthan.save();}catch (Exception ae){

        }
        Localdialog.findViewById(R.id.clickmedismissf).setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            public void onClick(View v) {
                Toast.makeText(Site_Survey_Rajasthan.this, "Saved", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(Site_Survey_Rajasthan.this, UploadAll.class);
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

        UploadAll.getInstance().init();
        double latti=UploadAll.latitude;
        double longi= UploadAll.longitude;

        String lat=String.valueOf(latti);
        String lng=String.valueOf(longi);

        File f = new File(imageStoragePathS);

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
        paint.setTextSize(15);
        paint.setAntiAlias(true);

        Paint innerPaint = new Paint();
        innerPaint.setColor(Color.parseColor("#61ECECEC"));
//    innerPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
        innerPaint.setAntiAlias(true);
        canvas.drawRect(180F, 50F, 0, 0, innerPaint);
        canvas.drawText("Lat - "+lat,5, 15, paint);
        canvas.drawText("Long - "+lng,5, 30, paint);
        canvas.drawText("Date - "+getDateTime(),5, 45, paint);
        return result;
    }
}
