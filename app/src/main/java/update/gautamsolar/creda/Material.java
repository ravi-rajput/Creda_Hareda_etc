package update.gautamsolar.creda;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.preference.PreferenceManager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

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
import update.gautamsolar.creda.R;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import update.gautamsolar.creda.Constants.Constants;

public class Material extends AppCompatActivity {

    /*static String panelnew;*/
    private static final int PERMISSION_REQUEST_CODE = 40;
    private static final int FIRST_REQUEST_CODE = 20;
    private static final int SECOND_REQUEST_CODE = 21;
    private static final int THIRD_REQUEST_CODE = 22;
    private static final int FOURTH_REQUEST_CODE = 23;
    private static final int FIFTH_REQUEST_CODE = 24;
    private static final int SIX_REQUEST_CODE = 25;
    private static final int SEVEN_REQUEST_CODE = 26;
    private static final int EIGHT_REQUEST_CODE = 27;
    private static final int NINE_REQUEST_CODE = 28;
    private static final int TEN_REQUEST_CODE = 29;
    private static final int ELEVEN_REQUEST_CODE = 30;
    private static final int TWELVE_REQUEST_CODE = 31;
    private static final int THIRTEEN_REQUEST_CODE = 32;
    private static final int FOURTEEN_REQUEST_CODE = 33;
    private static final int FIFTEEN_REQUEST_CODE = 34;
    private static final int SIXTEEN_REQUEST_CODE = 35;
    private static final int SEVENTEENREQUEST_CODE = 40;
    private static final int EIGHTEENREQUEST_CODE = 41;
    private static final int NINETEENREQUEST_CODE = 42;
    private static final int TWENTYREQUEST_CODE = 43;
    private static final int TWENTYONEREQUEST_CODE = 44;
    private static final int TWENTYTWOREQUEST_CODE = 45;
    private static final int TWENTYTHREEREQUEST_CODE = 46;
    private static final int TWENTYFOURREQUEST_CODE = 47;
    private static final int TWENTYFIVEREQUEST_CODE = 48;
    private static final int TWENTYSIXREQUEST_CODE = 49;
    private static final int TWENTYSEVENREQUEST_CODE = 50;
    private static final int TWENTYEIGHTREQUEST_CODE = 51;


    private static final int SEVENTEEN_REQUEST_CODE = 36;
    private static final int EIGHTEEN_REQUEST_CODE = 37;
    private static final int NINTEEN_REQUEST_CODE = 38;
    private static final int TWENTY_REQUEST_CODE = 39;
    AlertDialog.Builder builder;
    RadioGroup radiopipe, radiocable;
    RadioButton radio100pipe, radio70pipe, radio50pipe,
            radio30pipe, radio100cable,
            radio70cable, radio50cable, radio30cable;
    Dialog dialog;
    CheckBox structure_checkbox, accessories_checkbox, farma_checkbox, rod_checkbox;


    String radio_pipe_string,
            structure_checkbox_string, radio_cable_string, accessories_checkbox_string, farma_checkbox_string,
            rod_checkbox_string, pump_type, pump_capacity;


    ImageButton panel1btn, panel2btn,
            panel3btn, panel4btn, panel5btn, panel6btn,
            panel7btn, panel8btn, panel9btn, panel10btn, panel11btn,
            panel12btn, panel13btn, panel14btn, panel15btn, panel16btn, panel17btn, panel18btn, panel19btn, panel20btn, panel21btn, panel22btn, panel23btn, panel24btn, panel25btn, panel26btn, panel27btn, panel28btn, btnPumpScan, btnMotorScan,
            ControllerbtnNumber, btnRmuNumber;
    Button btnDisplay,btnVideo;
    ImageButton btnAdd;
    ProgressDialog progressDialog;
    TextView Beneficiary_textName, Beneficiary_txtRegNo;
    ProgressDialog pb;
    TextView Father, Contactid, Villageide, Pumpide, Contactide, Blockid, districtid, numberid;
    EditText villageName,panel1edit, panel2edit,
            panel3edit, panel4edit, panel5edit,
            panel6edit, panel7edit, panel8edit, panel9edit,
            panel10edit, panel11edit, panel12edit, panel13edit,
            panel14edit, panel15edit, panel16edit, panel17edit, panel18edit, panel19edit, panel20edit, panel21edit, panel22edit, panel23edit, panel24edit, panel25edit, panel26edit, panel27edit, panel28edit, pumpSerail, MotorSerial, RmuNumber, ControllerNumber, SimNumber, MobileNumber;
    String eng_id, Engineer_contact, BenificiaryName, reg_no, PumpSerial, engineer_role,
            motorSerial, rmuNumber, controllerNumber;
    String Engineer_Contact, pan1, pan2, pan3, pan4, pan5, pan6, pan7, pan8,
            pan9, pan10, pan11, pan12, pan13, pan14, pan15, pan16, pan17, pan18, pan19, pan20, pan21, pan22, pan23, pan24, pan25, pan26, pan27, pan28, pannew1, pannew2, pannew3,
            pannew4, pannew5, pannew6, pannew7, pannew8, pannew9, pannew10, pannew11, pannew12, pannew13, pannew14,
            pannew15, pannew16, pannew17, pannew18, pannew19, pannew20, pannew21, pannew22, pannew23, pannew24, pannew25, pannew26, pannew27, pannew28, fathername, benifname, regnnumber,
            contact, block, village, dispatch_status, simno, rmuno, mobileno,
            controler_srno, controler_rms_id, pumpserialnew, motorserialnew, project;
    private IntentIntegrator qrScan1, qrScan2, qrScan3, qrScan4, qrScan5, qrScan6,
            qrScan7, qrScan8, qrScan9, qrScan10, qrScan11, qrScan12, qrScan13, qrScan14, qrScan15, qrScan16, qrScan_17, qrScan_18, qrScan_19, qrScan_20, qrScan_21, qrScan_22, qrScan_23, qrScan_24, qrScan_25, qrScan_26, qrScan_27, qrScan_28,
            qrScan17, qrScan18, qrScan19, qrScan20;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_material);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        Engineer_Contact = sharedPreferences.getString("engcontact", "");

        builder = new AlertDialog.Builder(Material.this);
        eng_id = sharedPreferences.getString("eng_id", "");
        /*TEXT VIEW*/


        farma_checkbox = findViewById(R.id.farma_checkbox);
        btnVideo = findViewById(R.id.btn_video);
        if(sharedPreferences.getString("lead_phase", "").equalsIgnoreCase("HAREDA_PHASE3")){
            btnVideo.setVisibility(View.VISIBLE);
        }else{
            btnVideo.setVisibility(View.GONE);
        }

        btnVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Material.this, VideoCaptureActivity.class);
                i.putExtra("regnnumber", regnnumber);
                i.putExtra("route", "material");
                startActivity(i);
            }
        });

        rod_checkbox = findViewById(R.id.rod_checkbox);
        villageName = findViewById(R.id.villageName);
        dialog = new Dialog(Material.this); // Cont
        pb = new ProgressDialog(this, R.style.MyGravity);
        pb.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        pb.setIndeterminateDrawable(getResources().getDrawable(R.drawable.custom_progress_dialog));
        pb.setCancelable(false);

