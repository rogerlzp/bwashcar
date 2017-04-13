package com.bwash.bwashcar.activities;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import com.bwash.bwashcar.R;
import com.bwash.bwashcar.net.ReqCallBack;
import com.bwash.bwashcar.net.WCOKHttpClient;

import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created by zhengpingli on 2017/3/30.
 */

public class TestActivity extends Activity {


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        String actionUrl = "http://121.42.145.216:8080/reserve/add";
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("clientType", "A");
        hashMap.put("sessionKey", "00e8e8e1f4814b0bc3472c249f75eb0f");
        hashMap.put("shopId", "100001");
        hashMap.put("totalAmount", "100");


        String reserveProduct =

                "[{\"serviceId\":\"10000\",\"serviceName\":\"afafa\", \"serviceAmount\":\"230\"}," +
                        "{\"serviceId\":\"10001\",\"serviceName\":\"afa\", \"serviceAmount\":\"30\"}]";

        hashMap.put("reserveProductList", reserveProduct);


        Log.d("TestActivity", reserveProduct);
        WCOKHttpClient.getOkHttpClient(this).requestAsyn(actionUrl, WCOKHttpClient.TYPE_POST_JSON, hashMap,
                new ReqCallBack<JSONObject>() {

                    @Override
                    public void onReqSuccess(JSONObject jsonObject) {
                        Log.d("TestActivity", jsonObject.toString());


                    }

                    @Override
                    public void onReqFailed(String errorMsg) {
                        Log.d("TestActivity", errorMsg);
                    }


                });
    }

}
