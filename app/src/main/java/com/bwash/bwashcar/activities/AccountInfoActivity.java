package com.bwash.bwashcar.activities;

import android.content.Intent;
import android.os.*;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.google.gson.Gson;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.tendcloud.tenddata.TCAgent;
import com.bwash.bwashcar.R;
import com.bwash.bwashcar.application.LTNApplication;
import com.bwash.bwashcar.model.User;
import com.bwash.bwashcar.net.LTNHttpClient;
import com.bwash.bwashcar.utility.ActivityUtils;
import com.bwash.bwashcar.utility.LTNConstants;
import com.bwash.bwashcar.utility.Utils;
import com.bwash.bwashcar.utility.ViewUtils;
import com.bwash.bwashcar.view.MyEditText;
import com.bwash.bwashcar.view.UISwitchButton;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

/**
 * Created by rogerlzp on 15/12/18.
 */
public class AccountInfoActivity extends BaseActivity implements View.OnClickListener {
    private static final String TAG = AccountInfoActivity.class.getSimpleName();
    private TextView tvUserAuth, tvBindBankCard, tvModifyLoginPwd, tvResetTradePwd, tvModifyTradePwd,
            tvModifyGesturePwd;

    private Button mLogoutBtn;

    private MyEditText myEditText;

    private TextView tvTitle; //标题
    private UISwitchButton mUISwitchButton = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.account_info);
        initView();
    }

    @Override
    public void onClick(View v) {
        Intent nIntent;
        switch (v.getId()) {
            case R.id.back_btn:
                //  获取验证码;
                super.onBackPressed();
                break;
            case R.id.tv_reset_trade_password:
                Intent intent = new Intent(AccountInfoActivity.this, ForgetTradePwdActivity.class);
                startActivity(intent);
                break;
            case R.id.tv_modify_trade_password:
                intent = new Intent(AccountInfoActivity.this, ModifyTradePwdActivity.class);
                startActivity(intent);
                break;
            case R.id.notice_set://消息通知设置
                intent = new Intent(AccountInfoActivity.this, NotificationSettingActivity.class);
                startActivity(intent);
                break;
            case R.id.rl1:
            case R.id.tv_user_auth_value:
                if (LTNApplication.getInstance().getCurrentUser().getUserInfo().getUserName() == null) { // 没有实名认证了
                    nIntent = new Intent(AccountInfoActivity.this, UserAuthActivity.class);
                    startActivity(nIntent);
                } else {
                    nIntent = new Intent(AccountInfoActivity.this, UserHasAuthedActivity.class);
                    startActivity(nIntent);
                }
                break;
            case R.id.rl2:
            case R.id.tv_bind_bank_card:
                if (TextUtils.isEmpty(LTNApplication.getInstance().getCurrentUser().getUserInfo().getUserName())) {
                    Utils.showRealNameWarn(this);
                } else {
                    // 绑定银行卡
                    if (LTNApplication.getInstance().getCurrentUser().getUserInfo().getBankAuthStatus() != null && LTNApplication.getInstance().getCurrentUser().getUserInfo().getBankAuthStatus().equals("1")) {
                        nIntent = new Intent(AccountInfoActivity.this, BindedBankCardActivity.class);
                        startActivity(nIntent);
                    } else {
                        nIntent = new Intent(AccountInfoActivity.this, NoBankCardActivity.class);
                        startActivity(nIntent);
                    }
                }


                break;

            case R.id.tv_modify_login_password:
                // 修改登录密码
                nIntent = new Intent(AccountInfoActivity.this, ForgetPasswordActivity.class);
                startActivity(nIntent);
                break;

            case R.id.tv_modify_gesture_password:
                // 修改手势密码
                nIntent = new Intent(AccountInfoActivity.this, GestureVerifyActivity.class);
                nIntent.putExtra(LTNConstants.FROM_MODIFY_GESTURE_PASSWORD, LTNConstants.GESTURE_PASSWORD);
                nIntent.addFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT);
                nIntent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(nIntent);
                break;


            case R.id.btn_logout:
                // 确认
                logout();
                break;
        }
    }

    public void getUserInfo() {
        RequestParams mReqParams = new RequestParams();
        mReqParams.add(LTNConstants.CLIENT_TYPE_PARAM, LTNConstants.CLIENT_TYPE_MOBILE);
        mReqParams.add(LTNConstants.SESSION_KEY, LTNApplication.getInstance().getSessionKey());
        //TODO: 添加错误或者失败的跳转
        LTNHttpClient.getLTNHttpClient().get(LTNConstants.ACCESS_URL.MY_USER_INFO_URL, mReqParams,
                new JsonHttpResponseHandler() {
                    public void onSuccess(int statusCode, Header[] headers, JSONObject jsonObject) {
                        try {
                            String resultCode = jsonObject.getString(LTNConstants.RESULT_CODE);

                            if (resultCode.equals(LTNConstants.MSG_SUCCESS)) {
                                // 将用户信息存储到
                                Gson gson = new Gson();
                                JSONObject accountInfoObj = (JSONObject) jsonObject.get(LTNConstants.DATA);
                                LTNApplication.getInstance().getCurrentUser().setUserInfo(gson.fromJson(accountInfoObj.toString(), User.UserInfo.class));
                                initData();
                            } else {
                                String resultMsg = jsonObject.getString(LTNConstants.RESULT_MESSAGE);
                            }
                        } catch (JSONException je) {

                        }
                    }

                    //TODO: 增加对网络错误的处理,等待产品统一确定
                    public void onFailure(int statusCode, Header[] headers,
                                          Throwable throwable, JSONObject errorResponse) {
                        // Log.d(TAG, "onFailure in UserUtils" + errorResponse.toString());

                    }
                });

    }

    public void initData() {
        if (LTNApplication.getInstance().getCurrentUser().getUserInfo().getUserName() != null) {
            // 已经实名认证
            tvUserAuth.setText(getResources().getString(R.string.user_auth_true));
        } else {
            //未实名认证
            tvUserAuth.setText(getResources().getString(R.string.user_auth_false));
        }

        if (LTNApplication.getInstance().getCurrentUser().getUserInfo().getBankAuthStatus() != null && LTNApplication.getInstance().getCurrentUser().getUserInfo().getBankAuthStatus().equals("1")) {
            //已绑定银行卡
            tvBindBankCard.setText(getResources().getString(R.string.bank_card_binded));
        } else {
            //未绑定银行卡
            tvBindBankCard.setText(getResources().getString(R.string.bank_card_unbind));
        }
    }

    public void initView() {
        tvUserAuth = (TextView) findViewById(R.id.tv_user_auth_value);
        tvUserAuth.setOnClickListener(this);

        tvBindBankCard = (TextView) findViewById(R.id.tv_bind_bank_card);
        tvBindBankCard.setOnClickListener(this);

        tvModifyLoginPwd = (TextView) findViewById(R.id.tv_modify_login_password);
        tvModifyLoginPwd.setOnClickListener(this);

        tvResetTradePwd = (TextView) findViewById(R.id.tv_reset_trade_password);
        tvResetTradePwd.setOnClickListener(this);

        tvModifyTradePwd = (TextView) findViewById(R.id.tv_modify_trade_password);
        tvModifyTradePwd.setOnClickListener(this);

        tvModifyGesturePwd = (TextView) findViewById(R.id.tv_modify_gesture_password);
        tvModifyGesturePwd.setOnClickListener(this);

        mLogoutBtn = (Button) findViewById(R.id.btn_logout);
        mLogoutBtn.setOnClickListener(this);


        this.findViewById(R.id.back_btn).setOnClickListener(this);

        tvTitle = (TextView) findViewById(R.id.title);
        tvTitle.setText(getResources().getText(R.string.account_settings));

        this.findViewById(R.id.tv_reset_trade_password).setOnClickListener(this);
        this.findViewById(R.id.tv_modify_trade_password).setOnClickListener(this);
        this.findViewById(R.id.rl2).setOnClickListener(this);
        this.findViewById(R.id.rl1).setOnClickListener(this);
        this.findViewById(R.id.notice_set).setOnClickListener(this);
        mUISwitchButton = (UISwitchButton) findViewById(R.id.toggleButton);
        mUISwitchButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                LTNApplication.getInstance().setNotification(isChecked);
            }
        });
    }


    public void logout() {


        ViewUtils.showWarnDialog(this, getString(R.string.quit_app), getString(R.string.btn_confirm),
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                        RequestParams mReqParams = new RequestParams();
                        mReqParams.add(LTNConstants.CLIENT_TYPE_PARAM, LTNConstants.CLIENT_TYPE_MOBILE);
                        mReqParams.add(LTNConstants.SESSION_KEY, LTNApplication.getInstance().getSessionKey());
                        //TODO: 添加错误或者失败的跳转
                        LTNHttpClient.getLTNHttpClient().get(LTNConstants.ACCESS_URL.LOGOUT_URL, mReqParams,
                                new JsonHttpResponseHandler() {
                                    public void onSuccess(int statusCode, Header[] headers, JSONObject jsonObject) {
                                        try {
                                            String resultCode = jsonObject.getString(LTNConstants.RESULT_CODE);

                                            if (resultCode.equals(LTNConstants.MSG_SUCCESS)) {

                                            } else {
                                                String resultMsg = jsonObject.getString(LTNConstants.RESULT_MESSAGE);
                                            }
                                        } catch (JSONException je) {

                                        }
                                    }

                                    //TODO: 增加对网络错误的处理,等待产品统一确定
                                    public void onFailure(int statusCode, Header[] headers,
                                                          Throwable throwable, JSONObject errorResponse) {
                                    }
                                });

                        // 先提交请求,不等返回结果,直接从客户端退出
                        LTNApplication.getInstance().clearUser();
                        ActivityUtils.finishAll();
                        Intent intent = new Intent(AccountInfoActivity.this, MainActivity.class);
                        startActivity(intent);


                    }
                });


    }

    @Override
    public void onResume() {
        super.onResume();
        // Talking data
        TCAgent.onResume(this);
        getUserInfo();
        mUISwitchButton.setChecked(LTNApplication.getInstance().getNotification());
    }

    @Override
    public void onPause() {
        super.onPause();
        // Talking data
        TCAgent.onPause(this);
    }


}
