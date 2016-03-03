package com.tym.arvin.hookwechatsensor;

import android.app.Service;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;

public class Settings extends AppCompatActivity {

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        final SettingsHelper settingsHelper = new SettingsHelper(this, "com.tym.arvin.hookwechatsensor");
        final Vibrator vibrator = (Vibrator)getSystemService(Service.VIBRATOR_SERVICE);

        final TextView number = (TextView)findViewById(R.id.textView);
        final SeekBar seekBar = (SeekBar)findViewById(R.id.seekBar);
        final Switch switcher = (Switch)findViewById(R.id.switcher);

        number.setText(String.valueOf(settingsHelper.getInt("count", 10) * 100));
        seekBar.setProgress(settingsHelper.getInt("count", 10));
        switcher.setChecked(settingsHelper.getBoolean("check", true));

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                int setProgress = progress / 5 * 5;
                number.setText(String.valueOf(setProgress * 100));
                seekBar.setProgress(setProgress);
                settingsHelper.setInt("count", setProgress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
//                vibrator.vibrate(200);
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        switcher.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                vibrator.vibrate(100);
                if (isChecked) {
                    settingsHelper.setBoolean("check", true);
                } else
                    settingsHelper.setBoolean("check", false);
            }
        });

    }
}
