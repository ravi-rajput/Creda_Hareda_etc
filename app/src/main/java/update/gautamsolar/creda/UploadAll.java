package update.gautamsolar.creda;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.os.Looper;
import android.preference.PreferenceManager;
import android.provider.Settings;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import update.gautamsolar.creda.BluetoothSPP.Device_Update_list;
import update.gautamsolar.creda.Creda_MP.CMCButtons;
import update.gautamsolar.creda.Creda_MP.DeviceLIst_MP;
import update.gautamsolar.creda.Creda_MP.Foundation_MP;
import update.gautamsolar.creda.Creda_MP.Installation_MP;
import update.gautamsolar.creda.Creda_MP.Material_Dispatch_MP;
import update.gautamsolar.creda.Creda_MP.SiteSurvey_MP;
import update.gautamsolar.creda.Creda_Rajasthan.Device_list_rajastha;
import update.gautamsolar.creda.Creda_Rajasthan.Foundation_Rajasthan;
import update.gautamsolar.creda.Creda_Rajasthan.Installation_Rajasthan;
import update.gautamsolar.creda.Creda_Rajasthan.Material_Dispatch_Rajasthan;
import update.gautamsolar.creda.Creda_Rajasthan.Site_Survey_Rajasthan;

import update.gautamsolar.creda.R;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.karumi.dexter.BuildConfig;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.io.File;
import java.text.DateFormat;
import java.util.Date;

public class UploadAll extends AppCompatActivity {


    LinearLayout new_installation,CMCButton,foundation, mtrDetail, farmerPhoto, sitesurvey, RmuInstallation,pit_submit,f_material;
    Intent intent;
    TextView textViewname, textViewrole, textViewproject, textViewdist, textViewcontact;
    CredaModel credaModel;
    Dialog Localdialog;
CardView pit_card;
    SharedPreferences settings;
    String project, sregnnumber, sbenifname, sfname, scontact, svillage, sblock, spumpType, sinstallation_status, sbeneficiary_share, ssurvey_status, sfoundation_status,rmu_status;
    String water_level, bor_size, bor_depth, existing_moter_run, spump_type, sdispatch_status, spump_capacity,pit_status, sengineer_role, sKEYPHOTO1, sFKEYPHOTO6, sFKEYPHOTO7, sKEYPHOTO2, sKEYPHOTO3, sKEYPHOTO4, sKEYPHOTO5, sKEYPHOTO6, sKEYPHOTO7, sFKEYPHOTO1,
            sFKEYPHOTO2, sFKEYPHOTO3, sFKEYPHOTO4, sFKEYPHOTO5, sSKEYPHOTO1, sSKEYPHOTO2, sSKEYPHOTO3, sSKEYPHOTO4,sSKEYPHOTO5
            ,sSKEYPHOTO6,sSKEYPHOTO7,sSKEYPHOTO8;

    String engineer_role,bor_status, cregnnumber,adhar_no,bank_name,bank_account_no,ifsc_code,pump_head,regnnumber, benifname, fname, contact, village, block, pumpType, installation_status, beneficiary_share, survey_status, foundation_status;
    int REQUEST_CHECK_SETTINGS = 100;
    public static double latitude=0.0, longitude=0.0;
    private static final String TAG = MainActivity.class.getSimpleName();
    // location last updated time
    private String mLastUpdateTime;

    // location updates interval - 10sec
    private static final long UPDATE_INTERVAL_IN_MILLISECONDS = 10000;

    // fastest updates interval - 5 sec
    // location updates will be received if another app is requesting the locations
    // than your app can handle
    private static final long FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS = 5000;
    private static final int TIME_INTERVAL = 2000; // # milliseconds, desired time passed between two back presses.
    private long mBackPressed;

