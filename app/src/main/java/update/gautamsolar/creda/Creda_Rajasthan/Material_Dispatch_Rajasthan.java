package update.gautamsolar.creda.Creda_Rajasthan;

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
import update.gautamsolar.creda.MySingleton;
import update.gautamsolar.creda.SharedPrefManager;
import update.gautamsolar.creda.UploadAll;

public class Material_Dispatch_Rajasthan extends AppCompatActivity {


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
    private static final int SEVENTEEN_REQUEST_CODE = 36;
    private static final int EIGHTEEN_REQUEST_CODE = 37;
    private static final int NINTEEN_REQUEST_CODE = 38;
    private static final int TWENTY_REQUEST_CODE = 39;
    private static final int TWENTY_ONE_REQUEST_CODE = 409;
    private static final int TWENTY_TWO_REQUEST_CODE = 410;
    private static final int TWENTY_THREE_REQUEST_CODE = 411;
    private static final int TWENTY_FOUR_REQUEST_CODE = 413;
    private static final int TWENTY_FIVE_REQUEST_CODE = 419;
    private static final int TWENTY_SIX_REQUEST_CODE = 447;
    AlertDialog.Builder builder;
    RadioGroup radiopipe, radiocable;
    RadioButton radio100pipe, radio70pipe, radio50pipe,
            radio30pipe, radio100cable,
            radio70cable, radio50cable, radio30cable;
    Dialog dialog;
    CheckBox structure_checkbox, accessories_checkbox, farma_checkbox, rod_checkbox;


    String hommelight_panel_string, homeLight_panel_battery_serial, home_light_charge_controller, home_luminary_serial1, home_luminary_serial2, imei_number_string, radio_pipe_string,
            structure_checkbox_string, radio_cable_string, accessories_checkbox_string, farma_checkbox_string,
            rod_checkbox_string, pump_type, pump_capacity,project;

    private String GETPANEL = "http://gautamsolar.co.in/credanew/credaAndroidApi/get_panelnew.php";
    private String myurl2 = "http://gautamsolar.co.in/credanew/credaAndroidApi/MaterialUploadNewjatin.php";

    ImageButton panel1btn, panel2btn,
            panel3btn, panel4btn, panel5btn, panel6btn,
            panel7btn, panel8btn, panel9btn, panel10btn, panel11btn,
            panel12btn, panel13btn, panel14btn, panel15btn, panel16btn,panel17btn,panel18btn,panel19btn,panel20btn,panel21btn
            ,panel22btn, btnPumpScan, btnMotorScan,
            ControllerbtnNumber, btnRmuNumber, IMEIscan, HomeLightpanelscan, Homelightbatteryscan, cahrgecontrollerscan, LuminaryLight1scan, luminarylight2scan;
    Button btnDisplay;
    ImageButton btnAdd;
    ProgressDialog progressDialog;
    TextView Beneficiary_textName, Beneficiary_txtRegNo;
    ProgressDialog pb;
    TextView Father, Contactid, Villageide, Pumpide, Contactide, Blockid, districtid,numberid;
    EditText panel1edit, panel2edit,
            panel3edit, panel4edit, panel5edit,
            panel6edit, panel7edit, panel8edit, panel9edit,panel10edit
            ,panel11edit,panel12edit,panel13edit,
    panel14edit,panel15edit,panel16edit,panel17edit,panel18edit,panel19edit,panel20edit,panel21edit
            ,panel22edit, pumpSerail, MotorSerial, RmuNumber, ControllerNumber, SimNumber, MobileNumber, IMEIedit, PanelEdit, HomeLightpanelEdit, Homelightbatteryedit, cahrgecontrolleredit, LuminaryLight1edit, luminarylight2edit;
    String Engineer_contact, BenificiaryName, reg_no, PumpSerial, engineer_role,
            motorSerial, rmuNumber, controllerNumber;
    String fetch_material_dispatch, rmu_raj_mat_api, eng_id, Engineer_Contact, pan1, pan2, pan3, pan4, pan5, pan6, pan7, pan8,
            pan9, pan10, pan11, pan12, pan13, pan14, pan15, pan16,pan17,pan18,pan19,pan20,pan21,pan22, pannew1, pannew2, pannew3,
            pannew4, pannew5, pannew6, pannew7, pannew8, pannew9, pannew10, pannew11, pannew12, pannew13, pannew14,
            pannew15, pannew16,pannew17,pannew18,pannew19,pannew20,pannew21,pannew22, fathername, benifname, regnnumber, contact, block, village, dispatch_status, simno, rmuno, mobileno,
            controler_srno, controler_rms_id, pumpserialnew, motorserialnew;
    private IntentIntegrator qrScan1, qrScan2, qrScan3, qrScan4, qrScan5, qrScan6,
            qrScan7, qrScan8, qrScan9, qrScan10, qrScan11, qrScan12, qrScan13, qrScan14, qrScan15, qrScan16,
            qrScan17, qrScan18, qrScan19, qrScan20,qrScan_17,qrScan_18,qrScan_19,qrScan_20,qrScan_21,qrScan_22, qrScan21, qrScan22, qrScan23, qrScan24, qrScan25, qrScan26;
    SharedPreferences sharedPreferences;
    Constants constants;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_material__dispatch__rajasthan);
        getSupportActionBar().setTitle("Pump Installation RAJASTHAN");
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        Engineer_Contact = sharedPreferences.getString("engcontact", "");
        eng_id = sharedPreferences.getString("eng_id", "");
        rmu_raj_mat_api = constants.MATERIAL_DISPATCH;
        fetch_material_dispatch = constants.FETCH_MATERIAL_DISPATCH;
        builder = new AlertDialog.Builder(Material_Dispatch_Rajasthan.this);
        /*TEXT VIEW*/


        farma_checkbox = findViewById(R.id.farma_checkboxrdm);
        rod_checkbox = findViewById(R.id.rod_checkboxrdm);
        dialog = new Dialog(Material_Dispatch_Rajasthan.this); // Cont
        pb = new ProgressDialog(this, R.style.MyGravity);
        pb.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        pb.setIndeterminateDrawable(getResources().getDrawable(R.drawable.custom_progress_dialog));
        pb.setCancelable(false);

