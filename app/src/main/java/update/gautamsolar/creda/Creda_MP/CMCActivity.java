package update.gautamsolar.creda.Creda_MP;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Location;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import androidx.core.app.ActivityCompat;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
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
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import update.gautamsolar.creda.CameraUtils;
import update.gautamsolar.creda.Constants.Constants;
import update.gautamsolar.creda.CredaModel;
import update.gautamsolar.creda.Database.CMC_MP_Table;
import update.gautamsolar.creda.MySingleton;
import update.gautamsolar.creda.UploadAll;

import static android.provider.MediaStore.Files.FileColumns.MEDIA_TYPE_IMAGE;

public class CMCActivity extends AppCompatActivity {
    public static final String GALLERY_DIRECTORY_NAME = "GSPLCREDA";
    public static final String IMAGE_EXTENSION = "jpg";
    static final int REQUEST_LOCATION = 155;
    public static final String VIDEO_EXTENSION = "mp4";
    public static final int MEDIA_TYPE_IMAGEF = 155;
    Bitmap bitmap1 = null, bitmap2 = null, bitmap3 = null, bitmap4 = null, bitmap5 = null;
    public static final String KEY_IMAGE_STORAGE_PATH = "image_path";
    public static final int BITMAP_SAMPLE_SIZE = 8;
    private static String imageStoragePathF;
    LocationManager locationManager;
    boolean isGPSEnabled = false;
    ImageView Cmc_Image_One,Cmc_Image_Two,CMC_Image_three,Cmc_Image_four;
    String photo1, photo2, photo3, photo4, photo5,photo6,photo7;
    Dialog dialog, Localdialog;
    ProgressDialog pb;
    Button CMC_Button;
    CredaModel credaModel;
    private Button btnfoundation_upload;
    private static final int PERMISSION_REQUEST_CODE1 = 11;
    private static final int IMAGE_CAPTURE1 = 20, IMAGE_CAPTURE2 = 21, IMAGE_CAPTURE3 = 22, IMAGE_CAPTURE4 = 23, IMAGE_CAPTURE5 = 306;
    Date date;
    String lat,lon,eng_id,CMCapi,sregnnumber,button_id,regnnumber, benifname, fathername, contact, block, village, foundation_status;;
    Constants constants;
    SharedPreferences sharedPreferences;
    TextView Father, Contactid, Villageide, Pumpide, Contactide, Blockid, districtid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cmc);
        constants= new Constants();
        getSupportActionBar().setTitle("CREDA MP");
        Father = (TextView) findViewById(R.id.fatherideic);
        Contactid = (TextView) findViewById(R.id.contactideic);
        Villageide = (TextView) findViewById(R.id.villageideic);
        Pumpide = (TextView) findViewById(R.id.benificiaryideic);
        Blockid = (TextView) findViewById(R.id.blockideic);
        CMCapi= constants.CMC_UPDATE;
        CMC_Button=findViewById(R.id.btn_cmc_upload);


        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        eng_id=sharedPreferences.getString("eng_id","");
        sregnnumber = sharedPreferences.getString("regnnumber", "");
        button_id =  sharedPreferences.getString("button_id","");
        Cmc_Image_One=findViewById(R.id.imagecmc_one);
        Cmc_Image_Two=findViewById(R.id.imagecmc_two);
        CMC_Image_three=findViewById(R.id.imagecmc_three);
        Cmc_Image_four=findViewById(R.id.imagecmc_four);
        Bundle bundlef = getIntent().getExtras();
        fathername = bundlef.getString("fathername");
        regnnumber = bundlef.getString("regnnumber");
        benifname = bundlef.getString("benifname");
        village = bundlef.getString("village");
        block = bundlef.getString("block");
        contact = bundlef.getString("contact");
        Father.setText(fathername);
        Contactid.setText(contact);
        Villageide.setText(village);
        Blockid.setText(block);
        Pumpide.setText(benifname);
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        dialog = new Dialog(CMCActivity.this); // Context, this, etc.
        Localdialog = new Dialog(CMCActivity.this); // Context, this, etc.
        //initializing views and objects
        // db = new DatabaseHelper( this );


        btnfoundation_upload = findViewById(R.id.btnfoundation_uploadmf);
        pb = new ProgressDialog(this, R.style.MyGravity);
        pb.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        pb.setIndeterminateDrawable(getResources().getDrawable(R.drawable.custom_progress_dialog));
        pb.setCancelable(false);

        dialog.setContentView(R.layout.uploaddialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(false);

        Localdialog.setContentView(R.layout.localdialog);
        Localdialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        Localdialog.setCancelable(false);


        Cmc_Image_One.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (CameraUtils.checkPermissions(getApplicationContext())) {
                    captureImage1();
                } else {
                    requestCameraPermission1(MEDIA_TYPE_IMAGEF);
                }

            }
        });
        Cmc_Image_Two.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (CameraUtils.checkPermissions(getApplicationContext())) {
                    captureImage2();
                } else {
                    requestCameraPermission2(MEDIA_TYPE_IMAGEF);
                }

            }
        });
        CMC_Image_three.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (CameraUtils.checkPermissions(getApplicationContext())) {
                    captureImage3();
                } else {
                    requestCameraPermission3(MEDIA_TYPE_IMAGEF);
                }

            }
        });

        Cmc_Image_four.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (CameraUtils.checkPermissions(getApplicationContext())) {
                    captureImage4();
                } else {
                    requestCameraPermission4(MEDIA_TYPE_IMAGEF);
                }

            }
        });
        CMC_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getLocation();
                ConnectivityManager cm = (ConnectivityManager) CMCActivity.this.getSystemService(Context.CONNECTIVITY_SERVICE);
                final NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
                if (activeNetwork != null) {
                    if (bitmap1!=null && bitmap2!=null)
                    {
                        uploaduserimage1();
                    }
                    else
                    {
                        Toast.makeText(getApplicationContext(),"Please clcik first two images ",Toast.LENGTH_LONG).show();

                    }

                }else if (bitmap1!=null && bitmap2!=null)
                {
                    save_local();
                }
                else {
                    Toast.makeText(getApplicationContext(),"Please clcik first two images ",Toast.LENGTH_LONG).show();


                }
            }
        });
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

    private void requestCameraPermission2(final int type) {
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

                                captureImage2();

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

    private void requestCameraPermission3(final int type) {
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

                                captureImage3();

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

    private void requestCameraPermission4(final int type) {
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

                                captureImage4();
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (requestCode == IMAGE_CAPTURE1) {
            if (resultCode == RESULT_OK) {
                // Refreshing the gallery
                CameraUtils.refreshGallery(getApplicationContext(), imageStoragePathF);

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
        if (requestCode == IMAGE_CAPTURE2) {
            if (resultCode == RESULT_OK) {
                // Refreshing the gallery
                CameraUtils.refreshGallery(getApplicationContext(), imageStoragePathF);

                // successfully captured the image
                // display it in image view
                previewCapturedImage2();
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
        if (requestCode == IMAGE_CAPTURE3) {
            if (resultCode == RESULT_OK) {
                // Refreshing the gallery
                CameraUtils.refreshGallery(getApplicationContext(), imageStoragePathF);

                // successfully captured the image
                // display it in image view
                previewCapturedImage3();
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
        if (requestCode == IMAGE_CAPTURE4) {
            if (resultCode == RESULT_OK) {
                // Refreshing the gallery
                CameraUtils.refreshGallery(getApplicationContext(), imageStoragePathF);

                // successfully captured the image
                // display it in image view
                previewCapturedImage4();
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


    void getLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);


        } else {

            Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            if (location != null) {
                double latti = location.getLatitude();
                double longi = location.getLongitude();
                lat = String.valueOf(latti);
                lon = String.valueOf(longi);

/*
                ((EditText)findViewById(R.id.lati)).setText("Latitude:"+lat);
                ((EditText)findViewById(R.id.longi)).setText("Longitude:"+lon);*/


            } else {
                lat = "00.000000";
                lon = "00.000000";
                /*((EditText)findViewById(R.id.lati)).setText("Unable to find Latitude");
                ((EditText)findViewById(R.id.longi)).setText("Unable to fing Longitude");*/
            }
        }
    }
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        // get the file url
        imageStoragePathF = savedInstanceState.getString(KEY_IMAGE_STORAGE_PATH);
    }
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        // save file url in bundle as it will be null on screen orientation
        // changes
        outState.putString(KEY_IMAGE_STORAGE_PATH, imageStoragePathF);
    }

    private String imageTOString(Bitmap bitmap) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
        byte[] imageBytes = outputStream.toByteArray();
        String encodeImage1 = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodeImage1;

    }


    private String getDateTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        date = new Date();
        return dateFormat.format(date);
    }



    private void showPermissionsAlert() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Permissions required!")
                .setMessage("Camera needs few permissions to work properly. Grant them in settings.")
                .setPositiveButton("GOTO SETTINGS", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        CameraUtils.openSettings(CMCActivity.this);
                    }
                })
                .setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).show();
    }

    private void captureImage1() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        File file = CameraUtils.getOutputMediaFile(MEDIA_TYPE_IMAGEF);
        if (file != null) {
            imageStoragePathF = file.getAbsolutePath();
        }

        Uri fileUri = CameraUtils.getOutputMediaFileUri(getApplicationContext(), file);

        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);

        // start the image capture Intent
        startActivityForResult(intent, IMAGE_CAPTURE1);
    }


    private void captureImage2() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        File file = CameraUtils.getOutputMediaFile(MEDIA_TYPE_IMAGEF);
        if (file != null) {
            imageStoragePathF = file.getAbsolutePath();
        }

        Uri fileUri = CameraUtils.getOutputMediaFileUri(getApplicationContext(), file);

        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);

        // start the image capture Intent
        startActivityForResult(intent, IMAGE_CAPTURE2);
    }

    private void captureImage3() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        File file = CameraUtils.getOutputMediaFile(MEDIA_TYPE_IMAGEF);
        if (file != null) {
            imageStoragePathF = file.getAbsolutePath();
        }

        Uri fileUri = CameraUtils.getOutputMediaFileUri(getApplicationContext(), file);

        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);

        // start the image capture Intent
        startActivityForResult(intent, IMAGE_CAPTURE3);
    }

    private void captureImage4() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        File file = CameraUtils.getOutputMediaFile(MEDIA_TYPE_IMAGEF);
        if (file != null) {
            imageStoragePathF = file.getAbsolutePath();
        }

        Uri fileUri = CameraUtils.getOutputMediaFileUri(getApplicationContext(), file);

        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);

        // start the image capture Intent
        startActivityForResult(intent, IMAGE_CAPTURE4);
    }

    private void previewCapturedImage1() {
        try {
            // hide video preview


            Cmc_Image_One.setVisibility(View.VISIBLE);

            bitmap1 = CameraUtils.optimizeBitmap(BITMAP_SAMPLE_SIZE, imageStoragePathF);

            Cmc_Image_One.setImageBitmap(bitmap1);

        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    private void previewCapturedImage2() {
        try {
            // hide video preview


            Cmc_Image_Two.setVisibility(View.VISIBLE);

            bitmap2 = CameraUtils.optimizeBitmap(BITMAP_SAMPLE_SIZE, imageStoragePathF);

            Cmc_Image_Two.setImageBitmap(bitmap2);

        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    private void previewCapturedImage3() {
        try {
            // hide video preview


            CMC_Image_three.setVisibility(View.VISIBLE);

            bitmap3 = CameraUtils.optimizeBitmap(BITMAP_SAMPLE_SIZE, imageStoragePathF);

            CMC_Image_three.setImageBitmap(bitmap3);

        } catch (NullPointerException e) {


            e.printStackTrace();

        }
    }

    private void previewCapturedImage4() {
        try {
            // hide video preview


            Cmc_Image_four.setVisibility(View.VISIBLE);

            bitmap4 = CameraUtils.optimizeBitmap(BITMAP_SAMPLE_SIZE, imageStoragePathF);

            Cmc_Image_four.setImageBitmap(bitmap4);

        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }
    public void uploaduserimage1() {
        pb.show();
        if (bitmap1 == null) {
            photo1 = "noimage";

        } else {
            photo1 = imageTOString(bitmap1);

        }
        if (bitmap2 == null) {
            photo2 = "noimage";

        } else {
            photo2 = imageTOString(bitmap2);


        }
        if (bitmap3 == null) {
            photo3 = "noimage";


        } else {

            photo3 = imageTOString(bitmap3);




        }
        if (bitmap4 == null) {

            photo4 = "noimage";

        } else {

            photo4 = imageTOString(bitmap4);



        }



        StringRequest stringRequest = new StringRequest(Request.Method.POST,CMCapi, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                pb.dismiss();
               // Toast.makeText(getApplicationContext(),response,Toast.LENGTH_LONG).show();

                try {
                    JSONObject obj = new JSONObject(response);
                    // Toast.makeText(getApplicationContext(),response,Toast.LENGTH_SHORT).show();
                    String message = obj.getString("error");
                    if (message.equals("false")) {

                        dialog.findViewById(R.id.clickmedismiss).setOnClickListener(new View.OnClickListener() {
                            @SuppressLint("ResourceAsColor")
                            public void onClick(View v) {
                                Intent i = new Intent(CMCActivity.this, UploadAll.class);
                                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(i);
                                finish();
                                dialog.dismiss();
                            }
                        });
                        dialog.show();
                    } else {
                        pb.dismiss();
//                        save_local();


                    }

                } catch (JSONException e) {
                    pb.dismiss();
//                    save_local();
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                save_local();
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
                params.put("photo1",photo1);
                params.put("photo2",photo2);
                params.put("photo3",photo3);
                params.put("photo4",photo4);
                params.put("button_id",button_id);
                params.put("lat",lat);
                params.put("lon",lon);
                params.put("eng_id",eng_id);
                params.put("reg_no",sregnnumber);
                params.put("datetime",getDateTime());
                return params;
            }
        };

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(10000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        stringRequest.setShouldCache(false);
        MySingleton.getInstance(getApplicationContext()).addTorequestque(stringRequest);
    }
    public void save_local() {
        try {
            pb.dismiss();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        getLocation();
        if (bitmap1 == null) {
            photo1 = "noimage";

        } else {
            photo1 = imageTOString(bitmap1);

        }
        if (bitmap2 == null) {
            photo2 = "noimage";

        } else {
            photo2 = imageTOString(bitmap2);

        }
        if (bitmap3 == null) {
            photo3 = "noimage";

        } else {
            photo3 = imageTOString(bitmap3);

        }
        if (bitmap4 == null) {
            photo4 = "noimage";

        } else {
            photo4 = imageTOString(bitmap4);

        }



        CMC_MP_Table cmc_mp_table = new CMC_MP_Table();
        cmc_mp_table.foto1 = String.valueOf(photo1);
        cmc_mp_table.foto2 = String.valueOf(photo2);
        cmc_mp_table.foto3 = String.valueOf(photo3);
        cmc_mp_table.foto4 = String.valueOf(photo4);
        cmc_mp_table.Lat = lat;
        cmc_mp_table.Lon = lon;
        cmc_mp_table.eng_id=eng_id;
        cmc_mp_table.button_id=button_id;
        cmc_mp_table.Regn = sregnnumber;
        cmc_mp_table.Dati = getDateTime();
        cmc_mp_table.save();
        Localdialog.findViewById(R.id.clickmedismissf).setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            public void onClick(View v) {
                Toast.makeText(CMCActivity.this, "Saved", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(CMCActivity.this, UploadAll.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i);
                finish();
                Localdialog.dismiss();
            }
        });

        Localdialog.show();

        //  Toast.makeText(getApplicationContext(),"Saved inLocal",Toast.LENGTH_SHORT).show();

    }

}
