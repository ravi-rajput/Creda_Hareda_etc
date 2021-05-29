package update.gautamsolar.creda.BluetoothSPP;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.gautamsolar.creda.R;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by clicksbazaar on 4/9/2018.
 */

public class Search_Adapter extends RecyclerView.Adapter<Search_Adapter.ViewHolder> {

    List<Search_Model> listItems,filterList;
    Context mContext;
    SharedPreferences settings;

    public Search_Adapter(List<Search_Model> listItems, Context context) {
        this.listItems = listItems;
        mContext = context;

        this.filterList = new ArrayList<Search_Model>();
        // we copy the original list to the filter list and use it for setting row values
        this.filterList.addAll(this.listItems);
        settings = PreferenceManager.getDefaultSharedPreferences(mContext);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.registration_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final Search_Model list_model = filterList.get(position);
        holder.device_id.setText("Device ID: "+list_model.getDevice_id());
        holder.sim_no.setText("Sim NO: "+list_model.getSim_no());
        holder.output_amp.setText("Output Amp: "+list_model.getOutput_amp());
        holder.output_volt.setText("Output Volt: "+list_model.getOutput_volt());
        holder.power.setText("Power: "+list_model.getPower());
        holder.dc_bus.setText("DC Bus: "+list_model.getDc_bus());
        holder.run_hrs.setText("Run Hrs: "+list_model.getRun_hrs());
        holder.alarm.setText("Alarm: "+list_model.getAlarm());
        holder.frequency.setText("Frequency: "+list_model.getFrequency());
        holder.Running_Status_date.setText("R_Status: "+list_model.getRunning_Status_date().substring(0, list_model.getRunning_Status_date().indexOf("*")));
        holder.date.setText("Date: "+list_model.getRunning_Status_date().substring(list_model.getRunning_Status_date().indexOf("*")+1, list_model.getRunning_Status_date().length()));
        holder.time.setText("Time: "+list_model.getTime());
    }

    @Override
    public int getItemCount() {
//        return listItems.size();
        return (null != filterList ? filterList.size() : 0);
//        return filterList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {


        TextView device_id,sim_no,output_amp,output_volt,power,dc_bus,run_hrs,alarm,frequency,Running_Status_date,date,time;
        RelativeLayout item;

        public ViewHolder(View itemView) {
            super(itemView);
            device_id = itemView.findViewById(R.id.device_id);
            sim_no = itemView.findViewById(R.id.sim_no);
            output_amp = itemView.findViewById(R.id.output_amp);
            output_volt = itemView.findViewById(R.id.output_volt);
            power = itemView.findViewById(R.id.power);
            dc_bus = itemView.findViewById(R.id.dc_bus);
            run_hrs = itemView.findViewById(R.id.run_hrs);
            alarm = itemView.findViewById(R.id.alarm);
            frequency = itemView.findViewById(R.id.frequency);
            Running_Status_date = itemView.findViewById(R.id.Running_Status);
            date=itemView.findViewById(R.id.date);
            time=itemView.findViewById(R.id.time);
            item = itemView.findViewById(R.id.item);


        }
    }
}
