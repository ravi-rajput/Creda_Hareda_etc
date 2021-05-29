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
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import update.gautamsolar.creda.CameraUtils;
import update.gautamsolar.creda.Constants.Constants;
import update.gautamsolar.creda.Database.Rmu_Table_MP;
import update.gautamsolar.creda.MySingleton;
import update.gautamsolar.creda.SiteSurvey;
import update.gautamsolar.creda.UploadAll;

import com.gautamsolar.creda.R;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.provider.MediaStore.Files.FileColumns.MEDIA_TYPE_IMAGE;

public class RMU_MP extends AppCompatActivity {
    TextView lumn, TextSimno, Textdeviceid, Old_RMU, Block, BenificiaryName, FatherID, ContactId, VillageID,numberid;
    Constants constants;
    Dialog dialog, Localdialog;
    LinearLayout RmuImage;
    String updatermu, dateupload,spinner_val,rmu_number,rmu_str;
    ImageView RmuimageOne;
    String lat, rmunewnumber, Rmuspinnervalue;
    String lon;
    static final int REQUEST_LOCATION = 60;

    public static final int BITMAP_SAMPLE_SIZE = 8;
    private static final int PICK_IMAGE_REQUEST_SITE1 = 163;
    LocationManager locationManager;
    boolean isGPSEnabled = false;
    Bitmap bitmap1 = null;
    Spinner RmuSpinner;
    String photormu, Rmu_api, Engineer_Contact;
    SharedPreferences settings;
    Button send, UPLOAD;
    EditText NewRmuNo;
    SharedPreferences sharedPreferences;
    private static String imageStoragePathS;
    String eng_id,rmu_mp_api,sregnnumber, sbenifname, sfname, scontact, svillage, sblock, spumpType, old_rmu;
    SharedPreferences settingss;
    SharedPreferences.Editor prefsEditor;
    ProgressDialog pb;
    ArrayList<String> arrayListrmu = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rmu__mp);
        constants = new Constants();
        rmu_mp_api=constants.RMU_API;
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        eng_id=sharedPreferences.getString("eng_id","");

        getSupportActionBar().setTitle("CREDA MP");
        BenificiaryName = (TextView) findViewById(R.id.benificiaryide);
        Block = (TextView) findViewById(R.id.blockide);
        RmuSpinner = (Spinner) findViewById(R.id.selectprefixrmurmump);
        arrayListrmu.add("Select type");
        arrayListrmu.add("GSSP");
        arrayListrmu.add("GSSPM");
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1, arrayListrmu);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        RmuSpinner.setAdapter(arrayAdapter);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        Engineer_Contact = sharedPreferences.getString("engcontact", "");
        pb = new ProgressDialog(this, R.style.MyGravity);
        pb.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        pb.setIndeterminateDrawable(getResources().getDrawable(R.drawable.custom_progress_dialog));
        pb.setCancelable(false);

        NewRmuNo = (EditText) findViewById(R.id.textrmunonewrmump);


        RmuimageOne = (ImageView) findViewById(R.id.imagermu_onermump);
        RmuImage = (LinearLayout) findViewById(R.id.rmu_image_clickrmu);
        settings = PreferenceManager.getDefaultSharedPreferences(this);
        prefsEditor = settings.edit();
        Localdialog = new Dialog(RMU_MP.this); // Context, this, etc.
        dialog = new Dialog(RMU_MP.this); // Context, this, etc.
        dialog.setContentView(R.layout.uploaddialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setCancelable(false);


        Localdialog.setContentView(R.layout.localdialog);
        Localdialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        Localdialog.setCancelable(false);

        FatherID = (TextView) findViewById(R.id.fatheride);
        VillageID = (TextView) findViewById(R.id.villageide);
        ContactId = (TextView) findViewById(R.id.contactide);
        numberid=findViewById(R.id.numberid);
        UPLOAD = (Button) findViewById(R.id.senduurmump);

        settingss = PreferenceManager.getDefaultSharedPreferences(this);
        sregnnumber = settingss.getString("regnnumber", "");
        sbenifname = settingss.getString("benifname", "");
        sfname = settingss.getString("fname", "");
        scontact = settingss.getString("contact", "");
        svillage = settingss.getString("village", "");
        sblock = settingss.getString("block", "");
        spumpType = settingss.getString("pumpType", "");
        rmu_str=settingss.getString("rmu_no", "");
        if(rmu_str.length()>=19){
            spinner_val=rmu_str.substring(0,5);
            Log.d("spinner_Val",spinner_val);
            if(spinner_val.equals("GSSPM")) {
                NewRmuNo.setText(rmu_str.substring(5, 19));
                RmuSpinner.setSelection(2);
            }else{
                NewRmuNo.setText(rmu_str.substring(4, 19));
                RmuSpinner.setSelection(1);
            }}else{
            NewRmuNo.setText(rmu_str);
        }

        FatherID.setText(String.valueOf(sfname));
        VillageID.setText(String.valueOf(svillage));
        ContactId.setText(String.valueOf(scontact));
        Block.setText(String.valueOf(sblock));
        BenificiaryName.setText(String.valueOf(sbenifname));
        numberid.setText(sregnnumber);
        RmuSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {


                Rmuspinnervalue = RmuSpinner.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        RmuImage.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {

                if (CameraUtils.checkPermissions(getApplicationContext())) {
                    captureImage1();
                } else {
                    requestCameraPermission1(SiteSurvey.MEDIA_TYPE_IMAGES);
                }

            }
        });

        UPLOAD.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                getLocation();
                if (NewRmuNo.getText().toString().equals("")) {
                    Toast.makeText(getApplicationContext(), "कृपया नया  RMU नंबर दर्ज करे ", Toast.LENGTH_LONG).show();
                } else if (Rmuspinnervalue.equals("Select type")) {
                    Toast.makeText(getApplicationContext(), "कृपया टाइप सेलेक्ट करे ", Toast.LENGTH_LONG).show();
                }
                else if (bitmap1==null)
                {
                    Toast.makeText(getApplicationContext(),"please click image",Toast.LENGTH_LONG).show();


                }
                else  {
                    rmunewnumber = Rmuspinnervalue + NewRmuNo.getText().toString();
                    ConnectivityManager cm = (ConnectivityManager) RMU_MP.this.getSystemService(Context.CONNECTIVITY_SERVICE);
                    final NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
                    if (activeNetwork != null) {

                        UploadRmu(Rmu_api);
                    } else {
                        save_local();
                    }


                }


            }
        });