//        Beneficiary_textName= findViewById( R.id.Beneficiary_textName );
//        Beneficiary_txtRegNo= findViewById( R.id.Beneficiary_txtRegNo );

        /*16btnpanel*/
        radiopipe = (RadioGroup) findViewById(R.id.radiopipe);
        radio100pipe = (RadioButton) findViewById(R.id.radio100pipe);
        radio70pipe = (RadioButton) findViewById(R.id.radio70pipe);
        radio50pipe = (RadioButton) findViewById(R.id.radio50pipe);
        radio30pipe = (RadioButton) findViewById(R.id.radio30pipe);
        SimNumber = (EditText) findViewById(R.id.simeone);
        MobileNumber = (EditText) findViewById(R.id.mobileone);

        radiocable = (RadioGroup) findViewById(R.id.radiocable);
        radio100cable = (RadioButton) findViewById(R.id.radio100cable);
        radio70cable = (RadioButton) findViewById(R.id.radio70cable);
        radio50cable = (RadioButton) findViewById(R.id.radio50cable);
        radio30cable = (RadioButton) findViewById(R.id.radio30cable);
        Father = (TextView) findViewById(R.id.fatheride);
        Contactid = (TextView) findViewById(R.id.contactide);
        Villageide = (TextView) findViewById(R.id.villageide);
        Pumpide = (TextView) findViewById(R.id.benificiaryide);
        Contactide = (TextView) findViewById(R.id.contactide);
        Blockid = (TextView) findViewById(R.id.blockide);
        numberid = findViewById(R.id.numberid);
        Bundle bundlem = getIntent().getExtras();

        fathername = bundlem.getString("fathername");
        regnnumber = bundlem.getString("regnnumber");
        benifname = bundlem.getString("benifname");
        village = bundlem.getString("village");
        block = bundlem.getString("block");
        contact = bundlem.getString("contact");
        dispatch_status = bundlem.getString("dispatch_status");
        project = sharedPreferences.getString("project", "");
        Father.setText(fathername);
        Contactid.setText(contact);
        Villageide.setText(village);
        Blockid.setText(block);
        Pumpide.setText(benifname);
        numberid.setText(regnnumber);
        dialog.setContentView(R.layout.uploaddialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setCancelable(false);


        structure_checkbox = findViewById(R.id.structure_checkbox);
        accessories_checkbox = findViewById(R.id.accessories_checkbox);


        panel1btn = findViewById(R.id.panel1btn);
        panel2btn = findViewById(R.id.panel2btn);
        panel3btn = findViewById(R.id.panel3btn);
        panel4btn = findViewById(R.id.panel4btn);
        panel5btn = findViewById(R.id.panel5btn);
        panel6btn = findViewById(R.id.panel6btn);
        panel7btn = findViewById(R.id.panel7btn);
        panel8btn = findViewById(R.id.panel8btn);
        panel9btn = findViewById(R.id.panel9btn);
        panel10btn = findViewById(R.id.panel10btn);
        panel11btn = findViewById(R.id.panel11btn);
        panel12btn = findViewById(R.id.panel12btn);
        panel13btn = findViewById(R.id.panel13btn);
        panel14btn = findViewById(R.id.panel14btn);
        panel15btn = findViewById(R.id.panel15btn);
        panel16btn = findViewById(R.id.panel16btn);
        panel17btn = findViewById(R.id.panel17btn);
        panel18btn = findViewById(R.id.panel18btn);
        panel19btn = findViewById(R.id.panel19btn);
        panel20btn = findViewById(R.id.panel20btn);
        panel21btn = findViewById(R.id.panel21btn);
        panel22btn = findViewById(R.id.panel22btn);
        panel23btn = findViewById(R.id.panel23btn);
        panel24btn = findViewById(R.id.panel24btn);
        panel25btn = findViewById(R.id.panel25btn);
        panel26btn = findViewById(R.id.panel26btn);
        panel27btn = findViewById(R.id.panel27btn);
        panel28btn = findViewById(R.id.panel28btn);

        btnPumpScan = findViewById(R.id.btnPumpScan);
        btnMotorScan = findViewById(R.id.btnMotorScan);
        ControllerbtnNumber = findViewById(R.id.ControllerbtnNumber);
        btnRmuNumber = findViewById(R.id.btnRmuNumber);



        /*end 16 panel*/
        /*Panel 16 Edit Text*/
        panel1edit = findViewById(R.id.panel1edit);
        panel2edit = findViewById(R.id.panel2edit);
        panel3edit = findViewById(R.id.panel3edit);
        panel4edit = findViewById(R.id.panel4edit);
        panel5edit = findViewById(R.id.panel5edit);
        panel6edit = findViewById(R.id.panel6edit);
        panel7edit = findViewById(R.id.panel7edit);
        panel8edit = findViewById(R.id.panel8edit);
        panel9edit = findViewById(R.id.panel9edit);
        panel10edit = findViewById(R.id.panel10edit);
        panel11edit = findViewById(R.id.panel11edit);
        panel12edit = findViewById(R.id.panel12edit);
        panel13edit = findViewById(R.id.panel13edit);
        panel14edit = findViewById(R.id.panel14edit);
        panel15edit = findViewById(R.id.panel15edit);
        panel16edit = findViewById(R.id.panel16edit);
        panel17edit = findViewById(R.id.panel17edit);
        panel18edit = findViewById(R.id.panel18edit);
        panel19edit = findViewById(R.id.panel19edit);
        panel20edit = findViewById(R.id.panel20edit);
        panel21edit = findViewById(R.id.panel21edit);
        panel22edit = findViewById(R.id.panel22edit);
        panel23edit = findViewById(R.id.panel23edit);
        panel24edit = findViewById(R.id.panel24edit);
        panel25edit = findViewById(R.id.panel25edit);
        panel26edit = findViewById(R.id.panel26edit);
        panel27edit = findViewById(R.id.panel27edit);
        panel28edit = findViewById(R.id.panel28edit);

       panelToServer(panel1edit);
       panelToServer(panel2edit);
       panelToServer(panel3edit);
       panelToServer(panel4edit);
       panelToServer(panel5edit);
       panelToServer(panel6edit);
       panelToServer(panel7edit);
       panelToServer(panel8edit);
       panelToServer(panel9edit);
       panelToServer(panel10edit);
       panelToServer(panel11edit);
       panelToServer(panel12edit);
       panelToServer(panel13edit);
       panelToServer(panel14edit);
       panelToServer(panel15edit);
       panelToServer(panel16edit);
       panelToServer(panel17edit);
       panelToServer(panel18edit);
       panelToServer(panel19edit);
       panelToServer(panel20edit);
       panelToServer(panel21edit);
       panelToServer(panel22edit);
       panelToServer(panel23edit);
       panelToServer(panel24edit);
       panelToServer(panel25edit);
       panelToServer(panel26edit);
       panelToServer(panel27edit);
       panelToServer(panel28edit);


        pumpSerail = findViewById(R.id.pumpSerail);
        MotorSerial = findViewById(R.id.MotorSerial);
        RmuNumber = findViewById(R.id.RmuNumber);
        ControllerNumber = findViewById(R.id.ControllerNumber);

        /*End of 16 Edit Text*/
        qrScan1 = new IntentIntegrator(this);
        qrScan2 = new IntentIntegrator(this);
        qrScan3 = new IntentIntegrator(this);
        qrScan4 = new IntentIntegrator(this);
        qrScan5 = new IntentIntegrator(this);
        qrScan6 = new IntentIntegrator(this);
        qrScan7 = new IntentIntegrator(this);
        qrScan8 = new IntentIntegrator(this);
        qrScan9 = new IntentIntegrator(this);
        qrScan10 = new IntentIntegrator(this);
        qrScan11 = new IntentIntegrator(this);
        qrScan12 = new IntentIntegrator(this);
        qrScan13 = new IntentIntegrator(this);
        qrScan14 = new IntentIntegrator(this);
        qrScan15 = new IntentIntegrator(this);
        qrScan16 = new IntentIntegrator(this);
        qrScan_17 = new IntentIntegrator(this);
        qrScan_18 = new IntentIntegrator(this);
        qrScan_19 = new IntentIntegrator(this);
        qrScan_20 = new IntentIntegrator(this);
        qrScan_21 = new IntentIntegrator(this);
        qrScan_22 = new IntentIntegrator(this);
        qrScan_23 = new IntentIntegrator(this);
        qrScan_24 = new IntentIntegrator(this);
        qrScan_25 = new IntentIntegrator(this);
        qrScan_26 = new IntentIntegrator(this);
        qrScan_27 = new IntentIntegrator(this);
        qrScan_28 = new IntentIntegrator(this);
        qrScan17 = new IntentIntegrator(this);
        qrScan18 = new IntentIntegrator(this);
        qrScan19 = new IntentIntegrator(this);
        qrScan20 = new IntentIntegrator(this);


        /*  btnAdd = (ImageButton) findViewById(R.id.btnAdd);*/
        btnDisplay = (Button) findViewById(R.id.btnDisplay);
        Engineer_contact = SharedPrefManager.getInstance(this).getUserContact();
        engineer_role = SharedPrefManager.getInstance(this).getUserRole();

        Bundle bundlepump = getIntent().getExtras();

        pump_type = bundlepump.getString("pump_type");
        //   Toast.makeText(getApplicationContext(),pump_type,Toast.LENGTH_SHORT).show();
        pump_capacity = bundlepump.getString("pump_capacity");

        fathername = bundlepump.getString("fathername");
        regnnumber = bundlepump.getString("regnnumber");
        benifname = bundlepump.getString("benifname");
        village = bundlepump.getString("village");
        block = bundlepump.getString("block");
        contact = bundlepump.getString("contact");
        dispatch_status = bundlepump.getString("dispatch_status");


        RmuNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                rmuno = String.valueOf(s).trim();


            }
        });

        MobileNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {


            }

            @Override
            public void afterTextChanged(Editable s) {
                mobileno = String.valueOf(s);


            }
        });
        SimNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {


            }

            @Override
            public void afterTextChanged(Editable s) {
                simno = String.valueOf(s);


            }
        });

        radiopipe.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.radio100pipe) {

                    radio_pipe_string = "100";
                } else if (checkedId == R.id.radio70pipe) {

                    radio_pipe_string = "70";

                } else if (checkedId == R.id.radio50pipe) {

                    radio_pipe_string = "50";

                } else if (checkedId == R.id.radio30pipe) {

                    radio_pipe_string = "30";

                }

            }
        });

        /*cable Radio*/

        radiocable.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.radio100cable) {

                    radio_cable_string = "100";
                } else if (checkedId == R.id.radio70cable) {

                    radio_cable_string = "70";

                } else if (checkedId == R.id.radio50cable) {

                    radio_cable_string = "50";

                } else if (checkedId == R.id.radio30cable) {

                    radio_cable_string = "30";

                }

            }
        });

        try {

            if (pump_capacity.equals("03 HP") || pump_capacity.equals("3 HP") || pump_capacity.equals("3HP") || pump_capacity.equals("03HP")) {
                panel11edit.setVisibility(View.GONE);
                panel12edit.setVisibility(View.GONE);
                panel13edit.setVisibility(View.GONE);
                panel14edit.setVisibility(View.GONE);
                panel15edit.setVisibility(View.GONE);
                panel16edit.setVisibility(View.GONE);
                panel17edit.setVisibility(View.GONE);
                panel18edit.setVisibility(View.GONE);
                panel19edit.setVisibility(View.GONE);
                panel20edit.setVisibility(View.GONE);
                panel21edit.setVisibility(View.GONE);
                panel22edit.setVisibility(View.GONE);
                panel23edit.setVisibility(View.GONE);
                panel24edit.setVisibility(View.GONE);
                panel25edit.setVisibility(View.GONE);
                panel26edit.setVisibility(View.GONE);
                panel27edit.setVisibility(View.GONE);
                panel28edit.setVisibility(View.GONE);

                panel11btn.setVisibility(View.GONE);
                panel12btn.setVisibility(View.GONE);
                panel13btn.setVisibility(View.GONE);
                panel14btn.setVisibility(View.GONE);
                panel15btn.setVisibility(View.GONE);
                panel16btn.setVisibility(View.GONE);
                panel16btn.setVisibility(View.GONE);
                panel17btn.setVisibility(View.GONE);
                panel18btn.setVisibility(View.GONE);
                panel19btn.setVisibility(View.GONE);
                panel20btn.setVisibility(View.GONE);
                panel21btn.setVisibility(View.GONE);
                panel22btn.setVisibility(View.GONE);
                panel23btn.setVisibility(View.GONE);
                panel24btn.setVisibility(View.GONE);
                panel25btn.setVisibility(View.GONE);
                panel26btn.setVisibility(View.GONE);
                panel27btn.setVisibility(View.GONE);
                panel28btn.setVisibility(View.GONE);

            }
            if (pump_capacity.equals("02 HP") || pump_capacity.equals("2 HP") || pump_capacity.equals("2HP") || pump_capacity.equals("03HP")) {

                panel7edit.setVisibility(View.GONE);
                panel8edit.setVisibility(View.GONE);
                panel9edit.setVisibility(View.GONE);
                panel10edit.setVisibility(View.GONE);
                panel11edit.setVisibility(View.GONE);
                panel12edit.setVisibility(View.GONE);
                panel13edit.setVisibility(View.GONE);
                panel14edit.setVisibility(View.GONE);
                panel15edit.setVisibility(View.GONE);
                panel16edit.setVisibility(View.GONE);
                panel17edit.setVisibility(View.GONE);
                panel18edit.setVisibility(View.GONE);
                panel19edit.setVisibility(View.GONE);
                panel20edit.setVisibility(View.GONE);
                panel21edit.setVisibility(View.GONE);
                panel22edit.setVisibility(View.GONE);
                panel23edit.setVisibility(View.GONE);
                panel24edit.setVisibility(View.GONE);
                panel25edit.setVisibility(View.GONE);
                panel26edit.setVisibility(View.GONE);
                panel27edit.setVisibility(View.GONE);
                panel28edit.setVisibility(View.GONE);

                panel7btn.setVisibility(View.GONE);
                panel8btn.setVisibility(View.GONE);
                panel9btn.setVisibility(View.GONE);
                panel10btn.setVisibility(View.GONE);
                panel11btn.setVisibility(View.GONE);
                panel12btn.setVisibility(View.GONE);
                panel13btn.setVisibility(View.GONE);
                panel14btn.setVisibility(View.GONE);
                panel15btn.setVisibility(View.GONE);
                panel16btn.setVisibility(View.GONE);
                panel16btn.setVisibility(View.GONE);
                panel17btn.setVisibility(View.GONE);
                panel18btn.setVisibility(View.GONE);
                panel19btn.setVisibility(View.GONE);
                panel20btn.setVisibility(View.GONE);
                panel21btn.setVisibility(View.GONE);
                panel22btn.setVisibility(View.GONE);
                panel23btn.setVisibility(View.GONE);
                panel24btn.setVisibility(View.GONE);
                panel25btn.setVisibility(View.GONE);
                panel26btn.setVisibility(View.GONE);
                panel27btn.setVisibility(View.GONE);
                panel28btn.setVisibility(View.GONE);
            }
            if (pump_capacity.equals("05 HP") || pump_capacity.equals("5 HP") || pump_capacity.equals("5HP") || pump_capacity.equals("05HP")) {

                panel17edit.setVisibility(View.GONE);
                panel18edit.setVisibility(View.GONE);
                panel19edit.setVisibility(View.GONE);
                panel20edit.setVisibility(View.GONE);
                panel21edit.setVisibility(View.GONE);
                panel22edit.setVisibility(View.GONE);
                panel23edit.setVisibility(View.GONE);
                panel24edit.setVisibility(View.GONE);
                panel25edit.setVisibility(View.GONE);
                panel26edit.setVisibility(View.GONE);
                panel27edit.setVisibility(View.GONE);
                panel28edit.setVisibility(View.GONE);

                panel16btn.setVisibility(View.GONE);
                panel17btn.setVisibility(View.GONE);
                panel18btn.setVisibility(View.GONE);
                panel19btn.setVisibility(View.GONE);
                panel20btn.setVisibility(View.GONE);
                panel21btn.setVisibility(View.GONE);
                panel22btn.setVisibility(View.GONE);
                panel23btn.setVisibility(View.GONE);
                panel24btn.setVisibility(View.GONE);
                panel25btn.setVisibility(View.GONE);
                panel26btn.setVisibility(View.GONE);
                panel27btn.setVisibility(View.GONE);
                panel28btn.setVisibility(View.GONE);
            }

            if (pump_capacity.equals("7.5 HP") || pump_capacity.equals("7.5 HP") || pump_capacity.equals("7.5HP") || pump_capacity.equals("7.5HP")) {


                panel23edit.setVisibility(View.GONE);
                panel24edit.setVisibility(View.GONE);
                panel25edit.setVisibility(View.GONE);
                panel26edit.setVisibility(View.GONE);
                panel27edit.setVisibility(View.GONE);
                panel28edit.setVisibility(View.GONE);

                panel23btn.setVisibility(View.GONE);
                panel24btn.setVisibility(View.GONE);
                panel25btn.setVisibility(View.GONE);
                panel26btn.setVisibility(View.GONE);
                panel27btn.setVisibility(View.GONE);
                panel28btn.setVisibility(View.GONE);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        String Beni = "Benificiery Name=";
        String Regi = "Registration Number=";


//        Beneficiary_textName.setText(Beni+ benifname );
//        Beneficiary_txtRegNo.setText( Regi+ regnnumber+','+pump_type);


        panel1btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(qrScan1.createScanIntent(), FIRST_REQUEST_CODE);

            }
        });
        panel2btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(qrScan2.createScanIntent(), SECOND_REQUEST_CODE);

            }
        });
        panel3btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(qrScan3.createScanIntent(), THIRD_REQUEST_CODE);

            }
        });
        panel4btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(qrScan4.createScanIntent(), FOURTH_REQUEST_CODE);

            }
        });
        panel5btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(qrScan5.createScanIntent(), FIFTH_REQUEST_CODE);

            }
        });
        panel6btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(qrScan6.createScanIntent(), SIX_REQUEST_CODE);

            }
        });
        panel7btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(qrScan7.createScanIntent(), SEVEN_REQUEST_CODE);

            }
        });
        panel8btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(qrScan8.createScanIntent(), EIGHT_REQUEST_CODE);

            }
        });
        panel9btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(qrScan9.createScanIntent(), NINE_REQUEST_CODE);

            }
        });
        panel10btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(qrScan10.createScanIntent(), TEN_REQUEST_CODE);

            }
        });
        panel11btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(qrScan11.createScanIntent(), ELEVEN_REQUEST_CODE);

            }
        });
        panel12btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(qrScan12.createScanIntent(), TWELVE_REQUEST_CODE);

            }
        });
        panel13btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(qrScan13.createScanIntent(), THIRTEEN_REQUEST_CODE);

            }
        });
        panel14btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(qrScan14.createScanIntent(), FOURTEEN_REQUEST_CODE);

            }
        });
        panel15btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(qrScan15.createScanIntent(), FIFTEEN_REQUEST_CODE);

            }
        });
        panel16btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(qrScan16.createScanIntent(), SIXTEEN_REQUEST_CODE);

            }
        });
        panel17btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(qrScan_17.createScanIntent(), SEVENTEENREQUEST_CODE);

            }
        });
        panel18btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(qrScan_18.createScanIntent(), EIGHTEENREQUEST_CODE);

            }
        });
        panel19btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(qrScan_19.createScanIntent(), NINETEENREQUEST_CODE);

            }
        });
        panel20btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(qrScan_20.createScanIntent(), TWENTYREQUEST_CODE);

            }
        });
        panel21btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(qrScan_21.createScanIntent(), TWENTYONEREQUEST_CODE);

            }
        });
        panel22btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(qrScan_22.createScanIntent(), TWENTYTWOREQUEST_CODE);

            }
        });
        panel23btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(qrScan_23.createScanIntent(), TWENTYTHREEREQUEST_CODE);

            }
        });
        panel24btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(qrScan_24.createScanIntent(), TWENTYFOURREQUEST_CODE);

            }
        });
        panel25btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(qrScan_25.createScanIntent(), TWENTYFIVEREQUEST_CODE);

            }
        });
        panel26btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(qrScan_26.createScanIntent(), TWENTYSIXREQUEST_CODE);

            }
        });
        panel27btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(qrScan_27.createScanIntent(), TWENTYSEVENREQUEST_CODE);

            }
        });
        panel28btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(qrScan_28.createScanIntent(), TWENTYEIGHTREQUEST_CODE);

            }
        });
        btnPumpScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(qrScan17.createScanIntent(), SEVENTEEN_REQUEST_CODE);

            }
        });
        btnMotorScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(qrScan18.createScanIntent(), EIGHTEEN_REQUEST_CODE);

            }
        });
        ControllerbtnNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(qrScan19.createScanIntent(), NINTEEN_REQUEST_CODE);

            }
        });
        btnRmuNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(qrScan20.createScanIntent(), TWENTY_REQUEST_CODE);

            }
        });

        btnDisplay.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
