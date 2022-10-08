package update.gautamsolar.creda.Creda_Rajasthan;

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

import update.gautamsolar.creda.CameraUtils;
import update.gautamsolar.creda.Constants.Constants;
import update.gautamsolar.creda.CredaModel;
import update.gautamsolar.creda.Database.Foundation_Table_Rajasthan;
import update.gautamsolar.creda.MySingleton;
import update.gautamsolar.creda.UploadAll;

import static android.provider.MediaStore.Files.FileColumns.MEDIA_TYPE_IMAGE;

public class Foundation_Rajasthan extends AppCompatActivity {
    // private DatabaseHelper db;
    /* private SQLiteOpenHelper openHelper;*/
    LocationManager locationManager;
    boolean isGPSEnabled = false;
    static final int REQUEST_LOCATION = 155;
    Date date;
    Dialog dialog, Localdialog;
    TextView status_foundation;
    TextView Father, Contactid, Villageide, Pumpide, Contactide, Blockid, districtid,numberid;
    public static final String GALLERY_DIRECTORY_NAME = "GSPLCREDA";
    public static final String IMAGE_EXTENSION = "jpg";
    public static final String VIDEO_EXTENSION = "mp4";
    public static final int MEDIA_TYPE_IMAGEF = 155;
    //public static final int MEDIA_TYPE_VIDEO = 190;
    public static final String KEY_IMAGE_STORAGE_PATH = "image_path";
    public static final int BITMAP_SAMPLE_SIZE = 4;
    private static String imageStoragePathF;

    private Button btnfoundation_upload;
    private static final int PERMISSION_REQUEST_CODE1 = 11;
    private static final int IMAGE_CAPTURE1 = 20, IMAGE_CAPTURE2 = 21, IMAGE_CAPTURE3 = 22, IMAGE_CAPTURE4 = 23, IMAGE_CAPTURE5 = 306;
    ImageView imagefound_one, imagefound_two, imagefound_three, imagefound_four;
    Bitmap bitmap1 = null, bitmap2 = null, bitmap3 = null, bitmap4 = null, bitmap5 = null;
    String KEYPHOTO1, KEYPHOTO2, KEYPHOTO3, KEYPHOTO4, KEYPHOTO5, radiofouncomplete_string, FCOMPLETE_STATUS_STRING;
    RadioGroup radiofouncomplete;
    RadioButton founcomplete, foununcomplete;
    String url1 = null;
    String photo1, photo2, photo3, photo4, photo5;
    String url2 = null;
    String url3 = null;
    String url4 = null;
    String url5 = null;
    int Status_foundation;


    public static final String myurlnew = "http://gautamsolar.co.in/rajasthan/RjAndroidApi/foundationimage_uploadnewjatin.php";
    private String FETCHIMAGE = "http://gautamsolar.co.in/credanew/credaAndroidApi/fetch_found_picnew1.php";
    ProgressDialog progressDialog;
    String foundation_api, eng_id, Engineer_Contact, reg_no, lat, lon, regnnumber, benifname, fathername, contact, block, village, foundation_status;


    //1 means data is synced and 0 means data is not synced
    public static final int NAME_SYNCED_WITH_SERVER = 1;
    public static final int NAME_NOT_SYNCED_WITH_SERVER = 0;
    //a broadcast to know weather the data is synced or not
    public static final String DATA_SAVED_BROADCAST = "net.simplifiedcoding.datasaved";

    //Broadcast receiver to know the sync status
    private BroadcastReceiver broadcastReceiver;
    ProgressDialog pb;
    CredaModel credaModel;
    Constants constants;

