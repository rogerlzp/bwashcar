package com.bwash.bwashcar.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bwash.bwashcar.R;
import com.bwash.bwashcar.model.Car;
import com.bwash.bwashcar.utility.LTNConstants;

/**
 * Created by zhengpingli on 2017/4/10.
 */

public class PeopleManageActivity extends BaseActivity implements View.OnClickListener {


    RelativeLayout rl_emptycar;
    LinearLayout ll_car;
    Car car;
    Button btn_new_person;
    TextView title, tv_new_person;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_people_manage);
        initView();
    }

    public void initView() {

        rl_emptycar = (RelativeLayout) findViewById(R.id.rl_emptycar);
        ll_car = (LinearLayout) findViewById(R.id.ll_car);
        btn_new_person = (Button) findViewById(R.id.btn_new_person);
        btn_new_person.setOnClickListener(this);

        title = (TextView) findViewById(R.id.title);
        title.setText("员工管理");

        initData();
    }

    public void initData() {
        if (user.carList.size() == 0) {
            rl_emptycar.setVisibility(View.VISIBLE);
            ll_car.setVisibility(View.GONE);
        } else {
            ll_car.setVisibility(View.VISIBLE);
            rl_emptycar.setVisibility(View.GONE);

            car = user.getCarList().get(0);
            if (car != null) {

            }
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_btn:
                finish();
                break;
            case R.id.btn_add_car:
                Intent intent = new Intent(PeopleManageActivity.this, PersonAddActivity.class);
                startActivityForResult(intent, LTNConstants.ADD_CAR);
                startActivity(intent);
                break;
        }
    }


}