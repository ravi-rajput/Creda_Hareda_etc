package update.gautamsolar.creda.Automatic_BroadCast_Receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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

import update.gautamsolar.creda.BenificiaryListitem;
import update.gautamsolar.creda.Constants.Constants;
import update.gautamsolar.creda.CredaModel;
import update.gautamsolar.creda.Database.RMU_Table;
import update.gautamsolar.creda.Database.Rmu_Table_Rajasthan;
import update.gautamsolar.creda.MySingleton;

public class NetWork_State_RMU_Rajasthan extends BroadcastReceiver {

    private Context context;
    SharedPreferences sharedPreferences;
    String Engineer_Contact;
    String universallocaldate;
    BenificiaryListitem benificiaryListitem;

    Constants constants;
    String KEYPHOTO1, KEYPHOTO2, KEYPHOTO3, KEYPHOTO4, KEYPHOTO5, Lat, Lon, Eng_Contact, REGNO, DATETIME;

    @Override
    public void onReceive(Context context, Intent intent) {

        this.context = context;
        constants= new Constants();
        benificiaryListitem = new BenificiaryListitem();
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        Engineer_Contact = sharedPreferences.getString("engcontact", "");


        List<Rmu_Table_Rajasthan> rmu_table_rajasthans = getAlldate();
        //Adding all the items of the inventories to arraylist
        for (int i = 0; i < rmu_table_rajasthans.size(); i++) {
            Rmu_Table_Rajasthan rmu_table_rajasthan = rmu_table_rajasthans.get(i);
            loadNames(rmu_table_rajasthan.fotoRMu, rmu_table_rajasthan.latrmu, rmu_table_rajasthan.lonrmu, rmu_table_rajasthan.datetime, rmu_table_rajasthan.NewRMuNO, rmu_table_rajasthan.eng_id, rmu_table_rajasthan.regn_no_rmu);


        }


    }


    public void loadNames(final String Fotormu, final String LatRmu, final String LonRmu, final String dateTime, final String NEw_RMu_NO, final String eng_id, final String Regn_No) {


        StringRequest stringRequest = new StringRequest(Request.Method.POST, constants.RMU_API,
                new Response.Listener<String>() {


                    @Override
                    public void onResponse(String response) {
                        //      Toast.makeText(context,response,Toast.LENGTH_SHORT).show();

                        try {
                            JSONObject obj = new JSONObject(response);
                            String upload = obj.getString("error");
                            //    Toast.makeText(context,upload,Toast.LENGTH_SHORT).show();
                            if (upload.equals("false")) {
try{
                                new Delete().from(Rmu_Table_Rajasthan.class).where("regn_no_rmu =" + Regn_No).execute();
                                Toast.makeText(context, "RMU Data Rajasthan Upload Successfully", Toast.LENGTH_SHORT).show();
}catch (Exception ae){
}
                            }
                        } catch (JSONException e) {
                            Toast.makeText(context, "RMU Data  Rajasthan  Upload failed", Toast.LENGTH_LONG).show();
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(context, "RMU Data Upload failed", Toast.LENGTH_LONG).show();
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
                params.put("photo1", Fotormu);
                params.put("lat", LatRmu);
                params.put("lon", LonRmu);
                params.put("eng_id", eng_id);
                params.put("reg_no", Regn_No);
                params.put("datetime", dateTime);
                params.put("new_controler_rms_id", NEw_RMu_NO);

                return params;
            }
        };

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(10000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        stringRequest.setShouldCache(false);
        MySingleton.getInstance(context).addTorequestque(stringRequest);

    }

    public List<Rmu_Table_Rajasthan> getAlldate() {
        //Getting all items stored in Inventory table
        return new Select()
                .from(Rmu_Table_Rajasthan.class)
                .execute();
    }


}
