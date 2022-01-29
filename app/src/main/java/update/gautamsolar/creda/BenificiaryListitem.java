package update.gautamsolar.creda;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.preference.PreferenceManager;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AlertDialog;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.SearchView;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Cache;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;
import update.gautamsolar.creda.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import update.gautamsolar.creda.Automatic_BroadCast_Receivers.NetWork_State_Found_Rajasthan;
import update.gautamsolar.creda.Automatic_BroadCast_Receivers.NetWork_State_Installation_MP;
import update.gautamsolar.creda.Automatic_BroadCast_Receivers.NetWork_State_Installation_Rajasthan;
import update.gautamsolar.creda.Automatic_BroadCast_Receivers.NetWork_State_RMU_MP;
import update.gautamsolar.creda.Automatic_BroadCast_Receivers.NetWork_State_RMU_Rajasthan;
import update.gautamsolar.creda.Automatic_BroadCast_Receivers.NetWork_State_Site_Survey_MP;
import update.gautamsolar.creda.Automatic_BroadCast_Receivers.NetWork_State_Site_Survey_Rajasthan;
import update.gautamsolar.creda.Automatic_BroadCast_Receivers.NetworkStateChecker;
import update.gautamsolar.creda.Automatic_BroadCast_Receivers.NetworkStateCheckerI;
import update.gautamsolar.creda.Automatic_BroadCast_Receivers.NetworkStateCheckerS;
import update.gautamsolar.creda.Automatic_BroadCast_Receivers.NetworkStateFoundMP;
import update.gautamsolar.creda.Automatic_BroadCast_Receivers.Network_State_CMC_MP;
import update.gautamsolar.creda.Automatic_BroadCast_Receivers.Network_State_Foundation_M;
import update.gautamsolar.creda.Automatic_BroadCast_Receivers.Network_State_Pit;
import update.gautamsolar.creda.Automatic_BroadCast_Receivers.NetworkstateCheckerRMuNEw;
import update.gautamsolar.creda.Constants.Constants;

public class