//        getDateTime();
//          dateupload=String.valueOf(getDateTime());
//        Toast.makeText(getApplicationContext(),dateupload,Toast.LENGTH_LONG).show();
    }


    private String getDateTime() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        String datewe = dateFormat.format(date);
        return datewe;


    }


    private void captureImage1() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        File file = CameraUtils.getOutputMediaFile(SiteSurvey.MEDIA_TYPE_IMAGES);
        if (file != null) {
            imageStoragePathS = file.getAbsolutePath();
        }

        Uri fileUri = CameraUtils.getOutputMediaFileUri(getApplicationContext(), file);

        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);

        // start the image capture Intent
        startActivityForResult(intent, PICK_IMAGE_REQUEST_SITE1);
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

    private void showPermissionsAlert() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Permissions required!")
                .setMessage("Camera needs few permissions to work properly. Grant them in settings.")
                .setPositiveButton("GOTO SETTINGS", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        CameraUtils.openSettings(RMU_MP.this);
                    }
                })
                .setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).show();
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
            }

        }

    }

    @Override
    public void onBackPressed() {
        Intent backhomeintent = new Intent(getApplicationContext(), UploadAll.class);
        startActivity(backhomeintent);
    }

    private void previewCapturedImage1() {
        try {
            // hide video preview


            RmuimageOne.setVisibility(View.VISIBLE);

            bitmap1 = CameraUtils.optimizeBitmap(BITMAP_SAMPLE_SIZE, imageStoragePathS);

            RmuimageOne.setImageBitmap(bitmap1);

        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    public String imageTOString(Bitmap bitmap) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
        byte[] imageBytes = outputStream.toByteArray();
        String encodeImage1 = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodeImage1;

    }

    public void UploadRmu(String Api_Rmu) {
        pb.show();
        if (bitmap1 == null) {
            photormu = "noimage";

        } else {
            photormu = imageTOString(bitmap1);

        }
        StringRequest stringRequest = new StringRequest(Request.Method.POST, rmu_mp_api, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject obj = new JSONObject(response);
                    //  Toast.makeText(getApplicationContext(),response,Toast.LENGTH_LONG).show();
                    String message = obj.getString("error");
                    if (message.equals("false")) {
                        pb.dismiss();
                        dialog.findViewById(R.id.clickmedismiss).setOnClickListener(new View.OnClickListener() {
                            @SuppressLint("ResourceAsColor")
                            public void onClick(View v) {
                                Toast.makeText(RMU_MP.this, "Saved", Toast.LENGTH_SHORT).show();
                                Intent i = new Intent(RMU_MP.this, UploadAll.class);
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
                        Toast.makeText(getApplicationContext(), "Upload failed", Toast.LENGTH_LONG).show();
                    }

                } catch (Exception e) {
                    pb.dismiss();
                    save_local();
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                pb.dismiss();
                save_local();

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("photo1", photormu);
                params.put("lat", lat);
                params.put("eng_id",eng_id);
                params.put("lon", lon);
                params.put("reg_no", sregnnumber);
                params.put("datetime", getDateTime());
                params.put("new_controler_rms_id", rmunewnumber);


                return params;
            }
        };

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(10000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        stringRequest.setShouldCache(false);
        MySingleton.getInstance(getApplicationContext()).addTorequestque(stringRequest);


    }


    void getLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);


        } else {
            locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
            isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
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

    public void save_local() {
        try {
            pb.dismiss();
        }
        catch (Exception e)
        {

        }

        if (bitmap1 == null) {
            photormu = "noimage";

        } else {
            photormu = imageTOString(bitmap1);

        }
        Rmu_Table_MP rmu_table_mp = new Rmu_Table_MP();
        rmu_table_mp.fotoRMu = String.valueOf(photormu);
        rmu_table_mp.NewRMuNO = String.valueOf(rmunewnumber);
        rmu_table_mp.regn_no_rmu = String.valueOf(sregnnumber);
        rmu_table_mp.latrmu = lat;
        rmu_table_mp.eng_id= eng_id;
        rmu_table_mp.lonrmu = lon;
        rmu_table_mp.datetime = getDateTime();
        rmu_table_mp.save();
        Localdialog.findViewById(R.id.clickmedismissf).setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            public void onClick(View v) {
                Toast.makeText(RMU_MP.this, "Saved", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(RMU_MP.this, UploadAll.class);
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