//                if (RmuNumber.getText().toString().length()<19 && !RmuNumber.getText().toString().equals(""))
//                {
//                    Toast.makeText(getApplicationContext(),"Please Enter 19 or 20 digit Rmu Number ",Toast.LENGTH_SHORT).show();
//
//                }
//                 if (MobileNumber.getText().toString().length()<10&&!MobileNumber.getText().toString().equals(""))
//                {
//                    Toast.makeText(getApplicationContext(),"Please Enter 13 digit Mobile number",Toast.LENGTH_SHORT).show();
//                }
//             else  if (rmuno.length()<19 || rmuno.length()<20 )
//                {
//                    Toast.makeText(getApplicationContext(),"Please Enter 19 or 20 digit Rmu Number ",Toast.LENGTH_SHORT).show();
//
//                }
//                else if (SimNumber.getText().toString().equals(""))
//                {
//                    Toast.makeText(getApplicationContext(),"Please Enter 20 or 22 digit sim number",Toast.LENGTH_SHORT).show();
//
//                }
//                else if (simno.length()<20 && simno.length()<22)
//                {
//                    Toast.makeText(getApplicationContext(),"Please Enter 20 or 22 digit sim number",Toast.LENGTH_SHORT).show();
//
//                }
//                if (mobileno.length()<13||mobileno.equals(""))
//                {
//                    Toast.makeText(getApplicationContext(),"Please Enter 13 digit Mobile number",Toast.LENGTH_SHORT).show();
//
//                }
                if (RmuNumber.getText().toString().length() != 15) {
                    Toast.makeText(getApplicationContext(), "Please Enter 15 digit IMEI Number ", Toast.LENGTH_SHORT).show();

                }
