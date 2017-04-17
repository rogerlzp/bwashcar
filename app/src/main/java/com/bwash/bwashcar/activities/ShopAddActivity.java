package com.bwash.bwashcar.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.bwash.bwashcar.R;
import com.bwash.bwashcar.application.LTNApplication;
import com.bwash.bwashcar.fragment.DepositDialogFragment;
import com.bwash.bwashcar.model.BReserve;
import com.bwash.bwashcar.model.User;
import com.bwash.bwashcar.net.ReqCallBack;
import com.bwash.bwashcar.net.WCOKHttpClient;
import com.bwash.bwashcar.utility.LTNConstants;
import com.bwash.bwashcar.utility.LogUtils;
import com.bwash.bwashcar.utility.StringUtils;
import com.bwash.bwashcar.utility.Utils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by zhengpingli on 2017/4/14.
 */

public class ShopAddActivity extends BaseActivity {

    EditText et_shop, et_address, et_worktime, et_washspace;

    Button btn_submit;

    String shopName, shopAddress, shopWorktime, shopWashspace, serviceName, servicePrice, serviceTime;

    String companyId, shopId;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_add);
        initView();
        initData();
        //TODO:使用一个真的值来代替
        //   companyId = getIntent().getExtras().getString(LTNConstants.COMPANY_ID, "");
    }

    public void initView() {
        ((TextView) findViewById(R.id.title)).setText(getString(R.string.new_shop));
        et_shop = (EditText) findViewById(R.id.et_shop);
        et_address = (EditText) findViewById(R.id.et_address);
        et_worktime = (EditText) findViewById(R.id.et_worktime);
        et_washspace = (EditText) findViewById(R.id.et_washspace);
        btn_submit = (Button) findViewById(R.id.btn_submit);
        btn_submit.setOnClickListener(this);
    }

    public void initData() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_submit: //创建新的店铺
                addShop();
                break;

        }
    }

    //TODO: validate
    public void validateInput() {
        shopName = et_shop.getText().toString().trim();
        shopAddress = et_address.getText().toString().trim();
        shopWorktime = et_worktime.getText().toString().trim();
        shopWashspace = et_washspace.getText().toString().trim();
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (resultCode) {
            case LTNConstants.ADD_BSHOP_PRODUCT_SUCCESS: // 从充值成功后跳转过来
                //结束当前页面
                finish();
                break;

        }

    }

    public void addShop() {
        validateInput();

        //TODO：添加经纬度
        HashMap<String, String> mReqParams = new HashMap();
        mReqParams.put(LTNConstants.CLIENT_TYPE_PARAM, LTNConstants.CLIENT_TYPE_MOBILE);
        mReqParams.put(LTNConstants.SESSION_KEY, LTNApplication.getInstance().getSessionKey());

        mReqParams.put(LTNConstants.SHOP_NAME, shopName);
        mReqParams.put(LTNConstants.SHOP_ADDRESS, shopAddress);
        mReqParams.put(LTNConstants.SHOP_WORK_TIME, shopWorktime);
        mReqParams.put(LTNConstants.SHOP_WASHSPACE, shopWashspace);


        mReqParams.put(LTNConstants.COMPANY_ID, "" + User.getUserInstance().getUserInfo().getCompanyId());


        mReqParams.put(LTNConstants.SESSION_KEY, LTNApplication.getInstance().getSessionKey());

        // TODO: 上传location

        WCOKHttpClient.getOkHttpClient().requestAsyn(LTNConstants.ACCESS_URL.ADD_SHOP_URL, WCOKHttpClient.TYPE_GET,
                mReqParams,
                new ReqCallBack<JSONObject>() {

                    @Override
                    public void onReqSuccess(JSONObject jsonObject) {
                        try {

                            if (jsonObject.optInt(LTNConstants.RESULT_CODE) == 0) {
                                JSONObject dataObj = (JSONObject) jsonObject.get(LTNConstants.DATA);

                                shopId = dataObj.getString(LTNConstants.SHOP_ID);

                                Intent intent = new Intent(ShopAddActivity.this, BShopProductAddActivity.class);
                                Bundle b = new Bundle();
                                if (!StringUtils.isNullOrEmpty(shopId)) {
                                    Toast.makeText(ShopAddActivity.this, getResources().getString(R.string.success_create_shop),
                                            Toast.LENGTH_LONG).show();
                                    b.putString(LTNConstants.SHOP_ID, shopId);
                                    intent.putExtras(b);
                                }
                                startActivityForResult(intent, 1001);

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