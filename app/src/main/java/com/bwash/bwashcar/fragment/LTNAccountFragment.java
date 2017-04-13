package com.bwash.bwashcar.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.bwash.bwashcar.R;
import com.bwash.bwashcar.activities.AccountInfoActivity;
import com.bwash.bwashcar.activities.BalanceDetailActivity;
import com.bwash.bwashcar.activities.BirdDetailActivity;
import com.bwash.bwashcar.activities.CarActivity;
import com.bwash.bwashcar.activities.CouponsActivity;
import com.bwash.bwashcar.activities.MyInvestActivity;
import com.bwash.bwashcar.activities.PartnerActivity;
import com.bwash.bwashcar.activities.TotalAssetsActivity;
import com.bwash.bwashcar.application.LTNApplication;
import com.bwash.bwashcar.model.Car;
import com.bwash.bwashcar.model.User;
import com.bwash.bwashcar.net.ReqCallBack;
import com.bwash.bwashcar.net.WCOKHttpClient;
import com.bwash.bwashcar.utility.LTNConstants;
import com.bwash.bwashcar.utility.Utils;
import com.bwash.bwashcar.utility.ViewUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;


/**
 * 我的账户
 */
public class LTNAccountFragment extends BaseFragment implements View.OnClickListener {

    public static String TAG = LTNAccountFragment.class.getSimpleName();
    private View rootView;

    private boolean isPrepared, isShowMoeny = true;
    private boolean isVisible;
    private TextView tv_daishou_shouyi_value, tv_daishou_birdcoin_value;
    private TextView welcomeId;
    private TextView tv_userbalance_value;
    private TextView tv_birdcoin;
    private TextView tv_total_asset;
    private boolean isViewShown;
    private Button btnDeposit, btnWithdraw;
    private RelativeLayout invest_current_layout = null, anxin_layout = null;

    //添加车辆
    private TextView tv_name, tv_car;
    private ImageView iv_profile;


    private ImageView ivShowMoney;

    private ImageView iv_redtip;

