package update.gautamsolar.creda;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
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
import update.gautamsolar.creda.R;
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
import update.gautamsolar.creda.Database.SurveyTable;

import static android.provider.MediaStore.Files.FileColumns.MEDIA_TYPE_IMAGE;

public class SiteSurvey extends AppCompatActivity {

    TextView status_sitsurvey;
    TextView Father, Contactid, Villageide, Pumpide, Contactide, Blockid, districtid,numberidi;

    LocationManager locationManager;
    boolean isGPSEnabled = false;
    private static final int PICK_IMAGE_REQUEST_SITE1 = 63;
    static final int REQUEST_LOCATION = 60;
    private Button btnsite_upload;
    private static final int PERMISSION_REQUEST_CODE_SITE = 62;
    private static final int PICK_IMAGE_REQUEST_SITE2 = 64;
    private static final int PICK_IMAGE_REQUEST_SITE3 = 65;
    private static final int PICK_IMAGE_REQUEST_SITE4 = 66;
    private static final int PICK_IMAGE_REQUEST_SITE5 = 67;
    public static final String GALLERY_DIRECTORY_NAME = "GSPLCREDA";
    public static final String IMAGE_EXTENSION = "jpg";
    public static final String VIDEO_EXTENSION = "mp4";
    public static final int MEDIA_TYPE_IMAGES = 302;
    Dialog dialog, Localdialog;
    //public static final int MEDIA_TYPE_VIDEO = 190;
    public static final String KEY_IMAGE_STORAGE_PATH = "image_path";
    public static final int BITMAP_SAMPLE_SIZE = 8;
    private static String imageStoragePathS;


    ImageView passport_size_pic, adharImage, bankpassbookimage, imagemarkedsite,site_formate,aadhar_back,survay_image2,boaring_image;
    EditText edittextwaterlevel, edittextbordepth, edittextborsize, edittextexisting_motor;
    String edittextwaterlevel_string, edittextbordepth_string, edittextborsize_string,
            radiogroupbor_string, edittextexisting_motor_string, radiocomplete_string, fathername, regnnumber, benifname, contact, block, village, survey_status;
    Bitmap bitmap1 = null, bitmap2 = null, bitmap3 = null, bitmap4 = null, bitmap5=null;
    String KEYPHOTO1, KEYPHOTO2, KEYPHOTO3, KEYPHOTO4,KEYPHOTO5,KEYPHOTO6,KEYPHOTO7,KEYPHOTO8, WATER_LEVEL_STRING, BORE_DEPTH_STRING,
            BORE_SIZE_STRING, BORESTATUS_STRING, EXISTING_MOTOR_RUNNING_STRING, SCOMPLETE_STATUS_STRING;
    Constants constants;
    RadioGroup radiogroupbor, radiocomplete;
    RadioButton borempty, borused, complete, uncomplete;
    ProgressDialog progressDialog;
    String Engineer_contact, reg_no, lat, lon;
    String water_level, bor_size, bor_depth, existing_moter_run, eng_id, photo11, photo12, photo13, photo14,photo15,photo16,photo17,photo18, Engineer_Contact, localcounti;
    int Status_SiteSurvey;
    ProgressDialog pb;
String img_no;

    String bore_status, url1 = null, url2 = null, url3 = null, url4 = null, localcount;
    SharedPreferences sharedPreferences;


    //1 means data is synced and 0 means data is not synced
    public static final int NAME_SYNCED_WITH_SERVERS = 1;
    public static final int NAME_NOT_SYNCED_WITH_SERVERS = 0;
    int localdatacount = 0;
    //a broadcast to know weather the data is synced or not
    public static final String DATA_SAVED_BROADCASTS = "net.simplifiedcoding.datasaveds";

