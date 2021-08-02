package update.gautamsolar.creda.Fragments;


import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.appcompat.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import update.gautamsolar.creda.BenificiaryListitem;

import update.gautamsolar.creda.R;
import update.gautamsolar.creda.SharedPrefManager;

/**
 * A simple {@link Fragment} subclass.
 */
public class FarmerDetail extends Fragment {
    private SQLiteOpenHelper openHelper;
    private SQLiteDatabase db;
    Context context;
    Cursor cursor;
    String reg, fname, product_type, pump_type, source_head;

    public FarmerDetail() {
        // Required empty public constructor
    }

    EditText regnumber, editTextbeneficiaryName;
    Button btn_farmer;
    String reg_no, engg_contact, engineer_role, regnumber_string, Engineer_Warehouse, editTextbeneficiaryName_string;

    AlertDialog.Builder builder;

    @Override
    public void onResume() {


        super.onResume();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


      //  openHelper = new DatabaseHelper(getActivity());

        engg_contact = SharedPrefManager.getInstance(getActivity()).getUserContact();
        engineer_role = SharedPrefManager.getInstance(getActivity()).getUserRole();


        View view = inflater.inflate(R.layout.fragment_farmer_detail, container, false);
        context = view.getContext();
        builder = new AlertDialog.Builder(getActivity());
        Intent intent = new Intent(getContext(), BenificiaryListitem.class);
        startActivity(intent);
//        StringRequest newstringRequest = new StringRequest(Request.Method.POST,"http://gautamsolar.co.in/credanew/credaAndroidApi/getalllist.php",
//                new Response.Listener<String>()
//                {
//                    @Override
//                    public void onResponse(String response) {
//                        Toast.makeText(getContext().getApplicationContext(),response,Toast.LENGTH_SHORT).show();
//
//                    }
//
//
//                },
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//
//                    }
//
//        })
//        {
//
//        };
        return view;


    }
}
