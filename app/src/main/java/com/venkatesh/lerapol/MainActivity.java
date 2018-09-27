package com.venkatesh.lerapol;

import android.bluetooth.BluetoothAdapter;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioManager;
import android.net.wifi.WifiManager;
import android.os.BatteryManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

        public SeekBar Ringervolume;
        AudioManager audioManager;
        final BluetoothAdapter  bAdapter=BluetoothAdapter.getDefaultAdapter();
    TextView Batterymode,Batterylevel;
    String Batterystatus="";

    IntentFilter intentFilter;
    int deviceStatus;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        intentFilter=new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        Batterymode=findViewById(R.id.Batterystate);
        Batterylevel=findViewById(R.id.BatteryPercentage);
        volumecontrol();
        MainActivity.this.registerReceiver(broadcastReceiver,intentFilter);

    }

    public void WifiOn(View view) {
        WifiManager wifi=(WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        if (wifi != null) {
            wifi.setWifiEnabled(true);
        }
    }

    public void wifiOff(View view) {
        WifiManager wifi=(WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);

        if (wifi != null) {
            wifi.setWifiEnabled(false);
        }
    }

    public void searchwifi(View view) {
        startActivity(new Intent(getApplicationContext(),WifiActivity.class));
    }

    public void volumecontrol()
    {
        try {
            Ringervolume=(SeekBar)findViewById(R.id.changeRinger);
            audioManager=(AudioManager) getSystemService(Context.AUDIO_SERVICE);
            Ringervolume.setMax(audioManager.getStreamMaxVolume(AudioManager.STREAM_RING));
            Ringervolume.setProgress(audioManager.getStreamVolume(AudioManager.STREAM_RING));
            Ringervolume.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    audioManager.setStreamVolume(AudioManager.STREAM_RING,progress,0);

                }
                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {

                }
                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {

                }
            });
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public void SceduleActivity(View view) {
        startActivity(new Intent(getApplicationContext(),Sceduletask.class));
    }

    public void SerchNearBlueTooth(View view) {

    }

    public void OfBluetooth(View view) {
        bAdapter.disable();
        Toast.makeText(MainActivity.this, "BlueTooth Turned OF", Toast.LENGTH_SHORT).show();

    }

    public void OnBluetoooth(View view) {
        if(bAdapter==null)
        {
            Toast.makeText(MainActivity.this,"BlueTooth Not supported" , Toast.LENGTH_SHORT).show();}
                else
            {
                if(!bAdapter.isEnabled())
                {
                    startActivityForResult(new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE),1);
                    if(bAdapter.isEnabled())
                        Toast.makeText(MainActivity.this, "BlueTooth TUrned On", Toast.LENGTH_SHORT).show();

                }
            }


    }

    private BroadcastReceiver broadcastReceiver=new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            deviceStatus = intent.getIntExtra(BatteryManager.EXTRA_STATUS,-1);
            int level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
            int scale = intent.getIntExtra(BatteryManager.EXTRA_SCALE, -1);
            int batteryLevel=(int)(((float)level / (float)scale) * 100.0f);
            if(deviceStatus == BatteryManager.BATTERY_STATUS_CHARGING){
                Batterystatus="CHARGING";
                Batterymode.setText(Batterystatus.toString());
                Batterylevel.setText(String.valueOf(batteryLevel));

            }

            if(deviceStatus == BatteryManager.BATTERY_STATUS_DISCHARGING){

                Batterystatus="DISCHARGING";
                Batterymode.setText(Batterystatus.toString());
                Batterylevel.setText(String.valueOf(batteryLevel)+"%");

            }

            if (deviceStatus == BatteryManager.BATTERY_STATUS_FULL){

                Batterystatus="BATTERY FULL";
                Batterymode.setText(Batterystatus.toString());
                Batterylevel.setText(String.valueOf(batteryLevel)+"%");

            }

            if(deviceStatus == BatteryManager.BATTERY_STATUS_UNKNOWN){

                Batterystatus="---";
                Batterymode.setText(Batterystatus.toString());
                Batterylevel.setText(String.valueOf(batteryLevel)+"%");
            }


            if (deviceStatus == BatteryManager.BATTERY_STATUS_NOT_CHARGING){

                Batterystatus="NOT CHARGING";
                Batterymode.setText(Batterystatus.toString());
                Batterylevel.setText(String.valueOf(batteryLevel)+"%");
            }

        }
    };
}
