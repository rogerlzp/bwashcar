package com.bwash.bwashcar.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bwash.bwashcar.R;
import com.bwash.bwashcar.application.LTNApplication;
import com.bwash.bwashcar.model.Car;
import com.bwash.bwashcar.net.ReqCallBack;
import com.bwash.bwashcar.net.WCOKHttpClient;
import com.bwash.bwashcar.utility.LTNConstants;
import com.bwash.bwashcar.view.MyEditText2;

import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created by zhengpingli on 2017/4/10.
 */

public class PersonAddActivity extends BaseActivity implements View.OnClickListener {


    RelativeLayout rl_emptycar;
    LinearLayout ll_car;
    Car car;
    Button btn_confirm;
    TextView title, tv_new_person;
    MyEditText2 ed_username, et_mobile_number, et_position;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_add);
        initView();
    }

    public void initView() {

        et_mobile_number = (MyEditText2) findViewById(R.id.et_mobile_number);
        et_position = (MyEditText2) findViewById(R.id.et_position);
        ed_username = (MyEditText2) findViewById(R.id.ed_username);


        btn_confirm = (Button) findViewById(R.id.btn_confirm);
        btn_confirm.setOnClickListener(this);

        title = (TextView) findViewById(R.id.title);
        title.setText("增加员工");

        initData();
    }

    public void initData() {


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_btn:
                finish();
                break;
            case R.id.btn_confirm:
                break;
        }
    }


    private void addUser(final String _mobielNo, String _username, String _roleName) {

        HashMap<String, String> mReqMap = new HashMap();
        mReqMap.put(LTNConstants.CLIENT_TYPE_PARAM, LTNConstants.CLIENT_TYPE_MOBILE);
        mReqMap.put(LTNConstants.SESSION_KEY, LTNApplication.getInstance().getSessionKey());
        mReqMap.put(LTNConstants.MOBILE_NO, _mobielNo);
        mReqMap.put(LTNConstants.ACCOUNT_USERNAME, _username);
        mReqMap.put(LTNConstants.ROLE_NAME, _roleName);


        showLoadingProgressDialog(PersonAddActivity.this, "正在提交中,请稍候");


        //TODO: enable it later
        //   LTNHttpClient.getLTNHttpClient().addHeader(LTNConstants.JPUSH_REG_ID, LTNApplication.getInstance().getJPushID());

        WCOKHttpClient.getOkHttpClient(this).requestAsyn(LTNConstants.ACCESS_URL.ADD_USER_URL, WCOKHttpClient.TYPE_GET, mReqMap,
                new ReqCallBack<JSONObject>() {

                    @Override
                    public void onReqSuccess(JSONObject jsonObject) {
                        dismissProgressDialog();
                        try {
                            String resultCode = jsonObject.getString(LTNConstants.RESULT_CODE);
                            // 验证码错误
                            if (resultCode.equals(LTNConstants.MSG_SUCCESS)) {

                            }
                        } catch (Exception e) {
                        }
                    }

                    @Override
                    public void onReqFailed(String errorMsg) {

                    }

                });
    }
}