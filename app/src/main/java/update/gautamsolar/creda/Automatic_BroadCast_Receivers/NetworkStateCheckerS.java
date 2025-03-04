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
import update.gautamsolar.creda.MySingleton;
import update.gautamsolar.creda.Database.SurveyTable;

public class NetworkStateCheckerS extends BroadcastReceiver {


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
//
//       if (universallocaldata.equals("0"))
//       {
//
//       }
//       else
//       {
//           //localdatacount   = Integer.parseInt(universallocaldata);
//         int finallocalcount= localdatacount-1;
//         SharedPreferences.Editor editor= sharedPreferences.edit();
//         editor.putString("localdata",String.valueOf(localdatacount));
//         editor.commit();
//       }

        //   db = new DatabaseHelper(context);

        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();

        if (activeNetwork != null) {
            //if connected to wifi or mobile data plan
            if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI ||
                    activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE) {
                List<SurveyTable> instalmodel = getSurvey();
                for (int i = 0; i < instalmodel.size(); i++) {
                    SurveyTable survey = instalmodel.get(i);

                    Surveyload(survey.foto1, survey.foto2, survey.foto3, survey.foto4,survey.foto5, survey.boredepth,
                            survey.boresize, survey.waterlevel, survey.borestatus, survey.exstmotorstring, survey.Lat,
                            survey.Lon, survey.eng_id, survey.Regn, survey.Dati,survey.foto6,survey.foto7,survey.foto8,survey.foto9,survey.foto10,survey.foto11,
                            survey.radioCleanString,survey.radioPumpHeadString,survey.radioSatisfyString,survey.radioLightString,survey.status);


                }

            }
        }


    }

    private void Surveyload(final String photo1, final String photo2, final String photo3, final String photo4, final String photo5, final String boredepth, final String boresize, final String waterlevel,
                            final String bore_status, final String existing_motor_string, final String lat, final String lon, final String eng_id,
                            final String reg_no, final String datetime, final String photo6, final String photo7, final String photo8,final String photo9,final String photo10,final String photo11, String radioCleanString, String radioPumpHeadString, String radioSatisfyString, String radioLightString, String status) {

        Constants constants = new Constants();
        String apiName = "";
        if (sharedPreferences.getString("lead_phase", "").equalsIgnoreCase("HAREDA_PHASE3")||sharedPreferences.getString("lead_phase", "").equalsIgnoreCase("HAREDA_PHASE4")||sharedPreferences.getString("lead_phase", "").equalsIgnoreCase("GALO_PHASE1")||sharedPreferences.getString("lead_phase", "").equalsIgnoreCase("MSEDCL_PHASE1")) {
            apiName = constants.SITE_SURVEY_API_HAREDA;
        } else {
            apiName = constants.SITE_SURVEY_API;
        }
        StringRequest stringRequest = new StringRequest(Request.Method.POST, apiName,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject obj = new JSONObject(response);
                            String message = obj.getString("error");
                            if (message.equals("false")) {
                                //updating the status in sqlite
                             try{
                                new Delete().from(SurveyTable.class).where("Regn =" + reg_no).execute();
                                Toast.makeText(context, "Site Survey Creda Upload Successfully", Toast.LENGTH_SHORT).show();
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
                        Toast.makeText(context, "Site Survey Creda Upload failed", Toast.LENGTH_LONG).show();

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
//                params.put("photo2", photo2);
//                params.put("photo3", photo3);
//                params.put("photo4", photo4);
                params.put("photo5", photo5);
                params.put("status", status);
//                params.put("photo6", photo6);
//                params.put("photo7", photo7);
//                params.put("photo8", photo8);
//                params.put("boredepth", boredepth);
//                params.put("boredsize", boresize);
                params.put("borewaterlevel", waterlevel);
                params.put("radioborestatus", bore_status);
//                params.put("edittextexisting_motor_string", existing_motor_string);
                params.put("lat", lat);
                params.put("lon", lon);
                params.put("eng_id", eng_id);
                params.put("reg_no", reg_no);
                params.put("datetime", datetime);


                if (sharedPreferences.getString("lead_phase", "").equalsIgnoreCase("HAREDA_PHASE3")||sharedPreferences.getString("lead_phase", "").equalsIgnoreCase("HAREDA_PHASE4")||sharedPreferences.getString("lead_phase", "").equalsIgnoreCase("GALO_PHASE1")||sharedPreferences.getString("lead_phase", "").equalsIgnoreCase("MSEDCL_PHASE1")) {
                    params.put("bor_clean_status", radioCleanString);
                    params.put("pump_head", radioPumpHeadString);
                    params.put("customer_satify_status", radioSatisfyString);
                    params.put("power_connection_status", radioLightString);
                    params.put("photo2", photo2);
                    params.put("photo3", photo6);
                    params.put("photo4", photo7);
                    params.put("bore_check_image", photo11);
                    params.put("farad_photo", photo9);
                    params.put("chalan_photo", photo10);
                    params.put("Consent_Letter_photo", photo4);
                    params.put("Consent_Letter_photo_farmer", photo8);
                    params.put("block", boredepth);
                    params.put("village", boresize);

                } else {
                    params.put("photo2", photo2);
                    params.put("photo4", photo4);
                    params.put("photo6", photo6);
                    params.put("photo7", photo7);
                    params.put("photo8", photo8);
                    params.put("boredepth", boredepth);
                    params.put("boredsize", boresize);
                    params.put("edittextexisting_motor_string", existing_motor_string);
                }

                return params;
            }
        };

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(10000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        stringRequest.setShouldCache(false);
        MySingleton.getInstance(context).addTorequestque(stringRequest);
    }

    public List<SurveyTable> getSurvey() {
        //Getting all items stored in Inventory table
        return new Select()
                .from(SurveyTable.class)
                .execute();
    }


}
