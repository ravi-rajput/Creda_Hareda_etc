package update.gautamsolar.creda.BluetoothSPP;


import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.location.Location;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import androidx.core.app.ActivityCompat;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import update.gautamsolar.creda.CameraUtils;
import update.gautamsolar.creda.Constants.Constants;
import update.gautamsolar.creda.MySingleton;
import com.gautamsolar.creda.R;
import update.gautamsolar.creda.Database.RMU_Table;
import update.gautamsolar.creda.UploadAll;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import update.gautamsolar.creda.SiteSurvey;

import static android.provider.MediaStore.Files.FileColumns.MEDIA_TYPE_IMAGE;

public class Bluetoth_Update_Main extends AppCompatActivity {

    Button btnDis;
    EditText NewRmuNou;
    private static String imageStoragePathS;
    TextView lumn, TextSimno, Textdeviceid, Old_RMU, Block, BenificiaryName, FatherID, ContactId, VillageID;
    Constants constants;
    Dialog dialog, Localdialog;
    LinearLayout RmuImage;
    ProgressDialog pb;

    //SeekBar brightness;
    String photormu, Rmu_api, Engineer_Contact;
    //TextView msg1;
    TextView txtArduino, txtString, txtStringLength, sensorView0, sensorView3;
    String address = null, lat, lon;
    private ProgressDialog progress;
    static final int REQUEST_LOCATION = 60;

