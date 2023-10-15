package update.gautamsolar.creda;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import update.gautamsolar.creda.BluetoothSPP.DeviceList;
import update.gautamsolar.creda.BluetoothSPP.Device_Update_list;

import update.gautamsolar.creda.R;

public class RMU_Update extends AppCompatActivity {
    LinearLayout CheckRmuData,Updatermudata;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rmu__update);
        Updatermudata=(LinearLayout)findViewById(R.id.updatermudata);
        CheckRmuData=(LinearLayout)findViewById(R.id.checkrmudata);
        Updatermudata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent checkintent= new Intent(RMU_Update.this, Device_Update_list.class);
                startActivity(checkintent);

            }
        });

        CheckRmuData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent checkintent= new Intent(RMU_Update.this, DeviceList.class);
                startActivity(checkintent);

            }
        });

    }
}
