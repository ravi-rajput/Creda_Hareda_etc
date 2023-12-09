package update.gautamsolar.creda.Automatic_BroadCast_Receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import androidx.preference.PreferenceManager;

import android.os.Build;
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

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import update.gautamsolar.creda.Constants.Constants;
import update.gautamsolar.creda.Database.InstalltionTable;
import update.gautamsolar.creda.MultipartUtility;
import update.gautamsolar.creda.MySingleton;
import update.gautamsolar.creda.UploadBenificieryPic;

public class NetworkStateCheckerI extends BroadcastReceiver {

    private Context context;
    String video_path, reg_no, Engineer_Contact;
    int id;
    SharedPreferences sharedPreferences;
    Constants constants;

    @Override
    public void onReceive(Context context, Intent intent) {

        this.context = context;
        constants= new Constants();
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        Engineer_Contact = sharedPreferences.getString("engcontact", "");

        //db = new DatabaseHelper(context);

        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (cm != null) {
                NetworkCapabilities capabilities = cm.getNetworkCapabilities(cm.getActiveNetwork());
                if (capabilities != null) {
                    List<InstalltionTable> instalmodel = getInstall();
                    for (int i = 0; i < instalmodel.size(); i++) {
                        InstalltionTable installi = instalmodel.get(i);
                        loadNames(installi.foto1, installi.foto2, installi.foto3, installi.foto4, installi.foto5, installi.foto6, installi.foto7, installi.foto8,installi.foto9,installi.Lat, installi.Lon, installi.eng_id, installi.Regn, installi.Dati);
                    }
                }
            }
        }

//        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
//
//        if (activeNetwork != null) {
//            //if connected to wifi or mobile data plan
//            if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI ||
//                    activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE) {
//                List<InstalltionTable> instalmodel = getInstall();
//                for (int i = 0; i < instalmodel.size(); i++) {
//                    InstalltionTable installi = instalmodel.get(i);
//                    loadNames(installi.foto1, installi.foto2, installi.foto3, installi.foto4, installi.foto5, installi.foto6, installi.foto7, installi.foto8,installi.Lat, installi.Lon, installi.eng_id, installi.Regn, installi.Dati);
//
//
//
//                }
//
//            }
//        }


    }

    private void loadNames(final String photo1, final String photo2, final String photo3, final String photo4, final String photo5, final String photo6, final String photo7,
                           final String photo8,final String photo9, final String lat, final String lon, final String eng_id, final String reg_no, final String datetime) {


        StringRequest stringRequest = new StringRequest(Request.Method.POST,constants.INSTALLATION_API,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //   Toast.makeText(context,response,Toast.LENGTH_SHORT).show();

                        try {
                            JSONObject obj = new JSONObject(response);

                            String message = obj.getString("message");
                            if (message.equals("Upload Successfully")) {
                                //updating the status in sqlite
                                //  db.updateNameStatusI(id, UploadBenificieryPic.NAME_SYNCED_WITH_SERVERI);

                                //sending the broadcast to refresh the list
try{
                                new Delete().from(InstalltionTable.class).where("Regn =" + reg_no).execute();
                                Toast.makeText(context, "Installation Data  Creda Upload Successfully", Toast.LENGTH_SHORT).show();
}catch (Exception ae){
}                        }
                        } catch (JSONException e) {
                            Toast.makeText(context, "Instalation Data Creda Upload failed", Toast.LENGTH_LONG).show();
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(context, "Installation Data Creda Upload failed", Toast.LENGTH_LONG).show();

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
                params.put("photo5", photo5);
                params.put("photo6", photo6);
                params.put("photo7", photo7);
                params.put("photo8", photo8);
                params.put("photo9", photo9);
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


    private void uploadVideo1() {
        class UploadVideo extends AsyncTask<Void, Void, String> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();


            }


            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                //      db.updateNameStatusB(id, UploadBenificieryPic.NAME_SYNCED_WITH_SERVERI);

                //sending the broadcast to refresh the list
                context.sendBroadcast(new Intent(UploadBenificieryPic.DATA_SAVED_BROADCASTI));

                // process_status.setText(Html.fromHtml("<b>Uploaded at <a href='" + s + "'>" + s + "</a></b>"));
                //process_status.setMovementMethod(LinkMovementMethod.getInstance());

            }

            @Override
            protected String doInBackground(Void... params) {
                try {

                    String charset = "UTF-8";
                    File uploadFile1 = new File(video_path);
                    String requestURL = "http://gautamsolar.co.in/streetlightapi/uploadvideo_to_server1.php";

                    MultipartUtility multipart = new MultipartUtility(requestURL, charset);
                    multipart.addFormField("reg_no", reg_no);

                    multipart.addFilePart("myFile", uploadFile1);

                    List<String> response = multipart.finish();

                    Log.v("rht", "SERVER REPLIED:");

                    for (String line : response) {
                        Log.v("rht", "Line : " + line);
                        //   Toast.makeText(, response.toString(), Toast.LENGTH_SHORT).show();

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return "ok";


             /*   Upload u = new Upload();
                String msg = u.uploadVideo(video_path,reg_no);
                return msg;*/
            }
        }


    }

    public List<InstalltionTable> getInstall() {
        //Getting all items stored in Inventory table
        return new Select()
                .from(InstalltionTable.class)
                .execute();
    }


}