//              else if (!SimNumber.getText().toString().equals("")&&SimNumber.getText().toString().length()<19 && SimNumber.getText().toString().length()<20)
//                {
//                    Toast.makeText(getApplicationContext(),"Please Enter 19 or 20 digit Sim Number ",Toast.LENGTH_SHORT).show();
//
//                }
             /*    if (panel1edit.getText().toString().length()<19)
                {
                    Toast.makeText(getApplicationContext(),"Please Enter 19 Digit panel Number",Toast.LENGTH_SHORT).show();

                }
                else if (panel2edit.getText().toString().length()<19)
                {
                    Toast.makeText(getApplicationContext(),"Please Enter 19 Digit panel Number",Toast.LENGTH_SHORT).show();

                }
                else if (panel3edit.getText().toString().length()<19)
                {
                    Toast.makeText(getApplicationContext(),"Please Enter 19 Digit panel Number",Toast.LENGTH_SHORT).show();

                }
                else if (panel4edit.getText().toString().length()<19)
                {
                    Toast.makeText(getApplicationContext(),"Please Enter 19 Digit panel Number",Toast.LENGTH_SHORT).show();

                }
                else if (panel5edit.getText().toString().length()<19)
                {
                    Toast.makeText(getApplicationContext(),"Please Enter 19 Digit panel Number",Toast.LENGTH_SHORT).show();

                }
                else if (panel6edit.getText().toString().length()<19)
                {
                    Toast.makeText(getApplicationContext(),"Please Enter 19 Digit panel Number",Toast.LENGTH_SHORT).show();

                }
                else if (panel7edit.getText().toString().length()<19)
                {
                    Toast.makeText(getApplicationContext(),"Please Enter 19 Digit panel Number",Toast.LENGTH_SHORT).show();

                }else if (panel1edit.getText().toString().length()<19)
                {
                    Toast.makeText(getApplicationContext(),"Please Enter 19 Digit panel Number",Toast.LENGTH_SHORT).show();

                }
                else if (panel8edit.getText().toString().length()<19)
                {
                    Toast.makeText(getApplicationContext(),"Please Enter 19 Digit panel Number",Toast.LENGTH_SHORT).show();

                }
                else if (panel9edit.getText().toString().length()<19)
                {
                    Toast.makeText(getApplicationContext(),"Please Enter 19 Digit panel Number",Toast.LENGTH_SHORT).show();

                }
                else if (panel10edit.getText().toString().length()<19)
                {
                    Toast.makeText(getApplicationContext(),"Please Enter 19 Digit panel Number",Toast.LENGTH_SHORT).show();

                }else if (panel11edit.getText().toString().length()<19)
                {
                    Toast.makeText(getApplicationContext(),"Please Enter 19 Digit panel Number",Toast.LENGTH_SHORT).show();

                }else if (panel12edit.getText().toString().length()<19)
                {
                    Toast.makeText(getApplicationContext(),"Please Enter 19 Digit panel Number",Toast.LENGTH_SHORT).show();

                }else if (panel1edit.getText().toString().length()<19)
                {
                    Toast.makeText(getApplicationContext(),"Please Enter 19 Digit panel Number",Toast.LENGTH_SHORT).show();

                }else if (panel13edit.getText().toString().length()<19)
                {
                    Toast.makeText(getApplicationContext(),"Please Enter 19 Digit panel Number",Toast.LENGTH_SHORT).show();

                }else if (panel14edit.getText().toString().length()<19)
                {
                    Toast.makeText(getApplicationContext(),"Please Enter 19 Digit panel Number",Toast.LENGTH_SHORT).show();

                }else if (panel15edit.getText().toString().length()<19)
                {
                    Toast.makeText(getApplicationContext(),"Please Enter 19 Digit panel Number",Toast.LENGTH_SHORT).show();

                }
                else if (panel16edit.getText().toString().length()<19)
                {
                    Toast.makeText(getApplicationContext(),"Please Enter 19 Digit panel Number",Toast.LENGTH_SHORT).show();

                }*/
                else {
                    ConnectivityManager cm = (ConnectivityManager) Material.this.getSystemService(Context.CONNECTIVITY_SERVICE);
                    final NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
                    if (activeNetwork != null) {
                        uploadData();

                    } else {
                        Toast.makeText(getApplicationContext(), "Please Connect to the Internet", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });


        loadalreadydata();

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(Material.this, "Permission Granted Successfully! ", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(Material.this, "Permission Denied :( ", Toast.LENGTH_LONG).show();
                }
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == FIRST_REQUEST_CODE) {
            IntentResult result = IntentIntegrator.parseActivityResult(qrScan1.REQUEST_CODE, resultCode, data);
            if (result != null) {
                if (result.getContents() == null) {
                   /* pumpSerail.setText( "kkkk" );
                    PumpSerial= String.valueOf( pumpSerail.getText() );*/
                    Toast.makeText(this, "Result Not Found", Toast.LENGTH_LONG).show();
                } else {
                    try {

                        JSONObject obj = new JSONObject(result.getContents());

                        panel1edit.setText(obj.getString("name") + " " + obj.getString("address"));
                        pan1 = String.valueOf(panel1edit.getText());
                        /*PumpSerial = pumpSerail.getText().toString();*/


                    } catch (JSONException e) {
                        e.printStackTrace();
                        if (result.getContents().length() < 19) {
                            Toast.makeText(this, "Length Below OR Above 19 or 20", Toast.LENGTH_LONG).show();
                        } else {
                            panel1edit.setText(result.getContents().toString());
                            pan1 = String.valueOf(panel1edit.getText());
                        }
                    }

                }
            }

        } else if (requestCode == SECOND_REQUEST_CODE) {

            IntentResult result = IntentIntegrator.parseActivityResult(qrScan2.REQUEST_CODE, resultCode, data);
            if (result != null) {
                if (result.getContents() == null) {
                    Toast.makeText(this, "Result Not Found", Toast.LENGTH_LONG).show();
                } else {
                    try {
                        JSONObject obj = new JSONObject(result.getContents());
                        panel2edit.setText(obj.getString("name") + " " + obj.getString("address"));
                        pan2 = String.valueOf(panel2edit.getText());
                    } catch (JSONException e) {
                        e.printStackTrace();
                        if (result.getContents().length() < 19) {
                            Toast.makeText(this, "Length Below OR Above 19 or 20", Toast.LENGTH_LONG).show();
                        } else {

                            panel2edit.setText(result.getContents().toString());
                            pan2 = String.valueOf(panel2edit.getText());
                            Toast.makeText(this, result.getContents(), Toast.LENGTH_LONG).show();
                        }
                    }


                }
            }

        } else if (requestCode == THIRD_REQUEST_CODE) {
            IntentResult result = IntentIntegrator.parseActivityResult(qrScan3.REQUEST_CODE, resultCode, data);
            if (result != null) {
                if (result.getContents() == null) {
                    Toast.makeText(this, "Result Not Found", Toast.LENGTH_LONG).show();
                } else {
                    try {
                        JSONObject obj = new JSONObject(result.getContents());

                        panel3edit.setText(obj.getString("name") + " " + obj.getString("address"));
                        pan3 = String.valueOf(panel3edit.getText());
                    } catch (JSONException e) {
                        e.printStackTrace();
                        if (result.getContents().length() < 19) {
                            Toast.makeText(this, "Length Below OR Above 19 or 20", Toast.LENGTH_LONG).show();
                        } else {

                            panel3edit.setText(result.getContents().toString());
                            pan3 = String.valueOf(panel3edit.getText());
                            Toast.makeText(this, result.getContents(), Toast.LENGTH_LONG).show();
                        }
                    }
                }
            }

        } else if (requestCode == FOURTH_REQUEST_CODE) {

            IntentResult result = IntentIntegrator.parseActivityResult(qrScan4.REQUEST_CODE, resultCode, data);
            if (result != null) {
                if (result.getContents() == null) {

                    Toast.makeText(this, "Result Not Found", Toast.LENGTH_LONG).show();
                } else {
                    try {

                        JSONObject obj = new JSONObject(result.getContents());

                        panel4edit.setText(obj.getString("name") + " " + obj.getString("address"));
                        pan4 = String.valueOf(panel4edit.getText());
                    } catch (JSONException e) {
                        e.printStackTrace();
                        if (result.getContents().length() < 19) {
                            Toast.makeText(this, "Length Below OR Above 19 or 20", Toast.LENGTH_LONG).show();
                        } else {


                            panel4edit.setText(result.getContents().toString());
                            pan4 = String.valueOf(panel4edit.getText());
                            Toast.makeText(this, result.getContents(), Toast.LENGTH_LONG).show();
                        }
                    }


                }
            }

        } else if (requestCode == FIFTH_REQUEST_CODE) {

            IntentResult result = IntentIntegrator.parseActivityResult(qrScan5.REQUEST_CODE, resultCode, data);
            if (result != null) {
                if (result.getContents() == null) {

                    Toast.makeText(this, "Result Not Found", Toast.LENGTH_LONG).show();
                } else {
                    try {

                        JSONObject obj = new JSONObject(result.getContents());

                        panel5edit.setText(obj.getString("name") + " " + obj.getString("address"));
                        pan5 = String.valueOf(panel5edit.getText());
                    } catch (JSONException e) {
                        e.printStackTrace();
                        if (result.getContents().length() < 19) {
                            Toast.makeText(this, "Length Below OR Above 19 or 20", Toast.LENGTH_LONG).show();
                        } else {

                            panel5edit.setText(result.getContents().toString());
                            pan5 = String.valueOf(panel5edit.getText());
                            Toast.makeText(this, result.getContents(), Toast.LENGTH_LONG).show();
                        }
                    }


                }
            }

        } else if (requestCode == SIX_REQUEST_CODE) {

            IntentResult result = IntentIntegrator.parseActivityResult(qrScan6.REQUEST_CODE, resultCode, data);
            if (result != null) {
                if (result.getContents() == null) {

                    Toast.makeText(this, "Result Not Found", Toast.LENGTH_LONG).show();
                } else {
                    try {

                        JSONObject obj = new JSONObject(result.getContents());

                        panel6edit.setText(obj.getString("name") + " " + obj.getString("address"));
                        pan6 = String.valueOf(panel6edit.getText());


                    } catch (JSONException e) {
                        e.printStackTrace();
                        if (result.getContents().length() < 19) {
                            Toast.makeText(this, "Length Below OR Above 19 or 20", Toast.LENGTH_LONG).show();
                        } else {

                            panel6edit.setText(result.getContents().toString());
                            pan6 = String.valueOf(panel6edit.getText());
                            Toast.makeText(this, result.getContents(), Toast.LENGTH_LONG).show();
                        }
                    }


                }
            }

        } else if (requestCode == SEVEN_REQUEST_CODE) {

            IntentResult result = IntentIntegrator.parseActivityResult(qrScan7.REQUEST_CODE, resultCode, data);
            if (result != null) {
                if (result.getContents() == null) {

                    Toast.makeText(this, "Result Not Found", Toast.LENGTH_LONG).show();
                } else {
                    try {

                        JSONObject obj = new JSONObject(result.getContents());

                        panel7edit.setText(obj.getString("name") + " " + obj.getString("address"));
                        pan7 = String.valueOf(panel7edit.getText());


                    } catch (JSONException e) {
                        e.printStackTrace();
                        if (result.getContents().length() < 19) {
                            Toast.makeText(this, "Length Below OR Above 19 or 20", Toast.LENGTH_LONG).show();
                        } else {

                            panel7edit.setText(result.getContents().toString());
                            pan7 = String.valueOf(panel7edit.getText());
                            Toast.makeText(this, result.getContents(), Toast.LENGTH_LONG).show();
                        }
                    }


                }
            }

        } else if (requestCode == EIGHT_REQUEST_CODE) {

            IntentResult result = IntentIntegrator.parseActivityResult(qrScan8.REQUEST_CODE, resultCode, data);
            if (result != null) {
                if (result.getContents() == null) {

                    Toast.makeText(this, "Result Not Found", Toast.LENGTH_LONG).show();
                } else {
                    try {

                        JSONObject obj = new JSONObject(result.getContents());

                        panel8edit.setText(obj.getString("name") + " " + obj.getString("address"));
                        pan8 = String.valueOf(panel8edit.getText());

                    } catch (JSONException e) {
                        e.printStackTrace();
                        if (result.getContents().length() < 19) {
                            Toast.makeText(this, "Length Below OR Above 19 or 20", Toast.LENGTH_LONG).show();
                        } else {

                            panel8edit.setText(result.getContents().toString());
                            pan8 = String.valueOf(panel8edit.getText());
                            Toast.makeText(this, result.getContents(), Toast.LENGTH_LONG).show();
                        }
                    }


                }
            }

        } else if (requestCode == NINE_REQUEST_CODE) {

            IntentResult result = IntentIntegrator.parseActivityResult(qrScan9.REQUEST_CODE, resultCode, data);
            if (result != null) {
                if (result.getContents() == null) {

                    Toast.makeText(this, "Result Not Found", Toast.LENGTH_LONG).show();
                } else {
                    try {

                        JSONObject obj = new JSONObject(result.getContents());

                        panel9edit.setText(obj.getString("name") + " " + obj.getString("address"));
                        pan9 = String.valueOf(panel9edit.getText());


                    } catch (JSONException e) {
                        e.printStackTrace();

                        if (result.getContents().length() < 19) {
                            Toast.makeText(this, "Length Below OR Above 19 or 20", Toast.LENGTH_LONG).show();
                        } else {
                            panel9edit.setText(result.getContents().toString());
                            pan9 = String.valueOf(panel9edit.getText());
                            Toast.makeText(this, result.getContents(), Toast.LENGTH_LONG).show();
                        }
                    }


                }
            }

        } else if (requestCode == TEN_REQUEST_CODE) {

            IntentResult result = IntentIntegrator.parseActivityResult(qrScan10.REQUEST_CODE, resultCode, data);
            if (result != null) {
                if (result.getContents() == null) {

                    Toast.makeText(this, "Result Not Found", Toast.LENGTH_LONG).show();
                } else {
                    try {

                        JSONObject obj = new JSONObject(result.getContents());

                        panel10edit.setText(obj.getString("name") + " " + obj.getString("address"));
                        pan10 = String.valueOf(panel10edit.getText());


                    } catch (JSONException e) {
                        e.printStackTrace();

                        if (result.getContents().length() < 19) {
                            Toast.makeText(this, "Length Below OR Above 19 or 20", Toast.LENGTH_LONG).show();
                        } else {
                            panel10edit.setText(result.getContents().toString());
                            pan10 = String.valueOf(panel10edit.getText());
                            Toast.makeText(this, result.getContents(), Toast.LENGTH_LONG).show();
                        }
                    }


                }
            }

        } else if (requestCode == ELEVEN_REQUEST_CODE) {

            IntentResult result = IntentIntegrator.parseActivityResult(qrScan11.REQUEST_CODE, resultCode, data);
            if (result != null) {
                if (result.getContents() == null) {

                    Toast.makeText(this, "Result Not Found", Toast.LENGTH_LONG).show();
                } else {
                    try {

                        JSONObject obj = new JSONObject(result.getContents());

                        panel11edit.setText(obj.getString("name") + " " + obj.getString("address"));
                        pan11 = String.valueOf(panel11edit.getText());

                    } catch (JSONException e) {
                        e.printStackTrace();

                        if (result.getContents().length() < 19) {
                            Toast.makeText(this, "Length Below OR Above 19 or 20", Toast.LENGTH_LONG).show();
                        } else {
                            panel11edit.setText(result.getContents().toString());
                            pan11 = String.valueOf(panel11edit.getText());
                            Toast.makeText(this, result.getContents(), Toast.LENGTH_LONG).show();
                        }
                    }


                }
            }

        } else if (requestCode == TWELVE_REQUEST_CODE) {

            IntentResult result = IntentIntegrator.parseActivityResult(qrScan12.REQUEST_CODE, resultCode, data);
            if (result != null) {
                if (result.getContents() == null) {

                    Toast.makeText(this, "Result Not Found", Toast.LENGTH_LONG).show();
                } else {
                    try {

                        JSONObject obj = new JSONObject(result.getContents());

                        panel12edit.setText(obj.getString("name") + " " + obj.getString("address"));
                        pan12 = String.valueOf(panel12edit.getText());


                    } catch (JSONException e) {
                        e.printStackTrace();

                        if (result.getContents().length() < 19) {
                            Toast.makeText(this, "Length Below OR Above 19 or 20", Toast.LENGTH_LONG).show();
                        } else {
                            panel12edit.setText(result.getContents().toString());
                            pan12 = String.valueOf(panel12edit.getText());
                            Toast.makeText(this, result.getContents(), Toast.LENGTH_LONG).show();
                        }
                    }


                }
            }

        } else if (requestCode == THIRTEEN_REQUEST_CODE) {

            IntentResult result = IntentIntegrator.parseActivityResult(qrScan13.REQUEST_CODE, resultCode, data);
            if (result != null) {
                if (result.getContents() == null) {

                    Toast.makeText(this, "Result Not Found", Toast.LENGTH_LONG).show();
                } else {
                    try {

                        JSONObject obj = new JSONObject(result.getContents());

                        panel13edit.setText(obj.getString("name") + " " + obj.getString("address"));
                        pan13 = String.valueOf(panel13edit.getText());


                    } catch (JSONException e) {
                        e.printStackTrace();

                        if (result.getContents().length() < 19) {
                            Toast.makeText(this, "Length Below OR Above 19 or 20", Toast.LENGTH_LONG).show();
                        } else {
                            panel13edit.setText(result.getContents().toString().trim());
                            pan13 = String.valueOf(panel13edit.getText());
                            Toast.makeText(this, result.getContents(), Toast.LENGTH_LONG).show();
                        }
                    }


                }
            }

        } else if (requestCode == FOURTEEN_REQUEST_CODE) {

            IntentResult result = IntentIntegrator.parseActivityResult(qrScan14.REQUEST_CODE, resultCode, data);
            if (result != null) {
                if (result.getContents() == null) {

                    Toast.makeText(this, "Result Not Found", Toast.LENGTH_LONG).show();
                } else {
                    try {

                        JSONObject obj = new JSONObject(result.getContents());

                        panel14edit.setText(obj.getString("name") + " " + obj.getString("address"));
                        pan14 = String.valueOf(panel14edit.getText());


                    } catch (JSONException e) {
                        e.printStackTrace();

                        if (result.getContents().length() < 19) {
                            Toast.makeText(this, "Length Below OR Above 19 or 20", Toast.LENGTH_LONG).show();
                        } else {
                            panel14edit.setText(result.getContents().toString().trim());
                            pan14 = String.valueOf(panel14edit.getText());
                            Toast.makeText(this, result.getContents(), Toast.LENGTH_LONG).show();
                        }
                    }


                }
            }

        } else if (requestCode == FIFTEEN_REQUEST_CODE) {

            IntentResult result = IntentIntegrator.parseActivityResult(qrScan15.REQUEST_CODE, resultCode, data);
            if (result != null) {
                if (result.getContents() == null) {

                    Toast.makeText(this, "Result Not Found", Toast.LENGTH_LONG).show();
                } else {
                    try {

                        JSONObject obj = new JSONObject(result.getContents());

                        panel15edit.setText(obj.getString("name") + " " + obj.getString("address"));
                        pan15 = String.valueOf(panel15edit.getText());


                    } catch (JSONException e) {
                        e.printStackTrace();

                        if (result.getContents().length() < 19) {
                            Toast.makeText(this, "Length Below OR Above 19 or 20", Toast.LENGTH_LONG).show();
                        } else {
                            panel15edit.setText(result.getContents().toString().trim());
                            pan15 = String.valueOf(panel15edit.getText());
                            Toast.makeText(this, result.getContents(), Toast.LENGTH_LONG).show();
                        }
                    }


                }
            }

        } else if (requestCode == SIXTEEN_REQUEST_CODE) {

            IntentResult result = IntentIntegrator.parseActivityResult(qrScan16.REQUEST_CODE, resultCode, data);
            if (result != null) {
                if (result.getContents() == null) {

                    Toast.makeText(this, "Result Not Found", Toast.LENGTH_LONG).show();
                } else {
                    try {

                        JSONObject obj = new JSONObject(result.getContents());

                        panel16edit.setText(obj.getString("name") + " " + obj.getString("address"));

                        pan16 = String.valueOf(panel16edit.getText());

                    } catch (JSONException e) {
                        e.printStackTrace();

                        if (result.getContents().length() < 19) {
                            Toast.makeText(this, "Length Below OR Above 19 or 20", Toast.LENGTH_LONG).show();
                        } else {
                            panel16edit.setText(result.getContents().toString().trim());
                            pan16 = String.valueOf(panel16edit.getText());
                            Toast.makeText(this, result.getContents(), Toast.LENGTH_LONG).show();
                        }
                    }


                }
            }

        } else if (requestCode == SEVENTEENREQUEST_CODE) {

            IntentResult result = IntentIntegrator.parseActivityResult(qrScan_17.REQUEST_CODE, resultCode, data);
            if (result != null) {
                if (result.getContents() == null) {

                    Toast.makeText(this, "Result Not Found", Toast.LENGTH_LONG).show();
                } else {
                    try {

                        JSONObject obj = new JSONObject(result.getContents());

                        panel17edit.setText(obj.getString("name") + " " + obj.getString("address"));

                        pan17 = String.valueOf(panel17edit.getText());

                    } catch (JSONException e) {
                        e.printStackTrace();

                        if (result.getContents().length() < 19) {
                            Toast.makeText(this, "Length Below OR Above 19 or 20", Toast.LENGTH_LONG).show();
                        } else {
                            panel17edit.setText(result.getContents().toString().trim());
                            pan17 = String.valueOf(panel17edit.getText());
                            Toast.makeText(this, result.getContents(), Toast.LENGTH_LONG).show();
                        }
                    }


                }
            }

        } else if (requestCode == EIGHTEENREQUEST_CODE) {

            IntentResult result = IntentIntegrator.parseActivityResult(qrScan_18.REQUEST_CODE, resultCode, data);
            if (result != null) {
                if (result.getContents() == null) {

                    Toast.makeText(this, "Result Not Found", Toast.LENGTH_LONG).show();
                } else {
                    try {

                        JSONObject obj = new JSONObject(result.getContents());

                        panel18edit.setText(obj.getString("name") + " " + obj.getString("address"));

                        pan18 = String.valueOf(panel18edit.getText());

                    } catch (JSONException e) {
                        e.printStackTrace();

                        if (result.getContents().length() < 19) {
                            Toast.makeText(this, "Length Below OR Above 19 or 20", Toast.LENGTH_LONG).show();
                        } else {
                            panel18edit.setText(result.getContents().toString().trim());
                            pan18 = String.valueOf(panel18edit.getText());
                            Toast.makeText(this, result.getContents(), Toast.LENGTH_LONG).show();
                        }
                    }


                }
            }

        } else if (requestCode == NINETEENREQUEST_CODE) {

            IntentResult result = IntentIntegrator.parseActivityResult(qrScan_19.REQUEST_CODE, resultCode, data);
            if (result != null) {
                if (result.getContents() == null) {

                    Toast.makeText(this, "Result Not Found", Toast.LENGTH_LONG).show();
                } else {
                    try {

                        JSONObject obj = new JSONObject(result.getContents());

                        panel19edit.setText(obj.getString("name") + " " + obj.getString("address"));

                        pan19 = String.valueOf(panel19edit.getText());

                    } catch (JSONException e) {
                        e.printStackTrace();

                        if (result.getContents().length() < 19) {
                            Toast.makeText(this, "Length Below OR Above 19 or 20", Toast.LENGTH_LONG).show();
                        } else {
                            panel19edit.setText(result.getContents().toString().trim());
                            pan19 = String.valueOf(panel19edit.getText());
                            Toast.makeText(this, result.getContents(), Toast.LENGTH_LONG).show();
                        }
                    }


                }
            }

        } else if (requestCode == TWENTYREQUEST_CODE) {

            IntentResult result = IntentIntegrator.parseActivityResult(qrScan_20.REQUEST_CODE, resultCode, data);
            if (result != null) {
                if (result.getContents() == null) {

                    Toast.makeText(this, "Result Not Found", Toast.LENGTH_LONG).show();
                } else {
                    try {

                        JSONObject obj = new JSONObject(result.getContents());

                        panel20edit.setText(obj.getString("name") + " " + obj.getString("address"));

                        pan20 = String.valueOf(panel20edit.getText());

                    } catch (JSONException e) {
                        e.printStackTrace();

                        if (result.getContents().length() < 19) {
                            Toast.makeText(this, "Length Below OR Above 19 or 20", Toast.LENGTH_LONG).show();
                        } else {
                            panel20edit.setText(result.getContents().toString().trim());
                            pan20 = String.valueOf(panel20edit.getText());
                            Toast.makeText(this, result.getContents(), Toast.LENGTH_LONG).show();
                        }
                    }


                }
            }

        } else if (requestCode == TWENTYONEREQUEST_CODE) {

            IntentResult result = IntentIntegrator.parseActivityResult(qrScan_21.REQUEST_CODE, resultCode, data);
            if (result != null) {
                if (result.getContents() == null) {

                    Toast.makeText(this, "Result Not Found", Toast.LENGTH_LONG).show();
                } else {
                    try {

                        JSONObject obj = new JSONObject(result.getContents());

                        panel21edit.setText(obj.getString("name") + " " + obj.getString("address"));

                        pan21 = String.valueOf(panel21edit.getText());

                    } catch (JSONException e) {
                        e.printStackTrace();

                        if (result.getContents().length() < 19) {
                            Toast.makeText(this, "Length Below OR Above 19 or 20", Toast.LENGTH_LONG).show();
                        } else {
                            panel21edit.setText(result.getContents().toString().trim());
                            pan21 = String.valueOf(panel21edit.getText());
                            Toast.makeText(this, result.getContents(), Toast.LENGTH_LONG).show();
                        }
                    }


                }
            }

        } else if (requestCode == TWENTYTWOREQUEST_CODE) {

            IntentResult result = IntentIntegrator.parseActivityResult(qrScan_22.REQUEST_CODE, resultCode, data);
            if (result != null) {
                if (result.getContents() == null) {

                    Toast.makeText(this, "Result Not Found", Toast.LENGTH_LONG).show();
                } else {
                    try {

                        JSONObject obj = new JSONObject(result.getContents());

                        panel22edit.setText(obj.getString("name") + " " + obj.getString("address"));

                        pan22 = String.valueOf(panel22edit.getText());

                    } catch (JSONException e) {
                        e.printStackTrace();

                        if (result.getContents().length() < 19) {
                            Toast.makeText(this, "Length Below OR Above 19 or 20", Toast.LENGTH_LONG).show();
                        } else {
                            panel22edit.setText(result.getContents().toString().trim());
                            pan22 = String.valueOf(panel22edit.getText());
                            Toast.makeText(this, result.getContents(), Toast.LENGTH_LONG).show();
                        }
                    }


                }
            }

        } else if (requestCode == TWENTYTHREEREQUEST_CODE) {

            IntentResult result = IntentIntegrator.parseActivityResult(qrScan_23.REQUEST_CODE, resultCode, data);
            if (result != null) {
                if (result.getContents() == null) {

                    Toast.makeText(this, "Result Not Found", Toast.LENGTH_LONG).show();
                } else {
                    try {

                        JSONObject obj = new JSONObject(result.getContents());

                        panel23edit.setText(obj.getString("name") + " " + obj.getString("address"));

                        pan23 = String.valueOf(panel23edit.getText());

                    } catch (JSONException e) {
                        e.printStackTrace();

                        if (result.getContents().length() < 19) {
                            Toast.makeText(this, "Length Below OR Above 19 or 20", Toast.LENGTH_LONG).show();
                        } else {
                            panel23edit.setText(result.getContents().toString().trim());
                            pan23 = String.valueOf(panel23edit.getText());
                            Toast.makeText(this, result.getContents(), Toast.LENGTH_LONG).show();
                        }
                    }


                }
            }

        } else if (requestCode == TWENTYFOURREQUEST_CODE) {

            IntentResult result = IntentIntegrator.parseActivityResult(qrScan_24.REQUEST_CODE, resultCode, data);
            if (result != null) {
                if (result.getContents() == null) {

                    Toast.makeText(this, "Result Not Found", Toast.LENGTH_LONG).show();
                } else {
                    try {

                        JSONObject obj = new JSONObject(result.getContents());

                        panel24edit.setText(obj.getString("name") + " " + obj.getString("address"));

                        pan24 = String.valueOf(panel24edit.getText());

                    } catch (JSONException e) {
                        e.printStackTrace();

                        if (result.getContents().length() < 19) {
                            Toast.makeText(this, "Length Below OR Above 19 or 20", Toast.LENGTH_LONG).show();
                        } else {
                            panel24edit.setText(result.getContents().toString().trim());
                            pan24 = String.valueOf(panel24edit.getText());
                            Toast.makeText(this, result.getContents(), Toast.LENGTH_LONG).show();
                        }
                    }


                }
            }

        } else if (requestCode == TWENTYFIVEREQUEST_CODE) {

            IntentResult result = IntentIntegrator.parseActivityResult(qrScan_25.REQUEST_CODE, resultCode, data);
            if (result != null) {
                if (result.getContents() == null) {

                    Toast.makeText(this, "Result Not Found", Toast.LENGTH_LONG).show();
                } else {
                    try {

                        JSONObject obj = new JSONObject(result.getContents());

                        panel25edit.setText(obj.getString("name") + " " + obj.getString("address"));

                        pan25 = String.valueOf(panel25edit.getText());

                    } catch (JSONException e) {
                        e.printStackTrace();

                        if (result.getContents().length() < 19) {
                            Toast.makeText(this, "Length Below OR Above 19 or 20", Toast.LENGTH_LONG).show();
                        } else {
                            panel25edit.setText(result.getContents().toString().trim());
                            pan25 = String.valueOf(panel25edit.getText());
                            Toast.makeText(this, result.getContents(), Toast.LENGTH_LONG).show();
                        }
                    }


                }
            }

        } else if (requestCode == TWENTYSIXREQUEST_CODE) {

            IntentResult result = IntentIntegrator.parseActivityResult(qrScan_26.REQUEST_CODE, resultCode, data);
            if (result != null) {
                if (result.getContents() == null) {

                    Toast.makeText(this, "Result Not Found", Toast.LENGTH_LONG).show();
                } else {
                    try {

                        JSONObject obj = new JSONObject(result.getContents());

                        panel26edit.setText(obj.getString("name") + " " + obj.getString("address"));

                        pan26 = String.valueOf(panel26edit.getText());

                    } catch (JSONException e) {
                        e.printStackTrace();

                        if (result.getContents().length() < 19) {
                            Toast.makeText(this, "Length Below OR Above 19 or 20", Toast.LENGTH_LONG).show();
                        } else {
                            panel26edit.setText(result.getContents().toString().trim());
                            pan26 = String.valueOf(panel26edit.getText());
                            Toast.makeText(this, result.getContents(), Toast.LENGTH_LONG).show();
                        }
                    }


                }
            }

        } else if (requestCode == TWENTYSEVENREQUEST_CODE) {

            IntentResult result = IntentIntegrator.parseActivityResult(qrScan_27.REQUEST_CODE, resultCode, data);
            if (result != null) {
                if (result.getContents() == null) {

                    Toast.makeText(this, "Result Not Found", Toast.LENGTH_LONG).show();
                } else {
                    try {

                        JSONObject obj = new JSONObject(result.getContents());

                        panel27edit.setText(obj.getString("name") + " " + obj.getString("address"));

                        pan27 = String.valueOf(panel27edit.getText());

                    } catch (JSONException e) {
                        e.printStackTrace();

                        if (result.getContents().length() < 19) {
                            Toast.makeText(this, "Length Below OR Above 19 or 20", Toast.LENGTH_LONG).show();
                        } else {
                            panel27edit.setText(result.getContents().toString().trim());
                            pan27 = String.valueOf(panel27edit.getText());
                            Toast.makeText(this, result.getContents(), Toast.LENGTH_LONG).show();
                        }
                    }


                }
            }

        } else if (requestCode == TWENTYEIGHTREQUEST_CODE) {

            IntentResult result = IntentIntegrator.parseActivityResult(qrScan_28.REQUEST_CODE, resultCode, data);
            if (result != null) {
                if (result.getContents() == null) {

                    Toast.makeText(this, "Result Not Found", Toast.LENGTH_LONG).show();
                } else {
                    try {

                        JSONObject obj = new JSONObject(result.getContents());

                        panel28edit.setText(obj.getString("name") + " " + obj.getString("address"));

                        pan28 = String.valueOf(panel28edit.getText());

                    } catch (JSONException e) {
                        e.printStackTrace();

                        if (result.getContents().length() < 19) {
                            Toast.makeText(this, "Length Below OR Above 19 or 20", Toast.LENGTH_LONG).show();
                        } else {
                            panel28edit.setText(result.getContents().toString().trim());
                            pan28 = String.valueOf(panel28edit.getText());
                            Toast.makeText(this, result.getContents(), Toast.LENGTH_LONG).show();
                        }
                    }


                }
            }

        } else if (requestCode == SEVENTEEN_REQUEST_CODE) {

            IntentResult result = IntentIntegrator.parseActivityResult(qrScan17.REQUEST_CODE, resultCode, data);
            if (result != null) {
                if (result.getContents() == null) {

                    Toast.makeText(this, "Result Not Found", Toast.LENGTH_LONG).show();
                } else {
                    try {

                        JSONObject obj = new JSONObject(result.getContents());

                        pumpSerail.setText(obj.getString("name") + " " + obj.getString("address"));
                        PumpSerial = pumpSerail.getText().toString();

                    } catch (JSONException e) {
                        e.printStackTrace();


                        pumpSerail.setText(result.getContents().toString().trim());
                        PumpSerial = pumpSerail.getText().toString();
                        Toast.makeText(this, result.getContents(), Toast.LENGTH_LONG).show();
                    }


                }
            }

        } else if (requestCode == EIGHTEEN_REQUEST_CODE) {

            IntentResult result = IntentIntegrator.parseActivityResult(qrScan18.REQUEST_CODE, resultCode, data);
            if (result != null) {
                if (result.getContents() == null) {

                    Toast.makeText(this, "Result Not Found", Toast.LENGTH_LONG).show();
                } else {
                    try {


                        JSONObject obj = new JSONObject(result.getContents());
                        MotorSerial.setText(obj.getString("name") + " " + obj.getString("address"));
                        motorSerial = MotorSerial.getText().toString();

                    } catch (JSONException e) {
                        e.printStackTrace();


                        MotorSerial.setText(result.getContents().toString().trim());

                        motorSerial = MotorSerial.getText().toString();
                        Toast.makeText(this, result.getContents(), Toast.LENGTH_LONG).show();
                    }


                }
            }

        } else if (requestCode == NINTEEN_REQUEST_CODE) {

            IntentResult result = IntentIntegrator.parseActivityResult(qrScan19.REQUEST_CODE, resultCode, data);
            if (result != null) {
                if (result.getContents() == null) {

                    Toast.makeText(this, "Result Not Found", Toast.LENGTH_LONG).show();
                } else {
                    try {


                        JSONObject obj = new JSONObject(result.getContents());
                        ControllerNumber.setText(obj.getString("name") + " " + obj.getString("address"));
                        controllerNumber = ControllerNumber.getText().toString();

                    } catch (JSONException e) {
                        e.printStackTrace();


                        ControllerNumber.setText(result.getContents().toString().trim());

                        controllerNumber = ControllerNumber.getText().toString();
                        Toast.makeText(this, result.getContents(), Toast.LENGTH_LONG).show();
                    }


                }
            }

        } else if (requestCode == TWENTY_REQUEST_CODE) {

            IntentResult result = IntentIntegrator.parseActivityResult(qrScan20.REQUEST_CODE, resultCode, data);
            if (result != null) {
                if (result.getContents() == null) {

                    Toast.makeText(this, "Result Not Found", Toast.LENGTH_LONG).show();
                } else {
                    try {


                        JSONObject obj = new JSONObject(result.getContents());
                        RmuNumber.setText(obj.getString("name") + " " + obj.getString("address"));
                        rmuNumber = RmuNumber.getText().toString();

                    } catch (JSONException e) {
                        e.printStackTrace();


                        RmuNumber.setText(result.getContents().toString().trim());

                        rmuNumber = RmuNumber.getText().toString();
                        Toast.makeText(this, result.getContents(), Toast.LENGTH_LONG).show();
                    }


                }
            }

        } else {

            super.onActivityResult(requestCode, resultCode, data);
        }


    }


    public void loadalreadydata() {
        pb.show();
        StringRequest jsonObjectRequest = new StringRequest(Request.Method.POST, Constants.FETCH_MATERIAL_DISPATCH,
                new Response.Listener<String>() {


                    @Override
                    public void onResponse(String response) {
                        try {
                            pb.dismiss();
                            JSONArray jsonArray = new JSONArray(response);
                            JSONObject jsonObject = jsonArray.getJSONObject(0);
                            String Sim = jsonObject.getString("sim_number");
//                            String Mobile=jsonObject.getString("sim_mob_no");
                            pannew1 = jsonObject.getString("panel1");
                            pannew2 = jsonObject.getString("panel2");
                            pannew3 = jsonObject.getString("panel3");
                            pannew4 = jsonObject.getString("panel4");
                            pannew5 = jsonObject.getString("panel5");
                            pannew6 = jsonObject.getString("panel6");
                            pannew7 = jsonObject.getString("panel7");
                            pannew8 = jsonObject.getString("panel8");
                            pannew9 = jsonObject.getString("panel9");
                            pannew10 = jsonObject.getString("panel10");
                            pannew11 = jsonObject.getString("panel11");
                            pannew12 = jsonObject.getString("panel12");
                            pannew13 = jsonObject.getString("panel13");
                            pannew14 = jsonObject.getString("panel14");
                            pannew15 = jsonObject.getString("panel15");
                            pannew16 = jsonObject.getString("panel16");
                            pannew17 = jsonObject.getString("panel17");
                            pannew18 = jsonObject.getString("panel18");
                            pannew19 = jsonObject.getString("panel19");
                            pannew20 = jsonObject.getString("panel20");
                            pannew21 = jsonObject.getString("panel21");
                            pannew22 = jsonObject.getString("panel22");
                            pannew23 = jsonObject.getString("panel23");
                            pannew24 = jsonObject.getString("panel24");
                            pannew25 = jsonObject.getString("panel25");
                            pannew26 = jsonObject.getString("panel26");
                            pannew27 = jsonObject.getString("panel27");
                            pannew28 = jsonObject.getString("panel28");
                            controler_srno = jsonObject.getString("controler_srno");
                            controler_rms_id = jsonObject.getString("controler_rms_id");
                            pumpserialnew = jsonObject.getString("pumpserial");
                            motorserialnew = jsonObject.getString("motorserial");
                            radio_pipe_string = jsonObject.getString("pipe_status");
                            radio_cable_string = jsonObject.getString("cable_status");
                            structure_checkbox_string = jsonObject.getString("structure_status");
                            accessories_checkbox_string = jsonObject.getString("accessories_status");
                            rod_checkbox_string = jsonObject.getString("rod");
                            farma_checkbox_string = jsonObject.getString("farma");
                            villageName.setText(jsonObject.getString("village"));

                            panel1edit.setText(pannew1);
                            SimNumber.setText(Sim);
//                            MobileNumber.setText(Mobile);
                            panel2edit.setText(pannew2);
                            panel3edit.setText(pannew3);
                            panel4edit.setText(pannew4);
                            panel5edit.setText(pannew5);
                            panel6edit.setText(pannew6);
                            panel7edit.setText(pannew7);
                            panel8edit.setText(pannew8);
                            panel9edit.setText(pannew9);
                            panel10edit.setText(pannew10);
                            panel11edit.setText(pannew11);
                            panel12edit.setText(pannew12);
                            panel13edit.setText(pannew13);
                            panel15edit.setText(pannew15);
                            panel14edit.setText(pannew14);
                            panel16edit.setText(pannew16);
                            panel17edit.setText(pannew17);
                            panel18edit.setText(pannew18);
                            panel19edit.setText(pannew19);
                            panel20edit.setText(pannew20);
                            panel21edit.setText(pannew21);
                            panel22edit.setText(pannew22);
                            panel23edit.setText(pannew23);
                            panel24edit.setText(pannew24);
                            panel25edit.setText(pannew25);
                            panel26edit.setText(pannew26);
                            panel27edit.setText(pannew27);
                            panel28edit.setText(pannew28);


//                            if(pannew1.length()>=19)
//                            {
//                                panel1edit.setEnabled( false );
//                                panel1btn.setEnabled( false );
//                            }
//                            if(pannew2.length()>=19)
//                            {
//                                panel2edit.setEnabled( false );
//                                panel2btn.setEnabled( false );
//                            }
//                            if(pannew3.length()>=19)
//                            {
//                                panel3edit.setEnabled( false );
//                                panel3btn.setEnabled( false );
//                            }
//                            if(pannew4.length()>=19)
//                            {
//                                panel4edit.setEnabled( false );
//                                panel4btn.setEnabled( false );
//                            }
//                            if(pannew5.length()>=19)
//                            {
//                                panel5edit.setEnabled( false );
//                                panel5btn.setEnabled( false );
//                            }
//                            if(pannew6.length()>=19)
//                            {
//                                panel6edit.setEnabled( false );
//                                panel6btn.setEnabled( false );
//                            }
//                            if(pannew7.length()>=19)
//                            {
//                                panel7edit.setEnabled( false );
//                                panel7btn.setEnabled( false );
//                            }
//                            if(pannew8.length()>=19)
//                            {
//                                panel8edit.setEnabled( false );
//                                panel8btn.setEnabled( false );
//                            }
//                            if(pannew9.length()>=19)
//                            {
//                                panel9edit.setEnabled( false );
//                                panel9btn.setEnabled( false );
//                            }
//                            if(pannew10.length()>=19)
//                            {
//                                panel10edit.setEnabled( false );
//                                panel10btn.setEnabled( false );
//                            } if(pannew11.length()>=19)
//                            {
//                                panel11edit.setEnabled( false );
//                                panel11btn.setEnabled( false );
//                            }
//                            if(pannew12.length()>=19)
//                            {
//                                panel12edit.setEnabled( false );
//                                panel12btn.setEnabled( false );
//                            }
//                            if(pannew13.length()>=19)
//                            {
//                                panel13edit.setEnabled( false );
//                                panel13btn.setEnabled( false );
//                            } if(pannew14.length()>=19)
//                            {
//                                panel14edit.setEnabled( false );
//                                panel14btn.setEnabled( false );
//                            } if(pannew15.length()>=19)
//                            {
//                                panel15edit.setEnabled( false );
//                                panel15btn.setEnabled( false );
//                            } if(pannew16.length()>=19)
//                            {
//                                panel16edit.setEnabled( false );
//                                panel16btn.setEnabled( false );
//                            }if(pannew17.length()>=19)
//                            {
//                                panel17edit.setEnabled( false );
//                                panel17btn.setEnabled( false );
//                            }if(pannew18.length()>=19)
//                            {
//                                panel18edit.setEnabled( false );
//                                panel18btn.setEnabled( false );
//                            }if(pannew19.length()>=19)
//                            {
//                                panel19edit.setEnabled( false );
//                                panel19btn.setEnabled( false );
//                            }if(pannew20.length()>=19)
//                            {
//                                panel20edit.setEnabled( false );
//                                panel20btn.setEnabled( false );
//                            }if(pannew21.length()>=19)
//                            {
//                                panel21edit.setEnabled( false );
//                                panel21btn.setEnabled( false );
//                            }if(pannew22.length()>=19)
//                            {
//                                panel22edit.setEnabled( false );
//                                panel22btn.setEnabled( false );
//                            }if(pannew23.length()>=19)
//                            {
//                                panel23edit.setEnabled( false );
//                                panel23btn.setEnabled( false );
//                            }if(pannew24.length()>=19)
//                            {
//                                panel24edit.setEnabled( false );
//                                panel24btn.setEnabled( false );
//                            }if(pannew25.length()>=19)
//                            {
//                                panel25edit.setEnabled( false );
//                                panel25btn.setEnabled( false );
//                            }if(pannew26.length()>=19)
//                            {
//                                panel26edit.setEnabled( false );
//                                panel26btn.setEnabled( false );
//                            }if(pannew27.length()>=19)
//                            {
//                                panel27edit.setEnabled( false );
//                                panel27btn.setEnabled( false );
//                            }if(pannew28.length()>=19)
//                            {
//                                panel28edit.setEnabled( false );
//                                panel28btn.setEnabled( false );
//                            }

                            if (controler_srno.equals("null") || controler_srno.equals("")) {
                                ControllerNumber.setText("");
                            } else {
                                ControllerNumber.setText(controler_srno);
                            }
                            if (controler_rms_id.equals("null") || controler_rms_id.equals("")) {
                                RmuNumber.setText("");
                            } else {

                                RmuNumber.setText(controler_rms_id);

                            }
                            if (pumpserialnew.equals("null") || pumpserialnew.equals("")) {
                                pumpSerail.setText("");
                            } else {
                                pumpSerail.setText(pumpserialnew);

                            }
                            if (motorserialnew.equals("null") || motorserialnew.equals("")) {
                                MotorSerial.setText("");
                            } else {
                                MotorSerial.setText(motorserialnew);
                            }
//                            if(pumpserialnew.length()>5)
//                            {
//                                pumpSerail.setEnabled( false );
//                                btnPumpScan.setEnabled( false );
//
//                            }
//                            if(controler_rms_id.length()>=19)
//                            {
//                                RmuNumber.setEnabled( false );
//                                btnRmuNumber.setEnabled( false );
//
//                            }
//
//
//                            if(motorserialnew.length()>5)
//                            {
//                                MotorSerial.setEnabled( false );
//                                btnMotorScan.setEnabled( false );
//
//                            }
//                            if(controler_srno.length()>5)
//                            {
//                                ControllerNumber.setEnabled( false );
//                                ControllerbtnNumber.setEnabled( false );
//
//                            }


                            if (radio_pipe_string.equals("100")) {

                                radio100pipe.setChecked(true);
                                radio100pipe.setEnabled(false);
                                radio70pipe.setEnabled(false);
                                radio50pipe.setEnabled(false);
                                radio30pipe.setEnabled(false);

                            } else if (radio_pipe_string.equals("70")) {
                                radio70pipe.setChecked(true);

                                radio100pipe.setEnabled(false);
                                radio70pipe.setEnabled(false);
                                radio50pipe.setEnabled(false);
                                radio30pipe.setEnabled(false);
                                /*radiopipe.setEnabled( false );*/
                            } else if (radio_pipe_string.equals("50")) {
                                radio50pipe.setChecked(true);
                                radio100pipe.setEnabled(false);
                                radio70pipe.setEnabled(false);
                                radio50pipe.setEnabled(false);
                                radio30pipe.setEnabled(false);
                            } else if (radio_pipe_string.equals("30")) {
                                radio30pipe.setChecked(true);
                                radio100pipe.setEnabled(false);
                                radio70pipe.setEnabled(false);
                                radio50pipe.setEnabled(false);
                                radio30pipe.setEnabled(false);
                            }
                            /*cable*/
                            if (radio_cable_string.equals("100")) {

                                radio100cable.setChecked(true);
                                radio100cable.setEnabled(false);
                                radio70cable.setEnabled(false);
                                radio50cable.setEnabled(false);
                                radio30cable.setEnabled(false);

                            } else if (radio_cable_string.equals("70")) {
                                radio70cable.setChecked(true);
                                radio100cable.setEnabled(false);
                                radio70cable.setEnabled(false);
                                radio50cable.setEnabled(false);
                                radio30cable.setEnabled(false);
                            } else if (radio_cable_string.equals("50")) {
                                radio50cable.setChecked(true);
                                radio100cable.setEnabled(false);
                                radio70cable.setEnabled(false);
                                radio50cable.setEnabled(false);
                                radio30cable.setEnabled(false);
                            } else if (radio_cable_string.equals("30")) {
                                radio30cable.setChecked(true);
                                radio100cable.setEnabled(false);
                                radio70cable.setEnabled(false);
                                radio50cable.setEnabled(false);
                                radio30cable.setEnabled(false);
                            }



                            /*pumpmake*/


                            if (structure_checkbox_string.equals("Yes")) {

                                structure_checkbox.setChecked(true);
                                structure_checkbox.setEnabled(false);
                            }


                            if (accessories_checkbox_string.equals("Yes")) {

                                accessories_checkbox.setChecked(true);
                                accessories_checkbox.setEnabled(false);
                            }

                            if (rod_checkbox_string.equals("Yes")) {

                                rod_checkbox.setChecked(true);
                                rod_checkbox.setEnabled(false);
                            }
                            if (farma_checkbox_string.equals("Yes")) {

                                farma_checkbox.setChecked(true);
                                farma_checkbox.setEnabled(false);
                            }


                        } catch (JSONException e) {
                            pb.dismiss();
                            e.printStackTrace();
                        }


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                pb.dismiss();
                Toast.makeText(Material.this, "Error", Toast.LENGTH_SHORT).show();
                error.printStackTrace();

            }
        }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> paramsnew = new HashMap<>();

                paramsnew.put("eng_id", eng_id);
                paramsnew.put("reg_no", regnnumber);
                return paramsnew;
            }


        };
        MySingleton.getInstance(Material.this).addTorequestque(jsonObjectRequest);

    }

    public void uploadData() {
        Constants constants = new Constants();


        pb.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, constants.MATERIAL_DISPATCH, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                pb.dismiss();
                try {
                    JSONObject obj = new JSONObject(response);

                    String message = obj.getString("error");


                    if (message.equals("false")) {

                        dialog.findViewById(R.id.clickmedismiss).setOnClickListener(new View.OnClickListener() {
                            @SuppressLint("ResourceAsColor")
                            public void onClick(View v) {
                                Toast.makeText(Material.this, "Saved", Toast.LENGTH_SHORT).show();
                                Intent i = new Intent(Material.this, UploadAll.class);
                                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(i);
                                finish();
                                dialog.dismiss();
                            }
                        });

                        dialog.show();

                    }
                } catch (Exception e) {
                    pb.dismiss();
                    e.printStackTrace();
                    Toast.makeText(Material.this, response, Toast.LENGTH_SHORT).show();
                }


                // Toast.makeText( Material.this, response, Toast.LENGTH_LONG ).show();


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                pb.dismiss();
                if (error instanceof NetworkError) {
                    pb.dismiss();
                } else if (error instanceof ServerError) {
                    pb.dismiss();
                } else if (error instanceof AuthFailureError) {
                    pb.dismiss();
                } else if (error instanceof ParseError) {
                    pb.dismiss();
                } else if (error instanceof NoConnectionError) {
                    pb.dismiss();
                } else if (error instanceof TimeoutError) {
                    pb.dismiss();
                    Toast.makeText(getApplicationContext(), "Oops. Timeout error!", Toast.LENGTH_LONG).show();
                }

                Toast.makeText(getApplicationContext(), "error:" + error.toString(), Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();


                if (pan1 != null && !pan1.equals("")) {
                    params.put("pan1", pan1);

                } else {
                    pan1 = panel1edit.getText().toString();

                    params.put("pan1", pan1);
                }
                if (pan2 != null && !pan2.equals("")) {
                    params.put("pan2", pan2);

                } else {
                    pan2 = panel2edit.getText().toString();

                    params.put("pan2", pan2);
                }
                if (pan3 != null && !pan3.equals("")) {
                    params.put("pan3", pan3);

                } else {
                    pan3 = panel3edit.getText().toString();

                    params.put("pan3", pan3);
                }
                if (pan4 != null && !pan4.equals("")) {
                    params.put("pan4", pan4);

                } else {
                    pan4 = panel4edit.getText().toString();

                    params.put("pan4", pan4);
                }
                if (pan5 != null && !pan5.equals("")) {
                    params.put("pan5", pan5);

                } else {
                    pan5 = panel5edit.getText().toString();

                    params.put("pan5", pan5);
                }
                if (pan6 != null && !pan6.equals("")) {
                    params.put("pan6", pan6);

                } else {
                    pan6 = panel6edit.getText().toString();

                    params.put("pan6", pan6);
                }
                if (pan7 != null && !pan7.equals("")) {
                    params.put("pan7", pan7);

                } else {
                    pan7 = panel7edit.getText().toString();

                    params.put("pan7", pan7);
                }
                if (pan8 != null && !pan8.equals("")) {
                    params.put("pan8", pan8);

                } else {
                    pan8 = panel8edit.getText().toString();

                    params.put("pan8", pan8);
                }
                if (pan9 != null && !pan9.equals("")) {
                    params.put("pan9", pan9);

                } else {
                    pan9 = panel9edit.getText().toString();

                    params.put("pan9", pan9);
                }
                if (pan10 != null && !pan10.equals("")) {
                    params.put("pan10", pan10);

                } else {
                    pan10 = panel10edit.getText().toString();

                    params.put("pan10", pan10);
                }
                if (pan11 != null && !pan11.equals("")) {
                    params.put("pan11", pan11);

                } else {
                    pan11 = panel11edit.getText().toString();

                    params.put("pan11", pan11);
                }
                if (pan12 != null && !pan12.equals("")) {
                    params.put("pan12", pan12);

                } else {
                    pan12 = panel12edit.getText().toString();

                    params.put("pan12", pan12);
                }
                if (pan13 != null && !pan13.equals("")) {
                    params.put("pan13", pan13);

                } else {
                    pan13 = panel13edit.getText().toString();

                    params.put("pan13", pan13);
                }
                if (pan14 != null && !pan14.equals("")) {
                    params.put("pan14", pan14);

                } else {
                    pan14 = panel14edit.getText().toString();

                    params.put("pan14", pan14);
                }
                if (pan15 != null && !pan15.equals("")) {
                    params.put("pan15", pan15);

                } else {
                    pan15 = panel15edit.getText().toString();

                    params.put("pan15", pan15);
                }
                if (pan16 != null && !pan16.equals("")) {
                    params.put("pan16", pan16);

                } else {
                    pan16 = panel16edit.getText().toString();

                    params.put("pan16", pan16);
                }
                if (pan17 != null && !pan17.equals("")) {
                    params.put("pan17", pan17);

                } else {
                    pan17 = panel17edit.getText().toString();

                    params.put("pan17", pan17);
                }
                if (pan18 != null && !pan18.equals("")) {
                    params.put("pan18", pan18);

                } else {
                    pan18 = panel18edit.getText().toString();

                    params.put("pan18", pan18);
                }
                if (pan19 != null && !pan19.equals("")) {
                    params.put("pan19", pan19);

                } else {
                    pan19 = panel19edit.getText().toString();

                    params.put("pan19", pan19);
                }
                if (pan20 != null && !pan20.equals("")) {
                    params.put("pan20", pan20);

                } else {
                    pan20 = panel20edit.getText().toString();

                    params.put("pan20", pan20);
                }
                if (pan21 != null && !pan21.equals("")) {
                    params.put("pan21", pan21);

                } else {
                    pan21 = panel21edit.getText().toString();

                    params.put("pan21", pan21);
                }
                if (pan22 != null && !pan22.equals("")) {
                    params.put("pan22", pan22);

                } else {
                    pan22 = panel22edit.getText().toString();

                    params.put("pan22", pan22);
                }
                if (pan23 != null && !pan23.equals("")) {
                    params.put("pan23", pan23);

                } else {
                    pan23 = panel23edit.getText().toString();

                    params.put("pan23", pan23);
                }
                if (pan24 != null && !pan24.equals("")) {
                    params.put("pan24", pan24);

                } else {
                    pan24 = panel24edit.getText().toString();

                    params.put("pan24", pan24);
                }
                if (pan25 != null && !pan25.equals("")) {
                    params.put("pan25", pan25);

                } else {
                    pan25 = panel25edit.getText().toString();

                    params.put("pan25", pan25);
                }
                if (pan26 != null && !pan26.equals("")) {
                    params.put("pan26", pan26);

                } else {
                    pan26 = panel26edit.getText().toString();

                    params.put("pan26", pan26);
                }
                if (pan27 != null && !pan27.equals("")) {
                    params.put("pan27", pan27);

                } else {
                    pan27 = panel27edit.getText().toString();

                    params.put("pan27", pan27);
                }
                if (pan28 != null && !pan28.equals("")) {
                    params.put("pan28", pan28);

                } else {
                    pan28 = panel28edit.getText().toString();

                    params.put("pan28", pan28);
                }


                if (PumpSerial != null && !PumpSerial.equals("")) {
                    params.put("PumpSerial", PumpSerial);

                } else {
                    PumpSerial = pumpSerail.getText().toString();

                    params.put("PumpSerial", PumpSerial);
                }
                if (motorSerial != null && !motorSerial.equals("")) {
                    params.put("motorSerial", motorSerial);

                } else {
                    motorSerial = MotorSerial.getText().toString();

                    params.put("motorSerial", motorSerial);
                }
                if (controllerNumber != null && !controllerNumber.equals("")) {
                    params.put("controllerNumber", controllerNumber);

                } else {
                    controllerNumber = ControllerNumber.getText().toString();

                    params.put("controllerNumber", controllerNumber);
                }
                if (rmuNumber != null && !rmuNumber.equals("")) {
                    params.put("rmuNumber", rmuNumber);

                } else {
                    rmuNumber = RmuNumber.getText().toString();

                    params.put("rmuNumber", rmuNumber);
                }


                params.put("radio_pipe", radio_pipe_string);
                params.put("radio_cable", radio_cable_string);
                if (simno != null && !simno.equals("")) {
                    params.put("sim_no", simno);
                } else {
                    params.put("sim_no", SimNumber.getText().toString());
                }
                if (structure_checkbox.isChecked()) {
                    structure_checkbox_string = "Yes";
                    params.put("structure_checkbox_string", structure_checkbox_string);
                } else {
                    structure_checkbox_string = "No";
                    params.put("structure_checkbox_string", structure_checkbox_string);

                }


                if (accessories_checkbox.isChecked()) {
                    accessories_checkbox_string = "Yes";
                    params.put("accessories_checkbox_string", accessories_checkbox_string);
                } else {
                    accessories_checkbox_string = "No";
                    params.put("accessories_checkbox_string", accessories_checkbox_string);

                }


                if (rod_checkbox.isChecked()) {
                    rod_checkbox_string = "Yes";
                    params.put("rod_checkbox_string", rod_checkbox_string);
                } else {
                    rod_checkbox_string = "No";
                    params.put("rod_checkbox_string", rod_checkbox_string);

                }


                if (farma_checkbox.isChecked()) {
                    farma_checkbox_string = "Yes";
                    params.put("farma_checkbox_string", farma_checkbox_string);
                } else {
                    farma_checkbox_string = "No";
                    params.put("farma_checkbox_string", farma_checkbox_string);

                }
                params.put("pump_type", pump_type);
                params.put("reg_number", regnnumber);
                params.put("eng_id", eng_id);
                params.put("project", project);
                params.put("village", villageName.getText().toString());
//                params.put("sim_mob_no",mobileno);
                return params;
            }
        };

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(30000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        stringRequest.setShouldCache(false);


        MySingleton.getInstance(getApplicationContext()).addTorequestque(stringRequest);
    }