    //Broadcast receiver to know the sync status
    private BroadcastReceiver broadcastReceivers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_site_survey);
        //  registerReceiver( new NetworkStateCheckerS(), new IntentFilter( ConnectivityManager.CONNECTIVITY_ACTION ) );
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        Engineer_Contact = sharedPreferences.getString("engcontact", "");
        constants = new Constants();
        eng_id = sharedPreferences.getString("eng_id", "");

        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);

        if (!isGPSEnabled) {
            // no network provider is enabled
            Intent intent1 = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            startActivity(intent1);
        }
        //   db = new DatabaseHelper( this );
        pb = new ProgressDialog(this, R.style.MyGravity);
        pb.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        pb.setIndeterminateDrawable(getResources().getDrawable(R.drawable.custom_progress_dialog));
        pb.setCancelable(false);

        btnsite_upload = findViewById(R.id.btnsite_uploadsc);

        radiogroupbor = findViewById(R.id.radiogroupbosc);
        borempty = findViewById(R.id.boremptysc);
        borused = findViewById(R.id.borusedsc);
        Father = (TextView) findViewById(R.id.fatheride);
        Contactid = (TextView) findViewById(R.id.contactide);
        Villageide = (TextView) findViewById(R.id.villageide);
        Pumpide = (TextView) findViewById(R.id.benificiaryide);
        Contactide = (TextView) findViewById(R.id.contactide);
        Blockid = (TextView) findViewById(R.id.blockide);
        numberidi=findViewById(R.id.numberid);
        dialog = new Dialog(SiteSurvey.this); // Context, this, etc.
        Localdialog = new Dialog(SiteSurvey.this); // Context, this, etc.

        edittextexisting_motor = findViewById(R.id.edittextexisting_motorsc);

        edittextwaterlevel = findViewById(R.id.edittextwaterlevelsc);
        edittextbordepth = findViewById(R.id.edittextbordepthsc);
        edittextborsize = findViewById(R.id.edittextborsizesc);

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

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        localcounti = sharedPreferences.getString("localdata", "");
        // Toast.makeText(getApplicationContext(),String.valueOf(localcounti),Toast.LENGTH_LONG).show();

        passport_size_pic = findViewById(R.id.passport_size_picsc);
        adharImage = findViewById(R.id.adharImagesc);
        bankpassbookimage = findViewById(R.id.bankpassbookimagesc);
        imagemarkedsite = findViewById(R.id.imagemarkedsitesc);
        site_formate=findViewById(R.id.site_formate);
        aadhar_back=findViewById(R.id.aadhar_back);
        boaring_image=findViewById(R.id.boaring_image);
        survay_image2=findViewById(R.id.survey_image2);

        dialog.setContentView(R.layout.uploaddialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(false);

        Localdialog.setContentView(R.layout.localdialog);
        Localdialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        Localdialog.setCancelable(false);

        Bundle bundleUploadB = getIntent().getExtras();

        fathername = bundleUploadB.getString("fathername");
        regnnumber = bundleUploadB.getString("regnnumber");
        benifname = bundleUploadB.getString("benifname");
        bore_status = bundleUploadB.getString("bor_status");
        village = bundleUploadB.getString("village");
        block = bundleUploadB.getString("block");
        contact = bundleUploadB.getString("contact");
        survey_status = bundleUploadB.getString("survey_status");
        KEYPHOTO1 = bundleUploadB.getString("adharimg");
        KEYPHOTO2 = bundleUploadB.getString("passbookimg");
        KEYPHOTO3 = bundleUploadB.getString("sidemarkimg");
        KEYPHOTO4 = bundleUploadB.getString("benifimg");
        KEYPHOTO5 = bundleUploadB.getString("site_format");
        KEYPHOTO6 = bundleUploadB.getString("aadhar_back");
        KEYPHOTO7 = bundleUploadB.getString("boaring_image");
        KEYPHOTO8 = bundleUploadB.getString("survay2");
        water_level = bundleUploadB.getString("water_level");
        bor_size = bundleUploadB.getString("bor_size");
        bor_depth = bundleUploadB.getString("bor_depth");
        Father.setText(fathername);
        Contactid.setText(contact);
        Villageide.setText(village);
        Blockid.setText(block);
        Pumpide.setText(benifname);
        numberidi.setText(regnnumber);
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
        if (!KEYPHOTO5.equals("null")) {
            site_formate.setBackgroundResource(R.mipmap.tickclick);
        }
        if (!KEYPHOTO6.equals("null")) {
            aadhar_back.setBackgroundResource(R.mipmap.tickclick);
        }
        if (!KEYPHOTO7.equals("null")) {
            boaring_image.setBackgroundResource(R.mipmap.tickclick);
        }
        if (!KEYPHOTO8.equals("null")) {
            survay_image2.setBackgroundResource(R.mipmap.tickclick);
        }
        if (bore_status.equals("empty")) {
            borempty.setChecked(true);
            radiogroupbor_string = "empty";

        } else {
            borused.setChecked(true);
            radiogroupbor_string = "not_used_for_three_month";
        }



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

        site_formate.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {

                if (CameraUtils.checkPermissions(getApplicationContext())) {
                    captureImage1();
                } else {
                    requestCameraPermission1(MEDIA_TYPE_IMAGES);
                }

img_no="5";
            }
        });
        aadhar_back.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {

                if (CameraUtils.checkPermissions(getApplicationContext())) {
                    captureImage1();
                } else {
                    requestCameraPermission1(MEDIA_TYPE_IMAGES);
                }

                img_no="6";
            }
        });
        boaring_image.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {

                if (CameraUtils.checkPermissions(getApplicationContext())) {
                    captureImage1();
                } else {
                    requestCameraPermission1(MEDIA_TYPE_IMAGES);
                }

                img_no="7";
            }
        });
        survay_image2.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {

                if (CameraUtils.checkPermissions(getApplicationContext())) {
                    captureImage1();
                } else {
                    requestCameraPermission1(MEDIA_TYPE_IMAGES);
                }

                img_no="8";
            }
        });
        radiogroupbor.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.boremptysc) {

                    radiogroupbor_string = "empty";
                } else if (checkedId == R.id.borusedsc) {

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
                ConnectivityManager cm = (ConnectivityManager) SiteSurvey.this.getSystemService(Context.CONNECTIVITY_SERVICE);
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

                    }  else if(lat.equals("0")&&lon.equals("0")){
                       getLocation();
                    }
                        else
                        { uploadsitedetail();
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

                    } else if(lat.equals("0")&&lon.equals("0")){
                        getLocation();
                    }else {
                        save_local(); }

                }


            }
        });

        loadNamessites();
        broadcastReceivers = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                //loading the names again
