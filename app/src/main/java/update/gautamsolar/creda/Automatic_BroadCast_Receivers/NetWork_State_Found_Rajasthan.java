package update.gautamsolar.creda.Automatic_BroadCast_Receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import androidx.preference.PreferenceManager;
import android.util.Log;
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
import update.gautamsolar.creda.Database.Foundation_Table_Rajasthan;
import update.gautamsolar.creda.MySingleton;

public class NetWork_State_Found_Rajasthan extends BroadcastReceiver {

    private Context context;
Constants constants;

    SharedPreferences sharedPreferences;
    String KEYPHOTO1, KEYPHOTO2, KEYPHOTO3, KEYPHOTO4, KEYPHOTO5, Lat, Lon, Eng_Contact, REGNO, Engineer_Contact;

    @Override
    public void onReceive(Context context, Intent intent) {

        this.context = context;
        constants= new Constants();
        List<Foundation_Table_Rajasthan> credaModels = getAlldate();
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);

        //Adding all the items of the inventories to arraylist
        for (int i = 0; i < credaModels.size(); i++) {
//            Foundation_Table_MP foundation_table_mp = credaModels.get(i);
            Foundation_Table_Rajasthan foundation_table_rajasthan= credaModels.get(i);
            loadNames(foundation_table_rajasthan.foto1, foundation_table_rajasthan.foto2, foundation_table_rajasthan.foto3, foundation_table_rajasthan.foto4,foundation_table_rajasthan.eng_id, foundation_table_rajasthan.Lat, foundation_table_rajasthan.Lon, foundation_table_rajasthan.Regn, foundation_table_rajasthan.Dati);
            Log.d("DATACONTACT", String.valueOf(foundation_table_rajasthan.Eng_contact));


        }


    }


    public void loadNames(final String phot1, final String phot2, final String phot3, final String phot4, final String eng_id, final String Latti, final String Longi,
                         final String RegisterNo, final String Date_time) {


        StringRequest stringRequest = new StringRequest(Request.Method.POST,constants.FOUNDATION_API,
                new Response.Listener<String>() {


                    @Override
                    public void onResponse(String response) {
                        //   Toast.makeText(context,response,Toast.LENGTH_SHORT).show();

                        try {
                            JSONObject obj = new JSONObject(response);
                            String upload = obj.getString("error");
                            //  Toast.makeText(context,upload,Toast.LENGTH_SHORT).show();
                            if (upload.equals("false")) {
try{
                                new Delete().from(Foundation_Table_Rajasthan.class).where("Regn =" + RegisterNo).execute();
                                Toast.makeText(context, "Foundation Rajsthan Data Upload Successfully", Toast.LENGTH_SHORT).show();
                            }catch (Exception ae){
                            }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(context, "Foundation Rajasthan Data Upload failed", Toast.LENGTH_LONG).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Toast.makeText(context, "Foundation Rajasthan Data Upload failed", Toast.LENGTH_LONG).show();

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
                params.put("photo1", phot1);
                params.put("photo2", phot2);
                params.put("photo3", phot3);
                params.put("photo4", phot4);
                params.put("eng_id", eng_id);
                params.put("lat", Latti);
                params.put("lon", Longi);
                params.put("reg_no", RegisterNo);
                params.put("datetime", Date_time);
                return params;
            }
        };

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(10000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        stringRequest.setShouldCache(false);
        MySingleton.getInstance(context).addTorequestque(stringRequest);

    }

    public List<Foundation_Table_Rajasthan> getAlldate() {
        //Getting all items stored in Inventory table
        return new Select()
                .from(Foundation_Table_Rajasthan.class)
                .execute();
    }


}