    User.Account account;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.tab_account, null);
        isPrepared = true;
        initView();
        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (Utils.isNetworkConnected(getActivity())) {
            if (LTNApplication.getInstance().getSessionKey() != null) {
                getAccountInfo();
                getUserInfo();
                getCarInfo();
                getReserveInfo();
            }
        }
    }

    public void initView() {
        tv_userbalance_value = (TextView) rootView.findViewById(R.id.tv_userbalance_value);
        tv_birdcoin = (TextView) rootView.findViewById(R.id.tv_birdcoin);
        tv_total_asset = (TextView) rootView.findViewById(R.id.tv_total_asset);
        rootView.findViewById(R.id.my_account).setOnClickListener(this);
        rootView.findViewById(R.id.tv_anxin).setOnClickListener(this);
        rootView.findViewById(R.id.balance_layout).setOnClickListener(this);
        rootView.findViewById(R.id.all_account).setOnClickListener(this);
        rootView.findViewById(R.id.coupon_layout).setOnClickListener(this);
        rootView.findViewById(R.id.partner).setOnClickListener(this);
        rootView.findViewById(R.id.birdcoin_help).setOnClickListener(this);
        rootView.findViewById(R.id.birdcoin_layout).setOnClickListener(this);
        tv_car = (TextView) rootView.findViewById(R.id.tv_car);
        tv_name = (TextView) rootView.findViewById(R.id.tv_name);
        iv_profile = (ImageView) rootView.findViewById(R.id.iv_profile);

        tv_car.setOnClickListener(this);
        tv_name.setOnClickListener(this);
        iv_profile.setOnClickListener(this);


    }


    public void getCarInfo() {
        HashMap<String, String> mReqParams = new HashMap();
        mReqParams.put(LTNConstants.CLIENT_TYPE_PARAM, LTNConstants.CLIENT_TYPE_MOBILE);
        mReqParams.put(LTNConstants.SESSION_KEY, LTNApplication.getInstance().getSessionKey());
        mReqParams.put(LTNConstants.PAGE_SIZE, "10");
        mReqParams.put(LTNConstants.CURRENT_PAGE, "0");

        WCOKHttpClient.getOkHttpClient(getContext()).requestAsyn(LTNConstants.ACCESS_URL.CAR_LIST_URL,
                WCOKHttpClient.TYPE_GET, mReqParams,
                new ReqCallBack<JSONObject>() {

                    @Override
                    public void onReqSuccess(JSONObject jsonObject) {
                        try {
                            String resultCode = jsonObject.getString(LTNConstants.RESULT_CODE);

                            if (resultCode.equals(LTNConstants.MSG_SUCCESS)) {
                                // 将用户信息存储到
                                Gson gson = new Gson();
                                JSONObject resultObj = (JSONObject) jsonObject.get(LTNConstants.DATA);

                                JSONObject carObj = resultObj.getJSONObject(LTNConstants.CAR);
                                Car car = gson.fromJson(carObj.toString(), Car.class);
                                if (car != null) {
                                    LTNApplication.getInstance().getCurrentUser().getCarList().add(car);
                                    tv_car.setText(car.getCarBrandtype());
                                }

                            } else {
                                String resultMsg = jsonObject.getString(LTNConstants.RESULT_MESSAGE);

                            }
                        } catch (JSONException je) {
                        }
                    }

                    @Override
                    public void onReqFailed(String errorMsg) {


                    }
                });

    }

    public void getReserveInfo() {
        HashMap<String, String> mReqParams = new HashMap();
        mReqParams.put(LTNConstants.CLIENT_TYPE_PARAM, LTNConstants.CLIENT_TYPE_MOBILE);
        mReqParams.put(LTNConstants.SESSION_KEY, LTNApplication.getInstance().getSessionKey());
        mReqParams.put(LTNConstants.PAGE_SIZE, "10");
        mReqParams.put(LTNConstants.CURRENT_PAGE, "0");

        WCOKHttpClient.getOkHttpClient(getContext()).requestAsyn(LTNConstants.ACCESS_URL.RESERVE_LIST_URL,
                WCOKHttpClient.TYPE_GET, mReqParams,
                new ReqCallBack<JSONObject>() {

                    @Override
                    public void onReqSuccess(JSONObject jsonObject) {
                        try {
                            String resultCode = jsonObject.getString(LTNConstants.RESULT_CODE);

                            if (resultCode.equals(LTNConstants.MSG_SUCCESS)) {
                                // 将用户信息存储到
                                Gson gson = new Gson();
                                JSONObject resultObj = (JSONObject) jsonObject.get(LTNConstants.DATA);

                                JSONObject carObj = resultObj.getJSONObject(LTNConstants.CAR);
                                Car car = gson.fromJson(carObj.toString(), Car.class);
                                if (car != null) {
                                    LTNApplication.getInstance().getCurrentUser().getCarList().add(car);
                                    tv_car.setText(car.getCarBrandtype());
                                }

                            } else {
                                String resultMsg = jsonObject.getString(LTNConstants.RESULT_MESSAGE);

                            }
                        } catch (JSONException je) {
                        }
                    }

                    @Override
                    public void onReqFailed(String errorMsg) {


                    }
                });

    }

    public void getUserInfo() {

        HashMap<String, String> mReqParams = new HashMap();
        mReqParams.put(LTNConstants.CLIENT_TYPE_PARAM, LTNConstants.CLIENT_TYPE_MOBILE);
        mReqParams.put(LTNConstants.SESSION_KEY, LTNApplication.getInstance().getSessionKey());

        WCOKHttpClient.getOkHttpClient(getContext()).requestAsyn(LTNConstants.ACCESS_URL.MY_USER_INFO_URL,
                WCOKHttpClient.TYPE_GET, mReqParams,
                new ReqCallBack<JSONObject>() {

                    @Override
                    public void onReqSuccess(JSONObject jsonObject) {
                        try {
                            String resultCode = jsonObject.getString(LTNConstants.RESULT_CODE);


                            if (resultCode.equals(LTNConstants.MSG_SUCCESS)) {
                                // 将用户信息存储到
                                Gson gson = new Gson();
                                JSONObject accountInfoObj = (JSONObject) jsonObject.get(LTNConstants.DATA);
                                LTNApplication.getInstance().getCurrentUser().setUserInfo(gson.fromJson(accountInfoObj.toString(), User.UserInfo.class));
                                User.UserInfo userInfo = LTNApplication.getInstance().getCurrentUser().getUserInfo();
                            } else {
                                String resultMsg = jsonObject.getString(LTNConstants.RESULT_MESSAGE);
                                // Log.e("TAG", resultMsg);
                            }
                        } catch (JSONException je) {
                            // Log.e("TAG", "出错了");
                        }
                    }

                    @Override
                    public void onReqFailed(String errorMsg) {
                        Log.d(TAG, errorMsg);
                    }
                });

    }

    private void setData() {
        User user = LTNApplication.getInstance().getCurrentUser();
        account = user.getAccount();
        if (account == null) {
            return;
        }


        tv_userbalance_value.setText(com.bwash.bwashcar.utility.TextUtils.formatDoubleValue(String.valueOf(account.usableBalance)));
        tv_birdcoin.setText(com.bwash.bwashcar.utility.TextUtils.formatDoubleValue(String.valueOf(account.birdCoin)));
        tv_total_asset.setText(com.bwash.bwashcar.utility.TextUtils.formatDoubleValue(String.valueOf(account.totalAsset)));

    }

    public void getAccountInfo() {

        HashMap<String, String> mReqParams = new HashMap();
        mReqParams.put(LTNConstants.CLIENT_TYPE_PARAM, LTNConstants.CLIENT_TYPE_MOBILE);
        mReqParams.put(LTNConstants.SESSION_KEY, LTNApplication.getInstance().getSessionKey());

        WCOKHttpClient.getOkHttpClient(getContext()).requestAsyn(LTNConstants.ACCESS_URL.MY_ACCOUNT_URL,
                WCOKHttpClient.TYPE_GET, mReqParams,
                new ReqCallBack<JSONObject>() {

                    @Override
                    public void onReqSuccess(JSONObject jsonObject) {
                        try {
                            String resultCode = jsonObject.getString(LTNConstants.RESULT_CODE);

                            if (resultCode.equals(LTNConstants.MSG_SUCCESS)) {
                                // 将用户信息存储到
                                // Log.e("TAG", "sessionkey--" + LTNApplication.getInstance().getSessionKey());
                                Gson gson = new Gson();
                                JSONObject accountInfoObj = (JSONObject) jsonObject.get(LTNConstants.DATA);
                                // Log.e("TAG", jsonObject.toString());
                                LTNApplication.getInstance().getCurrentUser().setAccount(gson.fromJson(accountInfoObj.toString(), User.Account.class));
                                setData();

                            } else {
                                String resultMsg = jsonObject.getString(LTNConstants.RESULT_MESSAGE);
                                // Log.e("TAG", resultCode + "+++" + resultMsg);
                                Toast.makeText(LTNApplication.getInstance(), resultMsg, Toast.LENGTH_SHORT);
                            }
                        } catch (JSONException je) {
                            // Log.e("JSONException", je.toString());
                        }
                    }

                    @Override
                    public void onReqFailed(String errorMsg) {

                    }


                });

    }


    protected void lazyLoad() {
        if (isPrepared || isVisible) {
            //	getData();
            //	showRadioGroup();
            //		autoScroll();
            initView();
        }
        //填充各控件的数据
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (getUserVisibleHint()) {
            // Log.d(FRAGMENT_TAG, "isVisibleToUser: true");
            isVisible = true;
        } else {
            // Log.d(FRAGMENT_TAG, "isVisibleToUser: false");
            isVisible = false;
        }
        // Log.d(TAG, "setUserVisibleHint");
        if (this.getView() != null) {
            isViewShown = true;
            lazyLoad();
            // Log.d(FRAGMENT_TAG, "setUserVisibleHint true");
        } else {
            isViewShown = false;
            // Log.d(FRAGMENT_TAG, "setUserVisibleHint false");
        }
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden && Utils.isNetworkConnected(getActivity())) {

            getAccountInfo();
            getUserInfo();
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.balance_layout://可用余额
                Intent intent = new Intent(getActivity(), BalanceDetailActivity.class);
                startActivity(intent);
                break;
            case R.id.birdcoin_help://可用余额
                ViewUtils.showBirdCoinHelpDialog(getActivity());
                break;
            case R.id.birdcoin_layout://可用鸟币
                intent = new Intent(getActivity(), BirdDetailActivity.class);
                startActivity(intent);
                break;
            case R.id.partner://合伙人
                intent = new Intent(getActivity(), PartnerActivity.class);
                startActivity(intent);
                break;
            case R.id.all_account://总资产
                intent = new Intent(getActivity(), TotalAssetsActivity.class);
                startActivity(intent);
                break;
            case R.id.coupon_layout://理财金券
                intent = new Intent(getActivity(), CouponsActivity.class);
                startActivity(intent);
                break;
            case R.id.invest_layout://我的投资
                intent = new Intent(getActivity(), MyInvestActivity.class);
                startActivity(intent);
                break;
            case R.id.tv_car://我的车子
                intent = new Intent(getActivity(), CarActivity.class);
                startActivity(intent);
                break;
            case R.id.tv_name://TODO:进入个人账户信息，可以修改昵称和名字
                intent = new Intent(getActivity(), CarActivity.class);
                startActivity(intent);
                break;
            case R.id.iv_profile://TODO: 进入修改头像
                intent = new Intent(getActivity(), CarActivity.class);
                startActivity(intent);
                break;
            case R.id.my_account:
                Intent mIntent = new Intent(this.getActivity(), AccountInfoActivity.class);
                startActivity(mIntent);
                break;

        }
    }

    public boolean checkIsBindBank(User.UserInfo userInfo) {
        boolean flag = true;
        if (TextUtils.isEmpty(userInfo.getUserName())) {
            Utils.showRealNameWarn(getActivity());
            flag = false;
        } else if (!(userInfo.getBankAuthStatus() != null && userInfo.getBankAuthStatus().equals("1"))) {
            Utils.showBindBanWarn(getActivity());
            flag = false;
        }
        return flag;
    }


}

