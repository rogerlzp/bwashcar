package com.bwash.bwashcar.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.bwash.bwashcar.R;
import com.bwash.bwashcar.application.LTNApplication;
import com.bwash.bwashcar.view.UISwitchButton;

/**
 * Created by jiajia on 2016/2/17.
 */
public class NotificationSettingActivity extends BaseActivity{
    private UISwitchButton receive_switch,sound_switch,vibrate_switch;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notification_setting);
        initView();
    }

    private void initView() {
        findViewById(R.id.back_btn).setOnClickListener(this);
        ((TextView)findViewById(R.id.title)).setText(R.string.notification_setting);
        receive_switch = (UISwitchButton) findViewById(R.id.receive_switch);
        sound_switch = (UISwitchButton) findViewById(R.id.sound_switch);
        vibrate_switch = (UISwitchButton) findViewById(R.id.vibrate_switch);

        receive_switch.setChecked(LTNApplication.getInstance().getNotification());
        int visible = LTNApplication.getInstance().getNotification() ? View.VISIBLE : View.GONE;
        findViewById(R.id.set_layout).setVisibility(visible);
        findViewById(R.id.sound_hint).setVisibility(visible);
        sound_switch.setChecked(LTNApplication.getInstance().getNotificationSound());
        vibrate_switch.setChecked(LTNApplication.getInstance().getNotificationVibrate());
        receive_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                LTNApplication.getInstance().setNotification(isChecked);
                int visible = LTNApplication.getInstance().getNotification() ? View.VISIBLE : View.GONE;
                findViewById(R.id.set_layout).setVisibility(visible);
                findViewById(R.id.sound_hint).setVisibility(visible);
            }
        });
        sound_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                LTNApplication.getInstance().setNotificationSound(isChecked);
            }
        });
        vibrate_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                LTNApplication.getInstance().setNotificationVibrate(isChecked);
            }
        });
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()){
            case R.id.back_btn:
                finish();
                break;
        }
    }
}