    // bunch of location related apis
    private FusedLocationProviderClient mFusedLocationClient;
    private SettingsClient mSettingsClient;
    private LocationRequest mLocationRequest;
    private LocationSettingsRequest mLocationSettingsRequest;
    private LocationCallback mLocationCallback;
    private Location mCurrentLocation;
    // boolean flag to toggle the ui
    private Boolean mRequestingLocationUpdates;
    Button saral,structure_plus,hareda_update,hareda3InformaticRes;
    private static UploadAll instance;
    @Override
    public void onBackPressed() {
        Intent backintent = new Intent(getApplicationContext(), BenificiaryListitem.class);
        startActivity(backintent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_all);

        instance = this;
        init();
saral = findViewById(R.id.saral);
        structure_plus = findViewById(R.id.structure_plus);
        foundation = findViewById(R.id.siteSurveyf);
        pit_submit = findViewById(R.id.pit_submit);
        hareda_update = findViewById(R.id.hareda_update);
        hareda3InformaticRes = findViewById(R.id.hareda3InformaticRes);
        new_installation = findViewById(R.id.new_installation);
        mtrDetail = findViewById(R.id.mtrDetail);
        pit_card=findViewById(R.id.pit_card);
        f_material=findViewById(R.id.f_material);
        credaModel = new CredaModel();
        Intent i= getIntent();
        CMCButton=findViewById(R.id.cmcbutton);
        settings = PreferenceManager.getDefaultSharedPreferences(this);
        project = settings.getString("project", "");
        hareda_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent(UploadAll.this,QuarterListActivity.class);
                startActivity(i);
            }
        });
        if(settings.getString("lead_phase","").equalsIgnoreCase("HAREDA_PHASE3")||settings.getString("lead_phase","").equalsIgnoreCase("HAREDA_PHASE4")) {
            hareda3InformaticRes.setVisibility(View.VISIBLE);
        }
            hareda3InformaticRes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent(UploadAll.this,ImageResolution.class);
                startActivity(i);
            }
        });

        structure_plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i= new Intent(UploadAll.this,StructureActivity.class);
                i.putExtra("regnnumber",settings.getString("regnnumber", ""));
                i.putExtra("benifname",settings.getString("benifname", ""));
                i.putExtra("fathername",settings.getString("fname", ""));
                i.putExtra("contact",settings.getString("contact", ""));
                i.putExtra("village",settings.getString("village", ""));
                i.putExtra("block",settings.getString("block", ""));
                i.putExtra("image1",settings.getString("purlin_image", ""));
                i.putExtra("image2",settings.getString("refter_image", ""));
                i.putExtra("image3",settings.getString("allPanel_image", ""));
                i.putExtra("image4",settings.getString("paani_image", ""));
                i.putExtra("status",settings.getString("structureStatus", ""));
                i.putExtra("video",settings.getString("structureVideo", ""));
                startActivity(i);
                finish();
            }
        });
        saral.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i= new Intent(UploadAll.this,SaralActivity.class);
                    i.putExtra("regnnumber",settings.getString("regnnumber", ""));
                    i.putExtra("benifname",settings.getString("benifname", ""));
                    i.putExtra("fathername",settings.getString("fname", ""));
                    i.putExtra("contact",settings.getString("contact", ""));
                    i.putExtra("village",settings.getString("village", ""));
                    i.putExtra("block",settings.getString("block", ""));
                    i.putExtra("saralid",settings.getString("saralid", ""));
                    i.putExtra("saralyear",settings.getString("saralyear", ""));
                    startActivity(i);
                }
            });

        if (project.equals("RAJASTHAN")) {
            getSupportActionBar().setTitle("Pump Installation RAJASTHAN");
            sitesurvey = findViewById(R.id.sitesurvey);
            farmerPhoto = findViewById(R.id.farmerPhoto);
            RmuInstallation = findViewById(R.id.installrmubluetooth);
            intent = getIntent();
            pit_card.setVisibility(View.GONE);
            settings = PreferenceManager.getDefaultSharedPreferences(this);
            sregnnumber = settings.getString("regnnumber", "");
            sbenifname = settings.getString("benifname", "");
            sfname = settings.getString("fname", "");
            scontact = settings.getString("contact", "");
            svillage = settings.getString("village", "");
            sblock = settings.getString("block", "");
            spumpType = settings.getString("pumpType", "");

            sinstallation_status = settings.getString("installation_status", "");

            ssurvey_status = settings.getString("survey_status", "");
            sfoundation_status = settings.getString("foundation_status", "");
            spump_type = settings.getString("pump_capacity", "");
            sdispatch_status = settings.getString("dispatch_status", "");
            spump_capacity = settings.getString("pump_capacity", "");
            sKEYPHOTO1 = settings.getString("KEYPHOTO1", "");
            sKEYPHOTO2 = settings.getString("KEYPHOTO2", "");
            sKEYPHOTO3 = settings.getString("KEYPHOTO3", "");
            sKEYPHOTO4 = settings.getString("KEYPHOTO4", "");
            sFKEYPHOTO1 = settings.getString("FKEYPHOTO1", "");
            sFKEYPHOTO2 = settings.getString("FKEYPHOTO2", "");
            sFKEYPHOTO3 = settings.getString("FKEYPHOTO3", "");
            sFKEYPHOTO4 = settings.getString("FKEYPHOTO4", "");
            sSKEYPHOTO1 = settings.getString("SKEYPHOTO1", "");
            sSKEYPHOTO2 = settings.getString("SKEYPHOTO2", "");
            sSKEYPHOTO3 = settings.getString("SKEYPHOTO3", "");
            sSKEYPHOTO4 = settings.getString("SKEYPHOTO4", "");
            water_level = settings.getString("water_level", "");
            bor_size = settings.getString("bor_size", "");
            bor_depth = settings.getString("bor_depth", "");
            bor_status = settings.getString("bor_status", "");

            Toast.makeText(getApplicationContext(),sregnnumber,Toast.LENGTH_LONG).show();
            existing_moter_run = settings.getString("existing_moter_run", "");

            engineer_role = SharedPrefManager.getInstance(getApplicationContext()).getUserRole();


            farmerPhoto.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (sinstallation_status.equals("Complete")) {

                        Toast.makeText(getApplicationContext(), "Installation has already been completed", Toast.LENGTH_SHORT).show();

                    } else {
                        intent = new Intent(getApplicationContext(), Installation_Rajasthan.class);
                        Bundle bundleUploadB = new Bundle();
                        bundleUploadB.putString("benifname", sbenifname);
                        bundleUploadB.putString("regnnumber", sregnnumber);
                        bundleUploadB.putString("fathername", sfname);
                        bundleUploadB.putString("contact", scontact);
                        bundleUploadB.putString("block", sblock);
                        bundleUploadB.putString("village", svillage);
                        bundleUploadB.putString("installation_status", sinstallation_status);
                        bundleUploadB.putString("instimg1", sKEYPHOTO1);
                        bundleUploadB.putString("instimg2", sKEYPHOTO2);
                        bundleUploadB.putString("instimg3", sKEYPHOTO3);
                        bundleUploadB.putString("instimg4", sKEYPHOTO4);
                        intent.putExtras(bundleUploadB);
                        startActivity(intent);
                    }

                }
            });


            foundation.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    if (sfoundation_status.equals("Complete")) {
                        Toast.makeText(getApplicationContext(), "Foundation has already been completed", Toast.LENGTH_SHORT).show();


                    } else {
                        intent = new Intent(getApplicationContext(), Foundation_Rajasthan.class);
                        Bundle bundlef = new Bundle();
                        bundlef.putString("benifname", sbenifname);
                        bundlef.putString("regnnumber", sregnnumber);
                        bundlef.putString("fathername", sfname);
                        bundlef.putString("contact", scontact);
                        bundlef.putString("block", sblock);
                        bundlef.putString("village", svillage);
                        bundlef.putString("foundation_status", sfoundation_status);

                        bundlef.putString("fondimg1", sFKEYPHOTO1);
                        bundlef.putString("fondimg2", sFKEYPHOTO2);
                        bundlef.putString("fondimg3", sFKEYPHOTO3);
                        bundlef.putString("fondimg4", sFKEYPHOTO4);

                        intent.putExtras(bundlef);
                        startActivity(intent);
                    }

                }
            });


            mtrDetail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    intent = new Intent(getApplicationContext(), Material_Dispatch_Rajasthan.class);
                    Bundle bundlepump = new Bundle();
                    bundlepump.putString("pump_type", spumpType);
                    bundlepump.putString("pump_capacity", spump_capacity);
                    bundlepump.putString("benifname", sbenifname);
                    bundlepump.putString("regnnumber", sregnnumber);
                    bundlepump.putString("fathername", sfname);
                    bundlepump.putString("contact", scontact);
                    bundlepump.putString("block", sblock);
                    bundlepump.putString("village", svillage);
                    bundlepump.putString("dispatch_status", sdispatch_status);


                    intent.putExtras(bundlepump);
                    startActivity(intent);


                }
            });
            RmuInstallation.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(UploadAll.this, Device_list_rajastha.class);
                    startActivity(intent);
                }
            });

            sitesurvey.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        if (ssurvey_status.equals("Complete")) {

                            Toast.makeText(getApplicationContext(), "Survey has already been completed", Toast.LENGTH_SHORT).show();


                        } else {
                            intent = new Intent(getApplicationContext(), Site_Survey_Rajasthan.class);
                            Bundle bundlesuvey = new Bundle();
                            bundlesuvey.putString("benifname", sbenifname);
                            bundlesuvey.putString("regnnumber", sregnnumber);
                            bundlesuvey.putString("fathername", sfname);
                            bundlesuvey.putString("contact", scontact);
                            bundlesuvey.putString("block", sblock);
                            bundlesuvey.putString("village", svillage);
                            bundlesuvey.putString("survey_status", ssurvey_status);
                            bundlesuvey.putString("adharimg", sSKEYPHOTO1);
                            bundlesuvey.putString("passbookimg", sSKEYPHOTO2);
                            bundlesuvey.putString("sidemarkimg", sSKEYPHOTO3);
                            bundlesuvey.putString("benifimg", sSKEYPHOTO4);
                            bundlesuvey.putString("water_level", water_level);

                            bundlesuvey.putString("bor_size", bor_size);
                            bundlesuvey.putString("bor_status", bor_status);
                            bundlesuvey.putString("bor_depth", bor_depth);
                            bundlesuvey.putString("existing_moter_run", existing_moter_run);
                             intent.putExtras(bundlesuvey);
                            startActivity(intent);
                        }

                    } catch (Exception e) {
                        intent = new Intent(getApplicationContext(), Site_Survey_Rajasthan.class);
                        startActivity(intent);
                           e.printStackTrace();
                    }


                }
            });

        }

        else if (project.equals("CREDA")||project.equals("HAREDA")||project.equals("MSKPY")||project.equals("PEDA")||project.equals("MSEDCL")||project.equals("MEDA")) {
            getSupportActionBar().setTitle("Pump Installation");
            sitesurvey = findViewById(R.id.sitesurvey);
            farmerPhoto = findViewById(R.id.farmerPhoto);
            f_material.setVisibility(View.VISIBLE);
            RmuInstallation = findViewById(R.id.installrmubluetooth);
            saral.setVisibility(View.VISIBLE);
            if(settings.getString("lead_phase","").equalsIgnoreCase("HAREDA_PHASE4")){
                structure_plus.setVisibility(View.VISIBLE);
            }
            intent = getIntent();
            cregnnumber = settings.getString("regnnumber", "");
            pit_card.setVisibility(View.VISIBLE);
            sbenifname = settings.getString("benifname", "");
            sfname = settings.getString("fname", "");
            scontact = settings.getString("contact", "");
            svillage = settings.getString("village", "");
            sblock = settings.getString("block", "");
            spumpType = settings.getString("pumpType", "");
            sinstallation_status = settings.getString("installation_status", "");

            ssurvey_status = settings.getString("survey_status", "");
            sfoundation_status = settings.getString("foundation_status", "");
            spump_type = settings.getString("pump_capacity", "");
            sdispatch_status = settings.getString("dispatch_status", "");
            spump_capacity = intent.getStringExtra("pump_capacity");
            pit_status=settings.getString("pit_status","");
            sKEYPHOTO1 = settings.getString("KEYPHOTO1", "");
            sKEYPHOTO2 = settings.getString("KEYPHOTO2", "");
            sKEYPHOTO3 = settings.getString("KEYPHOTO3", "");
            sKEYPHOTO4 = settings.getString("KEYPHOTO4", "");
            sKEYPHOTO5 = settings.getString("KEYPHOTO5", "");
            sKEYPHOTO6 = settings.getString("KEYPHOTO6", "");
            sKEYPHOTO7 = settings.getString("KEYPHOTO7", "");
            sFKEYPHOTO1 = settings.getString("FKEYPHOTO1", "");
            sFKEYPHOTO2 = settings.getString("FKEYPHOTO2", "");
            sFKEYPHOTO3 = settings.getString("FKEYPHOTO3", "");
            sFKEYPHOTO4 = settings.getString("FKEYPHOTO4", "");
            sFKEYPHOTO5 = settings.getString("FKEYPHOTO5", "");
            sSKEYPHOTO1 = settings.getString("SKEYPHOTO1", "");
            sSKEYPHOTO2 = settings.getString("SKEYPHOTO2", "");
            sSKEYPHOTO3 = settings.getString("SKEYPHOTO3", "");
            sSKEYPHOTO4 = settings.getString("SKEYPHOTO4", "");
            sSKEYPHOTO5 = settings.getString("SKEYPHOTO5", "");
            sSKEYPHOTO6 = settings.getString("SKEYPHOTO6", "");
            sSKEYPHOTO7 = settings.getString("SKEYPHOTO7", "");
            sSKEYPHOTO8 = settings.getString("SKEYPHOTO8", "");
            water_level = settings.getString("water_level", "");
            bor_size = settings.getString("bor_size", "");
            bor_depth = settings.getString("bor_depth", "");
            bor_status = settings.getString("bor_status", "");
            existing_moter_run = settings.getString("existing_moter_run", "");
            engineer_role = SharedPrefManager.getInstance(getApplicationContext()).getUserRole();
            Log.d("foundation_ma",settings.getString("rate", "")+
                    settings.getString("rode", "")+settings.getString("saria_status", ""));
f_material.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {

        Intent bundleFoundation=new Intent(UploadAll.this,Foundation_Material.class);
//        Bundle bundleFoundation = new Bundle();
        bundleFoundation.putExtra("benifname", sbenifname);
        bundleFoundation.putExtra("regnnumber", cregnnumber);
        bundleFoundation.putExtra("fathername", sfname);
        bundleFoundation.putExtra("contact", scontact);
        bundleFoundation.putExtra("block", sblock);
        bundleFoundation.putExtra("village", svillage);

        bundleFoundation.putExtra("rate", settings.getString("rate", ""));
        bundleFoundation.putExtra("rode", settings.getString("road", ""));
        bundleFoundation.putExtra("saria_status", settings.getString("saria", ""));
        startActivity(bundleFoundation);
    }
});