public void panelToServer(EditText panel){

    panel.addTextChangedListener(new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            panel.setTextColor(getResources().getColor(R.color.black));
        }
        @Override
        public void afterTextChanged(Editable s) {
            if(String.valueOf(s).length()>=18){
                if(!checkDuplicateLocal(panel,String.valueOf(s)))
                   uploadPanels(String.valueOf(s),panel);
                }
        }
    });
}

    public void uploadPanels(String panel_no, EditText panel) {
        pb.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://gautamsolar.co.in/pumpall_api/check_panel.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                pb.dismiss();

                try {
                    JSONObject obj = new JSONObject(response);
                    //  Toast.makeText(getApplicationContext(),response,Toast.LENGTH_SHORT).show();
                    Log.d("response_panel",response);
                    int status = obj.getInt("status");
                    String code = obj.getString("code");
                    if (status == 0) {
                        Toast.makeText(Material.this,code,Toast.LENGTH_SHORT).show();
                        panel.setTextColor(getResources().getColor(R.color.primary_darker));
                    }
                    pb.dismiss();
                } catch (JSONException e) {
                    pb.dismiss();
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                pb.dismiss();
                Toast.makeText(Material.this,error.toString(),Toast.LENGTH_SHORT).show();

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("eng_id", eng_id);
                params.put("panel", panel_no);
                return params;
            }
        };

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(10000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        stringRequest.setShouldCache(false);
        MySingleton.getInstance(getApplicationContext()).addTorequestque(stringRequest);
    }
