package update.gautamsolar.creda;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Payment_Approve_Activity extends AppCompatActivity {
TextView reg_no,benef_name,eng_name,district,scheme,cmnt,for_payment,pay_name;
        EditText aproved_amnt;
        ProgressDialog pb;
        String eng_id;
        Intent i;
        TextView reqst;
        LinearLayout name_layout,for_payment_lay,eng_layout;
        RadioGroup radioGroup;
        RadioButton radioButton,radioButton2;
        SharedPreferences sharedPreferences;
        boolean checked_status=false;
        RadioButton rb;
    Button upload;
    @Override
    protected void onCreate(Bundle SavedInstanceState){
        super.onCreate(SavedInstanceState);
        setContentView(R.layout.payment_approve_activity);

        i= getIntent();
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        eng_id=sharedPreferences.getString("eng_id","");

        reg_no=findViewById(R.id.reg_no);
        upload=findViewById(R.id.upload);
        reqst=findViewById(R.id.reqst);
        radioGroup=findViewById(R.id.r_group);
        radioButton=findViewById(R.id.radioButton);
        radioButton2=findViewById(R.id.radioButton2);
        benef_name=findViewById(R.id.benef_name);
        eng_name=findViewById(R.id.eng_name);
        district=findViewById(R.id.district);
        scheme=findViewById(R.id.scheme);
        cmnt=findViewById(R.id.cmnt);
        aproved_amnt=findViewById(R.id.aproved_amnt);
        for_payment=findViewById(R.id.for_payment);
        pay_name=findViewById(R.id.pay_name);
        name_layout=findViewById(R.id.name_layout);
        for_payment_lay=findViewById(R.id.for_payment_lay);
        eng_layout=findViewById(R.id.eng_layout);
        name_layout=findViewById(R.id.name_layout);

        if(i.getStringExtra("type").equals("PAYMENT")){
            eng_layout.setVisibility(View.GONE);
            for_payment_lay.setVisibility(View.VISIBLE);
            name_layout.setVisibility(View.VISIBLE);
        }


        pb=new ProgressDialog(this);
        pb.setMessage("Uploading...");

        reg_no.setText(i.getStringExtra("reg_no"));
        benef_name.setText(i.getStringExtra("benif_name"));
        eng_name.setText(i.getStringExtra("eng_name"));
        district.setText(i.getStringExtra("dist"));
        scheme.setText(i.getStringExtra("schme"));
        cmnt.setText(i.getStringExtra("cmnt"));
        aproved_amnt.setText(i.getStringExtra("real_amnt"));
        for_payment.setText(i.getStringExtra("for_payment"));
        pay_name.setText(i.getStringExtra("pay_name"));

        reqst.setText("Requested Ammount : "+i.getStringExtra("amount_send"));
if(i.getStringExtra("status").equals("Approved")){
    radioGroup.check(R.id.radioButton);
    aproved_amnt.setEnabled(false);
    radioButton2.setEnabled(false);
    upload.setText("Already Approved");
    upload.setEnabled(false);
}else if(i.getStringExtra("status").equals("Disapproved")){
    radioGroup.check(R.id.radioButton2);
    aproved_amnt.setEnabled(false);
    radioButton.setEnabled(false);
    upload.setText("Already Disapproved");
    upload.setEnabled(false);
}else{
    checked_status=true;
}

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @SuppressLint("ResourceType")
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                  rb = (RadioButton) group.findViewById(checkedId);
                if (null != rb && checkedId > -1) {
                    Toast.makeText(Payment_Approve_Activity.this, rb.getText(), Toast.LENGTH_SHORT).show();
                    if(rb.getText().equals("Disapproved")){
                        aproved_amnt.setText("0");
                        aproved_amnt.setEnabled(false);
                    }else{
                        aproved_amnt.setEnabled(true);
                    }
                     }

            }
        });
upload.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
       if(radioGroup.getCheckedRadioButtonId() == -1){
           Toast.makeText(Payment_Approve_Activity.this, "Please choose approvel status", Toast.LENGTH_SHORT).show();
       }else if(aproved_amnt.getText().toString().length()<=2 && rb.getText().equals("Approved")) {
           Toast.makeText(Payment_Approve_Activity.this, "Please enter Approved Amount", Toast.LENGTH_SHORT).show();
       }else
       {
           if(checked_status==true){
               upload();
           }else{
               Toast.makeText(Payment_Approve_Activity.this, "It is already"+i.getStringExtra("status"), Toast.LENGTH_SHORT).show();
           }
    }}
});
    }
public void upload(){
        pb.show();

    StringRequest stringRequest=new StringRequest(Request.Method.POST, "http://gautamsolar.co.in/pumpall_api/creda_approved_status_updatev_1.php", new Response.Listener<String>() {
        @Override
        public void onResponse(String response) {
            try {
                JSONObject obj = new JSONObject(response);
               String status= obj.optString("error");
                String message= obj.optString("message");
               if(status.equals("false")){
                   finish();
               }else{
                   Toast.makeText(Payment_Approve_Activity.this, message, Toast.LENGTH_SHORT).show();
                }

            } catch (JSONException e) {
                e.printStackTrace();
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
                Toast.makeText(Payment_Approve_Activity.this, message, Toast.LENGTH_SHORT).show();
                pb.dismiss();
            }
            else if (error instanceof ServerError) {
                message = "The server could not be found. Please try again after some time!!";
                Toast.makeText(Payment_Approve_Activity.this, message, Toast.LENGTH_SHORT).show();
                pb.dismiss();
            } else if (error instanceof AuthFailureError) {
                message = "Cannot connect to Internet...Please check your connection!";
                Toast.makeText(Payment_Approve_Activity.this, message, Toast.LENGTH_SHORT).show();
                pb.dismiss();
            } else if (error instanceof ParseError) {
                message = "Parsing error! Please try again after some time!!";
                Toast.makeText(Payment_Approve_Activity.this, message, Toast.LENGTH_SHORT).show();
                pb.dismiss();
            } else if (error instanceof NoConnectionError) {
                message = "Cannot connect to Internet...Please check your connection!";
                Toast.makeText(Payment_Approve_Activity.this, message, Toast.LENGTH_SHORT).show();
                pb.dismiss();
            } else if (error instanceof TimeoutError) {
                message = "Connection TimeOut! Please check your internet connection.";
                Toast.makeText(Payment_Approve_Activity.this, message, Toast.LENGTH_SHORT).show();
                pb.dismiss();
            }
            else{
                Toast.makeText(Payment_Approve_Activity.this, error.toString(), Toast.LENGTH_SHORT).show();
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
            params.put("approved_status",rb.getText().toString());
            params.put("real_amount",aproved_amnt.getText().toString());
            params.put("reg_no",i.getStringExtra("reg_no"));
            params.put("type",i.getStringExtra("type"));
            params.put("id",i.getStringExtra("id"));
            return params;
        }
    };
    //creating a request queue
    RequestQueue requestQueue = Volley.newRequestQueue(this);

    //adding the string request to request queue
        requestQueue.add(stringRequest);

}
}
