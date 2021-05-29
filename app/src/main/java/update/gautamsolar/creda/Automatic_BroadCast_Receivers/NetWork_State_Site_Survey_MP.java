package update.gautamsolar.creda.Automatic_BroadCast_Receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import androidx.preference.PreferenceManager;
import android.widget.Toast;

import com.activeandroid.query.Delete;
import com.activeandroid.query.Select;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import update.gautamsolar.creda.Constants.Constants;
import update.gautamsolar.creda.Database.Site_Survey_Table_MP;
import update.gautamsolar.creda.Database.Site_Survey_Table_Rajasthan;
import update.gautamsolar.creda.MySingleton;
import update.gautamsolar.creda.SiteSurvey;

public class NetWork_State_Site_Survey_MP extends BroadcastReceiver {


    private Context context;
    SharedPreferences sharedPreferences;
    int localdatacount;
    Constants constants;
    String Engineer_Contact, universallocaldata;

    //private String photo1,photo2,photo3,photo4,boredepth,boresize,waterlevel,borestatus,pump_head,existing_motor_running;
    @Override
    public void onReceive(Context context, Intent intent) {

        this.context = context;
constants= new Constants();
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        universallocaldata = sharedPreferences.getString("localdata", "");
//        Toast.makeText(context,universallocaldata,Toast.LENGTH_LONG).show();
        Engineer_Contact = sharedPreferences.getString("engcontact", "");

        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();

        if (activeNetwork != null) {
            //if connected to wifi or mobile data plan
            if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI ||
                    activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE) {
                List<Site_Survey_Table_MP> instalmodel = getSurvey();
                for (int i = 0; i < instalmodel.size(); i++) {
                    Site_Survey_Table_MP site_survey_table_mp = instalmodel.get(i);

                    Surveyload(site_survey_table_mp.foto1, site_survey_table_mp.foto2, site_survey_table_mp.foto3, site_survey_table_mp.foto4, site_survey_table_mp.boredepth, site_survey_table_mp.boresize, site_survey_table_mp.waterlevel, site_survey_table_mp.borestatus, site_survey_table_mp.exstmotorstring, site_survey_table_mp.Lat, site_survey_table_mp.Lon, site_survey_table_mp.eng_id, site_survey_table_mp.Regn, site_survey_table_mp.Dati,site_survey_table_mp.account_number,site_survey_table_mp.ifsc_code,site_survey_table_mp.bank_name,site_survey_table_mp.choosepumphead,site_survey_table_mp.adharnumber);


                }

            }
        }


    }

    private void Surveyload(final String photo1, final String photo2, final String photo3, final String photo4, final String boredepth, final String boresize, final String waterlevel,
                            final String bore_status, final String existing_motor_string, final String lat, final String lon, final String eng_id,
                            final String reg_no, final String datetime,final String account_number,final String ifsc_code,final String bank_name,final String choosepumphead,final String adharnumber) {

        StringRequest stringRequest = new StringRequest(Request.Method.POST,constants.SITE_SURVEY_API ,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                          //  Toast.makeText(context,response,Toast.LENGTH_LONG).show();
                            JSONObject obj = new JSONObject(response);
                            String message = obj.getString("error");
                            if (message.equals("false")) {
                                //updating the status in sqlite
                           try{
                                new Delete().from(Site_Survey_Table_MP.class).where("Regn =" + reg_no).execute();
                                Toast.makeText(context, "Site Survey MP Upload Successfully", Toast.LENGTH_SHORT).show();
                           }catch (Exception ae){
                           }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();

                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(context, "Site Survey MP Upload failed", Toast.LENGTH_LONG).show();

                        if (error instanceof NetworkError) {
                        } else if (error instanceof ServerError) {
                        } else if (error instanceof AuthFailureError) {
                        } else if (error instanceof ParseError) {
                        } else if (error instanceof NoConnectionError) {
                        } else if (error instanceof TimeoutError) {
                            Toast.makeText(context, "Time Out Error", Toast.LENGTH_LONG).show();


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
                params.put("account_no", account_number);
                params.put("bank_name", bank_name);
                params.put("ifsc_code", ifsc_code);
                params.put("pump_head", choosepumphead);
                params.put("adhar_no", adharnumber);
                params.put("boredepth", boredepth);
                params.put("boredsize", boresize);
                params.put("borewaterlevel", waterlevel);
                params.put("radioborestatus", bore_status);
                params.put("edittextexisting_motor_string", existing_motor_string);
                params.put("lat", lat);
                params.put("lon", lon);
                params.put("eng_id", eng_id);
                params.put("reg_no", reg_no);
                params.put("datetime", datetime);
                return params;
            }
        };

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(10000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        stringRequest.setShouldCache(false);
        MySingleton.getInstance(context).addTorequestque(stringRequest);
    }

    public List<Site_Survey_Table_MP> getSurvey() {
        //Getting all items stored in Inventory table
        return new Select()
                .from(Site_Survey_Table_MP.class)
                .execute();


    }


}