//        Beneficiary_textName= findViewById( R.id.Beneficiary_textName );
//        Beneficiary_txtRegNo= findViewById( R.id.Beneficiary_txtRegNo );

        /*16btnpanel*/
        radiopipe = (RadioGroup) findViewById(R.id.radiopiperdm);
        radio100pipe = (RadioButton) findViewById(R.id.radio100piperdm);
        radio70pipe = (RadioButton) findViewById(R.id.radio70piperdm);
        radio50pipe = (RadioButton) findViewById(R.id.radio50piperdm);
        radio30pipe = (RadioButton) findViewById(R.id.radio30piperdm);
        SimNumber = (EditText) findViewById(R.id.simeonerdm);
        MobileNumber = (EditText) findViewById(R.id.mobileonerdm);
        IMEIscan = (ImageButton) findViewById(R.id.scanimeinumberrdm);
        HomeLightpanelscan = (ImageButton) findViewById(R.id.scanhomelightpanelserialrdm);
        Homelightbatteryscan = (ImageButton) findViewById(R.id.scanhomelightbatteryrdm);
        cahrgecontrollerscan = (ImageButton) findViewById(R.id.scanchargecontrollerrdm);
        LuminaryLight1scan = (ImageButton) findViewById(R.id.scanluminarylight1rdm);
        luminarylight2scan = (ImageButton) findViewById(R.id.luminarylight2rdm);
        IMEIedit = (EditText) findViewById(R.id.imeinumbereditrdm);

        HomeLightpanelEdit = (EditText) findViewById(R.id.homelightpanelserialeditrdm);
        Homelightbatteryedit = (EditText) findViewById(R.id.scanhomelightbatteryeditrdm);
        cahrgecontrolleredit = (EditText) findViewById(R.id.chargecontrollereditrdm);
        LuminaryLight1edit = (EditText) findViewById(R.id.luminarylight1editrdm);
        luminarylight2edit = (EditText) findViewById(R.id.Luminarylighttwoeditrdm);

        radiocable = (RadioGroup) findViewById(R.id.radiocablerdm);
        radio100cable = (RadioButton) findViewById(R.id.radio100cablerdm);
        radio70cable = (RadioButton) findViewById(R.id.radio70cablerdm);
        radio50cable = (RadioButton) findViewById(R.id.radio50cablerdm);
        radio30cable = (RadioButton) findViewById(R.id.radio30cablerdm);
        Father = (TextView) findViewById(R.id.fatheride);
        Contactid = (TextView) findViewById(R.id.contactide);
        Villageide = (TextView) findViewById(R.id.villageide);
        Pumpide = (TextView) findViewById(R.id.benificiaryide);
        Blockid = (TextView) findViewById(R.id.blockide);
        numberid=findViewById(R.id.numberid);
        Bundle bundlem = getIntent().getExtras();
        fathername = bundlem.getString("fathername");
        regnnumber = bundlem.getString("regnnumber");
        benifname = bundlem.getString("benifname");
        village = bundlem.getString("village");
        block = bundlem.getString("block");
        contact = bundlem.getString("contact");
        dispatch_status = bundlem.getString("dispatch_status");
        Father.setText(fathername);
        Contactid.setText(contact);
        Villageide.setText(village);
        Blockid.setText(block);
        Pumpide.setText(benifname);
        numberid.setText(regnnumber);
        dialog.setContentView(R.layout.uploaddialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setCancelable(false);


        structure_checkbox = findViewById(R.id.structure_checkboxrdm);
        accessories_checkbox = findViewById(R.id.accessories_checkboxrdm);


        panel1btn = findViewById(R.id.panel1btnrdm);
        panel2btn = findViewById(R.id.panel2btnrdm);
        panel3btn = findViewById(R.id.panel3btnrdm);
        panel4btn = findViewById(R.id.panel4btnrdm);
        panel5btn = findViewById(R.id.panel5btnrdm);
        panel6btn = findViewById(R.id.panel6btnrdm);
        panel7btn = findViewById(R.id.panel7btnrdm);
        panel8btn = findViewById(R.id.panel8btnrdm);
        panel9btn = findViewById(R.id.panel9btnrdm);
        panel10btn = findViewById(R.id.panel10btnrdm);
        panel11btn =  findViewById(R.id.panel11btn);
        panel12btn =  findViewById(R.id.panel12btn);
        panel13btn =  findViewById(R.id.panel13btn);
        panel14btn =  findViewById(R.id.panel14btn);
        panel15btn =  findViewById(R.id.panel15btn);
        panel16btn =  findViewById(R.id.panel16btn);
        panel17btn =  findViewById(R.id.panel17btn);
        panel18btn =  findViewById(R.id.panel18btn);
        panel19btn =  findViewById(R.id.panel19btn);
        panel20btn =  findViewById(R.id.panel20btn);
        panel21btn =  findViewById(R.id.panel21btn);
        panel22btn =  findViewById(R.id.panel22btn);
        btnPumpScan = findViewById(R.id.btnPumpScanrdm);
        btnMotorScan = findViewById(R.id.btnMotorScanrdm);
        ControllerbtnNumber = findViewById(R.id.ControllerbtnNumberrdm);
        btnRmuNumber = findViewById(R.id.btnRmuNumberrdm);



        /*end 16 panel*/
        /*Panel 16 Edit Text*/
        panel1edit = findViewById(R.id.panel1editrdm);
        panel2edit = findViewById(R.id.panel2editrdm);
        panel3edit = findViewById(R.id.panel3editrdm);
        panel4edit = findViewById(R.id.panel4editrdm);
        panel5edit = findViewById(R.id.panel5editrdm);
        panel6edit = findViewById(R.id.panel6editrdm);
        panel7edit = findViewById(R.id.panel7editrdm);
        panel8edit = findViewById(R.id.panel8editrdm);
        panel9edit = findViewById(R.id.panel9editrdm);
        panel10edit = findViewById(R.id.panel10editrdm);
        panel11edit = findViewById( R.id.panel11edit );
        panel12edit = findViewById( R.id.panel12edit );
        panel13edit = findViewById( R.id.panel13edit );
        panel14edit = findViewById( R.id.panel14edit );
        panel15edit = findViewById( R.id.panel15edit );
        panel16edit = findViewById( R.id.panel16edit );
        panel17edit = findViewById( R.id.panel17edit );
        panel18edit = findViewById( R.id.panel18edit );
        panel19edit = findViewById( R.id.panel19edit );
        panel20edit = findViewById( R.id.panel20edit );
        panel21edit = findViewById( R.id.panel21edit );
        panel22edit = findViewById( R.id.panel22edit );
        pumpSerail = findViewById(R.id.pumpSerailrdm);
        MotorSerial = findViewById(R.id.MotorSerialrdm);
        RmuNumber = findViewById(R.id.RmuNumberrdm);
        ControllerNumber = findViewById(R.id.ControllerNumberrdm);

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
  qrScan_20= new IntentIntegrator(this);
qrScan_21= new IntentIntegrator(this);
qrScan_22= new IntentIntegrator(this);

        qrScan17 = new IntentIntegrator(this);
        qrScan18 = new IntentIntegrator(this);
        qrScan19 = new IntentIntegrator(this);
        qrScan20 = new IntentIntegrator(this);
        qrScan21 = new IntentIntegrator(this);
        qrScan22 = new IntentIntegrator(this);
        qrScan23 = new IntentIntegrator(this);
        qrScan24 = new IntentIntegrator(this);
        qrScan25 = new IntentIntegrator(this);
        qrScan26 = new IntentIntegrator(this);



        /*  btnAdd = (ImageButton) findViewById(R.id.btnAdd);*/
        btnDisplay = (Button) findViewById(R.id.btnDisplayrdm);
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

        project = sharedPreferences.getString("project", "");

        RmuNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                rmuno = String.valueOf(s);


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
                if (checkedId == R.id.radio100piperdm) {

                    radio_pipe_string = "100";
                } else if (checkedId == R.id.radio70piperdm) {

                    radio_pipe_string = "70";

                } else if (checkedId == R.id.radio50piperdm) {

                    radio_pipe_string = "50";

                } else if (checkedId == R.id.radio30piperdm) {

                    radio_pipe_string = "30";

                }

            }
        });

        /*cable Radio*/

        radiocable.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.radio100cablerdm) {

                    radio_cable_string = "100";
                } else if (checkedId == R.id.radio70cablerdm) {

                    radio_cable_string = "70";

                } else if (checkedId == R.id.radio50cablerdm) {

                    radio_cable_string = "50";

                } else if (checkedId == R.id.radio30cablerdm) {

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
               
                panel11btn.setVisibility(View.GONE);
                panel12btn.setVisibility(View.GONE);
                panel13btn.setVisibility(View.GONE);
                panel14btn.setVisibility(View.GONE);
                panel15btn.setVisibility(View.GONE);
                panel16btn.setVisibility(View.GONE);
                panel17btn.setVisibility(View.GONE);
                panel18btn.setVisibility(View.GONE);
                panel19btn.setVisibility(View.GONE);
                panel20btn.setVisibility(View.GONE);
                panel21btn.setVisibility(View.GONE);
                panel22btn.setVisibility(View.GONE);
            }

            if (pump_capacity.equals("05 HP") || pump_capacity.equals("5 HP") || pump_capacity.equals("5HP") || pump_capacity.equals("05HP")) {


                panel16edit.setVisibility(View.GONE);
                panel17edit.setVisibility(View.GONE);
                panel18edit.setVisibility(View.GONE);
                panel19edit.setVisibility(View.GONE);
                panel20edit.setVisibility(View.GONE);
                panel21edit.setVisibility(View.GONE);
                panel22edit.setVisibility(View.GONE);
              


                panel16btn.setVisibility(View.GONE);
                panel17btn.setVisibility(View.GONE);
                panel18btn.setVisibility(View.GONE);
                panel19btn.setVisibility(View.GONE);
                panel20btn.setVisibility(View.GONE);
                panel21btn.setVisibility(View.GONE);
                panel22btn.setVisibility(View.GONE);
                
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
                startActivityForResult(qrScan11.createScanIntent(), ELEVEN_REQUEST_CODE  );

            }
        });
        panel12btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(qrScan12.createScanIntent(), TWELVE_REQUEST_CODE  );

            }
        });
        panel13btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(qrScan13.createScanIntent(), THIRTEEN_REQUEST_CODE  );

            }
        });
        panel14btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(qrScan14.createScanIntent(), FOURTEEN_REQUEST_CODE  );

            }
        });
        panel15btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(qrScan15.createScanIntent(), FIFTEEN_REQUEST_CODE  );

            }
        });
        panel16btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(qrScan16.createScanIntent(), SIXTEEN_REQUEST_CODE  );

            }
        }); panel17btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(qrScan_17.createScanIntent(), SEVENTEENREQUEST_CODE  );

            }
        }); panel18btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(qrScan_18.createScanIntent(), EIGHTEENREQUEST_CODE );

            }
        }); panel19btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(qrScan_19.createScanIntent(), NINETEENREQUEST_CODE  );

            }
        }); panel20btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(qrScan_20.createScanIntent(), TWENTYREQUEST_CODE  );

            }
        }); panel21btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(qrScan_21.createScanIntent(), TWENTYONEREQUEST_CODE );

            }
        }); panel22btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(qrScan_22.createScanIntent(), TWENTYTWOREQUEST_CODE  );

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
        IMEIscan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(qrScan20.createScanIntent(), TWENTY_ONE_REQUEST_CODE);
            }
        });
        HomeLightpanelscan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(qrScan20.createScanIntent(), TWENTY_TWO_REQUEST_CODE);
            }
        });
        Homelightbatteryscan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(qrScan20.createScanIntent(), TWENTY_THREE_REQUEST_CODE);

            }
        });
        cahrgecontrollerscan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(qrScan20.createScanIntent(), TWENTY_FOUR_REQUEST_CODE);

            }
        });
        LuminaryLight1scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(qrScan20.createScanIntent(), TWENTY_FIVE_REQUEST_CODE);
            }
        });
        luminarylight2scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(qrScan20.createScanIntent(), TWENTY_SIX_REQUEST_CODE);
            }
        });

        btnDisplay.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (RmuNumber.getText().toString().length()!=15)
                {
                    Toast.makeText(getApplicationContext(),"Please Enter 15 digit IMEI Number ",Toast.LENGTH_SHORT).show();

                }