//                loadNamessites();
            }
        };

        registerReceiver(broadcastReceivers, new IntentFilter(DATA_SAVED_BROADCASTS));


    }

    private void loadNamessites() {


        // Cursor cursor = db.getNamesS( regnnumber );
//        if (cursor.moveToFirst()) {
//            do {
//                KEYPHOTO1 = cursor.getString( cursor.getColumnIndex( DatabaseHelper.SPASSPORT_IMAGE ) );
//                KEYPHOTO2 = cursor.getString( cursor.getColumnIndex( DatabaseHelper.SADHAR_IMAGE ) );
//                KEYPHOTO3 = cursor.getString( cursor.getColumnIndex( DatabaseHelper.SPASSBOOK_IMAGE ) );
//                KEYPHOTO4 = cursor.getString( cursor.getColumnIndex( DatabaseHelper.SMARKED_IMAGE ) );
//
//
//                WATER_LEVEL_STRING = cursor.getString( cursor.getColumnIndex( DatabaseHelper.WATER_LEVEL ) );
//                BORE_DEPTH_STRING = cursor.getString( cursor.getColumnIndex( DatabaseHelper.BORE_DEPTH ) );
//                BORE_SIZE_STRING = cursor.getString( cursor.getColumnIndex( DatabaseHelper.BORE_SIZE ) );
//                BORESTATUS_STRING = cursor.getString( cursor.getColumnIndex( DatabaseHelper.BORESTATUS ) );
//                EXISTING_MOTOR_RUNNING_STRING = cursor.getString( cursor.getColumnIndex( DatabaseHelper.EXISTING_MOTOR_RUNNING ) );
//
//                               passport_size_pic.setImageBitmap( convertToBitmap( KEYPHOTO1 ) );
//                url1=KEYPHOTO1;
//                adharImage.setImageBitmap( convertToBitmap( KEYPHOTO2 ) );
//                url2=KEYPHOTO2;
//                bankpassbookimage.setImageBitmap( convertToBitmap( KEYPHOTO3 ) );
//                url3=KEYPHOTO3;
//                imagemarkedsite.setImageBitmap( convertToBitmap( KEYPHOTO4 ) );
//                url4=KEYPHOTO4;
//                imagemarkedsite.setImageBitmap( convertToBitmap( KEYPHOTO4 ) );
//
//
//
//
//
//
//                if(WATER_LEVEL_STRING.equals( null ))
//                {
//                    edittextwaterlevel.setText( "null");
//                }
//
//                else {
//                    edittextwaterlevel.setText( WATER_LEVEL_STRING );
//                }
//                if(BORE_SIZE_STRING.equals( null ))
//                {
//                    edittextbordepth.setText( "null" );
//                }
//                else
//                {
//                    edittextborsize.setText( BORE_SIZE_STRING );
//                }
//                if(BORE_DEPTH_STRING.equals( null ))
//                {
//                    edittextbordepth.setText( "null" );
//                }
//                else {
//                    edittextbordepth.setText( BORE_DEPTH_STRING );
//                }
//
//                if(EXISTING_MOTOR_RUNNING_STRING.equals( null ))
//                {
//                    edittextexisting_motor.setText( "null" );
//                }
//                else {
//                    edittextexisting_motor.setText( EXISTING_MOTOR_RUNNING_STRING );
//                }
//
//
//
//
//
//
//                if (BORESTATUS_STRING.equals( "empty" )){
//
//                    borempty.setChecked(true );
//
//
//
//                }
//                else if(BORESTATUS_STRING.equals( "not_used_for_three_month" ))
//                {
//                    borused.setChecked( true );
//                }
//                else {
//
//                    borused.setChecked( true );
//                }
//
//
//
//
//
//                String Status_String1 = String.valueOf( survey_status );
//                if (Status_String1.equals( "1" )) {
//
//                    status_sitsurvey.setText( "Complete" );
//                } else if (Status_String1.equals( "0" )) {
//                    status_sitsurvey.setText( "Un Complete" );
//                } else {
//
//
//                }
//            } while (cursor.moveToNext());
//        }


    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        switch (requestCode) {
            case PERMISSION_REQUEST_CODE_SITE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(SiteSurvey.this, "Permission Granted Successfully! ", Toast.LENGTH_LONG).show();
                    /*Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(intent, IMAGE_CAPTURE1);*/
                } else {
                    Toast.makeText(SiteSurvey.this, "Permission Denied :( ", Toast.LENGTH_LONG).show();
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
        Constants constants = new Constants();


        StringRequest stringRequest = new StringRequest(Request.Method.POST, constants.SITE_SURVEY_API, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                pb.dismiss();

                try {
                    JSONObject obj = new JSONObject(response);

                    String message = obj.getString("error");
                    if (message.equals("false")) {

//
                        dialog.findViewById(R.id.clickmedismiss).setOnClickListener(new View.OnClickListener() {
                            @SuppressLint("ResourceAsColor")
                            public void onClick(View v) {
                                Intent i = new Intent(SiteSurvey.this, UploadAll.class);
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
//                params.put("photo3", photo13);
                params.put("photo4", photo14);
                params.put("photo5", photo15);
                params.put("photo6", photo16);
                params.put("photo7", photo17);
                params.put("photo8", photo18);
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
                        CameraUtils.openSettings(SiteSurvey.this);
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
            }else if(img_no.equals("5")){
                photo15=imageTOString(result);
                site_formate.setImageBitmap(result);
            }else if(img_no.equals("6")){
                photo16=imageTOString(result);
                aadhar_back.setImageBitmap(result);
            }else if(img_no.equals("7")){
                photo17=imageTOString(result);
                boaring_image.setImageBitmap(result);
            }else if(img_no.equals("8")){
                photo18=imageTOString(result);
                survay_image2.setImageBitmap(result);
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


        SurveyTable survey = new SurveyTable();
        survey.foto1 = String.valueOf(photo11);
        survey.foto2 = String.valueOf(photo12);
        survey.foto3 = String.valueOf(photo13);
        survey.foto4 = String.valueOf(photo14);
        survey.foto5 = String.valueOf(photo15);
        survey.foto6 = String.valueOf(photo16);
        survey.foto7 = String.valueOf(photo17);
        survey.foto8 = String.valueOf(photo18);
        survey.eng_id = eng_id;
        survey.boredepth = edittextbordepth_string;
        survey.boresize = edittextborsize_string;
        survey.waterlevel = edittextwaterlevel_string;
        survey.borestatus = radiogroupbor_string;
        survey.exstmotorstring = edittextexisting_motor_string;
        survey.Lat = lat;
        survey.Lon = lon;
        survey.Regn = regnnumber;
        survey.Dati = getDateTime();
        try{
        survey.save();}catch (Exception ae){

        }
        Localdialog.findViewById(R.id.clickmedismissf).setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            public void onClick(View v) {
                Toast.makeText(SiteSurvey.this, "Saved", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(SiteSurvey.this, UploadAll.class);
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