    SharedPreferences sharedPreferences;
String img_no;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_foundation__rajasthan);

        /*openHelper = new DatabaseHelper( this );*/
        getSupportActionBar().setTitle("Pump Installation RAJASTHAN");
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        credaModel = new CredaModel();
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        Engineer_Contact = sharedPreferences.getString("engcontact", "");
        eng_id = sharedPreferences.getString("eng_id", "");
        constants = new Constants();
        foundation_api = constants.FOUNDATION_API;

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
        dialog = new Dialog(Foundation_Rajasthan.this); // Context, this, etc.
        Localdialog = new Dialog(Foundation_Rajasthan.this); // Context, this, etc.
        //initializing views and objects
        // db = new DatabaseHelper( this );


        btnfoundation_upload = findViewById(R.id.btnfoundation_uploadr);
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

        imagefound_one = findViewById(R.id.imagefound_oner);
        imagefound_two = findViewById(R.id.imagefound_twor);
        imagefound_three = findViewById(R.id.imagefound_threer);
        imagefound_four = findViewById(R.id.imagefound_fourr);
        Father = (TextView) findViewById(R.id.fatheride);
        Contactid = (TextView) findViewById(R.id.contactide);
        Villageide = (TextView) findViewById(R.id.villageide);
        Pumpide = (TextView) findViewById(R.id.benificiaryide);
        Contactide = (TextView) findViewById(R.id.contactide);
        Blockid = (TextView) findViewById(R.id.blockide);
        numberid=findViewById(R.id.numberid);
        credaModel.setDipatch_status(contact);

        photo1 = "noimage";
        photo2 = "noimage";
        photo3 = "noimage";
        photo4 = "noimage";
        lat="0";
        lon="0";

        Bundle bundlef = getIntent().getExtras();

        fathername = bundlef.getString("fathername");
        regnnumber = bundlef.getString("regnnumber");
        benifname = bundlef.getString("benifname");
        village = bundlef.getString("village");
        block = bundlef.getString("block");
        contact = bundlef.getString("contact");
        foundation_status = bundlef.getString("foundation_status");
        contact = bundlef.getString("contact");
        KEYPHOTO1 = bundlef.getString("fondimg1");
        KEYPHOTO2 = bundlef.getString("fondimg2");
        KEYPHOTO3 = bundlef.getString("fondimg3");
        KEYPHOTO4 = bundlef.getString("fondimg4");
        if (!KEYPHOTO1.equals("null")) {
            imagefound_one.setBackgroundResource(R.mipmap.tickclick);
        }
        if (!KEYPHOTO2.equals("null")) {
            imagefound_two.setBackgroundResource(R.mipmap.tickclick);

        }
        if (!KEYPHOTO3.equals("null")) {
            imagefound_three.setBackgroundResource(R.mipmap.tickclick);
        }
        if (!KEYPHOTO4.equals("null")) {
            imagefound_four.setBackgroundResource(R.mipmap.tickclick);
        }
        Father.setText(fathername);
        Contactid.setText(contact);
        Villageide.setText(village);
        Blockid.setText(block);
        Pumpide.setText(benifname);
        numberid.setText(regnnumber);
        //   Toast.makeText(getApplicationContext(),contact,Toast.LENGTH_SHORT).show();
        ConnectivityManager cm = (ConnectivityManager) Foundation_Rajasthan.this.getSystemService(Context.CONNECTIVITY_SERVICE);
        final NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if (activeNetwork != null) {


        } else {
            Toast.makeText(getApplicationContext(), "Please connect to the internet", Toast.LENGTH_SHORT).show();
        }


        imagefound_one.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (CameraUtils.checkPermissions(getApplicationContext())) {
                    captureImage1();
                } else {
                    requestCameraPermission1(MEDIA_TYPE_IMAGEF);
                }
                img_no="1";
            }
        });
        imagefound_two.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (CameraUtils.checkPermissions(getApplicationContext())) {
                    captureImage1();
                } else {
                    requestCameraPermission1(MEDIA_TYPE_IMAGEF);
                }
                img_no="2";
            }
        });
        imagefound_three.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (CameraUtils.checkPermissions(getApplicationContext())) {
                    captureImage1();
                } else {
                    requestCameraPermission1(MEDIA_TYPE_IMAGEF);
                }
                img_no="3";
            }
        });
        imagefound_four.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (CameraUtils.checkPermissions(getApplicationContext())) {
                    captureImage1();
                } else {
                    requestCameraPermission1(MEDIA_TYPE_IMAGEF);
                }
                img_no="4";
            }
        });


        btnfoundation_upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
                isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
//                if (isGPSEnabled) {
                getLocation();
                ConnectivityManager cm = (ConnectivityManager) Foundation_Rajasthan.this.getSystemService(Context.CONNECTIVITY_SERVICE);
                final NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
                if (activeNetwork != null) {
                     if(lat.equals("0")&&lon.equals("0")){
                        getLocation();
                    }
                   else{ uploaduserimage1();}
                }else {
                    if(lat.equals("0")&&lon.equals("0")){
                        getLocation();
                    }
                   else{ save_local();}
                }

