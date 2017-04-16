package com.bwash.bwashcar.activities;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bwash.bwashcar.R;
import com.bwash.bwashcar.adapter.ReserveAdapter;
import com.bwash.bwashcar.application.LTNApplication;
import com.bwash.bwashcar.library.PullToRefreshBase;
import com.bwash.bwashcar.library.PullToRefreshListView;
import com.bwash.bwashcar.model.BReserve;
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
 * Created by zhengpingli on 2017/4/13.
 */

public class ConfirmReserveActivity extends BaseActivity implements PullToRefreshBase.OnRefreshListener2<ListView>, ReserveAdapter.ConfirmInterface {


    PullToRefreshListView lvBReserve;

    public ReserveAdapter mReserveAdapter;
    private ArrayList<BReserve> mBReserves = null;
    ProgressDialog mProgressdialog;
    private ProgressBar mProgressBar;
    private int page = 0;
    private boolean isViewShown;
    private boolean isPrepared;
    private boolean isCreate = true;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_reserve);
        initView();
        initData();
    }

    public void initView() {
        ((TextView) findViewById(R.id.title)).setText(getString(R.string.confirm_reserve));
        lvBReserve = (PullToRefreshListView) findViewById(R.id.lv_reserves);

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


    public void initData() {
        lvBReserve.setMode(PullToRefreshBase.Mode.BOTH);
        mReserveAdapter = new ReserveAdapter(this.getApplicationContext(), this);
        lvBReserve.setAdapter(mReserveAdapter);
        lvBReserve.setOnRefreshListener(this);
        ListView listview = lvBReserve.getRefreshableView();
        listview.setDivider(ContextCompat.getDrawable(this, R.drawable.divide_normal));
        listview.setDividerHeight(ViewUtils.dip2px(this, 10));
        showDialog();
        onPullDownToRefresh(lvBReserve);
    }

    @Override
    public void onClick(View v) {

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

    public void getShops(final boolean isPullDownToRefresh) {

        HashMap<String, String> mReqParams = new HashMap();
        mReqParams.put(LTNConstants.CLIENT_TYPE_PARAM, LTNConstants.CLIENT_TYPE_MOBILE);
        mReqParams.put(LTNConstants.CURRENT_PAGE, String.valueOf(page));
        mReqParams.put(LTNConstants.PAGE_SIZE, String.valueOf(LTNConstants.PAGE_COUNT));
        String sessionKey = LTNApplication.getInstance().getSessionKey();

        mReqParams.put(LTNConstants.STATUS, "0");
        mReqParams.put(LTNConstants.SHOP_ID, "100001");
        mReqParams.put(LTNConstants.SESSION_KEY, sessionKey);
        // TODO: 上传location

        WCOKHttpClient.getOkHttpClient(this).requestAsyn(LTNConstants.ACCESS_URL.GET_BRSERVE_URL,
                WCOKHttpClient.TYPE_GET, mReqParams,
                new ReqCallBack<JSONObject>() {

                    @Override
                    public void onReqSuccess(JSONObject jsonObject) {
                        try {
                            if (jsonObject.optInt(LTNConstants.RESULT_CODE) == 0) {
                                if (mBReserves == null) {
                                    mBReserves = new ArrayList<BReserve>();
                                }

                                JSONObject dataObj = (JSONObject) jsonObject.get(LTNConstants.DATA);
                                JSONArray resultArray = (JSONArray) dataObj.get(LTNConstants.LIST);
                                ArrayList<BReserve> shops = new Gson().fromJson(resultArray.toString(),
                                        new TypeToken<ArrayList<BReserve>>() {
                                        }.getType());
                                if (shops != null) {
                                    if (isPullDownToRefresh) {
                                        mBReserves.clear();
                                    }
                                    mBReserves.addAll(shops);
                                }
                                mReserveAdapter.setBReserves(mBReserves);
                            }
                        } catch (JSONException je) {
                            // Log.d(FRAGMENT_TAG, je.getMessage());
                        }
                        if (mProgressdialog.isShowing()) {
                            mProgressdialog.cancel();
                        }
                        lvBReserve.onRefreshComplete();
                        //   upDateRefreshTime();
                    }

                    @Override
                    public void onReqFailed(String errorMsg) {
                        LogUtils.d(errorMsg);
                        if (mProgressdialog.isShowing()) {
                            mProgressdialog.cancel();
                        }
                        lvBReserve.onRefreshComplete();
                    }
                });

    }

    public void refreshCurrent(final boolean isPullDownToRefresh) {


        HashMap<String, String> mReqParams = new HashMap();
        mReqParams.put(LTNConstants.CLIENT_TYPE_PARAM, LTNConstants.CLIENT_TYPE_MOBILE);

        mReqParams.put(LTNConstants.CURRENT_PAGE, "0");
        mReqParams.put(LTNConstants.SHOP_ID, "100001");
        mReqParams.put(LTNConstants.STATUS, "0");
        mReqParams.put(LTNConstants.PAGE_SIZE, String.valueOf(LTNConstants.PAGE_COUNT + page * LTNConstants.PAGE_COUNT));
        String sessionKey = LTNApplication.getInstance().getSessionKey();
        if (sessionKey != null) {
            mReqParams.put(LTNConstants.SESSION_KEY, sessionKey);
        }
        // TODO: 上传location

        WCOKHttpClient.getOkHttpClient().requestAsyn(LTNConstants.ACCESS_URL.GET_SHOPS_URL, WCOKHttpClient.TYPE_GET, mReqParams,
                new ReqCallBack<JSONObject>() {

                    @Override
                    public void onReqSuccess(JSONObject jsonObject) {
                        try {

                            if (jsonObject.optInt(LTNConstants.RESULT_CODE) == 0) {
                                if (mBReserves == null) {
                                    mBReserves = new ArrayList<BReserve>();
                                }

                                JSONObject dataObj = (JSONObject) jsonObject.get(LTNConstants.DATA);
                                JSONArray resultArray = (JSONArray) dataObj.get(LTNConstants.LIST);
                                ArrayList<BReserve> shops = new Gson().fromJson(resultArray.toString(),
                                        new TypeToken<ArrayList<BReserve>>() {
                                        }.getType());
                                if (shops != null) {
                                    if (isPullDownToRefresh) {
                                        mBReserves.clear();
                                    }
                                    mBReserves.addAll(shops);
                                }
                                mReserveAdapter.setBReserves(mBReserves);
                            }

                            if (mProgressdialog.isShowing()) {
                                mProgressdialog.cancel();
                            }
                            lvBReserve.onRefreshComplete();

                        } catch (JSONException je) {
                            // Log.d(FRAGMENT_TAG, je.getMessage());
                        }
                        if (mProgressdialog.isShowing()) {
                            mProgressdialog.cancel();
                        }
                        lvBReserve.onRefreshComplete();
                    }

                    @Override
                    public void onReqFailed(String errorMsg) {
                        if (mProgressdialog.isShowing()) {
                            mProgressdialog.cancel();
                        }
                        lvBReserve.onRefreshComplete();
                        Utils.isNetworkConnected(ConfirmReserveActivity.this);
                    }
                });


    }

    @Override
    public void onResume() {
        super.onResume();
        refreshCurrent(true);
    }

    @Override
    public void onConfirmClicked(BReserve bReserve) {
        HashMap<String, String> mReqParams = new HashMap();
        mReqParams.put(LTNConstants.CLIENT_TYPE_PARAM, LTNConstants.CLIENT_TYPE_MOBILE);
        mReqParams.put(LTNConstants.RESERVE_NO, bReserve.getReserveNo());

        mReqParams.put(LTNConstants.STATUS, "0");

        mReqParams.put(LTNConstants.UPDATED_STATUS, "1");

        String sessionKey = LTNApplication.getInstance().getSessionKey();
        if (sessionKey != null) {
            mReqParams.put(LTNConstants.SESSION_KEY, sessionKey);
        }

        WCOKHttpClient.getOkHttpClient().requestAsyn(LTNConstants.ACCESS_URL.CONFIRM_BRSERVE_URL,
                WCOKHttpClient.TYPE_GET, mReqParams,
                new ReqCallBack<JSONObject>() {
                    @Override
                    public void onReqSuccess(JSONObject jsonObject) {
                        if (jsonObject.optInt(LTNConstants.RESULT_CODE) == 0) {
                            Toast.makeText(ConfirmReserveActivity.this, "订单确认成功", Toast.LENGTH_SHORT).show();
                            //刷新页面
                            refreshCurrent(true);

                        }

                    }

                    @Override
                    public void onReqFailed(String errorMsg) {
                    }
                });


    }


}
