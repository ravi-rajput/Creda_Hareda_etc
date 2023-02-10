package update.gautamsolar.creda;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import update.gautamsolar.creda.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import update.gautamsolar.creda.Adapters.Misc_Pend_Adapter;

public class Misc_Pend_Inatallation extends AppCompatActivity {
    RecyclerView recyclerView;
    Misc_Pend_Adapter misc_pend_adapter;
    List<Misc_Pend_Model> listItems;
    Misc_Pend_Model miscPendModel;
    ProgressDialog pb;
    String eng_id,type;
    SearchView searchView;
    TextView textViewname, textViewrole, textViewproject, textViewdist, textViewcontact;
    Intent i;
    Dialog Localdialog;
    SharedPreferences sharedPreferences;
    SwipeRefreshLayout swipeContainer;
    @Override
    protected void onCreate(Bundle SavedInstanceState){
        super.onCreate(SavedInstanceState);
        setContentView(R.layout.misc_pend_installation);
        recyclerView=findViewById(R.id.recycler_list);
        searchView=findViewById(R.id.search);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setNestedScrollingEnabled(true);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        eng_id=sharedPreferences.getString("eng_id","");
        listItems=new ArrayList<>();
        pb=new ProgressDialog(this);
        pb.setMessage("Loading...");
        i=getIntent();
        all_list(i.getStringExtra("api"));
        type=i.getStringExtra("type");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextChange(String newText) {
                if (listItems.size() != 0) {
                    misc_pend_adapter.getFilter().filter(newText);

                } else {
                    Toast.makeText(Misc_Pend_Inatallation.this, "list is not loaded", Toast.LENGTH_SHORT).show();
                }
                return false;
            }

            @Override
            public boolean onQueryTextSubmit(String query) {
                if (listItems.size() != 0) {
                    misc_pend_adapter.getFilter().filter(query);
                } else {
                    Toast.makeText(Misc_Pend_Inatallation.this, "list is not loaded", Toast.LENGTH_SHORT).show();
                }
                return false;
            }
        });
        swipeContainer = (SwipeRefreshLayout) findViewById(R.id.swipeContainer);
        // Setup refresh listener which triggers new data loading
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeContainer.setRefreshing(true);
                Log.d("Swipe", "Refreshing Number");
                (new Handler()).postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        swipeContainer.setRefreshing(false);
                        ConnectivityManager cm = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
                        final NetworkInfo activeNetwork = cm.getActiveNetworkInfo();

                        if (activeNetwork != null) {
                            //if connected to wifi or mobile data plan
                            if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI ||
                                    activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE) {
                                listItems.clear();
                                all_list(i.getStringExtra("api"));
                            }}else{
                            Toast.makeText(Misc_Pend_Inatallation.this, "No Internet Connection", Toast.LENGTH_SHORT).show();
                        }
                    }
                }, 3000);
            }
        });
        // Configure the refreshing colors
        swipeContainer.setColorSchemeResources(android.R.color.holo_red_dark,
                android.R.color.holo_green_dark,
                android.R.color.holo_orange_dark);

    }
    public void all_list(String api) {
        pb.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                "http://gautamsolar.co.in/pumpall_api/"+api, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.d("responce",response);
                        try {
                             final JSONArray jsonArray = new JSONArray(response);
                            listItems.clear();
                            for (int i = 0; i < jsonArray.length(); i++) {
                                miscPendModel = new Misc_Pend_Model();
                                JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                                miscPendModel.setBenefitiary_name(jsonObject1.optString("beneficiary_name"));
                                miscPendModel.setEng_name(jsonObject1.optString("eng_name"));
                                miscPendModel.setIns_miss_amount_send(jsonObject1.optString("miss_amount_send"));
                                miscPendModel.setMisc_ammount(jsonObject1.optString("miss_real_amount"));
                                miscPendModel.setMisc_comment(jsonObject1.optString("comment"));
                                miscPendModel.setReg_no(jsonObject1.optString("reg_no"));
                                miscPendModel.setStatus(jsonObject1.optString("miss_approve_status"));
                                miscPendModel.setScheme(jsonObject1.optString("scheme"));
                                miscPendModel.setDistrict(jsonObject1.optString("district"));
                                miscPendModel.setBlock(jsonObject1.optString("block"));
                                miscPendModel.setFor_payment(jsonObject1.optString("for_payment"));
                                miscPendModel.setPay_name(jsonObject1.optString("pay_name"));
                                miscPendModel.setId(jsonObject1.optString("id"));
                                miscPendModel.setType(type);
                                listItems.add(miscPendModel);
                                pb.dismiss();
                            }

                            misc_pend_adapter = new Misc_Pend_Adapter(listItems, Misc_Pend_Inatallation.this);
                            recyclerView.setAdapter(misc_pend_adapter);
                            pb.dismiss();

                        } catch (JSONException e) {
                            e.printStackTrace();
                            pb.dismiss();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        String message = null;
                        if (error instanceof NetworkError) {
                            message = "Cannot connect to Internet...Please check your connection!";
                            Toast.makeText(Misc_Pend_Inatallation.this, message, Toast.LENGTH_SHORT).show();
                            pb.dismiss();
                        }
                        else if (error instanceof ServerError) {
                            message = "The server could not be found. Please try again after some time!!";
                            Toast.makeText(Misc_Pend_Inatallation.this, message, Toast.LENGTH_SHORT).show();
                            pb.dismiss();
                        } else if (error instanceof AuthFailureError) {
                            message = "Cannot connect to Internet...Please check your connection!";
                            Toast.makeText(Misc_Pend_Inatallation.this, message, Toast.LENGTH_SHORT).show();
                            pb.dismiss();
                        } else if (error instanceof ParseError) {
                            message = "Parsing error! Please try again after some time!!";
                            Toast.makeText(Misc_Pend_Inatallation.this, message, Toast.LENGTH_SHORT).show();
                            pb.dismiss();
                        } else if (error instanceof NoConnectionError) {
                            message = "Cannot connect to Internet...Please check your connection!";
                            Toast.makeText(Misc_Pend_Inatallation.this, message, Toast.LENGTH_SHORT).show();
                            pb.dismiss();
                        } else if (error instanceof TimeoutError) {
                            message = "Connection TimeOut! Please check your internet connection.";
                            Toast.makeText(Misc_Pend_Inatallation.this, message, Toast.LENGTH_SHORT).show();
                            pb.dismiss();
                        }
                        else{
                            Toast.makeText(Misc_Pend_Inatallation.this, error.toString(), Toast.LENGTH_SHORT).show();
                            pb.dismiss();
                        }
                    }
                }) {
            @Override
            public Map<String, String> getParams() throws AuthFailureError {
                final Map<String, String> params = new HashMap<>();
                params.put("Accept", "application/json");
                params.put("Content-Type", "application/json");
                params.put("eng_id", eng_id);
                return params;
            }
        };
        //creating a request queue
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        //adding the string request to request queue
        requestQueue.add(stringRequest);

    }
    public void onResume(){
        all_list(i.getStringExtra("api"));
        super.onResume();
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

                Localdialog = new Dialog(Misc_Pend_Inatallation.this);
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

                        SharedPreferences.Editor editor = sharedPreferences.edit();
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

}
