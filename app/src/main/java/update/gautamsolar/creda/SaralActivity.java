package update.gautamsolar.creda;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
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

import update.gautamsolar.creda.Constants.Constants;


public class SaralActivity extends AppCompatActivity implements
        AdapterView.OnItemSelectedListener {
    String[] year = {"Select year", "2019", "2020", "2021"};
    String salactedYear, eng_id, regnnumber;
    EditText number;
    Button submit;
    Date date;
    ProgressDialog pb;
    double latti;
    double longi;
    SharedPreferences sharedPreferences;
    TextView Father, Contactid, Villageide, Pumpide, Contactide, Blockid,numberid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.saral_activity);
        latti = 0.0;
        longi = 0.0;
        Bundle bundlef = getIntent().getExtras();
        Spinner spin = (Spinner) findViewById(R.id.spinner);
        submit = findViewById(R.id.submit);
        number = findViewById(R.id.number);
        Father = (TextView) findViewById(R.id.fatheride);
        Contactid = (TextView) findViewById(R.id.contactide);
        Villageide = (TextView) findViewById(R.id.villageide);
        Pumpide = (TextView) findViewById(R.id.benificiaryide);
        Blockid = (TextView) findViewById(R.id.blockide);
        numberid=findViewById(R.id.numberid);
        spin.setOnItemSelectedListener(this);
        pb = new ProgressDialog(this);
        pb.setMessage("Please Wait");
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        eng_id = sharedPreferences.getString("eng_id", "");
        regnnumber = bundlef.getString("regnnumber");
        Father.setText(bundlef.getString("fathername"));
        Contactid.setText(bundlef.getString("contact"));
        Villageide.setText(bundlef.getString("village"));
        Blockid.setText(bundlef.getString("block"));
        Pumpide.setText(bundlef.getString("benifname"));
        numberid.setText(regnnumber);
        if(!bundlef.getString("saralid").equals("null")&& !bundlef.getString("saralyear").equals("null")) {
            number.setText(bundlef.getString("saralid"));
            year[0] = bundlef.getString("saralyear");
        }
        //Creating the ArrayAdapter instance having the country list
        ArrayAdapter aa = new ArrayAdapter(this, android.R.layout.simple_spinner_item, year);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        spin.setAdapter(aa);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (number.getText().toString().length() != 5) {
                    Toast.makeText(SaralActivity.this, "Please Enter Saral id", Toast.LENGTH_SHORT).show();
                } else if (salactedYear.equals("Select year")) {
                    Toast.makeText(SaralActivity.this, "Please Select year", Toast.LENGTH_SHORT).show();
                } else {
                    upload();
                }
            }
        });

    }

    @Override
    public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long id) {
        Toast.makeText(getApplicationContext(), year[position], Toast.LENGTH_LONG).show();
        salactedYear = year[position];
    }

    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub
    }

    private String getDateTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        date = new Date();
        return dateFormat.format(date);
    }

    public void upload() {
        UploadAll.getInstance().init();
        latti = UploadAll.latitude;
        longi = UploadAll.longitude;
        pb.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.SARAL_AP, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                pb.dismiss();

                try {
                    JSONObject obj = new JSONObject(response);
                    //  Toast.makeText(getApplicationContext(),response,Toast.LENGTH_SHORT).show();
                    Log.d("response", response);
                    String message = obj.getString("error");
                    if (message.equals("false")) {
                        pb.dismiss();
                        finish();
                        Toast.makeText(SaralActivity.this, "Upload Successfully", Toast.LENGTH_SHORT).show();
                    } else {
                        pb.dismiss();

                        Toast.makeText(SaralActivity.this, "Upload Unsuccessfully", Toast.LENGTH_SHORT).show();

                    }

                } catch (JSONException e) {
                    pb.dismiss();
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                pb.dismiss();
                Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_LONG).show();

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("year", salactedYear);
                params.put("saralid", number.getText().toString());
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