BenificiaryListitem extends Activity {
    CredaModel credaModel;
    RecyclerView mRecyclerView;
    SearchView searchView;
    ImageView localdataimage;
    TextView locadataTExt;
    SharedPreferences settings;
    ImageView intiimageview;
    public static final int REQUEST_ID_MULTIPLE_PERMISSIONS = 1;
    ProgressDialog pb;
    Constants constants;
    public BenifRecyclerview benifRecyclerview;
    List<CredaModel> list_models;
    ImageView SearchName, SearchLoctaion, SearchId, Searchcontact, Refresh;
    String eng_id, Engineer_Contact, customerlistapi, project;
    SharedPreferences sharedPreferences;
    ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_benificiary_listitem);
        searchView = findViewById(R.id.search);
        try {
            registerReceiver(new NetworkStateChecker(), new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
            registerReceiver(new Network_State_CMC_MP(),new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
            registerReceiver(new NetworkStateCheckerI(), new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
            registerReceiver(new NetworkstateCheckerRMuNEw(), new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
            registerReceiver(new NetworkStateCheckerS(), new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
            registerReceiver(new NetWork_State_RMU_MP(), new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
            registerReceiver(new NetWork_State_RMU_Rajasthan(), new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
            registerReceiver(new NetWork_State_Site_Survey_MP(), new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
            registerReceiver(new NetWork_State_Site_Survey_Rajasthan(), new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
            registerReceiver(new NetWork_State_Installation_Rajasthan(), new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
            registerReceiver(new NetWork_State_Installation_MP(), new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
            registerReceiver(new NetWork_State_Found_Rajasthan(), new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
            registerReceiver(new NetworkStateFoundMP(), new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
            registerReceiver(new Network_State_Pit(), new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
            registerReceiver(new Network_State_Foundation_M(), new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
        } catch (Exception e) {
            e.printStackTrace();
        }


        constants = new Constants();
        customerlistapi = constants.CustomerList;
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        settings = PreferenceManager.getDefaultSharedPreferences(this);
        eng_id = sharedPreferences.getString("eng_id", "");

        String datacount = sharedPreferences.getString("localdata", "");
        Engineer_Contact = sharedPreferences.getString("engcontact", "");
        checkAndRequestPermissions();
        searchView.onActionViewExpanded();
        Refresh = (ImageView) findViewById(R.id.refresh);
        intiimageview = (ImageView) findViewById(R.id.noInto);
        intiimageview.setVisibility(View.GONE);
        credaModel = new CredaModel();
        pb = new ProgressDialog(this, R.style.MyGravity);
        pb.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        pb.setIndeterminateDrawable(getResources().getDrawable(R.drawable.custom_progress_dialog));
        pb.setCancelable(false);
        SearchName = (ImageView) findViewById(R.id.searchbyname);
        SearchId = (ImageView) findViewById(R.id.searchbyregn);
        Searchcontact = (ImageView) findViewById(R.id.searchbyno);
        SearchLoctaion = (ImageView) findViewById(R.id.searchbydist);
        Refresh = (ImageView) findViewById(R.id.refresh);


        mRecyclerView = findViewById(R.id.recyclerbenif);
        mRecyclerView.setVisibility(View.VISIBLE);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mRecyclerView.setNestedScrollingEnabled(true);
        mRecyclerView.setVisibility(View.VISIBLE);
        intiimageview.setVisibility(View.GONE);
        list_models = new ArrayList<>();
        all_complaints();


        Refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                customerlistapi = constants.CustomerList;
                all_complaints();



            }
        });
        SearchName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchName.setAlpha((float) 1.0);
                Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.zoom_in);
                SearchName.startAnimation(animation);

                SearchId.setBackgroundResource(0);
                SearchId.setAlpha((float) 0.5);
                Searchcontact.setAlpha((float) 0.5);
                SearchLoctaion.setAlpha((float) 0.5);

                Searchcontact.setBackgroundResource(0);
                SearchLoctaion.setBackgroundResource(0);
                searchView.setQueryHint("Search by Name");
                searchView.setInputType(InputType.TYPE_CLASS_TEXT);
                searchView.setQuery("", true);
            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextChange(String newText) {
                if (list_models.size() != 0) {
                    benifRecyclerview.getFilter().filter(newText);

                } else {
                    Toast.makeText(BenificiaryListitem.this, "list is not loaded", Toast.LENGTH_SHORT).show();
                }
                return false;
            }

            @Override
            public boolean onQueryTextSubmit(String query) {
                if (list_models.size() != 0) {
                    benifRecyclerview.getFilter().filter(query);
                } else {
                    Toast.makeText(BenificiaryListitem.this, "list is not loaded", Toast.LENGTH_SHORT).show();
                }
                return false;
            }
        });

        SearchId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchId.setAlpha((float) 1.0);

                RotateAnimation ranim = (RotateAnimation) AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate);
                ranim.setFillAfter(true);
                SearchId.setAnimation(ranim);
                Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.zoom_in);
                SearchId.startAnimation(animation);
                SearchName.setAlpha((float) 0.5);
                searchView.setQueryHint("Search by Registraion number");
                Searchcontact.setAlpha((float) 0.5);
                SearchLoctaion.setAlpha((float) 0.5);
                searchView.setInputType(InputType.TYPE_CLASS_NUMBER);
                searchView.setQuery("", true);

            }
        });
        Searchcontact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.zoom_in);
                Searchcontact.startAnimation(animation);
                Searchcontact.setAlpha((float) 1.0);
                SearchId.setAlpha((float) 0.5);
                SearchName.setAlpha((float) 0.5);
                SearchLoctaion.setAlpha((float) 0.5);
                searchView.setQueryHint("Search by Contact");

                searchView.setInputType(InputType.TYPE_CLASS_NUMBER);
                searchView.setQuery("", true);

            }
        });
        SearchLoctaion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.zoom_in);
                SearchLoctaion.startAnimation(animation);
                SearchLoctaion.setAlpha((float) 1.0);
                SearchId.setAlpha((float) 0.5);
                SearchName.setAlpha((float) 0.5);
                Searchcontact.setAlpha((float) 0.5);
                searchView.setQueryHint("Search by Location");
                searchView.setInputType(InputType.TYPE_CLASS_TEXT);
                searchView.setQuery("", true);

            }
        });


    }

    @Override
    public void onBackPressed() {
        androidx.appcompat.app.AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
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
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();


            }
        });
        builder1.show();


    }

    public void all_complaints() {

        project = sharedPreferences.getString("project", "");
        pd = new ProgressDialog(BenificiaryListitem.this);
        pd.setMessage("Loading");
        pd.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, customerlistapi, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {


                try {
                    pd.dismiss();
                    JSONArray jsonArray = new JSONArray(response);
                    Log.d("response_list",response);
                    list_models.clear();

                    if (project.equals("RAJASTHAN")) {
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            credaModel = new CredaModel();
                            credaModel.setInst_image1(jsonObject.optString("inst_image1"));
                            credaModel.setInst_image2(jsonObject.optString("inst_image2"));
                            credaModel.setInst_image3(jsonObject.optString("inst_image3"));
                            credaModel.setInst_image4(jsonObject.optString("inst_image4"));
                            credaModel.setFound_image1(jsonObject.optString("foun_image1"));
                            credaModel.setFound_image2(jsonObject.optString("foun_image2"));
                            credaModel.setFound_image3(jsonObject.optString("foun_image3"));
                            credaModel.setOld_rmu_id(jsonObject.optString("controler_rms_id"));
                            credaModel.setFound_image4(jsonObject.optString("foun_image4"));
                            credaModel.setAdhar_image(jsonObject.optString("adhar_image"));
                            credaModel.setBenificiary_image(jsonObject.optString("benificiary_image"));
                            credaModel.setSidemark_image(jsonObject.optString("sidemark_image"));
                            credaModel.setPassbook_image(jsonObject.optString("bankpassbook_image"));
                            credaModel.setBenifname(jsonObject.optString("fname"));
                            credaModel.setFname(jsonObject.getString("fathername"));
                            credaModel.setFoundation_status(jsonObject.getString("foundation_status"));
                            credaModel.setContact(jsonObject.getString("contact_no"));
                            credaModel.setVillage(jsonObject.getString("village"));
                            credaModel.setBlock(jsonObject.getString("block"));
                            credaModel.setDistrict(jsonObject.getString("district"));
                            credaModel.setInstalletion_status(jsonObject.getString("instalation_status"));
                            credaModel.setSitesurvey(jsonObject.getString("site_survay_status"));
                            credaModel.setRegistrationno(jsonObject.getString("reg_no"));
                            credaModel.setPump_capacity(jsonObject.getString("pump_capacity"));
                            credaModel.setPumptype(jsonObject.getString("pump_type"));
                            credaModel.setExisting_motor_run(jsonObject.getString("existing_moter_run"));
                            credaModel.setBor_size(jsonObject.getString("bor_size"));
                            credaModel.setWater_level(jsonObject.getString("water_level"));
                            credaModel.setBor_depth(jsonObject.getString("bor_depth"));
                            credaModel.setPhase(jsonObject.getString("phase"));
                            credaModel.setRmu_number(jsonObject.getString("new_controler_rms_id"));
                            list_models.add(credaModel);

                        }

                        benifRecyclerview = new BenifRecyclerview(list_models, BenificiaryListitem.this);
                        mRecyclerView.setAdapter(benifRecyclerview);

                    } else if (project.equals("CREDA")||project.equals("HAREDA")||project.equals("MSKPY")||project.equals("PEDA")||project.equals("MSEDCL")||project.equals("MEDA")) {
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            credaModel = new CredaModel();
                            credaModel.setInst_image1(jsonObject.optString("inst_image1"));
                            credaModel.setInst_image2(jsonObject.optString("inst_image2"));
                            credaModel.setInst_image3(jsonObject.optString("inst_image3"));
                            credaModel.setInst_image4(jsonObject.optString("inst_image4"));
                            credaModel.setInst_image5(jsonObject.optString("inst_image5"));
                            credaModel.setInst_image6(jsonObject.optString("inst_image6"));
                            credaModel.setInst_image7(jsonObject.optString("inst_image7"));
                            credaModel.setFound_image1(jsonObject.optString("foun_image1"));
                            credaModel.setFound_image2(jsonObject.optString("foun_image2"));
                            credaModel.setFound_image3(jsonObject.optString("foun_image3"));
                            credaModel.setOld_rmu_id(jsonObject.optString("controler_rms_id"));
                            credaModel.setFound_image4(jsonObject.optString("foun_image4"));
                            credaModel.setFound_image5(jsonObject.optString("foun_image5"));
                            credaModel.setAdhar_image(jsonObject.optString("adhar_image"));
                            credaModel.setBenificiary_image(jsonObject.optString("benificiary_image"));
                            credaModel.setSidemark_image(jsonObject.optString("sidemark_image"));
                            credaModel.setPassbook_image(jsonObject.optString("bankpassbook_image"));
                            credaModel.setBenifname(jsonObject.optString("fname"));
                            credaModel.setFname(jsonObject.getString("fathername"));
                            credaModel.setFoundation_status(jsonObject.getString("foundation_status"));
                            credaModel.setContact(jsonObject.getString("contact_no"));
                            credaModel.setVillage(jsonObject.getString("village"));
                            credaModel.setBlock(jsonObject.getString("block"));
                            credaModel.setDistrict(jsonObject.getString("district"));
                            credaModel.setInstalletion_status(jsonObject.getString("instalation_status"));
                            credaModel.setSitesurvey(jsonObject.getString("site_survay_status"));
                            credaModel.setRegistrationno(jsonObject.getString("reg_no"));
                            credaModel.setPump_capacity(jsonObject.getString("pump_capacity"));
                            credaModel.setPumptype(jsonObject.getString("pump_type"));
                            credaModel.setExisting_motor_run(jsonObject.getString("existing_moter_run"));
                            credaModel.setBor_size(jsonObject.getString("bor_size"));
                            credaModel.setWater_level(jsonObject.getString("water_level"));
                            credaModel.setBor_depth(jsonObject.getString("bor_depth"));
                            credaModel.setPhase(jsonObject.getString("phase"));
                            credaModel.setRmu_number(jsonObject.getString("new_controler_rms_id"));
                            credaModel.setPit_status(jsonObject.optString("pit_status"));
                            credaModel.setSite_format(jsonObject.optString("site_format"));
                            credaModel.setAadhar_back(jsonObject.optString("aadharback_image"));
                            credaModel.setBoaring(jsonObject.optString("bor_image_with_cust"));
                            credaModel.setSurvay2(jsonObject.optString("sidemark_image2"));
                            credaModel.setRoad_status(jsonObject.optString("road_status"));
                            credaModel.setSaria_status(jsonObject.optString("saria_status"));
                            credaModel.setRate_gitti_status(jsonObject.optString("rate_status"));
                            credaModel.setSaralid(jsonObject.getString("saralid"));
                            credaModel.setSaralyear(jsonObject.getString("saralyear"));
                            credaModel.setInstallation_video(jsonObject.getString("installation_video"));
                            credaModel.setPic_date(jsonObject.optString("invoice_image_date"));
                            credaModel.setSite_lat_new(jsonObject.optString("site_lat_new"));
                            credaModel.setSite_long_new(jsonObject.optString("site_long_new"));
                            credaModel.setMedaReg(jsonObject.optString("meda_reg"));

                            Log.d("foundation_ma",jsonObject.optString("reg_no")+jsonObject.optString("road_status")+
                                    jsonObject.optString("saria_status")+jsonObject.optString("rate_status"));
                            list_models.add(credaModel);


                        }

                        benifRecyclerview = new BenifRecyclerview(list_models, BenificiaryListitem.this);
                        mRecyclerView.setAdapter(benifRecyclerview);
                        pd.dismiss();

                    } else if (project.equals("MP")) {
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            credaModel = new CredaModel();
                            credaModel.setInst_image1(jsonObject.optString("inst_image1"));
                            credaModel.setInst_image2(jsonObject.optString("inst_image2"));
                            credaModel.setInst_image3(jsonObject.optString("inst_image3"));
                            credaModel.setInst_image4(jsonObject.optString("inst_image4"));
                            credaModel.setInst_image5(jsonObject.optString("inst_image5"));
                            credaModel.setInst_image6(jsonObject.optString("inst_image6"));
                            credaModel.setInst_image7(jsonObject.optString("inst_image7"));
                            credaModel.setFound_image1(jsonObject.optString("foun_image1"));
                            credaModel.setFound_image2(jsonObject.optString("foun_image2"));
                            credaModel.setFound_image3(jsonObject.optString("foun_image3"));
                            credaModel.setOld_rmu_id(jsonObject.optString("controler_rms_id"));
                            credaModel.setFound_image4(jsonObject.optString("foun_image4"));
                            credaModel.setFound_image5(jsonObject.optString("ir_image"));
                            credaModel.setFound_image6(jsonObject.optString("ir_image2"));
                            credaModel.setFound_imiage7(jsonObject.optString("ir_image3"));
                            credaModel.setAdhar_image(jsonObject.optString("adhar_image"));
                            credaModel.setBenificiary_image(jsonObject.optString("benificiary_image"));
                            credaModel.setSidemark_image(jsonObject.optString("sidemark_image"));
                            credaModel.setPassbook_image(jsonObject.optString("bankpassbook_image"));
                            credaModel.setBenifname(jsonObject.optString("fname"));
                            credaModel.setFname(jsonObject.getString("fathername"));
                            credaModel.setFoundation_status(jsonObject.getString("foundation_status"));
                            credaModel.setContact(jsonObject.getString("contact_no"));
                            credaModel.setVillage(jsonObject.getString("village"));
                            credaModel.setBlock(jsonObject.getString("block"));
                            credaModel.setDistrict(jsonObject.getString("district"));
                            credaModel.setInstalletion_status(jsonObject.getString("instalation_status"));
                            credaModel.setSitesurvey(jsonObject.getString("site_survay_status"));
                            credaModel.setRegistrationno(jsonObject.getString("reg_no"));
                            credaModel.setPump_capacity(jsonObject.getString("pump_capacity"));
                            credaModel.setPumptype(jsonObject.getString("pump_type"));
                            credaModel.setExisting_motor_run(jsonObject.getString("existing_moter_run"));
                            credaModel.setBor_size(jsonObject.getString("bor_size"));
                            credaModel.setWater_level(jsonObject.getString("water_level"));
                            credaModel.setBor_depth(jsonObject.getString("bor_depth"));
                            credaModel.setBore_Status(jsonObject.getString("bor_status"));
                            credaModel.setAdhar_number(jsonObject.getString("adhar_no"));
                            credaModel.setAccount_number(jsonObject.getString("bank_account_no"));
                            credaModel.setBank_name(jsonObject.getString("bank_name"));
                            credaModel.setIfsc_code(jsonObject.getString("ifsc_code"));
                            credaModel.setPumphead(jsonObject.getString("pump_head"));
                            credaModel.setCMC_1(jsonObject.getString("cms_status1"));
                            credaModel.setCMC_2(jsonObject.getString("cms_status2"));
                           // Toast.makeText(getApplicationContext(),String.valueOf(jsonObject.getString("cms_status1")),Toast.LENGTH_LONG).show();
                            credaModel.setCMC_3(jsonObject.getString("cms_status3"));
                            credaModel.setCMC_4(jsonObject.getString("cms_status4"));
                            credaModel.setCMC_5(jsonObject.getString("cms_status5"));
                            credaModel.setCMC_6(jsonObject.getString("cms_status6"));
                            credaModel.setCMC_7(jsonObject.getString("cms_status7"));
                            credaModel.setCMC_8(jsonObject.getString("cms_status8"));
                            credaModel.setCMC_9(jsonObject.getString("cms_status9"));
                            credaModel.setCMC_10(jsonObject.getString("cms_status10"));
                            credaModel.setPhase(jsonObject.getString("phase"));
                            credaModel.setRmu_number(jsonObject.getString("new_controler_rms_id"));
                            credaModel.setRmu_status(jsonObject.getString("rmu_status"));
                            list_models.add(credaModel);
                             }
                        benifRecyclerview = new BenifRecyclerview(list_models, BenificiaryListitem.this);
                        mRecyclerView.setAdapter(benifRecyclerview);


                    }
                    Toast.makeText(getApplicationContext(),"List Updated",Toast.LENGTH_LONG).show();



                } catch (JSONException e) {
                    pd.dismiss();
//                            mRecyclerView.setVisibility(View.GONE);
//                            intiimageview.setVisibility(View.VISIBLE);
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                pd.dismiss();
//                mRecyclerView.setVisibility(View.GONE);
//                intiimageview.setVisibility(View.VISIBLE);

            }
        }) {
            @Override
            protected Response<String> parseNetworkResponse(NetworkResponse response) {
                pd.dismiss();
                try {
                    Cache.Entry cacheEntry = HttpHeaderParser.parseCacheHeaders(response);
                    if (cacheEntry == null) {
                        cacheEntry = new Cache.Entry();
                    }
                    final long cacheHitButRefreshed = 0; // in 3 minutes cache will be hit, but also refreshed on background
                    final long cacheExpired = 24 * 60 * 60 * 1000; // in 24 hours this cache entry expires completely
                    long now = System.currentTimeMillis();
                    final long softExpire = now + cacheHitButRefreshed;
                    final long ttl = now + cacheExpired;
                    cacheEntry.data = response.data;
                    cacheEntry.softTtl = softExpire;
                    cacheEntry.ttl = ttl;
                    String headerValue;
                    headerValue = response.headers.get("Date");
                    if (headerValue != null) {
                        cacheEntry.serverDate = HttpHeaderParser.parseDateAsEpoch(headerValue);
                    }
                    headerValue = response.headers.get("Last-Modified");
                    if (headerValue != null) {
                        cacheEntry.lastModified = HttpHeaderParser.parseDateAsEpoch(headerValue);
                    }
                    cacheEntry.responseHeaders = response.headers;
                    final String jsonString = new String(response.data,
                            HttpHeaderParser.parseCharset(response.headers));
                    return Response.success(new String(jsonString), cacheEntry);
                } catch (UnsupportedEncodingException e) {
                    pd.dismiss();
                    return Response.error(new ParseError(e));
                }
            }

            @Override
            protected void deliverResponse(String response) {
                super.deliverResponse(response);
                pb.dismiss();
            }

            @Override
            public void deliverError(VolleyError error) {
                super.deliverError(error);
                pb.dismiss();
            }

            @Override
            protected VolleyError parseNetworkError(VolleyError volleyError) {
                pd.dismiss();
                return super.parseNetworkError(volleyError);
            }

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("eng_id", eng_id);
                return params;
            }

        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(10000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        stringRequest.setShouldCache(true);
        MySingleton.getInstance(BenificiaryListitem.this).addTorequestque(stringRequest);

    }

    private boolean checkAndRequestPermissions() {
        int camera = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA);
        int storage = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int loc = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION);
        int loc2 = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);
        List<String> listPermissionsNeeded = new ArrayList<>();

        if (camera != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.CAMERA);
        }
        if (storage != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        if (loc2 != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.ACCESS_FINE_LOCATION);
        }
        if (loc != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.ACCESS_COARSE_LOCATION);
        }
        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(this, listPermissionsNeeded.toArray
                    (new String[listPermissionsNeeded.size()]), REQUEST_ID_MULTIPLE_PERMISSIONS);
            return false;
        }
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        all_complaints();
    }
}
