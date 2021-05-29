package update.gautamsolar.creda;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
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
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import update.gautamsolar.creda.Database.PitDatabase;

import static android.provider.MediaStore.Files.FileColumns.MEDIA_TYPE_IMAGE;

public class Activity_Pit extends AppCompatActivity {
    TextView Father, Contactid, Villageide, Pumpide, Contactide, Blockid,numberid;
    String reg_no, regnnumber, benifname, fathername, contact, block, village,eng_id,KEYPHOTO1;
    ImageView imagefound_one;
    SharedPreferences sharedPreferences;
    Button btnfoundation_upload;
    public static final String GALLERY_DIRECTORY_NAME = "GSPLCREDA";
    public static final String IMAGE_EXTENSION = "jpg";
    public static final String VIDEO_EXTENSION = "mp4";
    public static final int MEDIA_TYPE_IMAGEF = 155;

    public static final String KEY_IMAGE_STORAGE_PATH = "image_path";
    public static final int BITMAP_SAMPLE_SIZE = 8;
    private static String imageStoragePathF;
    private static final int IMAGE_CAPTURE1 = 20;
    Bitmap bitmap1 = null;
    Dialog Localdialog,dialog;
    String  photo1;
    double latti;
    double longi;
    Date date;
    ProgressDialog pb;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_foundation_pit);

        Father = (TextView) findViewById(R.id.fatheride);
        Contactid = (TextView) findViewById(R.id.contactide);
        Villageide = (TextView) findViewById(R.id.villageide);
        Pumpide = (TextView) findViewById(R.id.benificiaryide);
        Contactide = (TextView) findViewById(R.id.contactide);
        Blockid = (TextView) findViewById(R.id.blockide);
        numberid=findViewById(R.id.numberid);
        btnfoundation_upload=findViewById(R.id.btnfoundation_upload);
        imagefound_one=findViewById(R.id.imagefound_one);
        latti=0.0;
        longi=0.0;
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        eng_id=sharedPreferences.getString("eng_id","");
        Bundle bundlef = getIntent().getExtras();