public Boolean checkDuplicateLocal(EditText panel,String panelNo){

        if(panel.getId()!=panel1edit.getId()&&panelNo.equals(panel1edit.getText().toString())){
            panel.setTextColor(getResources().getColor(R.color.primary_darker));
            Toast.makeText(Material.this,"Panel No Already Exist",Toast.LENGTH_SHORT).show();
            return true;
        }else if(panel.getId()!=panel2edit.getId()&&panelNo.equals(panel2edit.getText().toString())){
            Toast.makeText(Material.this,"Panel No Already Exist",Toast.LENGTH_SHORT).show();
            panel.setTextColor(getResources().getColor(R.color.primary_darker));
            return true;
        }else if(panel.getId()!=panel3edit.getId()&&panelNo.equals(panel3edit.getText().toString())){
            Toast.makeText(Material.this,"Panel No Already Exist",Toast.LENGTH_SHORT).show();
            panel.setTextColor(getResources().getColor(R.color.primary_darker));
            return true;
        }else if(panel.getId()!=panel4edit.getId()&&panelNo.equals(panel4edit.getText().toString())){
            panel.setTextColor(getResources().getColor(R.color.primary_darker));
            Toast.makeText(Material.this,"Panel No Already Exist",Toast.LENGTH_SHORT).show();
            return true;
        }else if(panel.getId()!=panel5edit.getId()&&panelNo.equals(panel5edit.getText().toString())){
            panel.setTextColor(getResources().getColor(R.color.primary_darker));
            Toast.makeText(Material.this,"Panel No Already Exist",Toast.LENGTH_SHORT).show();
            return true;
        }else if(panel.getId()!=panel6edit.getId()&&panelNo.equals(panel6edit.getText().toString())){
            panel.setTextColor(getResources().getColor(R.color.primary_darker));
            Toast.makeText(Material.this,"Panel No Already Exist",Toast.LENGTH_SHORT).show();
            return true;
        }else if(panel.getId()!=panel7edit.getId()&&panelNo.equals(panel7edit.getText().toString())){
            panel.setTextColor(getResources().getColor(R.color.primary_darker));
            Toast.makeText(Material.this,"Panel No Already Exist",Toast.LENGTH_SHORT).show();
            return true;
        }else if(panel.getId()!=panel8edit.getId()&&panelNo.equals(panel8edit.getText().toString())){
            panel.setTextColor(getResources().getColor(R.color.primary_darker));
            Toast.makeText(Material.this,"Panel No Already Exist",Toast.LENGTH_SHORT).show();
            return true;
        }else if(panel.getId()!=panel9edit.getId()&&panelNo.equals(panel9edit.getText().toString())){
            panel.setTextColor(getResources().getColor(R.color.primary_darker));
            Toast.makeText(Material.this,"Panel No Already Exist",Toast.LENGTH_SHORT).show();
            return true;
        }else if(panel.getId()!=panel10edit.getId()&&panelNo.equals(panel10edit.getText().toString())){
            panel.setTextColor(getResources().getColor(R.color.primary_darker));
            Toast.makeText(Material.this,"Panel No Already Exist",Toast.LENGTH_SHORT).show();
            return true;
        }else if(panel.getId()!=panel11edit.getId()&&panelNo.equals(panel11edit.getText().toString())){
            panel.setTextColor(getResources().getColor(R.color.primary_darker));
            Toast.makeText(Material.this,"Panel No Already Exist",Toast.LENGTH_SHORT).show();
            return true;
        }else if(panel.getId()!=panel12edit.getId()&&panelNo.equals(panel12edit.getText().toString())){
            panel.setTextColor(getResources().getColor(R.color.primary_darker));
            Toast.makeText(Material.this,"Panel No Already Exist",Toast.LENGTH_SHORT).show();
            return true;
        }else if(panel.getId()!=panel13edit.getId()&&panelNo.equals(panel13edit.getText().toString())){
            panel.setTextColor(getResources().getColor(R.color.primary_darker));
            Toast.makeText(Material.this,"Panel No Already Exist",Toast.LENGTH_SHORT).show();
            return true;
        }else if(panel.getId()!=panel14edit.getId()&&panelNo.equals(panel14edit.getText().toString())){
            panel.setTextColor(getResources().getColor(R.color.primary_darker));
            Toast.makeText(Material.this,"Panel No Already Exist",Toast.LENGTH_SHORT).show();
            return true;
        }else if(panel.getId()!=panel15edit.getId()&&panelNo.equals(panel15edit.getText().toString())){
            panel.setTextColor(getResources().getColor(R.color.primary_darker));
            Toast.makeText(Material.this,"Panel No Already Exist",Toast.LENGTH_SHORT).show();
            return true;
        }else if(panel.getId()!=panel16edit.getId()&&panelNo.equals(panel16edit.getText().toString())){
            panel.setTextColor(getResources().getColor(R.color.primary_darker));
            Toast.makeText(Material.this,"Panel No Already Exist",Toast.LENGTH_SHORT).show();
            return true;
        }else if(panel.getId()!=panel17edit.getId()&&panelNo.equals(panel17edit.getText().toString())){
            panel.setTextColor(getResources().getColor(R.color.primary_darker));
            Toast.makeText(Material.this,"Panel No Already Exist",Toast.LENGTH_SHORT).show();
            return true;
        }else if(panel.getId()!=panel18edit.getId()&&panelNo.equals(panel18edit.getText().toString())){
            panel.setTextColor(getResources().getColor(R.color.primary_darker));
            Toast.makeText(Material.this,"Panel No Already Exist",Toast.LENGTH_SHORT).show();
            return true;
        }else if(panel.getId()!=panel19edit.getId()&&panelNo.equals(panel19edit.getText().toString())){
            panel.setTextColor(getResources().getColor(R.color.primary_darker));
            Toast.makeText(Material.this,"Panel No Already Exist",Toast.LENGTH_SHORT).show();
            return true;
        }else if(panel.getId()!=panel20edit.getId()&&panelNo.equals(panel20edit.getText().toString())){
            panel.setTextColor(getResources().getColor(R.color.primary_darker));
            Toast.makeText(Material.this,"Panel No Already Exist",Toast.LENGTH_SHORT).show();
            return true;
        }else if(panel.getId()!=panel21edit.getId()&&panelNo.equals(panel21edit.getText().toString())){
            panel.setTextColor(getResources().getColor(R.color.primary_darker));
            Toast.makeText(Material.this,"Panel No Already Exist",Toast.LENGTH_SHORT).show();
            return true;
        }else if(panel.getId()!=panel22edit.getId()&&panelNo.equals(panel22edit.getText().toString())){
            panel.setTextColor(getResources().getColor(R.color.primary_darker));
            Toast.makeText(Material.this,"Panel No Already Exist",Toast.LENGTH_SHORT).show();
            return true;
        }else if(panel.getId()!=panel23edit.getId()&&panelNo.equals(panel23edit.getText().toString())){
            panel.setTextColor(getResources().getColor(R.color.primary_darker));
            Toast.makeText(Material.this,"Panel No Already Exist",Toast.LENGTH_SHORT).show();
            return true;
        }else if(panel.getId()!=panel24edit.getId()&&panelNo.equals(panel24edit.getText().toString())){
            panel.setTextColor(getResources().getColor(R.color.primary_darker));
            Toast.makeText(Material.this,"Panel No Already Exist",Toast.LENGTH_SHORT).show();
            return true;
        }else if(panel.getId()!=panel25edit.getId()&&panelNo.equals(panel25edit.getText().toString())){
            panel.setTextColor(getResources().getColor(R.color.primary_darker));
            Toast.makeText(Material.this,"Panel No Already Exist",Toast.LENGTH_SHORT).show();
            return true;
        }else if(panel.getId()!=panel26edit.getId()&&panelNo.equals(panel26edit.getText().toString())){
            panel.setTextColor(getResources().getColor(R.color.primary_darker));
            Toast.makeText(Material.this,"Panel No Already Exist",Toast.LENGTH_SHORT).show();
            return true;
        }else if(panel.getId()!=panel27edit.getId()&&panelNo.equals(panel27edit.getText().toString())){
            panel.setTextColor(getResources().getColor(R.color.primary_darker));
            Toast.makeText(Material.this,"Panel No Already Exist",Toast.LENGTH_SHORT).show();
            return true;
        }else if(panel.getId()!=panel28edit.getId()&&panelNo.equals(panel28edit.getText().toString())){
            panel.setTextColor(getResources().getColor(R.color.primary_darker));
            Toast.makeText(Material.this,"Panel No Already Exist",Toast.LENGTH_SHORT).show();
            return true;
        }else{
            return false;
        }

    }
}