if(project.equals("PEDA")){
    new_installation.setVisibility(View.VISIBLE);
}else{
    new_installation.setVisibility(View.GONE);
}

            new_installation.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i= new Intent(UploadAll.this,NewInstalationActivity.class);
                    i.putExtra("reg_no",settings.getString("regnnumber", ""));
                    i.putExtra("name",settings.getString("benifname", ""));
                    i.putExtra("fname",settings.getString("fname", ""));
                    i.putExtra("contact",settings.getString("contact", ""));
                    i.putExtra("village",settings.getString("village", ""));
                    i.putExtra("block",settings.getString("block", ""));
                    i.putExtra("saralid",settings.getString("saralid", ""));
                    i.putExtra("saralyear",settings.getString("saralyear", ""));
                    startActivity(i);
                }
            });

            pit_submit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (pit_status.equals("Complete")) {
                        Toast.makeText(getApplicationContext(), "Foundation has already been completed", Toast.LENGTH_SHORT).show();


                    } else {
                        intent = new Intent(getApplicationContext(), Activity_Pit.class);
                        intent.putExtra("name", sbenifname);
                        intent.putExtra("fname", sfname);
                        intent.putExtra("reg_no", cregnnumber);
                        intent.putExtra("block", sblock);
                        intent.putExtra("village", svillage);
                        intent.putExtra("contact", scontact);

                        startActivity(intent);
                    }

                }
            });

            farmerPhoto.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (sinstallation_status.equals("Complete")) {

                        Toast.makeText(getApplicationContext(), "Installation has already been completed", Toast.LENGTH_SHORT).show();

                    } else {
                        intent = new Intent(getApplicationContext(), UploadBenificieryPic.class);
                        Bundle bundleUploadB = new Bundle();
                        bundleUploadB.putString("benifname", sbenifname);

                        bundleUploadB.putString("regnnumber", cregnnumber);
                        bundleUploadB.putString("fathername", sfname);
                        bundleUploadB.putString("contact", scontact);
                        bundleUploadB.putString("block", sblock);
                        bundleUploadB.putString("village", svillage);
                        bundleUploadB.putString("installation_status", sinstallation_status);
                        bundleUploadB.putString("instimg1", sKEYPHOTO1);
                        bundleUploadB.putString("instimg2", sKEYPHOTO2);
                        bundleUploadB.putString("instimg3", sKEYPHOTO3);
                        bundleUploadB.putString("instimg4", sKEYPHOTO4);
                        bundleUploadB.putString("instimg5", sKEYPHOTO5);
                        bundleUploadB.putString("instimg6", sKEYPHOTO6);
                        bundleUploadB.putString("instimg7", sKEYPHOTO7);
                        bundleUploadB.putString("fondimg5", sFKEYPHOTO5);
                        bundleUploadB.putString("pic_date", settings.getString("pic_date", ""));
                        intent.putExtras(bundleUploadB);
                        startActivity(intent);
                    }

                }
            });


            foundation.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    if (sfoundation_status.equals("Complete")) {
                        Toast.makeText(getApplicationContext(), "Foundation has already been completed", Toast.LENGTH_SHORT).show();


                    } else {
                        intent = new Intent(getApplicationContext(), FoundationActivity.class);
                        Bundle bundlef = new Bundle();
                        bundlef.putString("benifname", sbenifname);
                        bundlef.putString("regnnumber", cregnnumber);
                        bundlef.putString("fathername", sfname);

                        bundlef.putString("contact", scontact);
                        bundlef.putString("block", sblock);
                        bundlef.putString("village", svillage);
                        bundlef.putString("foundation_status", sfoundation_status);

                        bundlef.putString("fondimg1", sFKEYPHOTO1);
                        bundlef.putString("fondimg2", sFKEYPHOTO2);
                        bundlef.putString("fondimg3", sFKEYPHOTO3);
                        bundlef.putString("fondimg4", sFKEYPHOTO4);
                        bundlef.putString("fondimg5", sFKEYPHOTO5);
                        bundlef.putString("pic_date", settings.getString("pic_date", ""));


                        intent.putExtras(bundlef);
                        startActivity(intent);
                    }

                }
            });


            mtrDetail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    intent = new Intent(getApplicationContext(), Material.class);
                    Bundle bundlepump = new Bundle();
                    bundlepump.putString("pump_type", spumpType);
                    bundlepump.putString("pump_capacity", spump_type);
                    bundlepump.putString("benifname", sbenifname);
                    bundlepump.putString("regnnumber", cregnnumber);
                    bundlepump.putString("fathername", sfname);
                    bundlepump.putString("contact", scontact);
                    bundlepump.putString("block", sblock);
                    bundlepump.putString("village", svillage);
                    bundlepump.putString("dispatch_status", sdispatch_status);

                    intent.putExtras(bundlepump);
                    startActivity(intent);


                }
            });
            RmuInstallation.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(UploadAll.this, Device_Update_list.class);
                    startActivity(intent);
                }
            });

            sitesurvey.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    try {
                        if (ssurvey_status.equals("Complete")) {

                            Toast.makeText(getApplicationContext(), "Survey has already been completed", Toast.LENGTH_SHORT).show();


                        } else {
                            intent = new Intent(getApplicationContext(), SiteSurvey.class);
                            Bundle bundlesuvey = new Bundle();
                            bundlesuvey.putString("benifname", sbenifname);
                            bundlesuvey.putString("regnnumber", cregnnumber);
                            bundlesuvey.putString("fathername", sfname);
                            bundlesuvey.putString("contact", scontact);
                            bundlesuvey.putString("bor_status", bor_status);
                            bundlesuvey.putString("block", sblock);
                            bundlesuvey.putString("village", svillage);
                            bundlesuvey.putString("survey_status", ssurvey_status);
                            bundlesuvey.putString("adharimg", sSKEYPHOTO1);
                            bundlesuvey.putString("passbookimg", sSKEYPHOTO2);
                            bundlesuvey.putString("sidemarkimg", sSKEYPHOTO3);
                            bundlesuvey.putString("benifimg", sSKEYPHOTO4);
                            bundlesuvey.putString("site_format", sSKEYPHOTO5);
                            bundlesuvey.putString("aadhar_back", sSKEYPHOTO6);
                            bundlesuvey.putString("boaring_image", sSKEYPHOTO7);
                            bundlesuvey.putString("survay2", sSKEYPHOTO8);
                            bundlesuvey.putString("water_level", water_level);
                            bundlesuvey.putString("bor_size",bor_size );
                            bundlesuvey.putString("bor_depth", bor_depth);
                            bundlesuvey.putString("existing_moter_run", existing_moter_run);
                            intent.putExtras(bundlesuvey);
                            startActivity(intent);
                        }

                    } catch (Exception e) {
                        intent = new Intent(getApplicationContext(), SiteSurvey.class);
                        startActivity(intent);
                         e.printStackTrace();
                    }


                }
            });
        }
        else if (project.equals("MP")) {
            getSupportActionBar().setTitle("Pump Installation MP");
            sitesurvey = findViewById(R.id.sitesurvey);
            farmerPhoto = findViewById(R.id.farmerPhoto);
            RmuInstallation = findViewById(R.id.installrmubluetooth);
            intent = getIntent();
            pit_card.setVisibility(View.GONE);
            sregnnumber = settings.getString("regnnumber", "");
            sbenifname = settings.getString("benifname", "");
            rmu_status = settings.getString("rmu_status", "");

            //Toast.makeText(getApplicationContext(),sregnnumber,Toast.LENGTH_LONG).show();
            sfname = settings.getString("fname", "");
            scontact = settings.getString("contact", "");
            svillage = settings.getString("village", "");
            sblock = settings.getString("block", "");
            spumpType = settings.getString("pumpType", "");
            sinstallation_status = settings.getString("installation_status", "");
            ssurvey_status = settings.getString("survey_status", "");
            sfoundation_status = settings.getString("foundation_status", "");
            spump_type = settings.getString("pump_capacity", "");
            sdispatch_status = settings.getString("dispatch_status", "");
            spump_capacity = intent.getStringExtra("pump_capacity");
            sKEYPHOTO1 = settings.getString("KEYPHOTO1", "");
            sKEYPHOTO2 = settings.getString("KEYPHOTO2", "");
            sKEYPHOTO3 = settings.getString("KEYPHOTO3", "");
            sKEYPHOTO4 = settings.getString("KEYPHOTO4", "");
            sKEYPHOTO5 = settings.getString("KEYPHOTO5", "");
            sKEYPHOTO6 = settings.getString("KEYPHOTO6", "");
            sKEYPHOTO7 = settings.getString("KEYPHOTO7", "");
            sFKEYPHOTO1 = settings.getString("FKEYPHOTO1", "");
            sFKEYPHOTO2 = settings.getString("FKEYPHOTO2", "");
            sFKEYPHOTO3 = settings.getString("FKEYPHOTO3", "");
            sFKEYPHOTO4 = settings.getString("FKEYPHOTO4", "");
            sFKEYPHOTO5 = settings.getString("FKEYPHOTO5", "");
            sFKEYPHOTO6 = settings.getString("FKEYPHOTO6", "");
            sFKEYPHOTO7 = settings.getString("FKEYPHOTO7", "");
            sSKEYPHOTO1 = settings.getString("SKEYPHOTO1", "");
            sSKEYPHOTO2 = settings.getString("SKEYPHOTO2", "");
            sSKEYPHOTO3 = settings.getString("SKEYPHOTO3", "");
            sSKEYPHOTO4 = settings.getString("SKEYPHOTO4", "");
            water_level = settings.getString("water_level", "");
            bor_size = settings.getString("bor_size", "");
            bor_depth = settings.getString("bor_depth", "");
            existing_moter_run = settings.getString("existing_moter_run", "");
            adhar_no = settings.getString("adhar_no", "");
            bank_name = settings.getString("bank_name", "");
            bor_status = settings.getString("bor_status", "");
            bank_account_no = settings.getString("bank_account_no", "");
            ifsc_code = settings.getString("ifsc_code", "");
            pump_head = settings.getString("pump_head", "");
            engineer_role = SharedPrefManager.getInstance(getApplicationContext()).getUserRole();


            farmerPhoto.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (sinstallation_status.equals("Complete")) {

                        Toast.makeText(getApplicationContext(), "Installation has already been completed", Toast.LENGTH_SHORT).show();

                    } else {
                        intent = new Intent(getApplicationContext(), Installation_MP.class);
                        Bundle bundleUploadB = new Bundle();
                        bundleUploadB.putString("benifname", sbenifname);
                        bundleUploadB.putString("rmu_status", rmu_status);
                        bundleUploadB.putString("regnnumber", sregnnumber);
                        bundleUploadB.putString("fathername", sfname);
                        bundleUploadB.putString("contact", scontact);
                        bundleUploadB.putString("block", sblock);
                        bundleUploadB.putString("village", svillage);
                        bundleUploadB.putString("installation_status", sinstallation_status);
                        bundleUploadB.putString("instimg1", sKEYPHOTO1);
                        bundleUploadB.putString("instimg2", sKEYPHOTO2);
                        bundleUploadB.putString("instimg3", sKEYPHOTO3);
                        bundleUploadB.putString("instimg4", sKEYPHOTO4);
                        intent.putExtras(bundleUploadB);
                        startActivity(intent);
                    }

                }
            });


            foundation.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    if (sfoundation_status.equals("Complete")) {
                        Toast.makeText(getApplicationContext(), "Foundation has already been completed", Toast.LENGTH_SHORT).show();


                    } else {
                        intent = new Intent(getApplicationContext(), Foundation_MP.class);
                        Bundle bundlef = new Bundle();
                        bundlef.putString("benifname", sbenifname);
                        bundlef.putString("regnnumber", sregnnumber);
                        bundlef.putString("fathername", sfname);
                        bundlef.putString("contact", scontact);
                        bundlef.putString("block", sblock);
                        bundlef.putString("village", svillage);
                        bundlef.putString("foundation_status", sfoundation_status);

                        bundlef.putString("fondimg1", sFKEYPHOTO1);
                        bundlef.putString("fondimg2", sFKEYPHOTO2);
                        bundlef.putString("fondimg3", sFKEYPHOTO3);
                        bundlef.putString("fondimg4", sFKEYPHOTO4);
                        bundlef.putString("fondimg5", sFKEYPHOTO5);
                        bundlef.putString("fondimg6", sFKEYPHOTO6);
                        bundlef.putString("fondimg7", sFKEYPHOTO7);


                        intent.putExtras(bundlef);
                        startActivity(intent);
                    }

                }
            });


            mtrDetail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    intent = new Intent(getApplicationContext(), Material_Dispatch_MP.class);
                    Bundle bundlepump = new Bundle();
                    bundlepump.putString("pump_type", spumpType);
                    bundlepump.putString("pump_capacity", spump_type);
                    bundlepump.putString("benifname", sbenifname);
                    bundlepump.putString("regnnumber", sregnnumber);
                    bundlepump.putString("fathername", sfname);
                    bundlepump.putString("contact", scontact);
                    bundlepump.putString("block", sblock);
                    bundlepump.putString("village", svillage);
                    bundlepump.putString("dispatch_status", sdispatch_status);
                    intent.putExtras(bundlepump);
                    startActivity(intent);


                }
            });
            RmuInstallation.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(UploadAll.this, DeviceLIst_MP.class);
                    startActivity(intent);
                }
            });

            sitesurvey.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (ssurvey_status.equals("Complete")) {

                        Toast.makeText(getApplicationContext(), "Survey has already been completed", Toast.LENGTH_SHORT).show();


                    } else {
                        intent = new Intent(getApplicationContext(), SiteSurvey_MP.class);
                        Bundle bundlesuvey = new Bundle();
                        bundlesuvey.putString("benifname", sbenifname);
                        bundlesuvey.putString("regnnumber", sregnnumber);
                        bundlesuvey.putString("fathername", sfname);
                        bundlesuvey.putString("contact", scontact);
                        bundlesuvey.putString("block", sblock);
                        bundlesuvey.putString("village", svillage);
                        bundlesuvey.putString("survey_status", ssurvey_status);
                        bundlesuvey.putString("adharimg", sSKEYPHOTO1);
                        bundlesuvey.putString("passbookimg", sSKEYPHOTO2);
                        bundlesuvey.putString("sidemarkimg", sSKEYPHOTO3);
                        bundlesuvey.putString("benifimg", sSKEYPHOTO4);
                        bundlesuvey.putString("water_level", water_level);
                        bundlesuvey.putString("bor_status", bor_status);
                        bundlesuvey.putString("bor_size", bor_size);
                        bundlesuvey.putString("bor_depth", bor_depth);
                        bundlesuvey.putString("existing_moter_run", existing_moter_run);
                        bundlesuvey.putString("water_level", water_level);
                        bundlesuvey.putString("bor_size", bor_size);
                        bundlesuvey.putString("bor_depth", bor_depth);
                        bundlesuvey.putString("existing_moter_run", existing_moter_run);
                        bundlesuvey.putString("adhar_no", adhar_no);
                        bundlesuvey.putString("bank_name", bank_name);
                        bundlesuvey.putString("bank_account_no", bank_account_no);
                        bundlesuvey.putString("ifsc_code", ifsc_code);
                        bundlesuvey.putString("pump_head", pump_head);
                        intent.putExtras(bundlesuvey);
                        startActivity(intent);
                    }


                }
            });
            CMCButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent cmcbutton= new Intent(getApplicationContext(), CMCButtons.class);
                    Bundle bundlecmc = new Bundle();
                    bundlecmc.putString("benifname", sbenifname);
                    bundlecmc.putString("regnnumber", sregnnumber);
                    bundlecmc.putString("fathername", sfname);
                    bundlecmc.putString("contact", scontact);
                    bundlecmc.putString("block", sblock);
                    bundlecmc.putString("village", svillage);
                    cmcbutton.putExtras(bundlecmc);
                    startActivity(cmcbutton);

                }
            });

        }

        restoreValuesFromBundle(savedInstanceState);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.logoutpage, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.profile:

                Localdialog = new Dialog(UploadAll.this);
                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
                String username = sharedPreferences.getString("username", "");
                String contact = sharedPreferences.getString("contact", "");
                String district = sharedPreferences.getString("district", "");
                String project = sharedPreferences.getString("project", "");
                String role = sharedPreferences.getString("role", "");
                Localdialog.setContentView(R.layout.profile);
                Localdialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                Localdialog.setCancelable(false);
                textViewname = Localdialog.findViewById(R.id.nametextdialog);
                textViewcontact = Localdialog.findViewById(R.id.textdialogcontact);
                textViewrole = Localdialog.findViewById(R.id.textdialogdesg);
                textViewproject = Localdialog.findViewById(R.id.textdialogproject);
                textViewdist = Localdialog.findViewById(R.id.textdialogdist);


                textViewname.setText(username);
                textViewcontact.setText(contact);
                textViewrole.setText(role);
                textViewproject.setText(project);
                textViewdist.setText(district);

                Localdialog.findViewById(R.id.clickok).setOnClickListener(new View.OnClickListener() {
                    @SuppressLint("ResourceAsColor")
                    public void onClick(View v) {


                        Localdialog.dismiss();
                    }
                });

                Localdialog.show();
                break;
            case R.id.credalogut:
                AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
                builder1.setTitle("EXIT");
                builder1.setIcon(R.mipmap.gsplcredaicon);
                builder1.setMessage("Are you sure? ");

                builder1.setPositiveButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {


                    }
                });
                builder1.setNegativeButton("Log out", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        SharedPreferences.Editor editor = settings.edit();
                        editor.putString("login_status", "0");
                        editor.commit();
                        clearApplicationData();
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        finish();


                    }
                });
                builder1.show();


                break;

        }
        return super.onOptionsItemSelected(item);
    }

    public void clearApplicationData() {
        File cacheDirectory = getCacheDir();
        File applicationDirectory = new File(cacheDirectory.getParent());
        if (applicationDirectory.exists()) {
            String[] fileNames = applicationDirectory.list();
            for (String fileName : fileNames) {
                if (!fileName.equals("lib")) {
                    deleteFile(new File(applicationDirectory, fileName));
                }
            }
        }
    }
    public static boolean deleteFile(File file) {
        boolean deletedAll = true;
        if (file != null) {
            if (file.isDirectory()) {
                String[] children = file.list();
                for (int i = 0; i < children.length; i++) {
                    deletedAll = deleteFile(new File(file, children[i])) && deletedAll;
                }
            } else {
                deletedAll = file.delete();
            }
        }

        return deletedAll;
    }


    public static UploadAll getInstance() {
        return instance;
    }
    public void init() {
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        mSettingsClient = LocationServices.getSettingsClient(this);

        mLocationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                super.onLocationResult(locationResult);
                // location is received
                mCurrentLocation = locationResult.getLastLocation();
                mLastUpdateTime = DateFormat.getTimeInstance().format(new Date());

                updateLocationUI();
            }
        };

        mRequestingLocationUpdates = false;

        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(UPDATE_INTERVAL_IN_MILLISECONDS);
        mLocationRequest.setFastestInterval(FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder();
        builder.addLocationRequest(mLocationRequest);
        mLocationSettingsRequest = builder.build();

        // Requesting ACCESS_FINE_LOCATION using Dexter library
        Dexter.withActivity(this)
                .withPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse response) {
                        mRequestingLocationUpdates = true;
                        startLocationUpdates();
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse response) {
                        if (response.isPermanentlyDenied()) {
                            // open device settings when the permission is
                            // denied permanently
                            openSettings();
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).check();

    }

    /**
     * Restoring values from saved instance state
     */
    private void restoreValuesFromBundle(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            if (savedInstanceState.containsKey("is_requesting_updates")) {
                mRequestingLocationUpdates = savedInstanceState.getBoolean("is_requesting_updates");
            }

            if (savedInstanceState.containsKey("last_known_location")) {
                mCurrentLocation = savedInstanceState.getParcelable("last_known_location");
            }

            if (savedInstanceState.containsKey("last_updated_on")) {
                mLastUpdateTime = savedInstanceState.getString("last_updated_on");
            }
        }

        updateLocationUI();
    }


    /**
     * Update the UI displaying the location data
     * and toggling the buttons
     */
    private void updateLocationUI() {
        if (mCurrentLocation != null) {
            latitude= mCurrentLocation.getLatitude();
            longitude= mCurrentLocation.getLongitude();



            // location last updated time
//            latlong.setText("Last updated on: " + mLastUpdateTime);
        }

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean("is_requesting_updates", mRequestingLocationUpdates);
        outState.putParcelable("last_known_location", mCurrentLocation);
        outState.putString("last_updated_on", mLastUpdateTime);

    }

    /**
     * Starting location updates
     * Check whether location settings are satisfied and then
     * location updates will be requested
     */
    private void startLocationUpdates() {
        mSettingsClient
                .checkLocationSettings(mLocationSettingsRequest)
                .addOnSuccessListener(this, new OnSuccessListener<LocationSettingsResponse>() {
                    @SuppressLint("MissingPermission")
                    @Override
                    public void onSuccess(LocationSettingsResponse locationSettingsResponse) {
                        Log.i(TAG, "All location settings are satisfied.");

                        //noinspection MissingPermission
                        mFusedLocationClient.requestLocationUpdates(mLocationRequest,
                                mLocationCallback, Looper.myLooper());

                        updateLocationUI();
                    }
                })
                .addOnFailureListener(this, new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        int statusCode = ((ApiException) e).getStatusCode();
                        switch (statusCode) {
                            case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                                Log.i(TAG, "Location settings are not satisfied. Attempting to upgrade " +
                                        "location settings ");
                                try {
                                    // Show the dialog by calling startResolutionForResult(), and check the
                                    // result in onActivityResult().
                                    ResolvableApiException rae = (ResolvableApiException) e;
                                    rae.startResolutionForResult(UploadAll.this, REQUEST_CHECK_SETTINGS);
                                } catch (IntentSender.SendIntentException sie) {
                                    Log.i(TAG, "PendingIntent unable to execute request.");
                                }
                                break;
                            case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                                String errorMessage = "Location settings are inadequate, and cannot be " +
                                        "fixed here. Fix in Settings.";
                                Log.e(TAG, errorMessage);

                                Toast.makeText(UploadAll.this, errorMessage, Toast.LENGTH_LONG).show();
                        }

                        updateLocationUI();
                    }
                });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CHECK_SETTINGS) {

            if (resultCode == RESULT_OK) {
                Log.e(TAG, "User agreed to make required location settings changes.");
                // Nothing to do. startLocationupdates() gets called in onResume again.
            }else {

                Log.e(TAG, "User chose not to make required location settings changes.");
                mRequestingLocationUpdates = false;
            }}
    }

    private void openSettings() {
        Intent intent = new Intent();
        intent.setAction(
                Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package",
                BuildConfig.APPLICATION_ID, null);
        intent.setData(uri);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mRequestingLocationUpdates && checkPermissions()) {
            startLocationUpdates();
        }

        updateLocationUI();
    }

    private boolean checkPermissions() {
        int permissionState = ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION);
        return permissionState == PackageManager.PERMISSION_GRANTED;

    }
}