//                } else  {
//                    Intent intent1 = new Intent( Settings.ACTION_LOCATION_SOURCE_SETTINGS );
//                    startActivity( intent1 );
//
//                }


            }
        });

        //registering the broadcast receiver to update sync status
        registerReceiver(broadcastReceiver, new IntentFilter(DATA_SAVED_BROADCAST));


    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        switch (requestCode) {
            case PERMISSION_REQUEST_CODE1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(Foundation_Rajasthan.this, "Permission Granted Successfully! ", Toast.LENGTH_LONG).show();
                    /*Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(intent, IMAGE_CAPTURE1);*/
                } else {
                    Toast.makeText(Foundation_Rajasthan.this, "Permission Denied :( ", Toast.LENGTH_LONG).show();
                }
                break;

        }
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


    }


    void getLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);


        } else {

            try {
                UploadAll.getInstance().init();
            }catch (Exception ae){
            }
            double latti=UploadAll.latitude;
            double longi= UploadAll.longitude;

            lat = String.valueOf(latti);
            lon = String.valueOf(longi);
        }
    }


    public void uploaduserimage1() {
        pb.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, foundation_api, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                pb.dismiss();

                try {
                    JSONObject obj = new JSONObject(response);
                    // Toast.makeText(getApplicationContext(),response,Toast.LENGTH_SHORT).show();
                    String message = obj.getString("error");
                    if (message.equals("false")) {

                        dialog.findViewById(R.id.clickmedismiss).setOnClickListener(new View.OnClickListener() {
                            @SuppressLint("ResourceAsColor")
                            public void onClick(View v) {
                                Intent i = new Intent(Foundation_Rajasthan.this, UploadAll.class);
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


                    }

                } catch (JSONException e) {
                    pb.dismiss();
                    save_local();
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
                params.put("photo1", photo1);
                params.put("photo2", photo2);
                params.put("photo3", photo3);
                params.put("photo4", photo4);
                params.put("eng_id", eng_id);
                params.put("lat", lat);
                params.put("lon", lon);
                params.put("Status", foundation_status);
                params.put("reg_no", regnnumber);
                params.put("datetime", getDateTime());
                return params;
            }
        };

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(10000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        stringRequest.setShouldCache(false);
        MySingleton.getInstance(getApplicationContext()).addTorequestque(stringRequest);
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
        outState.putString(KEY_IMAGE_STORAGE_PATH, imageStoragePathF);
    }

    /**
     * Restoring image path from saved instance state
     */
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        // get the file url
        imageStoragePathF = savedInstanceState.getString(KEY_IMAGE_STORAGE_PATH);
    }

    private void showPermissionsAlert() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Permissions required!")
                .setMessage("Camera needs few permissions to work properly. Grant them in settings.")
                .setPositiveButton("GOTO SETTINGS", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        CameraUtils.openSettings(Foundation_Rajasthan.this);
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


    private void previewCapturedImage1() {
        try {
            // hide video preview

            imagefound_one.setVisibility(View.VISIBLE);

            bitmap1 = CameraUtils.optimizeBitmap(BITMAP_SAMPLE_SIZE, imageStoragePathF);
            Bitmap result=print_img(bitmap1);
            if(img_no.equals("1")){
                photo1=imageTOString(result);
                imagefound_one.setImageBitmap(result);
            }else if(img_no.equals("2")){
                photo2=imageTOString(result);
                imagefound_two.setImageBitmap(result);
            }else if(img_no.equals("3")){
                photo3=imageTOString(result);
                imagefound_three.setImageBitmap(result);
            }else if(img_no.equals("4")){
                photo4=imageTOString(result);
                imagefound_four.setImageBitmap(result);
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
        getLocation();


        Foundation_Table_Rajasthan rajsthan_foundation_table = new Foundation_Table_Rajasthan();
        rajsthan_foundation_table.foto1 = String.valueOf(photo1);
        rajsthan_foundation_table.foto2 = String.valueOf(photo2);
        rajsthan_foundation_table.foto3 = String.valueOf(photo3);
        rajsthan_foundation_table.foto4 = String.valueOf(photo4);
        rajsthan_foundation_table.Lat = lat;
        rajsthan_foundation_table.eng_id = eng_id;
        rajsthan_foundation_table.Lon = lon;
        rajsthan_foundation_table.Regn = regnnumber;
        rajsthan_foundation_table.Dati = getDateTime();
        try {
            rajsthan_foundation_table.save();
        }catch (Exception ae){

        }
        Localdialog.findViewById(R.id.clickmedismissf).setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            public void onClick(View v) {
                Toast.makeText(Foundation_Rajasthan.this, "Saved", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(Foundation_Rajasthan.this, UploadAll.class);
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

        try {
            UploadAll.getInstance().init();
        }catch (Exception ae){
        }
        double latti=UploadAll.latitude;
        double longi= UploadAll.longitude;

        String lat=String.valueOf(latti);
        String lng=String.valueOf(longi);

        File f = new File(imageStoragePathF);

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
