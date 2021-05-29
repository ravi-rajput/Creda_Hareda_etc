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
import update.gautamsolar.creda.Database.CMC_MP_Table;
import update.gautamsolar.creda.Database.RMU_Table;
import update.gautamsolar.creda.Database.Rmu_Table_MP;
import update.gautamsolar.creda.Database.Rmu_Table_Rajasthan;
import update.gautamsolar.creda.MySingleton;

public class Network_State_CMC_MP extends BroadcastReceiver {

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


        List<CMC_MP_Table> cmc_mp_tables = getAlldate();
        //Adding all the items of the inventories to arraylist
        for (int i = 0; i < cmc_mp_tables.size(); i++) {
            CMC_MP_Table cmc_mp_table = cmc_mp_tables.get(i);
            loadNames(cmc_mp_table.foto1, cmc_mp_table.foto2, cmc_mp_table.foto3, cmc_mp_table.foto4, cmc_mp_table.Lat, cmc_mp_table.Lon, cmc_mp_table.eng_id,cmc_mp_table.button_id,cmc_mp_table.Regn,cmc_mp_table.Dati);


        }


    }


    public void loadNames(final String foto1, final String foto2, final String foto3, final String foto4, final String lat, final String lon, final String eng_id,final String button_id,final  String reg_no,final String Date) {


        StringRequest stringRequest = new StringRequest(Request.Method.POST, constants.CMC_UPDATE,
                new Response.Listener<String>() {


                    @Override
                    public void onResponse(String response) {
                        //      Toast.makeText(context,response,Toast.LENGTH_SHORT).show();

                        try {
                            JSONObject obj = new JSONObject(response);
                            String upload = obj.getString("error");
                            //    Toast.makeText(context,upload,Toast.LENGTH_SHORT).show();
                            if (upload.equals("false")) {
try {
    new Delete().from(CMC_MP_Table.class).where("Regn =" + reg_no).execute();
}catch (Exception ae){
}

                                Toast.makeText(context, "CMC Data MP Upload Successfully", Toast.LENGTH_SHORT).show();

                            }
                        } catch (JSONException e) {
                            Toast.makeText(context, "CMC Data MP Upload failed", Toast.LENGTH_LONG).show();
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(context, "CMC Data Upload failed", Toast.LENGTH_LONG).show();
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
                params.put("photo1", foto1);
                params.put("photo2", foto2);
                params.put("photo3", foto3);
                params.put("photo4", foto4);
                params.put("lat", lat);
                params.put("lon", lon);
                params.put("eng_id", eng_id);
                params.put("reg_no", reg_no);
                params.put("datetime", Date);
                params.put("button_id", button_id);

                return params;
            }
        };

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(10000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        stringRequest.setShouldCache(false);
        MySingleton.getInstance(context).addTorequestque(stringRequest);

    }

    public List<CMC_MP_Table> getAlldate() {
        //Getting all items stored in Inventory table
        return new Select()
                .from(CMC_MP_Table.class)
                .execute();
    }


}