    public static final int BITMAP_SAMPLE_SIZE = 8;
    private static final int PICK_IMAGE_REQUEST_SITE1 = 164;
    BluetoothAdapter myBluetooth = null;
    LocationManager locationManager;
    boolean isGPSEnabled = false;
    Bitmap bitmap1 = null;
    public static final String myurlnew = "http://gautamsolar.co.in/gspl_mp/MpAndroidApi/bluetoothdatasend.php";
    //private BluetoothGatt mBluetoothGatt;
    BluetoothSocket btSocket = null;
    private Handler mHandler;
    private ConnectedThread mConnectedThread;
    private boolean isBtConnected = false;
    private final static int MESSAGE_READ = 2;
    /* private StringBuilder sb = new StringBuilder();*/
    Button send;
    ImageView RmuimageOneU;
    EditText location;
    SharedPreferences settings, settingss, sharedPreferences;
    SharedPreferences.Editor prefsEditor;
    //SPP UUID. Look for it
    String device_id, sim_no, output_amp, output_volt, power, dc_bus, run_hrs, alarm, frequency, Running_Status_date, time, json;
    //static final UUID myUUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
    static final UUID myUUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
    RecyclerView mRecyclerView;
    Search_Adapter search_adapter;
    List<Search_Model> list_models;
    Search_Model search_model;
    String rmu_api,eng_id,sregnnumber, sbenifname, sfname, scontact, svillage, sblock, spumpType, old_rmu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_ledcontrol);
        settings = PreferenceManager.getDefaultSharedPreferences(this);
        prefsEditor = settings.edit();
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        eng_id=sharedPreferences.getString("eng_id","");
        rmu_api=constants.RMU_API;
        Engineer_Contact = sharedPreferences.getString("engcontact", "");
        json = settings.getString("bluetooth_data", "");
        Toast.makeText(this, json, Toast.LENGTH_SHORT).show();
        list_models = new ArrayList<>();
        progress = new ProgressDialog(this);
        progress.setMessage("Please Wait");
        // restore the values from saved instance state
        Intent newint = getIntent();
        pb = new ProgressDialog(this, R.style.MyGravity);
        pb.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        pb.setIndeterminateDrawable(getResources().getDrawable(R.drawable.custom_progress_dialog));
        pb.setCancelable(false);
        FatherID = (TextView) findViewById(R.id.fatheridebu);
        BenificiaryName = (TextView) findViewById(R.id.benificiaryidesu);
        Block = (TextView) findViewById(R.id.blockidesu);

        VillageID = (TextView) findViewById(R.id.villageidebu);
        ContactId = (TextView) findViewById(R.id.contactidebu);
        address = newint.getStringExtra(DeviceList.EXTRA_ADDRESS); //receive the address of the bluetooth device
        settingss = PreferenceManager.getDefaultSharedPreferences(this);
        sregnnumber = settingss.getString("regnnumber", "");
        sbenifname = settingss.getString("benifname", "");
        sfname = settingss.getString("fname", "");
        scontact = settingss.getString("contact", "");
        svillage = settingss.getString("village", "");
        sblock = settingss.getString("block", "");
        spumpType = settingss.getString("pumpType", "");
        RmuImage = (LinearLayout) findViewById(R.id.rmu_image_clicku);
        FatherID.setText(String.valueOf(sfname));
        VillageID.setText(String.valueOf(svillage));
        ContactId.setText(String.valueOf(scontact));
        Block.setText(String.valueOf(sblock));
        BenificiaryName.setText(String.valueOf(sbenifname));
        Localdialog = new Dialog(Bluetoth_Update_Main.this); // Context, this, etc.
        dialog = new Dialog(Bluetoth_Update_Main.this); // Context, this, etc.
        dialog.setContentView(R.layout.uploaddialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setCancelable(false);


        Localdialog.setContentView(R.layout.localdialog);
        Localdialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        Localdialog.setCancelable(false);
        NewRmuNou = (EditText) findViewById(R.id.textrmunonewu);
        NewRmuNou.setFocusable(false);
        RmuimageOneU = (ImageView) findViewById(R.id.imagermu_oneu);
        new ConnectBT().execute(); //Call the class to connect
        sensorView0 = (TextView) findViewById(R.id.sensorView0u);
        location = findViewById(R.id.textu);
        send = findViewById(R.id.sendu);
        RmuImage.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {

                if (CameraUtils.checkPermissions(getApplicationContext())) {
                    captureImage1();
                } else {
                    requestCameraPermission1(SiteSurvey.MEDIA_TYPE_IMAGES);
                }

            }
        });

        mRecyclerView = findViewById(R.id.recycler_listu);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mRecyclerView.setNestedScrollingEnabled(true);

        Log.d(device_id + "bluetooth_data", json);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getLocation();

                ConnectivityManager cm = (ConnectivityManager) Bluetoth_Update_Main.this.getSystemService(Context.CONNECTIVITY_SERVICE);
                final NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
                if (NewRmuNou.getText().toString().equals(""))
                {
                    Toast.makeText(getApplicationContext(),"कृपया नया  RMU नंबर दर्ज करे ",Toast.LENGTH_LONG).show();

                }
                else
                {
                    if (activeNetwork!=null)
                    {
                        UploadRmu(Rmu_api);
                    }
                    else
                    {
                        save_local();

                    }


                }

            }
        });
        mHandler = new Handler() {
            public void handleMessage(Message msg) {
                if (msg.what == MESSAGE_READ) {
                    String readMessage = null;

                    try {
                        readMessage = new String((byte[]) msg.obj, "UTF-8");
//                        if(readMessage.contains("#")){
//
//                        }else if(readMessage.contains(";")){
//
                        Map counts = getCharFreq(readMessage);
                        int coun = (int) counts.get('$'); // => 3
                        //  Toast.makeText(Bluetoth_Update_Main.this, String.valueOf(coun), Toast.LENGTH_SHORT).show();
                        for (int i = 0; i < coun; i++) {
                            if (readMessage.contains("$")) {
                                String kept = readMessage.substring(0, readMessage.indexOf("$"));
                                if (i == 0) {
                                    device_id = kept;
                                } else if (i == 1) {
                                    sim_no = kept;
                                } else if (i == 2) {
                                    output_amp = kept;
                                } else if (i == 3) {
                                    output_volt = kept;
                                } else if (i == 4) {
                                    power = kept;
                                } else if (i == 5) {
                                    dc_bus = kept;
                                } else if (i == 6) {
                                    run_hrs = kept;
                                } else if (i == 7) {
                                    alarm = kept;
                                } else if (i == 8) {
                                    frequency = kept;
                                } else if (i == 9) {
                                    Running_Status_date = kept;
                                } else if (i == 10) {
                                    time = kept;
                                }
//
//                                        sensorView0.setText(all);
//
//

                            }
                            String remainder = readMessage.substring(readMessage.indexOf("$") + 1, readMessage.length());

                            readMessage = remainder;
//                            }
                        }
                        search_model = new Search_Model();

                        search_model.setDevice_id(device_id);
                        search_model.setSim_no(sim_no);
                        search_model.setOutput_amp(output_amp);
                        search_model.setOutput_volt(output_volt);
                        search_model.setPower(power);
                        search_model.setDc_bus(dc_bus);
                        search_model.setRun_hrs(run_hrs);
                        search_model.setAlarm(alarm);
                        search_model.setFrequency(frequency);
                        search_model.setRunning_Status_date(Running_Status_date);
                        search_model.setTime(time);
                        NewRmuNou.setText(device_id);
//
//                        list_models.add(search_model);
//                        search_adapter = new Search_Adapter(list_models, Bluetoth_Update_Main.this);
//                        mRecyclerView.setAdapter(search_adapter);
//                        savelocal(device_id);
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                        Toast.makeText(Bluetoth_Update_Main.this, "ERROR", Toast.LENGTH_SHORT).show();
                    }
                    //     msg1.setText(readMessage);

                }


            }

        };
