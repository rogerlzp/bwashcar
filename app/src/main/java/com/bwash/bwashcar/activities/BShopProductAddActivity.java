package com.bwash.bwashcar.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.bwash.bwashcar.R;
import com.bwash.bwashcar.application.LTNApplication;
import com.bwash.bwashcar.net.ReqCallBack;
import com.bwash.bwashcar.net.WCOKHttpClient;
import com.bwash.bwashcar.utility.LTNConstants;
import com.bwash.bwashcar.utility.LogUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created by zhengpingli on 2017/4/16.
 */

public class BShopProductAddActivity extends BaseActivity {

    EditText et_service_name, et_service_price, et_service_time;

    String serviceName, servicePrice, serviceTime, shopId;

    String serviceId = "10001"; //hardcode it now

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bshopproduct_add);
        initView();
        initData();
        shopId = getIntent().getExtras().getString(LTNConstants.SHOP_ID, "");
    }

    public void initView() {
        ((TextView) findViewById(R.id.title)).setText(getString(R.string.new_shop_product));
        et_service_name = (EditText) findViewById(R.id.et_service_name);
        et_service_price = (EditText) findViewById(R.id.et_service_price);
        et_service_time = (EditText) findViewById(R.id.et_service_time);
        findViewById(R.id.btn_submit).setOnClickListener(this);
    }

    public void initData() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_submit: //创建新的店铺
                addShopProduct();
                break;

        }
    }

    //TODO: validate
    public void validateInput() {

        serviceName = et_service_name.getText().toString().trim();
        serviceTime = et_service_time.getText().toString().trim();
        servicePrice = et_service_price.getText().toString().trim();
        //TODO: 修改serviceId获取方式

    }

    public void addShopProduct() {
        validateInput();

        //TODO：添加经纬度
        HashMap<String, String> mReqParams = new HashMap();
        mReqParams.put(LTNConstants.CLIENT_TYPE_PARAM, LTNConstants.CLIENT_TYPE_MOBILE);
        mReqParams.put(LTNConstants.SESSION_KEY, LTNApplication.getInstance().getSessionKey());

        mReqParams.put(LTNConstants.SERVICE_NAME, serviceName);
        mReqParams.put(LTNConstants.SERVICE_ID, serviceId);
        mReqParams.put(LTNConstants.SERVICE_PRICE, servicePrice);
        mReqParams.put(LTNConstants.SERVICE_TIME_CONSUME, serviceTime);

        mReqParams.put(LTNConstants.SHOP_ID, shopId);

        mReqParams.put(LTNConstants.SESSION_KEY, LTNApplication.getInstance().getSessionKey());

        // TODO: 上传location

        WCOKHttpClient.getOkHttpClient().requestAsyn(LTNConstants.ACCESS_URL.ADD_SHOPPRODUCT_URL, WCOKHttpClient.TYPE_GET, mReqParams,
                new ReqCallBack<JSONObject>() {

                    @Override
                    public void onReqSuccess(JSONObject jsonObject) {
                        try {

                            if (jsonObject.optInt(LTNConstants.RESULT_CODE) == 0) {
                                JSONObject dataObj = (JSONObject) jsonObject.get(LTNConstants.DATA);

                                Toast.makeText(BShopProductAddActivity.this, getResources().getString(R.string.success_new_shop_product),
                                        Toast.LENGTH_LONG).show();
                                setResult(LTNConstants.ADD_BSHOP_PRODUCT_SUCCESS);
                                finish();
                            }


                        } catch (JSONException je) {
                            // Log.d(FRAGMENT_TAG, je.getMessage());
                        }
                    }

                    @Override
                    public void onReqFailed(String errorMsg) {
                        LogUtils.e(errorMsg);
                    }
                });


    }


}
