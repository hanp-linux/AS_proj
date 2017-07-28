package com.example.hanp.jnidemo;

import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.os.ILedService;
import android.os.ServiceManager;

public class MainActivity extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener{
    private Button ledAll;
    private CheckBox led0, led1, led2, led3;
    private boolean led_status = true;
    private ILedService iLedService = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /* 获取服务 */
        iLedService = ILedService.Stub.asInterface(ServiceManager.getService("led"));

        ledAll = (Button) findViewById(R.id.bt_ledAll);
        led0 = (CheckBox) findViewById(R.id.cb_led0);
        led1 = (CheckBox) findViewById(R.id.cb_led1);
        led2 = (CheckBox) findViewById(R.id.cb_led2);
        led3 = (CheckBox) findViewById(R.id.cb_led3);

        led0.setOnCheckedChangeListener(this);
        led1.setOnCheckedChangeListener(this);
        led2.setOnCheckedChangeListener(this);
        led3.setOnCheckedChangeListener(this);

        ledAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (led_status) {
                    ledAll.setText("LED ON ALL");
                    led_status = !led_status;
                    try {
                        iLedService.ledCtrl(0, 1);
                        iLedService.ledCtrl(1, 1);
                        iLedService.ledCtrl(2, 1);
                        iLedService.ledCtrl(3, 1);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                } else {
                    ledAll.setText("LED OFF ALL");
                    led_status = !led_status;
                    try {
                        iLedService.ledCtrl(0, 0);
                        iLedService.ledCtrl(1, 0);
                        iLedService.ledCtrl(2, 0);
                        iLedService.ledCtrl(3, 0);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch (buttonView.getId()) {
            case R.id.cb_led0:
                if (isChecked) {
                    led0.setText("LED0 ON");
                    try {
                        iLedService.ledCtrl(0, 1);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                } else {
                    led0.setText("LED0 OFF");
                    try {
                        iLedService.ledCtrl(0, 0);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }
                break;
            case R.id.cb_led1:
                try {
                    if (isChecked) {
                        iLedService.ledCtrl(1, 1);
                        led1.setText("LED1 ON");
                    } else {
                        led1.setText("LED1 OFF");
                        iLedService.ledCtrl(1, 0);
                    }
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.cb_led2:
                try {
                    if (isChecked) {
                        led2.setText("LED2 ON");
                        iLedService.ledCtrl(2, 1);
                    } else {
                        led2.setText("LED2 OFF");
                        iLedService.ledCtrl(2, 0);
                    }
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.cb_led3:
                try {
                    if (isChecked) {
                        led3.setText("LED3 ON");
                        iLedService.ledCtrl(3, 1);
                    } else {
                        led3.setText("LED3 OFF");
                        iLedService.ledCtrl(3, 0);
                    }
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                break;
            default:
                break;
        }
    }
}