pb=new ProgressDialog(this);
pb.setMessage("Please Wait");
pb.setCancelable(false);
        fathername = bundlef.getString("fathername");
        regnnumber = bundlef.getString("regnnumber");
        benifname = bundlef.getString("benifname");
        village = bundlef.getString("village");
        block = bundlef.getString("block");
        contact = bundlef.getString("contact");
        KEYPHOTO1 = bundlef.getString("fondimg1");
        if (!KEYPHOTO1.equals("null")) {
            imagefound_one.setBackgroundResource(R.mipmap.tickclick);
        }

        Father.setText(fathername);
        Contactid.setText(contact);
        Villageide.setText(village);
        Blockid.setText(block);
        Pumpide.setText(benifname);
        numberid.setText(regnnumber);

        dialog = new Dialog(Activity_Pit.this); // Context, this, etc.
        Localdialog = new Dialog(Activity_Pit.this); // Context, this, etc.

        dialog.setContentView(R.layout.uploaddialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(false);

        Localdialog.setContentView(R.layout.localdialog);
        Localdialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        Localdialog.setCancelable(false);
try {
    UploadAll.getInstance().init();
    latti = UploadAll.latitude;
    longi = UploadAll.longitude;
}catch (Exception ae){
}
        imagefound_one.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (CameraUtils.checkPermissions(getApplicationContext())) {
                    captureImage1();
                } else {
                    requestCameraPermission1(MEDIA_TYPE_IMAGEF);
                }
            }
        });

        btnfoundation_upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConnectivityManager cm = (ConnectivityManager) Activity_Pit.this.getSystemService(Context.CONNECTIVITY_SERVICE);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    NetworkCapabilities capabilities = cm.getNetworkCapabilities(cm.getActiveNetwork());
                if (capabilities != null) {
                    //if connected to wifi or mobile data plan
                         uploaduserimage1();
                        Toast.makeText(Activity_Pit.this, "Internet Access", Toast.LENGTH_SHORT).show();
//                        uploadVideo();
                    }else {
                    Toast.makeText(Activity_Pit.this, "Internet Not Access", Toast.LENGTH_SHORT).show();
                    save_local();
                }                }
                else {
                    if (cm != null) {
                        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
                        if (activeNetwork != null) {
                            uploaduserimage1();
                            Toast.makeText(Activity_Pit.this, "Internet Access", Toast.LENGTH_SHORT).show();
//                        uploadVideo();
                        }else {
                            Toast.makeText(Activity_Pit.this, "Internet Not Access", Toast.LENGTH_SHORT).show();
                            save_local();
                        }
                    }
                }
            }
        });

        restoreFromBundle(savedInstanceState);

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
                        CameraUtils.openSettings(Activity_Pit.this);
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



    private String imageTOString(Bitmap bitmap) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
        byte[] imageBytes = outputStream.toByteArray();
        String encodeImage1 = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodeImage1;

    }

    private void previewCapturedImage1() {
        try {
            // hide video preview


            imagefound_one.setVisibility(View.VISIBLE);

            bitmap1 = CameraUtils.optimizeBitmap(BITMAP_SAMPLE_SIZE, imageStoragePathF);

            imagefound_one.setImageBitmap(bitmap1);

        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }
    private void restoreFromBundle(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            if (savedInstanceState.containsKey(KEY_IMAGE_STORAGE_PATH)) {
                imageStoragePathF = savedInstanceState.getString(KEY_IMAGE_STORAGE_PATH);
                if (!TextUtils.isEmpty(imageStoragePathF)) {
                    if (imageStoragePathF.substring(imageStoragePathF.lastIndexOf(".")).equals("." + IMAGE_EXTENSION)) {
                        previewCapturedImage1();

                    }
                }
            }
        }}
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
        }else if (requestCode == 2) {
            try {
                Uri selectedImage = data.getData();
                String[] filePath = {MediaStore.Images.Media.DATA};
                Cursor c = getContentResolver().query(selectedImage, filePath, null, null, null);
                c.moveToFirst();
                int columnIndex = c.getColumnIndex(filePath[0]);
                String picturePath = c.getString(columnIndex);
                c.close();
                imageStoragePathF = picturePath;
                CameraUtils.refreshGallery(getApplicationContext(), imageStoragePathF);

                // successfully captured the image
                // display it in image view
                previewCapturedImage1();
            }catch (Exception ae){
                ae.getStackTrace();
            }
        }}

    public void save_local() {
        try {
            UploadAll.getInstance().init();
            latti = UploadAll.latitude;
            longi = UploadAll.longitude;
        }catch (Exception ae){
        }
        if (bitmap1 == null) {
            photo1 = "noimage";
        } else {
            photo1 = imageTOString(bitmap1);
        }
        PitDatabase pitDatabase = new PitDatabase();
        pitDatabase.foto1 = String.valueOf(photo1);
        pitDatabase.Lat = String.valueOf(latti);
        pitDatabase.Lon = String.valueOf(longi);
        pitDatabase.eng_id=eng_id;
        pitDatabase.Regn = regnnumber;
        pitDatabase.Dati = getDateTime();
        try{
        pitDatabase.save();}catch (Exception ae){
        }
        Localdialog.findViewById(R.id.clickmedismissf).setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            public void onClick(View v) {
                Toast.makeText(Activity_Pit.this, "Saved", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(Activity_Pit.this, UploadAll.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i);
                finish();
                Localdialog.dismiss();
            }
        });

        Localdialog.show();

        //  Toast.makeText(getApplicationContext(),"Saved inLocal",Toast.LENGTH_SHORT).show();

    }
    private String getDateTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        date = new Date();
        return dateFormat.format(date);
    }

    public void uploaduserimage1() {
        pb.show();
        try {
            UploadAll.getInstance().init();
            latti = UploadAll.latitude;
            longi = UploadAll.longitude;
        }catch (Exception ae){
        }
        if (bitmap1 == null) {
            photo1 = "noimage";
        } else {
            photo1 = imageTOString(bitmap1);
        }

        Log.d("photo5",photo1);
        Log.d("lat",String.valueOf(latti));
        Log.d("lon",String.valueOf(longi));
        Log.d("eng_id",eng_id);
        Log.d("regnnumber",regnnumber);
        Log.d("getDateTime",getDateTime());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://gautamsolar.co.in/pumpall_api/pituploadv_1.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                pb.dismiss();

                try {
                    JSONObject obj = new JSONObject(response);
                    //  Toast.makeText(getApplicationContext(),response,Toast.LENGTH_SHORT).show();
                    Log.d("response",response);
                    String message = obj.getString("error");
                    if (message.equals("false")) {

                        dialog.findViewById(R.id.clickmedismiss).setOnClickListener(new View.OnClickListener() {
                            @SuppressLint("ResourceAsColor")
                            public void onClick(View v) {
                                Intent i = new Intent(Activity_Pit.this, UploadAll.class);
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

}
