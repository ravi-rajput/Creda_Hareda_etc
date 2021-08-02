package update.gautamsolar.creda;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.preference.PreferenceManager;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import update.gautamsolar.creda.Database.Foundation_M_Database;

public class Foundation_Material extends AppCompatActivity {
    TextView Fatheri, Contactidi, Villageidei, Pumpidei,  Blockidi, numberidi;
    String  reg_no, regnnumber, benifname, fathername, contact, block, village,eng_id,road_status,saria_status,rate_gitt;
    CheckBox ch1,ch2,ch3;
    ProgressDialog pb;
    double latti;
    double longi;
    Date date;
    Dialog Localdialog,dialog;
    SharedPreferences sharedPreferences;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.foundation_material_activity);

        road_status="NO";
        saria_status="NO";
        rate_gitt="NO";

        Fatheri = (TextView) findViewById(R.id.fatheride);
        Contactidi = (TextView) findViewById(R.id.contactide);
        Villageidei = (TextView) findViewById(R.id.villageide);
        Pumpidei = (TextView) findViewById(R.id.benificiaryide);
        Blockidi = (TextView) findViewById(R.id.blockide);
        numberidi=findViewById(R.id.numberid);
        ch1=findViewById(R.id.checkBox1);
        ch2=findViewById(R.id.checkBox2);
        ch3=findViewById(R.id.checkBox3);
        pb=new ProgressDialog(this);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        eng_id=sharedPreferences.getString("eng_id","");
        Intent bundleUploadB = getIntent();
        fathername = bundleUploadB.getStringExtra("fathername");
        regnnumber = bundleUploadB.getStringExtra("regnnumber");
        benifname = bundleUploadB.getStringExtra("benifname");
        village = bundleUploadB.getStringExtra("village");
        block = bundleUploadB.getStringExtra("block");
        contact = bundleUploadB.getStringExtra("contact");

        dialog = new Dialog(Foundation_Material.this); // Context, this, etc.
        Localdialog = new Dialog(Foundation_Material.this); // Context, this, etc.

        dialog.setContentView(R.layout.uploaddialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(false);

        Localdialog.setContentView(R.layout.localdialog);
        Localdialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        Localdialog.setCancelable(false);

        Fatheri.setText(fathername);
        Contactidi.setText(contact);
        Villageidei.setText(village);
        Blockidi.setText(block);
        Pumpidei.setText(benifname);
        numberidi.setText(regnnumber);

if(bundleUploadB.getStringExtra("rate").equals("YES")){
    ch3.setChecked(true);
}else{
    ch3.setChecked(false);

} if(bundleUploadB.getStringExtra("rode").equals("YES")){
    ch1.setChecked(true);
}else{
            ch1.setChecked(false);
        }

if(bundleUploadB.getStringExtra("saria_status").equals("YES")){
    ch2.setChecked(true);
        }else{
    ch2.setChecked(false);
        }

    }
    // This function is invoked when the button is pressed.
    public void Check(View v)
    {
        // Concatenation of the checked options in if

        // isChecked() is used to check whether
        // the CheckBox is in true state or not.
        if(ch1.isChecked()){
            road_status="YES";}else{
            road_status="NO";
        }
        if(ch2.isChecked())
        {
            saria_status="YES";}else{
            saria_status="NO";
        }
        if(ch3.isChecked())
        {
            rate_gitt="YES";}else{
            rate_gitt="NO";
        }
if(road_status=="NO"&&saria_status=="NO"&&rate_gitt=="NO"){
    Toast.makeText(this, "Please Select any ONE", Toast.LENGTH_SHORT).show();
}
       else if(latti==0.0&&longi==0.0){
            UploadAll.getInstance().init();
            latti=UploadAll.latitude;
            longi= UploadAll.longitude;
            Toast.makeText(Foundation_Material.this, "Please wait we are getting location", Toast.LENGTH_SHORT).show();
        }else {
            ConnectivityManager cm = (ConnectivityManager) Foundation_Material.this.getSystemService(Context.CONNECTIVITY_SERVICE);
            final NetworkInfo activeNetwork = cm.getActiveNetworkInfo();

            if (activeNetwork != null) {
                //if connected to wifi or mobile data plan
                if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI ||
                        activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE) {
                    pb.show();
                    upload();
                }
            } else {
                save_local();
            }
        }
    }

    public void upload() {
       
        UploadAll.getInstance().init();
        latti=UploadAll.latitude;
        longi= UploadAll.longitude;
  
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://gautamsolar.co.in/pumpall_api/foundation_material_uploda_v1.php",
                new Response.Listener<String>() {
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
                                Intent i = new Intent(Foundation_Material.this, UploadAll.class);
                                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(i);
                                finish();
                                dialog.dismiss();
                            }
                        });
                        dialog.show();

                    } else {
                        pb.dismiss();
                        Toast.makeText(Foundation_Material.this, message, Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    pb.dismiss();
                    e.printStackTrace();
                    Toast.makeText(Foundation_Material.this, "Something went Wrong Please try Latter..", Toast.LENGTH_SHORT).show();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                error.printStackTrace();
                String message = null;
                if (error instanceof NetworkError) {
                    message = "Cannot connect to Internet...Please check your connection!";
                    Toast.makeText(Foundation_Material.this, message, Toast.LENGTH_SHORT).show();
                    pb.dismiss();
                }
                else if (error instanceof ServerError) {
                    message = "The server could not be found. Please try again after some time!!";
                    Toast.makeText(Foundation_Material.this, message, Toast.LENGTH_SHORT).show();
                    pb.dismiss();
                } else if (error instanceof AuthFailureError) {
                    message = "Cannot connect to Internet...Please check your connection!";
                    Toast.makeText(Foundation_Material.this, message, Toast.LENGTH_SHORT).show();
                    pb.dismiss();
                } else if (error instanceof ParseError) {
                    message = "Parsing error! Please try again after some time!!";
                    Toast.makeText(Foundation_Material.this, message, Toast.LENGTH_SHORT).show();
                    pb.dismiss();
                } else if (error instanceof NoConnectionError) {
                    message = "Cannot connect to Internet...Please check your connection!";
                    Toast.makeText(Foundation_Material.this, message, Toast.LENGTH_SHORT).show();
                    pb.dismiss();
                } else if (error instanceof TimeoutError) {
                    message = "Connection TimeOut! Please check your internet connection.";
                    Toast.makeText(Foundation_Material.this, message, Toast.LENGTH_SHORT).show();
                    pb.dismiss();
                }
                else{
                    Toast.makeText(Foundation_Material.this, message, Toast.LENGTH_SHORT).show();
                    pb.dismiss();
                }
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("lat", String.valueOf(latti));
                params.put("lon", String.valueOf(longi));
                params.put("eng_id", eng_id);
                params.put("reg_no", regnnumber);
                params.put("datetime", getDateTime());
                params.put("road_status",road_status);
                params.put("rate_gitti_status",rate_gitt);
                params.put("saria_jall_status",saria_status);
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
        date = new Date();
        return dateFormat.format(date);
    }

    public void save_local() {
        UploadAll.getInstance().init();
        latti=UploadAll.latitude;
        longi= UploadAll.longitude;

        Foundation_M_Database f_mDatabase = new Foundation_M_Database();
        f_mDatabase.Lat = String.valueOf(latti);
        f_mDatabase.Lon = String.valueOf(longi);
        f_mDatabase.eng_id=eng_id;
        f_mDatabase.Regn = regnnumber;
        f_mDatabase.Dati = getDateTime();
        f_mDatabase.Rode=road_status;
        f_mDatabase.Rate_Gitt=rate_gitt;
        f_mDatabase.Saria_Jall=saria_status;
        try{
            f_mDatabase.save();}catch (Exception ae){
        }
     Localdialog.findViewById(R.id.clickmedismissf).setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            public void onClick(View v) {
                Toast.makeText(Foundation_Material.this, "Saved", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(Foundation_Material.this, UploadAll.class);
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