//        ArrayAdapter adapter = new ArrayAdapter(this,android.R.layout.simple_list_item_2, list1);
//        msg1.setAdapter(adapter);




    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.blutoothdisconnect,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
       switch (item.getItemId())
       {
           case R.id.bluedibutton:

                       Disconnect(); //close connection
               break;

       };
        return super.onOptionsItemSelected(item);
    }


    private void Disconnect() {
        if (btSocket != null) //If the btSocket is busy
        {
            try {
                btSocket.close(); //close connection
            } catch (IOException e) {
                msg("Error");
            }
        }
        finish(); //return to the first layout

    }

    // fast way to call Toast
    private void msg(String s) {
        Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG).show();
    }

    private class ConnectBT extends AsyncTask<Void, Void, Void>  // UI thread
    {
        private boolean ConnectSuccess = true; //if it's here, it's almost connected

        @Override
        protected void onPreExecute() {
            progress.show();
        }

        @SuppressLint("WrongThread")
        @Override
        protected Void doInBackground(Void... devices) //while the progress dialog is shown, the connection is done in background
        {
            try {
                if (btSocket == null || !isBtConnected) {
                    myBluetooth = BluetoothAdapter.getDefaultAdapter();//get the mobile bluetooth device
                    BluetoothDevice dispositivo = myBluetooth.getRemoteDevice(address);//connects to the device's address and checks if it's available
                    btSocket = dispositivo.createRfcommSocketToServiceRecord(myUUID);//create a RFCOMM (SPP) connection
                    BluetoothAdapter.getDefaultAdapter().cancelDiscovery();
                    btSocket.connect();//start connection

                    send.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            try {
                                btSocket.getOutputStream().write(location.getText().toString().getBytes());

                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                        }
                    });
                }


            } catch (IOException e) {
                ConnectSuccess = false;
                Log.e("", "doInBackground: ", e);
                //if the try failed, you can check the exception here
            }


            return null;
        }

        @Override
        protected void onPostExecute(Void result) //after the doInBackground, it checks if everything went fine
        {
            super.onPostExecute(result);

            if (!ConnectSuccess) {
                msg("Connection Failed. Is it a SPP Bluetooth? Try again.");
                finish();
            } else {
                msg("Connected.");
                mConnectedThread = new ConnectedThread(btSocket);
                mConnectedThread.start();
                isBtConnected = true;
            }
            progress.dismiss();
        }
    }

    private class ConnectedThread extends Thread {
        private final BluetoothSocket mmSocket;
        private final InputStream mmInStream;
        /*  private final OutputStream mmOutStream;*/

        public ConnectedThread(BluetoothSocket socket) {
            mmSocket = socket;
            InputStream tmpIn = null;
            /* OutputStream tmpOut = null;*/

            // Get the input and output streams, using temp objects because
            // member streams are final
            try {
                tmpIn = socket.getInputStream();
                /* tmpOut = socket.getOutputStream();*/
            } catch (IOException e) {
            }

            mmInStream = tmpIn;
            /*   mmOutStream = tmpOut;*/
        }

        public void run() {
            byte[] buffer = new byte[1024];  // buffer store for the stream
            int bytes; // bytes returned from read()
            // Keep listening to the InputStream until an exception occurs
            while (true) {
                try {
                    // Read from the InputStream
                    bytes = mmInStream.available();
                    if (bytes != 0) {
                        buffer = new byte[1024];
                        SystemClock.sleep(100); //pause and wait for rest of data. Adjust this depending on your sending speed.
                        bytes = mmInStream.available(); // how many bytes are ready to be read?
                        //String readMessage = new String(buffer, 0, bytes);
                        bytes = mmInStream.read(buffer, 0, bytes); // record how many bytes we actually read
                        mHandler.obtainMessage(MESSAGE_READ, bytes, -1, buffer)
                                .sendToTarget(); // Send the obtained bytes to the UI activity
                    }
                } catch (IOException e) {
                    e.printStackTrace();

                    break;
                }
            }
        }

        //Call this from the main activity to send data to the remote device
      /*  public void write(String input) {
            byte[] bytes = input.getBytes();           //converts entered String into bytes
            try {
             *//*   mmOutStream.write(bytes);*//*
            } catch (IOException e) { }
        }*/

        // Call this from the main activity to shutdown the connection
        public void cancel() {
            try {
                mmSocket.close();
            } catch (IOException e) {
            }
        }
    }

    public static Map<Character, Integer> getCharFreq(String s) {
        Map<Character, Integer> charFreq = new HashMap<Character, Integer>();
        if (s != null) {
            for (Character c : s.toCharArray()) {
                Integer count = charFreq.get(c);
                int newCount = (count == null ? 1 : count + 1);
                charFreq.put(c, newCount);
            }
        }
        return charFreq;
    }

    public void UploadRmu(String Api_Rmu) {
pb.show();
        if (bitmap1 == null) {
            photormu = "noimage";

        } else {
            photormu = imageTOString(bitmap1);

        }

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Api_Rmu, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject obj = new JSONObject(response);
                   // Toast.makeText(getApplicationContext(), response, Toast.LENGTH_LONG).show();
                    String message = obj.getString("error");
                    if (message.equals("false")) {
                        pb.dismiss();
                        dialog.findViewById(R.id.clickmedismiss).setOnClickListener(new View.OnClickListener() {
                            @SuppressLint("ResourceAsColor")
                            public void onClick(View v) {
                                 Intent i = new Intent(Bluetoth_Update_Main.this, UploadAll.class);
                                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(i);
                                finish();
                                dialog.dismiss();
                                pb.dismiss();
                            }
                        });
                        dialog.show();

                    } else {
                        pb.dismiss();
                        save_local();
                        Toast.makeText(getApplicationContext(), "Upload failed", Toast.LENGTH_LONG).show();
                    }

                } catch (Exception e) {
                    save_local();
                    pb.dismiss();
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                pb.dismiss();
                save_local();

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("photo1",photormu);
                params.put("lat",lat);
                params.put("lon",lon);
                params.put("eng_id",eng_id);
                params.put("reg_no",sregnnumber);
                params.put("datetime",getDateTime());
                params.put("new_controler_rms_id",NewRmuNou.getText().toString());


                return params;
            }
        };

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(10000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        stringRequest.setShouldCache(false);
        MySingleton.getInstance(getApplicationContext()).addTorequestque(stringRequest);


    }

    private void requestCameraPermission1(final int type) {
        Dexter.withActivity(this)
                .withPermissions(Manifest.permission.CAMERA,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.RECORD_AUDIO)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        if (report.areAllPermissionsGranted()) {

                            if (type == MEDIA_TYPE_IMAGE) {
                                // capture picture
                                captureImage1();

                            } /*else {
                                captureVideo1();
                            }*/

                        } else if (report.isAnyPermissionPermanentlyDenied()) {
                            showPermissionsAlert();
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).check();
    }

    private void showPermissionsAlert() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Permissions required!")
                .setMessage("Camera needs few permissions to work properly. Grant them in settings.")
                .setPositiveButton("GOTO SETTINGS", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        CameraUtils.openSettings(Bluetoth_Update_Main.this);
                    }
                })
                .setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).show();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST_SITE1) {
            if (resultCode == RESULT_OK) {
                // Refreshing the gallery
                CameraUtils.refreshGallery(getApplicationContext(), imageStoragePathS);

                // successfully captured the image
                // display it in image view
                previewCapturedImage1();
            }


        }

    }

    private void previewCapturedImage1() {
        try {
            // hide video preview


            RmuimageOneU.setVisibility(View.VISIBLE);

            bitmap1 = CameraUtils.optimizeBitmap(BITMAP_SAMPLE_SIZE, imageStoragePathS);

            RmuimageOneU.setImageBitmap(bitmap1);

        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    public String imageTOString(Bitmap bitmap) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
        byte[] imageBytes = outputStream.toByteArray();
        String encodeImage1 = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodeImage1;

    }


    void getLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);


        } else {
            locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
            isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
            Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            if (location != null) {
                double latti = location.getLatitude();
                double longi = location.getLongitude();
                lat = String.valueOf(latti);
                lon = String.valueOf(longi);

/*
                ((EditText)findViewById(R.id.lati)).setText("Latitude:"+lat);
                ((EditText)findViewById(R.id.longi)).setText("Longitude:"+lon);*/


            } else {
                lat = "00.000000";
                lon = "00.000000";
                /*((EditText)findViewById(R.id.lati)).setText("Unable to find Latitude");
                ((EditText)findViewById(R.id.longi)).setText("Unable to fing Longitude");*/
            }
        }
    }

    private String getDateTime() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        String datewe = dateFormat.format(date);
        return datewe;


    }

    public void save_local() {

        try {
            pb.dismiss();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        if (bitmap1 == null) {
            photormu = "noimage";

        } else {
            photormu = imageTOString(bitmap1);

        }
        RMU_Table rmu_Table = new RMU_Table();
        rmu_Table.fotoRMu = String.valueOf(photormu);
        rmu_Table.NewRMuNO = String.valueOf(NewRmuNou.getText().toString());
        rmu_Table.regn_no_rmu = String.valueOf(sregnnumber);
        rmu_Table.latrmu = lat;
        rmu_Table.eng_id=eng_id;
        rmu_Table.lonrmu = lon;
        rmu_Table.datetime = getDateTime();
        rmu_Table.save();
        Localdialog.findViewById(R.id.clickmedismissf).setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            public void onClick(View v) {
                Toast.makeText(Bluetoth_Update_Main.this, "Saved", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(Bluetoth_Update_Main.this, UploadAll.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i);
                finish();
                Localdialog.dismiss();
            }
        });

        Localdialog.show();
        //  Toast.makeText(getApplicationContext(),"Saved inLocal",Toast.LENGTH_SHORT).show();

    }

    private void captureImage1() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        File file = CameraUtils.getOutputMediaFile(SiteSurvey.MEDIA_TYPE_IMAGES);
        if (file != null) {
            imageStoragePathS = file.getAbsolutePath();
        }

        Uri fileUri = CameraUtils.getOutputMediaFileUri(getApplicationContext(), file);

        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);

        // start the image capture Intent
        startActivityForResult(intent, PICK_IMAGE_REQUEST_SITE1);
    }
}
