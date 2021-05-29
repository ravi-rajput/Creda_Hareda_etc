package update.gautamsolar.creda;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.preference.PreferenceManager;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.gautamsolar.creda.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import update.gautamsolar.creda.Constants.Constants;

public class MainActivity extends AppCompatActivity {

    Button btn_login;
    EditText input_mobile, input_password;
    String mobile, password, login_api, code;


    ProgressDialog progressDialog;
    AlertDialog.Builder builder;
    SharedPreferences settings;
    Constants constants;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        constants = new Constants();

        settings = PreferenceManager.getDefaultSharedPreferences(this);
        final String login = settings.getString("login_status", "");
        final String role = settings.getString("role", "");

        login_api = constants.ENGINEER_LOGIN;

        if (login.equals("1") && !role.equals("Admin")) {
            Intent intent = new Intent(getApplicationContext(), BenificiaryListitem.class);
            startActivity(intent);
            finish();
        } else if (login.equals("1") && role.equals("Admin")) {
            Intent intent = new Intent(getApplicationContext(), Owner_Portion_Activity.class);
            startActivity(intent);
            finish();
        }


        input_mobile = findViewById(R.id.input_mobile);
        input_password = findViewById(R.id.input_password);
        btn_login = findViewById(R.id.btn_login);
        builder = new AlertDialog.Builder(MainActivity.this);

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mobile = input_mobile.getText().toString().trim();
                password = input_password.getText().toString().trim();


                if (mobile.equals("") && password.equals("")) {
                    builder.setTitle("Some Thing Went Wrong");
                    displayAlert("Enter A Valid username or password");

                }

                ConnectivityManager cm = (ConnectivityManager) MainActivity.this.getSystemService(Context.CONNECTIVITY_SERVICE);
                final NetworkInfo activeNetwork = cm.getActiveNetworkInfo();

                if (activeNetwork != null) {

                    progressDialog = new ProgressDialog(MainActivity.this, R.style.AppTheme_Dark_Dialog);
                    progressDialog.setIndeterminate(true);
                    progressDialog.setMessage("Authenticating...");
                    progressDialog.show();

                    StringRequest stringRequest = new StringRequest(Request.Method.POST, login_api,

                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    progressDialog.dismiss();
                                    // Toast.makeText(getApplicationContext(), response, Toast.LENGTH_LONG).show();

                                    try {
                                        JSONArray jsonArray = new JSONArray(response);
                                        for (int i = 0; i < jsonArray.length(); i++) {
                                            JSONObject jsonObject = jsonArray.getJSONObject(i);

                                            SharedPreferences.Editor myeditor = settings.edit();

                                            code = jsonObject.getString("login_status");


                                            if (code.equals("0")) {

                                                AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
                                                alertDialog.setTitle("Login failed");
                                                alertDialog.setMessage("User not found Please try again");
                                                alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK",
                                                        new DialogInterface.OnClickListener() {
                                                            public void onClick(DialogInterface dialog, int which) {
                                                                dialog.dismiss();
                                                            }
                                                        });
                                                alertDialog.show();

                                            } else {
                                                String role = jsonObject.getString("role");
                                                SharedPreferences.Editor editor = settings.edit();
                                                String contact = jsonObject.getString("contact");
                                                String email = jsonObject.getString("email");
                                                String username = jsonObject.getString("eng_name");
                                                String eng_contact = jsonObject.getString("contact");
                                                String eng_id = jsonObject.getString("eng_id");
                                                String district = jsonObject.getString("district");
                                                String project = jsonObject.getString("project");
                                                //  Toast.makeText(getApplicationContext(),project,Toast.LENGTH_LONG).show();
                                                editor.putString("email_eng", email);
                                                editor.putString("role", role);
                                                editor.putString("eng_id", eng_id);
                                                editor.putString("district", district);
                                                editor.putString("project", project);
                                                editor.putString("username", username);
                                                editor.putString("contact", eng_contact);
                                                editor.putString("login_status", "1");
                                                editor.commit();

                                                Toast.makeText(MainActivity.this, role + " , " + project, Toast.LENGTH_SHORT).show();
                                                if (role.equals("Admin") && project.equals("CREDA")) {
                                                    Intent intent = new Intent(getApplicationContext(), Owner_Portion_Activity.class);
                                                    startActivity(intent);
                                                    finish();

                                                } else {
                                                    Intent intent = new Intent(getApplicationContext(), BenificiaryListitem.class);
                                                    startActivity(intent);
                                                    finish();
                                                }
                                            }

                                        }


                                    } catch (JSONException e) {

//                                        AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
//                                        alertDialog.setTitle("Login failed");
//                                        alertDialog.setMessage("User Not Found Please Try Again!");
//                                        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK",
//                                                new DialogInterface.OnClickListener() {
//                                                    public void onClick(DialogInterface dialog, int which) {
//                                                        dialog.dismiss();
//                                                    }
//                                                });
//                                        alertDialog.show();
                                        e.printStackTrace();
                                    }

                                }
                            },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    try {
                                        AlertDialog.Builder builder = new AlertDialog.Builder(getApplicationContext());
                                        builder.setMessage("Something went  wrong");
                                        builder.show();
                                        Toast.makeText(MainActivity.this, "Error", Toast.LENGTH_SHORT).show();
                                        error.printStackTrace();
                                        progressDialog.dismiss();
                                    } catch (Exception ae) {

                                    }

                                }
                            }) {

                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String, String> params = new HashMap<>();
                            params.put("mobile", mobile);
                            params.put("password", password);

                            return params;
                        }
                    };
                    stringRequest.setRetryPolicy( new DefaultRetryPolicy( 3000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT ) );
                    stringRequest.setShouldCache( false );

                    MySingleton.getInstance(MainActivity.this).addTorequestque(stringRequest);

                } else {
                    Toast.makeText(getApplicationContext(), "Please Turn on Internet Connection", Toast.LENGTH_SHORT).show();
                }


            }
        });


    }

    @Override
    public void onBackPressed() {
        // Disable going back to the MainActivity
        moveTaskToBack(true);
    }

    public void displayAlert(String message) {

        builder.setMessage(message);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                input_mobile.setText("");
                input_password.setText("");

            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

}
