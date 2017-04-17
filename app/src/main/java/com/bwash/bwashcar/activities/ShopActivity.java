package com.bwash.bwashcar.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.bwash.bwashcar.R;
import com.bwash.bwashcar.adapter.ShopAdapter;
import com.bwash.bwashcar.application.LTNApplication;
import com.bwash.bwashcar.library.PullToRefreshBase;
import com.bwash.bwashcar.library.PullToRefreshListView;
import com.bwash.bwashcar.model.Shop;
import com.bwash.bwashcar.net.ReqCallBack;
import com.bwash.bwashcar.net.WCOKHttpClient;
import com.bwash.bwashcar.utility.LTNConstants;
import com.bwash.bwashcar.utility.LogUtils;
import com.bwash.bwashcar.utility.Utils;
import com.bwash.bwashcar.utility.ViewUtils;
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

public class ShopActivity extends BaseActivity implements PullToRefreshBase.OnRefreshListener2<ListView> {

    TextView tv_new_shop;

    PullToRefreshListView lvShop;
    public ShopAdapter mShopAdapter;
    private ArrayList<Shop> mShops = null;
    ProgressDialog mProgressdialog;
    private int page = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);
        initView();
        initData();
    }

    public void initView() {
        ((TextView) findViewById(R.id.title)).setText(getString(R.string.confirm_reserve));
        findViewById(R.id.tv_new_shop).setOnClickListener(this);
        lvShop = (PullToRefreshListView) findViewById(R.id.lv_shops);
    }

    public void initData() {
        mShopAdapter = new ShopAdapter(this);
        lvShop.setAdapter(mShopAdapter);
        lvShop.setOnRefreshListener(this);
        ListView listview = lvShop.getRefreshableView();
        listview.setDivider(ContextCompat.getDrawable(this, R.drawable.divide_normal));
        listview.setDividerHeight(ViewUtils.dip2px(this, 10));
        showDialog();
        onPullDownToRefresh(lvShop);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_new_shop: //创建新的店铺
                Intent intent = new Intent(ShopActivity.this, ShopAddActivity.class);
                startActivity(intent);
                break;

        }

    }

    public void showDialog() {
        if (mProgressdialog == null || !mProgressdialog.isShowing()) {
            mProgressdialog = new ProgressDialog(this);
            mProgressdialog.setMessage("正在加载数据");
            mProgressdialog.setIndeterminate(true);
            mProgressdialog.setCancelable(true);
            mProgressdialog.show();
        }
    }

    public void getShops(final boolean isPullDownToRefresh) {

        HashMap<String, String> mReqParams = new HashMap();
        mReqParams.put(LTNConstants.CLIENT_TYPE_PARAM, LTNConstants.CLIENT_TYPE_MOBILE);
        mReqParams.put(LTNConstants.CURRENT_PAGE, String.valueOf(page));
        mReqParams.put(LTNConstants.PAGE_SIZE, String.valueOf(LTNConstants.PAGE_COUNT));

        mReqParams.put(LTNConstants.SESSION_KEY, LTNApplication.getInstance().getSessionKey());

        // TODO: 上传location

        WCOKHttpClient.getOkHttpClient(this).requestAsyn(LTNConstants.ACCESS_URL.GET_SHOPS_URL,
                WCOKHttpClient.TYPE_GET, mReqParams,
                new ReqCallBack<JSONObject>() {

                    @Override
                    public void onReqSuccess(JSONObject jsonObject) {
                        try {
                            if (jsonObject.optInt(LTNConstants.RESULT_CODE) == 0) {
                                if (mShops == null) {
                                    mShops = new ArrayList<Shop>();
                                }

                                JSONObject dataObj = (JSONObject) jsonObject.get(LTNConstants.DATA);
                                JSONArray resultArray = (JSONArray) dataObj.get(LTNConstants.LIST);
                                ArrayList<Shop> shops = new Gson().fromJson(resultArray.toString(),
                                        new TypeToken<ArrayList<Shop>>() {
                                        }.getType());
                                if (shops != null) {
                                    if (isPullDownToRefresh) {
                                        mShops.clear();
                                    }
                                    mShops.addAll(shops);
                                }
                                mShopAdapter.setShops(mShops);
                            }
                        } catch (JSONException je) {
                            // Log.d(FRAGMENT_TAG, je.getMessage());
                        }
                        if (mProgressdialog.isShowing()) {
                            mProgressdialog.cancel();
                        }
                        lvShop.onRefreshComplete();
                    }

                    @Override
                    public void onReqFailed(String errorMsg) {
                        LogUtils.d(errorMsg);
                        if (mProgressdialog.isShowing()) {
                            mProgressdialog.cancel();
                        }
                        lvShop.onRefreshComplete();
                    }
                });

    }

    public void refreshCurrent(final boolean isPullDownToRefresh) {


        HashMap<String, String> mReqParams = new HashMap();
        mReqParams.put(LTNConstants.CLIENT_TYPE_PARAM, LTNConstants.CLIENT_TYPE_MOBILE);

        mReqParams.put(LTNConstants.CURRENT_PAGE, "0");
        mReqParams.put(LTNConstants.PAGE_SIZE, String.valueOf(LTNConstants.PAGE_COUNT + page * LTNConstants.PAGE_COUNT));

        mReqParams.put(LTNConstants.SESSION_KEY, LTNApplication.getInstance().getSessionKey());

        // TODO: 上传location

        WCOKHttpClient.getOkHttpClient().requestAsyn(LTNConstants.ACCESS_URL.GET_SHOPS_URL, WCOKHttpClient.TYPE_GET, mReqParams,
                new ReqCallBack<JSONObject>() {

                    @Override
                    public void onReqSuccess(JSONObject jsonObject) {
                        try {

                            if (jsonObject.optInt(LTNConstants.RESULT_CODE) == 0) {
                                if (mShops == null) {
                                    mShops = new ArrayList<Shop>();
                                }

                                JSONObject dataObj = (JSONObject) jsonObject.get(LTNConstants.DATA);
                                JSONArray resultArray = (JSONArray) dataObj.get(LTNConstants.LIST);
                                ArrayList<Shop> shops = new Gson().fromJson(resultArray.toString(),
                                        new TypeToken<ArrayList<Shop>>() {
                                        }.getType());
                                if (shops != null) {
                                    if (isPullDownToRefresh) {
                                        mShops.clear();
                                    }
                                    mShops.addAll(shops);
                                }
                                mShopAdapter.setShops(mShops);
                            }

                            if (mProgressdialog.isShowing()) {
                                mProgressdialog.cancel();
                            }
                            lvShop.onRefreshComplete();

                        } catch (JSONException je) {
                            // Log.d(FRAGMENT_TAG, je.getMessage());
                        }
                        if (mProgressdialog.isShowing()) {
                            mProgressdialog.cancel();
                        }
                        lvShop.onRefreshComplete();
                    }

                    @Override
                    public void onReqFailed(String errorMsg) {
                        if (mProgressdialog.isShowing()) {
                            mProgressdialog.cancel();
                        }
                        lvShop.onRefreshComplete();
                        Utils.isNetworkConnected(ShopActivity.this);
                    }
                });


    }

    @Override
    public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
        page = 0;
        getShops(true);
    }

    @Override
    public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
        page++;
        getShops(false);
    }

    @Override
    public void onResume() {
        super.onResume();
        refreshCurrent(true);
    }

}