//                if (MobileNumber.getText().toString().length()!=10) {
//                    Toast.makeText(getApplicationContext(), "Please Enter correct Mobile number", Toast.LENGTH_SHORT).show();
//                }
//                else if (rmuno.length() < 19 || rmuno.length() < 20) {
//                    Toast.makeText(getApplicationContext(), "Please Enter 19 or 20 digit Rmu Number ", Toast.LENGTH_SHORT).show();

//                }
//                else if (SimNumber.getText().toString().equals("")) {
//                    Toast.makeText(getApplicationContext(), "Please Enter 20 or 22 digit sim number", Toast.LENGTH_SHORT).show();
//
//                } else if (simno.length() < 20 && simno.length() < 22) {
//                    Toast.makeText(getApplicationContext(), "Please Enter 20 or 22 digit sim number", Toast.LENGTH_SHORT).show();
//
//                } else if (mobileno.length() < 13 || mobileno.equals("")) {
//                    Toast.makeText(getApplicationContext(), "Please Enter 13 digit Mobile number", Toast.LENGTH_SHORT).show();
//
//                }
//                else if (!RmuNumber.getText().toString().startsWith("GSSP") && !RmuNumber.getText().toString().startsWith("GSSPM")) {
//                    Toast.makeText(getApplicationContext(), "Please enter rmu start with GSSP or GSSPM  ", Toast.LENGTH_SHORT).show();
//                }
//                else if (!SimNumber.getText().toString().equals("") && SimNumber.getText().toString().length() < 19 && SimNumber.getText().toString().length() < 20) {
//                    Toast.makeText(getApplicationContext(), "Please Enter 19 or 20 digit Sim Number ", Toast.LENGTH_SHORT).show();
//
//                } else if (panel1edit.getText().toString().length() < 19) {
//                    Toast.makeText(getApplicationContext(), "Please Enter 19 or 20 Digit panel Number", Toast.LENGTH_SHORT).show();
//
//                } else if (panel2edit.getText().toString().length() < 19) {
//                    Toast.makeText(getApplicationContext(), "Please Enter 19 or 20 Digit panel Number", Toast.LENGTH_SHORT).show();
//
//                } else if (panel3edit.getText().toString().length() < 19) {
//                    Toast.makeText(getApplicationContext(), "Please Enter 19 or 20 Digit panel Number", Toast.LENGTH_SHORT).show();
//
//                } else if (panel4edit.getText().toString().length() < 19) {
//                    Toast.makeText(getApplicationContext(), "Please Enter 19 or 20 Digit panel Number", Toast.LENGTH_SHORT).show();
//
//                } else if (panel5edit.getText().toString().length() < 19) {
//                    Toast.makeText(getApplicationContext(), "Please Enter 19 or 20 Digit panel Number", Toast.LENGTH_SHORT).show();
//
//                } else if (panel6edit.getText().toString().length() < 19) {
//                    Toast.makeText(getApplicationContext(), "Please Enter 19 or 20 Digit panel Number", Toast.LENGTH_SHORT).show();
//
//                } else if (panel7edit.getText().toString().length() < 19) {
//                    Toast.makeText(getApplicationContext(), "Please Enter 19 or 20 Digit panel Number", Toast.LENGTH_SHORT).show();
//
//                }  else if (panel8edit.getText().toString().length() < 19) {
//                    Toast.makeText(getApplicationContext(), "Please Enter 19 or 20 Digit panel Number", Toast.LENGTH_SHORT).show();
//
//                } else if (panel9edit.getText().toString().length() < 19) {
//                    Toast.makeText(getApplicationContext(), "Please Enter 19 or 20 Digit panel Number", Toast.LENGTH_SHORT).show();
//
//                } else if (panel10edit.getText().toString().length() < 19) {
//                    Toast.makeText(getApplicationContext(), "Please Enter 19 or 20 Digit panel Number", Toast.LENGTH_SHORT).show();
//
//                }else if (panel11edit.getText().toString().length() < 19) {
//                    Toast.makeText(getApplicationContext(), "Please Enter 19 or 20 Digit panel Number", Toast.LENGTH_SHORT).show();
//
//                }else if (panel12edit.getText().toString().length() < 19) {
//                    Toast.makeText(getApplicationContext(), "Please Enter 19 or 20 Digit panel Number", Toast.LENGTH_SHORT).show();
//
//                }else if (panel13edit.getText().toString().length() < 19) {
//                    Toast.makeText(getApplicationContext(), "Please Enter 19 or 20 Digit panel Number", Toast.LENGTH_SHORT).show();
//
//                }else if (panel14edit.getText().toString().length() < 19) {
//                    Toast.makeText(getApplicationContext(), "Please Enter 19 or 20 Digit panel Number", Toast.LENGTH_SHORT).show();
//
//                }else if (panel15edit.getText().toString().length() < 19) {
//                    Toast.makeText(getApplicationContext(), "Please Enter 19 or 20 Digit panel Number", Toast.LENGTH_SHORT).show();
//
//                }else if (panel16edit.getText().toString().length() < 19) {
//                    Toast.makeText(getApplicationContext(), "Please Enter 19 or 20 Digit panel Number", Toast.LENGTH_SHORT).show();
//
//                }else if (panel17edit.getText().toString().length() < 19) {
//                    Toast.makeText(getApplicationContext(), "Please Enter 19 or 20 Digit panel Number", Toast.LENGTH_SHORT).show();
//
//                }else if (panel18edit.getText().toString().length() < 19) {
//                    Toast.makeText(getApplicationContext(), "Please Enter 19 or 20 Digit panel Number", Toast.LENGTH_SHORT).show();
//
//                }else if (panel19edit.getText().toString().length() < 19) {
//                    Toast.makeText(getApplicationContext(), "Please Enter 19 or 20 Digit panel Number", Toast.LENGTH_SHORT).show();
//
//                }else if (panel20edit.getText().toString().length() < 19) {
//                    Toast.makeText(getApplicationContext(), "Please Enter 19 or 20 Digit panel Number", Toast.LENGTH_SHORT).show();
//
//                }else if (panel21edit.getText().toString().length() < 19) {
//                    Toast.makeText(getApplicationContext(), "Please Enter 19 or 20 Digit panel Number", Toast.LENGTH_SHORT).show();
//
//                }else if (panel22edit.getText().toString().length() < 19) {
//                    Toast.makeText(getApplicationContext(), "Please Enter 19 or 20 Digit panel Number", Toast.LENGTH_SHORT).show();
//
//                }
               else {
                    ConnectivityManager cm = (ConnectivityManager) Material_Dispatch_Rajasthan.this.getSystemService(Context.CONNECTIVITY_SERVICE);
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
                    Toast.makeText(Material_Dispatch_Rajasthan.this, "Permission Granted Successfully! ", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(Material_Dispatch_Rajasthan.this, "Permission Denied :( ", Toast.LENGTH_LONG).show();
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
                            panel1edit.setText(result.getContents().toString().trim());
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

                            panel2edit.setText(result.getContents().toString().trim());
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

                            panel3edit.setText(result.getContents().toString().trim());
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


                            panel4edit.setText(result.getContents().toString().trim());
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

                            panel5edit.setText(result.getContents().toString().trim());
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

                            panel6edit.setText(result.getContents().toString().trim());
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

                            panel7edit.setText(result.getContents().toString().trim());
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

                            panel8edit.setText(result.getContents().toString().trim());
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
                            panel9edit.setText(result.getContents().toString().trim());
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
                            panel10edit.setText(result.getContents().toString().trim());
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
                            panel11edit.setText(result.getContents().toString().trim());
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
                            panel12edit.setText(result.getContents().toString().trim());
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

        }
        else if(requestCode==SEVENTEENREQUEST_CODE  ){

            IntentResult result = IntentIntegrator.parseActivityResult(qrScan_17.REQUEST_CODE, resultCode, data);
            if (result != null) {
                if (result.getContents() == null) {

                    Toast.makeText(this, "Result Not Found", Toast.LENGTH_LONG).show();
                } else {
                    try {

                        JSONObject obj = new JSONObject(result.getContents());

                        panel17edit.setText(obj.getString("name") + " " + obj.getString("address"));

                        pan17= String.valueOf( panel17edit.getText() );

                    } catch (JSONException e) {
                        e.printStackTrace();

                        if (result.getContents().length() < 19) {
                            Toast.makeText( this, "Length Below OR Above 19 or 20", Toast.LENGTH_LONG ).show();
                        } else {
                            panel17edit.setText( result.getContents().toString().trim() );
                            pan17 = String.valueOf( panel17edit.getText() );
                            Toast.makeText( this, result.getContents(), Toast.LENGTH_LONG ).show();
                        }
                    }


                }
            }

        }   else if(requestCode==EIGHTEENREQUEST_CODE  ){

            IntentResult result = IntentIntegrator.parseActivityResult(qrScan_18.REQUEST_CODE, resultCode, data);
            if (result != null) {
                if (result.getContents() == null) {

                    Toast.makeText(this, "Result Not Found", Toast.LENGTH_LONG).show();
                } else {
                    try {

                        JSONObject obj = new JSONObject(result.getContents());

                        panel18edit.setText(obj.getString("name") + " " + obj.getString("address"));

                        pan18= String.valueOf( panel18edit.getText() );

                    } catch (JSONException e) {
                        e.printStackTrace();

                        if (result.getContents().length() < 19) {
                            Toast.makeText( this, "Length Below OR Above 19 or 20", Toast.LENGTH_LONG ).show();
                        } else {
                            panel18edit.setText( result.getContents().toString().trim() );
                            pan18 = String.valueOf( panel18edit.getText() );
                            Toast.makeText( this, result.getContents(), Toast.LENGTH_LONG ).show();
                        }
                    }


                }
            }

        }   else if(requestCode==NINETEENREQUEST_CODE  ){

            IntentResult result = IntentIntegrator.parseActivityResult(qrScan_19.REQUEST_CODE, resultCode, data);
            if (result != null) {
                if (result.getContents() == null) {

                    Toast.makeText(this, "Result Not Found", Toast.LENGTH_LONG).show();
                } else {
                    try {

                        JSONObject obj = new JSONObject(result.getContents());

                        panel19edit.setText(obj.getString("name") + " " + obj.getString("address"));

                        pan19= String.valueOf( panel19edit.getText() );

                    } catch (JSONException e) {
                        e.printStackTrace();

                        if (result.getContents().length() < 19) {
                            Toast.makeText( this, "Length Below OR Above 19 or 20", Toast.LENGTH_LONG ).show();
                        } else {
                            panel19edit.setText( result.getContents().toString().trim() );
                            pan19 = String.valueOf( panel19edit.getText() );
                            Toast.makeText( this, result.getContents(), Toast.LENGTH_LONG ).show();
                        }
                    }


                }
            }

        }   else if(requestCode==TWENTYREQUEST_CODE  ){

            IntentResult result = IntentIntegrator.parseActivityResult(qrScan_20.REQUEST_CODE, resultCode, data);
            if (result != null) {
                if (result.getContents() == null) {

                    Toast.makeText(this, "Result Not Found", Toast.LENGTH_LONG).show();
                } else {
                    try {

                        JSONObject obj = new JSONObject(result.getContents());

                        panel20edit.setText(obj.getString("name") + " " + obj.getString("address"));

                        pan20= String.valueOf( panel20edit.getText() );

                    } catch (JSONException e) {
                        e.printStackTrace();

                        if (result.getContents().length() < 19) {
                            Toast.makeText( this, "Length Below OR Above 19 or 20", Toast.LENGTH_LONG ).show();
                        } else {
                            panel20edit.setText( result.getContents().toString().trim() );
                            pan20 = String.valueOf( panel20edit.getText() );
                            Toast.makeText( this, result.getContents(), Toast.LENGTH_LONG ).show();
                        }
                    }


                }
            }

        }   else if(requestCode==TWENTYONEREQUEST_CODE  ){

            IntentResult result = IntentIntegrator.parseActivityResult(qrScan_21.REQUEST_CODE, resultCode, data);
            if (result != null) {
                if (result.getContents() == null) {

                    Toast.makeText(this, "Result Not Found", Toast.LENGTH_LONG).show();
                } else {
                    try {

                        JSONObject obj = new JSONObject(result.getContents());

                        panel21edit.setText(obj.getString("name") + " " + obj.getString("address"));

                        pan21= String.valueOf( panel21edit.getText() );

                    } catch (JSONException e) {
                        e.printStackTrace();

                        if (result.getContents().length() < 19) {
                            Toast.makeText( this, "Length Below OR Above 19 or 20", Toast.LENGTH_LONG ).show();
                        } else {
                            panel21edit.setText( result.getContents().toString().trim() );
                            pan21 = String.valueOf( panel21edit.getText() );
                            Toast.makeText( this, result.getContents(), Toast.LENGTH_LONG ).show();
                        }
                    }


                }
            }

        }   else if(requestCode==TWENTYTWOREQUEST_CODE  ){

            IntentResult result = IntentIntegrator.parseActivityResult(qrScan_22.REQUEST_CODE, resultCode, data);
            if (result != null) {
                if (result.getContents() == null) {

                    Toast.makeText(this, "Result Not Found", Toast.LENGTH_LONG).show();
                } else {
                    try {

                        JSONObject obj = new JSONObject(result.getContents());

                        panel22edit.setText(obj.getString("name") + " " + obj.getString("address"));

                        pan22= String.valueOf( panel22edit.getText() );

                    } catch (JSONException e) {
                        e.printStackTrace();

                        if (result.getContents().length() < 19) {
                            Toast.makeText( this, "Length Below OR Above 19 or 20", Toast.LENGTH_LONG ).show();
                        } else {
                            panel22edit.setText( result.getContents().toString().trim() );
                            pan22 = String.valueOf( panel22edit.getText() );
                            Toast.makeText( this, result.getContents(), Toast.LENGTH_LONG ).show();
                        }
                    }


                }
            }

        }


        else if (requestCode == SEVENTEEN_REQUEST_CODE) {

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

        } else if (requestCode == TWENTY_ONE_REQUEST_CODE) {

            IntentResult result = IntentIntegrator.parseActivityResult(qrScan20.REQUEST_CODE, resultCode, data);
            if (result != null) {
                if (result.getContents() == null) {

                    Toast.makeText(this, "Result Not Found", Toast.LENGTH_LONG).show();
                } else {
                    try {


                        JSONObject obj = new JSONObject(result.getContents());
                        IMEIedit.setText(obj.getString("name") + " " + obj.getString("address"));


                    } catch (JSONException e) {
                        e.printStackTrace();


                        IMEIedit.setText(result.getContents().toString().trim());
                        Toast.makeText(this, result.getContents(), Toast.LENGTH_LONG).show();
                    }


                }
            }

        } else if (requestCode == TWENTY_TWO_REQUEST_CODE) {

            IntentResult result = IntentIntegrator.parseActivityResult(qrScan20.REQUEST_CODE, resultCode, data);
            if (result != null) {
                if (result.getContents() == null) {

                    Toast.makeText(this, "Result Not Found", Toast.LENGTH_LONG).show();
                } else {
                    try {


                        JSONObject obj = new JSONObject(result.getContents());
                        HomeLightpanelEdit.setText(obj.getString("name") + " " + obj.getString("address"));


                    } catch (JSONException e) {
                        e.printStackTrace();


                        HomeLightpanelEdit.setText(result.getContents().toString().trim());
                        Toast.makeText(this, result.getContents(), Toast.LENGTH_LONG).show();
                    }


                }
            }

        } else if (requestCode == TWENTY_THREE_REQUEST_CODE) {

            IntentResult result = IntentIntegrator.parseActivityResult(qrScan20.REQUEST_CODE, resultCode, data);
            if (result != null) {
                if (result.getContents() == null) {

                    Toast.makeText(this, "Result Not Found", Toast.LENGTH_LONG).show();
                } else {
                    try {


                        JSONObject obj = new JSONObject(result.getContents());
                        Homelightbatteryedit.setText(obj.getString("name") + " " + obj.getString("address"));


                    } catch (JSONException e) {
                        e.printStackTrace();


                        Homelightbatteryedit.setText(result.getContents().toString().trim());
                        Toast.makeText(this, result.getContents(), Toast.LENGTH_LONG).show();
                    }


                }
            }

        } else if (requestCode == TWENTY_FOUR_REQUEST_CODE) {

            IntentResult result = IntentIntegrator.parseActivityResult(qrScan20.REQUEST_CODE, resultCode, data);
            if (result != null) {
                if (result.getContents() == null) {

                    Toast.makeText(this, "Result Not Found", Toast.LENGTH_LONG).show();
                } else {
                    try {


                        JSONObject obj = new JSONObject(result.getContents());
                        cahrgecontrolleredit.setText(obj.getString("name") + " " + obj.getString("address"));


                    } catch (JSONException e) {
                        e.printStackTrace();


                        cahrgecontrolleredit.setText(result.getContents().toString().trim());

                        Toast.makeText(this, result.getContents(), Toast.LENGTH_LONG).show();
                    }


                }
            }

        } else if (requestCode == TWENTY_FIVE_REQUEST_CODE) {

            IntentResult result = IntentIntegrator.parseActivityResult(qrScan20.REQUEST_CODE, resultCode, data);
            if (result != null) {
                if (result.getContents() == null) {

                    Toast.makeText(this, "Result Not Found", Toast.LENGTH_LONG).show();
                } else {
                    try {


                        JSONObject obj = new JSONObject(result.getContents());
                        LuminaryLight1edit.setText(obj.getString("name") + " " + obj.getString("address"));


                    } catch (JSONException e) {
                        e.printStackTrace();


                        LuminaryLight1edit.setText(result.getContents().toString().trim());

                        Toast.makeText(this, result.getContents(), Toast.LENGTH_LONG).show();
                    }


                }
            }

        } else if (requestCode == TWENTY_SIX_REQUEST_CODE) {

            IntentResult result = IntentIntegrator.parseActivityResult(qrScan20.REQUEST_CODE, resultCode, data);
            if (result != null) {
                if (result.getContents() == null) {

                    Toast.makeText(this, "Result Not Found", Toast.LENGTH_LONG).show();
                } else {
                    try {


                        JSONObject obj = new JSONObject(result.getContents());
                        luminarylight2edit.setText(obj.getString("name") + " " + obj.getString("address"));

                    } catch (JSONException e) {
                        e.printStackTrace();

                        luminarylight2edit.setText(result.getContents().toString().trim());
                        Toast.makeText(this, result.getContents(), Toast.LENGTH_LONG).show();

                    }


                }
            }

        } else {

            super.onActivityResult(requestCode, resultCode, data);
        }


    }


    public void loadalreadydata() {

        StringRequest jsonObjectRequest = new StringRequest(Request.Method.POST, fetch_material_dispatch,
                new Response.Listener<String>() {


                    @Override
                    public void onResponse(String response) {
                        try {

                            JSONArray jsonArray = new JSONArray(response);
                            JSONObject jsonObject = jsonArray.getJSONObject(0);
                            String Sim = jsonObject.getString("sim_no");
                            String Mobile = jsonObject.getString("sim_mob_no");
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

                            controler_srno = jsonObject.getString("controler_srno");
                            controler_rms_id = jsonObject.getString("controler_rms_id");
                            pumpserialnew = jsonObject.getString("pumpserial");
                            motorserialnew = jsonObject.getString("motorserial");
                            rmuno = jsonObject.getString("controler_rms_id");
                            radio_pipe_string = jsonObject.getString("pipe_status");
                            radio_cable_string = jsonObject.getString("cable_status");
                            structure_checkbox_string = jsonObject.getString("structure_status");
                            accessories_checkbox_string = jsonObject.getString("accessories_status");
                            rod_checkbox_string = jsonObject.getString("rod");
                            farma_checkbox_string = jsonObject.getString("farma");


//                            hommelight_panel_string = jsonObject.getString("hl_panel_serial");
//                            homeLight_panel_battery_serial = jsonObject.getString("hl_battery_serial");
//                            home_light_charge_controller = jsonObject.getString("hl_charge_controller");
//                            home_luminary_serial1 = jsonObject.getString("hl_luminary_serial1");
//                            home_luminary_serial2 = jsonObject.getString("hl_luminary_serial2");
//                            imei_number_string = jsonObject.getString("imei_number_string");
                            panel1edit.setText(pannew1);
                            SimNumber.setText(Sim);
                            MobileNumber.setText(Mobile);
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
//                            Homelightbatteryedit.setText(homeLight_panel_battery_serial);
//                            HomeLightpanelEdit.setText(hommelight_panel_string);
//                            cahrgecontrolleredit.setText(home_light_charge_controller);
//                            LuminaryLight1edit.setText(home_luminary_serial1);
//                            luminarylight2edit.setText(home_luminary_serial2);
//                            IMEIedit.setText(imei_number_string);
//                            if(imei_number_string.length()>=19){
//                                IMEIedit.setEnabled(false);
//                                IMEIscan.setEnabled(false);
//                            }
//                            if (pannew1.length() == 19) {
//                                panel1edit.setEnabled(false);
//                                panel1btn.setEnabled(false);
//                            }
//                            if (pannew2.length() == 19) {
//                                panel2edit.setEnabled(false);
//                                panel2btn.setEnabled(false);
//                            }
//                            if (IMEIedit.getText().toString().length() == 19) {
//                                IMEIedit.setEnabled(false);
//                                IMEIscan.setEnabled(false);
//                            }
//                            if (Homelightbatteryedit.getText().toString().length() == 19) {
//                                Homelightbatteryedit.setEnabled(false);
//                            }


//                            if (HomeLightpanelEdit.getText().toString().length() == 19) {
//                                HomeLightpanelEdit.setEnabled(false);
//                            }
//                            if (cahrgecontrolleredit.getText().toString().length() == 19) {
//                                cahrgecontrolleredit.setEnabled(false);
//                            }
//                            if (LuminaryLight1edit.getText().toString().length() == 19) {
//                                LuminaryLight1edit.setEnabled(false);
//                            }
//                            if (luminarylight2edit.getText().toString().length() == 19) {
//                                luminarylight2edit.setEnabled(false);
//                            }
//                            if (IMEIedit.getText().toString().length() == 19) {
//                                IMEIedit.setEnabled(false);
//                            }

//                            if (pannew3.length() == 19) {
//                                panel3edit.setEnabled(false);
//                                panel3btn.setEnabled(false);
//                            }
//                            if (pannew4.length() == 19) {
//                                panel4edit.setEnabled(false);
//                                panel4btn.setEnabled(false);
//                            }
//                            if (pannew5.length() == 19) {
//                                panel5edit.setEnabled(false);
//                                panel5btn.setEnabled(false);
//                            }
//                            if (pannew6.length() == 19) {
//                                panel6edit.setEnabled(false);
//                                panel6btn.setEnabled(false);
//                            }
//                            if (pannew7.length() == 19) {
//                                panel7edit.setEnabled(false);
//                                panel7btn.setEnabled(false);
//                            }
//                            if (pannew8.length() == 19) {
//                                panel8edit.setEnabled(false);
//                                panel8btn.setEnabled(false);
//                            }
//                            if (pannew9.length() == 19) {
//                                panel9edit.setEnabled(false);
//                                panel9btn.setEnabled(false);
//                            }
//                            if (pannew10.length() == 19) {
//                                panel10edit.setEnabled(false);
//                                panel10btn.setEnabled(false);
//                            }


                            if (RmuNumber.getText().toString().equals("") || RmuNumber.getText().toString().equals("null")
                            ) {

                                RmuNumber.setText(rmuno);
                            }
                            if (controler_srno.equals("null") || controler_srno.equals("")) {
                                ControllerNumber.setText("null");
                            } else {
                                ControllerNumber.setText(controler_srno);
                            }
                            if (controler_rms_id.equals("null") || controler_rms_id.equals("")) {
                                RmuNumber.setText("null");
                            } else {

                                RmuNumber.setText(controler_rms_id);

                            }
                            if (pumpserialnew.equals("null") || pumpserialnew.equals("")) {
                                pumpSerail.setText("null");
                            } else {
                                pumpSerail.setText(pumpserialnew);

                            }
                            if (motorserialnew.equals("null") || motorserialnew.equals("")) {
                                MotorSerial.setText("null");
                            } else {
                                MotorSerial.setText(motorserialnew);
                            }
//                            if (pumpserialnew.length() > 5) {
//                                pumpSerail.setEnabled(false);
//                                btnPumpScan.setEnabled(false);
//
//                            }
//                            if (controler_rms_id.length() > 7) {
//                                RmuNumber.setEnabled(false);
//                                btnRmuNumber.setEnabled(false);
//
//                            }
//
//
//                            if (motorserialnew.length() > 5) {
//                                MotorSerial.setEnabled(false);
//                                btnMotorScan.setEnabled(false);
//
//                            }
//                            if (controler_srno.length() > 5) {
//                                ControllerNumber.setEnabled(false);
//                                ControllerbtnNumber.setEnabled(false);
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

                            e.printStackTrace();
                        }


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(Material_Dispatch_Rajasthan.this, "Error", Toast.LENGTH_SHORT).show();
                error.printStackTrace();

            }
        }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> paramsnew = new HashMap<>();


                paramsnew.put("reg_no", regnnumber);
                paramsnew.put("eng_id", eng_id);
                return paramsnew;
            }


        };
        MySingleton.getInstance(Material_Dispatch_Rajasthan.this).addTorequestque(jsonObjectRequest);

    }

    public void uploadData() {
        pb.show();
        if (accessories_checkbox.isChecked()) {
            accessories_checkbox_string = "Yes";
        } else {
            accessories_checkbox_string = "No";
        }
        if (farma_checkbox.isChecked()) {
            farma_checkbox_string = "Yes";
        } else {
            farma_checkbox_string = "No";
        }
        if (structure_checkbox.isChecked()) {
            structure_checkbox_string = "Yes";
        } else {
            structure_checkbox_string = "NO";
        }
        if (rod_checkbox.isChecked()) {
            rod_checkbox_string = "Yes";
        } else {
            rod_checkbox_string = "No";
        }


        if (panel1edit.getText().toString().equals("null") || panel1edit.getText().toString().equals("")) {
            pan1 = "null";

        } else {
            pan1 = panel1edit.getText().toString();


        }


        if (panel2edit.getText().toString().equals("null") || panel2edit.getText().toString().equals("")) {
            pan2 = "null";

        } else {

            pan2 = panel2edit.getText().toString();


        }


        if (panel3edit.getText().toString().equals("null") || panel3edit.getText().toString().equals("")) {
            pan3 = "null";

        } else {
            pan3 = panel3edit.getText().toString();


        }

        if (panel4edit.getText().toString().equals("null") || panel4edit.getText().toString().equals("")) {
            pan4 = "null";


        } else {
            pan4 = panel4edit.getText().toString();


        }

        if (panel5edit.getText().toString().equals("null") || panel5edit.getText().toString().equals("")) {
            pan5 = "null";

        } else {
            pan5 = panel5edit.getText().toString();


        }

        if (panel6edit.getText().toString().equals("null") || panel6edit.getText().toString().equals("")) {
            pan6 = "null";

        } else {
            pan6 = panel6edit.getText().toString();


        }

        if (panel7edit.getText().toString().equals("null") || panel7edit.getText().toString().equals("")) {
            pan7 = "null";

        } else {
            pan7 = panel7edit.getText().toString();


        }

        if (panel8edit.getText().toString().equals("null") || panel8edit.getText().toString().equals("")) {
            pan8 = "null";

        } else {
            pan8 = panel8edit.getText().toString();


        }
        if (panel9edit.getText().toString().equals("null") || panel9edit.getText().toString().equals("")) {
            pan9 = "null";

        } else {
            pan9 = panel9edit.getText().toString();


        }
        if (panel10edit.getText().toString().equals("null") || panel10edit.getText().toString().equals("")) {

            pan10 = "null";

        } else {
            pan10 = panel10edit.getText().toString();


        } if (panel11edit.getText().toString().equals("null") || panel11edit.getText().toString().equals("")) {

            pan11 = "null";

        } else {
            pan11 = panel11edit.getText().toString();


        } if (panel12edit.getText().toString().equals("null") || panel12edit.getText().toString().equals("")) {

            pan12 = "null";

        } else {
            pan12 = panel12edit.getText().toString();


        } if (panel13edit.getText().toString().equals("null") || panel13edit.getText().toString().equals("")) {

            pan13 = "null";

        } else {
            pan13 = panel13edit.getText().toString();


        } if (panel14edit.getText().toString().equals("null") || panel14edit.getText().toString().equals("")) {

            pan14 = "null";

        } else {
            pan14 = panel14edit.getText().toString();


        } if (panel15edit.getText().toString().equals("null") || panel15edit.getText().toString().equals("")) {

            pan15 = "null";

        } else {
            pan15 = panel15edit.getText().toString();


        } if (panel16edit.getText().toString().equals("null") || panel16edit.getText().toString().equals("")) {

            pan16 = "null";

        } else {
            pan16 = panel16edit.getText().toString();


        } if (panel17edit.getText().toString().equals("null") || panel17edit.getText().toString().equals("")) {

            pan17 = "null";

        } else {
            pan17 = panel17edit.getText().toString();


        } if (panel18edit.getText().toString().equals("null") || panel18edit.getText().toString().equals("")) {

            pan18 = "null";

        } else {
            pan18 = panel18edit.getText().toString();


        } if (panel19edit.getText().toString().equals("null") || panel19edit.getText().toString().equals("")) {

            pan19 = "null";

        } else {
            pan19 = panel19edit.getText().toString();


        } if (panel20edit.getText().toString().equals("null") || panel20edit.getText().toString().equals("")) {

            pan20 = "null";

        } else {
            pan20 = panel20edit.getText().toString();


        } if (panel21edit.getText().toString().equals("null") || panel21edit.getText().toString().equals("")) {

            pan21 = "null";

        } else {
            pan21 = panel21edit.getText().toString();


        } if (panel22edit.getText().toString().equals("null") || panel22edit.getText().toString().equals("")) {

            pan22 = "null";

        } else {
            pan22 = panel22edit.getText().toString();


        }
        StringRequest stringRequest = new StringRequest(Request.Method.POST, rmu_raj_mat_api, new Response.Listener<String>() {
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
                                Toast.makeText(Material_Dispatch_Rajasthan.this, "Saved", Toast.LENGTH_SHORT).show();
                                Intent i = new Intent(Material_Dispatch_Rajasthan.this, UploadAll.class);
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


                params.put("pan1", panel1edit.getText().toString());
                params.put("pan2", panel2edit.getText().toString());
                params.put("pan3", panel3edit.getText().toString());
                params.put("pan4", panel4edit.getText().toString());
                params.put("pan5", panel5edit.getText().toString());
                params.put("pan6", panel6edit.getText().toString());
                params.put("pan7", panel7edit.getText().toString());
                params.put("pan8", panel8edit.getText().toString());
                params.put("pan9", panel9edit.getText().toString());
                params.put("pan10", panel10edit.getText().toString());
               params.put("pan11", panel11edit.getText().toString());
               params.put("pan12", panel12edit.getText().toString());
               params.put("pan13", panel13edit.getText().toString());
               params.put("pan14", panel14edit.getText().toString());
               params.put("pan15", panel15edit.getText().toString());
               params.put("pan16", panel16edit.getText().toString());
               params.put("pan17", panel17edit.getText().toString());
               params.put("pan18", panel18edit.getText().toString());
               params.put("pan19", panel19edit.getText().toString());
               params.put("pan20", panel20edit.getText().toString());
               params.put("pan21", panel21edit.getText().toString());
               params.put("pan22", panel22edit.getText().toString());
//                params.put("imei_number_string", IMEIedit.getText().toString());
//                params.put("luminary_number1_string", LuminaryLight1edit.getText().toString());
//                params.put("luminary_number2_string", luminarylight2edit.getText().toString());
                params.put("PumpSerial", pumpSerail.getText().toString());
                params.put("motorSerial", MotorSerial.getText().toString());
                params.put("controllerNumber", ControllerNumber.getText().toString());
                params.put("rmuNumber", RmuNumber.getText().toString());
//                params.put("hl_panel_number_string", HomeLightpanelEdit.getText().toString());
//                params.put("hlbattery_number_string", Homelightbatteryedit.getText().toString());
//                params.put("charge_controller_number_string", cahrgecontrolleredit.getText().toString());
                params.put("radio_pipe", radio_pipe_string);
                params.put("radio_cable", radio_cable_string);
                params.put("structure_checkbox_string", structure_checkbox_string);
                params.put("accessories_checkbox_string", accessories_checkbox_string);
                params.put("rod_checkbox_string", rod_checkbox_string);
                params.put("farma_checkbox_string", farma_checkbox_string);
                params.put("reg_number", regnnumber);
                params.put("eng_id", eng_id);
//                params.put("sim_no", simno);
//                params.put("sim_mob_no", mobileno);
                return params;
            }
        };

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(10000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        stringRequest.setShouldCache(false);


        MySingleton.getInstance(getApplicationContext()).addTorequestque(stringRequest);
    }


}
