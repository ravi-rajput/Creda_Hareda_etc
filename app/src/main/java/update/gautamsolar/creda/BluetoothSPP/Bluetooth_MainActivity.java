package update.gautamsolar.creda.BluetoothSPP;


import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.preference.PreferenceManager;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.gautamsolar.creda.R;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class Bluetooth_MainActivity extends AppCompatActivity {

    Button btnDis;
    //SeekBar brightness;
    TextView lumn;
    //TextView msg1;
    TextView txtArduino, txtString, txtStringLength, sensorView0,  sensorView3 ;
    String address = null;
    private ProgressDialog progress;
    BluetoothAdapter myBluetooth = null;
    public static final String myurlnew = "http://gautamsolar.co.in/gspl_mp/MpAndroidApi/bluetoothdatasend.php";
    //private BluetoothGatt mBluetoothGatt;
    BluetoothSocket btSocket = null;
    private Handler mHandler;
    private ConnectedThread mConnectedThread;
    private boolean isBtConnected = false;
    private final static int MESSAGE_READ = 2;
    /* private StringBuilder sb = new StringBuilder();*/
    Button send;
    EditText location;
    SharedPreferences settings;
    SharedPreferences.Editor prefsEditor;
    //SPP UUID. Look for it
    String device_id,sim_no,output_amp,output_volt,power,dc_bus,run_hrs,alarm,frequency,Running_Status_date,time,json;
    //static final UUID myUUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
    static final UUID myUUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
    RecyclerView mRecyclerView;
    Search_Adapter search_adapter;
    List<Search_Model> list_models;
    Search_Model search_model;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_led_control);
        settings = PreferenceManager.getDefaultSharedPreferences(this);
        prefsEditor = settings.edit();
        json = settings.getString("bluetooth_data", "");
        Toast.makeText(this, json, Toast.LENGTH_SHORT).show();
        list_models = new ArrayList<>();
        progress=new ProgressDialog(this);
        progress.setMessage("Please Wait");
        // restore the values from saved instance state
        Intent newint = getIntent();
        address = newint.getStringExtra(DeviceList.EXTRA_ADDRESS); //receive the address of the bluetooth device
        new ConnectBT().execute(); //Call the class to connect
        btnDis = (Button)findViewById(R.id.button4);
        sensorView0 = (TextView) findViewById(R.id.sensorView0);
        location=findViewById(R.id.text);
        send=findViewById(R.id.send);
        btnDis.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Disconnect(); //close connection
            }
        });
        mRecyclerView = findViewById(R.id.recycler_list);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mRecyclerView.setNestedScrollingEnabled(true);

        Log.d(device_id+"bluetooth_data",json);

        mHandler = new Handler()  {
            public void handleMessage(Message msg){
                if(msg.what == MESSAGE_READ){
                    String readMessage = null ;

                    try {
                        readMessage = new String((byte[]) msg.obj, "UTF-8");
//                        if(readMessage.contains("#")){
//
//                        }else if(readMessage.contains(";")){
//
                        Map counts = getCharFreq(readMessage);
                        int coun= (int) counts.get('$'); // => 3
                        Toast.makeText(Bluetooth_MainActivity.this, String.valueOf(coun), Toast.LENGTH_SHORT).show();
                        for(int i=0;i<coun;i++){
                            if(readMessage.contains("$") ){
                                String kept = readMessage.substring(0, readMessage.indexOf("$"));
                                if(i==0){
                                    device_id=kept;
                                }
                                else if(i==1) {
                                    sim_no=kept;
                                }
                                else if(i==2) {
                                    output_amp=kept;
                                }    else if(i==3){
                                    output_volt=kept;
                                } else if(i==4){
                                    power=kept;
                                }else if(i==5){
                                    dc_bus=kept;
                                }else if(i==6){
                                    run_hrs=kept;
                                }else if(i==7){
                                    alarm=kept;
                                }else if(i==8){
                                    frequency=kept;
                                }else if(i==9){
                                    Running_Status_date=kept;
                                }else if(i==10){
                                    time=kept;
                                }
//
//                                        sensorView0.setText(all);
//
//

                            }
                            String remainder = readMessage.substring(readMessage.indexOf("$")+1, readMessage.length());

                            readMessage=remainder;
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

                        list_models.add(search_model);
                        search_adapter = new Search_Adapter(list_models, Bluetooth_MainActivity.this);
                        mRecyclerView.setAdapter(search_adapter);
//                        savelocal(device_id);
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                        Toast.makeText(Bluetooth_MainActivity.this, "ERROR", Toast.LENGTH_SHORT).show();
                    }
                    //     msg1.setText(readMessage);

                }



            }

        };
//        ArrayAdapter adapter = new ArrayAdapter(this,android.R.layout.simple_list_item_2, list1);
//        msg1.setAdapter(adapter);




    }


    private void Disconnect()
    {
        if (btSocket!=null) //If the btSocket is busy
        {
            try
            {
                btSocket.close(); //close connection
            }
            catch (IOException e)
            { msg("Error");}
        }
        finish(); //return to the first layout

    }

    // fast way to call Toast
    private void msg(String s)
    {
        Toast.makeText(getApplicationContext(),s,Toast.LENGTH_LONG).show();
    }

    private class ConnectBT extends AsyncTask<Void, Void, Void>  // UI thread
    {
        private boolean ConnectSuccess = true; //if it's here, it's almost connected

        @Override
        protected void onPreExecute()
        {
            progress.show();
        }

        @SuppressLint("WrongThread")
        @Override
        protected Void doInBackground(Void... devices) //while the progress dialog is shown, the connection is done in background
        {
            try
            {
                if (btSocket == null || !isBtConnected)
                {
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


            }
            catch (IOException e)
            {
                ConnectSuccess = false;
                Log.e("", "doInBackground: ",e);
                //if the try failed, you can check the exception here
            }




            return null;
        }
        @Override
        protected void onPostExecute(Void result) //after the doInBackground, it checks if everything went fine
        {
            super.onPostExecute(result);

            if (!ConnectSuccess)
            {
                msg("Connection Failed. Is it a SPP Bluetooth? Try again.");
                finish();
            }
            else
            {
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
            } catch (IOException e) { }

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
                    if(bytes != 0) {
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
            } catch (IOException e) { }
        }
    }

    public static Map<Character,Integer> getCharFreq(String s) {
        Map<Character,Integer> charFreq = new HashMap<Character,Integer>();
        if (s != null) {
            for (Character c : s.toCharArray()) {
                Integer count = charFreq.get(c);
                int newCount = (count==null ? 1 : count+1);
                charFreq.put(c, newCount);
            }
        }
        return charFreq;
    }
}
