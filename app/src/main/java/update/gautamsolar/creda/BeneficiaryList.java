package update.gautamsolar.creda;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import update.gautamsolar.creda.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BeneficiaryList extends AppCompatActivity {

    List<GetDataAdapter> GetDataAdapter1;

    RecyclerView recyclerView;

    RecyclerView.LayoutManager recyclerViewlayoutManager;

    RecyclerView.Adapter recyclerViewadapter;

    ProgressBar progressBar;


    String GET_JSON_DATA_HTTP_URL = "http://gautamsolar.co.in/credanew/credaAndroidApi/BeneficiaryListApi.php";
    String JSON_ID = "id";
    String JSON_NAME = "name";
    String BenificiaryName;
    String JSON_PHONE_NUMBER = "phone_number";
    String DISPATCH_STATUS = "dispatch_status";
    String PUMP_TYPE = "pump_type";
    String STRUCTURE = "structure_type";

    //JsonArrayRequest jsonArrayRequest ;

    //RequestQueue requestQueue ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_beneficiary_list );

        Bundle bundleBeneficiaryList= getIntent().getExtras();
        BenificiaryName=  bundleBeneficiaryList.getString("fname");

        GetDataAdapter1 = new ArrayList<>();

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView1);
        recyclerView.setHasFixedSize(   true );

        progressBar = (ProgressBar) findViewById(R.id.progressBar1);



        recyclerView.setHasFixedSize(true);

        recyclerViewlayoutManager = new LinearLayoutManager(this);

        recyclerView.setLayoutManager(recyclerViewlayoutManager);

        JSON_DATA_WEB_CALL();

    }

    public void JSON_DATA_WEB_CALL(){

        StringRequest stringRequest = new StringRequest( Request.Method.POST,GET_JSON_DATA_HTTP_URL,

                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        progressBar.setVisibility( View.GONE);
                        try {
                            JSONArray jsonArray = new JSONArray( response );
                            JSON_PARSE_DATA_AFTER_WEBCALL(jsonArray);
                        }
                        catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(BeneficiaryList.this,"Error Found", Toast.LENGTH_SHORT).show();
                        error.printStackTrace();
                    }
                }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();


                params.put( "BenificiaryName", BenificiaryName );




                return params;
            }

        };

        MySingleton.getInstance(getApplicationContext() ).addTorequestque(stringRequest);
    }

    public void JSON_PARSE_DATA_AFTER_WEBCALL(JSONArray array){

        for(int i = 0; i<array.length(); i++) {

            GetDataAdapter GetDataAdapter2 = new GetDataAdapter();

            JSONObject json = null;
            try {
                json = array.getJSONObject(i);
                GetDataAdapter2.setId(json.getInt(JSON_ID));
                GetDataAdapter2.setName(json.getString(JSON_NAME));
                GetDataAdapter2.setPhone_number(json.getString(JSON_PHONE_NUMBER));
                GetDataAdapter2.setPump_type(json.getString(PUMP_TYPE));
                GetDataAdapter2.setdispatch_status(json.getString(DISPATCH_STATUS));
                GetDataAdapter2.setStructure(json.getString(STRUCTURE));


            } catch (JSONException e) {

                e.printStackTrace();
            }
            GetDataAdapter1.add(GetDataAdapter2);
        }

        recyclerViewadapter = new RecyclerViewAdapter(GetDataAdapter1, this);

        recyclerView.setAdapter(recyclerViewadapter);
    }

}
