package update.gautamsolar.creda.BluetoothSPP;

/**
 * Created by clicksbazaar on 4/9/2018.
 */

public class Search_Model {
    String Device_id, sim_no,output_amp
            ,output_volt,power,dc_bus,run_hrs,alarm,frequency,Running_Status_date,time;

    public String getDevice_id() {
        return Device_id;
    }

    public void setDevice_id(String device_id) {
        Device_id = device_id;
    }

    public String getOutput_amp() {
        return output_amp;
    }

    public void setOutput_amp(String output_amp) {
        this.output_amp = output_amp;
    }

    public String getOutput_volt() {
        return output_volt;
    }

    public void setOutput_volt(String output_volt) {
        this.output_volt = output_volt;
    }

    public String getPower() {
        return power;
    }

    public void setPower(String power) {
        this.power = power;
    }

    public String getDc_bus() {
        return dc_bus;
    }

    public void setDc_bus(String dc_bus) {
        this.dc_bus = dc_bus;
    }

    public String getRun_hrs() {
        return run_hrs;
    }

    public void setRun_hrs(String run_hrs) {
        this.run_hrs = run_hrs;
    }

    public String getAlarm() {
        return alarm;
    }

    public void setAlarm(String alarm) {
        this.alarm = alarm;
    }

    public String getFrequency() {
        return frequency;
    }

    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }

    public String getSim_no() {
        return sim_no;
    }

    public void setSim_no(String sim_no) {
        this.sim_no = sim_no;
    }

    public String getRunning_Status_date() {
        return Running_Status_date;
    }

    public void setRunning_Status_date(String running_Status_date) {
        Running_Status_date = running_Status_date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
